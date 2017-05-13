package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.launcher.Main.PlayerMode;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameTable;

public interface GameController< S extends GameState<S,A>, A extends GameAction<S,A> > {
	
	/**
	 * Ejecuta un movimiento manual
	 * @param a La accion
	 */
	public void makeManualMove(A a);
	
	/**
	 * Ejecuta un movimiento random
	 */
	public void makeRandomMove();
	
	/**
	 * Ejecuta un movimiento inteligente
	 */
	public void makeSmartMove();
	
	/**
	 * Reinicia el juego
	 */
	public void restartGame();
	
	/**
	 * Para el juego
	 */
	public void stopGame();
	
	/**
	 * Cambia el modo del jugador
	 * @param p Modo del jugador
	 */
	public void changePlayerMode(PlayerMode p);
	
	/**
	 * Ejecuta un evento
	 * @param e El evento
	 */
	public void handleEvent(GameEvent<S, A> e); 
	
	/**
	 * Devuelve el modo del jugador
	 * @return Modo del jugador
	 */
	public PlayerMode getPlayerMode();
	
	/**
	 * Devuelve el identificador del jugador
	 * @return El identificador del jugador
	 */
	public int getPlayerId();
	
	/**
	 * Devuelve el modelo del juego
	 * @return El modelo del juego
	 */
	public GameTable<S, A> getGame();
}
