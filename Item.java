public class Item
{
    private String descripcionItem;
    private int pesoItem;
    private boolean puedeSerCogido;
    /**
     * Constructor for objects of class Item
     */
    public Item(String descripcionItem, int pesoItem, boolean puedeSerCogido)
    {
        this.descripcionItem = descripcionItem;
        this.pesoItem = pesoItem;
        this.puedeSerCogido = puedeSerCogido;
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
     * Devuelve si el objeto puede recogerse o no
     */
    public boolean puedeSerCogido() {
        return puedeSerCogido;
    }
    
    /**
     * Devuelve una cadena con la descripcion y el peso del objeto
     */
    public String informacionItem() {
        return descripcionItem + " Peso: " + pesoItem + " Kg";
    }
}
