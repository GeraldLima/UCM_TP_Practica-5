package es.ucm.fdi.tp.was;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;

public class WolfAndSheepPlayer implements GamePlayer{

	private int player;
	private int posPlayer;
	private int [] players;
	private int[][] board;
	/*******************/
	WolfAndSheepAction action;
	private int fil, col, filFin, colFin;
	
	public WolfAndSheepPlayer(int[] jugadores, int jugador,int[][] tablero) {
		player = jugador;
		players = jugadores;
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero.length; j++) {
				board[i][j] = tablero[i][j];
			}
		}
	}
	
	@Override
	public void join(int playerNumber) {
		players[players.length] = playerNumber;
		
	}

	@Override
	public int getPlayerNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends GameState<S, A>, A extends GameAction<S, A>> A requestAction(S state) {
		action = new WolfAndSheepAction(player, fil, col, filFin, colFin);
		
		return action;//TODO ERRRORR
	}
	public void aplyMove(int fi, int col, int fFin, int cFin){
		fil = fi;
		col = col;
		filFin = fFin;
		colFin = cFin;
	}

}
