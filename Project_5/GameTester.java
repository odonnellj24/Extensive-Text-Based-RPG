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
import java.io.File;
import java.util.*;
public class GameTester {

    public static void main(String[] args) throws Exception{

        //Scanner for basic input
        Scanner user = new Scanner(System.in);

        //Basic Info about me
        System.out.println("Name: Conway Dang");
        System.out.println("NetID: cdang4");
        System.out.println("Name: Jack O'Donnell");
        System.out.println("NetID: jodonn6");
        System.out.println("Name: Michelle Nguyen");
        System.out.println("NetID: mnguye61");

        //Creating a file path to the GDF file and creating a scanner to read the file.
        File file = new File("MystiCity 51.gdf");
        Scanner sc = new Scanner(file);
        
        //Creating the game and playing it
        Game project = new Game(sc);

        //Closing the file scanner and playing the game.
        sc.close();
        project.play();
    }
}
