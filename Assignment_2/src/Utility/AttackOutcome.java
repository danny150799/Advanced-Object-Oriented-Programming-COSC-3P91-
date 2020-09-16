package Utility;

/**
 * This class represents the outcomes when 2 players ( or player vs bot) to determine which one is the winner.
 */
public class AttackOutcome {
    static int numberOfAttack = 0; // number of attack to calculate the damage on the army/defense.

    /**
     * This method returns true when the attacker wins and false otherwise. How this work is that we subtract the
     * damage from the health for both player, which player health reduced to 0 oro below first is the loser.
     * @param playerHP
     * @param playerD
     * @param botHP
     * @param botD
     * @return
     */
    public static boolean determineWinning(Double playerHP, Double playerD, Double botHP, Double botD){
        Double pHP = playerHP;
        Double bHP = botHP;
        while(pHP>0 || bHP >0){
            pHP -= botD;
            bHP -= playerD;
            numberOfAttack +=1;
        }
        return bHP <= 0;
    }

    /**
     * This method returns the number of attack in the fight.
     * @return
     */
    public static int getNumberOfAttack(){return numberOfAttack;}
}
