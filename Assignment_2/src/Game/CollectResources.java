package Game;

import GameElements.*;
import Utility.Print;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * This class represents the resources of a player, including gold, iron and lumber. The player relies on these resources
 * to perform certain actions with their village.
 */

public class CollectResources {
    private Double totalGold;
    private Double totalIron;
    private Double totalLumber;
    private static int totalHabitant=0;
    private Double totalHitPoint = 0.;

    private ArrayList<GoldMine> goldMines;
    private ArrayList<IronMine> ironMines;
    private ArrayList<LumberMill> lumberMills;
    private ArrayList<GoldMiner> goldMiners;
    private ArrayList<IronMiner> ironMiners;
    private ArrayList<LumberMan> lumberMen;

    /**
     * Every time the class is created, variables and arraylists are created. Player will have 1 mine and lumber mill for
     * each resource at the beginning.
     */
    public CollectResources(){
        totalGold = 100.;
        totalIron = 100.;
        totalLumber = 100.;
        goldMines = new ArrayList<>();
        ironMines = new ArrayList<>();
        lumberMills = new ArrayList();
        goldMiners = new ArrayList<>();
        ironMiners = new ArrayList<>();
        lumberMen = new ArrayList<>();

        goldMines.add(new GoldMine(30.,1,10, 10000));
        ironMines.add(new IronMine(30.,1,10, 10000));
        lumberMills.add(new LumberMill(30.,1,10, 10000));
    }

    /**
     * This method adds loot to the player's resources.
     * @param gold
     * @param iron
     * @param lumber
     */
    public void takeLoot(Double gold, Double iron, Double lumber){
        totalGold += gold;
        totalIron += iron;
        totalLumber += lumber;
    }

    /**
     * These methods returns the GoldMines, IronMines and LumberMills arrayList object.
     * @return
     */
    public ArrayList<GoldMine> getGoldMines(){
        return this.goldMines;
    }
    public ArrayList<IronMine> getIronMines(){
        return this.ironMines;
    }
    public ArrayList<LumberMill> getLumberMills(){
        return this.lumberMills;
    }

    /**
     * This method generate resource according to the time period as the input. Lambda expression.
     * @param time
     */
    public void generateResources(Double time){
        if(goldMines.size()!=0){
            goldMines.forEach(n -> totalGold += n.goldProduction(time));
        }
        if(ironMines.size()!=0){
            ironMines.forEach(n -> totalIron += n.ironProduction(time));
        }
        if(lumberMills.size()!=0){
            lumberMills.forEach((n -> totalLumber += n.lumberProduction(time)));
        }
    }

    /**
     * This method returns the total current hit points of all the gold mines, iron mines, and lumber mills.
     * @return
     */

    public Double getTotalHitPoint(){
        if(goldMines.size()!=0){
            goldMines.forEach(n-> this.totalHitPoint+= n.getHitPoints());
        }
        if(ironMines.size()!=0){
            ironMines.forEach( n-> this.totalHitPoint+=n.getHitPoints());
        }
        if(lumberMills.size()!=0){
            lumberMills.forEach( n-> this.totalHitPoint+=n.getHitPoints());
        }
        return this.totalHitPoint;
    }
    /**
     * This method collects resources in the mines and lumbers by putting one corresponding worker to each mine/lumber.
     * Only one worker per mine. Ex: 2 workers and 1 mine will result in 1 worker working while the other doesn't.
     */
    public void collectResource(PrintWriter out){
        if(goldMiners.size()!=0 && goldMines.size()!=0){
            int min = Math.min(goldMiners.size(), goldMines.size());
            for(int i=0; i<min; i++){
                totalGold += goldMiners.get(i).goldMining(goldMines.get(i));
            }
        }else{
            out.println("You need to have both gold mine and gold miner.");
        }
        if(ironMiners.size()!=0 && ironMines.size()!=0){
            int min = Math.min(ironMiners.size(), ironMines.size());
            for(int i=0; i<min; i++){
                totalIron += ironMiners.get(i).ironMining(ironMines.get(i));
            }
        }else{
            out.println("You need to have both iron mine and iron miner.");
        }
        if(lumberMen.size()!=0 && lumberMen.size()!=0){
            int min = Math.min(lumberMen.size(), lumberMills.size());
            for(int i=0; i<min; i++){
                totalLumber += lumberMen.get(i).lumberMining(lumberMills.get(i));
            }
        }else{
            out.println("You need to have both lumber mill and lumber man.");
        }
    }

    /**
     * The following methods return the resources of a player.
     * @return
     */
    public Double getTotalGold(){return this.totalGold;}
    public Double getTotalIron(){return this.totalIron;}
    public Double getTotalLumber(){return this.totalLumber;}

    /**
     * This method recovers the health of gold, iron mines and lumbermill by setting current hitpoints to maximum hit points.
     */
    public void recoverResources(PrintWriter out){
        if(goldMines.size()!=0){
            goldMines.forEach(n->n.setHitPoints(n.getMaxHitPoints()));
        }
        if(ironMines.size()!=0){
            ironMines.forEach(n->n.setHitPoints(n.getMaxHitPoints()));
        }if(lumberMills.size()!=0){
            lumberMills.forEach(n->n.setHitPoints(n.getMaxHitPoints()));
        }
        try {
            out.println("Mines and mill are in recovery state.");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        out.println("Mines and mill finish recovering.");
    }

    /**
     * This method prints all of the resource of a player. Generic. Anonymous class.
     */
    public void printAll(PrintWriter out){
        Print print = new Print() {
            @Override
            public <T extends Number> void print(T g, T i, T l) {
                out.println("Your Gold, Iron and Lumber are " +
                        g +", " + i +", and " + l +".");
            }
        };
        print.print(totalGold, totalIron, totalLumber);
    }

    /**
     * This method creates a gold mine. The cost are iron and lumber.
     */
    public void createGoldMine(GoldMine goldM, PrintWriter out){
        goldMines.add(goldM);
        totalIron -= goldMines.get(goldMines.size()-1).getCost();
        totalLumber -= goldMines.get(goldMines.size()-1).getCost();
        out.println("Gold Mine is being built.");
        try {
            Thread.sleep(goldMines.get(goldMines.size()-1).getBuildTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        out.println("Finish building Gold Mine");
        printAll(out);
    }

    /**
     * This method creates an iron mine. The cost are iron and lumber.
     */
    public void createIronMine(IronMine ironM, PrintWriter out){
        ironMines.add(ironM);
        totalIron -= ironMines.get(ironMines.size()-1).getCost();
        totalLumber -= ironMines.get(ironMines.size()-1).getCost();
        out.println("Iron Mine is being built.");
        try {
            Thread.sleep(ironMines.get(ironMines.size()-1).getBuildTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        out.println("Finish building Iron Mine");
        printAll(out);
    }

    /**
     * This method creates a lumber mill. The cost are iron and lumber.
     */
    public void createLumberMill(LumberMill lumberM, PrintWriter out){
        lumberMills.add(lumberM);
        totalIron -= lumberMills.get(lumberMills.size()-1).getCost();
        totalLumber -= lumberMills.get(lumberMills.size()-1).getCost();
        out.println("Lumber Mill is being built.");
        try {
            Thread.sleep(lumberMills.get(lumberMills.size()-1).getBuildTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        out.println("Finish building Lumber Mill");
        printAll(out);
    }

    /**
     * This method employs a gold miner. The cost is gold.
     */
    public void createGoldMiner(GoldMiner goldMer, PrintWriter out){
        if(totalWorkers() + TotalArmy.totalArmy()< Farm.getMaxPopulation()){
            goldMiners.add(goldMer);
            totalGold -= goldMiners.get(goldMiners.size()-1).getCost();
            out.println("Gold Miner is in an interview.");
            try {
                Thread.sleep(goldMiners.get(goldMiners.size()-1).getTrainTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println("Finish interviewing Gold Miner");
            totalHabitant +=1;
        }else{
            out.println("You cannot have more people, need to upgrade Farm.");
        }
    }

    /**
     * This method employs an iron miner. The cost is gold.
     */
    public void createIronMiner(IronMiner ironMer, PrintWriter out){
        if(totalWorkers() +TotalArmy.totalArmy()< Farm.getMaxPopulation()){
            ironMiners.add(ironMer);
            totalGold -= goldMines.get(goldMines.size()-1).getCost();
            out.println("Iron Miner is in an interview.");
            try {
                Thread.sleep(ironMiners.get(ironMiners.size()-1).getTrainTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println("Finish interviewing Iron Miner");
            totalHabitant +=1;
        }else{
            out.println("You cannot have more people, need to upgrade Farm.");
        }
    }

    /**
     * This method employs a lumber man. The cost is gold.
     */
    public void createLumberMen(LumberMan lumberMan, PrintWriter out){
        if(totalWorkers()+TotalArmy.totalArmy() < Farm.getMaxPopulation()){
            lumberMen.add(lumberMan);
            totalGold -= goldMines.get(goldMines.size()-1).getCost();
            out.println("Lumber man is in an interview.");
            try {
                Thread.sleep(lumberMen.get(lumberMen.size()-1).getTrainTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.println("Finish interviewing Lumber man");
            totalHabitant +=1;
        }else{
            out.println("You cannot have more people, need to upgrade Farm.");
        }
    }

    /**
     * The following methods set the data of the resources according to the input.
     * @param g
     */
    public void setTotalGold(Double g){
        totalGold = g;
    }
    public void setTotalIron(Double i){
        totalIron = i;
    }
    public void setTotalLumber(Double l){
        totalLumber = l;
    }

    /**
     * This method returns the total of habitants of the resources class.
     * @return
     */
    public static int totalWorkers(){
        return totalHabitant;
    }

    /**
     * This method upgrades all of the gold mines. The cost are iron and lumber. If there isn't enough resources,
     * player cannot upgrade the gold mines.
     */
    public void upgradeGoldMine(PrintWriter out){
        if(costUpgradeGoldMine()<=totalIron && costUpgradeGoldMine()<=totalLumber) {
            totalIron -= costUpgradeGoldMine();
            totalLumber -= costUpgradeGoldMine();
            int time = 0;
            for(int i=0; i<goldMines.size(); i++){
                time += 100;
            }
            try {
                out.println("Upgrading gold mines.");
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            goldMines.forEach(x -> x.upgrade(out));
            out.println("Finish  upgrading.");
        }else{
            out.println("You need more iron and lumber.");
            printAll(out);
        }
    }

    /**
     * This method returns the total cost of upgrading all of the gold mines.
     * @return
     */
    public int costUpgradeGoldMine(){
        int result = 0;
        for (GoldMine goldMine : goldMines) {
            result += goldMine.getCost();
        }
        return result;
    }

    /**
     * This method returns the total cost of upgrading all of the iron mines.
     * @return
     */
    public int costUpgradeIronMine(){
        int result = 0;
        for (IronMine ironMine : ironMines) {
            result += ironMine.getCost();
        }
        return result;
    }

    /**
     * This method returns the total cost of upgrading all of the lumber mills.
     * @return
     */
    public int costUpgradeLumberMill(){
        int result = 0;
        for (LumberMill lumberMill : lumberMills) {
            result += lumberMill.getCost();
        }
        return result;
    }

    /**
     * This method upgrades all of the iron mines. The cost are iron and lumber. If there isn't enough resources,
     * player cannot upgrade the iron mines.
     */
    public void upgradeIronMine(PrintWriter out){
        if(costUpgradeIronMine()<=totalIron && costUpgradeIronMine()<=totalLumber) {
            totalIron -= costUpgradeIronMine();
            totalLumber -= costUpgradeIronMine();
            int time = 0;
            for(int i=0; i<ironMines.size(); i++){
                time += 100;
            }
            try {
                out.println("Upgrading iron mines.");
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ironMines.forEach(x -> x.upgrade(out));
            out.println("Finish  upgrading.");
        }else{
            out.println("You need more iron and lumber.");
            printAll(out);
        }
    }

    /**
     * This method upgrades all of the lumber mill. The cost are iron and lumber. If there isn't enough resources,
     * player cannot upgrade the lumber mills.
     */
    public void upgradeLumberMill(PrintWriter out){
        if(costUpgradeLumberMill()<=totalIron && costUpgradeLumberMill()<= totalLumber) {
            totalIron -= costUpgradeLumberMill();
            totalLumber -= costUpgradeLumberMill();
            int time = 0;
            for(int i=0; i<lumberMills.size(); i++){
                time += 100;
            }
            try {
                out.println("Upgrading lumber mills.");
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lumberMills.forEach(x -> x.upgrade(out));
            out.println("Finish  upgrading.");
        }else{
            out.println("You need more iron and lumber.");
            printAll(out);
        }
    }

    /**
     * This method upgrades the all of the gold miners if they exist. The upgrade takes gold.
     */
    public void upgradeGoldMiner(PrintWriter out){
        if(goldMiners.size()!=0) {
            totalGold -= costUpgradeGoldMiner();
            int time = 0;
            for(int i=0; i<goldMiners.size(); i++){
                time += 100;
            }
            try {
                out.println("Training gold miners.");
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            goldMiners.forEach(x -> x.upgrade(out));
            out.println("Finish training.");
        }else{
            out.println("You need to employ a gold miner first.");
            printAll(out);
        }
    }

    /**
     * This method upgrades the all of the iron miners if they exist. The upgrade takes gold.
     */
    public void upgradeIronMiner(PrintWriter out){
        if(ironMiners.size()!=0) {
            totalGold -= costUpgradeIronMiner();
            int time = 0;
            for(int i=0; i<ironMiners.size(); i++){
                time += 100;
            }
            try {
                out.println("Training iron miners.");
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ironMiners.forEach(x -> x.upgrade(out));
            out.println("Finish training.");
        }else{
            out.println("You need to employ an iron miner first.");
            printAll(out);
        }
    }

    /**
     * This method upgrades the all of the lumbermen if they exist. The upgrade takes gold.
     */
    public void upgradeLumberMen(PrintWriter out){
        if(lumberMen.size()!=0) {
            totalGold -= costUpgradeLumberMen();
            int time = 0;
            for(int i=0; i<lumberMen.size(); i++){
                time += 100;
            }
            try {
                out.println("Training lumbermen.");
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lumberMen.forEach(x->x.upgrade(out));
            out.println("Finish  training.");
        }else{
            out.println("You need to employ a lumberman first.");
            printAll(out);
        }
    }

    /**
     * This method returns the cost for upgrading all of the gold miners.
     * @return
     */
    public int costUpgradeGoldMiner(){
        int result = 0;
        for (GoldMiner goldMiner : goldMiners) {
            result += goldMiner.getCost();
        }
        return result;
    }

    /**
     * This method returns the cost for upgrading all of the iron miners.
     * @return
     */
    public int costUpgradeIronMiner(){
        int result = 0;
        for (IronMiner ironMiner : ironMiners) {
            result += ironMiner.getCost();
        }
        return result;
    }

    /**
     * This method returns the cost for upgrading all of the lumbermen.
     * @return
     */
    public int costUpgradeLumberMen(){
        int result = 0;
        for (LumberMan lumberMAN : lumberMen) {
            result += lumberMAN.getCost();
        }
        return result;
    }
}
