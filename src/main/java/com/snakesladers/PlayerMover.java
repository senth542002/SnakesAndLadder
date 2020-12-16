package com.snakesladers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.snakesladers.observers.CurrentPlayerObserver;
import com.snakesladers.observers.GameBoardObserver;
import com.snakesladers.observers.SizeObserver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlayerMover implements SLChain, GameBoardObserver, CurrentPlayerObserver, SizeObserver {
	
	SLChain rulesApplier;
	private Map<String, Integer> gameBoard;
	private String currentPlayer;
	private List<GameBoardObserver> gameBoardObservers = new ArrayList<>();
	private Integer size;
	
	public PlayerMover(SLChain rulesApplier) {
		this.rulesApplier = rulesApplier;
	}

	@Override
	public void compute() {
		
		if(gameBoard.get(currentPlayer) < 30) {			
			int moveValue = new Random().nextInt((6 - 1) + 1) + 1;
			log.info("The die value is "+moveValue);
			
			Integer newPosition = gameBoard.get(currentPlayer) + moveValue;
			
			if(newPosition <= size) {			
				gameBoard.put(currentPlayer, newPosition);
				log.info("New position is "+newPosition);
			}
			
		}
		
		this.gameBoardObservers.forEach(observer -> observer.updateGameBoard(gameBoard));
		
		this.rulesApplier.compute();
	}

	@Override
	public void updateGameBoard(Map<String, Integer> gameBoard) {
		this.gameBoard = gameBoard;
	}

	@Override
	public void updateCurrentPlayer(String currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public void addGameBoardObserver(GameBoardObserver gameBoardObserver) {
		this.gameBoardObservers.add(gameBoardObserver);
	}

	@Override
	public void setSize(Integer size) {
		this.size = size;
	}

}
