package tank;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

public class MainField extends JFrame {

	public static void main(String args[]) {
		MainField mainframe = new MainField();
		mainframe.setVisible(true);
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	MainField() {
		setTitle("World of Tanks");
		setSize(500, 500);
		Container con = getContentPane();
		MainPanel mainPanel = new MainPanel();
		
		SubPanel sub = new SubPanel();
		con.add(mainPanel,BorderLayout.CENTER);
		con.add(sub,BorderLayout.EAST);
		pack();
		System.out.println();
	}
}
