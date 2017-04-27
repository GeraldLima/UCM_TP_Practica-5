package es.ucm.fdi.tp.gui;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.control.GameController;
import es.ucm.fdi.tp.control.GameViewCtrl;
import es.ucm.fdi.tp.extra.jboard.JBoard;

public abstract class RectBoardGameView< S extends GameState<S,A>, A extends GameAction<S,A> > 	extends GameView/*<S extends GameState<S,A>, A extends GameAction<S,A>>*/{

	
	protected JBoard jboard;
	protected GameViewCtrl<S,A> guiCtrl;
	
	public RectBoardGameView(int playerId, GamePlayer randPlayer, GamePlayer smartPalyer, 
			GameView<S,A> gameView, GameController<S,A> gameCtrl){
		super(blablabla NINGÚN ARGUMENTO SE CORRESPONDE CON los argumentos de GameView);
		
	}
	
	
}
