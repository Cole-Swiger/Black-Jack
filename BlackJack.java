import java.util.Random;
import java.util.Scanner;

/*
 * Rules of BlackJack: You want your cards to add up to 21 to win.
 * Ace can be 1 or 11, Jack, Queen , and King are 10.
 * 
 * You can keep drawing cards until you break (go over 21) or want to stop.
 * After you play, the computer will go and try to beat you.
 * A tie is a win for the computer.
 * 
 * Computer and Player totals/number of aces need to be kept separate 
 * so they can be worked with individually.
 * Aces having two possible values makes keep track of the totals a bit messy.
 * Because of this, I use "Player" and "Computer" strings in some of my methods
 * so the program knows which values to change.
 * If this were expanded to allow multiple players, I would probably need to 
 * create a Player class to keep everyone's scores separate.
 * 
 * Author: Cole Swiger
 * Start Date: 9/9/20
 * Last Update: 9/13/20
 */

public class BlackJack {

   private Deck deck;

   //for drawing random cards
   private Random rand;				
   private int total = 0;
   private int cpuTotal = 0;

   //The value of aces can change mid game
   private int numberOfAces = 0;		
   private int cpuAces = 0;

   //for user input
   private Scanner scan;			

   public BlackJack() {
	   deck = new Deck();
	   rand = new Random();
	   scan = new Scanner(System.in);
   }
   
   public void firstDraw() {
	   //Draw two cards randomly out of the deck and then remove from deck.
	   //Do this for computer and player
	   Card cpuCard1 = drawCard();
	   Card cpuCard2 = drawCard();

	   //keeping track of all the aces
	   ifIsAce(cpuCard1, "Computer");      
	   ifIsAce(cpuCard2, "Computer");
	   
	   Card card1 = drawCard();
	   Card card2 = drawCard();
	   ifIsAce(card1, "Player");
	   ifIsAce(card2, "Player");

	   //Add the values together
	   total += card1.getValue() + card2.getValue();		
	   cpuTotal += cpuCard1.getValue() + cpuCard2.getValue();
	   
	   //check if cpuTotal is 21
	   if (cpuTotal == 21) {
		   System.out.println("The computer drew a natural 21. You lose!\n"
				   + "Please try again.");
		   return;
	   }
	   
	   System.out.println("The computer has drawn two cards.\n"
	   		+ "One card will be shown now: " + cpuCard1.getCardName());
	   
	   System.out.println("\nHere are your cards:");
	   System.out.println("First card: " + card1.getCardName() 
	                     + "\nSecond card: " + card2.getCardName());
	   //System.out.println("Values: " + card1.getValue() + " " + card2.getValue());
	   System.out.println("Your total is " + total);
	   
	   //If there are aces in play and a total is over 21, the value of the ace 
	   //needs to be changed. This check happens every time we want to check or 
	   //display the total after a card is drawn.
	   if (numberOfAces > 0) {
		   checkAces(total, "Player");
	   }
	   if (cpuAces > 0) {
		   checkAces(cpuTotal, "Computer");
	   }
	   
	   if (total == 21) {
		   //this will always go to computer's turn
		   checkTotal(total);
	   }
	   System.out.println();
	   firstResponse();
   }

   //using the draw method from Deck class
   public Card drawCard() {		
	   Card c = deck.draw(rand.nextInt(deck.getDeckSize()));
	   deck.removeCard(c);
	   return c;
   }
   
   public void ifIsAce(Card c, String player) {
	   //This method essentially makes the default value for an ace 11, even though 
	   //in the Deck class it is given a value of 1. Adding 10 was easier than changing the
	   //initial value, as I would still have to do something similar to change it from 11 to 1.
	   if (c.isAce() == true) {
		   if (player.equals("Player")) {
			   numberOfAces++;
			   total += 10;
		   }
		   else if (player.equals("Computer")){
			   cpuAces++;
			   cpuTotal += 10;
		   }
	   }
   }
   
   public void checkAces(int score, String player) {
	   //When aces are in play, a total over 21 does not automatically 
	   //end the game. The ace just becomes a 1.
	   if (score > 21 && player.equals("Player")) {
		   numberOfAces--;
		   total -= 10;
	   } 
	   if (score > 21 && player.equals("Computer")) {
		   cpuAces--;
		   cpuTotal -= 10;
	   }
   }
   
   public void firstResponse() {
	   System.out.println("Type \"Play\" to draw another card.\n"
	   		+ "Type \"Stop\" to stick with what you have.");
	   
	   //Taking user input to continue game
	   String response = scan.nextLine();
	   answerResponse(response);
   }
   
   public void promptUser() {
	   System.out.println("Type \"Play\" or \"Stop\"");
	   String response = scan.nextLine();
	   answerResponse(response);
   }
   
   public void answerResponse(String res) {
	   if (res.equals("Play")) {
		   //Playing just adds another card to the two the player has 
		   //and adds to their total
		   Card nextCard = drawCard();
		   System.out.println("New Card: " + nextCard.getCardName());
		   ifIsAce(nextCard, "Player");
		   
		   total += nextCard.getValue();
		   
		   if (numberOfAces > 0) {
		      checkAces(total, "Player");
		   }
		   
		   System.out.println("New total: " + total);
		      
		   checkTotal(total);
		   //promptUser();
	   }
	   else if (res.equals("Stop")) {
		   System.out.println("Your final total is " + total);
		   System.out.println("The computer will now take it's turn.\n");
		   cpuTurn();
	   }
	   else {
		   System.out.println(res + " is not a proper response.\n");
		   firstResponse();  //totals are still saved
	   }
   }
   
   public void checkTotal(int total) {
	   if (total < 21) {
		   //game keeps going
		   promptUser();
	   }
	   else if (total > 21) {
		   System.out.println("Bust! Your total is over 21.\nTry again!");
		   return;
	   }
	   else {	//total is 21
		   System.out.println("Your total is 21!\nThe computer will now take its turn.");
		   //The computer will then take its turn since its the dealer.
		   cpuTurn();
	   }
   }
   
   public void cpuTurn() {
	   //computer will stop trying once its total is 17 or greater, 
	   //as per the official rules. (Some allow play when equal to 17).
	   while (cpuTotal < 17) {
		   //basically does the same thing the player does but doesn't have any choices
		   Card nextCard = drawCard();
		   ifIsAce(nextCard, "Computer");
		   cpuTotal += nextCard.getValue();
		   
		   System.out.println("New Card: " + nextCard.getCardName());
		   
		   if (cpuAces > 0) {
		      checkAces(cpuTotal, "Computer");
		   }
		   
		   System.out.println("New total: " + cpuTotal);
	   }
	   System.out.println("Computer's total: " + cpuTotal);

	   if (cpuTotal > 21) {
		   System.out.println("The computer busted. You win!");
	   }
	   else if (total > cpuTotal){
		   System.out.println("Your total is higher than the computer's. You win!");
	   }
	   else {
		   System.out.println("Your total is not higher than the computer's. You lose."
		   		+ "\nPlease try again.");
	   }
   }

   public static void main(String[] args) {
	   BlackJack game = new BlackJack();
	   game.firstDraw();
   }
}
