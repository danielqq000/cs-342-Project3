import javafx.scene.image.Image;

import java.io.Serializable;

public class Card implements Comparable<Card>, Serializable {
    private int rank; //ace, queen, king = 11, 12, 13
    private int suit; //1, 2, 3, 4 = clubs, spades, hearts, diamonds
    private String cardSR;
    private Image image;

    public Card() {}

    public Card(int rank, int suit, String cardSR, Image image) {
        this.rank = rank;
        this.suit = suit;
        this.cardSR = cardSR;
        this.image = image;
    }

    @Override
    public int compareTo(Card other) {
        if (this.rank > other.rank)
            return 1;
        else if (this.rank < other.rank)
            return -1;
        else
            return 0;
    }

    public int getRank() {
        return this.rank;
    }

    public int getSuit() {
        return this.suit;
    }

    public String getCardSR() { return this.cardSR; }

    public Image getImage() {
        return this.image;
    }
}
