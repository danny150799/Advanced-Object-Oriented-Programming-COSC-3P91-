package GameElements;

import java.io.PrintWriter;

public class Catapult extends Army {
    /**
     * This class represent a catapult unit in the army so it extends the army abstract class.
     * @param d - damage
     * @param h - hit points
     * @param l - level
     * @param c - cost
     * @param t - train time
     */
    public Catapult(Double d, Double h, Integer l, Integer c, Integer t) {
        super(d, h, l, c, t);
    }

    /**
     * This method upgrades the catapult, it focuses on increase the damage, hit points and cost.
     */
    @Override
    public void upgrade(PrintWriter out) {
        if(super.getLevel()<10) {
            super.setLevel(super.getLevel() + 1);
            super.setDamage(super.getDamage() + (super.getLevel() * 5));
            super.setHitPoints(super.getHitPoints() + (super.getLevel() + 6));
            super.setCost(super.getCost() + (super.getLevel() * 5));
            super.setTrainTime(super.getTrainTime() + super.getLevel() * 300);
            super.setMaxHitPoints(super.getMaxHitPoints() +(super.getLevel() + 6));
        }else{
            out.println("Level 10 is the max level.");
        }
    }

    /**
     * Catapult deal damages so this method can be used to retrieve the damage stat.
     * @return
     */
    @Override
    public Double dealDamage() {
        return super.getDamage();
    }
}
