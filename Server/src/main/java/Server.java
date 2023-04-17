import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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
    class ConnThread extends Thread{
        
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
                this.server = new ServerSocket(getPort());

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
					System.out.println("Server received:"+data);

					if (data.contains("QUIT")) {
						Server.this.connthread.sockets.remove(this.name);
						refreshClientList();
						sendToAll("DISCONNECTED#" + this.name);
						this.socket.close();
						break;
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
