package Game;

import GameElements.*;

/**
 * This class returns a new of Army units depend on the input.
 */
public class ArmyFactory {
    public Army getArmy(int choice){
        if(choice==1){
            return new Soldier(3.,10.,1, 2, 1000);
        }else if(choice==2){
            return new Archer(4., 7.,1, 2, 1000);
        }else if(choice==3){
            return new Knight(5., 15., 1, 5, 3000);
        }else if(choice==4){
            return new Catapult(7.,20., 1, 10, 3000);
        }
        return null;
    }
}
