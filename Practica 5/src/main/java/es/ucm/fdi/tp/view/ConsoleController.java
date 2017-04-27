package es.ucm.fdi.tp.view;

import java.util.List;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.control.GameController;
import es.ucm.fdi.tp.mvc.GameTable;

public class ConsoleController< S extends GameState<S,A>, A extends GameAction<S,A> > extends GameController implements Runnable{

	private List<GamePlayer> players;
	private GameTable<S,A> game;
	//TODO aniadido para UN ARGUMENTO MAS EN EL CONTRUCTOR
	//private GameState<S, A> initialState;
	
	public ConsoleController(List<GamePlayer> jugadores, GameTable<S,A> g/*, GameState<S, A> estado*/) {
		players = jugadores;
		game = g;
		//initialState = estado;
		
	}
	
	private void playGame(){
		super.startGame();
	}
	
	/*@Override
	public void run() {
		int playerCount = 0;
		for (GamePlayer p : players) {
			p.join(playerCount++); // welcome each player, and assign
									// playerNumber
		}
		@SuppressWarnings("unchecked")
		S currentState = (S) initialState;
		System.out.println("Original State:\n" + currentState);
		int cont= 1;
		while (!currentState.isFinished()) {
			// request move
			A action = players.get(currentState.getTurn()).requestAction(currentState);
			// apply move
			currentState = action.applyTo(currentState);
			System.out.println("After " + cont + " action:\n" + currentState);
			cont++;
			if (currentState.isFinished()) {
				// game over
				String endText = "The game ended: ";
				int winner = currentState.getWinner();
				if (winner == -1) {
					endText += "draw!";
				} else {
					endText += "player " + (winner + 1) + " (" + players.get(winner).getName() + ") won!";
				}
				System.out.println(endText);
			}
		}
	}*/

	/**more methods..**/
}
