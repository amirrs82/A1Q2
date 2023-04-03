package Model.Cards;

public class Troop extends Card {

    private int hitPoint;
    private final int maxHitPoint;

    public int getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public int getMaxHitPoint() {
        return maxHitPoint;
    }

    public Troop(int damage, int hitPoint, int price, String cardName) {
        super(damage, price, cardName);
        this.hitPoint = hitPoint;
        maxHitPoint = hitPoint;
    }
}
