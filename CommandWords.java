import java.util.HashMap;
/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class CommandWords
{
    private HashMap<String,Option> validCommands;       

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        validCommands = new HashMap<String,Option>();
        validCommands.put("go",Option.GO);
        validCommands.put("quit",Option.QUIT);
        validCommands.put("help",Option.HELP);
        validCommands.put("look",Option.LOOK);
        validCommands.put("eat",Option.EAT);
        validCommands.put("back",Option.BACK);
        validCommands.put("take",Option.TAKE);
        validCommands.put("drop",Option.DROP);
        validCommands.put("items",Option.ITEMS);
        validCommands.put("unknown",Option.UNKNOWN);
    }

    /**
     * Check whether a given String is a valid command word. 
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand(String aString)
    {
        return validCommands.containsKey(aString);
    }

    /**
     * Print all valid commands to System.out
     */
    public void showAll() {
        String cadenaComandos = "Your command words are:\n";
        for(String keyEnLista : validCommands.keySet()) {
            cadenaComandos += keyEnLista + " ";
        }
        System.out.println(cadenaComandos);
    }

    /**
     * Return the object Option associated with a word.
     * @param commandWord The word to look up (as a string).
     * @return the object Option correspondng to the paramater commandWord, or the object Option.UNKNOWN
     *         if it is not a valid command word
     */
    public Option getCommandWord(String commandWord) {
        Option opcion = Option.UNKNOWN;
        if(isCommand(commandWord)) {
            opcion = validCommands.get(commandWord);
        }
        return opcion;
    }
    
    
}
