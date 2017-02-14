import java.util.ArrayList;
import java.util.Collections;

/**
 * CardStack class to hold cards.
 * 
 * Basically, a fancy ArrayList...
 * 
 * @author Drew Osborne
 *
 */
public class CardStack extends ArrayList<Object>
{
	/**
	 * SerialVersio for whatever reason.
	 * 
	 * ... Can you tell my brain is dead yet?
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Card> stack = new ArrayList<Card>();


	/**
	 * Guess what?!? it does what it says!
	 */
	public Card get(int i)
	{
		return stack.get(i);
	}


	/**
	 * Oh my goodness, so does this one...
	 */
	public void shuffle()
	{
		Collections.shuffle(stack);
	}


	/**
	 * Wow, this one is particularly original.
	 * @return You guessed it! The length.
	 */
	int getLength()
	{
		return stack.size();
	}


	/**
	 * Adds a card to the front of the deck.
	 * 
	 * Sorry back, I like the front better.
	 * @param newCard
	 */
	public void push(Card newCard)
	{
		if (stack.isEmpty())
		{
			stack.add(newCard);
		}
		else
		{
			stack.add(0, newCard);
		}
	}


	/**
	 * Man overboard! We lost one to the sea.
	 * @return
	 */
	public Card pop()
	{
		if (!stack.isEmpty())
		{
			Card temp = stack.get(0);
			stack.remove(0);
			return temp;

		}
		return null;
	}


	/**
	 * You'll never guess with this one...
	 */
	public boolean isEmpty()
	{
		if (stack.size() == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}


	/**
	 * Think barcode scanner for... things.
	 * @param code
	 * @return
	 */
	Card checkCode(int code)
	{
		if (!stack.isEmpty())
		{
			Card c = stack.get(code);
			return c;
		}
		return null;
	}


	/**
	 * It combines them.
	 * @param temp
	 */
	public void combineStacks(CardStack temp)
	{
		for (int i = 0; i < temp.getLength(); i++)
		{
			this.stack.add(temp.pop());
		}
	}


	/**
	 * Adds ALL the cards.
	 */
	public void pushAll()
	{ // Creates a CardStack with all 52 cards

		Card[] fullDeck = Card.values();
		for (int i = 0; i < fullDeck.length - 2; i++)
		{
			stack.add(fullDeck[i]);
		}

		this.shuffle();

	}
}
