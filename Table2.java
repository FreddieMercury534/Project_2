//package Proj2;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
/**
*	This GUI assumes that you are using a 52 card deck and that you have 13 sets in the deck.
*	The GUI is simulating a playing table
	@author Patti Ordonez
	The class is now called Table2 because I renamed it temporarily to try out some changes without sending everything pa'l carajo and now
	I'm too lazy to change it back.

	I added a Set array that keeps track of all the sets that have been layed down and whether they're full or not; and a JButton to start or 
	quit the game; a Text field keeping track of the action to take and two buttons (one for each player) to finish their respective turn
*/
public class Table2 extends JFrame implements ActionListener
{
	final static int numDealtCards = 9;
	JPanel player1;
	JPanel player2;
	JPanel deckPiles;
	JLabel deck;
	JLabel stack;
	JList p1HandPile;
	JList p2HandPile;

	Deck cardDeck;
	Pile stackDeck;
	Hand p1H;
	Hand p2H;

	Set[] layed = new Set[13]; //sets that have been layed down

	SetPanel [] setPanels = new SetPanel[13];
	JLabel topOfStack;
	JLabel deckPile;
	JButton p1Stack;
	JButton p2Stack;

	JButton p1Deck;
	JButton p2Deck;

	JButton p1Lay;
	JButton p2Lay;

	JButton p1LayOnStack;
	JButton p2LayOnStack;

	JButton p1Turn;
	JButton p2Turn;

	JButton startButton;
	orgaPanel orga;
	JLabel status;

	DefaultListModel p1Hand;
	DefaultListModel p2Hand;

	int action;
	int turn;
	int compPlayers;
	int start;
    




	// ****************** setup ****************** //d
	
	/**
	 * Table constructor; sets up GUI and initiates hands & stack/deck
	 * @param sstack input stack that will be copied to be the table's stack
	 * @param ddeck same as stack but with deck (the deck that has also been used to deal the input hands)
	 * @param pp1 input hand of player 1, has been set up beforehand
	 * @param pp2 same as with player 1 but with player 2
	 * @param no number of automatic players needed
	 */
	public Table2(Pile sstack, Deck ddeck, Hand pp1, Hand pp2, int no)
	{
		super("The Card Game of the Century");
		start = 0;

		setLayout(new BorderLayout());
		setSize(1200,700);


		cardDeck = ddeck;
		stackDeck = sstack;

		p1H = pp1;
		p2H = pp2;

		for (int i = 0; i < 13; i++){ // set up the Sets (so far empty), will be used later to check if a single card can be layed
			layed[i] = new Set(Card.rank[i]); 
		}

		JPanel top = new JPanel();

		for (int i = 0; i < Card.rank.length;i++)
			setPanels[i] = new SetPanel(Card.getRankIndex(Card.rank[i]));


		top.add(setPanels[0]);
		top.add(setPanels[1]);
		top.add(setPanels[2]);
		top.add(setPanels[3]);

		player1 = new JPanel();

		player1.add(top);




		add(player1, BorderLayout.NORTH);
		JPanel bottom = new JPanel();


		bottom.add(setPanels[4]);
		bottom.add(setPanels[5]);
		bottom.add(setPanels[6]);
		bottom.add(setPanels[7]);
		bottom.add(setPanels[8]);

		player2 = new JPanel();




		player2.add(bottom);
		add(player2, BorderLayout.SOUTH);


		JPanel middle = new JPanel(new GridLayout(1,3));

		p1Stack = new JButton("Stack");
		p1Stack.addActionListener(this);
		p1Deck = new JButton("Deck ");
		p1Deck.addActionListener(this);
		p1Lay = new JButton("Lay  ");
		p1Lay.addActionListener(this);
		p1LayOnStack = new JButton("LayOnStack");
		p1LayOnStack.addActionListener(this);
		p1Turn = new JButton("FinishedTurn");
		p1Turn.addActionListener(this);


		Card [] cardsPlayer1 = new Card[numDealtCards + 1];

		for(int i = 0; i < cardsPlayer1.length; i++){
			cardsPlayer1[i] = pp1.getCard(i);
		}

		p1Hand = new DefaultListModel();
		for(int i = 0; i < cardsPlayer1.length; i++)
			p1Hand.addElement(cardsPlayer1[i]);
		p1HandPile = new JList(p1Hand);


		middle.add(new HandPanel("Player 1", p1HandPile, p1Stack, p1Deck, p1Lay, p1LayOnStack, p1Turn));

		deckPiles = new JPanel();
		deckPiles.setLayout(new BoxLayout(deckPiles, BoxLayout.Y_AXIS));
		deckPiles.add(Box.createGlue());
		JPanel left = new JPanel();
		left.setAlignmentY(Component.CENTER_ALIGNMENT);

		status = new JLabel("Draw a card from Deck or Stack");
		status.setAlignmentY(Component.CENTER_ALIGNMENT);

		startButton = new JButton("Start game");
		startButton.addActionListener(this);

		orga = new orgaPanel("Press START to begin!", startButton);
		middle.add(orga);


		stack = new JLabel("Stack");
		stack.setAlignmentY(Component.CENTER_ALIGNMENT);

		left.add(stack);
		topOfStack = new JLabel();
		topOfStack.setIcon(new ImageIcon(Card.directory + "blank.gif"));
		topOfStack.setAlignmentY(Component.CENTER_ALIGNMENT);
		left.add(topOfStack);
		deckPiles.add(left);
		deckPiles.add(Box.createGlue());

		JPanel right = new JPanel();
		right.setAlignmentY(Component.CENTER_ALIGNMENT);

		deck = new JLabel("Deck");

		deck.setAlignmentY(Component.CENTER_ALIGNMENT);
		right.add(deck);
		deckPile = new JLabel();
		deckPile.setIcon(new ImageIcon(Card.directory + "b.gif"));
		deckPile.setAlignmentY(Component.CENTER_ALIGNMENT);
		right.add(deckPile);
		deckPiles.add(right);
		deckPiles.add(Box.createGlue());
		middle.add(deckPiles);


		p2Stack = new JButton("Stack");
		p2Stack.addActionListener(this);
		p2Deck = new JButton("Deck ");
		p2Deck.addActionListener(this);
		p2Lay = new JButton("Lay  ");
		p2Lay.addActionListener(this);
		p2LayOnStack = new JButton("LayOnStack");
		p2LayOnStack.addActionListener(this);
		p2Turn = new JButton("FinishedTurn");
		p2Turn.addActionListener(this);


		Card [] cardsPlayer2 = new Card[numDealtCards];
		for(int i = 0; i < cardsPlayer2.length; i++){
			cardsPlayer2[i] = pp2.getCard(i);
		}
		p2Hand = new DefaultListModel();

		for(int i = 0; i < cardsPlayer2.length; i++)
			p2Hand.addElement(cardsPlayer2[i]);

		p2HandPile = new JList(p2Hand);

		middle.add(new HandPanel("Player 2", p2HandPile, p2Stack, p2Deck, p2Lay, p2LayOnStack, p2Turn));

		add(middle, BorderLayout.CENTER);


		JPanel leftBorder = new JPanel(new GridLayout(2,1));


		setPanels[9].setLayout(new BoxLayout(setPanels[9], BoxLayout.Y_AXIS));
		setPanels[10].setLayout(new BoxLayout(setPanels[10], BoxLayout.Y_AXIS));
		leftBorder.add(setPanels[9]);
		leftBorder.add(setPanels[10]);
		add(leftBorder, BorderLayout.WEST);

		JPanel rightBorder = new JPanel(new GridLayout(2,1));

		setPanels[11].setLayout(new BoxLayout(setPanels[11], BoxLayout.Y_AXIS));
		setPanels[12].setLayout(new BoxLayout(setPanels[12], BoxLayout.Y_AXIS));
		rightBorder.add(setPanels[11]);
		rightBorder.add(setPanels[12]);
		add(rightBorder, BorderLayout.EAST);


		// at first it is nobody's turn & the first action to be done is to lay down a card
		turn = 0;
		action = 1;
		compPlayers = no;

		// make sure the last card on the deck is being turned over for the stack
		Card card = cardDeck.dealCard();
		stackDeck.push(card);
		topOfStack.setIcon(card.getCardImage());

		System.out.println("Player 1's hand now: " + p1H.toString() + "\nPlayer 2's hand now: " + p2H.toString());
		
		System.out.println("Press START to begin!"); 

		start = 42;
    }
    




    // ****************** reaction method ****************** //

	/**
	 * method that controls the actions that will be accepted depending on the ActionEvent e; controls which functions will be called in response.
	 * 
	 * first it will check if the start Button is being clicked; then if the turn is being changed.
	 */
	public void actionPerformed(ActionEvent e)
	{
		Object src = e.getSource();

		// start or quit game
		if(src == startButton){
			if(start == 42){
				setTurn(1);
				startButton.setText("Quit game");
				orga.setText("Draw a card from Deck or Stack");
				start = 69; // after the game has started it turns into the end button
			} else {
				this.dispose(); // end the game
			}
		}		

        // check if turns were finished
		if(src == p1Turn){
			if(turn == 1){
				System.out.println("Player 1's hand now: " + p1H.toString() + "\n Player 2's turn: ");
				setTurn(2);
				action = 1;
			} else {
				System.out.println("It's not Player 1's Turn");
			}
		} else if (src == p2Turn){
			if(turn == 2){
				System.out.println("Player 2's hand now: " + p2H.toString() + "\n Player 1's turn: ");
				setTurn(1);
				action = 1;
			} else {
				System.out.println("It's not Player 2's Turn");
			}
		}

        // depending on whose turn it is, accept move or not
		if(turn == 1){
			if(p2Deck == src || p2Lay == src || p2Stack == src || p2LayOnStack == src){
				System.out.println("It is not Player 2's turn.");

			} else if(action == 1){
				if(p1Deck == src){
					p1Deck();
					orga.setText("Form a set or lay a card on Stack");

				} else if (p1Stack == src){
					p1Stack();
					orga.setText("Form a set or lay a card on Stack");

				} else {
					
				}
			} else if (action >= 2){
				if (p1LayOnStack == src){
					p1LayOnStack();
					orga.setText("Finish Turn");
				} else if (action == 3 && p1Lay == src){
					p1Lay();
					orga.setText("Discard a card to the stack");
				} else {
					System.out.println("move not accepted");
				}
			}
            

		} else if(turn == 2){
			if(p1Deck == src || p1Stack == src || p1Lay == src || p1LayOnStack == src){
				System.out.println("It is not Player 1's turn.");
			} else if(action == 1){
				if(p2Deck == src){
					p2Deck();
					orga.setText("Form a set or lay a card on Stack");

				} else if (p2Stack == src){
					p2Stack();
					orga.setText("Form a set or lay a card on Stack");

				} else {
					
				}

			} else if (action >= 2){
				if (p2LayOnStack == src){
					p2LayOnStack();
					orga.setText("Finish Turn");
				} else if (action == 3 && p2Lay == src){
					p2Lay();
					orga.setText("Discard a card to the stack");
				} else {
					System.out.println("move not accepted");
				}
			}
		}else { 
			System.out.println("Start game first!");
		}
	}



    
    // ****************** helper methods ****************** //


	public void layCard(Card card)
	{
		char rank = card.getRank();
		char suit = card.getSuit();
		int suitIndex =  Card.getSuitIndex(suit);
		int rankIndex =  Card.getRankIndex(rank);
		//setPanels[rankIndex].array[suitIndex].setText(card.toString());
		System.out.println("\tlaying " + card);
		setPanels[rankIndex].array[suitIndex].setIcon(card.getCardImage());

		layed[rankIndex].addCard(card);

	}


    /**
	 * After deck is empty, compare hands to figure out the winner!
	 */
	public void wincheck(){
		int p1_val = p1H.evaluateHand();
		int p2_val = p2H.evaluateHand();

		System.out.println("\nPoints: " + p1_val + " to " + p2_val);

		int comp = p1H.compareTo(p2H);

		if(comp > 0 ){
			System.out.println(" Player 2 wins!");
		} else if(comp < 0){
			System.out.println(" Player 1 wins!");
		} else {
			System.out.println(" It's a tie!");
		}

		this.dispose(); // close window and end game
	}
	
	// set turn and automatically check if autoplay needs to be called
	public void setTurn(int t) {
		if((compPlayers == 1 && t == 2) || (compPlayers == 2 && t == 2)){
			autoPlay(2);

		} else if(compPlayers == 2 && t == 1){
			autoPlay(1);

		} else {
			this.turn = t;
		}
	}

	// controls automatic players
	public void autoPlay(int t){
		if(t == 1){
			p1H.play(this, 1);
			System.out.println("Player 2's turn");
			setTurn(2);
		} else if (t == 2){
			p2H.play(this, 2);
			System.out.println("Player 1's turn");
			setTurn(1);
		}
	}

	// has to be fixed, sort the hand that is given in the arguments
	public DefaultListModel outputSort(Hand playerHand){
		playerHand.sort();

		Card [] cardsPlayer = new Card[numDealtCards];
		for(int i = 0; i < cardsPlayer.length; i++){
			cardsPlayer[i] = playerHand.getCard(i);
		}

		DefaultListModel handOutput = new DefaultListModel();
		handOutput.clear();

		for(int i = 0; i < cardsPlayer.length; i++)
			handOutput.addElement(cardsPlayer[i]);

		return handOutput;
	}
    

    // functions for all actions so that it is a little easier to see whats going on up there
    public void p1Deck(){
        Card card = cardDeck.dealCard();

		System.out.println(p1H.toString());
        if (card != null){
			p1Hand.addElement(card);
			p1H.addCard(card);

		}
		
        if(cardDeck.getSizeOfDeck() == 0){
			deckPile.setIcon(new ImageIcon(Card.directory + "blank.gif"));
			System.out.println("No cards left in deck.");
			wincheck();
		}
		System.out.println("\t from Deck: " + card.toString());		
		
		action = 3;
    }

    public void p2Deck(){
        Card card = cardDeck.dealCard();

        if (card != null){
			p2Hand.addElement(card);
		}
		
        if(cardDeck.getSizeOfDeck() == 0){
			deckPile.setIcon(new ImageIcon(Card.directory + "blank.gif"));
			
			wincheck();
		}

		System.out.println("\t from Deck: " + card.toString());	

		action = 3;

		//p2Hand = outputSort(p2H);
    }

    public void p1Stack(){
        Card card = stackDeck.pop();
					
        if(card != null){
            Card topCard = stackDeck.top();
            if (topCard != null)
                topOfStack.setIcon(topCard.getCardImage());
            else
                topOfStack.setIcon(new ImageIcon(Card.directory + "blank.gif"));
			 p1Hand.addElement(card);
			 p1H.addCard(card);						
		}

		System.out.println("\t from Stack: " + card.toString());	

		action = 3;

		//p1Hand = outputSort(p1H);
    }

    public void p2Stack(){
        Card card = stackDeck.pop();
					
        if(card != null){
            Card topCard = stackDeck.top();
            if (topCard != null)
                topOfStack.setIcon(topCard.getCardImage());
            else
                topOfStack.setIcon(new ImageIcon(Card.directory + "blank.gif"));
		 	p2Hand.addElement(card);						
		} 

		System.out.println("\t from Stack: " + card.toString());

		action = 3;

		//p2Hand = outputSort(p2H);
    }

    public void p1Lay(){
        boolean test = true;
		Object [] cards = p1HandPile.getSelectedValues();
        if (cards != null){
			Card card_zero = (Card)cards[0];
			char rank = card_zero.getRank();
			for (int i = 0; i < cards.length; i++){
				Card card = (Card)cards[i];
				if(card.getRank() != rank){
					test = false;
					break;
                }
			}

			action = 2;
		
			if(test && cards.length <= 4 && cards.length > 2){
				//System.out.println("accepted Set");
            	for(int i = 0; i < cards.length; i++)
            	{
            	    Card card = (Card)cards[i];
            	    layCard(card);
					p1Hand.removeElement(card);
					p1H.removeCard(card);
				}

				if(p1Hand.isEmpty()){
					System.out.println("Player 1 wins, got rid of all cards!");
					this.dispose();
				}
			} else if (cards.length == 1 && !layed[Card.getRankIndex(rank)].isEmpty()) {
				layCard(card_zero);

				p1Hand.removeElement(card_zero);
				p1H.removeCard(card_zero);

				if(p1Hand.isEmpty()){
					System.out.println("Player 1 wins, got rid of all cards!");
					this.dispose();
				}
			} else {
				System.out.println("Selected cards do not have the same rank");
				action = 3;
            }
		} else {
			
		}

		//p1Hand = outputSort(p1H);
    }

    public void p2Lay(){
        boolean test = true;
		Object [] cards = p2HandPile.getSelectedValues();
        if (cards != null){
			Card card_zero = (Card)cards[0];
			char rank = card_zero.getRank();
			for (int i = 0; i < cards.length; i++){
				Card card = (Card)cards[i];
				if(card.getRank() != rank){
					test = false;
					break;
                }
			}

			action = 2;
		
			if(test && cards.length <= 4 && cards.length > 2){
				System.out.println("accepted Set");
            	for(int i = 0; i < cards.length; i++)
            	{
            	    Card card = (Card)cards[i];
            	    layCard(card);
					p2Hand.removeElement(card);
					p2H.removeCard(card);
				}
				if(p2Hand.isEmpty()){
					System.out.println("Player 2 wins, got rid of all cards!");
					this.dispose();
				}
			} else if (cards.length == 1 && !layed[Card.getRankIndex(rank)].isEmpty()) {
				layCard(card_zero);
				p2Hand.removeElement(card_zero);
				p2H.removeCard(card_zero);

				if(p2Hand.isEmpty()){
					System.out.println("Player 2 wins, got rid of all cards!");
					this.dispose();
				}

			} else {
				System.out.println("Selected cards do not have the same rank");
				action = 3;
            }
		} else {
			
		}

		//p2Hand = outputSort(p2H);
    }

    public void p1LayOnStack(){
        int [] num  = p1HandPile.getSelectedIndices();
        if (num.length == 1)
        {
            Object obj = p1HandPile.getSelectedValue();
            if (obj != null)
            {
                p1Hand.removeElement(obj);
				Card card = (Card)obj;
				p1H.removeCard(card);

                stackDeck.push(card);
				topOfStack.setIcon(card.getCardImage());
				System.out.println("\t discarded to Stack: " + card.toString());
            }
			action = 0;
		}

		if(p1Hand.isEmpty()){
			System.out.println("Player 1 wins, got rid of all cards!");
			this.dispose();
		}

		//p1Hand = outputSort(p1H);
    }

    public void p2LayOnStack(){
        int [] num  = p2HandPile.getSelectedIndices();
        if (num.length == 1)
        {
            Object obj = p2HandPile.getSelectedValue();
            if (obj != null)
            {
				p2Hand.removeElement(obj);
                Card card = (Card)obj;
				p2H.removeCard(card);

                stackDeck.push(card);
                topOfStack.setIcon(card.getCardImage());
				System.out.println("\t discarded to Stack: " + card.toString());
            }
			action = 0;
		}
		if(p2Hand.isEmpty()){
			System.out.println("Player 2 wins, got rid of all cards!");
			this.dispose();
		}

		//p2Hand = outputSort(p2H);
    }


}

class HandPanel extends JPanel
{

	public HandPanel(String name,JList hand, JButton stack, JButton deck, JButton lay, JButton layOnStack, JButton turn)
	{
		//model = hand.createSelectionModel();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//		add(Box.createGlue());
		JLabel label = new JLabel(name);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(label);
		stack.setAlignmentX(Component.CENTER_ALIGNMENT);
//		add(Box.createGlue());
		add(stack);
		deck.setAlignmentX(Component.CENTER_ALIGNMENT);
//		add(Box.createGlue());
		add(deck);
		lay.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(lay);
		layOnStack.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(layOnStack);
		turn.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(turn);
		add(Box.createGlue());
		add(hand);
		add(Box.createGlue());
	}

}
class SetPanel extends JPanel
{
	private Set data;
	JButton [] array = new JButton[4];

	public SetPanel(int index)
	{
		super();
		data = new Set(Card.rank[index]);

		for(int i = 0; i < array.length; i++){
			array[i] = new JButton("   ");
			add(array[i]);
		}
	}

}

/**
 * Organisation panel for text on what to do next & the start/quit button
 */
class orgaPanel extends JPanel
{
	JLabel instructions;

	public orgaPanel(String text, JButton start){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		instructions = new JLabel(text);
		instructions.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(instructions);
		start.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(start);
		add(Box.createGlue()); 
	}

	public void setText(String newText){
		instructions.setText(newText);
	}
}



/* Das ganze Zeug was du noch irgendwie auf die Reihe kriegen musst:

- make console output right (important)
- put it in a package (somewhat important)
- sort hand (somewhat important)

- change output when autoplay (relatively important)

- put comments (do this at the end)

*/