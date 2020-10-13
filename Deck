import java.util.ArrayList;

/*
 * A full deck has 52 cards. 4 of each card, ignoring suits.
 * Some games of BlackJack use multiple decks at once, this one only uses 1.
 */

public class Deck {
   private ArrayList<Card> deck;
   private String[] names = {"Ace", "Two", "Three", "Four", "Five", "Six",
		   "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"}
   
   //Alternative: Use a HashMap with names as the key and card values as values.
   public Deck() {
	   deck = new ArrayList<Card>();
	   
	   // Cards have values 1-11
	   for (int i = 0; i <= 12; i++) {
		      for (int j = 1; j <= 4; j++) {
			      // Adding each card 4 times
		    	  if (i < 10) {
		    	      deck.add(new Card(i + 1, names[i]));
		    	  }
		    	  //Jack through King also count as 10
		    	  else {
		    		  deck.add(new Card(10, names[i]));
		    	  }
		      }
	   }
   }
   public int getDeckSize() {
	   return deck.size();
   }
   
   public Card draw(int place) {		//draw a card from the deck
	   return deck.get(place);
   }
   
   public int getCardPlace(Card c) {	//Find the position of a card in the deck
	   return deck.indexOf(c);
   }
   
   public void removeCard(Card c) {		//remove a card from the deck.
	   deck.remove(getCardPlace(c));
   }
}
