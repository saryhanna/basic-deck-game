package org.basic.deck;

public class Card implements Comparable<Card> {
	public enum Suit {
		HEARTS(1), SPADE(2), CLUBS(3), DIAMOND(4);

		private final int value;

		Suit(int value) {
			this.value = value;

		}

		int value() {
			return this.value;
		}
	};

	public enum Face {
		ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(11), QWEEN(12),
		KING(13);

		private final int value;

		Face(int value) {
			this.value = value;

		}

		int value() {
			return this.value;
		}

	}

	private Suit suit;
	private Face face;

	public Card(Suit suit, Face face) {
		this.suit = suit;
		this.face = face;
	}

	public Suit getSuit() {
		return this.suit;
	}

	public Face getFace() {
		return this.face;
	}

	@Override
	public int compareTo(Card o) {
		if (this.suit.value != o.suit.value)
			return this.suit.value - o.suit.value;
		return this.face.value - o.face.value;
	}

	public String toString() {
		return suit + "[" + face.value + "]";
	}
}
