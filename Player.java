import java.util.ArrayList;
public class Player
{
    private int PESO_MAXIMO = 5;
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
}
