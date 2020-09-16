package GameElements;

import java.io.PrintWriter;

public class IronMine extends Building {
    private Double maxIron;
    private Double curIron;
    private Double ironRate;

    /**
     * This method represents the iron mine. Players can collect iron from this with an iron miner. It has the maximum iron
     * that it can hold, a current iron and the iron production rate.
     * @param h - hit point
     * @param l - level
     * @param c - cost
     * @param b - build time
     */
    public IronMine(Double h, Integer l, Integer c, Integer b) {
        super(h, l, c, b);
        this.maxIron = 20.;
        this.curIron =0.;
        this.ironRate = 2.;
    }

    /**
     * This method upgrades the gold mine, mostly focused on the maximum iron and the iron production rate.
     */
    @Override
    public void upgrade(PrintWriter out) {
        if(super.getLevel()<10) {
            super.setLevel(super.getLevel() + 1);
            super.setHitPoints(super.getHitPoints() + super.getLevel() * 5);
            super.setCost(super.getCost() + super.getLevel() * 2);
            this.maxIron += super.getLevel() * 3;
            this.ironRate += super.getLevel() * 2;
            super.setBuildTime(super.getBuildTime() + super.getLevel() * 300);
            super.setMaxHitPoints(super.getMaxHitPoints() +(super.getLevel() + 5));
        }else{
            out.println("Level 10 is the max level.");
        }
    }

    /**
     * This helper methods retrieve the current iron stat from the iron mine.
     * @return current iron
     */
    public Double getCurIron(){return this.curIron;}

    /**
     * This method allows the iron mine to produce iron with the input as time.
     * @param time
     * @return - iron
     */
    public Double ironProduction(double time){
        this.curIron += ironRate*time;
        if(curIron>maxIron){
            curIron = maxIron;
        }
        return curIron;
    }

    /**
     * This method sets the current iron of the mine. Can be used to adjust the current iron after collecting from it.
     * @param i
     */
    public void setCurIron(Double i){
        this.curIron = i;
    }
}
