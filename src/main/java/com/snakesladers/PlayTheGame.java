package com.snakesladers;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PlayTheGame {
	
	private static Map<Integer,Integer> rules;
	private static PlayerChooser playerChooser;
	private static PlayerMover playerMover;
	private static RulesApplier rulesApplier;
	private static GameCompletionChecker gameCompletionChecker;
	
	public static void main(String[] args) {

		setup();
		
		Game game = new Game(List.of("Player1","Player2"), rules, 30, playerChooser);
		applyObservers(game);
		
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNextLine()) {
			game.play();
			scanner.nextLine();
		}
		
		scanner.close();
	}
	
	public static void setup() {
		rules = Map.of(27, 1, 21, 9, 17, 4, 19, 7, 11, 26, 3, 22, 5, 8, 20, 29);
		gameCompletionChecker = new GameCompletionChecker();
		rulesApplier = new RulesApplier(gameCompletionChecker);
		playerMover = new PlayerMover(rulesApplier);
		playerChooser = new PlayerChooser(playerMover);
	
	}
	
	private static void applyObservers(Game game) {
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

}
