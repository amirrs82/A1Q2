package Model.Cards;

public class Card {
    private static int damage;
    private static int hitPoint;
    private int currentRow;
    private int currentColumn;

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public int getCurrentColumn() {
        return currentColumn;
    }

    public void setCurrentColumn(int currentColumn) {
        this.currentColumn = currentColumn;
    }


    public static void setDamage(int damage) {
        Card.damage = damage;
    }

    public static void setHitPoint(int hitPoint) {
        Card.hitPoint = hitPoint;
    }

    public static int getHitPoint() {
        return hitPoint;
    }

    public static int getDamage() {
        return damage;
    }
}
