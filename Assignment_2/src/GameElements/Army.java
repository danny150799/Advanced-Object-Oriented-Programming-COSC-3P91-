package GameElements;

import java.io.PrintWriter;

public abstract class Army implements Damager{
    private Double damage;
    private Double hitPoints;
    private Integer level;
    private Integer cost;
    private Integer trainTime;
    private Double maxHitPoints;

    /**
     * This abstract class implements Damager interface. This abstract class represents the army units such soldier,
     * archer, knight and catapult. They have things in commons such as hit points, damage, level, range, cost and train
     * time as well as the ability to upgrade their stats so abstract class is the most suitable choice.
     * @param d - damage
     * @param h - hit points
     * @param l - level
     * @param c - cost
     * @param t - train time
     */
    public Army(Double d, Double h, Integer l, Integer c, Integer t){
        this.damage = d;
        this.hitPoints = h;
        this.level = l;
        this.cost = c;
        this.trainTime = t;
        this.maxHitPoints = hitPoints;
    }

    /**
     * Below are get and set helper methods that can be used to retrieve or set numbers for the army unit.
     * @return
     */
    public Double getDamage(){return this.damage;}
    public Double getHitPoints(){return this.hitPoints;}
    public Integer getLevel(){return this.level;}
    public Integer getCost(){return this.cost;}
    public Integer getTrainTime(){return this.trainTime;}
    public Double getMaxHitPoints(){return this.maxHitPoints;}

    public void setDamage(Double d){ this.damage = d;}
    public void setHitPoints(Double h){ this.hitPoints = h;}
    public void setLevel(Integer l){ this.level = l;}
    public void setCost(Integer c){ this.cost = c;}
    public void setTrainTime(Integer t) { this.trainTime = t; }
    public void setMaxHitPoints(Double m){this.maxHitPoints = m; }

    /**
     * This abstract method is the common feature of the army unit which is can be used to level up. Each unit's stats
     * are upgraded differently so it's best to leave this as an abstract method.
     */
    public abstract void upgrade(PrintWriter out);
}
