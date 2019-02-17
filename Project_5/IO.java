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


import javax.swing.JFrame;



public class IO implements UserInterface{
    public static final int TEXT = 0;
    public static final int ConwayD = 1;
    public static final int JackO = 2;
    public static final int MichelleN = 3;
    private UserInterface implementor;


    public IO(){
        selectInterface(0);
    }
    public void display(String text){
        implementor.display(text);
    }
    public String getLine(){
        String result = implementor.getLine();
        return result;
    }

    public void selectInterface(int choice){
        if(choice == 0){
            implementor = new TextInterface();
        }
        else if(choice == 1){
            implementor = new GUI_C();
            
        }
        else if(choice == 2){
            implementor = new GUI_J();
            

        }
        else if(choice == 3){
            implementor = new GUI_M();
        }
    }

}


