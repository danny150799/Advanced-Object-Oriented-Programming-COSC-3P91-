package Game;

import GameElements.Farm;
import GameElements.VillageHall;
import Utility.AttackOutcome;
import Utility.Map;


import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class represents a player. A player has certain variables that are created when a player is initialized.
 */
public class Player {
    public CollectResources resources;
    public TotalArmy army;
    public TotalDefense defense;
    VillageHall villageHall = null;
    Farm farm = null;
    Map map;

    /**
     * This method sets up the important variables so that player can use them.
     */
    public Player() {
        resources = new CollectResources();
        army = new TotalArmy();
        defense = new TotalDefense();
        farm = new Farm(30., 1, 10, 20000, 20);
        map = new Map(200, 200);
    }

    /**
     * This method returns a new player that is exactly the same with the player.
     * @return
     */
    public Player clone(){
        Player clone = new Player();
        if(this.army.getSoldiers()!=null){
            clone.army.setSoldiers(new ArrayList<>(this.army.getSoldiers()));
        }
        if(this.army.getArchers()!=null){
            clone.army.setArchers(new ArrayList<>(this.army.getArchers()));
        }
        if(this.army.getKnights()!=null){
            clone.army.setKnights(new ArrayList<>(this.army.getKnights()));
        }
        if(this.army.getCatapults()!=null){
            clone.army.setCatapults(new ArrayList<>(this.army.getCatapults()));
        }

        if(this.defense.getCannon()!=null){
            clone.defense.setCannons(new ArrayList<>(this.defense.getCannon()));
        }
        if(this.defense.getArcherTowers()!=null){
            clone.defense.setArcherTowers(new ArrayList<>(this.defense.getArcherTowers()));
        }
        clone.resources.setTotalGold(this.resources.getTotalGold());
        clone.resources.setTotalIron(this.resources.getTotalIron());
        clone.resources.setTotalLumber(this.resources.getTotalLumber());
        return clone;
    }
}
