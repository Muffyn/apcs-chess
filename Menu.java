
import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Menu extends JFrame
{
	
	private JButton start, credits, settings, exit;
	
	private JPanel menuPanel;
	
	private ActionListener click;
	
	//need side display of pieces that have been removed from the board
	
	public void drawMenu()
	{
		menuPanel = new JPanel();
		menuPanel.setBounds( 100, 100, 450, 300 );
		
		start = new JButton( "Start" );
		credits = new JButton( "Credits" );
		settings = new JButton ( "Settings" );
		exit = new JButton ( "Exit" );
		
		menuPanel.add( start );
		menuPanel.add( credits );
		menuPanel.add( settings );
		menuPanel.add( exit );

		add(menuPanel);
		
		start.addActionListener(click);
		settings.addActionListener(click);
		credits.addActionListener(click);
		exit.addActionListener(click);
		
		start.setVisible(true);
		credits.setVisible(true);
		settings.setVisible(true);
		exit.setVisible(true);
	}
	
	
	
	

	public static void main (String[] args)
	{
		Menu s = new Menu();
		s.drawMenu();
		s.setBounds( 100, 100, 450, 300 );
		s.setTitle("Menu");
		s.setVisible(true);
	}
	
}