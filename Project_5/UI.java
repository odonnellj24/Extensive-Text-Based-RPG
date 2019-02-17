//Name: Conway Dang
//ACCC: cdang
//Name: Jack O'Donnell
//ACC: jodonnel
//Name: Michelle Nguyen
//ACC: mnguyen

/*
    File Name: Character.java

    Written Fall 2018 by Conway Dang and Jack O'Donnell and Michelle Nguyen for CS 342

    This class represents the Character object which holds some information about the character. This is the user's
    viewpoint through the map and can interact the things around it.
*/
import java.util.*;
public class UI implements DecisionMaker{

    //Overriding.
    @Override
    public Move getMove(Character user, Place currPlace){
        Scanner cin = KeyboardScanner.getKeyboardScanner();

        //Fancy User Interface Stuff
        user.screen.display("Next Instruction? (Type in Help or ? for List of Commands)");
        user.screen.display("\n");
        user.screen.display(">");

        String command = cin.nextLine();
        while(command.length() == 0){
            user.screen.display("You can not enter nothing.");
            user.screen.display("\n");
            command = cin.nextLine();
        }
        //Stringtokenizer to split up the command into the first word and the rest should be it's argument.
        StringTokenizer tokens = new StringTokenizer(command);

        //Get the firstword of the command.
        String firstword = tokens.nextToken();

        //Building the Argument string
        String argument = "";
        while(tokens.hasMoreTokens()) {
            argument = argument + " " + tokens.nextToken();
        }

        //Trimming the argument.
        argument = argument.trim();

        if(firstword.equalsIgnoreCase("GUI")){
            return new Move(firstword, argument);
        }
        //Drop item from inventory
        else if(firstword.equalsIgnoreCase("Drop")){
            return new Move(firstword, argument);
        }
        //Display
        else if(firstword.equalsIgnoreCase("Display")){
            return new Move(firstword, "");
        }
        //Exit the game
        else if(firstword.equalsIgnoreCase("EXIT")||firstword.equalsIgnoreCase("QUIT")){
            return new Move("Exit","");
        }
        else if(firstword.equalsIgnoreCase("Fight") || firstword.equalsIgnoreCase("Challenge")){
            return new Move("Fight", argument);
        }
        //Get command
        else if(firstword.equalsIgnoreCase("Get")){
            return new Move(firstword, argument);
        }
        //Print out all commands that the user can type out.
        else if(firstword.equalsIgnoreCase("Help") || firstword.equalsIgnoreCase("?")){
            return new Move ("Help", "");
        }
        //Print out all items in inventory.
        else if(firstword.equalsIgnoreCase("Inventory") || firstword.equalsIgnoreCase("Inve")){
            return new Move("Inventory","");
        }
        //Print out current room only and description.
        else if(firstword.equalsIgnoreCase("Look")){
            return new Move(firstword,"");
        }
        //Handle Go, Head, or Move -> Cardinal Direction
        else if(firstword.equalsIgnoreCase("Move")|| firstword.equalsIgnoreCase("Head") ||firstword.equalsIgnoreCase
                ("Go")){
            return new Move("Move", argument);
        }
        //Pass the information
        else if(firstword.equalsIgnoreCase("Pass")){
            return new Move(firstword, "");
        }
        //Print the room and all directions in the room.
        else if(firstword.equalsIgnoreCase("Print")){
            return new Move(firstword, "");
        }
        //Search for artifacts in the room.
        else if(firstword.equalsIgnoreCase("Search")){
            return new Move(firstword, "");
        }
        //Use the artifact if it is in the inventory.
        else if(firstword.equalsIgnoreCase("Use")){
            return new Move(firstword, argument);
        }
        //What command did you send in??
        else{
            return new Move("Unknown", command);
        }
    }
}