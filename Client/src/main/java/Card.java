import javafx.scene.image.Image;

public class Card {
    private int rank; //ace, queen, king = 11, 12, 13
    private int suit;
    private Image image;

    public Card(int rank, int suit, Image image) {
        this.rank = rank;
        this.suit = suit;
        this.image = image;
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
