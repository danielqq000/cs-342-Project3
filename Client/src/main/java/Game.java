public class Game {
    private int ante = 0;
    private int antepay = 0;
    private int pairplus = 0;
    private int pairpluspay = 0;
    private int wager = 0;
    private int balance = 0;
    private int totalWins = 0;

    public void setAnte(int ante) { this.ante = ante; }
    public void setPairPlus(int pairplus) { this.pairplus = pairplus; }
    public void setBalance(int balance) { this.balance = balance; }
    public int getAnte() { return this.ante; }
    public int getPairPlus() { return this.pairplus; }
    public int getBalance() { return this.balance; }

}
