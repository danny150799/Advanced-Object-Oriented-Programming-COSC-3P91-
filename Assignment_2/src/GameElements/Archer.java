package GameElements;

import java.io.PrintWriter;

public class Archer extends Army {
    /**
     * This class represent an archer unit in the army so it extends the Army class.
     * @param d
     * @param h
     * @param l
     * @param c
     * @param t
     */
    public Archer(Double d, Double h, Integer l, Integer c, Integer t) {
        super(d, h, l, c, t);
    }

    /**
     * The upgrade method for archer unit. When upgraded the damage is increased many more compared to the hit points.
     * Which means this unit deals a lot of damage but also very fragile.
     */
    @Override
    public void upgrade(PrintWriter out) {
        if(super.getLevel()<10) {
            super.setLevel(super.getLevel() + 1);
            super.setDamage(super.getDamage() + (super.getLevel() * 3));
            super.setHitPoints(super.getHitPoints() + (super.getLevel() + 3));
            super.setCost(super.getCost() + (super.getLevel() * 2));
            super.setTrainTime(super.getTrainTime() + super.getLevel() * 300);
            super.setMaxHitPoints(super.getMaxHitPoints() +(super.getLevel() + 3));
        }else{
            out.println("Level 10 is the max level.");
        }
    }

    /**
     * A feature of Damager is that they deal damage.
     * @return - damage
     */
    @Override
    public Double dealDamage() {
        return super.getDamage();
    }
}
