import java.util.ArrayList;

public class Game extends Deck {
    private ArrayList<Card> playerHand;
    private ArrayList<Card> dealerHand;
    private String type;
    private int ante = 0;
    private int pairplus = 0;
    private int wager = 0;
    private int balance = 0;
    private int totalWins = 0;

    public void updateDealer(ArrayList<Card> dealerHand) {
        this.dealerHand = dealerHand;
    }

    public void updatePlayer(ArrayList<Card> playerHand) {
        this.playerHand = playerHand;
    }

    public ArrayList<Card> getPlayer() {
        return playerHand;
    }

    public ArrayList<Card> getDealer() {
        return dealerHand;
    }

    public void setAnte(int ante) { this.ante = ante; }
    public void setPairPlus(int pairplus) { this.pairplus = pairplus; }
    public void setBalance(int balance) { this.balance = balance; }
    public int getAnte() { return this.ante; }
    public int getPairPlus() { return this.pairplus; }
    public int getBalance() { return this.balance; }
    public void setType(String type) { this.type = type; }
    public String getType() { return this.type; }

}
