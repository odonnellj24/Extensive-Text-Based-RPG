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
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class GUI_C extends JFrame implements UserInterface{
    Button trigger;
    Frame GUI;
    TextArea output;
    TextArea input;
    Panel result;

    GUI_C(){
        GUI = new Frame();
        output = new TextArea(1, 1);
        trigger = new Button("Submit");
        result = new Panel(new GridLayout(1,1, 10,10));
        input = new TextArea(1,1);


        output.setEditable(false);
        result.add(output);
        result.add(input);
        result.add(trigger);

        trigger.addActionListener(new MyListener());

        GUI.add(result);

        GUI.setSize(500,1000);
        GUI.setVisible(true);

    }

    public String getLine(){
        return "";
    }
    public void display(String text){
        output.append(text);
        return;
    }


    class MyListener implements ActionListener{

        public void actionPerformed(ActionEvent e){
            String s = e.getActionCommand();
            if (s.equalsIgnoreCase("submit")) {
                // set the text of the label to the text of the field
                input.getText();
            }
        }
    }

}
class WindowCloser extends WindowAdapter{

    public void WindowClosing(WindowEvent e){
        System.exit(0);
    }
}