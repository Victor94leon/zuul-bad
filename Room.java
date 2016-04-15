import java.util.HashMap;
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
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */
public class Room 
{
    private String description;
    private static final String NORTH = "north";
    private static final String EAST = "east";
    private static final String SOUTHEAST = "southeast";
    private static final String SOUTH = "south";
    private static final String WEST = "west";
    private static final String NORTHWEST = "northwest";
    private HashMap<String,Room> listaSalidas;
    private String descripcionItem;
    private int pesoItem;
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description, String descripcionItem, int pesoItem) 
    {
        this.description = description;
        listaSalidas = new HashMap<String,Room>();
        this.descripcionItem = descripcionItem;
        this.pesoItem = pesoItem;
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    public Room getExit(String direccion) {
        Room roomExit = listaSalidas.get(direccion);
        return roomExit;
    }

    /**
     * Return a description of the room's exits.
     * For example: "Exits: north east west"
     *
     * @ return A description of the available exits.
     */
    public String getExitString() {
        String descripcion = "Exits: ";
        for(String nombreSala : listaSalidas.keySet()) {
            descripcion += nombreSala + " ";
        }
        return descripcion;
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor The room in the given direction.
     */
    public void setExit(String direction, Room neighbor) {
        listaSalidas.put(direction,neighbor);
    }

    /**
     * Return a long description of this room, of the form:
     *     You are in the 'name of room'
     *     Exits: north west southwest
     * @return A description of the room, including exits.
     */
    public String getLongDescription() {
        return "Estás en " + description + "\n" + getExitString();
    }
    
    /**
     * Devuelve una cadena con la información de los objetos que hay en las salas
     */
    public String getItemDescription() {
        return "Objeto en la sala: " + descripcionItem + " " + pesoItem + " Kg";
    }
}
