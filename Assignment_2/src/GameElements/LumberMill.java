package GameElements;

import java.io.PrintWriter;

public class LumberMill extends Building {
    private Double maxLumber;
    private Double curLumber;
    private Double lumberRate;

    /**
     * This class represents the lumber mill building that produce lumber. It has current lumber, maximum lumber
     * and the rate of producing lumber
     * @param h - hit points
     * @param l - level
     * @param c - cost
     * @param b - build time
     */
    public LumberMill(Double h, Integer l, Integer c, Integer b) {
        super(h, l, c, b);
        this.maxLumber = 20.;
        this.curLumber = 0.;
        this.lumberRate = 2.;
    }

    /**
     * This helper method retrieves the current lumber stat.
     * @return - current lumber
     */
    public Double getCurLumber(){return this.curLumber;}

    /**
     * This method upgrades the lumber mill, mostly focus on its maximum lumber and lumber rate.
     */
    @Override
    public void upgrade(PrintWriter out) {
        if(super.getLevel()<10) {
            super.setLevel(super.getLevel() + 1);
            super.setHitPoints(super.getHitPoints() + super.getLevel() * 5);
            super.setCost(super.getCost() + super.getLevel() * 2);
            this.maxLumber += super.getLevel() * 3;
            this.lumberRate += super.getLevel() * 2;
            super.setBuildTime(super.getBuildTime() + super.getLevel() * 300);
            super.setMaxHitPoints(super.getMaxHitPoints() +(super.getLevel() + 5));
        }
    }

    /**
     * This method produces lumber depends on the input as time.
     * @param time
     * @return
     */
    public Double lumberProduction(double time){
        curLumber += lumberRate*time;
        if(curLumber>maxLumber){
            curLumber = maxLumber;
        }
        return curLumber;
    }

    /**
     * This method set current lumber which can be used to adjust the current lumber after collecting.
     * @param l
     */
    public void setCurLumber(Double l){
        this.curLumber = l;
    }
}
