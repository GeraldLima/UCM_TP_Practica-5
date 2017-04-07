package es.ucm.fdi.tp.control;

import java.util.List;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameTable;

public class ConsoleController< S extends GameState<S,A>, A extends GameAction<S,A> > implements Runnable{

	
	public ConsoleController(List<GamePlayer> players, GameTable<S,A> game) {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
