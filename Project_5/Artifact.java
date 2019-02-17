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
public class Artifact{

    //Variables
    private String name;
    private String desc;
    private int value;
    private int weight;
    private int keyPattern;
    private int origin;
    private int ID;
    private int attackValue;

    //Constructor for Artifacts
    public Artifact(Scanner file, String filetype){
        String current;
        while(true){
            current = file.nextLine();
            //Parse through all the comments.
            if (current.length() == 0 || current.startsWith("//") || current.startsWith("/*")) {
                continue;
            }
            else {
                //Place ID
                int origintemp = Integer.valueOf(current);
                if(filetype.equalsIgnoreCase("3.1")) {
                    if(origintemp >= 0){
                        this.origin = origintemp;
                    }
                    else{
                        System.out.println("Origin can not be a negative number in the " +  filetype + " version.");
                        System.exit(0);
                    }
                }
                else{
                    this.origin = origintemp;
                }


                //System.out.println(this.origin);
                current = file.nextLine();

                //Split up string into appropriate tokens and use data.
                StringTokenizer tokens = new StringTokenizer(current);
                int counter = 0;

                //Go through each individual token and assign them to their variables.
                while(tokens.hasMoreTokens()){
                    switch(counter){
                        //Artifact ID
                        case 0:
                            this.ID = Integer.valueOf(tokens.nextToken());
                            break;
                        //Artifact value
                        case 1:
                            this.value = Integer.valueOf(tokens.nextToken());
                            break;
                        //Artifact Weight
                        case 2:
                            this.weight = Integer.valueOf(tokens.nextToken());
                            break;
                        //Artifact KeyPattern
                        case 3:
                            this.keyPattern = Integer.valueOf(tokens.nextToken());
                            break;
                        //Artifact Attack Value
                        case 4:
                            if((filetype.equalsIgnoreCase("5.1"))) {
                                int result = Integer.valueOf(tokens.nextToken());
                                if (result > 0) {
                                    this.attackValue = result;
                                } else {
                                    this.attackValue = 0;
                                }
                                break;
                            }

                        //Artifact Name
                        default:
                            String temp = "";
                            while(tokens.hasMoreTokens()){
                                temp = temp + " " + tokens.nextToken();
                            }
                            if(temp.contains("//")){
                                temp = temp.substring(0, temp.indexOf("//"));
                            }
                            temp = temp.trim();
                            this.name = temp;
                            break;
                    }
                    counter++;
                }
                current = file.nextLine();

                //Grab Description of Artifact
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
    }

    //Returning the value of the artifact
    public int value(){
        return value;
    }

    public int getKeyPattern(){
        return keyPattern;
    }

    //Returning the weight of the artifact
    int size(){
        return weight;
    }

    //Returning the name of the artifact
    public String name(){
        return name;
    }

    //Returning the description of the artifact
    public String description(){
        return desc;
    }
    public int origin(){
        return origin;
    }

    public int getAttackValue(){
        return attackValue;
    }

    //Use the artifact/keys
    public void use(Character user, Place location, IO screen){

        //Check if the item is a key or not.
        if(getKeyPattern() > 0){
            //Use the key
            location.useKey(this, screen);
        }
        else{
            if(this.name.equalsIgnoreCase("Leather Bag")){
                screen.display("You increase your capacity due to the Leather Bag.");
                screen.display("\n");
                user.capacity = 20;
            }
        }
    }

    //Checking the name of the artifact and making sure the user has that artifact in his inventory.
    public boolean checkName(String item){
        if(item.equalsIgnoreCase(name)){
            return true;
        }
        return false;
    }

    //Printing for debugging purposes.
    public void print(IO screen){
        screen.display("Name: " + name);
        screen.display("\n");
        screen.display("Description: " + desc);
        screen.display("\n");
        screen.display("Value: " + value);
        screen.display("\n");
        screen.display("Weight: " + weight);
        screen.display("\n");
        screen.display("Damage: " + attackValue);
        screen.display("\n");
        screen.display("Key Pattern: " + keyPattern + "\n");
        screen.display("\n");

    }
}
