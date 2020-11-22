package org.basic.deck;

public class Deck {
	private int id;
	private Card[] cards = new Card[52];

	public Deck(int id) {
		this.id = id;
		int i = 0;
		for (Card.Suit suit : Card.Suit.values()) {
			for (Card.Face face : Card.Face.values()) {
				cards[i] = new Card(suit, face);
				i++;
			}
		}
	}

	public Card[] getCards() {
		return this.cards;
	}

	public int getId() {
		return this.id;
	}
}
