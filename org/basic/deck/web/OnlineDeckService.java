package org.basic.deck.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.basic.deck.*;

@Path("/onlinedeck")
public class OnlineDeckService {
	AtomicInteger counter = new AtomicInteger();
	Map<Integer, Game> gamesTable = new HashMap();
	Map<Integer, Deck> decksTable = new HashMap();
	Map<Integer, Player> playersTable = new HashMap();

	@POST
	@Path("/createGame")
	@Produces("Text/JSON")
	public String createGame() {
		int gameId = counter.incrementAndGet();
		Game game = new Game(gameId);
		gamesTable.put(gameId, game);
		return String.format("{\"game_id\":\"%s\"}", game.getId());

	}

	@POST
	@Path("/deleteGame/{game_id}")
	@Produces("Text/JSON")
	public String deleteGame(@PathParam("game_id") int gameId) {
		Game game = gamesTable.get(gameId);
		if (game != null) {
			gamesTable.remove(gameId);
			return String.format("{\"status\":\"deleted\",\"game_id\":\"%s\"}", gameId);
		} else {
			return String.format("{\"status\":\"notfound\",\"game_id\":\"%s\"}", gameId);
		}
	}

	@POST
	@Path("/createDeck")
	@Produces("Text/JSON")
	public String createDeck() {
		int deckId = counter.incrementAndGet();
		Deck deck = new Deck(deckId);
		decksTable.put(deckId, deck);
		return String.format("{\"deck_id\":\"%s\"}", deck.getId());

	}

	@POST
	@Path("/deleteDeck/{deck_id}")
	@Produces("Text/JSON")
	public String deleteDeck(@PathParam("deck_id") int deckId) {
		Deck deck = decksTable.get(deckId);
		if (deck != null) {
			decksTable.remove(deckId);
			return String.format("{\"status\":\"deleted\",\"deck_id\":\"%s\"}", deckId);
		} else {
			return String.format("{\"status\":\"notfound\",\"deck_id\":\"%s\"}", deckId);
		}

	}

	@POST
	@Path("/addDeckToGame/{deck_id}/{game_id}")
	@Produces("Text/JSON")
	public String addDeckToGame(@PathParam("deck_id") int deckId, @PathParam("game_id") int gameId) {
		gamesTable.get(gameId).addDeck(decksTable.get(deckId));
		return String.format("{\"deck_id\":\"%s\",\"game_id\":\"%s\"}", deckId, gameId);
	}

	@POST
	@Path("/createPlayer/{player_name}")
	@Produces("Text/JSON")
	public String createPlayer(@PathParam("player_name") String name) {
		int playerId = counter.incrementAndGet();
		Player player = new Player(playerId, name);
		playersTable.put(playerId, player);
		return String.format("{\"player_id\":\"%s\"}", player.getId());

	}

	@POST
	@Path("/addPlayerToGame/{player_id}/{game_id}")
	@Produces("Text/JSON")
	public String addPlayerToGame(@PathParam("palyer_id") int playerId, @PathParam("game_id") int gameId) {
		gamesTable.get(gameId).addPlayer(playersTable.get(playerId));
		return String.format("{\"player_id\":\"%s\",\"game_id\":\"%s\"}", playerId, gameId);

	}

	@POST
	@Path("/removePlayerFromGame/{player_id}/{game_id}")
	@Produces("Text/JSON")
	public String removePlayerFromGame(@PathParam("player_id") int playerId, @PathParam("game_id") int gameId) {
		if (gamesTable.get(gameId).removePlayer(playersTable.get(playerId))) {
			return String.format("{\"status\":\"success\",\"player_id\":\"%s\",\"game_id\":\"%s\"}", playerId, gameId);
		} else {
			return String.format("{\"status\":\"failed\",\"player_id\":\"%s\",\"game_id\":\"%s\"}", playerId, gameId);
		}
	}

	@POST
	@Path("/dealCards/{size}/{game_id}")
	@Produces("Text/JSON")
	public String dealCards(@PathParam("size") int size, @PathParam("game_id") int gameId) {
		Game game = gamesTable.get(gameId);
		try {
			game.dealCards(size);
			return String.format("{\"status\":\"success\",\"size\":\"%s\",\"game_id\":\"%s\"}", size, gameId);
		} catch (NoEnoughCardsToDealtException e) {
			return String.format("{\"status\":\"failed\",\"size\":\"%s\",\"game_id\":\"%s\"}", size, gameId);
		}

	}

	@GET
	@Path("/getPlayerListOfCards/{palyer_id}/{game_id}")
	@Produces("Text/JSON")
	public String getPlayerListOfCards(@PathParam("player_id") int playerId, @PathParam("game_id") int gameId) {
		List<Card> result = gamesTable.get(gameId).getPlayerListOfCards(playersTable.get(playerId));
		String response = "[";
		for (Card c : result) {
			response += String.format("{\"suit\":\"%s\",\"face\":\"%s\"},", c.getSuit(), c.getFace());
		}
		response = result.size()>0? response.substring(0, response.length()-1):response;
		response += "]";
		return response;
	}

	@GET
	@Path("/getPlayersCardsTotalValue/{game_id}")
	@Produces("Text/JSON")
	public String getPlayersCardsTotalValue(int gameId) {
		Map<Player, Long> result = gamesTable.get(gameId).getPlayersCardsTotalValue();
		String response = "[";
		for (Player p : result.keySet()) {
			response += String.format("{\"player_id\":\"%s\",\"total_value\":\"%s\"},", p.getId(), result.get(p));
		}
		response = result.size()>0? response.substring(0, response.length()-1):response;
		response += "]";
		return response;

	}

	@GET
	@Path("/getSuitRemainingCards/{game_id}")
	@Produces("Text/JSON")
	public String getSuitRemainingCards(@PathParam("game_id") int gameId) {
		Map<Card.Suit, Long> result = gamesTable.get(gameId).getSuitRemainingCards();
		String response = "[";
		for (Card.Suit suit : result.keySet()) {
			response += String.format("{\"suit\":\"%s\",\"remaining_cards\":\"%s\"},", suit, result.get(suit));
		}
		response = result.size()>0? response.substring(0, response.length()-1):response;
		response += "]";
		return response;

	}

	@GET
	@Path("/getRemainingCardsCount/{game_id}")
	@Produces("Text/JSON")
	public String getRemainingCardsCount(@PathParam("game_id") int gameId) {
		Map<Card, Long> result = gamesTable.get(gameId).getRemainingCardsCount();
		String response = "[";
		for (Card card : result.keySet()) {
			response += String.format("{\"card_suit\":\"%s\",\"card_face\":\"%s\",\"remaining_cards\":\"%s\"},",
					card.getSuit(), card.getFace(), result.get(card));
		}
		response = result.size()>0? response.substring(0, response.length()-1):response;
		response += "]";
		return response;

	}

	public void shuffle(@PathParam("game_id") int gameId) {
		gamesTable.get(gameId).shuffle();
		String.format("Game[%s] is shuffled..", gameId);

	}

}
