package es.ucm.fdi.tp.gui;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.control.GameViewCtrl;

public abstract class GameView< S extends GameState<S,A>, A extends GameAction<S,A> > {
	
	public abstract void enable();
	public abstract void update(S state);
	public abstract void showInfoMessage(String msg);
	public abstract void setGameVewCtrl(GameViewCtrl <S,A> guiCtrl);

}
