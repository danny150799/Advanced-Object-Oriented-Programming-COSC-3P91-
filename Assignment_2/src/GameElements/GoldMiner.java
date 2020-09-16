package GameElements;

public class GoldMiner extends Worker {
    /**
     * This class represents the gold miner. It extends the worker abstract class.
     * @param p - production
     * @param l - level
     * @param c - cost
     * @param t - train time
     */
    public GoldMiner(Double p, Integer l, Integer c, Integer t) {
        super(p, l, c, t);
    }

    /**
     * This method allows the gold miner to collect gold from gold mine. It also set the current gold of a gold mine
     * to zero after collecting it.
     * @param gM
     * @return - gold
     */
    public Double goldMining (GoldMine gM){
        if(super.getProduction() <= gM.getCurGold()){
            Double gold = gM.getCurGold()-super.getProduction();
            gM.setCurGold(gold);
            return gold;
        }else{
            Double gold = gM.getCurGold();
            gM.setCurGold(0.);
            return gold;
        }
    }
}
