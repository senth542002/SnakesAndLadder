package com.snakesladers;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class GameTest {
	
	private Map<Integer,Integer> rules;
	private PlayerChooser playerChooser;
	private PlayerMover playerMover;
	private RulesApplier rulesApplier;
	private GameCompletionChecker gameCompletionChecker;

	@Before
	public void setup() {
		rules = Map.of(27, 1, 21, 9, 17, 4, 19, 7, 11, 26, 3, 22, 5, 8, 20, 29);
		gameCompletionChecker = new GameCompletionChecker();
		rulesApplier = new RulesApplier(gameCompletionChecker);
		playerMover = new PlayerMover(rulesApplier);
		playerChooser = new PlayerChooser(playerMover);
	
	}

	@Test
	public void playTheGameForSinglePlayer() throws Exception {
		Game game = new Game(List.of("Player1"), rules, 30, playerChooser);
		
		applyObservers(game);
		
		game.play();		
	}

	private void applyObservers(Game game) {
		game.addGameBoardObserver(playerMover);
		game.addGameBoardObserver(rulesApplier);
		game.addGameBoardObserver(gameCompletionChecker);
		
		game.addSizeObserver(playerMover);
		
		game.addRulesObserver(rulesApplier);
		
		game.addPlayersObserver(playerChooser);
		
		playerChooser.addCurrentPlayerObserver(playerMover);
		playerChooser.addCurrentPlayerObserver(rulesApplier);
		playerChooser.addCurrentPlayerObserver(gameCompletionChecker);
		
		playerMover.addGameBoardObserver(rulesApplier);
		playerMover.addGameBoardObserver(gameCompletionChecker);
		
		rulesApplier.addGameBoardObserver(playerMover);
		rulesApplier.addGameBoardObserver(gameCompletionChecker);
	}
	@Test
	public void playTheGameForTwoPlayers() {
		Game game = new Game(List.of("Player1","Player2"), rules, 30, playerChooser);
		applyObservers(game);
		game.play();
		game.play();
	}

	@Test
	public void playTheGameForThreePlayers() throws Exception {
		Game game = new Game(List.of("Player1","Player2","Player3"), rules, 30, playerChooser);
		game.play();
	}

}
