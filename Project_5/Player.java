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
public class Player extends Character{
    //File Constructor
    public Player(Scanner file, String version){
        super(file, version, "Player");
        this.type = "Player";
        System.out.println("Do you want " + name + " to have developer powers? (No limit to how many " +
                "actions/directions this character can do?");
        Scanner cin = KeyboardScanner.getKeyboardScanner();
        String input;
        //Grabbing the user input.
        while(true) {
            input = cin.nextLine();
            if (input.equalsIgnoreCase("Yes") || input.equalsIgnoreCase("Y")){
                System.out.println("Developer Mode for " + name + " is on.");
                this.developerMode = true;
                break;
            }
            else if(input.equalsIgnoreCase("No") || input.equalsIgnoreCase("N")){
                System.out.println("Developer Mode for " + name + " is off.");
                this.developerMode = false;
                break;
            }
            else{
                System.out.println(input + " is not a valid input.");
            }
        }
    }
    //Non-File Constructor
    public Player(int ID, String name, String description){
        super(ID, name, description, "Player");
        this.type = "Player";
        System.out.println("Do you want " + name + " to have developer powers? (No limit to how many " +
                "actions/directions this character can do?)");
        Scanner cin = KeyboardScanner.getKeyboardScanner();
        String input;
        //Grabbing the user input.
        while(true) {
            input = cin.nextLine();
            if (input.equalsIgnoreCase("Yes") || input.equalsIgnoreCase("Y")){
                System.out.println("Developer Mode for " + name + " is on.");
                this.developerMode = true;
                break;
            }
            else if(input.equalsIgnoreCase("No") || input.equalsIgnoreCase("N")){
                System.out.println("Developer Mode for " + name + " is off.)");
                this.developerMode = false;
                break;
            }
            else{
                System.out.println(input + " is not a valid input.");
            }
        }
    }
}
