import java.util.Stack;
import java.util.ArrayList;
import java.util.Random;
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
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Player player;
    private Player otroPersonaje;
    private Room salida;
    private Item llave;
    private static final String NORTH = "north";
    private static final String EAST = "east";
    private static final String SOUTHEAST = "southeast";
    private static final String SOUTH = "south";
    private static final String WEST = "west";
    private static final String NORTHWEST = "northwest";
    private ArrayList<Item> listaItems;
    private ArrayList<Room> listaRooms;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        parser = new Parser();
        player = new Player();
        otroPersonaje = new Player();
        listaItems = new ArrayList<>();
        listaRooms = new ArrayList<>();
        createRooms();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room terraza,salon,habitacion,pasillo,salida,cocina,despensa;

        // create the rooms
        terraza = new Room("una terraza");
        listaRooms.add(terraza);
        salon = new Room("el sal�n de la casa");
        listaRooms.add(salon);
        habitacion = new Room("el dormitorio");
        listaRooms.add(habitacion);
        pasillo = new Room("el pasillo central de la casa");
        listaRooms.add(pasillo);
        salida = new Room("la puerta de salida");
        listaRooms.add(salida);
        cocina = new Room("la cocina");
        listaRooms.add(cocina);
        despensa = new Room("una peque�a despensa llena de comida");
        listaRooms.add(despensa);
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

        terraza.addItem("Silla",4,false);
        habitacion.addItem("Zapatillas", 2,true);
        habitacion.addItem("Cazadora",4,true);
        habitacion.addItem("Ordenador", 6, true);

        pasillo.addItem("Paraguas",2,true);
        salon.addItem("Tele",3,false);
        salon.addItem("Mando",1,true);

        addItemsCollection();
        player.setCurrentRoom(habitacion);
        copyItemsToPlayer(otroPersonaje);
        otroPersonaje.setCurrentRoom(pasillo);
        this.salida = salida;
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
        System.out.println("Type " + Option.HELP.getOptionString() + " if you need help.");
        System.out.println();
        player.printLocationInfo();
        System.out.println();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;
        Option commandWord = command.getCommandWord();
        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        switch(commandWord) {
            case HELP:
            printHelp();
            break;

            case GO:
            player.goRoom(command);
            printComprobarItems();

            
            
            
            break;

            case QUIT:
            wantToQuit = quit(command);
            break;

            case LOOK:
            player.printLocationInfo();
            break;

            case EAT:
            System.out.println("You have eaten now and you are not hungry any more");
            break;

            case BACK:
            player.backRoom();
            printComprobarItems();

            
            
            break;
            case TAKE:
            player.takeItem(command.getSecondWord());
            break;

            case DROP:
            player.dropItem(command.getSecondWord());
            break;

            case ITEMS:
            player.showItemsPlayer();
            break;

            case FINISH:
            if(player.getCurrentRoom() != salida) {
                System.out.println("No estas en la salida");
            }
            else {
                if(player.estaElItem("Llave")) {
                    System.out.println("Has conseguido salir de la casa \nJUEGO FINALIZADO");
                    wantToQuit = quit(command);
                }
                else {
                    System.out.println("No tienes la llave para salir");
                }
            }
            break;
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
        System.out.println("Est�s encerrado en tu casa");
        System.out.println("Tienes que salir antes de llegar tarde al trabajo");
        System.out.println();
        parser.printCommandWords();
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
     * Comprueba si dos objetos de la clase Player estan en la misma habitaci�n
     */
    private boolean mismaHabitacion(Player player1, Player player2) {
        boolean mismaHabitacion = false;
        if(player1.getCurrentRoom() == player2.getCurrentRoom()) {
            mismaHabitacion = true;
        }
        return mismaHabitacion;
    }

    /**
     * Comprueba si el jugador y el otro personaje estan en la misma habitacion y muestra por pantalla
     * el mensaje del otro personaje 
     */
    private void printComprobarItems() {
        if(mismaHabitacion(player,otroPersonaje)) { 
            int ItemsEncontrados = 0;
            for(Item itemPersonaje : otroPersonaje.getPlayerItems()) {
                boolean itemEncontrado = false;
                int i = 0;
                while(!itemEncontrado && i<player.getPlayerItems().size()) {
                    if(player.getPlayerItems().get(i) == itemPersonaje) {
                        ItemsEncontrados++;
                    }
                    i++;
                }
            }
            if(ItemsEncontrados == otroPersonaje.getPlayerItems().size()) {
                otroPersonaje.getCurrentRoom().addItem("Llave", 1,true);
                System.out.println("-----Muy bien, ahora puede coger la Llave en esta sala-----");
            }
            else {
                ArrayList<Item> items = otroPersonaje.getPlayerItems();
                System.out.println("-----Hay una persona en la sala-----");
                System.out.println("Hola, para poder darte la Llave tienes que \nentrar en la sala con siguientes objetos: \n" 
                    + items.get(0).getDescripcionItem() + " y " + items.get(1).getDescripcionItem());
            }  
        }
    }

    /**
     * A�ade todos los items del juego a una coleccion
     */
    private void addItemsCollection() {        
        for(Room roomEnLista : listaRooms) {
            ArrayList<Item> itemsSala = roomEnLista.getListaItems();
            for(Item itemEnLista : itemsSala) {
                listaItems.add(itemEnLista);
            }
        }
    }

    /**
     * Copia dos objetos de la lista al inventario de un personaje
     */
    private void copyItemsToPlayer(Player jugador) {
        Random rdm = new Random();
        ArrayList<Item> puedenCogerse = new ArrayList<>();
        for(Item itemEnLista : listaItems) {
            if (itemEnLista.puedeSerCogido() && itemEnLista.getPesoItem() <= 3) {
                puedenCogerse.add(itemEnLista);
            }
        }
        for(int i = 0; i<2; i++) {
            int numLista = rdm.nextInt(puedenCogerse.size());
            otroPersonaje.addItem(puedenCogerse.get(numLista));
        }
    }
}