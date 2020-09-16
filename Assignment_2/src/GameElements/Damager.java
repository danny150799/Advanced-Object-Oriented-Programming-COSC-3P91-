package GameElements;

/**
 * This interface exists only to indicate which unit or building can deal damage.
 */
public interface Damager {
    /**
     * A method to retrieve the damage stat of a unit or building.
     * @return - damage
     */
    Double dealDamage();
}
