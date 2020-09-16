package Utility;

import ChallengeDecision.ChallengeAttack;
import ChallengeDecision.ChallengeDefense;
import ChallengeDecision.ChallengeEntitySet;
import ChallengeDecision.ChallengeResource;
import Game.Bot;

import java.util.ArrayList;
import java.util.List;

/**
 *This class is an adaptor class that converts a Bot into a ChallengeEntitySet. The returned ChallengeEntitySet contains
 * the entityAttackList that contains all of the unit in the army, entityDefenseSet that contains the units in defense,
 * and the entityResourceSet that contains a resource value.
 * @param <T>
 * @param <V>
 */

public class BotToChallengeAdapter<T extends Number, V extends Number> extends ChallengeEntitySet {
    List<ChallengeAttack<T,V>> entityAttackList;
    List<ChallengeDefense<T,V>> entityDefenseList;
    List<ChallengeResource<T,V>> entityResourceList;

    public BotToChallengeAdapter (){
        this.entityAttackList = new ArrayList<ChallengeAttack<T, V>>();
        this.entityDefenseList = new ArrayList<ChallengeDefense<T, V>>();
        this.entityResourceList = new ArrayList<ChallengeResource<T, V>>();
    }

    /**
     * This function converts a Bot object into a ChallengeEntitySet by creating entityAttack, entityDefense and entityResoure.
     * Then, it adds all of the units in Bot's army, defense and resources into the entity correspondingly.
     * @param challenger
     * @return
     */
    public ChallengeEntitySet botToChallenge(Bot challenger){
        if(challenger.getArmyB().getSoldiers().size()!=0){
            for(int i=0; i<challenger.getArmyB().getSoldiers().size(); i++){
                this.entityAttackList.add(new ChallengeAttack(challenger.getArmyB().getSoldiers().get(i).getDamage(),
                        challenger.getArmyB().getSoldiers().get(i).getHitPoints()));
            }
        }
        if(challenger.getArmyB().getArchers().size()!=0){
            for(int i=0; i<challenger.getArmyB().getArchers().size(); i++){
                this.entityAttackList.add(new ChallengeAttack(challenger.getArmyB().getArchers().get(i).getDamage(),
                        challenger.getArmyB().getArchers().get(i).getHitPoints()));
            }
        }
        if(challenger.getArmyB().getKnights().size()!=0){
            for(int i=0; i<challenger.getArmyB().getKnights().size(); i++){
                this.entityAttackList.add(new ChallengeAttack(challenger.getArmyB().getKnights().get(i).getDamage(),
                        challenger.getArmyB().getKnights().get(i).getHitPoints()));
            }
        }
        if(challenger.getArmyB().getCatapults().size()!=0){
            for(int i=0; i<challenger.getArmyB().getCatapults().size(); i++){
                this.entityAttackList.add(new ChallengeAttack(challenger.getArmyB().getCatapults().get(i).getDamage(),
                        challenger.getArmyB().getCatapults().get(i).getHitPoints()));
            }
        }
        if(challenger.getDefenseB().getArcherTowers().size()!=0){
            for(int i=0; i<challenger.getDefenseB().getArcherTowers().size(); i++){
                this.entityDefenseList.add(new ChallengeDefense(challenger.getDefenseB().getArcherTowers().get(i).dealDamage(),
                        challenger.getDefenseB().getArcherTowers().get(i).getHitPoints()));
            }
        }
        if(challenger.getDefenseB().getCannon().size()!=0){
            for(int i=0; i<challenger.getDefenseB().getCannon().size(); i++){
                this.entityDefenseList.add(new ChallengeDefense(challenger.getDefenseB().getCannon().get(i).dealDamage(),
                        challenger.getDefenseB().getCannon().get(i).getHitPoints()));
            }
        }

        ChallengeResource resources = new ChallengeResource(challenger.getResourcesB(), challenger.getResourceBHealth());
        this.entityResourceList.add(resources);
        return new ChallengeEntitySet(this.entityAttackList, this.entityDefenseList,this.entityResourceList);
    }
}
