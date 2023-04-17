import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Deck extends Card {
    private String cardSR;
    private Map<String, Card> deck = new HashMap<String, Card>();
    //1, 2, 3, 4 : clubs, spades, hearts, diamonds
    //11, 12, 13 : ace, queen, king
    public void createDeck() {
        String image;
        int value = 0;
        for (int suit = 1; suit <= 4; suit++) {
            for (int rank = 1; rank <= 13; rank++) {
                if (rank == 10 || rank == 23 || rank == 36 || rank == 49)
                    continue;
                if (rank%13 == 0)
                    value = 11;
                else
                    value = rank%13+1;
                image = rank + ".png";
                Image cardFace = new Image(image, 92, 95, true, true);
                Card card = new Card(value, suit, cardFace);
                cardSR = String.valueOf(suit) + "," + String.valueOf(value);
                deck.put(cardSR, card);
            }
        }
    }

    public Card getCard(String cardSR) {
        Card card = deck.get(cardSR);
		if (card != null){
			deck.remove(cardSR);
			return card;
		}
		else
			return null;
    }

	public int getSize() {
		return deck.size();
	}
}
