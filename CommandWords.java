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
        Option[] options = Option.values();
        for(int i = 0; i<options.length; i++) {
            validCommands.put(options[i].getOptionString(),options[i]);
        }
        
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
        Option option = validCommands.get(commandWord);
        if(option == null) {
            option = Option.UNKNOWN;
        }
        return option;
    }
    
    
}
