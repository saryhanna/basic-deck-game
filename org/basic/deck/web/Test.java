package org.basic.deck.web;

public class Test {

	public static void main(String[] args) {
		OnlineDeckService service  = new OnlineDeckService();
		System.out.println(service.createGame());
		System.out.println(service.createPlayer("Sary"));
		System.out.println(service.createPlayer("Waseem"));
		System.out.println(service.createPlayer("Naly"));
		System.out.println(service.addPlayerToGame(2, 1));
		System.out.println(service.addPlayerToGame(3, 1));
		System.out.println(service.addPlayerToGame(4, 1));
		System.out.println(service.createDeck());
		System.out.println(service.addDeckToGame(5, 1));
		service.shuffle(1);
		System.out.println(service.dealCards(2, 1));
		System.out.println(service.getPlayersCardsTotalValue(1));
		System.out.println(service.getPlayerListOfCards(2, 1));
		System.out.println(service.getRemainingCardsCount(1));
		System.out.println(service.getSuitRemainingCards(1));

	}

}
