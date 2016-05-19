import java.util.Stack;
import java.util.ArrayList;
public class Player
{
    static final int PESO_MAXIMO = 7;
    private ArrayList<Item> playerItems;
    private Room currentRoom;
    private Stack<Room> roomsAnteriores;
    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
        playerItems = new ArrayList<Item>();
        roomsAnteriores = new Stack<Room>();
        currentRoom = null;
    }

    /**
     * Devuelve el peso llevado por el jugador
     */
    public int pesoAlmacenado() {
        int pesoAlmacenado = 0;
        for(Item itemEnLista : playerItems) {
            pesoAlmacenado =+ itemEnLista.getPesoItem();
        }
        return pesoAlmacenado;
    }
    
    /**
     * Devuelve la habitación donde se encuentra el jugador
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Asigna un valor a currentRoom
     */
    public void setCurrentRoom(Room room) {
        if(currentRoom==null) {
            currentRoom = room;
        }
    }

    /**
     * Imprime por pantalla la información un la localización actual
     */
    public void printLocationInfo() {
        System.out.println(currentRoom.getLongDescription());
        currentRoom.printItems();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    public void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }
        String direction = command.getSecondWord();
        // Try to leave current room.
        Room nextRoom = null;
        nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            roomsAnteriores.push(currentRoom);
            currentRoom = nextRoom;
            printLocationInfo();
            System.out.println();
        }
    }

    /**
     * Vuelve a la habitación anterior
     */
    public void backRoom() {
        if(!roomsAnteriores.empty()) {
            currentRoom = roomsAnteriores.pop();
            printLocationInfo();
            System.out.println();
        }
        else {
            System.out.println("No hay localizaciones anteriores");
        }
    }

    /**
     * Recoge un item de la localización y se lo añade al jugador
     */
    public void takeItem(String descripcion) {
        Item item = currentRoom.buscarItem(descripcion);
        if(item == null) {
            System.out.println("take what?");
        }
        else if(!item.puedeSerCogido()) {
            System.out.println("El objeto no se puede coger");
        }
        else {
            if(item.getPesoItem()+pesoAlmacenado() >= PESO_MAXIMO) {
                System.out.println("El jugador no puede llevar tanto peso");
            }
            else {
                playerItems.add(item);
                currentRoom.removeItem(item);
                System.out.println("Has cogido " + item.getDescripcionItem());
            }
        }
    }

    /**
     * Suelta un objeto en la localización actual
     */
    public void dropItem(String descripcion) {
        boolean itemEncontrado = false;
        for(int index = 0; index<playerItems.size() && !itemEncontrado; index++) {
            if(playerItems.get(index).getDescripcionItem().equals(descripcion)) {
                currentRoom.addItem(playerItems.get(index).getDescripcionItem(),playerItems.get(index).getPesoItem(),true);
                playerItems.remove(playerItems.get(index));
                itemEncontrado = true;
                System.out.println("Has soltado un objeto");
            }
        }
        if(!itemEncontrado) {
            System.out.println("drop what?");
        }
    }

    /**
     * Muestra por pantalla la lista de objetos que tiene el jugador
     */
    public void showItemsPlayer() {
        if(playerItems.size()>0) {
            System.out.println("Lista de objetos del jugaodr:");
            for(Item itemEnLista : playerItems) {
                System.out.println(itemEnLista.informacionItem());
            }
        }
        else {
            System.out.print("El jugador no tiene objetos\n");
        }
    }
    
    /**
     * Busca un objeto entre los items del jugador devolviendo true si lo tiene
     * o false en caso contrario
     */
    public boolean estaElItem(String description) {
        boolean itemEncontrado = false;
        if(playerItems.size()!=0) {
            for(int i = 0; i<playerItems.size() && !itemEncontrado; i++) {
                if(playerItems.get(i).getDescripcionItem().equals(description)) {
                    itemEncontrado = true;
                }
            }
        }
        return itemEncontrado;
    }
    
    /**
     * Añade un item al inventario
     */
    public void addItem(Item item) {
        playerItems.add(item);
    }
}
