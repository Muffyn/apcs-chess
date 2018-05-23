import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Menu extends JFrame
{
	private JPanel menuPanel;
	
	private String[] names = {"Single Player", "Multiplayer", "Credits", "Exit"};
	private JButton[] buttons = new JButton[names.length];
	
	private JLabel image;
	
	public void drawMenu()
	{
		menuPanel = new JPanel();
		menuPanel.setBounds( 100, 100, 450, 300 );
		
		for(int k = 0; k < names.length; k ++)
		{
			buttons[k] = new JButton(names[k]);
			buttons[k].addActionListener(new ButtonHandler());

			menuPanel.add( buttons[k] );
			buttons[k].setVisible(true);
		}

		add(menuPanel);
		
		image = new JLabel (new ImageIcon("chess.jpg"));
		
		menuPanel.add(image);
		
		image.setVisible(true);
	}
	
	private class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent actionEvent)
		{
			String btnText = "";
			
			for(int x = 0; x < names.length; x++)
			{
				if((JButton)actionEvent.getSource() == buttons[x])
				{
					btnText = names[x];
					break;
					//gets text from button pressed
				}
			}
			
			if(btnText.equals("Single Player"))
			{
				JFrame frame= new JFrame("Chess - Single Player");
				frame.add(new Chess(false));
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setExtendedState(frame.MAXIMIZED_BOTH);
				frame.setVisible(true);
				menuPanel.setVisible(false);
				
			}
			else if(btnText.equals("Multiplayer"))
			{
				JFrame frame= new JFrame("Chess - Multi-Player");
				frame.add(new Chess(true));
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setBounds(100, 100, 450, 300);
				frame.setVisible(true);
				menuPanel.setVisible(false);
			}
			else if(btnText.equals("Exit"))
			{
				System.exit(0);
			}
			else if(btnText.equals("Credits"))
			{
				System.out.print("APCS 2017-18 class");
			}
	
		}
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