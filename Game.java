import java.util.Stack;
import java.util.ArrayList;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private static final String NORTH = "north";
    private static final String EAST = "east";
    private static final String SOUTHEAST = "southeast";
    private static final String SOUTH = "south";
    private static final String WEST = "west";
    private static final String NORTHWEST = "northwest";
    private Stack<Room> roomsAnteriores;
    private Player player;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        roomsAnteriores = new Stack<Room>();
        player = new Player();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room terraza,salon,habitacion,pasillo,salida,cocina,despensa;

        // create the rooms
        terraza = new Room("una terraza");
        salon = new Room("el salón de la casa");
        habitacion = new Room("el dormitorio");
        pasillo = new Room("el pasillo central de la casa");
        salida = new Room("la puerta de salida");
        cocina = new Room("la cocina");
        despensa = new Room("una pequeña despensa llena de comida");
        // initialise room exits
        //Salidas de terraza
        terraza.setExit("east",salon);
        terraza.setExit("southeast",pasillo);
        //Salidas de salon
        salon.setExit("east",habitacion);
        salon.setExit("south",pasillo);
        salon.setExit("west",terraza);
        //Salidas de habitacion
        habitacion.setExit("west",salon);
        //Salidas de pasillo
        pasillo.setExit("north",salon);
        pasillo.setExit("east",salida);
        pasillo.setExit("southeast",despensa);
        pasillo.setExit("south",cocina);
        pasillo.setExit("northwest",terraza);
        //Salidas de salida
        salida.setExit("west",pasillo);
        //Salidas de cocina
        cocina.setExit("north",pasillo);
        cocina.setExit("east",despensa);
        //SAlidas de despensa
        despensa.setExit("west",cocina);
        despensa.setExit("northwest",pasillo);
        // Items en las habitaciones
        terraza.addItem("Maceta",7);
        terraza.addItem("Silla",5);
        habitacion.addItem("Zapatillas", 2);
        habitacion.addItem("Cazadora",4);
        habitacion.addItem("Ordenador", 6);
        despensa.addItem("Llave", 1);
        currentRoom = habitacion;  //start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();
        System.out.println();
    }

    private void printLocationInfo() {
        System.out.println(currentRoom.getLongDescription());
        currentRoom.printItems();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("look")) {
            System.out.println(currentRoom.getLongDescription());
            currentRoom.printItems();
        }
        else if (commandWord.equals("eat")) {
            System.out.println("You have eaten now and you are not hungry any more");
        }
        else if(commandWord.equals("back")) {
            backRoom();
        }
        else if (commandWord.equals("take")) {
            takeItem(command);
        }
        else if (commandWord.equals("drop")) {
            dropItem(command);
        }
        else if (commandWord.equals("items")) {
            showItemsPlayer();
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("Estás encerrado en tu casa");
        System.out.println("Tienes que salir antes de llegar tarde al trabajo");
        System.out.println();
        parser.printCommandWords();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
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
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /**
     * Vuelve a la habitación anterior
     */
    private void backRoom() {
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
    private void takeItem(Command command) {
        if(!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }
        String descripcionItem = command.getSecondWord();

        ArrayList<Item> itemsRoom = currentRoom.getListaItems();
        boolean itemEncontrado = false;
        int index = 0;

        while (!itemEncontrado && index<itemsRoom.size()) {
            if(itemsRoom.get(index).getDescripcionItem().equals(descripcionItem)) {
                // Crea una copia del item de la localización que coincida con la descripcion del comando
                Item item = currentRoom.buscarItem(descripcionItem);
                // Añade el item al usuario
                player.addItemPlayer(item);
                // Borra el item de la localización
                currentRoom.removeItem(item);
                // Muestra por pantalla la información de la localización
                printLocationInfo();
                System.out.println();
            }
            index++;
        }
        if(itemEncontrado == false) {
            System.out.println("No se encontro ese objeto");
        }
    }

    /**
     * Suelta un objeto en la localización actual
     */
    private void dropItem(Command command) {
        if(!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return;
        }
        String descripcionItem = command.getSecondWord();

        ArrayList<Item> itemsPlayer = player.devolverItems();
        boolean itemEncontrado = false;
        int index = 0;
        while (!itemEncontrado && index<itemsPlayer.size()) {
            if(itemsPlayer.get(index).getDescripcionItem().equals(descripcionItem)) {
                itemEncontrado = true;

                // Crea una copia del item del jugador que coincida con la descripcion del comando
                Item item = player.buscarItemPlayer(descripcionItem);
                // Añade el item a la localización
                currentRoom.addItem(item.getDescripcionItem(),item.getPesoItem());
                // Borra el item del jugador
                player.reomveItemPlayer(item);
                // Muestra por pantalla la información de la localización
                printLocationInfo();
                System.out.println();
            }
            index++;
        }
        if(itemEncontrado == false) {
            System.out.println("No se encontro ese objeto");
        }
    }

    /**
     * Muestra por pantalla la lista de objetos que tiene el jugador
     */
    private void showItemsPlayer() {
        if(player.devolverItems().size() != 0) {
            ArrayList<Item> listaItems = player.devolverItems();
            for(Item itemEnLista : listaItems) {
                System.out.println(itemEnLista.informacionItem());
            }
        }
        else {
            System.out.print("El jugador no tiene objetos\n");
        }
    }
}