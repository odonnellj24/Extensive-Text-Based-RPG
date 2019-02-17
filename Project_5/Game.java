
import java.util.*;


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
public class Game {

    //Variables
    private String title;
    private boolean isFirstTime = true;

    //Data structures to keep track of the Players and NPCs
    private ArrayList<Player> Players = new ArrayList<>(0);
    private ArrayList<NPC> NPCs = new ArrayList<>(0);

    //Constructor
    public Game(Scanner file){

        //Get the file format first and see if it is correct
        String current = file.nextLine();

        //Truncate String after the comments
        current = CleanLineScanner.getCleanLine(current);

        //Grab the format of file and build appropriate errors
        String fileFormat = current.substring(0, 3);
        if(!fileFormat.equalsIgnoreCase("GDF")){
            System.out.println("Error: Invalid File Format");
            System.exit(0);
        }

        //Grab the type of file it is and build appropriate errors
        String type = current.substring(4, 7);
        float version = Float.valueOf(type);

        //Making sure that the GDF file is a 4.0 or 3.1 GDF file.
        if(fileFormat.equalsIgnoreCase("3.1")||fileFormat.equalsIgnoreCase("4.0")||fileFormat.equalsIgnoreCase
                ("5.0")||fileFormat.equalsIgnoreCase("5.1")){
            System.out.println("Error: Invalid Type of File. Only 4.0, 3.1, 5.0, and 5.1 File Types Accepted.");
            System.exit(0);
        }

        //Rest of string is just the title;
        title = current.substring(8, current.length());
        System.out.println("Let's Play: " +  title);

        //Keep getting the next line in file until we encounter the word "PLACE"
        current = file.nextLine();
        int result;
        while(true){
            current = CleanLineScanner.getCleanLine(current);
            if(current == null){
                current = file.nextLine();
                continue;
            }
            if(current.contains("PLACE")){
                current = current.replaceAll("[^0-9]", "");
                result = Integer.valueOf(current);
                break;
            }

        }

        //For loop to input the places in to the data storage of Game.
        int counter;
        for(counter = 0; counter < result; counter++){
            new Place(file, type);
        }

        //Import the exit and nowhere places AFTER we parse the file. (Starting Place should not be exit or nowhere.)
        Place exit = new Place(isFirstTime);
        isFirstTime = false;
        Place nowhere = new Place(isFirstTime);

        //Keep getting the next line in file until we encounter the word "DIRECTIONS"
        while(file.hasNextLine()){
            current = file.nextLine();

            //Parse through all the comments.
            if(current.length() == 0 || current.startsWith("//") || current.startsWith("/*")){
                continue;
            }
            if(current.contains("DIRECTIONS")) {
                current = CleanLineScanner.getCleanLine(current);
                current = current.replaceAll("[^0-9]", "");
                result = Integer.valueOf(current);
                break;
            }
        }

        //Place all directions in their corresponding place.
        for(counter = 0; counter < result; counter++){
            Direction dirTemp = new Direction(file, type);
            Place temp = dirTemp.getPlacefrom();
            temp.addDirection(dirTemp);
        }

        //Check for characters if the file is a 4.0 or 5.0 type file.
        if(!(type.equalsIgnoreCase("3.1"))){

            //Keep getting the next line in file until we encounter the word "CHARACTERS"
            while (true) {
                current = file.nextLine();

                //Parse through all the comments.
                if (current.length() == 0 || current.startsWith("//") || current.startsWith("/*")) {
                    continue;
                }
                if (current.contains("CHARACTERS")) {
                    current = CleanLineScanner.getCleanLine(current);
                    current = current.replaceAll("[^0-9]", "");
                    result = Integer.valueOf(current);
                    break;
                }
            }

            //Check for what version we are messing with
            if(version >= 4.0 && version < 5.0){
                //Go through all the characters and determine if they are Players or NPCs
                for (counter = 0; counter < result; counter++) {
                    while(true){
                        //Parse through the word until we encounter the word player or npc.
                        current = file.next();

                        //Call respective subclasses depending on which word is found.
                        if (current.equalsIgnoreCase("Player")){
                            Player temp = new Player(file, type);
                            Players.add(temp);
                            break;
                        }
                        else if (current.equalsIgnoreCase("Npc")){
                            NPC temp = new NPC(file, type);
                            NPCs.add(temp);
                            break;
                        }
                    }
                }
            }
            else if(version >= 5.0){
                for(counter = 0; counter < result; counter++){
                    while(file.hasNextLine()){
                        current = file.nextLine();

                        //Parse through all the comments.
                        if(current.length() == 0 || current.startsWith("//") || current.startsWith("/*")){
                            continue;
                        }
                        current = CleanLineScanner.getCleanLine(current);
                        //Call respective subclasses depending on which word is found.
                        if (current.equalsIgnoreCase("Player")){
                            Player temp = new Player(file, type);
                            Players.add(temp);
                            break;
                        }
                        else if (current.equalsIgnoreCase("Npc")){
                            NPC temp = new NPC(file, type);
                            NPCs.add(temp);
                            break;
                        }
                    }
                }
            }
        }


        //Keep getting the next line in file until we encounter the word "ARTIFACTS"
        while(file.hasNextLine()){
            current = file.nextLine();

            //Parse through all the comments.
            if(current.length() == 0 || current.startsWith("//") || current.startsWith("/*")){
                continue;
            }
            if(current.contains("ARTIFACTS")) {
                current = CleanLineScanner.getCleanLine(current);
                current = current.replaceAll("[^0-9]", "");
                result = Integer.valueOf(current);
                break;
            }
        }

        //Place all artifacts in their corresponding places.
        for(counter = 0; counter < result; counter++){
            Artifact artTemp = new Artifact(file, type);
            int origin = artTemp.origin();

            //Depending on what the origin of the artifact is we would assign it into a place or a character
            if(type.equalsIgnoreCase("4.0")) {
                if (origin > 0) {
                    Place temp = Place.getPlaceID(origin);
                    if(temp != null) {
                        temp.addArtifact(artTemp);
                    }
                    else{
                        System.exit(0);
                    }
                }
                else if (origin == 0) {
                    Place temp = Place.randomPlace();
                    temp.addArtifact(artTemp);
                }
                else {
                    origin = origin * -1;
                    Character temp = Character.getCharacterID(origin);
                    if(temp != null) {
                        temp.startingItem(artTemp);
                    }
                    else{
                        System.exit(0);
                    }
                }
            }
            else if(type.equalsIgnoreCase("3.1")){
                Place temp = Place.getPlaceID(origin);
                if(temp != null) {
                    temp.addArtifact(artTemp);
                }
                else{
                    System.exit(0);
                }
            }

        }
    }

    //Check the ID of all the Players and NPCs to make sure that no character has the same ID.
    boolean checkID(int ID){

        //Going through all the Players
        for(Player current : Players){
            if(current.ID == ID){
                System.out.println("ID: " + ID + " is already taken by the one of the players. Please " +
                        "select another.");
                return false;
            }
        }

        //Going through all the NPCs
        for(NPC current: NPCs){
            if(current.ID == ID){
                System.out.println("ID: " + ID + " is already taken by one of the NPCs. Please select " +
                        "another.");
                return false;
            }
        }
        return true;
    }

    //Check if all players are still alive
    public boolean checkAllPlayersAlive(){
        for(Player current : Players){
            if(current.alive){
                return true;
            }
        }
        return false;
    }


    //Playing the game
    public void play(){

        //When there are no players when we already parse the file.
        if(Players.isEmpty()){
            //Grab the keyboard Scanner and ask for players info.
            Scanner cin = KeyboardScanner.getKeyboardScanner();

            //How many players?
            System.out.println("How many players are playing?");
            String input = cin.nextLine();
            int counter = Integer.valueOf(input);

            int x;
            boolean flag;

            //Going through each individual player
            for(x = 0; x < counter; x++){
                System.out.println("Player " + (x+1));

                //Name
                System.out.println("What will be your character name?");
                String name = cin.nextLine();

                //Description
                System.out.println("Please provide information that describes your character.");
                String description = cin.nextLine();

                //ID
                System.out.println("Pick a number between 1 - 100 for your character's ID.");

                //Checking the ID if it fits in the range AND not taken.
                int ID;
                while(true){
                    input = cin.nextLine();
                    ID = Integer.valueOf(input);
                    if (ID > 100 || ID < 1) {
                        System.out.println("ID is not in range of 1 - 100");
                        continue;
                    }
                    flag = checkID(ID);
                    if(flag){
                        break;
                    }
                }

                //Adding the player
                Player temp = new Player(ID, name, description);
                Players.add(temp);
            }

            //Ask if the user wants to enter in NPCs.
            System.out.println("Would you like to add NPCS? Please type in Yes or No.");

            //Grabbing the user input.
            while(true) {
                input = cin.nextLine();
                if (input.equalsIgnoreCase("Yes") || input.equalsIgnoreCase("Y")){
                    System.out.println("How many NPCs will be playing with you?");
                    input = cin.nextLine();
                    counter = Integer.valueOf(input);
                    break;
                }
                else if(input.equalsIgnoreCase("No") || input.equalsIgnoreCase("N")){
                    System.out.println("Okay. No NPCs will be spawning in.");
                    counter = 0;
                    break;
                }
                else{
                    System.out.println(input + "is not a valid input.");
                }
            }

            //Adding the NPCs.
            for(x = 0; x < counter; x++){
                System.out.println("Non-playable Character " + (x+1));

                //Name
                System.out.println("What will be this NPC's name?");
                String name = cin.nextLine();

                //Description
                System.out.println("Please provide information that describes this NPC.");
                String description = cin.nextLine();

                //ID
                System.out.println("Pick a number between 1 - 100 for your character's ID.");
                int ID;

                //Checking the ID if it fits in the range AND not taken.
                while(true) {
                    input =  cin.nextLine();
                    ID = Integer.valueOf(input);
                    if (ID > 100 || ID < 1) {
                        System.out.println("ID is not in range of 1 - 100");
                        continue;
                    }

                    flag = checkID(ID);
                    if(flag){
                        break;
                    }
                }

                //Adding the NPC.
                NPC temp = new NPC(ID, name, description);
                NPCs.add(temp);
            }
        }

        //Go through each Player and NPC to make them take there turns until one of the players quit.
        int counter;
        while(true){
            for(counter = 0; counter < Players.size(); counter++) {
                Players.get(counter).makeMove();
            }
            for(counter = 0; counter < NPCs.size(); counter++){
                NPCs.get(counter).makeMove();
            }
            if(!(checkAllPlayersAlive())){
                System.out.println("All Players are dead. Ending Game...");
                System.exit(0);
            }
        }
    }
}
