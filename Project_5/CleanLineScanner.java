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
public class CleanLineScanner {

    //Sending in a string and parsing the comments part out of the line.
    public static String getCleanLine(String line){

        //Base Check for if the string is less than 0.
        if(line.length() != 0) {
            String data = line.substring(0, line.indexOf("//"));
            data = data.trim();
            return data;
        }
        return null;
    }
}
