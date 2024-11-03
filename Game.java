import java.util.Scanner;

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
    private Player player1;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        player1 = new Player(getPlayerName());
        createRooms();
        parser = new Parser();
    }



    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office, cellar;
        Item hammer, gun, sun, sample, mask, winterCoat, magicCookie;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        cellar = new Room("in the cellar of the computing office. It is full of wine :)");

        sun = new Item("sun", "Sun", 1000, false, false, true);
        hammer = new Item("hammer", "A huge hammer", 2, false, false, true);
        gun = new Item("gun", "Gun, .45mm", 1, false, false, true);
        sample = new Item("sample", "Sample of seamen", 1, false, false, true);
        mask = new Item("mask", "Theater mask", 1, true, false, true);
        winterCoat = new Item("coat", "Winter coat", 19, true, false, true);
        magicCookie = new Item("cookie", "Magic cookie - what could this be?", 12, false, true, true);

        
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        outside.setExit("down", office);
        outside.setItem(sun);

        theater.setExit("west", outside);
        theater.setItem(sample, mask);

        pub.setExit("east", outside);
        pub.setItem(gun, winterCoat);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);
        office.setExit("up", outside);
        office.setExit("down", cellar);
        office.setItem(hammer);

        cellar.setExit("superup", office);
        cellar.setItem(magicCookie);

        player1.setCurrentRoom(outside);
        player1.setMaximumWeightToCarry(20);
        // start game outside
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
        System.out.println("Welcome " + player1.getName() + " to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help. Valid commands are: ");
        System.out.println(getValidCommands() + "\n");
        printLocationInfo();
    }

    private String getPlayerName() {
        System.out.print("Please enter your name: ");
        Scanner input = new Scanner(System.in);
        return input.nextLine();
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

        //String commandWord = command.getCommandWord();
        CommandWord commandWord = command.getCommandWord();
        switch (commandWord) {
            case HELP:
            printHelp();
            break;
            case GO:
                goRoom(command);
                break;
            case LOOK:
                look();
                break;
            case BACK:
                goBack();
                break;
            case EAT:
                eat(command);
                break;
            case TAKE:
                take(command);
                break;
            case DROP:
                drop(command);
                break;
            case SAVE:
                save(command);
                break;
            case LOAD:
                load(command);
                break;
            case INVENTORY:
                System.out.println(player1.getInventoryString());
                break;
            case QUIT:
                wantToQuit = quit(command);
            default:
                System.out.println();
        }
        return wantToQuit;
    }

    private void save(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Saving...");
            FileHandler file = new FileHandler();
            String data = "name: " + player1.getName() + "#currentRoom: " + player1.getCurrentRoom().getDescription() + "#" + "health: 100#" + "inventory: ";
            if(!player1.getInventory().isEmpty())
                for( Item i : player1.getInventory())
                    data += i.getName() + ";";
            file.CreateFile(command.getSecondWord(),data);
        }
        else {
            System.out.println("Add a file name to save. eg.: \"save alive\"");
        }
    }

    private void load (Command command) {
        System.out.println("Loading something - I don't know yet what, but it is loading. Just wait for it");
        //TODO: implement load method
    }

    private void take(Command command) {

        //TODO: move this and the drop() methods to the Player class
        boolean isExists = false;
        if (command.hasSecondWord()) {
            for (Item item : player1.getCurrentRoom().getItem()) {
                if (item.getName().equalsIgnoreCase(command.getSecondWord())) {
                    if(item.getWeight() > player1.getMaximumWeightToCarry()) {
                        System.out.println("Item is too heavy to carry");
                        isExists = true;
                        break;
                    }
                    player1.setInventory(item);
                    player1.setMaximumWeightToCarry(player1.getMaximumWeightToCarry()-item.getWeight());
                    player1.getCurrentRoom().removeItem(item);
                    isExists = true;
                    System.out.println(item.getDescription() + " has been taken. Now you have it in your inventory");
                    break;
                }

            }
            if (!isExists) {
                    System.out.println(command.getSecondWord() + " does not exist in this room");
            }
        }
        else {
            System.out.println("What do you want to take?");
        }
    }

    private void drop(Command command) {
        boolean isExtists = false;
        if (command.hasSecondWord()) {
            for (Item item : player1.getInventory()) {
                if (item.getName().equalsIgnoreCase(command.getSecondWord())) {
                    isExtists = true;
                    player1.removeInventory(item);
                    player1.setMaximumWeightToCarry(player1.getMaximumWeightToCarry() + item.getWeight());
                    player1.getCurrentRoom().setItem(item);
                    System.out.println(item.getDescription() + " has been dropped.");
                    break;
                }

                }
            if (!isExtists) {
                    System.out.println(command.getSecondWord() + " does not exist in your inventory");
            }
        }
        else {
            System.out.println("What do you want to drop?");
        }
    }

    private void eat(Command command) {
        if (command.hasSecondWord() && player1.getAnItemFromInventory(command.getSecondWord())!=null
            && player1.getAnItemFromInventory(command.getSecondWord()).isCanEat()) {
            System.out.println(command.getSecondWord() + " has been eaten. Now you are full");
            player1.removeInventory(player1.getAnItemFromInventory(command.getSecondWord()));
            player1.setMaximumWeightToCarry(2000);
        }
        else {
            System.out.println("What would you like to eat?");
        }
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(getValidCommands());
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?\n" + player1.getCurrentRoom().getExitString());
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;

        nextRoom = player1.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            player1.addToRoomHistory(player1.getCurrentRoom());
            player1.setCurrentRoom(nextRoom);
            printLocationInfo();
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
     * "Look" was entered. Check the rest of the command to see
     * whether we really just wan to look around the room.
     * @return true, if this command quits the game, false otherwise.
     */
    private void look()
    {
            System.out.println("OK, then again...");
            System.out.println(player1.getCurrentRoom().getLongDescription());

    }

    private void goBack() {
        if (!player1.getRoomHistory().isEmpty()) {
            Room tempRoom = player1.getRoomHistory().getLast();
            System.out.println("Returning to previous room");
            player1.setCurrentRoom(player1.getRoomHistory().getLast());
            player1.removeFromRoomHistory(tempRoom);
            printLocationInfo();
        }
        else {
            System.out.println("Can not go back at this point");
        }
    }


    /**
     * Get the info on current location
     */
    private void printLocationInfo()
    {
        System.out.println("You are " + player1.getCurrentRoom().getLongDescription());
    }

    private String getValidCommands() {
        String commands = "\t";
        for (String c : parser.getValidCommands()) {
            commands += c + " ";
        }
        return commands;
    }
}
