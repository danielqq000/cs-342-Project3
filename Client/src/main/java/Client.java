import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.function.Consumer;



public class Client extends Thread{


    Socket socketClient;

    ObjectOutputStream out;
    ObjectInputStream in;
    String host;
    private int port;

    private Consumer<Serializable> callback;

    public Client(Consumer<Serializable> call, String host, int port){
        if (host == "localhost") {
            this.host = "127.0.0.1";
        } else
            this.host = host;
        this.port = port;
        callback = call;
    }

    public void run() {

        try {
            socketClient = new Socket(InetAddress.getByName(this.host), this.port);
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            socketClient.setTcpNoDelay(true);
			System.out.println(socketClient.getInetAddress() + ": " + socketClient.getPort());
        }
        catch(Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException("Client failed");
		}

        while(true) {

            try {
                String message = in.readObject().toString();
                callback.accept(message);
            }
            catch(Exception e) {}
        }

    }

    public void send(String data) {

        try {
            out.writeObject(data);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}

