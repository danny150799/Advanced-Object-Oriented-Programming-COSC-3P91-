package Game;

/**
 * This class prints out all of the important commands so player can choose what to do.
 */
public class PlayerView {
    public String printNonFightingCommand(){
        return( "This is not a fighting phase. You can choose to build or upgrade buildings or army/defense. Press 1 to change to Fight Phase. " +
                "Press 2 to build buildings. " +
                "Press 3 to train army. " +
                "Press 4 to upgrade buildings. " +
                "Press 5 to upgrade army. " +
                "Press 6 to collect resources. " +
                "Press 7 to employ workers. " +
                "Press 8 to upgrade workers. " +
                "Press 9 to recover building and army. " +
                "Press 10 to exit.");
    }

    public String printAskBuildingCommand(){
        return ("Press 1 to build ArcherTower. " +
                "Press 2 to build Cannon. " +
                "Press 3 to build VillageHall. "+
                "Press 4 to build Gold Mine. " +
                "Press 5 to build Iron Mine. " +
                "Press 6 to build Lumber Mill. " +
                "Press 10 to go back. ");
    }

    public String printEmployArmy(){
        return ("Press 1 to employ Soldier. "+
                "Press 2 to employ Archer. "+
                "Press 3 to employ Knight. " +
                "Press 4 to build Catapult. " +
                "Press 10 to go back. ");
    }

    public String printUpgradeBuilding(){
        return ("Press 1 to upgrade ArcherTower. " +
                "Press 2 to upgrade Cannon. " +
                "Press 3 to upgrade VillageHall. " +
                "Press 4 to upgrade Farm. "+
                "Press 5 to upgrade Gold Mine. " +
                "Press 6 to upgrade Iron Mine. " +
                "Press 7 to upgrade Lumber Mill. " +
                "Press 10 to go back. ");
    }

    public String printUpgradeArmy(){
        return ("Press 1 to train Soldier. " +
                "Press 2 to train Archer. " +
                "Press 3 to train Knight. " +
                "Press 4 to upgrade Catapult. " +
                "Press 10 to go back.");
    }
    public String printEmployWorker(){
        return ("Press 1 to employ a gold miner. " +
                "Press 2 to employ an iron miner. " +
                "Press 3 to employ lumberman. " +
                "Press 10 to go back.");
    }

    public String printUpgradeWorker(){
        return ("Press 1 to upgrade a gold miner. " +
                "Press 2 to upgrade an iron miner. " +
                "Press 3 to upgrade lumberman. " +
                "Press 10 to go back.");
    }

    public String printFightCommand(){
        return  ("You are in the Fighting Phase. "+
                "Press 1 to change to non-Fight Phase. " +
                "Press 2 to see all opponents. "+
                "Press 3 to attack an opponent. " +
                "Press 4 to see new opponents. " +
                "Press 5 to filter opponent by health. " +
                "Press 6 to test fight with opponent. " +
                "Press 10 to exit.");
    }
}
