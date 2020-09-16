package Game;

import GameElements.*;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * This class represents a bot that the player can fight against. The bot class in Assignment 3 is different than in
 * Assignment 2 to accommodate the vital information needed to convert a Bot into a ChallengeEntity.
 */

public class Bot {
    private Double resourcesB;
    private Double resourceBHealth;
    private TotalArmy armyB;
    private TotalDefense defenseB;


    public Bot(){
        this.resourcesB = 0.;
        this.resourceBHealth = 0.;
        this.armyB = new TotalArmy();
        this.defenseB = new TotalDefense();
    }

    /**
     * These methods return the variables in this class which are ArmyB, DefenseB, ResourceB and ResourcesBHealth as the
     * corresponding object.
     * @return
     */
    public TotalArmy getArmyB(){
        return this.armyB;
    }
    public TotalDefense getDefenseB(){
        return this.defenseB;
    }
    public Double getResourcesB(){
        return this.resourcesB;
    }
    public Double getResourceBHealth(){
        return this.resourceBHealth;
    }

    /**
     * This method generates the resource value of a Bot by taking the average Gold, Iron and Lumber.
     * @param player
     */
    public void generateResource(Player player){
        this.resourcesB = Math.floor(((player.resources.getTotalGold()+
                player.resources.getTotalIron()+player.resources.getTotalLumber()/3)*Math.random()));
        this.resourceBHealth =  Math.floor((Math.random()*1.1)+0.5)*player.resources.getTotalHitPoint();
    }

    /**
     * This method clones the Player's army and put the clone into the Bot's army.
     * @param player
     */
    private void cloneArmy(Player player){
        if(player.army.getSoldiers().size()!=0){
            this.armyB.setSoldiers(new ArrayList<Soldier>(player.army.getSoldiers()));
        }
        if(player.army.getArchers().size()!=0){
            this.armyB.setArchers(new ArrayList<Archer>(player.army.getArchers()));
        }
        if(player.army.getKnights().size()!=0){
            this.armyB.setKnights(new ArrayList<Knight>(player.army.getKnights()));
        }
        if(player.army.getCatapults().size()!=0){
            this.armyB.setCatapults(new ArrayList<Catapult>(player.army.getCatapults()));
        }
    }



    /**
     * This method duplicates the Player's defense units and put the clone units into the Bot's object.
     * @param player
     */
    private void cloneDefence(Player player){
        if(player.defense.getArcherTowers().size()!=0){
            this.defenseB.setArcherTowers(new ArrayList<ArcherTower>(player.defense.getArcherTowers()));
        }
        if(player.defense.getCannon().size()!=0){
            this.defenseB.setCannons(new ArrayList<Cannon>(player.defense.getCannon()));
        }
    }

    /**
     * This method generates an army for the Bot object. The army of a Bot object is the Player's army but some units have
     * been removed to ensure that the Player will likely to win.
     * @param player
     */
    public void generateArmy(Player player){
        double random = Math.random();
        cloneArmy(player);
        for(int i=0; i<Math.floor(random*armyB.getSoldiers().size()); i++){
            this.armyB.getSoldiers().remove((int) (Math.random() * armyB.getSoldiers().size()));
        }
        for(int i=0; i<Math.floor(random*armyB.getArchers().size()); i++){
            this.armyB.getArchers().remove((int) (Math.random() * armyB.getArchers().size()));
        }
        for(int i=0; i<Math.floor(random*armyB.getKnights().size()); i++){
            this.armyB.getKnights().remove((int) (Math.random() * armyB.getKnights().size()));
        }
        for(int i=0; i<Math.floor(random*armyB.getCatapults().size()); i++){
            this.armyB.getCatapults().remove((int) (Math.random() * armyB.getCatapults().size()));
        }
        armyB.getTotalDamage();
        armyB.getTotalHitPoints();
    }

    /**
     * This methods generates defense units according the Player. The units are essentially a clones from the Player's
     * defense units, but some units are removed since we want the player to win rather than lose to a bot.
     * @param player
     */
    public void generateDefense(Player player){
        double random = Math.random();
        cloneDefence(player);
        for(int i=0; i<Math.floor(random*defenseB.getArcherTowers().size()); i++){
            this.defenseB.getArcherTowers().remove((int) (Math.random() * defenseB.getArcherTowers().size()));
        }
        for(int i=0; i<Math.floor(random*armyB.getArchers().size()); i++){
            this.defenseB.getCannon().remove((int) (Math.random() * defenseB.getCannon().size()));
        }
        defenseB.getTotalDamage();
        defenseB.getTotalHitPoints();
    }

    /**
     * This method generates 5 Bot objects and put them into an arraylist in which this method returns.
     * @param player
     * @return
     */
    public static ArrayList<Bot> generateBots(Player player){
        ArrayList<Bot> bots = new ArrayList<>();
        Bot bot;
        for(int a=0; a<5; a++){
            bot = new Bot();
            bot.generateArmy(player);
            bot.generateDefense(player);
            bot.generateResource(player);
            bots.add(bot);
        }

        return bots;
    }

    /**
     * This method prints all of the information of all the bots in an input arraylist of bots.
     * @param bots
     */
    public static void printBots(ArrayList<Bot> bots, PrintWriter out){
        if(bots.size()!=0) {
            for (int i = 0; i < bots.size(); i++) {
                out.println("Opponent " + (i + 1) + "\n" +
                        "Total Army Hit Point: " + bots.get(i).armyB.getTotalHitPoints() + "\n" +
                        "Total Army Damage: " + bots.get(i).armyB.getTotalDamage() + "\n" +
                        "Total Defense Hit Point: " + bots.get(i).defenseB.getTotalHitPoints() + "\n" +
                        "Total Defense Damage: " + bots.get(i).defenseB.getTotalDamage() + "\n" +
                        "Total Resource: " + bots.get(i).resourcesB + "\n" + "\n");
            }
        }else{
            out.println("You have no enemy, please find new opponents.");
        }
    }

    /**
     * This method filters the bots by health in the arraylist using stream filter method and returns the selected bots.
     * @param bots
     * @param d
     * @return
     */
    public static ArrayList<Bot> filterBots(ArrayList<Bot> bots, Double d){
        ArrayList<Bot> result = (ArrayList<Bot>) bots.stream().filter(bot -> bot.defenseB.getTotalHitPoints()<d).collect(Collectors.toList());
        return result;
    }
}
