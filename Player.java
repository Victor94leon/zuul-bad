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
}
