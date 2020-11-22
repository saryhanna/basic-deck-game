package org.basic.deck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Game {
	private int id;

	private int numOfDecks;
	private List<Card> gameDeck;
	private Set<Player> players;

	public Game(int id) {
		this.id = id;
		numOfDecks = 0;
		players = new HashSet<Player>();
		gameDeck = new ArrayList<Card>();
	}

	public int getId() {
		return this.id;
	}

	public void addPlayer(Player player) {
		players.add(player);

	}

	public boolean removePlayer(Player player) {
		if (players.contains(player)) {
			gameDeck.addAll(player.getHand());
			players.remove(player);
			return true;
		}
		return false;

	}

	public void addDeck(Deck deck) {
		gameDeck.addAll(Arrays.asList(deck.getCards()));
		numOfDecks++;
		// it's possible to call shuffle() here
	}

	public void dealCards(int size) throws NoEnoughCardsToDealtException {
		int numOfPlayers = players.size();
		int totalDealSize = numOfPlayers * size;
		if (((numOfDecks * 52) - gameDeck.size()) + totalDealSize > 52) {
			throw new NoEnoughCardsToDealtException(this, totalDealSize);
		}
		for (Player player : players) {
			int count = 0;
			while (count < size) {
				player.dealCard(gameDeck.remove(0));
				count++;
			}
		}
	}

	public void shuffle() {
		Collections.shuffle(gameDeck, new Random());
	}

	public List<Card> getPlayerListOfCards(Player player) {
		for (Player p : players)
			if (p.equals(player))
				return p.getHand();
		return null;
	}

	public Map<Player, Long> getPlayersCardsTotalValue() {
		Map<Player, Long> playersTotalValue = players.stream()
				.collect(Collectors.toMap(player -> player, player -> player.getTotalHandValue()));
		return playersTotalValue.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (p1, p2) -> p2, LinkedHashMap::new));
	}

	public Map<Card.Suit, Long> getSuitRemainingCards() {
		return gameDeck.stream().collect(Collectors.groupingBy(card -> card.getSuit(), Collectors.counting()));
	}

	public Map<Card, Long> getRemainingCardsCount() {
		return gameDeck.stream().collect(Collectors.groupingBy(card -> card, TreeMap::new, Collectors.counting()));
	}

}
