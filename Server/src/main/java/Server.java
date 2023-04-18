import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.function.Consumer;


public class Server {

    private int port;

    private ConnThread connthread;
    private Consumer<Serializable> callback;

	// constructor
    Server(int port, Consumer<Serializable> callback) {
        this.callback = callback;
        this.connthread = new ConnThread();
        this.connthread.setDaemon(true);
        this.port = port;
    }

	// connection start and close
    public void startConn(){
        connthread.start();
    }

	public void closeConn() throws Exception{
        for( ClientThread t: connthread.sockets.values()) {
            t.socket.close();
        }
        connthread.server.close();
    }

	private int getPort() {
        return port;
    }
	
	// send data to client
    public void sendToAll(Serializable data) throws Exception{
        System.out.println("Server Send (All):"+ data);
        for( ClientThread t: connthread.sockets.values()){
            t.out.writeObject(data);
        }
    }

    public void sendToOne(Serializable data, ClientThread t) throws Exception{
        System.out.println("Server Sent:" + data);
        t.out.writeObject(data);
    }

	// GUI about Clients
    private void updateClientNumFX(){
        callback.accept("PLAYERNUM#" + connthread.sockets.size());
    }

    private void refreshClientList(){
        String base = "REFRESHLIST#";
        for(String s : connthread.sockets.keySet()){
            base += s + "\n";
        }
        callback.accept(base);
    }

	// For Server, recation when Client comes in
    class ConnThread extends Thread {
        
		private ServerSocket server;
        private Integer counter;
        private HashMap<String, ClientThread> sockets;

		// constructoer
        ConnThread(){
            this.counter = 0;
            this.sockets = new HashMap<>();
        }

        public void run() {
            try{
				// create socket
                this.server = new ServerSocket(getPort(), 4, InetAddress.getByName("127.0.0.1"));
				System.out.println(server.getInetAddress().getHostAddress() + ": " + server.getLocalPort());
                while(true) {
                    Socket s = server.accept();
                    String name = "Player" + this.counter;

                    ClientThread t = new ClientThread(s, name);

                    this.counter++;
                    this.sockets.put(name, t);

                    // inform this client about its name
                    sendToOne("CONNECTED!\nNAME#" + name, t);

                    // update client number in JavaFX
                    updateClientNumFX();

                    // refresh client list in JavaFX
                    refreshClientList();


                    t.start();
                }

            }
            catch(Exception e) {
                System.out.println(e);
                callback.accept("connection Closed, this is from the callback of cnnthread in server.java");
            }
        }
    }

	// For Client thread, created when a new Client comes in
    public class ClientThread extends Thread {
        
		private Socket socket;
        private String name;
        private ObjectOutputStream out;
        private ObjectInputStream in;
		private Game game;

		// constructor
        ClientThread(Socket s, String name){
            this.socket = s;
            this.name = name;

            try {
                this.socket.setTcpNoDelay(true);
                this.out = new ObjectOutputStream(this.socket.getOutputStream());
				this.in = new ObjectInputStream(this.socket.getInputStream());
			}
			catch (Exception e) {
				System.out.println(e);
				throw new RuntimeException("Error from ClientThread constuctor, Server");
			}
		}

		public void run() {
			try{
				while(true) {
					String data = in.readObject().toString();
					String[] tokens = data.split("#");

					// QUIT
					if (data.contains("QUIT")) {
						Server.this.connthread.sockets.remove(this.name);
						refreshClientList();
						sendToAll("PLAYER #" + this.name + "DISCONNECTED");
						this.socket.close();
						break;
					}

					// game start
					// MONEY# + <money>
					if(data.contains("MONEY#")) {
						game.updateMoney(Integer.parseInt(tokens[1]));
					}
					// DRAW# + <ante> + # + <pairplus>
					else if(data.contains("DRAW#")) {
						// get ante
						game.updateAnte(Integer.parseInt(tokens[1]) + game.getAnte());
						game.updatePairplus(Integer.parseInt(tokens[2]));
						game.updateMoney(game.getMoney() - Integer.parseInt(tokens[1]) - Integer.parseInt(tokens[2]));

						// create hand and send
						try{
						game.dealCards();
						}catch(Exception e){
							game = new Game();
							game.dealCards();
						}
						out.writeObject("CARDS#");
						out.writeObject(game.getPlayer());
						out.writeObject(game.getDealer());
						// send status
						String s = "STATS#MONEY#" + game.getMoney() + "ANTE#" + game.getAnte() + "PAIR#" + game.getPairplus();
						out.writeObject(s);
					}
					// BET# + <bet>
					else if(data.contains("BET#")) {
						// take bets
						game.updateBet(Integer.parseInt(tokens[1]));
						game.updateMoney(game.getMoney() - Integer.parseInt(tokens[1]));

						// send status
						out.writeObject("STATS#MONEY#" + game.getMoney() + "BET#" + game.getBet());

						// game logic
						// check pairplus first
						int ante = game.getAnte();
						int bet = game.getBet();
						int pp = game.getPairplus();
						pp *= game.determinePairplus();
						game.updatePairplus(0);

						// check dealer's Queen high
						if(!game.checkDealer()) {
							bet = 0;
							game.updateAnte(ante);
							game.updateBet(0);
							game.updateMoney(game.getMoney() + bet + pp);
							out.writeObject("QH#MONEY#" + game.getMoney() + "ANTE#" + ante + "BET#" + bet + "PAIR#" + pp);
						}
						else{
							char c = game.determineWinner();
							// player wins
							if( c == 'P') {
								ante *= 2;
								bet *= 2;
								game.updateAnte(0);
								game.updateBet(0);
								game.updateMoney(game.getMoney() + ante + bet + pp);
								out.writeObject("WIN#MONEY#" + game.getMoney() + "ANTE#" + ante + "BET#" + bet + "PAIR#" + pp);
							}
							// player loses
							else if ( c == 'D') {
								ante = 0;
								bet = 0;
								game.updateAnte(0);
								game.updateBet(0);
								game.updateMoney(game.getMoney() + ante + bet + pp);
								out.writeObject("LOSE#MONEY#" + game.getMoney() + "ANTE#" + ante + "BET#" + bet + "PAIR#" + pp);
							}
							// player ties
							else{
								bet = 0;
								game.updateAnte(0);
								game.updateBet(0);
								game.updateMoney(game.getMoney() + bet + pp);
								out.writeObject("TIE#MONEY#" + game.getMoney() + "ANTE#" + ante + "BET#" + bet + "PAIR#" + pp);
							}
						}

						// update STATS
						out.writeObject("STATS#MONEY#" + game.getMoney() + "ANTE#" + game.getAnte() + "BET#" + game.getBet() + "PAIR#" + game.getPairplus());
					}

					// FOLD
					if(data.contains("FOLD#")) {
						game.updateAnte(0);
						game.updateBet(0);
						game.updatePairplus(0);
					}
				}

			}
			catch(Exception e) {
				System.out.println(e);
				callback.accept("connection Closed by ClientThread");
				throw new RuntimeException("Error from ClientThread run(), Server");
			}
		}

	}

}
