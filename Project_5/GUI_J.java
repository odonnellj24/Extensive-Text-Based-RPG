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
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI_J extends JFrame implements UserInterface
{
	JPanel p = new JPanel();
	JButton b = new JButton("Input command ->");
	JTextField t = new JTextField("", 20);
	
	JLabel l = new JLabel("Lets play a game!");
	String string = "";

	GUI_J()
	{
		super("Jack's GUI");
		
		setSize(1000, 1000);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		ButtonHandler handler = new ButtonHandler();
        b.addActionListener( handler);
		p.add(b);
		p.add(t);
		//p.add(ta);
		p.add(l);
		
		add(p);
        

		setVisible(true);
	}
	
    public String getLine(){
        String userinput = t.getText();
        JOptionPane.showMessageDialog(t, "whats getting sent:" + t.getText() );
        System.out.println(userinput);
        return userinput;
    }
    public void display(String text){
        System.out.println(text);
        JTextArea aad = new JTextArea(text);
        p.add(aad);
        return;
    }


    private class ButtonHandler implements ActionListener {

        // handle button event
        public void actionPerformed( ActionEvent event )
        {
           JButton temp = (JButton) event.getSource();
           JOptionPane.showMessageDialog( b,
              "You made a move: " + event.getActionCommand() + " " + temp.getText() );
              getLine();
              //display();
        }
  
     } // end private inner class ButtonHandler
     
  
}