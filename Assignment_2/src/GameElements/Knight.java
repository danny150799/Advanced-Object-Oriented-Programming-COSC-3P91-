package GameElements;

import java.io.PrintWriter;

public class Knight extends Army{
    /**
     * This class represent a knight unit in the army. It extends the army.
     * @param d - damage
     * @param h - hit point
     * @param l - level
     * @param c- cost
     * @param t - train time
     */
    public Knight(Double d, Double h, Integer l, Integer c, Integer t) {
        super(d, h, l, c, t);
    }

    /**
     * This method upgrades the knight stats, mostly focus on the damage, hit points and cost.
     */
    @Override
    public void upgrade(PrintWriter out) {
        if(super.getLevel()<10) {
            super.setLevel(super.getLevel() + 1);
            super.setDamage(super.getDamage() + (super.getLevel() * 4));
            super.setHitPoints(super.getHitPoints() + (super.getLevel() + 5));
            super.setCost(super.getCost() + (super.getLevel() * 3));
            super.setTrainTime(super.getTrainTime() + super.getLevel() * 300);
            super.setMaxHitPoints(super.getMaxHitPoints() +(super.getLevel() + 5));
        }else{
            out.println("Level 10 is the max level.");
        }
    }

    /**
     * The army implements the Damage interface since all army units can deal damage.
     * @return - damage
     */
    @Override
    public Double dealDamage() {
        return super.getDamage();
    }
}
