package Utility;

import ChallengeDecision.ChallengeAttack;
import ChallengeDecision.ChallengeDefense;
import ChallengeDecision.ChallengeEntitySet;
import ChallengeDecision.ChallengeResource;
import Game.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * This class converts a Player into a ChallengeEntitySet.
 * @param <T>
 * @param <V>
 */
public class PlayerToChallengeAdapter<T extends Number, V extends Number> extends ChallengeEntitySet {
    List<ChallengeAttack<T,V>> entityAttackList;
    List<ChallengeDefense<T,V>> entityDefenseList;
    List<ChallengeResource<T,V>> entityResourceList;

    public PlayerToChallengeAdapter (){
        this.entityAttackList = new ArrayList<ChallengeAttack<T, V>>();
        this.entityDefenseList = new ArrayList<ChallengeDefense<T, V>>();
        this.entityResourceList = new ArrayList<ChallengeResource<T, V>>();
    }

    /**
     * This method converts the Player object into a ChallengeEntitySet and returns the ChallengeEntitySet. It creates
     * the ChallengeAttack, ChallengeDefense and ChallengeResource and add the units as well as the resource values correspondingly.
     * @param challenger
     * @return
     */
    public ChallengeEntitySet playerToChallenge(Player challenger){
        if(challenger.army.getSoldiers().size()!=0){
            for(int i=0; i<challenger.army.getSoldiers().size(); i++){
                this.entityAttackList.add(new ChallengeAttack(challenger.army.getSoldiers().get(i).getDamage(),
                        challenger.army.getSoldiers().get(i).getHitPoints()));
            }
        }
        if(challenger.army.getArchers().size()!=0){
            for(int i=0; i<challenger.army.getArchers().size(); i++){
                this.entityAttackList.add(new ChallengeAttack(challenger.army.getArchers().get(i).getDamage(),
                        challenger.army.getArchers().get(i).getHitPoints()));
            }
        }
        if(challenger.army.getKnights().size()!=0){
            for(int i=0; i<challenger.army.getKnights().size(); i++){
                this.entityAttackList.add(new ChallengeAttack(challenger.army.getKnights().get(i).getDamage(),
                        challenger.army.getKnights().get(i).getHitPoints()));
            }
        }
        if(challenger.army.getCatapults().size()!=0){
            for(int i=0; i<challenger.army.getCatapults().size(); i++){
                this.entityAttackList.add(new ChallengeAttack(challenger.army.getCatapults().get(i).getDamage(),
                        challenger.army.getCatapults().get(i).getHitPoints()));
            }
        }
        if(challenger.defense.getArcherTowers().size()!=0){
            for(int i=0; i<challenger.defense.getArcherTowers().size(); i++){
                this.entityDefenseList.add(new ChallengeDefense(challenger.defense.getArcherTowers().get(i).dealDamage(),
                        challenger.defense.getArcherTowers().get(i).getHitPoints()));
            }
        }
        if(challenger.defense.getCannon().size()!=0){
            for(int i=0; i<challenger.defense.getCannon().size(); i++){
                this.entityDefenseList.add(new ChallengeDefense(challenger.defense.getCannon().get(i).dealDamage(),
                        challenger.defense.getCannon().get(i).getHitPoints()));
            }
        }

        Double resource = Math.floor((challenger.resources.getTotalGold()+challenger.resources.getTotalIron()
                +challenger.resources.getTotalLumber())/3);
        ChallengeResource resources = new ChallengeResource(resource, challenger.resources.getTotalHitPoint());
        this.entityResourceList.add(new ChallengeResource(resource, challenger.resources.getTotalHitPoint()));
        return new ChallengeEntitySet(this.entityAttackList, this.entityDefenseList,this.entityResourceList);
    }
}
