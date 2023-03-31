package Model.Cards;

import Model.User;

public class Card {
    private final int damage;
    private final int price;
    private int currentRow;
    private User owner;
    private final String cardName;

    public Card(int damage, int price, String cardName) {
        this.damage = damage;
        this.price = price;
        this.cardName = cardName;
    }

    public int getPrice() {
        return price;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int getDamage() {
        return damage;
    }

    public String getCardName() {
        return cardName;
    }
}
