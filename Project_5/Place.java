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
public class Place{

    //Private Variables
    private int ID;
    private String name;
    private String desc;
    private ArrayList<Direction> dirList = new ArrayList<Direction>(1);
    private static Place Start;

    //Items that can be found in current place
    private ArrayList<Artifact> items = new ArrayList<Artifact>(1);

    //Collection of all places in the game
    private static HashMap<Integer, Place> placeMap = new HashMap<>();

    //Collection of places for randomization purposes
    private static ArrayList<Integer> placeIDList = new ArrayList<>(1);

    //Collection of characters in THIS place.
    private ArrayList<Character> people = new ArrayList<Character>(1);



    //Constructor for the exit and nowhere place.
    public Place(boolean isFirstTime){
        if(isFirstTime){
            this.ID = 1;
            this.name = "Exit";
            this.desc = "You have left the area.";
        }
        else {
            this.ID = 0;
            this.name = "Nowhere";
            this.desc = "There is a door here, but it leads to a wall?";
        }
        placeMap.put(this.ID,this);
        placeIDList.add(this.ID);
    }

    //Constructor for when there is a file parsing
    public Place(Scanner file, String filetype){
        String current;
        while(true){
            current = file.nextLine();

            //Parse through all the comments.
            if(current.length() == 0 || current.startsWith("//") || current.startsWith("/*")){
                continue;
            }
            else{
                //First Line
                current = CleanLineScanner.getCleanLine(current);

                //Split up string into tokens and then use data appropriately.
                StringTokenizer tokens = new StringTokenizer(current);

                //ID
                int IDtemp = Integer.valueOf(tokens.nextToken());
                if(filetype.equalsIgnoreCase("4.0")){
                    if (IDtemp <= 1) {
                        System.out.println("Places has invalid ID number.\n");
                        System.exit(0);
                    }
                    else{

                    }
                }
                this.ID = IDtemp;

                //Place Name
                String placeName = "";
                while(tokens.hasMoreTokens()) {
                    placeName = placeName + " " + tokens.nextToken();
                }
                placeName = placeName.trim();
                this.name = placeName;

                //Second Line
                current = file.nextLine();
                int nOfDesc = Integer.valueOf(current);
                int i;

                String description = "";
                //Rest should be description
                for(i = 0; i < nOfDesc; i++){
                    current = file.nextLine();
                    description = description + "\n" + current;
                }
                description = description.trim();
                //System.out.println(description);
                this.desc = description;
                break;
            }
        }

        //Making sure the start place is the first entry of the hashmap.
        if(Start == null){
            Start = this;
        }
        placeMap.put(this.ID,this);
        placeIDList.add(this.ID);
    }

    //Return the starting location.
    public static Place getStart(){
        return Start;
    }
    //Method set up for adding Directions onto the map.
    public void addDirection(Direction name){
        this.dirList.add(name);
    }

    //Add the Artifact into the ArrayList of items in the current place.
    public void addArtifact(Artifact temp){
        this.items.add(temp);
    }

    //Adding the characater to the Place.
    public void addCharacter(Character user){
        this.people.add(user);
    }

    //Removing the character from ths place because he/she moved away.
    public void removeCharacter(Character user){
        this.people.remove(user);
    }

    //Method for allowing the character to follow a specific path.
    public Place followDirection(String input, IO screen){
        //Keep track of where we are and where our future destination may be.
        Place result = this;
        int flag = 0;

        //Check all available directions for this place.
        for(Direction d: dirList){
                //Leaving work to the direction class. If the input matches, then we send the user to follow that
                //direction.
                if(d.match(input)){
                    result = d.follow();
                    flag = 1;
                    break;
                }
        }
        //Check if the place is the exit.
        if(result.ID == 1){
            screen.display("You left the map. Goodbye");
            screen.display("\n");
            System.exit(0);
        }
        //Check if the place is nowhere and magically teleport user to the start of map.
        else if(result.ID == 0){
            screen.display("You ended up somewhere where you shouldn't be. You passed out and ended up back at " +
                    "where you started.\n");
            screen.display("\n");
            return getPlaceID(12);
        }

        //We didn't go anywhere. Print out we are still in the same spot :/
        if(flag == 0){
            Direction temp = dirList.get(0);
            if(temp.check(input)){
                screen.display("There is no available direction for " + input + "\n");
                screen.display("\n");
            }
            else{
                screen.display(input + " is not a valid direction/move.\n");
                screen.display("\n");
            }

        }

        //Return whatever place we landed in.
        return result;
    }

    //This function will print out all artifacts in a room.
    public void searching(IO screen){
        screen.display("Searching the room for items...");
        screen.display("\n");
        int counter = items.size();
        screen.display("Items Found: " + counter);
        screen.display("\n");
        for(Artifact item: items){
            item.print(screen);
        }
        return;
    }

    //User Friendly information?
    public void display(IO screen){
        screen.display("Current Place: ");
        this.printNameNewLine(screen);
        screen.display("\n");
        screen.display("Description");
        this.printDesc(screen);
        screen.display("\n");

        int value;
        int counter;
        //Printing all the people in this place!
        value = this.people.size();
        if(value > 0){
            screen.display("Current People in this place.");
            screen.display("\n");
            //Print our character information too.
            for (counter = 0; counter < value; counter++) {
                screen.display("Character " + (counter + 1) + " Data");
                screen.display("\n");
                this.people.get(counter).display();
                screen.display("\n");
            }
        }
        else{
            screen.display("Something is wrong with Characters if you somehow reached this print " +
                    "statement.");
            screen.display("\n");
        }

    }
    //Print out name of the place.
    public void printName(IO screen){
        screen.display(name);
    }
    //Print out name of the place.
    public void printNameNewLine(IO screen){
        screen.display(name);
        screen.display("\n");
    }
    //Print out description of the place.
    public void printDesc(IO screen){
        screen.display(desc);
    }
    //Print out name of the place.
    public void printDescNewLine(IO screen){
        screen.display(desc);
        screen.display("\n");
    }

    //Function to use the artifact in the current place
    public void useKey(Artifact item, IO screen){
        for(Direction d: dirList){
            d.useKey(item, screen);
            return;
        }
        screen.display("There is no use for " + item.name() + " in this area.");
        screen.display("\n");
    }
/*
    //Check if the item exists in the items ArrayList
    public boolean itemExistence(String name){
        for(Artifact a: items){
            if(name.equalsIgnoreCase(a.name())){
                return true;
            }
        }
        return false;

    }
*/
    //Get an item in the place if it exists there.
    public Artifact getItem(String itemName, int inUse, int capacity, IO screen){
        for(Artifact a: items){
            if(itemName.equalsIgnoreCase(a.name())){
                if(a.size() < 0){
                    screen.display("This item is WAY too heavy for you to carry.");
                    screen.display("\n");
                    break;
                }

                if(inUse + a.size() <= capacity) {
                    screen.display("You picked up " + a.name() + ".");
                    screen.display("\n");
                    items.remove(a);
                    return a;
                }
                else{
                    screen.display("You will be encumbered if you try to pick up this item.");
                    screen.display("\n");
                    return null;
                }
            }
        }
        screen.display("Item is not here.");
        screen.display("\n");
        return null;

    }

    //Drop item in the current place we are in.
    public void dropItem(Artifact dropped, IO screen){
        screen.display("You dropped " + dropped.name());
        screen.display("\n");
        items.add(dropped);
    }

    //Function to return a place of object in the static arrayList.
    public static Place getPlaceID(int placeID){
        if(placeMap.containsKey(placeID)){
            Place temp = placeMap.get(placeID);
            return temp;
        }
        else{
            return null;
        }
    }

    //Static Method for to printing all Data in game.
    public static void printAll(IO screen){

        screen.display("Information about the Game");
        screen.display("\n");
        int counter;
        int value;
        screen.display("Data for debugging purposes\n");
        screen.display("\n");

        //Go through every Place and print out the ID, Name, Description, People in this place, Artifacts in this
        // place, Directions and where they lead to.
        for(Map.Entry<Integer, Place> current : Start.placeMap.entrySet()){
            Place temp = current.getValue();
            screen.display("ID: " + temp.ID);
            screen.display("\n");
            screen.display("Name: " + temp.name);
            screen.display("\n");
            screen.display("Description: " + temp.desc + "\n");
            screen.display("\n");

            //Printing out direction information!
            value = temp.dirList.size();
            if(value > 0){
                screen.display("Available Directions");
                screen.display("\n");
                //Print our direction information too.
                for (counter = 0; counter < value; counter++) {
                    temp.dirList.get(counter).print(screen);
                    screen.display("\n");
                }
            }

            //Printing all the people in this place!
            value = temp.people.size();
            if(value > 0){
                screen.display("Current People in this place.");
                screen.display("\n");
                //Print our character information too.
                for (counter = 0; counter < value; counter++) {
                    screen.display("Character " + (counter + 1) + " Data");
                    screen.display("\n");
                    temp.people.get(counter).print();
                    screen.display("\n");
                }
            }
            else{
                screen.display("No Characters in this place.\n");
                screen.display("\n");
            }

            //Printing out all the Artifacts in this Place
            value = temp.items.size();
            if(value > 0){
                screen.display("Artifacts in this place");
                screen.display("\n");
                for (counter = 0; counter < value; counter++) {
                    screen.display("Artifact " + (counter + 1) + " Data");
                    screen.display("\n");
                    temp.items.get(counter).print(screen);
                    screen.display("\n");
                }
            }
            else{
                screen.display("No Artifacts in this place.");
                screen.display("\n");
            }

            //Divider line to help read output easier
            screen.display("----------------------------------------------------");
            screen.display("\n");
        }
    }

    //Return appropriate character based in inout
    public Character findCharacter(String input){
        for(Character current : people){
            if((current.checkName(input))){
                return current;
            }
        }

        return null;
    }

    //Variable to pull any Random Place
    public static Place randomPlace(){
        Place temp;
        while (true) {
            Random rand = new Random();
            int number = rand.nextInt((placeIDList.size() - 1));
            number = placeIDList.get(number);
            temp = getPlaceID(number);

            //Making sure that the random place is not exit or nowhere because that would suck.
            if (temp.getID() == 1 || temp.getID() == 0) {
                continue;
            }
            break;
        }
        return temp;
    }

    //Getter function for AI
    public int AIdiffBranches(){
        return dirList.size();
    }

    //Random decider for AI
    public Direction AIchosenDirection(int random){
        return dirList.get(random);
    }

    //Getter for ID
    public int getID(){
        return ID;
    }

    //Get the name of the place
    public String getName(){
        return name;
    }

    //Unused Methods
    //Get the description of this place.
    public String getDesc(){
        return desc;
    }
}
