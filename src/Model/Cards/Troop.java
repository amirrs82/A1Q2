package Model.Cards;

public class Troop extends Card {

    public int getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    private int hitPoint;

    public Troop(int damage, int hitPoint, int price, String cardName) {
        super(damage, price, cardName);
        this.hitPoint = hitPoint;
    }
}
