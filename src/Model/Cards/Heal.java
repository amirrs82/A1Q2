package Model.Cards;

public class Heal extends Spell {
    private int roundHealed = 0;


    public int getRoundHealed() {
        return roundHealed;
    }

    public void setRoundHealed(int roundHealed) {
        this.roundHealed = roundHealed;
    }

    //it does not damage but heals
    static {
        setDamage(1000);
    }
}
