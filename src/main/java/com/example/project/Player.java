package com.example.project;

import java.util.ArrayList;


public class Player {
    private ArrayList<Card> hand;
    private ArrayList<Card> allCards; // the current community cards + hand
    String[] suits = Utility.getSuits();
    String[] ranks = Utility.getRanks();

    public Player() {
        hand = new ArrayList<>();
        allCards = new ArrayList<>();
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public ArrayList<Card> getAllCards() {
        return allCards;
    }

    // Method to add a card to the player's hand
    public void addCard(Card c) {
        hand.add(c);
    }

    // Method to evaluate the player's best hand
    public String playHand(ArrayList<Card> communityCards) {
        // Combine hand and community cards
        allCards.clear();
        allCards.addAll(hand);
        allCards.addAll(communityCards);

        // Sort all cards for easier evaluation
        sortAllCards();

        // Determine the best hand
        if (isRoyalFlush()) return "Royal Flush";
        if (isStraightFlush()) return "Straight Flush";
        if (isFourOfAKind()) return "Four of a Kind";
        if (isFullHouse()) return "Full House";
        if (isFlush()) return "Flush";
        if (isStraight()) return "Straight";
        if (isThreeOfAKind()) return "Three of a Kind";
        if (isTwoPair()) return "Two Pair";
        if (isPair()) return "A Pair";
        if (isHighCard()) return "High Card";
        return "Nothing";
    }

    // Method to sort all cards in ascending order
    public void sortAllCards() {
        allCards.sort((c1, c2) -> Integer.compare(c1.getRankValue(), c2.getRankValue()));

    }

    // Method to calculate the frequency of each rank
    public ArrayList<Integer> findRankingFrequency() {
        ArrayList<Integer> frequency = new ArrayList<>();
        for (int i = 0; i < ranks.length; i++) {
            frequency.add(0);
        }
        for (Card card : allCards) {
            int index = getRankIndex(card.getRank());
            if (index != -1) {
                frequency.set(index, frequency.get(index) + 1);
            }
        }
        return frequency;
    }

    // Method to calculate the frequency of each suit
    public ArrayList<Integer> findSuitFrequency() {
        ArrayList<Integer> frequency = new ArrayList<>();
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

    // Helper method to get the index of a rank
    private int getRankIndex(String rank) {
        for (int i = 0; i < ranks.length; i++) {
            if (ranks[i].equals(rank)) {
                return i;
            }
        }
        return -1; // Rank not found
    }

    // Helper method to get the index of a suit
    private int getSuitIndex(String suit) {
        for (int i = 0; i < suits.length; i++) {
            if (suits[i].equals(suit)) {
                return i;
            }
        }
        return -1; // Suit not found
    }

    // Methods to check for specific hand rankings
    private boolean isRoyalFlush() {
        // Check for Royal Flush logic
        return false; // Placeholder
    }

    private boolean isStraightFlush() {
       return false;
    }

    private boolean isFourOfAKind() {
        ArrayList<Integer> frequency = findRankingFrequency();
        return frequency.contains(4);
    }

    private boolean isFullHouse() {
        ArrayList<Integer> frequency = findRankingFrequency();
        boolean hasThree = false;
        boolean hasTwo = false;
        for (int count : frequency) {
            if (count == 3) hasThree = true;
            if (count == 2) hasTwo = true;
        }
        return hasThree && hasTwo;
    }

    private boolean isFlush() {
        ArrayList<Integer> frequency = findSuitFrequency();
        for (int count : frequency) {
            if (count >= 5) return true;
        }
        return false;
    }

    private boolean isStraight() {
        sortAllCards();
        int count = 0;
        for (Card i : allCards) {
            if(i.getRankValue() == i.getRankValue() + 1) {
                count++;
            }
        }
        if (count == 5) {
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isThreeOfAKind() {
        ArrayList<Integer> frequency = findRankingFrequency();
        for (int count : frequency) {
            if (count == 3) return true;
        }
        return false;
    }

    private boolean isTwoPair() {
        ArrayList<Integer> frequency = findRankingFrequency();
        int pairCount = 0;
        for (int count : frequency) {
            if (count == 2) pairCount++;
        }
        return pairCount >= 2;
    }

    private boolean isPair() {
        ArrayList<Integer> frequency = findRankingFrequency();
        for (int count : frequency) {
            if (count == 2) return true;
        }
        return false;
    }

    private boolean isHighCard() {
        return !allCards.isEmpty();
    }

    public ArrayList<Card> getCards() {
        return allCards;  // Assuming `allCards` is the list holding player’s cards
    }
    
    public void receiveCard(Card card) {
        if (card != null) {
            allCards.add(card);  // Assuming `allCards` is your ArrayList<Card> that holds the player's cards
        }
    }
    
    

    @Override
    public String toString() {
        return hand.toString();
    }
}