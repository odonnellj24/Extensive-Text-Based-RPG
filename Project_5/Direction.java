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
public class Direction{
    private int ID;
    private Place to;
    private Place from;
    private DirType dir;
    private Boolean lockActive;
    private int lockPattern;

    //Private enum class
    private enum DirType {

        //Every field that the enum can have.
        NONE("None", "?"),
        N("North", "N"),
        S("South", "S"),
        E("East", "E"),
        W("West", "W"),
        U("Up", "U"),
        D("Down", "D"),
        NE("Northeast", "NE"),
        NW("Northwest", "NW"),
        SE("Southeast", "SE"),
        SW("Southwest", "SW"),
        NNE("North by Northeast", "NNE"),
        NNW("North by Northwest", "NNW"),
        ENE("East by Northeast", "ENE"),
        WNW("West by Northwest", "WNW"),
        ESE("East by Southeast", "ESE"),
        WSW("West by Southwest", "WSW"),
        SSE("South by Southeast", "SSE"),
        SSW("South by Southwest", "SSW");

        //Variables
        private String text;
        private String abbreviation;

        //Constructor for the enum class.
        DirType(String text, String ab){
            this.text = text;
            this.abbreviation = ab;
        }

        //Getter function for text
        public String toString(){
            return text;
        }

        //Checking the input and seeing if it matches the text or abbreviation.
        public boolean match(String input){
            if(input.equalsIgnoreCase(text) || input.equalsIgnoreCase(abbreviation)){
                return true;
            }
            else{
                return false;
            }
        }
    }


    //Constructor
    public Direction(Scanner file, String filetype) {
        String current;
        while(true) {
            current = file.nextLine();

            //Parse through all the comments.
            if (current.length() == 0 || current.startsWith("//") || current.startsWith("/*")) {
                continue;
            }
            else{
                //First Line
                current = CleanLineScanner.getCleanLine(current);
                //System.out.println(current);

                //Split up string into appropriate tokens and use data.
                StringTokenizer tokens = new StringTokenizer(current);
                int counter = 0;
                int converter;

                //Go through each individual token and assign them to their variables.
                while(tokens.hasMoreTokens()){
                    switch(counter){
                        //Direction ID
                        case 0:
                            converter = Integer.valueOf(tokens.nextToken());
                            if(filetype.equalsIgnoreCase("4.0")) {
                                if (converter < 0) {
                                    System.out.println("Place ID has to be greater than or equal to 0.");
                                    System.exit(0);
                                }
                            }
                            this.ID = converter;
                            break;

                        //Direction from place
                        case 1:
                            converter = Integer.valueOf(tokens.nextToken());
                            if(converter < 0){
                                converter = converter * -1;
                            }
                            this.from = Place.getPlaceID(converter);
                            break;
                        //Direction Type
                        case 2:
                            this.dir = DirType.valueOf(tokens.nextToken());
                            break;
                        //Direction to place
                        case 3:
                            converter = Integer.valueOf(tokens.nextToken());
                            if(converter < 0){
                                this.lockActive = true;
                                converter = converter * -1;
                            }
                            else{
                                this.lockActive = false;
                            }
                            this.to = Place.getPlaceID(converter);
                            break;
                        //Direction Lock Pattern
                        case 4:
                            converter = Integer.valueOf(tokens.nextToken());
                            if(filetype.equalsIgnoreCase("4.0")) {
                                if(converter < 0) {
                                    System.out.println("Lock Pattern has to be greater than or equal to 0.");
                                    System.exit(0);
                                }
                            }
                            this.lockPattern = converter;
                            break;

                    }
                    counter++;

                }
                break;

            }
        }
    }

    public boolean check(String input){
        for(DirType current : DirType.values()){
            if(current.match(input)){
                return true;
            }
        }
        return false;
    }
    //Match Method. Should return true if string passed is any resemblance of a direction.
    public boolean match(String direction){

        //Trimming the input for whitespaces.
        direction = direction.trim();

        //Sending the work to the enum class.
        if(dir.match(direction)){
            return true;
        }
        return false;

    }
    //Return what the lockPattern is depending on the artifact.
    public int getlockPattern(){
        return lockPattern;
    }

    //Using the key to unlock the door.
    public void useKey(Artifact item, IO screen){
        if((item.getKeyPattern() == lockPattern) && (lockActive)){
            screen.display("You hear a click as the key goes into the hole. The path to ");
            screen.display("\n");
            printPlaceToName(screen);
            screen.display(" from ");
            printPlaceFromName(screen);
            screen.display(" is unlocked.\n");
            screen.display("\n");
            this.unlock();
        }
        else{
            //System.out.println("This key doesn't fit for this door.");
        }
        return;
    }

    //Change lock status to locked
    public void lock(){
        this.lockActive = true;
    }

    //Change lock status to unlocked
    public void unlock(){
        this.lockActive = false;
    }

    //Check if the path is sealed or not.
    public boolean isLocked(){
        if(this.lockActive) {
            return true;
        }
        else{
            return false;
        }
    }

    //Have the character follow the direction and to see if they can go there or not.
    public Place follow(){
        if(this.isLocked() == false){
            return this.getPlaceTo();
        }
        else{
            System.out.println("The door is locked! There muse be a key somewhere.");
            return this.getPlacefrom();
        }
    }

    //Print out information for debugging purposes
    public void print(IO screen){
        screen.display("Direction: " + this.dir);
        screen.display("\n");
        screen.display("ID: " + this.ID);
        screen.display("\n");
        screen.display("Place to: ");
        this.printPlaceToName(screen);
        screen.display("Place from: ");
        this.printPlaceFromName(screen);
        screen.display("Lock: " + this.lockActive);
        screen.display("\n");
        screen.display("Lock Pattern is: " + this.lockPattern);
        screen.display("\n");
    }

    //Get the destination place
    public Place getPlaceTo(){
        return to;
    }
    //Get the source place
    public Place getPlacefrom(){
        return from;
    }
    //Get the ID
    public int getID(){
        return ID;
    }

    public String getDirectionName(){
        return this.dir.toString();
    }

    //Print the destination's name
    public void printPlaceToName(IO screen){
        to.printName(screen);
    }
    //Print the source's name
    public void printPlaceFromName(IO screen){
        from.printName(screen);
    }
}
