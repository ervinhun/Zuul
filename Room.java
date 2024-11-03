import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> exits;
    private List<Item> item = new ArrayList<>();



    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<String, Room>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor The room in the given direction.
     */
    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The description of the room.
     */

    public Room getExit (String direction)
    {
        return exits.get(direction);
    }

    public String getExitString() {
        String exitString = "Exit: ";
        for (String e: exits.keySet()) {
            exitString += e + " ";
        }
        return exitString;
    }
    /**
     * Return a long description of this room, of the form:
     * You are in the kitchen.
     * Exits: north west
     * @return A description of the room, including exits.
     */
    public String getLongDescription()
    {
        String returnString = "You are " + description + ".\n" + getExitString();
        if (getItem() != null) {
            returnString += "\nAvailable item: ";
            for (Item i : getItem())
                returnString += "\n\t" + i.getDescription() + " (" + i.getWeight() + " kg)";
        }
        return returnString;
    }

    public String getDescription() {
        return description;
    }

    public void setItem(Item... item) {
        this.item.addAll(Arrays.asList(item));
    }
    public List<Item> getItem() {
        if (item != null)
            return item;
        else
            return null;
    }

    public void removeItem(Item item) {
        if (item != null)
            this.item.remove(item);
    }

}

