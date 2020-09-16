package Utility;

/**
 * This class represents a map where the player can build things on. Each player have one map and they cannot upgrade it.
 * The map works by having x and y values, each building has their own x and y value. Every time, player build something,
 * the x and y of their map is decreased, until the map no longer have space. When map runs out of space, player cannot
 * build anything on it.
 *
 * Since we haven't learn and the requirement doesn't have graphic yet, x and y values will be used until a graphic is
 * implemented.
 */
public class Map {
    private Integer x;
    private Integer y;

    public Map(Integer x, Integer y){
        this.x = x;
        this.y = y;
    }

    /**
     * These methods are get and set methods that help to retrieve or set new values for the x and y values.
     * @return
     */
    public Integer getX(){return this.x;}
    public Integer getY(){return this.y;}
    public void setX(Integer space){this.x -= space;}
    public void setY(Integer space){this.y -= space;}

    /**
     * This method checks if the map has enough space to build the next building.
     * @param space
     * @return
     */
    public boolean checkSpace(Integer space){
        if(this.x-space>0 && this.y-space>0){
            return true;
        }else{
            return false;
        }
    }

}
