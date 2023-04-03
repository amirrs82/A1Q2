package Model.Cards;

public class Spell extends Card {

    int healAffect = 2;

    public int getHealAffect() {
        return healAffect;
    }

    public void setHealAffect(int healAffect) {
        this.healAffect = healAffect;
    }

    public Spell(int damage, int price, String cardName) {
        super(damage, price, cardName);
    }
}
