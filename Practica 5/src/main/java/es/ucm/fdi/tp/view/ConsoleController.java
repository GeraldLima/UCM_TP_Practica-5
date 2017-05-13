package es.ucm.fdi.tp.view;

import java.util.List;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameTable;

public class ConsoleController< S extends GameState<S,A>, A extends GameAction<S,A> > implements Runnable{

	private List<GamePlayer> players;
	private GameTable<S,A> game;
	
	
	public ConsoleController(List<GamePlayer> jugadores, GameTable<S,A> game) { //gmetable modelo
		this.players = jugadores;
		this.game = game;
		
	}
	
	@Override
	public void run() {
		
		int playerCount = 0;
		for (GamePlayer p : players) {
			p.join(playerCount++); 
	}
		
		game.start();
		
		while (!game.getState().isFinished()) {
			
			GamePlayer jugador  = players.get(game.getState().getTurn());//consigo el jugador	
			
			//consigo la accion del jugador
			A action =jugador.requestAction(game.getState());
			
			game.execute(action);

			if (game.getState().isFinished()) {
				game.stop();
			}
		}
	}

	
	
}