package Game;

import GameElements.*;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * This class represents the defense system of the village. It has archer tower and cannons.
 */
public class TotalDefense {
    private Double totalDamage = 0.;
    private Double totalHitPoints = 0.;
    private ArrayList<Cannon> cannons = new ArrayList<>();
    private ArrayList<ArcherTower> archerTowers = new ArrayList<>();

    /**
     * This method returns the total damage of the defense system. Used to determine who win or lose when someone attack
     * the player's village. Doesn't used right now since there is no multiplayer.
     * @return
     */
    public Double getTotalDamage(){
        if(cannons.size()!=0){
            cannons.forEach(n-> this.totalDamage+= n.dealDamage());
        }
        if(archerTowers.size()!=0){
            archerTowers.forEach( n-> this.totalDamage+=n.dealDamage());
        }
        return this.totalDamage;
    }

    /**
     * These methods set the defense units as input.
     * @param archerTowers
     */
    public void setArcherTowers(ArrayList<ArcherTower> archerTowers){
        this.archerTowers = archerTowers;
    }
    public void setCannons(ArrayList<Cannon> cannons){
        this.cannons = cannons;
    }

    /**
     * This method returns the total hit points of the defense system. Used to determine who win or lose when someone attack
     * the player's village. Doesn't used right now since there is no multiplayer.
     * @return
     */
    public Double getTotalHitPoints(){
        if(cannons.size()!=0){
            cannons.forEach(n-> this.totalHitPoints+= n.getHitPoints());
        }
        if(archerTowers.size()!=0){
            archerTowers.forEach( n-> this.totalHitPoints+=n.getHitPoints());
        }
        return this.totalHitPoints;
    }

    /**
     * This method creates a new cannon and adds it to the arraylist.
     */
    public void buildCannon(Cannon cannon){
        this.cannons.add(cannon);
    }

    /**
     * This method returns the arraylist of the cannon.
     */
    public ArrayList<Cannon> getCannon(){
        return cannons;
    }

    /**
     * This method returns the size of the cannon's arraylist.
     * @return
     */
    public int getCannonNumber(){
        return cannons.size();
    }

    /**
     * This method creates a new cannon and adds it to the arraylist.
     */
    public void buildArcherTower(ArcherTower archerT){
        this.archerTowers.add(archerT);
    }

    /**
     * This methods returns the arraylist of archer tower.
     * @return
     */
    public ArrayList<ArcherTower> getArcherTowers(){
        return archerTowers;
    }

    /**
     * This method return the size of the archer tower's arraylist.
     * @return
     */
    public int getArcherTowerNumber(){
        return archerTowers.size();
    }

    /**
     * This method recovers the health of all the archer tower and cannon by set the current health equals the max health.
     */
    public void recoveryDefense(PrintWriter out){
        if(archerTowers.size()!=0){
            archerTowers.forEach(n->n.setHitPoints(n.getMaxHitPoints()));
        }
        if(cannons.size()!=0){
            cannons.forEach(n->n.setHitPoints(n.getMaxHitPoints()));
        }
        try {
            out.println("Defense system is in recovery state.");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        out.println("Defense system finishes recovering.");
    }

    /**
     * This method upgrades all of the archer tower. Method reference. Try catch.
     */
    public void upgradeArcherTowers(PrintWriter out){
        if(archerTowers.size()!=0) {
            int time = 0;
            for(int i=0; i<archerTowers.size(); i++){
                time += 100;
            }
            try {
                out.println("Upgrading archer towers.");
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            archerTowers.forEach(x->x.upgrade(out));
            out.println("Finish upgrading.");
        }else{
            out.println("You need to have an Archer Tower first.");
        }
    }

    /**
     * This method upgrades all of the cannon. Method reference. Try catch.
     */
    public void upgradeCannons(PrintWriter out){
        if(cannons.size()!=0) {
            int time = 0;
            for(int i=0; i<cannons.size(); i++){
                time += 100;
            }
            try {
                out.println("Upgrading cannons.");
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cannons.forEach(x-> x.upgrade(out));
            out.println("Finish upgrading.");
        }else{
            out.println("You need to have a Cannon first.");
        }
    }

    /**
     * This method returns the cost of upgrading all of the archer tower.
     * @return
     */
    public int costUpgradeArcherTower(){
        int result = 0;
        for(int i=0; i<archerTowers.size(); i++){
            result += archerTowers.get(i).getCost();
        }
        return result;
    }

    /**
     * This method returns the cost of upgrading all of the cannon.
     * @return
     */
    public int costUpgradeCannon(){
        int result = 0;
        for(int i=0; i<cannons.size(); i++){
            result += cannons.get(i).getCost();
        }
        return result;
    }
}
