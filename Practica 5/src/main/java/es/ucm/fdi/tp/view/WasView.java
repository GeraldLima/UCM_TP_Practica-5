package es.ucm.fdi.tp.view;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameEvent.EventType;
import es.ucm.fdi.tp.mvc.GameObservable;
import es.ucm.fdi.tp.was.WolfAndSheepAction;
import es.ucm.fdi.tp.was.WolfAndSheepState;

@SuppressWarnings("serial")
public class WasView <S extends GameState<S,A>, A extends GameAction<S,A>> 	
					extends RectBoardGameView <WolfAndSheepState, WolfAndSheepAction>{

	private int filIni, colIni, filFin, colFin;
	
	/**
	 * Contructora que se encarga de crear la vista para este juego
	 * @param game El juego en el que estoy
	 * @param cameCtrl El controlador
	 * @param playerId Vista en la que estoy
	 * @param random Jugador random
	 * @param smart Jugador inteligente
	 */
	public WasView(int playerId, GamePlayer randPlayer, GamePlayer smartPalyer, 
					GameObservable<WolfAndSheepState, WolfAndSheepAction> game, GameController<WolfAndSheepState, WolfAndSheepAction> gameCtrl) {
		super(playerId, randPlayer, smartPalyer, game, gameCtrl);
		
	}
	

	@Override
	public void notifyEvent(GameEvent<WolfAndSheepState, WolfAndSheepAction> e) {
		
		
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
		super.onGameStart(board, gameDesc, pieces, turn);
		super.boardComp.onGameStart(this.getBoard(), gameDesc, pieces, turn);
	}

	@Override
	public void onGameOver(int[][] board, WolfAndSheepState state, int winner) {
		super.onGameOver(board, state, winner);
		super.boardComp.onGameOver(board, state, winner);
	}

	@Override
	public void onMoveStart(int[][] board, int turn) {		
	}

	@Override
	public void onMoveEnd(int[][] board, int turn, boolean success) {
		super.onMoveEnd(board, turn, success);
		super.boardComp.onMoveEnd(board, turn, success);
	}

	@Override
	public void onChangeTurn(int[][] board, int turn) {
		super.onChangeTurn(board, turn);
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
			int actualPlayer = gameControl.getGame().getState().at(row, col);
			//super.lastRow = row;
			//super.lastCol = col;
			if(!super.cancelMove(clicked) && !buttonR){
				if(actualPlayer == super.getPlayerId()){
					this.filIni = row;
					this.colIni = col;
					panelStatusMessages.addMessage("You have selected (" + filIni + "," + colIni + ") as origin cell");
					panelStatusMessages.addMessage("Click on an destination cell");
					clicked = true;
				}
				else{
					this.filIni = row;
					this.colIni = col;
					JFrame frame = new JFrame();
			        JOptionPane.showMessageDialog(frame, "you don't have a piece in position (" + filIni + "," + colIni + ")!", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(clicked  && !buttonR){
				if(actualPlayer == -1){
					//primer jugador
					if ((Math.abs(row - filIni) <= 1) && (Math.abs(col - colIni) <= 1)){
						panelStatusMessages.addMessage("You have selected (" + filFin + "," + colFin + ") as destination cell");
						if (getPlayerId() == 0){
							this.filFin = row;
							this.colFin = col;
							
							WolfAndSheepAction action = new WolfAndSheepAction(gameControl.getPlayerId(), filIni, colIni, filFin, colFin);
							decideMakeManualMove(action);
							clicked = false;
						}
						//segundo jugador
						else if (getPlayerId() == 1){
							this.filFin = row;
							this.colFin = col;						
							WolfAndSheepAction action = new WolfAndSheepAction(gameControl.getPlayerId(), filIni, colIni, filFin, colFin);
							decideMakeManualMove(action);
							clicked = false;
						}
					}					
					else{
						JFrame frame = new JFrame();
				        JOptionPane.showMessageDialog(frame, "the piece in ("+ filIni + "," + colIni + ") can not move there!", "ERROR", JOptionPane.ERROR_MESSAGE);
				    }
				}
				else if(actualPlayer == -2){
					JFrame frame = new JFrame();
			        JOptionPane.showMessageDialog(frame, "the piece in ("+ filIni + "," + colIni + ") can not move there!", "ERROR", JOptionPane.ERROR_MESSAGE);
			    }
				else{
					this.filFin = row;
					this.colFin = col;
					JFrame frame = new JFrame();
			        JOptionPane.showMessageDialog(frame, "position (" + row + "," + col + ") is already occupied!", "ERROR", JOptionPane.ERROR_MESSAGE);
			   }
			}
			//click en boton derecho
			else if(clicked && buttonR){
				if((row == this.filIni) && (col == this.colIni)){
					panelStatusMessages.addMessage("You have canceled (" + filIni + "," + colIni + ") as origin cell");
					panelStatusMessages.addMessage("Click on an origin piece");
					clicked = false;
				}
				else{
					JFrame frame = new JFrame();
			        JOptionPane.showMessageDialog(frame, "You have not clicked position (" + filIni + "," + colIni + ") as origin cell", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
			else{
				this.filIni = row;
				this.colIni = col;
				JFrame frame = new JFrame();
		        JOptionPane.showMessageDialog(frame, "You have not selected as origin cell", "ERROR", JOptionPane.ERROR_MESSAGE);
				clicked = false;
			}
		}
		//tablero no editable
		else{
			JFrame frame = new JFrame();
	        JOptionPane.showMessageDialog(frame, "The board is not available!", "ERROR", JOptionPane.ERROR_MESSAGE);
	        clicked = false;
		}
	}

	@Override
	protected int[][] getBoard() {
		return super.gameControl.getGame().getState().getBoard();
	}
	////////////////////////////////////////////////////////////////////////////////////////

	@Override
	protected String getNameGame() {
		return "Game - Wolf and Sheep ";
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