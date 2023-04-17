// Game.java
// GameLogic

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Game extends Info {

	private Deck deck;
	private ArrayList<Card> playerHand;
	private ArrayList<Card> dealerHand;

	// constructor
	public Game() {
		deck = new Deck();
		deck.createDeck();
		playerHand = new ArrayList<>();
		dealerHand = new ArrayList<>();
	}

	// random card logic
	private Card getRandomCard() {

		Card card;
		Random random = new Random();
		int rnd1, rnd2;
		String input;
		
		do{
			rnd1 = random.nextInt(4) + 1;
			rnd2 = random.nextInt(13) + 1;
			input = rnd1 + "," + rnd2;
			card = deck.getCard(input);
		}while(card == null);

		return card;
	}

	public void dealCards() {

		// check deck size
		if(deck.getSize() <= 6){
			throw new RuntimeException("Not enough cards in the deck.");
		}

		for(int i = 0; i < 3; i++){
			playerHand.add(getRandomCard());
		}

		for(int i = 0; i < 3; i++){
			dealerHand.add(getRandomCard());
		}

		Collections.sort(playerHand);
		Collections.sort(dealerHand);
	}

	// check if dealer has queen high or better
	public boolean checkDealer() {
		
		if(evaluate(dealerHand) == 1){
			int n = dealerHand.get(2).getRank();
			if( n < 12)
				return false;
		}

		return true;
	}

	public char determineWinner() {

		int dealerRank = evaluate(dealerHand);
		int playerRank = evaluate(playerHand);

		// D for dealer, P for player, T for tie
		if (dealerRank > playerRank)
			return 'D';
		else if (dealerRank < playerRank)
			return 'P';
		else {
			return higherEvaluate(dealerHand, playerHand);
		}	
	}

	public int determinePairplus() {

		int multi = evaluate(playerHand);
		switch(multi){
			case 6:
				return 40;
			case 5:
				return 30;
			case 4:
				return 6;
			case 3:
				return 3;
			case 2:
				return 1;
			default:
				return 0;
		}
	}

	private int evaluate(ArrayList<Card> hand) {

		int rank1 = hand.get(0).getRank();
		int rank2 = hand.get(1).getRank();
		int rank3 = hand.get(2).getRank();

		int suit1 = hand.get(0).getSuit();
		int suit2 = hand.get(1).getSuit();
		int suit3 = hand.get(2).getSuit();

		// is straight flush
		if ((rank1 == rank2 -1 && rank2 == rank3 -1) || (rank1 == 1 && rank2 == 12 && rank3 == 13))
			if (suit1 == suit2 && suit2 == suit3)
				return 6;

		// is three of a kind
		if (rank1 == rank2 && rank2 == rank3)
			return 5;

		// is straight
		if ((rank1 == rank2 -1 && rank2 == rank3 -1) || (rank1 == 1 && rank2 == 12 && rank3 == 13))
			return 4;

		// is flush
		if (suit1 == suit2 && suit2 == suit3)
			return 3;

		// is pair
		if (rank1 == rank2 || rank2 == rank3)
			return 2;

		return 1;
	}

	// if determine rank tie, check condition
	private char higherEvaluate(ArrayList<Card> dealerHand, ArrayList<Card> playerHand) {

		int dealerBiggest = dealerHand.get(2).getRank();
		int playerBiggest = playerHand.get(2).getRank();
		int E = evaluate(dealerHand);
		switch(E){
			// straight/straight flush
			case 6:
			case 4:
				// check biggest cards
				if (dealerBiggest > playerBiggest)
					return 'D';
				else if (dealerBiggest < playerBiggest)
					return 'P';
				else 
					return 'T';

			// flush/high card
			case 3:
			case 1:
				int i = 2;
				// check cards from biggest to smallest
				while(i >= 0){
					if (dealerHand.get(i).getRank() > playerHand.get(i).getRank())
						return 'D';
					else if (dealerHand.get(i).getRank() < playerHand.get(i).getRank())
						return 'P';
					else
						i--;
				}
				return 'T';

			// pair
			case 2:
				// check pair first
				if (dealerHand.get(1).getRank() > playerHand.get(1).getRank())
					return 'D';
				if (dealerHand.get(1).getRank() < playerHand.get(1).getRank())
					return 'P';
				// check highest card
				if (dealerHand.get(1).getRank() == dealerHand.get(2).getRank())
					dealerBiggest = dealerHand.get(0).getRank();
				if (playerHand.get(1).getRank() == playerHand.get(2).getRank())
					playerBiggest = playerHand.get(0).getRank();

				if(dealerBiggest > playerBiggest)
					return 'D';
				else if (dealerBiggest < playerBiggest)
					return 'P';
				else 
					return 'T';

			// else case all return tie
			default:
				return 'T';
		}
	}

}
