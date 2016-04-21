import java.util.HashMap;
import java.util.ArrayList;
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
    private HashMap<String,Room> listaSalidas;
    private ArrayList<Item> listaItems;
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        listaSalidas = new HashMap<String,Room>();
        listaItems = new ArrayList<Item>();
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * Devuelve una colecci�n con los objetos de la localizaci�n
     */
    public ArrayList<Item> getListaItems() {
        return listaItems;
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
        return "Est�s en " + description + "\n" + getExitString();
    }

    /**
     * A�ade un item a la localizaci�n
     */
    public void addItem(String descripcionItem, int pesoItem) {
        listaItems.add(new Item(descripcionItem, pesoItem));
    }

    /**
     * Muestra los objetos de la habitaci�n
     */
    public void printItems() {
        if(listaItems.size()>0) {
            System.out.println("Lista de objetos:");
            for (Item itemEnLista : listaItems) {
                System.out.println(itemEnLista.informacionItem());
            }
        }
        else {
            System.out.println("No hay objetos en esta sala");
        }
    }

    /**
     * Busca un item en la localizaci�n que coincida con una 
     * cadena introducida por par�metro
     */
    public Item buscarItem(String descripcion) {
        int index = 0;
        boolean encontrado = false;
        Item item = null;
        while(index<listaItems.size() && !encontrado) {
            if(listaItems.get(index).getDescripcionItem().equals(descripcion)) {
                item = listaItems.get(index);
            }
            index++;
        }
        return item;
    }

    /**
     * Borra una item de la localizaci�n que coincida con 
     * un item insertado por par�metro
     */
    public void removeItem(Item item) {
        int index = 0;
        boolean encontrado = false;
        while(index<listaItems.size() && !encontrado) {
            if(listaItems.get(index)==item) {
                listaItems.remove(index);
            }
            index++;
        }
    }
}
