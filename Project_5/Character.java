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
public class Character{

    //Protected Variables because the Descendants should be able to grab this information.
    protected int ID;
    protected int health;
    protected Place location;
    protected String name;
    protected String description;
    protected DecisionMaker decider;
    protected String type;
    protected int inUse = 0;
    protected Artifact offhand;
    protected int capacity = 10;
    protected boolean alive = true;
    protected boolean developerMode = false;
    protected IO screen;

    //Collection of all characters in the game
    private static HashMap<Integer, Character> characterMap = new HashMap<>();

    //Collection of places for randomization purposes
    private ArrayList<Integer> characterIDList = new ArrayList<>(1);

    //Inventory of the player (Artifacts)
    private ArrayList<Artifact> Inventory = new ArrayList<Artifact>(5);

//
//    PLAYER 		 // A player
//12			 // in the Entrance Hall, where expected
//        42	Zaphod Beeblrox
//10
//        2
//    Zaphod Beeblebrox is the President of the Universe
//    He has 2 heads and 3 arms, and likes to have a good time.

    //Constructor for Character when the scanner reads it.
    protected Character(Scanner file, String version, String charType){
        String current;
        int temp;
        if(version.equalsIgnoreCase("4.0")) {
            current = file.next();
            //Starting location specified by the file.
            temp = Integer.valueOf(current);
        }
        else{
            current = file.nextLine();
            current = CleanLineScanner.getCleanLine(current);
            temp = Integer.valueOf(current);
        }


        //Check the file to make sure what version we are messing with.
        if(version.equalsIgnoreCase("4.0")|| version.equalsIgnoreCase("5.0")||version.equalsIgnoreCase("5.1")) {
            if(temp > 0) {
                this.location = Place.getPlaceID(temp);
                location.addCharacter(this);
            }
            else{
                this.location = Place.randomPlace();
                location.addCharacter(this);
            }
        }
        else{
            this.location = Place.getPlaceID(temp);
            location.addCharacter(this);
        }

        //Creating the decision maker for character
        if(charType.equalsIgnoreCase("Player")) {
            this.decider = new UI();
            this.screen = new IO();
        }
        else{
            this.decider = new AI();
            this.screen = new IO();
        }

        //Parsing through all the comments.
        while(true){
            current = file.nextLine();

            //Parse through all the comments.
            if (current.length() == 0 || current.startsWith("//") || current.startsWith("/*")) {
                continue;
            }
            else{
                break;
            }
        }

        if(version.equalsIgnoreCase("4.0")){
            current = file.nextLine();
        }
        else{

        }

        //Use tokenizer to grab ID and name.
        StringTokenizer tokens = new StringTokenizer(current);
        int counter = 0;
        this.name = "";
        while(tokens.hasMoreTokens()){
            switch(counter){
                //Assign the ID
                case 0:
                    this.ID = Integer.valueOf(tokens.nextToken());
                    break;

                //Building the name
                default:
                    this.name = name + " " + tokens.nextToken();
                    break;
            }
            counter++;
        }

        //Trim the name
        this.name = this.name.trim();

        if(version.equalsIgnoreCase("5.1")){
            current = file.nextLine();
            health = Integer.valueOf(current);
        }
        else{
            health = 10;
        }

        //Description information
        current = file.nextLine();
        int nofDesc = Integer.valueOf(current);
        int i;
        description = "";

        //Building the Description.
        for(i = 0; i < nofDesc; i++){
            current = file.nextLine();
            description = description + current + "\n";
        }
        this.type = charType;

        //Adding the character
        characterMap.put(this.ID, this);
    }

    //Other Character constructor when we decide to let the user make their own characters.
    protected Character(int ID, String name, String desc, String charType){

        //Assigning information for each character.
        this.ID = ID;
        this.name = name;
        this.description = desc;

        //Creating the decision maker for the character
        if(charType.equalsIgnoreCase("Player")){
            this.decider = new UI();
        }
        else{
            this.decider = new AI();
        }

        //Make their starting location the starting location and specifying the type.
        this.location = Place.getStart();
        location.addCharacter(this);
        this.type = charType;
        this.screen = new IO();


        screen.display("How much health does this character have?");
        screen.display("\n");
        String answer = screen.getLine();
        health = Integer.valueOf(answer);
    }

    //Function to return a place of object in the static arrayList.
    public static Character getCharacterID(int characterID){
        if(characterMap.containsKey(characterID)){
            Character temp = characterMap.get(characterID);
            return temp;
        }
        else{
            System.out.println("Character" + characterID + " does not exist.");
            return null;
        }
    }

    //Print out all inventory for character
    public void printInventory(){
        screen.display("All Items in Inventory");
        screen.display("\n");
        for(Artifact a: Inventory){
            a.print(screen);
        }
        screen.display("Bag Capacity: " + this.inUse + "/" + this.capacity);
        screen.display("\n");
    }

    //Giving the user this item at the start.
    public void startingItem(Artifact item){
        inUse = inUse + item.size();
        Inventory.add(item);
    }



    //Getting the item
    public void getItem(String item, IO screen){
        //Let Place handle the rest
        Artifact temp = location.getItem(item, inUse, capacity, screen);
        if(temp != null){
            inUse = inUse + temp.size();
            Inventory.add(temp);
            return;
        }
    }

    //Using the item
    public void useItem(String item){
        //Run through all items in inventory.
        for(Artifact a: Inventory) {
            //Check all items in inventory and see if the item matches to user input.
            if(a.checkName(item)){
                a.use(this, this.location, this.screen);
                if(item.equalsIgnoreCase("Leather Bag")){
                    Inventory.remove(a);
                }
                return;
            }
        }
        screen.display("You do not have " + item + " in your inventory.");
        screen.display("\n");
    }

    //Dropping the item in this place.
    public void dropItem(String item){
        //Run through all items in inventory.
        for(Artifact a: Inventory){
            if(a.checkName(item)){
                location.dropItem(a, screen);
                inUse = inUse - a.size();
                Inventory.remove(a);
                return;
            }
        }
        screen.display("You do not have " + item + " in your inventory.");
        screen.display("\n");
    }

    //Equipping the item
    public void equipItem(String item){
        for(Artifact current : Inventory){
            if(current.checkName(item)){
                offhand = current;
                return;
            }
        }
        screen.display("You can not equip " + item + " because you do not have it.");
        screen.display("\n");
    }

    //Display user-friendly information about the Character
    public void display(){
        screen.display("Name: " + name);
        screen.display("\n");
        screen.display("Description: " + description);
        screen.display("\n");
    }

    //Search the place for artifacts if there is any.
    public void searching(){
        location.searching(screen);
    }

    public boolean loseHealth(int damage){
        health = health - damage;
        if(health < 0){
            health = 0;
            alive = false;
            screen.display(this + " has died.");
            screen.display("\n");
            return true;
        }
        return false;
    }

    //Debugging information.
    public void print(){
        if(developerMode){
            screen.display("This Character is a Developer!!");
            screen.display("\n");
        }
        screen.display("Type: " + type);
        screen.display("\n");
        screen.display("Character Name: " + name);
        screen.display("\n");
        screen.display("Description: " + description);
        screen.display("\n");
        screen.display("Health: " + health);
        screen.display("\n");
        screen.display("ID " + ID);
        screen.display("\n");
        printInventory();
        screen.display("Bag Capacity: " + inUse + "/" + capacity);
        screen.display("\n");
    }

    //The makeMove function that does all the work for the character
    public void makeMove() {
        //Variables
        int flag = 0;
        boolean directionAction = true;
        boolean moveAction = true;

        //Check if the person is alive.
        if(!alive){
            return;
        }

        //Specify who's turn it is.
        screen.display(name + "'s Turn");
        screen.display("\n");
        while (((moveAction) || (directionAction)||(developerMode)) && (alive)){

            //Make sure that I don't same information twice.
            if (flag == 0) {
                screen.display("Current Place: ");
                location.printNameNewLine(screen);
                screen.display("\n");
                screen.display("Description");
                screen.display("\n");
                location.printDescNewLine(screen);
                screen.display("\n");
            }
            else {
                flag = 0;
            }

            //Call respective decisionmaker to do something.
            Move temp = decider.getMove(this, location);

            //Dropping the item
            if (temp.check("Drop")) {
                if (moveAction||developerMode) {
                    String argument = temp.getArgument();
                    dropItem(argument);
                    moveAction = false;
                }
                else{
                    screen.display(name + " has already used up their action point this turn. This character will" +
                            " have to pass their turn to do another action or move to a different direction.");
                    screen.display("\n");
                }
            }
            //Displaying user-friendly information about this place.
            else if (temp.check("Display")) {
                location.display(screen);
                flag = 1;
            }
            else if(temp.check("Equip")){
                String argument = temp.getArgument();
                equipItem(argument);
            }
            //Exit the Game.
            else if (temp.check("Exit")) {
                screen.display("Character " + name + " has exited the game. Thanks for playing.");
                screen.display("\n");
                System.exit(0);
            }
            //Getting an item.
            else if (temp.check("Get")) {
                if (moveAction||developerMode) {
                    String argument = temp.getArgument();
                    getItem(argument, screen);
                    moveAction = false;
                }
                else{
                    screen.display(name + " has already used up their action point this turn. This character will" +
                            " have to pass their turn to do another action or move to a different direction.");
                    screen.display("\n");
                }
            }
            //Moving somewhere with a Go/Head/Move command
            else if (temp.check("Go") || temp.check("Head") || temp.check("Move")) {
                if(directionAction||developerMode) {
                    //Grabbing the place where we began.
                    Place old = location;
                    location = location.followDirection(temp.getArgument(), screen);

                    //Making sure NPCs don't go to exit or nowhere.
                    if (type.equalsIgnoreCase("NPC")) {
                        if (location.getID() == 1 || location.getID() == 0) {
                            location = old;
                        }
                    }

                    //If we actually did move somewhere
                    if (location != old) {
                        directionAction = false;
                        old.removeCharacter(this);
                        location.addCharacter(this);
                    }
                    //Can't move twice.
                    else {
                        screen.display(name + " has already moved somewhere this turn. This character will have to " +
                                "wait next turn to make another move.");
                        screen.display("\n");
                    }
                }
            }
            //Help information.
            else if (temp.check("Help")) {
                screen.display("List of Commands");
                screen.display("\n");
                screen.display("Cardinal Directions: Head towards the direction you specify.");
                screen.display("\n");
                screen.display("Display: Gives information about the room you are in.");
                screen.display("\n");
                screen.display("Drop: Drop the Artifact you type in.");
                screen.display("\n");
                screen.display("Equip: Equips the item so you can use it to attack.");
                screen.display("\n");
                screen.display("Fight/Challenge/Attack: Attack any person if he exists in the room.");
                screen.display("\n");
                screen.display("Get: Grab any Artifact by typing the name of it.");
                screen.display("\n");
                screen.display("Go/Head/Move: Head towards the direction you specify.");
                screen.display("\n");
                screen.display("Inventory/Inve: List out what you have in your inventory");
                screen.display("\n");
                screen.display("Look: Tell me where I am and description only.");
                screen.display("\n");
                screen.display("Pass: Skip your turn to next player.");
                screen.display("\n");
                screen.display("Print: Debugging information only used for debugging.");
                screen.display("\n");
                screen.display("Quit/Exit: To leave the game.");
                screen.display("\n");
                screen.display("Search: Attempt to look for any artifacts in the room.");
                screen.display("\n");
                screen.display("Use: Use the Artifact that you type in.\n");
                screen.display("\n");
            }
            //Print inventory information.
            else if (temp.check("Inventory") || temp.check("Inve")) {
                printInventory();
            }
            //Looking what's around me.
            else if (temp.check("Look")) {
                //Tell the user where he/she is and then set the flag to 1 so I don't print again.
                location.display(screen);
                flag = 1;
            }
            //Printing information for debugging purposes only.
            else if (temp.check("Print")) {
                //Give me information of the place I'm on and then set the flag to 1 so I don't print again. This is
                // for debugging purposes.
                if(developerMode) {
                    location.printAll(screen);
                    flag = 1;
                }
                else{
                    screen.display("You do not have access to this because you are not a developer.");
                    screen.display("\n");
                }
            }
            //Skipping your turn.
            else if (temp.check("Pass")) {
                screen.display("Character " + name + " has passed their turn.");
                screen.display("\n");
                break;
            }
            //Search the area for artifacts.
            else if (temp.check("Search")) {
                if (moveAction||developerMode) {
                    location.searching(screen);
                    moveAction = false;
                }
                else{
                    screen.display(name + " has already used up their action point this turn. This character will" +
                            " have to pass their turn to do another action or move to a different direction.");
                    screen.display("\n");
                }

            }
            //Use an artifact on any door
            else if (temp.check("Use")) {
                if(moveAction||developerMode) {
                    String argument = temp.getArgument();
                    useItem(argument);
                    moveAction = false;
                }
                else{
                    screen.display(name + " has already used up their action point this turn. This character will" +
                            " have to pass their turn to do another action or move to a different direction.");
                    screen.display("\n");
                }
            }

            else if (temp.check("Fight") || temp.check("Challenge") || temp.check("Attack")){
                if(moveAction||developerMode){
                    String argument = temp.getArgument();
                    Character victim = location.findCharacter(argument);
                    if(victim.equals(this)){
                        screen.display("You can't kill yourself.");
                        screen.display("\n");
                        continue;
                    }
                    else if(victim.equals(null)){
                        screen.display(argument + " is not in the room or does not exists.");
                        screen.display("\n");
                        continue;
                    }

                    //Lose Health when the character gets attacked.
                    int attackValue;
                    Artifact artTemp = offhand;
                    if(artTemp == null){
                        attackValue = 1;
                    }
                    else{
                        attackValue = artTemp.getAttackValue();
                    }
                    boolean death = victim.loseHealth(attackValue);
                    if(death){
                        location.removeCharacter(victim);
                    }
                }
            }
            else if(temp.check("GUI")){
                String argument = temp.getArgument();
                int choice = Integer.valueOf(argument);
                screen.selectInterface(choice);
            }
            //This should be a cardinal direction if we got here.
            else{
                if(directionAction||developerMode){
                    //Grabbing the place where we began.
                    Place old = location;
                    location = location.followDirection(temp.getArgument(), screen);

                    //Making sure NPCs don't go to exit or nowhere.
                    if (type.equalsIgnoreCase("NPC")) {
                        if (location.getID() == 1 || location.getID() == 0) {
                            location = old;
                        }
                    }

                    //If we actually did move somewhere
                    if (location != old) {
                        directionAction = false;
                        old.removeCharacter(this);
                        location.addCharacter(this);
                    }
                }
                else{
                    //Can't move twice.
                    screen.display(name + " has already moved somewhere this turn. This character will have to " +
                            "pass their turn to make another move or use an action.");
                    screen.display("\n");
                }

            }
        }
        screen.display("----------------------------------------------------");
        screen.display("\n");
    }

    public boolean checkName(String input){
        if(input.equalsIgnoreCase(name)){
            return true;
        }
        else{
            return false;
        }
    }
    //Unused method
    /*
    public Character randomCharacter(){
        Random rand = new Random();
        int number = rand.nextInt(characterIDList.size());
        number = number - 1;
        number = characterIDList.get(number);
        Character temp = getCharacterID(number);
        return temp;
    }
    */
}
