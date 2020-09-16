package GameElements;

import java.io.PrintWriter;

public class ArcherTower extends Building implements Damager{
    private Double damage;

    /**
     * This class represents the archer tower in the defence system. It extends the building abtract class and implement
     * the Damager interface because archer tower and cannon are the only 2 building that can deal damage.
     * @param h
     * @param l
     * @param c
     * @param d
     * @param b
     */
    public ArcherTower(Double h, Integer l, Integer c, Double d, Integer b) {
        super(h, l, c, b);
        this.damage = d;
    }

    /**
     * The upgrade method for archer tower. It focuses on increasing hit points.
     */
    @Override
    public void upgrade(PrintWriter out) {
        if(super.getLevel()<10) {
            super.setLevel(super.getLevel() + 1);
            super.setHitPoints(super.getHitPoints() + super.getLevel() * 5);
            super.setCost(super.getCost() + super.getLevel() * 3);
            this.damage += super.getLevel() * 3;
            super.setBuildTime(super.getBuildTime() + super.getLevel() * 300);
            super.setMaxHitPoints(super.getMaxHitPoints() +(super.getLevel() + 5));
        }else{
            out.println("Level 10 is the max level.");
        }
    }

    /**
     * The archer tower implements Damager so it can deal damages.
     * @return - damage
     */
    @Override
    public Double dealDamage() {
        return damage;
    }
}
