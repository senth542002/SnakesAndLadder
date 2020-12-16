package com.snakesladers;

import java.util.Map;

import com.snakesladers.observers.CurrentPlayerObserver;
import com.snakesladers.observers.GameBoardObserver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameCompletionChecker implements SLChain, CurrentPlayerObserver, GameBoardObserver {
	
	private String currentPlayer;
	private Map<String, Integer> gameBoard;

	@Override
	public void compute() {
		
		if(gameBoard.get(currentPlayer) >= 30 ) {
			log.info(currentPlayer+" won the game!!!");

			System.exit(1);
		} 
	}

	@Override
	public void updateCurrentPlayer(String currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	@Override
	public void updateGameBoard(Map<String, Integer> gameBoard) {
		this.gameBoard = gameBoard;
	}

}
