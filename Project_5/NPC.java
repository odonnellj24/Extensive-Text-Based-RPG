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
public class NPC extends Character{

    //File Constructor
    public NPC(Scanner file, String version){
        super(file, version, "NPC");
        this.type = "NPC";
    }

    //Non-File Constructor
    public NPC(int ID, String name, String description){
        super(ID, name, description, "NPC");
        this.type = "NPC";
    }
}
