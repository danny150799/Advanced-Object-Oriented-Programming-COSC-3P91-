package GameElements;

import java.io.PrintWriter;

public class VillageHall extends Building {

    /**
     * This class represent a village hall. This is where all of the resources are held.
     * @param h - hit point
     * @param l - level
     * @param c - cost
     * @param b - build time
     */
    public VillageHall(Double h, Integer l, Integer c, Integer b) {
        super(h, l, c, b);
    }

    /**
     * This method upgrades the village hall, mostly hit points.
     */
    @Override
    public void upgrade(PrintWriter out) {
        if(super.getLevel()<10) {
            super.setLevel(super.getLevel() + 1);
            super.setHitPoints(super.getHitPoints() + super.getLevel() * 6);
            super.setCost(super.getCost() + super.getLevel() * 2);
            super.setBuildTime(super.getBuildTime() + super.getLevel() * 300);
            super.setMaxHitPoints(super.getMaxHitPoints() +(super.getLevel() + 6));
        }else{
            out.println("Level 10 is the max level.");
        }
    }
}
