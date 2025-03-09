package com.example.project;

import java.util.ArrayList;
import java.util.Collections;

public class Player {
    private ArrayList<Card> hand;  // Stores the player's hand (2 cards in Texas Hold'em)
    private ArrayList<Card> allCards;  // Stores the player's hand + community cards for evaluation
    String[] suits = Utility.getSuits(); // Available suits from a utility class
    String[] ranks = Utility.getRanks(); // Available ranks from a utility class

    // Constructor initializes the hand and allCards lists
    public Player() {
        hand = new ArrayList<>();
        allCards = new ArrayList<>();
    }

    // Getter method for player's hand
    public ArrayList<Card> getHand() {
        return hand;
    }

    // Getter method for all cards (hand + community cards)
    public ArrayList<Card> getAllCards() {
        return allCards;
    }

    // Adds a card to the player's hand
    public void addCard(Card c) {
        hand.add(c);
    }

    /**
     * Evaluates the player's best poker hand using their cards and community cards.
     * @param communityCards The shared community cards on the table.
     * @return A string representing the best possible hand the player can form.
     */
    public String playHand(ArrayList<Card> communityCards) {
        int count = 0;
        allCards.clear();
        allCards.addAll(hand);  // Add player's hand to the list
        allCards.addAll(communityCards); // Add community cards
        sortAllCards();  // Sort cards in ascending order

        // Check hand rankings in descending order of strength
        if (isRoyalFlush()) return "Royal Flush";
        if (isStraightFlush()) return "Straight Flush";
        if (isFourOfAKind()) return "Four of a Kind";
        if (isFullHouse()) return "Full House";
        if (isFlush()) return "Flush";
        if (isStraight()) return "Straight";
        if (isThreeOfAKind()) return "Three of a Kind";
        if (isTwoPair()) return "Two Pair";
        if (isPair()) return "A Pair";

        // Check for High Card scenario
        allCards.clear();
        allCards.addAll(hand);
        sortAllCards();
        for (int i = 0; i < communityCards.size(); i++) {
            if (allCards.get(0).getRankValue() > communityCards.get(i).getRankValue()) {
                count++;
            }
            if (count == 3) {
                return "High Card";
            }
        }

        // If no valid hand is found, return "Nothing"
        allCards.clear();
        allCards.addAll(hand);
        allCards.addAll(communityCards);
        sortAllCards();
        return "Nothing";  
    }

    // Sorts all the cards in ascending order based on rank value
    public void sortAllCards() {
        for (int i = 0; i < allCards.size() - 1; i++) {
            for (int j = 0; j < allCards.size() - i - 1; j++) {
                if (allCards.get(j).getRankValue() > allCards.get(j + 1).getRankValue()) {
                    // Swap elements
                    Card temp = allCards.get(j);
                    allCards.set(j, allCards.get(j + 1));
                    allCards.set(j + 1, temp);
                }
            }
        }
    }
    

    // Calculates the frequency of each rank in the player's hand + community cards
    public ArrayList<Integer> findRankingFrequency() {
        ArrayList<Integer> frequency = new ArrayList<>(Collections.nCopies(ranks.length, 0));
        for (Card card : allCards) {
            int index = getRankIndex(card.getRank());
            if (index != -1) {
                frequency.set(index, frequency.get(index) + 1);
            }
        }
        return frequency;
    }

// Calculates the frequency of each suit in the player's hand + community cards
public ArrayList<Integer> findSuitFrequency() {
    ArrayList<Integer> frequency = new ArrayList<>();
    // Initialize the ArrayList with zeros
    for (int i = 0; i < suits.length; i++) {
        frequency.add(0);
    }
    for (Card card : allCards) {
        int index = getSuitIndex(card.getSuit());
        if (index != -1) {
            frequency.set(index, frequency.get(index) + 1);
        }
    }
    return frequency;
}

    // Helper method to find the index of a rank in the ranks array
    private int getRankIndex(String rank) {
        for (int i = 0; i < ranks.length; i++) {
            if (ranks[i].equals(rank)) {
                return i;
            }
        }
        return -1; // Rank not found
    }

    // Helper method to find the index of a suit in the suits array
    private int getSuitIndex(String suit) {
        for (int i = 0; i < suits.length; i++) {
            if (suits[i].equals(suit)) {
                return i;
            }
        }
        return -1; // Suit not found
    }

    // Determines if the player has a Royal Flush (A, K, Q, J, 10 of the same suit)
    private boolean isRoyalFlush() {
        return isStraightFlush() && allCards.get(4).getRankValue() == 14;
    }

    // Determines if the player has a Straight Flush (Five consecutive cards of the same suit)
    private boolean isStraightFlush() {
        return isFlush() && isStraight();
    }

    // Determines if the player has Four of a Kind
    private boolean isFourOfAKind() {
        ArrayList<Integer> frequency = findRankingFrequency();
    
        for (int count : frequency) {
            if (count == 4) {
                return true;
            }
        }
        
        return false;
    }

    // Determines if the player has a Full House (Three of a Kind + One Pair)
    private boolean isFullHouse() {
        ArrayList<Integer> frequency = findRankingFrequency();
        return frequency.contains(3) && frequency.contains(2);
    }

    // Determines if the player has a Flush (Five cards of the same suit)
    private boolean isFlush() {
        for (int count : findSuitFrequency()) {
            if (count >= 5) return true;
        }
        return false;
    }

    // Determines if the player has a Straight (Five consecutive rank values)
    private boolean isStraight() {
        int consecutiveCount = 1;
        for (int i = 1; i < allCards.size(); i++) {
            if (allCards.get(i).getRankValue() == allCards.get(i - 1).getRankValue() + 1) {
                consecutiveCount++;
            } else if (allCards.get(i).getRankValue() != allCards.get(i - 1).getRankValue()) {
                consecutiveCount = 1;
            }
        }
        return consecutiveCount >= 5;
    }

    // Determines if the player has Three of a Kind
    private boolean isThreeOfAKind() {
    ArrayList<Integer> frequency = findRankingFrequency();
    
    for (int count : frequency) {
        if (count == 3) {
            return true;
        }
    }
    
    return false;
}


    // Determines if the player has Two Pairs
    private boolean isTwoPair() {
        int pairCount = 0;
        for (int count : findRankingFrequency()) {
            if (count == 2) pairCount++;
        }
        return pairCount >= 2;
    }

    // Determines if the player has a Pair
    private boolean isPair() {
    ArrayList<Integer> frequency = findRankingFrequency();
    
    for (int count : frequency) {
        if (count == 2) {
            return true;
        }
    }
    
    return false;
}


    // Returns all cards in the player's possession (hand + community cards)
    public ArrayList<Card> getCards() {
        return allCards;
    }

    // Adds a new card to the player's list of all cards
    public void receiveCard(Card card) {
        if (card != null) {
            allCards.add(card);
        }
    }

    // Returns a string representation of the player's hand
    @Override
    public String toString() {
        return hand.toString();
    }
}
