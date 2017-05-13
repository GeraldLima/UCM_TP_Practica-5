package es.ucm.fdi.tp.extra.jboard;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameObservable;
import es.ucm.fdi.tp.mvc.GameObserver;



@SuppressWarnings("serial")

/**
 * 
 * Clase que se encarga de los componentes graficos del tablero
 *
 */
public abstract class JBoard <S extends GameState<S,A>, A extends GameAction<S,A>> 
						extends JComponent implements GameObserver<S, A> {
	
	/**
	 * Altura de la casilla
	 */
	private int _CELL_HEIGHT = 50;
	
	/**
	 * Anchura de la casilla
	 */
	private int _CELL_WIDTH = 50;
	
	/**
	 * El tablero
	 */
	private int[][] board;

	/**
	 * 
	 * Enumerado del tipo de figuras: circulo o rectangulo
	 *
	 */
	public enum Shapes { CIRCLE, RECTANGLE }
	
	/**
	 * Constructora que iniciliaza el componente tablero
	 * @param game Observador del juego
	 * @param board El tablero
	 */
	public JBoard(GameObservable<S, A> game, int[][] board) {
		this.board = board;
		initGUI();
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				game.addObserver(JBoard.this);
			}
		});
	}
	
	/**
	 * Inicializa la deteccion del click del raton en el tablero  
	 */
	private void initGUI() {

		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			/**
			 * Detecta el click del raton en el tablero
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				boolean buttonR;
				int col = (e.getX() / _CELL_WIDTH);
				int row = (e.getY() / _CELL_HEIGHT);
				
				if((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK){
					buttonR = false;
					JBoard.this.mouseClicked(row, col, e.getButton(), buttonR);
				}
				else{
					buttonR = true;
					JBoard.this.mouseClicked(row, col, e.getButton(), buttonR);
				}
			}
		});
		this.setPreferredSize(new Dimension(200, 200));
		repaint();
	}
	
	
	/**
	 * Devuelve si la figura correspondiente: circulo o rectangulo
	 * @param p Ficha
	 * @return Figura
	 */
	protected Shapes getPieceShape(int p){
		if(this.isPlayerPiece(p)){
			return Shapes.CIRCLE;
		}
		else
			return Shapes.RECTANGLE;
	}
	
	/**
	 * Dibuja el componente tablero
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		fillBoard(g);
	}
	
	/**
	 * Dibuja el tablero
	 * @param g Graficos
	 */
	public void fillBoard(Graphics g){
		if(board == null)
			return;
		int cols = board.length;
		int rows = board.length;
		_CELL_WIDTH = this.getWidth() / cols;
		_CELL_HEIGHT = this.getHeight() / rows;
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++)
				drawCell(i, j, g);
	}
	
	/**
	 * Pinta el tablero y las fichas
	 * @param row Fila del tablero
	 * @param col Columna del tablero
	 * @param g Grafico
	 */
	private void drawCell(int row, int col, Graphics g) {
		int x = col * _CELL_WIDTH;
		int y = row * _CELL_HEIGHT;
		
		if(NameGames()){
			if((row%2 == 0) && (col%2==0))
				g.setColor(Color.LIGHT_GRAY);
		
			if((row%2 == 1) && (col%2==1))
				g.setColor(Color.LIGHT_GRAY);
		
			if((row%2 == 0) && (col%2==1))
				g.setColor(Color.BLACK);
		
			if((row%2 == 1) && (col%2==0))
				g.setColor(Color.BLACK);
		}
		else
			g.setColor(Color.LIGHT_GRAY);
		
		g.fillRect(x + 2, y + 2, _CELL_WIDTH - 4, _CELL_HEIGHT - 4);
		
		int p = board[row][col];
		if(p != -1 && p != -2){
			Color c = getPiceColor(p);
			Shapes s = getPieceShape(p);
			
			switch(s){
			case CIRCLE:
				g.setColor(c);
				g.fillOval(x + 4, y + 4, _CELL_WIDTH - 8, _CELL_HEIGHT - 8);
				g.setColor(Color.BLACK);
				g.drawOval(x + 4, y + 4, _CELL_WIDTH - 8, _CELL_HEIGHT - 8);
				break;
			case RECTANGLE:
				g.setColor(Color.DARK_GRAY);
				g.fillRect(x + 4, y + 4, _CELL_WIDTH - 8, _CELL_HEIGHT - 8);
				g.setColor(Color.BLACK);
				g.drawRect(x + 4, y + 4, _CELL_WIDTH - 8, _CELL_HEIGHT - 8);
				break;
			}
		}
	}
	
	/**
	 * Modifica el tamaño del tablero
	 * @param rows Numero de filas
	 * @param cols Numero de columnas
	 */
	public void setBoardSize(int rows, int cols) {
		repaint();
	}
	
	/**
	 * Devuelve el color de la ficha
	 * @param p Ficha
	 * @return El color
	 */
	protected abstract Color getPiceColor(int p);
	
	/**
	 * Devuelve si la ficha es un circulo o un rectangulo
	 * @param p Ficha
	 * @return Boleano que indica si es un circulo o un rectangulo
	 */
	protected abstract boolean isPlayerPiece(int p);
	
	/**
	 * Informa a la vista del juego que se ha hecho click en el tablero
	 * @param row Fila del tablero
	 * @param col Columna del tablero
	 * @param mouseButton Boton del raton
	 * @param buttonR indica si se ha hecho click con el boton derecho del raton
	 */
	protected abstract void mouseClicked(int row, int col, int mouseButton, boolean buttonR);
	
	/**
	 * Indica si estamos en juego was o ttt para saber como pintar el tablero
	 * @return devuelve true si es was o false si es ttt
	 */
	protected abstract boolean NameGames();
	
	/**
	 * Inicializa el comenzar
	 */
	@Override
	public void onGameStart(int[][] board, String gameDesc, List<Integer> pieces, int turn) {
		 SwingUtilities.invokeLater(new Runnable(){
	        	
	        /**
	         * Ejecucion del comenzar 
	         */
	          @Override
	          public void run() {
	        	  handleOnGameStart(board, gameDesc, pieces, turn);
	          }
	    });
	}
	
	/**
     * Inicializa los datos del comenzar de la partida 
     * @param board El tablero
     * @param gameDesc Nombre del juego
     * @param pieces Lista de fichas
     * @param turn ficha del jugador que tiene el turno
     */
	protected void handleOnGameStart(int[][] board, String gameDesc,List<Integer> pieces,  int turn ) {
		this.board = board;
		repaint();
	}
	
	/**
	 * Terminar de la partida
	 */
	@Override
	public void onGameOver(int[][] board, S state, int winner) {
		 SwingUtilities.invokeLater(new Runnable(){
	        	
	        /**
	         * Ejecucion del terminar de la partida
	         */
	         @Override
	         public void run() {
	        	 handleOnGameOver(board, state, winner);
	         }
	    });
		
	}
	
	/**
     * Actualiza los datos del terminar de la partida
     * @param board El tablero
     * @param state El estado de la partida
     * @param winner La ficha del jugador ganador
     */
    protected void handleOnGameOver(int[][] board, S state, int winner) {
    	this.board = board;
		repaint();		
    }
	
	/**
	 * Informacion de empezar el movimiento
	 */
	@Override
	public void onMoveStart(int[][] board, int turn) {
		 SwingUtilities.invokeLater(new Runnable(){
	        	
			 /**
	          * Ejecucion del comenzar movimiento
	          */
	         @Override
	          public void run() {
	        	 handleOnMoveStart(board, turn);
	          }
	      });
	}
	
	/**
     * Actualizar datos del comenzar movimento
     * @param board El tablero
     * @param turn La ficha del jugador del turno
     */
    protected void handleOnMoveStart(int[][] board, int turn) {
    	
    }
	
	/**
	 * Informacion del terminar del movimiento
	 */
	@Override
	public void onMoveEnd(int[][] board, int turn, boolean success) {
		SwingUtilities.invokeLater(new Runnable(){
        	
        	/**
        	 * Ejecucion del terminar movimiento
        	 */
            @Override
            public void run() {
                handleOnMoveEnd();
            }
        });
	}
	
	/**
     * Actualizar datos del terminar movimiento
     */
    protected void handleOnMoveEnd() {
    	repaint();
    }
	
	/**
	 * Informacion al cambiar el turno
	 */
	@Override
	public void onChangeTurn(int[][] board, int turn) {
		 SwingUtilities.invokeLater(new Runnable(){
	        	
	        /**
	         * Ejecucion al cambiar el turno
	         */
	         @Override
	         public void run() {
	             handleOnChangeTurn(board, turn);
	         }
	    });
		
	}
	
	 /**
     * Actualizar datos del cambio de turno
     * @param board El tablero
     * @param turn La ficha del jugador del turno
     */
    protected void handleOnChangeTurn(int[][] board, final int turn) {
    	this.board = board;
		repaint();
    }
	
	/**
	 * Informacion de los errores de las reglas del juego
	 */
	public void onError(String msg) {
		 SwingUtilities.invokeLater(new Runnable(){
	        	
	        /**
	         * Ejecucion de los errores del juego
	         */
	         @Override
	         public void run() {
	        	 handleOnError(msg);
	         }
	     });
	}
	
	/**
     * Mostrar la ventana del error del jugador
     * @param msg Mensaje de error
     */
    protected void handleOnError(String msg) {
    	
    }
}

