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

public class Move{
    //Variables
    private MoveType type;
    private String argument;

    //Enum Class for MoveType. (Commands)
    public enum MoveType{

        //Every field that the enum can have.
        GUI("GUI"),
        Drop("Drop"),
        Display("Display"),
        Exit("Exit"),
        Fight("Fight"),
        Get("Get"),
        Help("Help"),
        Inventory("Inventory"),
        Look("Look"),
        Move("Move"),
        Pass("Pass"),
        Print("Print"),
        Search("Search"),
        Use("Use"),
        Unknown("Unknown");

        //Variables
        private String text;

        //Return the text.
        MoveType(String text){
            this.text = text;
        }

        //Checking the input and seeing if it matches the text or abbreviation.
        public boolean match(String input){
            if(input.equalsIgnoreCase(text)){
                return true;
            }
            else{
                return false;
            }
        }

        @Override
        //Getter function for text
        //Getter function for text
        public String toString(){
            return text;
        }
    }

    //Move Constructor
    public Move(String text, String argument){
        for(MoveType curr: MoveType.values()){
            if(curr.match(text)){
                this.type = curr;
                this.argument = argument;
                return;
            }
        }
    }

    //Checking if the string matches any of the types.
    boolean check(String input){
        if(type.match(input)){
            return true;
        }
        else{
            return false;
        }
    }

    //Grab the argument string.
    public String getArgument(){
        return argument;
    }
}