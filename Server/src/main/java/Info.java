import java.util.ArrayList;

public class Info extends Deck {

	private ArrayList<Card> playerHand;
	private ArrayList<Card> dealerHand;

	private int ante, bet, pairplus, money;

	public void updateDealer(ArrayList<Card> dealerHand) {
		this.dealerHand = dealerHand;
	}

	public void updatePlayer(ArrayList<Card> playerHand) {
		this.playerHand = playerHand;
	}

	public void updateAnte(int ante) {
		this.ante = ante;
	}

	public void updateBet(int bet) {
		this.bet = bet;
	}

	public void updatePairplus(int pairplus) {
		this.pairplus = pairplus;
	}

	public void updateMoney(int money) {
		this.money = money;
	}

	public ArrayList<Card> getPlayer() {
		return playerHand;
	}
	
	public ArrayList<Card> getDealer() {
		return dealerHand;
	}

	public int getAnte() {
		return ante;
	}
	
	public int getBet() {
		return bet;
	}

	public int getPairplus() {
		return pairplus;
	}

	public int getMoney() {
		return money;
	}
	
}
