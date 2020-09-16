package GameElements;

import java.io.PrintWriter;

public abstract class Worker {
    private Double production;
    private Integer level;
    private Integer cost;
    private Integer trainTime;

    /**
     * This abstract class represents the worker in the village such as gold miner, iron miner and lumbermen.
     * They all have common stats and method but they also have different feature so let worker as an abstract class
     * is the most suitable choice.
     * @param p - productivity
     * @param l - level
     * @param c - cost
     * @param t - train time
     */
    public Worker(Double p, Integer l, Integer c, Integer t){
        this.production = p;
        this.level = l;
        this.cost = c;
        this.trainTime = t;
    }

    /**
     * Below are the helper methods that can be used to retrieve stats of a worker.
     * @return
     */
    public Double getProduction(){return this.production;}
    public Integer getLevel(){return this.level;}
    public Integer getCost(){return this.cost;}
    public Integer getTrainTime(){return this.trainTime;}

    /**
     * This method upgrade the worker, their stats are upgraded in the same pace.
     */
    public void upgrade(PrintWriter out){
        if(this.getLevel()<10) {
            this.level += 1;
            this.production += level * 3;
            this.cost += level * 2;
            this.trainTime += level * 300;
        }else{
            out.println("Level 10 is the max level.");
        }
    }
}
