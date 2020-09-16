package GameElements;

import java.io.PrintWriter;

public class Cannon extends Building implements Damager{
    private Double damage;

    /**
     * This class represents the cannon building which is a building that can deal damage like the archer tower. It extends
     * the Building abstract class and implements the Damager interface.
     * @param h - hit points
     * @param l - level
     * @param c - cost
     * @param d - damage
     * @param b - build time
     */
    public Cannon(double h, Integer l, Integer c, Double d, Integer b) {
        super(h, l, c, b);
        this.damage = d;
    }

    /**
     * This method upgrades the stats of a cannon, mostly focus on the damage.
     */
    @Override
    public void upgrade(PrintWriter out) {
        if(super.getLevel()<10) {
            super.setLevel(super.getLevel() + 1);
            super.setHitPoints(super.getHitPoints() + super.getLevel() * 4);
            super.setCost(super.getCost() + super.getLevel() * 2);
            this.damage += super.getLevel() * 2;
            super.setBuildTime(super.getBuildTime() + super.getLevel() * 300);
            super.setMaxHitPoints(super.getMaxHitPoints() +(super.getLevel() + 4));
        }else{
            out.println("Level 10 is the max level.");
        }
    }

    /**
     * Cannon can deal damage so this method can be used to retrieve the damage of this cannon.
     * @return
     */
    @Override
    public Double dealDamage() {
        return this.damage;
    }
}
