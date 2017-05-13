package es.ucm.fdi.tp.view;

import java.util.List;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObservable;
import es.ucm.fdi.tp.mvc.GameObserver;

public class ConsoleView< S extends GameState<S,A>, A extends GameAction<S,A> > implements GameObserver<S,A> {

	/**
	 * Contructora que añade como observador la vista consola
	 * @param gameTable
	 */
	public ConsoleView(GameObservable<S,A> gameTable){
		gameTable.addObserver(this);
	}
	
	
	@Override
	public void notifyEvent(GameEvent<S, A> e) {
		
		System.out.println(e.toString());
	}


	@Override
	public void onGameStart(int[][] board, String gameDesc,
			List<Integer> pieces, int turn) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onGameOver(int[][] board, S state, int winner) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onMoveStart(int[][] board, int turn) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onMoveEnd(int[][] board, int turn, boolean success) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onChangeTurn(int[][] board, int turn) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onError(String msg) {
		// TODO Auto-generated method stub
		
	}
	


}
