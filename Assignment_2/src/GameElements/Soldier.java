package GameElements;

import java.io.PrintWriter;

public class Soldier extends Army {

    /**
     * This class represents a soldier unit in the army. The soldier is a cheap unit with low health and hit point,
     * fast train time. It extends the Army abstract class.
     * @param d - damage
     * @param h - hit points
     * @param l - level
     * @param c - cost
     * @param t - train time
     */
    public Soldier(Double d, Double h,  Integer l, Integer c, Integer t) {
        super(d, h, l, c, t);

    }

    /**
     * This method upgrade the soldier stats.
     */
    @Override
    public void upgrade(PrintWriter out) {
        if(super.getLevel()<10) {
            super.setLevel(super.getLevel() + 1);
            super.setDamage(super.getDamage() + (super.getLevel() * 2));
            super.setHitPoints(super.getHitPoints() + (super.getLevel() + 4));
            super.setCost(super.getCost() + super.getLevel());
            super.setTrainTime(super.getTrainTime() + super.getLevel());
            super.setMaxHitPoints(super.getMaxHitPoints() +(super.getLevel() + 4));
        }else{
            out.println("Level 10 is the max level.");
        }
    }

    /**
     * The soldier can deal damage so this method can be used to retrieve the damage stat.
     * @return - damage
     */
    @Override
    public Double dealDamage() {
        return super.getDamage();
    }
}
