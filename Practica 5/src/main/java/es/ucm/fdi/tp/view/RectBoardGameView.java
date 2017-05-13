package es.ucm.fdi.tp.view;

import java.awt.Color;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.extra.jboard.JBoard;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObservable;

public abstract class RectBoardGameView< S extends GameState<S,A>, A extends GameAction<S,A> > 	
										extends GameWindow<S, A>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Componente tablero
	 */
	protected JBoard<S,A> boardComp;
	
	/**
	 * Constructora que inicializa el tablero del juego
	 * @param g Observador del juego
	 * @param c Controlador
	 * @param playerId La ficha de la vista
	 * @param randPlayer Jugador random
	 * @param aiPlayer Jugador inteligente
	 */
	public RectBoardGameView (int playerId, GamePlayer random, GamePlayer smart, GameObservable<S, A> game, GameController<S, A> gameCtrl) {
		super(playerId, random, smart, game, gameCtrl);
	}
	
	/**
	 * Inicializa la parte grafica del tablero
	 */
	@Override
	protected void initBoardGUI() {
		 boardComp = new JBoard<S,A>(gameView, getBoard()){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			
			
			/**
			 * Devuelve el color de la ficha
			 */
			@Override
			protected Color getPiceColor(int p) {
				return getColorJugadorByid(p);
			}	
			
			/**
			 * Devuelve si la ficha es un circulo o un rectangulo
			 */
			@Override
			protected boolean isPlayerPiece(int p) {
				boolean isPiece;
				if(p == 0 || p == 1){
					 isPiece = true;
				}
				else
					 isPiece = false;
				return isPiece;
			}
			
			/**
			 * Informa a la vista del juego que se ha hecho click en el tablero
			 */
			@Override
			protected void mouseClicked(int row, int col, int mouseButton, boolean buttonR) {
				handleMouseClick(row, col, mouseButton, buttonR);
			}
			 
			/**
			 * Indica si estamos en juego was o ttt para saber como pintar el tablero
			 */
			@Override
			protected boolean NameGames() {
				return isNameGame();
			}
			
			 /**
			  * Notifica a la vista del evento correspondiente
			  */
			@Override
			public void notifyEvent(GameEvent<S, A> e) {				
				
			}


		 }; 
		super.setBoardArea(boardComp);
	}
	
	/**
	 * Informa a la vista del juego que se ha hecho un click en el tablero
	 * @param row Fila del tablero
	 * @param col Columna del tablero
	 * @param mouseButton Boton del raton
	 * @param buttonR Indica que se ha ha hecho click con el boton derecho del raton
	 */
	protected abstract void handleMouseClick(int row, int col, int mouseButton, boolean buttonR);
	
	/**
	 * Devuelve el tablero del juego
	 * @return El tablero
	 */
	protected abstract int[][] getBoard();
	
	
}
