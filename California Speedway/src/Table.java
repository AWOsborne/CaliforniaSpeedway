import java.util.Scanner;
import java.util.concurrent.Semaphore;

/**
 * A program for simulating a game of California Speedway.
 * 
 * @author Drew Osborne
 *
 */
public class Table
{
	// Objects used by the program.
	private CardStack[] piles = new CardStack[8];
	private CardStack mainDeck = new CardStack();
	private TableDisplay td = new TableDisplay();
	private Player player1 = new Player(1);
	private Player player2 = new Player(2);
	private CardStack p1Hand = new CardStack();
	private CardStack p2Hand = new CardStack();
	protected Semaphore lock = new Semaphore(1, true);
	protected boolean donePlaying = false;
	private final int size = 52;
	private static int time = 0;


	/**
	 * Constructor
	 */
	public Table()
	{
		createMainDeck();
		createInitialPiles();
		createInitialHands();
		player1.receiveCards(p1Hand);
		player2.receiveCards(p2Hand);
	}


	/**
	 * Adds all cards into a main deck.
	 */
	private void createMainDeck()
	{
		for (int x = 0; x < 13; x++)
		{
			for (int i = 0; i < size / 13; i++)
			{
				mainDeck.push(Card.findCard((x * 10) + i));
				mainDeck.shuffle();
			}
		}
	}


	/**
	 * creates initial piles of cards and fills them with one card each.
	 */
	private void createInitialPiles()
	{
		for (int i = 0; i < piles.length; i++)
		{
			piles[i] = new CardStack();
			Card temp = mainDeck.pop();
			piles[i].push(temp);
			td.setCard(i, temp.getCode());
		}
	}


	/**
	 * Splits the remaining cards into two equal groups.
	 */
	private void createInitialHands()
	{
		int length = mainDeck.getLength() / 2;
		for (int i = 0; i < length; i++)
		{
			Card st1 = mainDeck.pop();
			Card st2 = mainDeck.pop();
			p1Hand.push(st1);
			p2Hand.push(st2);
		}
	}


	/**
	 * runs the threads.
	 */
	public void doIt()
	{
		td.setVisible(true);
		player1.start();
		player2.start();

		try
		{
			player1.join();
			player2.join();
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	/**
	 * Fixes a deadlock if it occurs by redistributing the cards based on which
	 * side of the board they are on.
	 */
	public void fixDeadlock()
	{
		// Outputs what is happening for first player.
		td.makeReport(player1.number, " combines stacks" + "\n");

		// Combines top row of cards into player hand.
		for (int i = 0; i < piles.length / 2; i++)
		{
			player1.addStack(piles[i]);
			td.setCard(i, 909);
		}
		// Shuffles the players hand.
		player1.shuffleHand();
		// plays four cards on the top row.
		for (int i = 0; i < piles.length / 2; i++)
		{
			Card temp = player1.playCard();
			piles[i].push(temp);
			td.setCard(i, temp.getIcon());
		}
		// Resets the deadlock count.
		player1.setDeadlockCount(0);

		// Outputs what is happening for second player.
		td.makeReport(player2.number, " combines stacks" + "\n");

		// Combines top row of cards into player hand.
		for (int i = 4; i < piles.length; i++)
		{
			player2.addStack(piles[i]);
			td.setCard(i, 919);
		}
		// Shuffles the player hand.
		player2.shuffleHand();
		// plays four cards on the bottom row.
		for (int i = 4; i < piles.length; i++)
		{
			Card temp = player2.playCard();
			piles[i].push(temp);
			td.setCard(i, temp.getIcon());
		}
		// Resets the deadlock count.
		player2.setDeadlockCount(0);
	}

	/**
	 * Main player class.
	 * 
	 * @author Drew Osborne
	 *
	 */
	public class Player extends Thread
	{
		private int number;
		private CardStack hand;
		private int deadlockCount = 0;
		private boolean won = false;


		/**
		 * Constructor
		 * 
		 * @param number
		 */
		public Player(int number)
		{
			this.number = number;
		}


		/**
		 * 
		 * @param cardStack
		 */
		public void addStack(CardStack cardStack)
		{
			hand.combineStacks(cardStack);
		}


		/**
		 * plays the topmost card.
		 * 
		 * @return
		 */
		public Card playCard()
		{
			return hand.pop();
		}


		/**
		 * sets the hand to the initial hand.
		 * 
		 * @param newCards
		 */
		public void receiveCards(CardStack newCards)
		{
			hand = newCards;
		}


		/**
		 * Shuffles the players hand.
		 */
		public void shuffleHand()
		{
			hand.shuffle();
		}


		/**
		 * Sets the deadlock counter for each player.
		 * 
		 * @param i
		 */
		public void setDeadlockCount(int i)
		{
			deadlockCount = i;
		}


		/**
		 * Looks at the value of the topmost card.
		 * 
		 * @param tempCards
		 * @return
		 */
		public Card look(Card[] tempCards)
		{
			return hand.pop();
		}


		/**
		 * The Thread
		 */
		public void run()
		{
			try
			{
				/**
				 * done playing condition.
				 */
				while (!donePlaying)
				{
					// Checks if a players hand in empty.
					if (hand.getLength() == 0)
					{
						donePlaying = true;
						won = true;
					}

					// Reports the winner.
					if (won)
					{
						Thread.sleep(100);
						td.makeReport(1, " Player " + number + " has won");
						td.makeReport(2, " Player " + number + " has won");
						for (int x = 0; x < piles.length; x++)
						{
							if (number == 1)
								td.setCard(x, 909);
							else
								td.setCard(x, 919);
						}
					}

					// Initializes the deadlockCount.
					deadlockCount = 0;

					// Loops through all stacks for each stack and checks if the
					// cards are a match.
					for (int x = 0; x < piles.length; x++)
					{
						for (int y = 0; y < piles.length; y++)
						{
							Card temp = piles[x].checkCode(0);
							// Checks that it is not looking in the same stack.
							if (x != y)
							{
								// Plays the two cards.
								lock.acquire();
								if (temp.matchRank(piles[y].checkCode(0)))
								{
									Card temp1 = hand.pop();
									Card temp2 = hand.pop();

									if (donePlaying)
									{
										break;
									}

									if (temp1 != null)
									{
										td.makeReport(number, " plays " + temp1.getName() + " on pile " + (x + 1) + "\n");
										piles[x].push(temp1);
										td.setCard(x, temp1.getCode());
									}

									if (temp2 != null)
									{
										td.makeReport(number, " plays " + temp2.getName() + " on pile " + (y + 1) + "\n");
										piles[y].push(temp2);
										td.setCard(y, temp2.getCode());
									}
									Thread.sleep(time);
									lock.release();
								}
								else
								{
									// If a match is not found, increases the
									// DeadlockCount.
									lock.release();
									deadlockCount++;
								}
							}
						}
					}

					// If deadlockCount goes above stacks^2, assumes deadlock and
					// fixes.
					if (deadlockCount >= 56)
					{
						if (donePlaying)
						{
							break;
						}

						lock.acquire();
						fixDeadlock();
						lock.release();
					}
				}
				Thread.yield();
			}
			catch (InterruptedException ie)
			{
				ie.printStackTrace();
			}
		}

	}

	/**
	 * Main method of program.
	 * 
	 * @param args
	 */
	public static void main(String args[])
	{
		Scanner kb = new Scanner(System.in);
		while (time < 1)
		{
			System.out.println("How many miliseconds would you like have between card placement?");
			System.out.println("Must be greater than 0");
			time = kb.nextInt();
		}
		kb.close();

		Table table = new Table();
		table.doIt();
	}
}
