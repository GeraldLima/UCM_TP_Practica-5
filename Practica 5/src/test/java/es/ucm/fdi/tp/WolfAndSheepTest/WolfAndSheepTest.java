package es.ucm.fdi.tp.WolfAndSheepTest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.junit.Test;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.launcher.Main;
import es.ucm.fdi.tp.was.WolfAndSheepAction;
import es.ucm.fdi.tp.was.WolfAndSheepState;

public class WolfAndSheepTest {	
	
	@Test
	public void test() throws IOException{
		
		//TODO
		WolfAndSheepState state = new WolfAndSheepState(8);

		File temp = Files.createTempFile("game", ".state").toFile();
		System.out.println("Game saved as " + temp.getAbsolutePath() + " ...");
		state.save(temp);
		
		//TODO
		WolfAndSheepState load = (WolfAndSheepState) GameState.load(temp);
		
		System.out.println("Saved:\n" + state.toString());
		System.out.println("Loaded:\n" + load.toString());
		assertEquals("loaded == saved", load.toString(), state.toString());
		
		//TODO mas concreto******************************
		Scanner s = new Scanner(System.in);
		List<GamePlayer> players = new ArrayList<GamePlayer>();
		//WolfAndSheepState game = (WolfAndSheepState) Main.createInitialState("WAS");
		players.add(Main.createPlayer("was", "rand", "WOLF", s));
		players.add(Main.createPlayer("was", "rand", "SHEEPS", s));

		int playerCount, i; playerCount = 0; i = 2;
		for (GamePlayer p : players) {
			p.join(playerCount++); // welcome each player, and assign playerNumber
		}
		
		/************************************************/
		
		load = takeRandomAction(load);
		System.out.println("Loaded (after 1 move):\n" + load.toString());		

		while (!playGame(load, players)){
			load = takeRandomAction(load);
			System.out.println("Loaded (after " +i+ " move):\n" + load.toString());
			i++;
		}
		
	}
	
	private static <S extends GameState<S,A>, A extends GameAction<S,A>> S takeRandomAction(S state) {
		
		List<A> actions = state.validActions(state.getTurn());//null;		
		//actions = state.validActions(state.getTurn());		
		return actions.get(new Random().nextInt(actions.size())).applyTo(state);
	}
	
	private boolean playGame(WolfAndSheepState currentState, List<GamePlayer> players) {

		if (!currentState.isFinished()) {
			WolfAndSheepAction action = players.get(currentState.getTurn()).requestAction(currentState);

			currentState = action.applyTo(currentState);			
			if (currentState.isFinished()) {
				// game over
				String endText = "The game ended: ";
				int winner = currentState.getWinner();
				if (winner == -1) {
					endText += "draw!";
				} else {
					endText += "player " + (winner + 1) + " (" + players.get(winner).getName() + ") won!";
				}
				System.out.println("After action:\n" + currentState);
				System.out.println(endText);
				
				return true;
			}
			return false;
		}
		else
			return true;
	}
	

}
