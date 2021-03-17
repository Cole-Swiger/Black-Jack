/*
 * Card suit doesn't matter in Blackjack
 * Cards can have values 1-11 where Ace is 1 or 11, Jack, Queen, King are all 10
 */

public class Card {
   private int value;
   private String name;
   //private String suit;
   
   public Card(int val, String cardName) {
	   value = val;
	   name = cardName;
   }
   
   public int getValue() {
	   return value;
   }
   
   public String getCardName() {
	   return name;
   }
   
   public boolean isAce() {
	   if (getCardName().equals("Ace")) {
		   return true;
	   }
	   else {
		   return false;
	   }
   }
}
