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
    // a constant array that holds all valid command words
    /*private static final String[] validCommands = {
        "go", "quit", "help", "look", "back", "eat", "take", "drop", "save", "load", "inventory"
    };*/
    HashMap<String, CommandWord> validCommands;


    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        validCommands = new HashMap<String, CommandWord>();
        for(CommandWord command : CommandWord.values()) {
            if(command != CommandWord.UNKNOWN) {
                validCommands.put(command.toString(), command);
            }
        }
    }


    /**
     * Find the CommandWord associated with a command word.
     * @param commandWord The word to look up (as a string).
     * @return The CommandWord correspondng to commandWord, or UNKNOWN
     *         if it is not a valid command word.
     */
    public CommandWord getCommandWord(String commandWord)
    {
        CommandWord command = validCommands.get(commandWord);
        if(command != null) {
            return command;
        }
        else {
            return null;
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
        /**for(String s : validCommands.keySet()) {
            if(s.equals(aString))
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;*/
    }

    public String[] getValidCommands() {
        String[] commands = new String[validCommands.size()+1];
        for (String s : validCommands.keySet()) {
            CommandWord word = validCommands.get(s);
            if (word != CommandWord.UNKNOWN)
                commands[word.ordinal()] = s;
        }
        return commands;
    }
}
