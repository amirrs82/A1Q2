package Model.Cards;

import Model.User;

public class Card {
    private int damage;
    private int currentRow;
    private int currentColumn;
    private final int price;

    private User owner;

    private String cardName;

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

    public int getCurrentColumn() {
        return currentColumn;
    }

    public void setCurrentColumn(int currentColumn) {
        this.currentColumn = currentColumn;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }


    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
}
