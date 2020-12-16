package com.snakesladers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.snakesladers.observers.GameBoardObserver;
import com.snakesladers.observers.PlayersObserver;
import com.snakesladers.observers.RulesObserver;
import com.snakesladers.observers.SizeObserver;

public class Game {

	private List<String> players;
	private int size;
	public Map<String,Integer> gameBoard;
	private SLChain playerChooser;
	private Map<Integer, Integer> rules;
	
	private List<GameBoardObserver> gameBoardObservers = new ArrayList<>();
	private List<PlayersObserver> playersObservers = new ArrayList<>();
	private List<RulesObserver> rulesObservers = new ArrayList<>();
	private List<SizeObserver> sizeObservers = new ArrayList<>();
	
//	private SLChain slChain = new PlayerChooser(new PlayerMover(new RulesApplier(new GameCompletionChecker())));

	public Game(List<String> players, Map<Integer, Integer> rules, int size, SLChain playerChooser) {
		this.players = players;
		this.rules = rules;
		this.size = size;
		this.playerChooser = playerChooser;
		
		initializeGameBoard(players,size);
		
	}
	
	public void addGameBoardObserver(GameBoardObserver gameBoardObserver) {
		this.gameBoardObservers.add(gameBoardObserver);
	}

	public void addPlayersObserver(PlayersObserver playersObserver) {
		this.playersObservers.add(playersObserver);
	}

	public void addRulesObserver(RulesObserver rulesObserver) {
		this.rulesObservers.add(rulesObserver);
	}

	public void addSizeObserver(SizeObserver sizeObserver) {
		this.sizeObservers.add(sizeObserver);
	}

	private void initializeGameBoard(List<String> players, int size) {
		this.gameBoard = new HashMap<>(size);
		players.forEach(player -> gameBoard.put(player, 0));
	}

	public void play() {
		playersObservers.forEach(observer -> observer.updatePlayers(players));
		sizeObservers.forEach(observer -> observer.setSize(size));
		rulesObservers.forEach(observer -> observer.setRules(rules));
		gameBoardObservers.forEach(observer -> observer.updateGameBoard(gameBoard));
		
		playerChooser.compute();
	}

}
