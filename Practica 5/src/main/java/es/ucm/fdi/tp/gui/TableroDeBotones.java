package es.ucm.fdi.tp.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public abstract class TableroDeBotones extends JPanel{
	
	/**
	 * Clase que representa el Tablero principal de fichas.
	 */
	private int [][] _board;
	private MiBoton listaBotones[][];	
	private MiBoton boton;
	private List<Player/*GamePlayer ?¿?*/> players;
	
	//private MiBoton listaPosibles[][];
	
	public TableroDeBotones(List<Player> jugadores){
		//inicializaTablero();
		//inicializa();
		players = jugadores;
	}
	
	
	protected void inicializaTablero(){
		if (this._board.length == 0)
			this.removeAll();

		listaBotones = new MiBoton[_board.length][_board.length];
		//listaPosibles = new MiBoton[_board.length][_board.length];
		
		this.setLayout(new GridLayout(_board.length,_board.length,3,3));
		this.setPreferredSize(new Dimension(350, 350));
		
			for (int i = 0; i < _board.length; i++) {
				for (int j = 0; j < _board.length; j++) {
					boton = new MiBoton(i,j);
					this.add(boton);
					//boton.setBackground(Color.green);
					listaBotones[i][j] = boton;
					
					final MiBoton botonAux1 = boton;
					
					boton.addMouseListener(new MouseListener() {						
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
						
						@Override
						public void mouseClicked(MouseEvent e) {
							if (e.getComponent().isEnabled()) {
								if (e.getButton() == MouseEvent.BUTTON1){//Izq							
									TableroDeBotones.this.mouseClicked(botonAux1._fila, botonAux1._columna, 1);
								}							
								else if (e.getButton() == MouseEvent.BUTTON3)//Der
									TableroDeBotones.this.mouseClicked(botonAux1._fila, botonAux1._columna, 3);
							}
						}
					});						
				}
			}
	}
	
	
	
	protected abstract void mouseClicked(int row, int col, int mouseButton);
	
	public void drawBoard(int [][] board) {		
		if (_board == null ) {
			_board = board;
			inicializaTablero();
		}
		else{
			_board = board;
		}
		actualizaTablero();
	}
	
	protected void restartBoard(int [][] board){
		_board=null;
		drawBoard(board);
	}
	
	
	private void actualizaTablero(){
	
		for (int i = 0; i < _board.length; i++) {
			for (int j = 0; j < _board.length; j++) {
				//boton = new MiBoton(i,j);
				if(_board[i][j] == -1){
					listaBotones[i][j].setBackground(Color.green);
				}
				else{
					if (_board[i][j].equals(players.get(0)))
						listaBotones[i][j].setBackground(Color.blue);
					else if(_board[i][j].equals(players.get(1)))
						listaBotones[i][j].setBackground(Color.ORANGE);
					else if(_board[i][j].equals(new Piece("*")))
						listaBotones[i][j].setBackground(Color.BLACK);	
					else if(_board[i][j].equals(new Piece("#")))
						listaBotones[i][j].setBackground(Color.LIGHT_GRAY);
					else if(_board[i][j].equals(players.get(2)))
						listaBotones[i][j].setBackground(Color.YELLOW);
					else if(_board[i][j].equals(players.get(3)))
						listaBotones[i][j].setBackground(Color.CYAN);
					
				}
			}
		}	
		
	}

	
	protected void desactivaBotones(int [][] board){
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				listaBotones[i][j].setEnabled(false);
			}
		}
	}
	

	protected void activaBotones(int [][] board){
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				listaBotones[i][j].setEnabled(true);
			}
		}
	}

}

