package GameElements;

import java.io.PrintWriter;

public class Farm extends Building {
    private static Integer maxPopulation;

    /**
     * This class represents a farm building. A farm determines how much army units and miners a player can have.
     * A player can only have 1 farm.
     * @param h - hit point
     * @param l - level
     * @param c - cost
     * @param b - built time
     * @param m - max population
     */
    public Farm(Double h, Integer l, Integer c, Integer b, Integer m) {
        super(h, l, c, b);
        maxPopulation = m;
    }

    /**
     * This method upgrades the farm, mostly focus on the maximum population a farm can handle.
     */
    @Override
    public void upgrade(PrintWriter out) {
        if(super.getLevel()<10) {
            super.setLevel(super.getLevel() + 1);
            super.setHitPoints(super.getHitPoints() + super.getLevel() * 5);
            super.setCost(super.getCost() + super.getLevel() * 2);
            maxPopulation += super.getLevel() * 5;
            super.setBuildTime(super.getBuildTime() + super.getLevel() * 300);
            super.setMaxHitPoints(super.getMaxHitPoints() +(super.getLevel() + 5));
        }else{
            out.println("Level 10 is the max level.");
        }
    }

    /**
     * This helper method helps retrieve the maximum population of the farm.
     * @return
     */
    public static Integer getMaxPopulation(){return maxPopulation;}
}
