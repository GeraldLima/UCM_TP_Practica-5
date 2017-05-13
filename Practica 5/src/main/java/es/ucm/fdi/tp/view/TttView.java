package es.ucm.fdi.tp.view;


import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObservable;
import es.ucm.fdi.tp.ttt.TttAction;
import es.ucm.fdi.tp.ttt.TttState;
import es.ucm.fdi.tp.was.WolfAndSheepAction;
import es.ucm.fdi.tp.was.WolfAndSheepState;

public class TttView <S extends GameState<S,A>, A extends GameAction<S,A>> 
					extends RectBoardGameView <TttState, TttAction>{
	
	/**
	 * Contructora que se encarga de crear la vista para este juego
	 * @param game El juego en el que estoy
	 * @param cameCtrl El controlador
	 * @param playerId Vista en la que estoy
	 * @param random Jugador random
	 * @param smart Jugador inteligente
	 */
	public TttView(int playerId, GamePlayer random, GamePlayer smart,
					GameObservable<TttState, TttAction> game, GameController<TttState, TttAction> gameCtrl){
		super(playerId, random, smart, game, gameCtrl);
	}
	
	
	@Override
	public void notifyEvent(GameEvent<TttState, TttAction> e) {		
		
		switch (e.getType()) {
		case Start : 
			onGameStart(board, nameGame, getPlayers(), e.getState().getTurn()); 
			break;
		case Info : 
			onMoveEnd(board, e.getState().getTurn(), false); 
			break;
		case Change : 
			onChangeTurn(board, e.getState().getTurn()); 
			break;
		case Stop : 
			onGameOver(board, e.getState(), e.getState().getWinner()); 
			break;
		case Error : 
			onError(e.toString()); 
			break;
		default: break;
		}
		/*
		if (e.getType().equals(EventType.Start))
			onGameStart(super.board, super.nameGame, super.getPlayers(), e.getState().getTurn());
		else 
			if (e.getType().equals(EventType.Stop))
			onGameOver(super.board, e.getState(), e.getState().getWinner());
		else 
			if (e.getType().equals(EventType.Change))
			onChangeTurn(super.board, e.getState().getTurn());
		else 
			if (e.getType().equals(EventType.Info))
			onMoveEnd(super.board, e.getState().getTurn(), false);
		else
			if (e.getType().equals(EventType.Error))
				onError(e.toString());
				*/
	}	
	
	@Override
	public void onGameStart(int[][] board, String gameDesc, List<Integer> pieces, int turn) {
		// TODO super.onGameStart(board, gameDesc, pieces, turn);
		super.boardComp.onGameStart(this.getBoard(), gameDesc, pieces, turn);
	}

	@Override
	public void onGameOver(int[][] board, TttState state, int winner) {
		// TODO super.onGameOver(board, state, winner);
		super.boardComp.onGameOver(board, state, winner);
	}

	@Override
	public void onMoveStart(int[][] board, int turn) {		
	}

	@Override
	public void onMoveEnd(int[][] board, int turn, boolean success) {
		// TODO super.onMoveEnd(board, turn, success);
		super.boardComp.onMoveEnd(board, turn, success);
	}

	@Override
	public void onChangeTurn(int[][] board, int turn) {
		// TODO super.onChangeTurn(board, turn);
		super.boardComp.onChangeTurn(board, turn);
	}

	@Override
	public void onError(String msg) {
		// TODO super.onError(msg);
	}
	
	////////////////////de RectBoardGameView///////////////////////////
	@Override
	protected void handleMouseClick(int row, int col, int mouseButton, boolean buttonR) {
		if(editable){
			if(!buttonR){
				if (gameControl.getGame().getState().at(row, col) == -1) {
					TttAction action = new TttAction(gameControl.getPlayerId(), row, col);
					decideMakeManualMove(action);
				}
				else{
					JFrame frame = new JFrame();
			        JOptionPane.showMessageDialog(frame, "position (" + row + "," + col + ") is already occupied!", 
			        "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
			else{
				JFrame frame = new JFrame();
		        JOptionPane.showMessageDialog(frame, "Incorrect mouse button", 
		        "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
		else{
			JFrame frame = new JFrame();
	        JOptionPane.showMessageDialog(frame, "The board is not available!", 
	        "ERROR", JOptionPane.ERROR_MESSAGE);
		}		
	}		

	/**
	 * Devuelve el tablero de juego
	 */
	@Override
	protected int[][] getBoard() {
		return super.gameControl.getGame().getState().getBoard();
	}
	////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	protected String getNameGame() {
		return "Game - TickTackToe ";
	}

	@Override
	protected boolean isNameGame() {
		return true;
	}

	/*@Override
	protected void initBoardGUI() {		
	}*/

	@Override
	protected void activateBoard() {
		super.editable = true;
	}

	@Override
	protected void deActivateBoard() {
		super.editable = false;
	}

	

}
