package GameElements;

import java.io.PrintWriter;

public class GoldMine extends Building {
    private Double maxGold;
    private Double curGold;
    private Double goldRate;

    /**
     * This class represents a gold mine which is a building. It has maximum number of gold that it can hold, a current
     * number of gold and the rate it can generate gold. To collect gold, the player must have at least one gold
     * miner. Each gold miner is responsible for one gold mine. At the start, the player has one gold mine.
     * @param h - health
     * @param l - level
     * @param c - cost
     * @param b - build time
     */
    public GoldMine(double h, Integer l, Integer c, Integer b) {
        super(h, l, c, b);
        this.maxGold = 20.;
        this.curGold = 0.;
        this.goldRate = 2.;
    }

    /**
     * This helper method retrieves the current gold number so that we know how much a player collect from a gold mine.
     * @return - current gold
     */
    public Double getCurGold(){return this.curGold;}

    /**
     * This method upgrade the gold mine, mostly focus on the maximum gold it can hold and the gold generation rate.
     */
    @Override
    public void upgrade(PrintWriter out) {
        if(super.getLevel()<10) {
            super.setLevel(super.getLevel() + 1);
            super.setHitPoints(super.getHitPoints() + super.getLevel() * 5);
            super.setCost(super.getCost() + super.getLevel() * 2);
            this.maxGold += super.getLevel() * 3;
            this.goldRate += super.getLevel() * 2;
            super.setBuildTime(super.getBuildTime() + super.getLevel() * 300);
            super.setMaxHitPoints(super.getMaxHitPoints() +(super.getLevel() + 5));
        }else{
            out.println("Level 10 is the max level.");
        }
    }

    /**
     * This method generate gold with the input as time. the current gold cannot be higher than the max gold.
     * @param time
     * @return - curr gold
     */
    public Double goldProduction(double time){
        curGold += goldRate*time;
        if(curGold>maxGold){
            curGold = maxGold;
        }
        return curGold;
    }

    /**
     * This helper method can be used to reduce the current gold as player can collect gold from the mine.
     * @param g
     */
    public void setCurGold(Double g){
        this.curGold = g;
    }
}
