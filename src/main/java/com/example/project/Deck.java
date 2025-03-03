package com.example.project;
import java.util.ArrayList;
import java.util.Collections;

public class Deck{
    private ArrayList<Card> cards;

    public Deck(){
        cards = new ArrayList<>();
        initializeDeck();
        shuffleDeck();
    }

    public ArrayList<Card> getCards(){
        return cards;
    }

    public  void initializeDeck(){ //hint.. use the utility class
        String[] suits = Utility.getSuits();
        String[] ranks = Utility.getRanks();

        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    public  void shuffleDeck(){ //You can use the Collections library or another method. You do not have to create your own shuffle algorithm
        Collections.shuffle(cards);
    }

    public  Card drawCard(){
        if (!cards.isEmpty()) {
            return cards.remove(0); // Remove and return the first card
        }
        return null; // Return null if the deck is empty
    }
    public Card dealCard() {
        return cards.isEmpty() ? null : cards.remove(0);
    }

    public  boolean isEmpty(){
        return cards.isEmpty();
    }

   


}