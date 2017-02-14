
//Author: John M. Hunt
//This class provides the GUI display for a game of California Speedway
//A scrolling area is provided for each player to track the history of their plays
import java.awt.*;
import javax.swing.*;

public class TableDisplay extends JFrame
{
	private static final long serialVersionUID = 1L;
	JButton cards[] = new JButton[8];
	PlayerReport pr[] = new PlayerReport[2];


	public TableDisplay()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("California Speedway");
		add(new TableTop());
		pr[0] = new PlayerReport(1);
		pr[1] = new PlayerReport(2);
		add(pr[0], BorderLayout.NORTH);
		add(pr[1], BorderLayout.SOUTH);
		pack();
		bottomCorner();
		centerOnScreen();
		// setVisible(true);
	}


	private void centerOnScreen()
	{
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width / 2 - getWidth() / 2, size.height / 2 - getHeight() / 2);
	}


	private void bottomCorner()
	{
		Toolkit toolkit = getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation(size.width - getWidth(), size.height - getHeight());
	}

	private class TableTop extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		TableTop()
		{
			setBackground(Color.GREEN);
			setLayout(new GridLayout(2, 4, 10, 10));

			for (int i = 0; i < 8; i++)
			{
				cards[i] = Card.findCard(909).getButton();
				add(cards[i]);
			}

		}

	}

	private class PlayerReport extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JTextArea reportList;


		PlayerReport(int playerNum)
		{
			reportList = new JTextArea(5, 40);
			reportList.setEditable(false);
			JScrollPane scrollPane = new JScrollPane(reportList);
			JLabel playerLabel = new JLabel("Player " + playerNum);
			playerLabel.setFont(new Font("Serif", Font.BOLD, 24));
			playerLabel.setBackground(Color.BLUE);
			add(playerLabel);
			add(scrollPane);
		}


		public void append(String line)
		{
			reportList.append(line);
		}
	}


	void setCard(int idx, ImageIcon ii)
	{
		cards[idx].setIcon(ii);
	}


	void setCard(int idx, int code)
	{
		setCard(idx, Card.findCard(code).getIcon());
		repaint();
	}


	void makeReport(int playerNum, String rpt)
	{
		pr[playerNum - 1].append(rpt);// player 0 table setup
		repaint();
	}
}
