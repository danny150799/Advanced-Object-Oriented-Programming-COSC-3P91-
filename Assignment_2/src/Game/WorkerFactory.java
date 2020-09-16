package Game;

import GameElements.*;

/**
 * This class produces a worker depend on the input.
 */
public class WorkerFactory {
    public Worker getWorker(int choice){
        if(choice==1){
            return new GoldMiner(10.,1,5, 5000);
        }else if(choice==2){
            return new IronMiner(10.,1,5, 5000);
        }else if(choice==3){
            return new LumberMan(10.,1,5, 5000);
        }
        return null;
    }
}
