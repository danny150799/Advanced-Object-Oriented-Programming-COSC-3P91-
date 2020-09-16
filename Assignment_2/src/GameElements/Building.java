package GameElements;

import java.io.PrintWriter;

public abstract class Building {

    private Double hitPoints;
    private Integer level;
    private Integer cost;
    private Integer buildTime;
    private Double maxHitPoints;

    /**
     * This abstract class represents the buildings in the village such as gold mine, iron mine, lumber mill,
     * archer tower, cannon, village hall and farm. They all have commons variables and methods so abstract class is
     * the most suitable choice.
     * @param h -  hit point
     * @param l - level
     * @param c - cost
     * @param b - build time
     */
    public Building(Double h, Integer l, Integer c, Integer b){
        this.hitPoints = h;
        this.level = l;
        this.cost = c;
        this.buildTime = b;
        this.maxHitPoints = hitPoints;
    }

    /**
     * Below are the set and get helper methods that can be used to retrieve or set stats for each building.
     * @return
     */
    public Double getHitPoints() { return this.hitPoints; }
    public Integer getLevel() { return this.level; }
    public Integer getCost(){return this.cost;}
    public Integer getBuildTime(){return this.buildTime;}
    public Double getMaxHitPoints(){return this.maxHitPoints;}

    public void setHitPoints(Double h){ this.hitPoints = h; }
    public void setLevel(Integer l){ this.level = l; }
    public void setCost(Integer c){ this.cost = c; }
    public void setBuildTime(Integer b) {this.buildTime = b;}
    public void setMaxHitPoints(Double m){this.maxHitPoints = m;}

    /**
     * Each building with focus on different stats when they are upgraded but they all "upgrade" so leave this method
     * as an abstract method the best choice.
     */
    public abstract void upgrade(PrintWriter out);

}
