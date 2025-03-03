package com.example.project;

import java.util.ArrayList;

public class Game {

    // Method to determine the winner
    public static String determineWinner(Player p1, Player p2, String p1Hand, String p2Hand, ArrayList<Card> communityCards) {
        // Compare hand rankings
        int p1Rank = getHandRank(p1Hand);
        int p2Rank = getHandRank(p2Hand);

        if (p1Rank > p2Rank) {
            return "Player 1 wins!";
        } else if (p2Rank > p1Rank) {
            return "Player 2 wins!";
        } else {
            // Tiebreaker: Compare highest cards
            ArrayList<Card> p1Cards = new ArrayList<>(p1.getCards());
            p1Cards.addAll(communityCards);
            ArrayList<Card> p2Cards = new ArrayList<>(p2.getCards());
            p2Cards.addAll(communityCards);

            // Sort cards in descending order of rank
            sortCardsByRank(p1Cards);
            sortCardsByRank(p2Cards);

            // Compare the highest cards
            for (int i = 0; i < p1Cards.size(); i++) {
                if (p1Cards.get(i).getRankValue() > p2Cards.get(i).getRankValue()) {
                    return "Player 1 wins!";
                } else if (p1Cards.get(i).getRankValue() < p2Cards.get(i).getRankValue()) {
                    return "Player 2 wins!";
                }
            }
            return "Tie!";
        }
    }

    // Helper method to get the rank of a hand
    private static int getHandRank(String hand) {
        switch (hand) {
            case "Royal Flush": return 10;
            case "Straight Flush": return 9;
            case "Four of a Kind": return 8;
            case "Full House": return 7;
            case "Flush": return 6;
            case "Straight": return 5;
            case "Three of a Kind": return 4;
            case "Two Pair": return 3;
            case "One Pair": return 2;
            case "High Card": return 1;
            default: return 0; // Invalid hand
        }
    }

    // Helper method to sort cards by rank in descending order
    private static void sortCardsByRank(ArrayList<Card> cards) {
        for (int i = 0; i < cards.size() - 1; i++) {
            for (int j = i + 1; j < cards.size(); j++) {
                if (cards.get(i).getRankValue() < cards.get(j).getRankValue()) {
                    // Swap cards
                    Card temp = cards.get(i);
                    cards.set(i, cards.get(j));
                    cards.set(j, temp);
                }
            }
        }
    }

    // Method to simulate the game (not tested, but useful for testing)
    public static void play() {
        // Initialize deck and shuffle
        Deck deck = new Deck();
        deck.shuffleDeck();

        // Create players
        Player p1 = new Player();
        Player p2 = new Player();

        // Deal 2 cards to each player
        p1.receiveCard(deck.dealCard());
        p1.receiveCard(deck.dealCard());
        p2.receiveCard(deck.dealCard());
        p2.receiveCard(deck.dealCard());

        // Deal 5 community cards
        ArrayList<Card> communityCards = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            communityCards.add(deck.dealCard());
        }

        // Evaluate hands (assume `playHand()` is implemented in Player class)
        String p1Hand = p1.playHand(communityCards);
        String p2Hand = p2.playHand(communityCards);

        // Determine and print the winner
        String result = determineWinner(p1, p2, p1Hand, p2Hand, communityCards);
        System.out.println(result);
    }
}