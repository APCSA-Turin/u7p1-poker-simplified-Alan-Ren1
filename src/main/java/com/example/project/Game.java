package com.example.project;

import java.util.ArrayList;

public class Game {

    /**
     * Determines the winner between two players based on their best poker hands.
     * @param p1 The first player.
     * @param p2 The second player.
     * @param p1Hand The best hand ranking of player 1.
     * @param p2Hand The best hand ranking of player 2.
     * @param communityCards The shared community cards on the table.
     * @return A string announcing the winner or if it's a tie.
     */
    public static String determineWinner(Player p1, Player p2, String p1Hand, String p2Hand, ArrayList<Card> communityCards) {
        // Compare hand rankings numerically
        int p1Rank = getHandRank(p1Hand);
        int p2Rank = getHandRank(p2Hand);

        if (p1Rank > p2Rank) {
            return "Player 1 wins!";
        } else if (p2Rank > p1Rank) {
            return "Player 2 wins!";
        } else {
            // Tiebreaker: Compare highest-ranked cards
            ArrayList<Card> p1Cards = new ArrayList<>(p1.getCards());
            p1Cards.addAll(communityCards);
            ArrayList<Card> p2Cards = new ArrayList<>(p2.getCards());
            p2Cards.addAll(communityCards);

            // Sort cards in descending order of rank
            sortCardsByRank(p1Cards);
            sortCardsByRank(p2Cards);

            // Compare the highest cards one by one
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

    /**
     * Converts a hand name to a numerical rank for comparison.
     * @param hand The poker hand name.
     * @return The numerical rank of the hand (higher is better).
     */
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
            default: return 0; // Invalid or unrecognized hand
        }
    }

    /**
     * Sorts a list of cards in descending order based on their rank.
     * @param cards The list of cards to be sorted.
     */
    private static void sortCardsByRank(ArrayList<Card> cards) {
        for (int i = 0; i < cards.size() - 1; i++) {
            for (int j = i + 1; j < cards.size(); j++) {
                if (cards.get(i).getRankValue() < cards.get(j).getRankValue()) {
                    // Swap cards to maintain descending order
                    Card temp = cards.get(i);
                    cards.set(i, cards.get(j));
                    cards.set(j, temp);
                }
            }
        }
    }

    /**
     * Simulates a poker game round between two players.
     * This method creates a deck, deals cards, evaluates hands, and determines the winner.
     */
    public static void play() {
        // Initialize deck and shuffle it
        Deck deck = new Deck();
        deck.shuffleDeck();

        // Create two players
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

        // Evaluate the best hand for each player
        String p1Hand = p1.playHand(communityCards);
        String p2Hand = p2.playHand(communityCards);

        // Determine the winner and print the result
        String result = determineWinner(p1, p2, p1Hand, p2Hand, communityCards);
        System.out.println(result);
    }
}
