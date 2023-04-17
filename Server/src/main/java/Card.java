import javafx.scene.image.Image;

public class Card implements Comparable<Card> {
    private int rank; //ace, queen, king = 11, 12, 13
    private int suit;
    private Image image;

	// default constructor
	public Card() {}

    public Card(int rank, int suit, Image image) {
        this.rank = rank;
        this.suit = suit;
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

    public Image getImage() {
        return this.image;
    }
}
