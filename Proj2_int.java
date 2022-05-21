/**
 * Name: Nikola Mang
 * Student number: 701214112
 * Course: CCOM 4029 (High level programming languages)
 * Professor: Patti Ordonez
 * 
 * same here, it's called Proj2_int because that was a temporary renaming that stuck I suppose.
 */

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class Proj2_int {
    public static void main (String args[]){
        // first: set up the game, deal cards
        Deck deck = new Deck();
        Pile stack = new Pile();

        Hand player1 = new Hand();
        Hand player2 = new Hand();

        for (int i = 0; i <= 9; i++){
            player1.addCard(deck.dealCard());
            player2.addCard(deck.dealCard());
        }

        player1.sort();
        player2.sort();

        // ask for user input of player number
        System.out.println("How many players? (0 - 2)");
        Scanner input = new Scanner(System.in);
        int playerNo = input.nextInt();
        playerNo = 2 - playerNo;

        // set up table 
        Table2 table = new Table2(stack, deck, player1, player2, playerNo);
        table.setVisible(true);

        
    }
}