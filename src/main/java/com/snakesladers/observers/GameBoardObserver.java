package com.snakesladers.observers;

import java.util.Map;

public interface GameBoardObserver {
	
	void updateGameBoard(Map<String, Integer> gameBoard);

}
