package com.snakesladers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.snakesladers.observers.CurrentPlayerObserver;
import com.snakesladers.observers.GameBoardObserver;
import com.snakesladers.observers.RulesObserver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RulesApplier implements SLChain, GameBoardObserver, CurrentPlayerObserver, RulesObserver {
	
	SLChain gameCompletionChecker;
	private Map<String, Integer> gameBoard;
	private String currentPlayer;
	private Map<Integer, Integer> rules;
	
	private List<GameBoardObserver> gameBoardObservers = new ArrayList<>();
	
	public RulesApplier(SLChain gameCompletionChecker) {
		this.gameCompletionChecker = gameCompletionChecker;
	}

	public void notify(Map<String, Integer> gameBoard, String currentPlayer, Map<Integer, Integer> rules, int size,
			List<String> players) {
		
		Integer newPosition = rules.get(gameBoard.get(currentPlayer));
		
		if(newPosition != null) {			
			gameBoard.put(currentPlayer, rules.get(gameBoard.get(currentPlayer)));
		}
		
		log.info("The position of the "+currentPlayer+" after applying rules is "+gameBoard.get(currentPlayer));
		
//		this.sLObserver.notify(gameBoard, currentPlayer, rules, size, players);

	}

	@Override
	public void compute() {
		
		Integer newPosition = rules.get(gameBoard.get(currentPlayer));
		
		if(newPosition != null) {			
			gameBoard.put(currentPlayer, rules.get(gameBoard.get(currentPlayer)));
		}
		
		log.info("The position of the "+currentPlayer+" after applying rules is "+gameBoard.get(currentPlayer));
		
		this.gameBoardObservers.forEach(observer -> observer.updateGameBoard(gameBoard));
		
		this.gameCompletionChecker.compute();
	}

	@Override
	public void setRules(Map<Integer, Integer> rules) {
		this.rules = rules;
	}

	@Override
	public void updateCurrentPlayer(String currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	@Override
	public void updateGameBoard(Map<String, Integer> gameBoard) {
		this.gameBoard = gameBoard;
	}
	
	public void addGameBoardObserver(GameBoardObserver gameBoardObserver) {
		this.gameBoardObservers.add(gameBoardObserver);
	}

}
