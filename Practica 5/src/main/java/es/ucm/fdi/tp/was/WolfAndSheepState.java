package es.ucm.fdi.tp.was;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.ttt.TttAction;

public class WolfAndSheepState extends GameState<WolfAndSheepState, WolfAndSheepAction> {

	private static final long serialVersionUID = 1L;

	private final int turno;
	private final int winner;
	private final boolean finished;
	private final int [][] board;
	private final int dim;
	
	final static int EMPTY = -1; //casilla negra donde se puede mover
	final static int CASILLA_ROJA = -2;	
	final static int WOLF = 0;
	final static int SHEEP = 1;
	
	public WolfAndSheepState(int dimension) {
		
		super(2);
		
        if (dimension != 8) {
        	
            throw new IllegalArgumentException("Expected dim to be 8 x 8");
        }

       
        this.dim = dimension;
        board = new int[dim][dim];
        inicializa();
        
        turno = 0;
        winner = -1;
        finished = false;
	}
	
	//esta constructora sirve para cuando empiezo a hacer movimientos
	public WolfAndSheepState(WolfAndSheepState prev, int[][] board, boolean finished, int ganador) {
			
		//prev estado anterior
	    	super(2);
	    	dim = prev.dim;
	    	this.board = board;
	    	
	    	turno = (prev.turno + 1) % 2;
	    	this.finished = finished;
	    	winner = ganador;
	    }

	public int siguienteTurno(){
		return  (turno + 1) % 2;
	}
/**
 * Método que te devuelve una casilla
 * @param row, la fila
 * @param col, la columna
 * @return devuelve la casilla dentro del tablero
 */
public int at(int row, int col) {
	 return board[row][col];
}

/**
 * 
 * @param action
 * @return
 */
public boolean isValid(TttAction action) {
      if (isFinished()) {
          return false;
       }
        return at(action.getRow(), action.getCol()) == EMPTY;
  }
	

public void inicializa(){
	
	for(int i = 0; i < dim; i ++){ //filas
		for(int j = 0; j < dim; j++){//columnas
			if(i % 2 == 0){ //si la fila es par
				if(j % 2 == 0){//si la columna es par
					board[i][j] = CASILLA_ROJA;
				}
				else{//si la columna es impar
					board[i][j] = EMPTY;
				}
			}
			else{//si la fila es impar
				if(j  % 2 == 0){
					board[i][j] = EMPTY;
				}
				else{
					board[i][j] = CASILLA_ROJA;
				}
			}
			if(i == 0 && j % 2 == 1)
			board[0][j] = SHEEP;
			
		}
		
		board[dim - 1][0] = WOLF;		
	}		
}
/*public void inicializa(){
	
	for(int i = 0; i < dim; i ++){ //filas
		for(int j = 0; j < dim; j++){//columnas
			if(i % 2 == 0){ //si la fila es par
				if(j % 2 == 0){//si la columna es par
					board[i][j] = CASILLA_ROJA;
				}
				else{//si la columna es impar
					board[i][j] = EMPTY;
				}
			}
			else{//si la fila es impar
				if(j  % 2 == 0){
					board[i][j] = EMPTY;
				}
				else{
					board[i][j] = CASILLA_ROJA;
				}
			}
			if(i == dim-2 && j>1 && j % 2 == 1)
				board[dim-2][j] = SHEEP;
			board[dim-4][1] = SHEEP;
			
		}
		
		board[dim - 1][0] = WOLF;
	}		
}
*/

	
	
	
	@Override
	public int getTurn() {
		
		return turno; //devuelver el turno
	}
	
	
	//TODO 
	public boolean ganadorLobo(int[][] board, int player) {
	    	
		boolean hayGanador = false;
	    //ArrayList<WolfAndSheepAction> moves = null;//new ArrayList<>(); //deja lista de movimientos vacia
		
	    for (int i = 0; !hayGanador && i < board.length; i++) {
	    	for (int j = 0; !hayGanador && j < board.length; j++) {
	    		
	    		if (i == 0 && board[i][j] == WOLF)//wolf
	    			hayGanador = true;   //gana lobo            	
	    	}
	    }
	        
	    return hayGanador;
	}
	
	public boolean ganadorOveja(int[][] board, int player) {
		
	    boolean hayGanador = false;
	    //ArrayList<WolfAndSheepAction> moves = null;//new ArrayList<>(); //deja lista de movimientos vacia    
	    
	    for (int i = 0; !hayGanador && i < board.length; i++) {
	    	for (int j = 0; !hayGanador && j < board.length; j++) {
	    		
	    		if(validActions(0).isEmpty()) { 
	    			hayGanador = true;
	    			
	    		}
	
	    	}		
			
	    }
	    return hayGanador;
	}
	
	public boolean ganador(int[][] board, int player) {
		
		if(player == WOLF){
			return ganadorLobo(board, player);
		}
		
		else if(player == SHEEP){
			return ganadorOveja(board, player);
		}
		else
			return false;
			
	}
	   
	/*public static boolean isDraw(int[][] board, int player) {
		
	    boolean empate = false;
	    
	    for (int i=0; ! empate && i<board.length; i++) {
	        for (int j=0; ! empate && j<board.length; j++) {
	            if (i == board.length - 1 && board[i][j] == SHEEP) {
	                empate = true;
	            }
	        }
	    }
	    return empate;
	}*/
	
	
	/**
	 * Método que se encarga de controlar los límites del tablero
	 * @param i, la fila
	 * @param j, la columna
	 * @return true si esta en los limites, false en caso contrario
	 */
	private boolean limitesTablero(int i, int j){
		return ((i >= 0) && (i < dim) && (j >= 0) && (j < dim));
	}
	
	private void compruebaMovimientoWolf(ArrayList<WolfAndSheepAction> moves, int player, int posFil, int posCol){
		int fil = posFil-1;
		if (fil < 0)
			fil = 0;
		int col = posCol-1;
		if (col < 0)
			col = 0;
		
		while (fil <= posFil+1){
			while (col <= posCol+1 /*&& !lobitoEncontrado*/){
				if (fil < dim && col < dim) //por si se pasa de los limites
					if (fil != posFil && col != posCol && board[fil][col] == -1){					
						moves.add(new WolfAndSheepAction(player, posFil, posCol, fil, col));
					}
					col++;
			}
			col=0;
			fil++;
		}
	}
	private void compruebaMovimientoSheep(ArrayList<WolfAndSheepAction> moves, int player, int posFil, int posCol){
		int fil = posFil+1;
		if (fil >= dim)
			fil = dim-1;
		int col = posCol-1;
		if (col < 0)
			col = 0;
		
		while (col <= posCol+1){
			if (fil < dim && col < dim) //por si se pasa de los limites
				if (fil != posFil && col != posCol && board[fil][col] == -1){					
					moves.add(new WolfAndSheepAction(player, posFil, posCol, fil, col));
				}			
				col++;
		}
	}
	
	
	//TODO completada
	@Override
	public List<WolfAndSheepAction> validActions(int playerNumber) {
		
		ArrayList<WolfAndSheepAction> moves = new ArrayList<>();
		
        if (finished) {
            return moves;
        }

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board[i][j] == WOLF && playerNumber == WOLF){
                	compruebaMovimientoWolf(moves,playerNumber,i,j);	                   
                }
                if (board[i][j] == SHEEP && playerNumber == SHEEP){
                	compruebaMovimientoSheep(moves,playerNumber,i,j);
                }
            }	
        }
        return moves;
	}
	
	@Override
	public boolean isFinished() {
		return finished;
	}
	
	@Override
	public int getWinner() {
	
		return winner;
	}
	
	public int[][] getBoard() {
		
		int[][] copy = new int[board.length][];
		
		for (int i=0; i<board.length; i++) 
			copy[i] = board[i].clone();
		
    	return copy;
    }
		
	
	//TODO 
	public String toString() {
			 
	  StringBuilder sb = new StringBuilder();
	  
	    for (int i = 0; i < board.length; i++) {
	        sb.append("|");
	        for (int j = 0; j < board.length; j++) {
	        	sb.append(board[i][j] == CASILLA_ROJA  ? "   |" : board[i][j] == EMPTY ? "   |" : board[i][j] == 0 ? " W |" : " S |");           	
	     
	        }
	        sb.append("\n");
	    }
	    	        
	    return sb.toString();
	}
	
	
}