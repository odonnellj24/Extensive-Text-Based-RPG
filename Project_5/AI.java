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
public class AI implements DecisionMaker{
    //Variables
    private static int runs = 0;
    private boolean title = true;

    //Overriden
    @Override
    //Only Allow the NPCs to move and then pass their turn.
    public Move getMove(Character user, Place currPlace){
        //Direction action.
        if(runs == 0){
            Random rand = new Random();
            int choice = rand.nextInt(currPlace.AIdiffBranches());
            Direction temp = currPlace.AIchosenDirection(choice);
            String command = temp.getDirectionName();
            System.out.print(user.name + " moved " + command);
            runs++;
            return new Move("Unknown", command);
        }
        //Passing their turn.
        else{
            runs = 0;
            title = true;
            return new Move("Pass", "");
        }
    }
}
