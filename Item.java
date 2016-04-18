public class Item
{
    private String descripcionItem;
    private int pesoItem;

    /**
     * Constructor for objects of class Item
     */
    public Item(String descripcionItem, int pesoItem)
    {
        this.descripcionItem = descripcionItem;
        this.pesoItem = pesoItem;
    }
    
    /**
     * Devuelve la descripcion del objeto
     */
    public String getDescripcionItem() {
        return descripcionItem;
    }
    
    /**
     * Devuelve el peso del item
     */
    public int getPesoItem() {
        return pesoItem;
    }
    
    /**
     * Devuelve una cadena con la descripcion y el peso del objeto
     */
    public String informacionItem() {
        return descripcionItem + " Peso: " + pesoItem + " Kg";
    }
}
