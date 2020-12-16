package com.snakesladers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.snakesladers.observers.CurrentPlayerObserver;
import com.snakesladers.observers.PlayersObserver;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlayerChooser implements CurrentPlayerObserver, SLChain, PlayersObserver {
	
	private SLChain playerMover;
	
	public PlayerChooser(SLChain playerMover) {
		this.playerMover = playerMover;
	}


	private List<CurrentPlayerObserver> currentPlayerObservers = new ArrayList<>();
	private String currentPlayer;
	private List<String> players;

	public void notify(Map<String, Integer> gameBoard, String currentPlayer, Map<Integer, Integer> rules, int size,
			List<String> players) {
		
		List<String> fetchPlayers = players.stream().collect(Collectors.toList());
		
		String nextPlayer = fetchPlayers.get(fetchPlayers.indexOf(currentPlayer) < fetchPlayers.size() -1 ? fetchPlayers.indexOf(this.currentPlayer) + 1 : 0);
		
		log.info(nextPlayer+" is the next player");
		
//		sLObserver.notify(gameBoard, nextPlayer, rules, size, players);
		
		currentPlayerObservers.forEach(observer -> observer.updateCurrentPlayer(nextPlayer));
	}


	@Override
	public void updateCurrentPlayer(String currentPlayer) {
		this.currentPlayer = currentPlayer;
	}


	@Override
	public void compute() {
		
		List<String> fetchPlayers = players.stream().collect(Collectors.toList());
		
		String nextPlayer = fetchPlayers.get(fetchPlayers.indexOf(currentPlayer) < fetchPlayers.size() -1 ? fetchPlayers.indexOf(this.currentPlayer) + 1 : 0);
		
		log.info(nextPlayer+" is the next player");
		
		this.currentPlayer = nextPlayer;
		
		currentPlayerObservers.forEach(observer -> observer.updateCurrentPlayer(nextPlayer));
		
		this.playerMover.compute();
	}


	@Override
	public void updatePlayers(List<String> players) {
		this.players = players;
	}


	public void addCurrentPlayerObserver(CurrentPlayerObserver playerMover) {
		this.currentPlayerObservers.add(playerMover);
	}

}
