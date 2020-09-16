package Game;

import GameElements.*;

/**
 * This class returns a new building unit depends on the input.
 */
public class BuildingFactory {
    public Building getBuilding(int choice){
        if(choice==1){
            return new ArcherTower(25.0,1,15,4., 15000);
        }else if(choice==2){
            return new Cannon(20.0,1,10,3., 10000);
        }else if(choice==3){
            return new VillageHall(30., 1, 10, 20000);
        }else if(choice==4){
            return new GoldMine(30.,1,10, 10000);
        }else if(choice==5){
            return new IronMine(30.,1,10, 10000);
        }else if(choice==6){
            return new LumberMill(30.,1,10, 10000);
        }
        return null;
    }
}
