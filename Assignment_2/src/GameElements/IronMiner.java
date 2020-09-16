package GameElements;

public class IronMiner extends Worker {
    /**
     * This class represents the iron miner that can be used to collect iron from iron mine. Each iron miner is responsible
     * for one iron mine.
     * @param p - productivity
     * @param l - level
     * @param c - cost
     * @param t - train time
     */
    public IronMiner(Double p, Integer l, Integer c, Integer t) {
        super(p, l, c, t);
    }

    /**
     * This method allows the iron miner to collect iron from an iron mine according to the iron miner's productivity
     * @param iM
     * @return - iron
     */
    public Double ironMining (IronMine iM){
        if(super.getProduction() <= iM.getCurIron()){
            Double iron = iM.getCurIron()-super.getProduction();
            iM.setCurIron(iron);
            return iron;
        }else{
            Double iron = iM.getCurIron();
            iM.setCurIron(0.);
            return iron;
        }
    }
}
