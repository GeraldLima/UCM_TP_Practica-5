package es.ucm.fdi.tp.gui;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.control.GameController;
import es.ucm.fdi.tp.control.GameViewCtrl;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObserver;

public class GameWindow< S extends GameState<S,A>, A extends GameAction<S,A> > 
													implements GameViewCtrl<S, A>, 
																GameObserver<S, A>{

	public GameWindow(int playerId, GamePlayer randPlayer, GamePlayer smartPalyer, GameView<S,A> gameView, GameController<S,A> gameCtrl){
		
		
	}
	
	@Override
	public void notifyEvent(GameEvent<S, A> e) {
		// TODO Auto-generated method stub
		
	}
	
	

	@Override
	public void userActionAvailable(GameAction a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void randomActionButtonPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void smartActionButtonPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restartButtonPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void quitButtonPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerModeSelected() {
		// TODO Auto-generated method stub
		
	}

}
