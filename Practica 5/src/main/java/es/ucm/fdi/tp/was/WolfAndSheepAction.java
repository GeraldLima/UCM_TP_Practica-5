package es.ucm.fdi.tp.was;

import es.ucm.fdi.tp.base.model.GameAction;

public class WolfAndSheepAction implements GameAction<WolfAndSheepState, WolfAndSheepAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int player;
	private int filaOrigen;
	private int colOrigen;
	private int filaDestino;
	private int colDestino;
	
	public WolfAndSheepAction(int jugador, int fila, int columna, int filaDes, int colDes) {
		
		this.player = jugador;
		this.filaOrigen = fila;
		this.colOrigen = columna;
		this.filaDestino = filaDes;
		this.colDestino = colDes;
	}
	
		
	@Override
	public int getPlayerNumber() {
		return player;
	}
	//TODO requestMove

	@Override
	public WolfAndSheepState applyTo(WolfAndSheepState state) {
		
		 if (player != state.getTurn()) {
	            throw new IllegalArgumentException("Not the turn of this player");
	        }
		 
		 int[][] board = state.getBoard();
	        board[filaDestino][colDestino] = player;
	        board[filaOrigen][colOrigen] = -1;
	        
	        // update state
	        WolfAndSheepState next = new  WolfAndSheepState(state, board, false, -1);
	        
	        if (next.ganador(board, state.getTurn())) {
	            next = new WolfAndSheepState(state, board, true, state.getTurn());
	        }
	        
	        else if ((next.getTurn() == 0 && next.validActions(next.getTurn()).isEmpty()))
	        	next = new WolfAndSheepState(state, board, true, state.getTurn());
	        
	        else if (next.getTurn() == 1 && !state.ganador(board, state.getTurn()) && next.validActions(next.getTurn()).isEmpty())
	        	next = new WolfAndSheepState(next, board, false, -1);
	        
	        else {	        	
	            next = new WolfAndSheepState(state, board, false, -1);
	        }
	        return next;
		
	}

	public int getFil() {
		return filaDestino;
	}

	/*public void setFil(int fil) {
		this.fil = fil;
	}*/

	public int getCol() {
		return colDestino;
	}

	/*public void setCol(int col) {
		this.col = col;
	}*/
	
    public String toString() {
    	
        return "place " + player + " at (" + filaDestino + ", " + colDestino + ")";
    }
	
	
}
