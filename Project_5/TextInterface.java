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
public class TextInterface implements UserInterface{

    public void display(String text){
        System.out.print(text);
    }

    public String getLine(){
        Scanner cin = KeyboardScanner.getKeyboardScanner();
        String input = cin.nextLine();
        return input;
    }
}
