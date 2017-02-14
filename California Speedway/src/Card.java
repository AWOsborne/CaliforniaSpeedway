
//Author: John M. Hunt
//This file models a deck of cards as an enum

import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public enum Card
{
	AceClubs("Ace of Clubs", 00, "images/clubs/ace of clubs.gif"),
	TwoClubs("Two of Clubs", 10, "images/clubs/2 of clubs.gif"),
	ThreeClubs("Three of Clubs", 20, "images/clubs/3 of clubs.gif"),
	FourClubs("Four of Clubs", 30, "images/clubs/4 of clubs.gif"),
	FiveClubs("Five of Clubs", 40, "images/clubs/5 of clubs.gif"),
	SixClubs("Six of Clubs", 50, "images/clubs/6 of clubs.gif"),
	SevenClubs("Seven of Clubs", 60, "images/clubs/7 of clubs.gif"),
	EightClubs("Eight of Clubs", 70, "images/clubs/8 of clubs.gif"),
	NineClubs("Nine of Clubs", 80, "images/clubs/9 of clubs.gif"),
	TenClubs("Ten of Clubs", 90, "images/clubs/10 of clubs.gif"),
	JackClubs("Jack of Clubs", 100, "images/clubs/jack of clubs.gif"),
	QueenClubs("Queen of Clubs", 110, "images/clubs/queen of clubs.gif"),
	KingClubs("King of Clubs", 120, "images/clubs/king of clubs.gif"),

	AceDiamonds("Ace of Clubs", 01, "images/diamonds/ace of diamonds.gif"),
	TwoDiamonds("Two of Diamonds", 11, "images/diamonds/2 of diamonds.gif"),
	ThreeDiamonds("Three of Diamonds", 21, "images/diamonds/3 of diamonds.gif"),
	FourDiamonds("Four of Diamonds", 31, "images/diamonds/4 of diamonds.gif"),
	FiveDiamonds("Five of Diamonds", 41, "images/diamonds/5 of diamonds.gif"),
	SixDiamonds("Six of Diamonds", 51, "images/diamonds/6 of diamonds.gif"),
	SevenDiamonds("Seven of Diamonds", 61, "images/diamonds/7 of diamonds.gif"),
	EightDiamonds("Eight of Diamonds", 71, "images/diamonds/8 of diamonds.gif"),
	NineDiamonds("Nine of Diamonds", 81, "images/diamonds/9 of diamonds.gif"),
	TenDiamonds("Ten of Diamonds", 91, "images/diamonds/10 of diamonds.gif"),
	JackDiamonds("Jack of Diamonds", 101, "images/diamonds/jack of diamonds.gif"),
	QueenDiamonds("Queen of Diamonds", 111, "images/diamonds/queen of diamonds.gif"),
	KingDiamonds("King of Diamonds", 121, "images/diamonds/king of diamonds.gif"),

	AceHearts("Ace of Hearts", 02, "images/hearts/ace of hearts.gif"),
	TwoHearts("Two of Hearts", 12, "images/hearts/2 of hearts.gif"),
	ThreeHearts("Three of Hearts", 22, "images/hearts/3 of hearts.gif"),
	FourHearts("Four of Hearts", 32, "images/hearts/4 of hearts.gif"),
	FiveHearts("Five of Hearts", 42, "images/hearts/5 of hearts.gif"),
	SixHearts("Six of Hearts", 52, "images/hearts/6 of hearts.gif"),
	SevenHearts("Seven of Hearts", 62, "images/hearts/7 of hearts.gif"),
	EightHearts("Eight of Hearts", 72, "images/hearts/8 of hearts.gif"),
	NineHearts("Nine of Hearts", 82, "images/hearts/9 of hearts.gif"),
	TenHearts("Ten of Hearts", 92, "images/hearts/10 of hearts.gif"),
	JackHearts("Jack of Hearts", 102, "images/hearts/jack of hearts.gif"),
	QueenHearts("Queen of Hearts", 112, "images/hearts/queen of hearts.gif"),
	KingHearts("King of Hearts", 122, "images/hearts/king of hearts.gif"),

	AceSpades("Ace of Spades", 03, "images/spades/ace of spades.gif"),
	TwoSpades("Two of Spades", 13, "images/spades/2 of spades.gif"),
	ThreeSpades("Three of Spades", 23, "images/spades/3 of spades.gif"),
	FourSpades("Four of Spades", 33, "images/spades/4 of spades.gif"),
	FiveSpades("Five of Spades", 43, "images/spades/5 of spades.gif"),
	SixSpades( "Six of Spades", 53, "images/spades/6 of spades.gif"),
	SevenSpades("Seven of Spades", 63, "images/spades/7 of spades.gif"),
	EightSpades("Eight of Spades", 73, "images/spades/8 of spades.gif"),
	NineSpades("Nine of Spades", 83, "images/spades/9 of spades.gif"),
	TenSpades("Ten of Spades", 93, "images/spades/10 of spades.gif"),
	JackSpades("Jack of Spades", 103, "images/spades/jack of spades.gif"),
	QueenSpades("Queen of Spades", 113, "images/spades/queen of spades.gif"),
	KingSpades("King of Spades", 123, "images/spades/king of spades.gif"),

	RedBack("Red Back", 909, "images/back of card red.gif"),
	BlueBack("Blue Back", 919, "images/back of card blue.gif");

	private final String name;
	private final int code;
	private final String path;


	private Card(String name, int code, String path)
	{
		this.name = name;
		this.code = code;
		this.path = path;
	}

	int getCode()
	{
		return code;
	}

	JButton getButton()
	{
		ClassLoader cl = this.getClass().getClassLoader();
		ImageIcon cardImg;
		URL cardUrl = cl.getResource(path);
		if (cardUrl != null)
			cardImg = new ImageIcon(cl.getResource(path), name);
		else
			cardImg = new ImageIcon(path, name);
		JButton cardBtn = new JButton(cardImg);
		cardBtn.setBorder(null);
		return cardBtn;
	}


	String getName()
	{
		return name;
	}


	ImageIcon getIcon()
	{
		ClassLoader cl = this.getClass().getClassLoader();
		ImageIcon cardImg;
		URL cardUrl = cl.getResource(path);
		if (cardUrl != null)
			cardImg = new ImageIcon(cl.getResource(path), name);
		else
			cardImg = new ImageIcon(path, name);
		return cardImg;
	}


	static Card findCard(int code)
	{
		for (Card c : Card.values())
		{
			if (code == c.code)
				return c;
		}
		return null;
	}


	public boolean matchRank(Card c)
	{
		if (code / 10 == c.code / 10)
			return true;
		return false;
	}
}
