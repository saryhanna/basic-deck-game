package org.basic.deck;

public class NoEnoughCardsToDealtException extends Exception {
	private Game game;
	private int dealSize;
	

	public NoEnoughCardsToDealtException(Game game, int dealSize) {
		this.game = game;
		this.dealSize = dealSize;
	}
	
	public Game getGame() {
		return game;
	}
	public int getDealSize() {
		return dealSize;
	}
	
}
