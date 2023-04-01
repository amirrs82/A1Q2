package Model;

public class Castle {


    private final int damage;
    private int hitPoint;
    private final String side;

    public Castle(int hitPoint, int damage, String side) {
        this.hitPoint = hitPoint;
        this.damage = damage;
        this.side = side;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
    }

    public int getDamage() {
        return damage;
    }

    public String getSide() {
        return side;
    }
}
