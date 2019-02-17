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

public class GUI_M extends JFrame implements UserInterface
{
	JPanel p = new JPanel();
	JButton playGameButton = new JButton("Play Game");
	JTextField inputText = new JTextField("Input text here", 20);
	JButton submitButton = new JButton("Submit");
	JLabel l = new JLabel("Action: ");
	
	String choices[] = 
	{
				"Fight",
				"Get",
				"Drop",
				"Help",
				"Inventory",
				"Move",
				"Pass",
				"Print",
				"Search",
				"Use"
	};
	JComboBox cb = new JComboBox(choices);
	
	GUI_M()
	{
		super("Michelle's GUI");
		
		setSize(400, 300);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		p.add(playGameButton);
		p.add(inputText);
		p.add(submitButton);
		p.add(l);
		p.add(cb);
		add(p);
		
		setVisible(true);
	}
	
    public String getLine()
    {
    	String userInput = inputText.getText();
        return userInput;
    }
    public void display(String text)
    {
    	JTextArea displayText = new JTextArea(text, 5, 20);
    	p.add(displayText);
        return;
    }
}