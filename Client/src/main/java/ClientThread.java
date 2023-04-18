import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;



public class ClientThread extends Thread{
    //Info info = new Info();
    //Game game = new Game();
    Socket socketClient;

    ObjectOutputStream out;
    ObjectInputStream in;
    String host;
    Boolean isConnected = false;
    private int port;

    private Consumer<Serializable> callback;

    public ClientThread(Consumer<Serializable> call, String host, int port){
        if (host == "localhost") {
            this.host = "127.0.0.1";
        } else
            this.host = host;
        this.port = port;
        this.callback = call;
    }

    public void run() {

        try {
            socketClient = new Socket(InetAddress.getByName(this.host), this.port);
            System.out.println(socketClient.getInetAddress() + ": " + socketClient.getPort());
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            socketClient.setTcpNoDelay(true);
            isConnected = true;
        }
        catch(Exception e) {
            System.out.println("Failed to connect to Server");
        }

        while(true) {

            try {
                String message = in.readObject().toString();
                callback.accept(message);
            }
            catch(Exception e) {}
        }

    }

    public boolean isConnected() {
        return isConnected;
    }

    public void sendValues(String data) {

        try {
            out.writeObject(data);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void receiveValues() {
        try {
            String values = in.readUTF();
            //game.setType(values);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendCards(ArrayList<Card> hands) {
        try {
            out.writeObject(hands);
            out.flush();
            //messageLabel.setText("Sent Object: " + card);
        } catch (IOException e) {
            //messageLabel.setText("Failed to send object: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void receiveCards() {
        try {
            ArrayList<Card> receivedCards = (ArrayList<Card>) in.readObject();
            //game.updatePlayer(receivedCards);
            receivedCards = (ArrayList<Card>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }






    //welcomefx FX = new wee(info);

}

