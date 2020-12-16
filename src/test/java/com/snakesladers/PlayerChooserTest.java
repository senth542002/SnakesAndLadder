package com.snakesladers;

import static org.mockito.Mockito.doNothing;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PlayerChooserTest {

	@Mock
	private SLChain playerMover;

	private PlayerChooser playerChooser;
	
	@Before
	public void setup() {		
		playerChooser = new PlayerChooser(playerMover);
		doNothing().when(playerMover).compute();
	}

	@Test
	public void shouldFetchPlayer1_whenThereIsNoCurrentPlayer() throws Exception {
		playerChooser.updatePlayers(List.of("Player1","Player2"));
		playerChooser.compute();
	}
	
	@Test
	public void shouldFetchPlayer2_whenTheCurrentPlayerIsPlayer1() throws Exception {
		playerChooser.updateCurrentPlayer("Player1");
		playerChooser.updatePlayers(List.of("Player1","Player2"));
		playerChooser.compute();
	}
	
	@Test
	public void shouldFetchPlayer1_whenTheCurrentPlayerIsPlayer2() throws Exception {
		playerChooser.updateCurrentPlayer("Player2");
		playerChooser.updatePlayers(List.of("Player1","Player2"));
		playerChooser.compute();
	}

}
