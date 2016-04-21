import java.util.ArrayList;
public class Player
{
    static final int PESO_MAXIMO = 5;
    private int pesoAlmacenado;
    private ArrayList<Item> playerItems;

    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
        playerItems = new ArrayList<Item>();
        pesoAlmacenado = 0;
    }

    /**
     * Devuelve el peso llevado por el jugador
     */
    public int getPesoAlmacenado() {
        return pesoAlmacenado;
    }

    /**
     * Devuelve los objetos que tiene el jugador
     */
    public ArrayList<Item> devolverItems() {
        return playerItems;
    }

    /**
     * Añade un objeto al jugador
     */
    public void addItemPlayer(Item item) {
        playerItems.add(item);
    }

    /**
     * Busca y devuelve un objeto entre los Items del jugador que coincida con una 
     * cadena introducida como parámetro
     */
    public Item buscarItemPlayer(String descripcion) {
        int index = 0;
        boolean encontrado = false;
        Item item = null;
        while(index<playerItems.size() && !encontrado) {
            if(playerItems.get(index).getDescripcionItem().equals(descripcion)) {
                item = playerItems.get(index);
            }
            index++;
        }
        return item;
    }

    /**
     * Borra un objeto de los que tiene el jugador que coincida con
     * un objeto de la clase Item introducido como parámetro
     */
    public void reomveItemPlayer(Item item) {
        int index = 0;
        boolean encontrado = false;
        while(index<playerItems.size() && !encontrado) {
            if(playerItems.get(index)==item) {
                playerItems.remove(index);
            }
            index++;
        }
    }
}
