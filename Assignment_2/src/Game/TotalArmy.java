package Game;

import GameElements.*;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * This class represents the army of a player. It allows player to employ and upgrade their desired unit.
 */
public class TotalArmy {
    private Double totalDamage;
    private Double totalHitPoints;
    private ArrayList<Soldier> soldiers;
    private ArrayList<Archer> archers;
    private ArrayList<Knight> knights;
    private ArrayList<Catapult> catapults;
    private static int totalHabitant = 0;

    /**
     * Every time a totalArmy class is created, essential variables and arraylists are created.
     */
    public TotalArmy(){
        totalDamage = 0.;
        totalHitPoints = 0.;
        soldiers = new ArrayList<>();
        archers = new ArrayList<>();
        knights = new ArrayList<>();
        catapults = new ArrayList<>();
    }

    /**
     * These methods set the army units as the input.
     * @param soldier
     */
    public void setSoldiers(ArrayList<Soldier> soldier){
        this.soldiers = soldier;
    }
    public void setArchers(ArrayList<Archer> archer){
        this.archers = archer;
    }
    public void setKnights(ArrayList<Knight> knight){
        this.knights = knight;
    }
    public void setCatapults(ArrayList<Catapult> catapult){
        this.catapults = catapult;
    }

    /**
     * This method calculated the total damage of an army and return it. Every unit arraylist will be went through and
     * if they exist with size >0, the unit's damage in the arraylist is added to the result.
     * @return - total damage of the entire army
     */
    public Double getTotalDamage(){
        if(soldiers.size()!=0){
            soldiers.forEach(n-> this.totalDamage+= n.getDamage());
        }
        if(archers.size()!=0){
            archers.forEach( n-> this.totalDamage+=n.getDamage());
        }
        if(knights.size()!=0){
            knights.forEach( n-> this.totalDamage+=n.getDamage());
        }
        if(catapults.size()!=0){
            catapults.forEach( n-> this.totalDamage+=n.getDamage());
        }
        return this.totalDamage;
    }

    /**
     * This method calculated the total health of an army and return it. Every unit arraylist will be went through and
     * if they exist with size >0, the unit's health in the arraylist is added to the result.
     * @return - total health of the entire army
     */
    public Double getTotalHitPoints(){
        if(soldiers.size()!=0){
            soldiers.forEach(n-> this.totalHitPoints+= n.getHitPoints());
        }
        if(archers.size()!=0){
            archers.forEach( n-> this.totalHitPoints+=n.getHitPoints());
        }
        if(knights.size()!=0){
            knights.forEach( n-> this.totalHitPoints+=n.getHitPoints());
        }
        if(catapults.size()!=0){
            catapults.forEach( n-> this.totalHitPoints+=n.getHitPoints());
        }
        return this.totalHitPoints;
    }

    /**
     * This method returns the total habitants/units in the army. The purpose is to test how many habitant/unit there
     * are to check if it's exceed the farm's capacity.
     * @return
     */
    public static int totalArmy(){
        return totalHabitant;
    }

    /**
     * This method upgrades the soldier unit. All of the soldier will be upgraded, not one by one.
     */
    public void trainSoldier(Soldier soldier, PrintWriter out){
        if(totalArmy()+CollectResources.totalWorkers()< Farm.getMaxPopulation()){
            soldiers.add(soldier);
            totalHabitant += 1;
        }else{
            out.println("You cannot have more people, need to upgrade farm.");
        }
    }

    /**
     * This method upgrades the archer unit. All of the archer will be upgraded, not one by one.
     */
    public void trainArcher(Archer archer, PrintWriter out){
        if(totalArmy()+CollectResources.totalWorkers()<Farm.getMaxPopulation()) {
            archers.add(archer);
            totalHabitant += 1;
        }else{
            out.println("You cannot have more people, need to upgrade farm.");
        }
    }

    /**
     * This method upgrades the knight unit. All of the knight will be upgraded, not one by one.
     */
    public void trainKnight(Knight knight, PrintWriter out) {
        if(totalArmy()+CollectResources.totalWorkers()<Farm.getMaxPopulation()) {
            knights.add(knight);
            totalHabitant += 1;
        }else{
            out.println("You cannot have more people, need to upgrade farm.");
        }
    }

    /**
     * This method upgrades the catapult unit. All of the catapult will be upgraded, not one by one.
     */
    public void buildCatapult(Catapult catapult, PrintWriter out){
        if(totalArmy()+CollectResources.totalWorkers()<Farm.getMaxPopulation()) {
            catapults.add(catapult);
            totalHabitant += 1;
        }else{
            out.println("You cannot have more people, need to upgrade farm.");
        }}

    /**
     * These methods return the unit's arraylist with all of the units in it.
     * @return
     */
    public ArrayList<Soldier> getSoldiers(){return soldiers;}
    public ArrayList<Archer> getArchers(){return archers;}
    public ArrayList<Knight> getKnights(){return knights;}
    public ArrayList<Catapult> getCatapults(){return catapults;}

    /**
     * These methods return the size unit's arraylist.
     * @return
     */
    public Integer soldiersNumber(){return soldiers.size();}
    public Integer archersNumber(){return archers.size();}
    public Integer knightsNumber(){return knights.size();}
    public Integer catapultsNumber(){return catapults.size();}

    /**
     * This method deletes all of units in their arraylist.
     */
    public void loseArmy(){
        soldiers.clear();
        archers.clear();
        knights.clear();
        catapults.clear();
    }

    /**
     * This method recovers the health of each unit in their arraylist by setting the current health to max health.
     */
    public void recoverArmy(PrintWriter out){
        if(soldiers.size()!=0){
            soldiers.forEach(n->n.setHitPoints(n.getMaxHitPoints()));
        }
        if(archers.size()!=0){
            archers.forEach(n->n.setHitPoints(n.getMaxHitPoints()));
        }
        if(knights.size()!=0){
            knights.forEach(n->n.setHitPoints(n.getMaxHitPoints()));
        }
        if(catapults.size()!=0){
            catapults.forEach(n->n.setHitPoints(n.getMaxHitPoints()));
        }
        try {
            out.println("Army is in recovery state.");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        out.println("Army finish recovering.");
    }

    /**
     * This method distributes the damage throughout the army. The damage is calculated by multiplying the totalDamage
     * with the number of attack has been dealt. Then divides it to 4 since we have 4 types of units, and divide that damage into
     * the size of elements in each unit's arraylist.
     * @param botDamage
     * @param numAttack
     */
    public void takeDamage(Double botDamage, int numAttack){
        double damage = Math.floor((botDamage*numAttack)/4);
        if(soldiers.size()!=0){
            double sDamage = Math.floor(damage/soldiers.size());
            int temp = 0;
            while(temp<soldiers.size()){
                soldiers.get(temp).setHitPoints(soldiers.get(temp).getHitPoints()-sDamage);
                if(soldiers.get(temp).getHitPoints()<=0){
                    soldiers.remove(temp);
                }
                temp +=1;
            }
        }
        if(archers.size()!=0){
            double aDamage = Math.floor(damage/archers.size());
            int temp = 0;
            while(temp<archers.size()){
                archers.get(temp).setHitPoints(archers.get(temp).getHitPoints()-aDamage);
                if(archers.get(temp).getHitPoints()<=0){
                    archers.remove(temp);
                }
                temp +=1;
            }
        }
        if(knights.size()!=0){
            double kDamage = Math.floor(damage/knights.size());
            int temp = 0;
            while(temp<knights.size()){
                knights.get(temp).setHitPoints(knights.get(temp).getHitPoints()-kDamage);
                if(knights.get(temp).getHitPoints()<=0){
                    knights.remove(temp);
                }
                temp +=1;
            }
        }
        if(catapults.size()!=0){
            double cDamage = Math.floor(damage/catapults.size());
            int temp = 0;
            while(temp<catapults.size()){
                catapults.get(temp).setHitPoints(catapults.get(temp).getHitPoints()-cDamage);
                if(catapults.get(temp).getHitPoints()<=0){
                    catapults.remove(temp);
                }
                temp +=1;
            }
        }
    }

    /**
     * This method upgrades soldier units if it exists. Method reference. Try catch.
     */
    public void upgradeSoldiers(PrintWriter out){
        if(soldiers.size()!=0) {
            int time = 0;
            for(int i=0; i<soldiers.size(); i++){
                time += 100;
            }
            try {
                out.println("Upgrading soldiers.");
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            soldiers.forEach(x -> x.upgrade(out));
            out.println("Finish  upgrading.");
        }else{
            out.println("You need to have a soldier first.");
        }
    }

    /**
     * This method upgrades archer units if it exists. Method reference. Try catch.
     */
    public void upgradeArchers(PrintWriter out){
        if(archers.size()!=0) {
            int time = 0;
            for(int i=0; i<archers.size(); i++){
                time += 100;
            }
            try {
                out.println("Upgrading archers.");
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            archers.forEach(x -> x.upgrade(out));
            out.println("Finish  upgrading.");
        }else{
            out.println("You need to have an archer first.");
        }
    }

    /**
     * This method upgrades knight units if it exists. Method reference. Try catch.
     */
    public void upgradeKnights(PrintWriter out){
        if(knights.size()!=0) {
            int time = 0;
            for(int i=0; i<knights.size(); i++){
                time += 100;
            }
            try {
                out.println("Upgrading knights.");
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            knights.forEach(x -> x.upgrade(out));
            out.println("Finish  upgrading.");
        }else{
            out.println("You need to have a knight first.");
        }
    }

    /**
     * This method upgrades catapult units if it exists. Method reference. Try catch.
     */
    public void upgradeCatapult(PrintWriter out){
        if(catapults.size()!=0) {
            int time = 0;
            for(int i=0; i<catapults.size(); i++){
                time += 100;
            }
            try {
                out.println("Upgrading catapults.");
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            catapults.forEach(x -> x.upgrade(out));
            out.println("Finish  upgrading.");
        }else{
            out.println("You need to have a catapult first.");
        }
    }

    /**
     * This method returns the amount to upgrade all soldiers in gold.
     */
    public int costUpgradeSoldier(){
        int result = 0;
        for(int i=0; i<soldiers.size(); i++){
            result += soldiers.get(i).getCost();
        }
        return result;
    }

    /**
     * This method returns the amount to upgrade all archers in gold.
     */
    public int costUpgradeArcher(){
        int result = 0;
        for(int i=0; i<archers.size(); i++){
            result += archers.get(i).getCost();
        }
        return result;
    }

    /**
     * This method returns the amount to upgrade all knights in gold.
     */
    public int costUpgradeKnight(){
        int result = 0;
        for(int i=0; i<knights.size(); i++){
            result += knights.get(i).getCost();
        }
        return result;
    }

    /**
     * This method returns the amount to upgrade all catapults in gold.
     */
    public int costUpgradeCatapult(){
        int result = 0;
        for(int i=0; i<catapults.size(); i++){
            result += catapults.get(i).getCost();
        }
        return result;
    }
}