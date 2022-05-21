//package Proj2;
// Hand.java - John K. Estell - 8 May 2003
// last modified: 23 Febraury 2004
// Implementation of a abstract hand of playing cards.
// Uses the Card class.  Requires subclass for specifying
// the specifics of what constitutes the evaluation of a hand
// for the game being implemented.
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

//import javax.smartcardio.Card;


/**
 * Represents the basic functionality of a hand of cards.
 * Extensions of this class will provide the
 * definition of what constitutes a hand for that game and how hands are compared
 * to one another by overriding the <code>compareTo</code> method.
 * @author John K. Estell
 * @version 1.0
 */
public class Hand implements HandInterface {

   protected java.util.List hand = new ArrayList();
   final static char [] ranks = {'a','2','3','4','5','6','7','8','9','t','j','q','k'};
   final static char[] suits = {'c','d','h','s'};


  /**
   * Adds a card to this hand.
   * @param card card to be added to the current hand.
   */
   public void addCard( Card card ) {
      hand.add( card );
   }
   
  /**
   * Searches for the first instance of a set (3 or 4 Cards of the same rank) in the hand.
   * @return  returns Card [] of Cards found in deck or <code>-null </code> if not found.
   */
   public Set findSet( ){
      if (hand.isEmpty()){
         return null;
      }

      Set set = new Set();

      for (char c : ranks){
         Set temp = findSet(c);

         if(temp != null){
            if(set.compareTo(temp) <= 0){ // only accept new set if the value is higher than the old ones
               set = temp;
            }
         }
      }

      if(set.getRank() == '0'){
         return null;
      }
      
	   return set;
   }

   /**
    * Looks for set of one rank in particular
    * @param rank the rank to search
    * @return return found set if it contains more than 2 cards
    */
   private Set findSet(char rank){
      Set set = new Set(rank);

      for (int i = 0; i < hand.size(); i++){
         Card C = getCard(i);
         if (C.getRank() == rank){
            set.addCard(C);
         }
         if (set.isFull()){
            return set;
         }
      }

      if (set.getNumberOfCards() < 3){
         return null;
      }

      return set;
   }

  /**
   * Obtains the card stored at the specified location in the hand.  Does not
   * remove the card from the hand.
   * @param index position of card to be accessed.
   * @return the card of interest, or the null reference if the index is out of
   * bounds.
   */
   public Card getCard( int index ) {
      return (Card) hand.get( index );
   }


  /**
   * Removes the specified card from the current hand.
   * @param card the card to be removed.
   * @return the card removed from the hand, or null if the card
   * was not present in the hand.
   */
   public Card removeCard( Card card ) {
      int index = findCard(card);
      if ( index < 0 )
         return null;
      else
         return (Card) hand.remove( index );
   }


  /**
   * Removes the card at the specified index from the hand.
   * @param index poisition of the card to be removed.
   * @return the card removed from the hand, or the null reference if
   * the index is out of bounds.
   */
   public Card removeCard( int index ) {
      return (Card) hand.remove( index );
   }


  /**
   * Removes all the cards from the hand, leaving an empty hand.
   */
   public void discardHand() {
      hand.clear();
   }


  /**
   * The number of cards held in the hand.
   * @return number of cards currently held in the hand.
   */
   public int getNumberOfCards() {
      return hand.size();
   }


  /**
   * Sorts the card in the hand.
   * Sort is performed according to the order specified in the {@link Card} class.
   */
   public void sort() {
      Collections.sort( hand );
   }


  /**
   * Checks to see if the hand is empty.
   * @return <code>true</code> is the hand is empty.
   */
   public boolean isEmpty() {
      return hand.isEmpty();
   }


  /**
   * Determines whether or not the hand contains the specified card.
   * @param card the card being searched for in the hand.
   * @return <code>true</code> if the card is present in the hand.
   */
   public boolean containsCard( Card card ) {
      return false;
   }


  /**
   * Searches for the first instance of the specified card in the hand.
   * @param card card being searched for.
   * @return position index of card if found, or <code>-1</code> if not found.
   */
   public int findCard( Card card ) {
      int pos = -1;
      for (int i = 0; i < this.getNumberOfCards(); i++){
         if(this.getCard(i) == card){
            return i;
         }
      }
      return pos;
   }


  /**
   *  Compares two hands.
   *  @param otherHandObject the hand being compared.
   *  @return < 0 if this hand is less than the other hand, 0 if the two hands are
   *  the same, or > 0 if this hand is greater then the other hand.
   */
   public int compareTo( Object otherHandObject ) {
      Hand otherHand = (Hand) otherHandObject;
      return evaluateHand() - otherHand.evaluateHand();
   }


    /**
     * Evaluates a hand according to the rules of the dumb card game.
     * Each card is worth its displayed pip value (ace = 1, two = 2, etc.)
     * in points with face cards worth ten points.  The value of a hand
     * is equal to the summation of the points of all the cards held in
     * the hand.
     */
    public int evaluateHand() {
        int value = 0;
      
        for ( int i = 0; i < getNumberOfCards(); i++ ) {
            Card c = getCard( i );
            int cardValue = Card.getRankIndex(c.getRank()) - Card.getRankIndex('a') + 1;
            if ( cardValue > 10 )
               cardValue = 10;
            value += cardValue;
        }
		
        return value;
    }


   /**
    * Returns a description of the hand.
    * @return a list of cards held in the hand.
    */
    public String toString() {
        return hand.toString();
    }


   /**
    * Replaces the specified card with another card.  Only the first
    * instance of the targeted card is replaced.  No action occurs if
    * the targeted card is not present in the hand.
    * @return <code>true</code> if the replacement occurs.
    */
    public boolean replaceCard( Card oldCard, Card replacementCard ) {
        int location = findCard( oldCard );
        if ( location < 0 )
           return false;
        hand.set( location, replacementCard );
        return true;
    }

    /**
     * remove a set from current hand and lay it on the table.
     * @param set the set to be layed down
     * @param table the table the hand belongs to
     * @param p the player number
     */
    public void removeSet(Set set, Table2 table, int p){
      for(int i = 0; i < set.getNumberOfCards(); i++){
         Card c = set.getCard(i);
         Card d = this.removeCard(c);
         table.layCard(c);
         if(p == 1){
            table.p1Hand.removeElement(c);
         } else if (p == 2){
            table.p2Hand.removeElement(c);
         }
      }
    }

    /**
     * function that automatically plays one turn in the "this" hand
     * @param table the table the hand belongs to
     * @param p integer number specifying the player 
     * @return
     */
    public int play(Table2 table, int p){
       // randomly choose from where to draw a card
       Random rand = new Random();
       float choice_random = rand.nextFloat();
       Card newCard;

       if (table.stackDeck.isEmpty()) choice_random = 0; // if the stack is empty, you have to draw from the deck

      if(choice_random <= 0.5){
         // draw from discard pile
         newCard = table.cardDeck.removeCard();

         if(p ==1){
            table.p1Hand.addElement(newCard);
         } else if (p == 2){
            table.p2Hand.addElement(newCard);
         }

         if(table.cardDeck.getSizeOfDeck() == 0){ // end game if last card has been drawn
            table.deckPile.setIcon(new ImageIcon(Card.directory + "blank.gif"));
            System.out.println("No cards left in deck.");
            table.wincheck();
            System.exit(0);
         }

         
         this.addCard(newCard);
         System.out.println("\t Added from deck " + newCard.toString());

      } else {
         // draw from stack
         newCard = table.stackDeck.pop();

         if(p ==1){
            table.p1Hand.addElement(newCard);
         } else if (p == 2){
            table.p2Hand.addElement(newCard);
         }

         this.addCard(newCard);

         Card topCard = table.stackDeck.top();
            if (topCard != null){
               table.topOfStack.setIcon(topCard.getCardImage());
            } else{
               table.topOfStack.setIcon(new ImageIcon(Card.directory + "blank.gif"));
            }
         System.out.println("\t Added from stack " + newCard.toString());
      }

       // find Sets and if none are found, discard a random card
       Set set = findSet();

       if (set != null){
          removeSet(set, table, p);
          if(this.isEmpty()){ // end game if hand is now empty
            if(p == 1){
               System.out.println("Player 1 wins, got rid of all cards.");
            } else if (p == 2){
               System.out.println("Player 2 wins, got rid of all cards.");
            }
            table.dispose();
            System.exit(0);
          }
       } 

       // choose a random number within the range of the hand
       int discard_random = rand.nextInt(this.getNumberOfCards());

       // remove the card chosen by discard.random
       newCard = this.removeCard(discard_random);
       this.removeCard(newCard);
       if(p == 1){
          table.p1Hand.removeElement(newCard);
          if(this.isEmpty()){
             System.out.println("Player 1 wins, got rid of all cards.");
          }
       } else if (p == 2){
          table.p2Hand.removeElement(newCard);
          if(this.isEmpty()){
            System.out.println("Player 2 wins, got rid of all cards.");
         }
       }

      
       // lay the card on stack
       table.stackDeck.push(newCard);
       table.topOfStack.setIcon(newCard.getCardImage());
       System.out.println("\t Discarded to stack " + newCard.toString());

       // turn is over, re-sort hand
       this.sort();

       System.out.println("Player " + p + "'s hand now: " + this.toString());

       return 0;

    }

}