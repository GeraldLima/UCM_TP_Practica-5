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
	private int [][] board;
	private MiBoton listaBotones[][];	
	private MiBoton boton;
	//private List<Player/*GamePlayer ?¿?*/> players;
	private int player;
	final static int EMPTY = -1; //casilla negra donde se puede mover
	final static int CASILLA_ROJA = -2;	
	final static int WOLF = 0;
	final static int SHEEP = 1;	
	//private MiBoton listaPosibles[][];
	
	
	public TableroDeBotones(int jugador){
		
		player = jugador;
	}
	
	
	protected void inicializaTablero(){
		if (this.board.length == 0)
			this.removeAll();//para quitar mierda restante
 
		listaBotones = new MiBoton[board.length][board.length];//filas_x_columnas
		//listaPosibles = new MiBoton[board.length][board.length];
		
		this.setLayout(new GridLayout(board.length,board.length,3,3));
		this.setPreferredSize(new Dimension(350, 350));
		
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
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

	/**********  metodos abstractos *********************************************/
	protected abstract void mouseClicked(int row, int col, int mouseButton);	
	
	/*****************************************************************************/
	
	
	public void drawBoard(int [][] board) {		
		if (this.board == null ) {
			this.board = board;
			inicializaTablero();
		}
		else{
			this.board = board;
		}
		actualizaTablero();
	}
	
	//TODO dejar el estado del tablero como al principio
	protected void restartBoard(int [][] board){
		this.board = null;
		drawBoard(board);
	}	
	
	private void actualizaTablero(){
	
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				//boton = new MiBoton(i,j);
				if(board[i][j] == EMPTY){
					listaBotones[i][j].setBackground(Color.green);
				}
				else{
					if (board[i][j] == WOLF)
						listaBotones[i][j].setBackground(Color.blue);
					else if(board[i][j] == SHEEP)
						listaBotones[i][j].setBackground(Color.ORANGE);
					else if(board[i][j] == CASILLA_ROJA)
						listaBotones[i][j].setBackground(Color.BLACK);	
					
					/*
					else if(board[i][j] == AnotherPlayer)
						listaBotones[i][j].setBackground(Color.YELLOW);
					else if(board[i][j] == AnotherPlayer)
						listaBotones[i][j].setBackground(Color.CYAN);
					*/
				}
			}
		}
		
	}
	
	//TODO Cuando pase el turno de un jugador, no puede hacer nada hasta que le vuelva el turno
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

