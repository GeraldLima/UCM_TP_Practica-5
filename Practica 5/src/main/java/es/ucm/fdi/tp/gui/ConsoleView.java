package es.ucm.fdi.tp.gui;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObserver;

public class ConsoleView< S extends GameState<S,A>, A extends GameAction<S,A> > implements GameObserver<S,A> {

	@Override
	public void notifyEvent(GameEvent<S, A> e) {
		// TODO Auto-generated method stub
		
	}

}
