package com.example.project;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards; // List to hold all the cards in the deck

    /**
     * Constructor that initializes and shuffles the deck.
     */
    public Deck() {
        cards = new ArrayList<>();
        initializeDeck(); // Populate the deck with 52 cards
        shuffleDeck();    // Shuffle the deck for randomness
    }

    /**
     * Retrieves the current list of cards in the deck.
     * @return The list of cards.
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Initializes the deck with all possible cards (52 in a standard deck).
     * Uses the Utility class to get available suits and ranks.
     */
    public void initializeDeck() {
        String[] suits = Utility.getSuits(); // Retrieve available suits
        String[] ranks = Utility.getRanks(); // Retrieve available ranks

        // Create a card for each suit and rank combination
        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    /**
     * Shuffles the deck to randomize the order of the cards.
     */
    public void shuffleDeck() {
        Collections.shuffle(cards);
    }

    /**
     * Draws a card from the top of the deck (removes and returns it).
     * @return The drawn card, or null if the deck is empty.
     */
    public Card drawCard() {
        if (!cards.isEmpty()) {
            return cards.remove(0); // Remove and return the top card
        }
        return null; // Return null if the deck is empty
    }


    public Card dealCard() {
        return cards.isEmpty() ? null : cards.remove(0);
    }

    /**
     * Checks if the deck is empty.
     * @return True if the deck has no cards left, otherwise false.
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
