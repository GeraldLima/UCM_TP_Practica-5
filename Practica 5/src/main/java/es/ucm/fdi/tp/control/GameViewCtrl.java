package es.ucm.fdi.tp.control;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.gui.GameWindow;

public interface GameViewCtrl <S extends GameState<S,A>, A extends GameAction<S,A>> {
	
	public void userActionAvailable(GameAction a);
	public void randomActionButtonPressed();
	public void smartActionButtonPressed();
	public void restartButtonPressed();
	public void quitButtonPressed();
	public void playerModeSelected(/*GameWindow aaaaaa*/);
	
}
