package Game;

import ChallengeDecision.Arbitrer;
import ChallengeDecision.ChallengeResult;
import GameElements.*;
import Utility.BotToChallengeAdapter;
import Utility.PlayerToChallengeAdapter;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * This class is a thread that is responsible for managing the game for a single player. This class reads and writes out
 * instructions to player to guide them through the game. This class also follow the MVC model.
 */
public class PlayerControl extends Thread{
    private Player model;
    private PlayerView view;
    private Socket clientSocket;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private ArrayList<String> database = new ArrayList<>();

    public PlayerControl (Player model, PlayerView view, Socket socket){
        this.model = model;
        this.view = view;
        this.clientSocket = socket;
        System.out.println("Connected.");
        database.add("123");
        database.add("admin");
    }

    /**
     * This method gives options to the player. It also asks them to put their choice in and test if the input data
     * is a valid data (is it an int or not, if not they will be asked again). During this phase, player can be attacked,
     * but since we are no at multiplayer phase, the defense system will not be used that much. However, we can attack
     * bots that mimic the stats of a player and get loot.
     */
    public void run(){
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        out.println("Please enter your id:");
        String id = "";
        try {
            id = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(id!= null && !checkID(id)){
            out.println("Your id does not exist in database.");
        }else{
            out.println("Welcome back, " +id +".");
        }

        out.println(view.printNonFightingCommand());
        int choice = -1;
        do {
            try {
                choice = Integer.parseInt(in.readLine());
            } catch (InputMismatchException e) {
                out.println("Please enter a valid option.");
                choice =-1;
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(0<choice && choice<=10 ){
                break;
            }
        }while(choice==-1);

        /**
         * Local class time which is used to calculate the time passed to that we can generate resources such as gold more
         * accurately with real time.
         */
        class Time{
            /**
             * This method calculates the time has passed in second byt subtracting the current time with the input time.
             * @param timeStart
             * @return - amount of time has passed
             */
            public double timePassed(double timeStart){
                return (System.currentTimeMillis() - timeStart)/1000;
            }
        }
        Time time = new Time();
        do{
            double timeStart = System.currentTimeMillis();
            if(choice==6) {
                model.resources.generateResources(time.timePassed(timeStart));
                model.resources.collectResource(out);
                model.resources.printAll(out);
            }else if(choice==2) {
                askCreateBuilding(in);
            }else if (choice==3) {
                try {
                    employArmy(in);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (choice ==4) {
                try {
                    upgradeBuilding(in);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(choice==5) {
                try {
                    upgradeArmy(in);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (choice ==7) {
                try {
                    employWorker(in);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (choice ==8){
                try {
                    upgradeWorker(in);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(choice==1){
                fightingPhase(in);
            }
            else if(choice==9){
                recovery();
            }
            out.println(view.printNonFightingCommand());
            do {
                try {
                    choice = Integer.parseInt(in.readLine());
                } catch (InputMismatchException e) {
                   out.println("Please enter a valid option.");
                    choice =-1;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(0<choice && choice<=10 && choice!=8 ){
                    break;
                }
            }while(choice==-1);
        }while(choice!= 10);
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method recovers the health of all buildings, army and defence systems. The time player has to wait is
     * arbitrary and can be changed in the future if necessary.
     */
    private void recovery(){
        model.resources.recoverResources(out);
        model.army.recoverArmy(out);
        model.defense.recoveryDefense(out);
        model.farm.setHitPoints(model.farm.getMaxHitPoints());
        model.villageHall.setHitPoints(model.villageHall.getMaxHitPoints());
        try {
            out.println("Farm and village hall are in recovery state.");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        out.println("Farm and village hall finish recovering.");
    }

    /**
     * This method checks whether the player's id is in the database or not.
     * @param id
     * @return
     */
    private boolean checkID(String id){
        for(int i=0; i<database.size(); i++){
            if(id.equals(database.get(i))){
                return true;
            }
        }
        return false;
    }

    /**
     * This method gives the player the option to fight other players. Since we are not at the multiplayer phase,
     * the player can look at bots, attack them or ask for new bots. They can also filter the bots by health.
     * @param scan
     */
    private void fightingPhase(BufferedReader scan){
        out.println(view.printFightCommand());
        int choice = -1;
        do {
            try {
                choice = Integer.parseInt(in.readLine());
            } catch (InputMismatchException e) {
                out.println("Please enter a valid option.");
                choice =-1;
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(0<choice && choice<=10 ){
                break;
            }
        }while(choice==-1);
        ArrayList<Bot> bots = Bot.generateBots(model);
        do{
            if(choice==1) {
                run();
            }else if(choice==2) {
                Bot.printBots(bots, out);
            }else if (choice==3) {
                attackOpponent(scan,bots);
            }else if(choice == 4){
                bots = Bot.generateBots(model);
                Bot.printBots(bots, out);
            }else if(choice == 5){
                filterOpponent(scan, bots);
            }else if(choice == 6){
                testVill(scan, bots);
            }
            out.println(view.printFightCommand());
            try {
                choice = Integer.parseInt(in.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }while(choice!= 10);
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method filters the opponents by health. User will give the maximum health, then opponents with lower health
     * will be chosen.
     * @param scan
     * @param bots
     */
    private void filterOpponent(BufferedReader scan, ArrayList<Bot> bots){
        out.println("Filter enemies by health, please enter the maximum hit points the opponent can have:");
        double choice = -1;
        do {
            try {
                choice = Integer.parseInt(scan.readLine());
            } catch (InputMismatchException e) {
                out.println("Please enter a valid option.");
                choice =-1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }while(choice==-1);
        bots = Bot.filterBots(bots,choice);
        Bot.printBots(bots, out);
    }

    /**
     * This method allows player to attack the opponent by give input of which opponent. If player win the battle,
     * their army will be hurt and they will receive full loot. If player lose, they lose their army as well and only
     * receive half of the loot.
     * @param scan
     * @param bots
     */
    private void attackOpponent(BufferedReader scan, ArrayList<Bot> bots){
        if(model.army.getSoldiers().size()!=0 || model.army.getArchers().size()!=0 || model.army.getKnights().size()!=0 ||
                model.army.getCatapults().size()!=0) {
            out.println("Which opponents will you fight? (1 to 5)");
            int choice = -1;
            do {
                do {
                    try {
                        choice = Integer.parseInt(scan.readLine());
                    } catch (InputMismatchException e) {
                        out.println("Please enter a valid option.");
                        choice = -1;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (0 < choice && choice <= 5) {
                        break;
                    }
                }while(choice!=-1);
                Arbitrer arbitrer = new Arbitrer();
                PlayerToChallengeAdapter player = new PlayerToChallengeAdapter();
                BotToChallengeAdapter newBot = new BotToChallengeAdapter();

                ChallengeResult result = arbitrer.challengeDecide(player.playerToChallenge(model), newBot.botToChallenge(bots.get(choice)));
                if (result.getChallengeWon()) {
                    out.println("You win!");
                    out.println("Your loot are " + result.getLoot().get(0).getProperty() + " gold, " +
                            result.getLoot().get(0).getProperty() + " iron, and " +
                            result.getLoot().get(0).getProperty() + " lumber.");
                    model.resources.takeLoot(result.getLoot().get(0).getProperty(),
                            result.getLoot().get(0).getProperty(),result.getLoot().get(0).getProperty());
                    break;
                } else {
                    out.println("Your attack failed. You lost all of your army");
                    model.army.loseArmy();
                    model.resources.takeLoot(result.getLoot().get(0).getProperty(),
                            result.getLoot().get(0).getProperty(),
                            result.getLoot().get(0).getProperty());
                    out.println("Your loot are " +result.getLoot().get(0).getProperty() + " gold, " +
                            result.getLoot().get(0).getProperty() + " iron, and " +
                            result.getLoot().get(0).getProperty() + " lumber.");
                    break;
                }
            } while (choice == -1);
        }else{
            out.println("You need an army to fight, you don't have any army unit at the moment.");
        }
    }

    /**
     * This method creates a clone of the player and let that clone attacks a selected bot and return the result.
     * @param scan
     * @param bots
     */
    private void testVill(BufferedReader scan, ArrayList<Bot> bots){
        Player playerTest = model.clone();
        if(playerTest.army.getSoldiers().size()!=0 || playerTest.army.getArchers().size()!=0 || playerTest.army.getKnights().size()!=0 ||
                model.army.getCatapults().size()!=0) {
            out.println("Which opponents will you do the test fight? (1 to 5)");
            int choice = -1;
            do {
                do {
                    try {
                        choice = Integer.parseInt(scan.readLine());
                    } catch (InputMismatchException e) {
                        out.println("Please enter a valid option.");
                        choice = -1;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (0 < choice && choice <= 5) {
                        break;
                    }
                }while(choice!=-1);
                Arbitrer arbitrer = new Arbitrer();
                PlayerToChallengeAdapter player = new PlayerToChallengeAdapter();
                BotToChallengeAdapter newBot = new BotToChallengeAdapter();

                ChallengeResult result = arbitrer.challengeDecide(player.playerToChallenge(model), newBot.botToChallenge(bots.get(choice)));
                if (result.getChallengeWon()) {
                    out.println("You win!");
                    break;
                } else {
                    out.println("Your attack failed. You lost all of your army");
                    break;
                }
            } while (choice == -1);
        }else{
            out.println("You need an army to test fight, you don't have any army unit at the moment.");
        }
    }

    /**
     * This method give the player the options to upgrade their workers. They can upgrade gold miners, iron miners or
     * lumbermen depends of their input.
     * @param scan
     */
    public void upgradeWorker(BufferedReader scan) throws IOException {
        int input = -1;
        do{
            out.println(view.printUpgradeWorker());
            do {
                try {
                    input = Integer.parseInt(scan.readLine());
                } catch (InputMismatchException e) {
                    out.println("Please enter a valid option.");
                    input =-1;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(0<input && input<=10){
                    break;
                }
            }while(input==-1);

            if (input==1) {
                out.println("Upgrade gold miner will cost: " + model.resources.costUpgradeGoldMiner());
                out.println("Will you choose to upgrade or not (Enter y or n): ");
                try {
                    if (scan.readLine().equals("y")) {
                        model.resources.upgradeGoldMiner(out);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }else if (input ==2) {
                out.println("Upgrade iron miner will cost: " + model.resources.costUpgradeIronMiner());
                out.println("Will you choose to upgrade or not (Enter y or n): ");
                try {
                    if (scan.readLine().equals("y")) {
                        model.resources.upgradeIronMiner(out);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }else if (input ==3){
                out.println("Upgrade lumbermen will cost: " + model.resources.costUpgradeLumberMen());
                out.println("Will you choose to upgrade or not (Enter y or n): ");
                try {
                    if(scan.readLine().equals("y")){
                        model.resources.upgradeLumberMen(out);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }else if(input ==10){
                break;
            }

        }while(Integer.parseInt(scan.readLine())!=10);
        model.resources.printAll(out);
    }

    /**
     * This method allows player to buy/employee a gold miner, iron miner or lumbermen. Each employment is paid by gold.
     * @param scan
     */
    public void employWorker(BufferedReader scan) throws IOException {
        int input = -1;
        WorkerFactory factory = new WorkerFactory();
        do{
            out.println(view.printEmployWorker());
            do {
                try {
                    input = Integer.parseInt(scan.readLine());

                } catch (InputMismatchException e) {
                    out.println("Please enter a valid option.");
                    input =-1;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(0<input && input<=10){
                    break;
                }
            }while(input==-1);
            if(input ==1) {
                out.println("Employ gold miners will cost: " + 5 + "gold");
                out.println("Will you choose to employ or not (Enter y or n): ");
                try {
                    if (scan.readLine().equals("y")) {
                        model.resources.createGoldMiner((GoldMiner) factory.getWorker(input),out);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }else if(input ==2) {
                out.println("Employ iron miners will cost: " + 5 + "gold");
                out.println("Will you choose to employ or not (Enter y or n): ");
                try {
                    if (scan.readLine().equals("y")) {
                        model.resources.createIronMiner((IronMiner) factory.getWorker(input),out);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }else if (input ==3){
                out.println("Employ lumberman will cost: " + 5 + "gold");
                out.println("Will you choose to employ or not (Enter y or n): ");
                try {
                    if(scan.readLine().equals("y")){
                        model.resources.createLumberMen((LumberMan) factory.getWorker(input),out);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }else if(input ==10){
                break;
            }
        }while(Integer.parseInt(scan.readLine())!=10);
        model.resources.printAll(out);
    }

    /**
     * This method allows player to upgrade their army units. Each upgrade takes a few second and gold.
     * @param scan
     */
    public void upgradeArmy(BufferedReader scan) throws IOException {
        int input = -1;
        do{
            out.print(view.printUpgradeArmy());
            do {
                try {
                    input = Integer.parseInt(scan.readLine());
                } catch (InputMismatchException e) {
                    out.println("Please enter a valid option.");
                    input =-1;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(0<input && input<=10){
                    break;
                }
            }while(input==-1);

            if(input == 1) {
                out.println("Upgrade soldiers will cost: " + model.army.costUpgradeSoldier());
                out.println("Will you choose to upgrade or not (Enter y or n): ");
                try {
                    if (scan.readLine().equals("y")) {
                        model.resources.setTotalGold(model.resources.getTotalGold() - model.army.costUpgradeSoldier());
                        model.army.upgradeSoldiers(out);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }else if(input ==2) {
                out.println("Upgrade archers will cost: " + model.army.costUpgradeArcher());
                out.println("Will you choose to upgrade or not (Enter y or n): ");
                try {
                    if (scan.readLine().equals("y")) {
                        model.resources.setTotalGold(model.resources.getTotalGold() - model.army.costUpgradeArcher());
                        model.army.upgradeArchers(out);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }else if(input ==3) {
                out.println("Upgrade knights will cost: " + model.army.costUpgradeKnight());
                out.println("Will you choose to upgrade or not (Enter y or n): ");
                try {
                    if (scan.readLine().equals("y")) {
                        model.resources.setTotalGold(model.resources.getTotalGold() - model.army.costUpgradeKnight());
                        model.army.upgradeKnights(out);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }else if(input ==4){
                out.println("Upgrade catapults will cost: " + model.army.costUpgradeCatapult());
                out.println("Will you choose to upgrade or not (Enter y or n): ");
                try {
                    if(scan.readLine().equals("y")){
                        model.resources.setTotalGold(model.resources.getTotalGold()-model.army.costUpgradeCatapult());
                        model.army.upgradeCatapult(out);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }else if(input ==10){
                break;
            }

        }while(Integer.parseInt(scan.readLine())!=10);
        model.resources.printAll(out);
    }

    /**
     * This method allows player to upgrade building including archer tower and cannon. Each upgrade costs iron and lumber.
     * @param scan
     */
    public void upgradeBuilding(BufferedReader scan) throws IOException {
        int input = -1;
        do{
            out.println(view.printUpgradeBuilding());
            do {
                try {
                    input = Integer.parseInt(scan.readLine());
                } catch (InputMismatchException e) {
                    out.println("Please enter a valid option.");
                    input =-1;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(0<input && input<=10){
                    break;
                }
            }while(input==-1);
            if (input == 1) {
                out.println("Upgrade Archer Towers will cost: " + model.defense.costUpgradeArcherTower());
                out.println("Will you choose to upgrade or not (Enter y or n): ");
                try {
                    if (scan.readLine().equals("y")) {
                        model.resources.setTotalIron(model.resources.getTotalIron() - model.defense.costUpgradeArcherTower());
                        model.resources.setTotalLumber(model.resources.getTotalLumber() - model.defense.costUpgradeArcherTower());
                        model.defense.upgradeArcherTowers(out);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }else if(input ==2) {
                out.println("Upgrade Cannons will cost: " + model.defense.costUpgradeCannon());
                out.println("Will you choose to upgrade or not (Enter y or n): ");
                try {
                    if (scan.readLine().equals("y")) {
                        model.resources.setTotalIron(model.resources.getTotalIron() - model.defense.costUpgradeCannon());
                        model.resources.setTotalLumber(model.resources.getTotalLumber() - model.defense.costUpgradeCannon());
                        model.defense.upgradeCannons(out);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }else if (input==3) {
                if (model.villageHall != null) {
                    try {
                        Thread.sleep(model.villageHall.getBuildTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    model.resources.setTotalIron(model.resources.getTotalIron() - model.villageHall.getCost());
                    model.resources.setTotalLumber(model.resources.getTotalLumber() - model.villageHall.getCost());
                    model.villageHall.upgrade(out);
                } else {
                    out.println("You need to build a village hall first.");
                }
                break;
            }else if(input==4) {
                if (model.farm != null) {
                    try {
                        Thread.sleep(model.farm.getBuildTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    model.resources.setTotalIron(model.resources.getTotalIron() - model.farm.getCost());
                    model.resources.setTotalLumber(model.resources.getTotalLumber() - model.farm.getCost());
                    model.farm.upgrade(out);
                } else {
                    out.println("You need to build a farm first.");
                }
                break;
            }else if(input==5) {
                out.println("Upgrade gold mines will cost: " + model.resources.costUpgradeGoldMine());
                out.println("Will you choose to upgrade or not (Enter y or n): ");
                try {
                    if (scan.readLine().equals("y")) {
                        model.resources.upgradeGoldMine(out);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }else if (input==6) {
                out.println("Upgrade iron mines will cost: " + model.resources.costUpgradeIronMine());
                out.println("Will you choose to upgrade or not (Enter y or n): ");
                try {
                    if (scan.readLine().equals("y")) {
                        model.resources.upgradeIronMine(out);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }else if(input==7){
                out.println("Upgrade lumber mills will cost: " + model.resources.costUpgradeLumberMill());
                out.println("Will you choose to upgrade or not (Enter y or n): ");
                try {
                    if(scan.readLine().equals("y")){
                        model.resources.upgradeLumberMill(out);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }else if(input ==10){
                break;
            }
        }while(Integer.parseInt(scan.readLine())!=10);
        model.resources.printAll(out);
    }

    /**
     * This method allows player to employ new army unit such as soldier, archer, knight and catapult. Each employment takes
     * gold depend on which unit it is. Each unit have different stats which might increase or decrease their value
     * compared to other units.
     * @param scan
     */
    private void employArmy(BufferedReader scan) throws IOException {
        int input = -1;
        ArmyFactory factory = new ArmyFactory();
        do{
            out.println(view.printEmployArmy());
            do {
                try {
                    input = Integer.parseInt(scan.readLine());

                } catch (InputMismatchException e) {
                    out.println("Please enter a valid option.");
                    input =-1;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(0<input && input<=10){
                    break;
                }
            }while(input==-1);
            if(input==1) {
                out.println("Employ soldiers will cost: " + 2 + "gold");
                out.println("Will you choose to employ or not (Enter y or n): ");
                try {
                    if (scan.readLine().equals("y")) {
                        model.army.trainSoldier((Soldier) factory.getArmy(input), out);
                        model.resources.setTotalGold(model.resources.getTotalGold() - model.army.getSoldiers().get(model.army.soldiersNumber() - 1).getCost());
                        try {
                            out.println("Employing soldier.");
                            Thread.sleep(model.army.getSoldiers().get(model.army.soldiersNumber() - 1).getTrainTime());
                            out.println("Finish employing soldier.");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }else if(input ==2) {
                out.println("Employ archers will cost: " + 2 + "gold");
                out.println("Will you choose to employ or not (Enter y or n): ");
                try {
                    if (scan.readLine().equals("y")) {
                        model.army.trainArcher((Archer) factory.getArmy(input), out);
                        model.resources.setTotalGold(model.resources.getTotalGold() - model.army.getArchers().get(model.army.archersNumber() - 1).getCost());
                        try {
                            out.println("Employing archer.");
                            Thread.sleep(model.army.getArchers().get(model.army.archersNumber() - 1).getTrainTime());
                            out.println("Finish employing archer.");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }else if(input==3) {
                out.println("Employ knights will cost: " + 5 + "gold");
                out.println("Will you choose to employ or not (Enter y or n): ");
                try {
                    if (scan.readLine().equals("y")) {
                        model.army.trainKnight((Knight)factory.getArmy(input), out);
                        model.resources.setTotalGold(model.resources.getTotalGold() - model.army.getKnights().get(model.army.knightsNumber() - 1).getCost());
                        try {
                            out.println("Employing knight.");
                            Thread.sleep(model.army.getKnights().get(model.army.knightsNumber() - 1).getTrainTime());
                            out.println("Finish employing knight.");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }else if(input ==4){
                out.println("Build catapult will cost: " + 10 + "gold");
                out.println("Will you choose to employ or not (Enter y or n): ");
                try {
                    if(scan.readLine().equals("y")){
                        model.army.buildCatapult((Catapult) factory.getArmy(input), out);
                        model.resources.setTotalGold(model.resources.getTotalGold()-model.army.getCatapults().get(model.army.catapultsNumber()-1).getCost());
                        try {
                            out.println("Employing catapult.");
                            Thread.sleep(model.army.getCatapults().get(model.army.catapultsNumber()-1).getTrainTime());
                            out.println("Finish employing catapult.");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }else if(input ==10){
                break;
            }
        }while(Integer.parseInt(scan.readLine())!=10);
        model.resources.printAll(out);
    }

    /**
     * This method allows player to build buildings include archer tower and cannon. Each building costs iron and lumber.
     * @param scan
     */
    private void askCreateBuilding(BufferedReader scan){
        int input = -1;
        BuildingFactory factory = new BuildingFactory();
        do{
            out.println(view.printAskBuildingCommand());
            do {
                try {
                    input = Integer.parseInt(scan.readLine());
                } catch (InputMismatchException e) {
                    out.println("Please enter a valid option.");
                    input =-1;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(0<input && input<=10){
                    break;
                }
            }while(input==-1);

            if(input==1) {
                if(model.map.checkSpace(5)) {
                    model.defense.buildArcherTower((ArcherTower)factory.getBuilding(input));
                    model.resources.setTotalIron(model.resources.getTotalIron() -
                            model.defense.getArcherTowers().get(model.defense.getArcherTowerNumber() - 1).getCost());
                    model.resources.setTotalLumber(model.resources.getTotalLumber() -
                            model.defense.getArcherTowers().get(model.defense.getArcherTowerNumber() - 1).getCost());
                    out.println("Archer Tower is being built.");
                    model.map.setX(5);
                    model.map.setY(5);
                    try {
                        Thread.sleep(model.defense.getArcherTowers().get(model.defense.getArcherTowerNumber() - 1).getBuildTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    out.println("Finish building Archer Tower");
                }else{
                    out.println("You no longer have any space to build.");
                }
                break;
            }else if(input ==2) {
                if(model.map.checkSpace(5)) {
                    model.defense.buildCannon((Cannon)factory.getBuilding(input));
                    model.resources.setTotalIron(model.resources.getTotalIron() -
                            model.defense.getCannon().get(model.defense.getCannonNumber() - 1).getCost());
                    model.resources.setTotalLumber(model.resources.getTotalLumber() -
                            model.defense.getCannon().get(model.defense.getCannonNumber() - 1).getCost());
                    out.println("Cannon is being built.");
                    try {
                        Thread.sleep(model.defense.getCannon().get(model.defense.getCannonNumber() - 1).getBuildTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    out.println("Finish building Cannon");
                    model.map.setX(5);
                    model.map.setY(5);
                }else{
                    out.println("You no longer have any space to build.");
                }
                break;
            }else if(input ==3) {
                if(model.map.checkSpace(5)) {
                    if (model.villageHall == null) {
                        model.villageHall = (VillageHall) factory.getBuilding(input);
                        model.resources.setTotalIron(model.resources.getTotalIron() - model.villageHall.getCost());
                        model.resources.setTotalLumber(model.resources.getTotalLumber() - model.villageHall.getCost());
                        out.println("Village Hall is being built.");
                        try {
                            Thread.sleep(model.villageHall.getBuildTime());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        out.println("Finish building Village Hall");
                        model.map.setX(5);
                        model.map.setY(5);
                    } else {
                        out.println("You can only have 1 village hall, this is where all the resources are," +
                                "so protect it well");
                    }
                }else{
                    System.out.println("You no longer have any space to build.");
                }
                break;
            }else if(input == 4) {
                if(model.map.checkSpace(5)) {
                    model.resources.createGoldMine((GoldMine) factory.getBuilding(4), out);
                    model.map.setX(5);
                    model.map.setY(5);
                }else{
                    out.println("You no longer have any space to build.");
                }
                break;
            }else if(input==5) {
                if(model.map.checkSpace(5)) {
                    model.resources.createIronMine((IronMine) factory.getBuilding(5), out);
                    model.map.setX(5);
                    model.map.setY(5);
                }else{
                    out.println("You no longer have any space to build.");
                }
                break;
            }  else if(input == 6){
                if(model.map.checkSpace(5)) {
                    model.resources.createLumberMill((LumberMill) factory.getBuilding(6), out);
                    model.map.setX(5);
                    model.map.setY(5);
                }else{
                    out.println("You no longer have any space to build.");
                }
                break;
            }else if (input==10){
                break;
            }

        }while(input!=10);
        model.resources.printAll(out);
    }
}

