package es.ucm.fdi.tp.mvc;

import java.util.List;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;

/**
 * Can be notified of GameEvents for a particular game
 */
public interface GameObserver<S extends GameState<S,A>, A extends GameAction<S,A>> {
    /**
     * Notifies the observer of an event. Typically called by a GameObservable
     * that this observer has registered with
     * @param e the event
     */
    void notifyEvent(GameEvent<S,A> e);
    
    
    
    /**
     * Inicializa los datos del comenzar de la partida 
     * @param board El tablero
     * @param gameDesc Nombre del juego
     * @param pieces Lista de fichas
     * @param turn ficha del jugador que tiene el turno
     */
	void onGameStart(int[][] board, String gameDesc, List<Integer> pieces, int turn);
	
	/**
     * Actualiza los datos del terminar de la partida
     * @param board El tablero
     * @param state El estado de la partida
     * @param winner La ficha del jugador ganador
     */
	void onGameOver(int[][] board, S state, int winner);
	
	/**
     * Actualizar datos del comenzar movimento
     * @param board El tablero
     * @param turn La ficha del jugador del turno
     */
	void onMoveStart(int[][] board, int turn);
	
	/**
	 * Actualizar datos del terminar movimiento
	 * @param board El tablero
	 * @param turn  La ficha del jugador del turno
	 * @param success El suceso ocurrido
	 */
	void onMoveEnd(int[][] board, int turn, boolean success);
	
	 /**
     * Actualizar datos del cambio de turno
     * @param board El tablero
     * @param turn La ficha del jugador del turno
     */
	void onChangeTurn(int[][] board, int turn);
	
	/**
     * Mostrar la ventana del error del jugador
     * @param msg Mensaje de error
     */
	void onError(String msg);
	
	
	
}