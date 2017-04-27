package es.ucm.fdi.tp.gui;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.control.GameController;
import es.ucm.fdi.tp.control.GameViewCtrl;

public class TttView <S extends GameState<S,A>, A extends GameAction<S,A>> 	extends RectBoardGameView{

	
	
	public TttView(int playerId, GamePlayer randPlayer, GamePlayer smartPalyer, GameView gameView,
			GameController gameCtrl) {
		super(playerId, randPlayer, smartPalyer, gameView, gameCtrl);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void enable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameState state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showInfoMessage(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setGameVewCtrl(GameViewCtrl guiCtrl) {
		// TODO Auto-generated method stub
		
	}
 
}
