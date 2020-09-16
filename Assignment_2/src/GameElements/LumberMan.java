package GameElements;

public class LumberMan extends Worker {
    /**
     * This class represents the lumberman that can be used to collect lumber from lumber mill. Each lumberman is
     * responsible for 1 lumber mill.
     * @param p - productivity
     * @param l - level
     * @param c - cost
     * @param t - train time
     */
    public LumberMan(Double p, Integer l, Integer c, Integer t) {
        super(p, l, c, t);
    }

    /**
     * This method allows the lumber man to collect lumber from the lumber mill according to the productivity stat.
     * @param lM
     * @return - lumber
     */
    public Double lumberMining (LumberMill lM){
        if(super.getProduction() <= lM.getCurLumber()){
            Double lumber = lM.getCurLumber()-super.getProduction();
            lM.setCurLumber(lumber);
            return lumber;
        }else{
            Double lumber = lM.getCurLumber();
            lM.setCurLumber(0.);
            return lumber;
        }
    }
}
