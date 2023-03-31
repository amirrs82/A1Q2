package Model;

import Model.Cards.Card;

import java.util.ArrayList;

public class Place {
    private final ArrayList<Card> cards;

    private final String lineDirection;
    private final int rowNumber;

    public String getLineDirection() {
        return lineDirection;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Place(String lineDirection, int rowNumber) {
        this.lineDirection = lineDirection;
        this.rowNumber = rowNumber;
        cards = new ArrayList<>();
    }
}
