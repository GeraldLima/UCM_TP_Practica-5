package es.ucm.fdi.tp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.control.GameController;
import es.ucm.fdi.tp.control.GameViewCtrl;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObserver;

public class GameWindow< S extends GameState<S,A>, A extends GameAction<S,A> > 
													implements GameViewCtrl<S, A>, 
																GameObserver<S, A>{

	public GameWindow(int playerId, GamePlayer randPlayer, GamePlayer smartPalyer, 
						GameView<S,A> gameView, GameController<S,A> gameCtrl){
		localPlayer = playerId;
		this.randPlayer = randPlayer;
		this.smartPlayer = smartPalyer;
		this.gameView = gameView;
		gameCotrol = gameCtrl;
		playerMode = new HashMap<Integer,GamePlayer>();
		colorJugador = new HashMap<Integer,Color>();
		
		//Iniciar EL JUEGO.....
		
	}
	
	/*** Empieza la fiesta con Atributos, Getters, Setters!!! ****/
	//Tipo de Jugador
	private Map<Integer,GamePlayer> playerMode;
		
	public Map<Integer, GamePlayer> getPlayerMode() {
		return playerMode;
	}
	
	//Tipo de Color
	private Map<Integer,Color> colorJugador;
	
	public Map<Integer, Color> getColorJugador() {
		return colorJugador;
	}
	
	//Tipo de Color dado un jugador
	public Color getColorJugador(int player) {
		return colorJugador.get(player);
	}	
	public void setColorJugador(int player, Color color) {
		colorJugador.put(player,color);
	}
	
	//lista de jugadores
	private int[] players;
	private int turno;
	
	final protected int[] getPlayers() {
		return this.players;
	} 
	final protected int getTurn(){
		return this.turno;
	}
	
	//El controlador
	private GameViewCtrl/*<S extends GameState<S, A>, A extends GameAction<S, A>>*/ control;
	private GameController<S,A> gameCotrol;
	
	// Game View
	GameView<S,A> gameView;
	//Jugador al que pertenece la "Ventana Activa"
	private int localPlayer;
	public int getLocalPlayer(){
		return localPlayer;
	}
	private GamePlayer randPlayer, smartPlayer;
	
	//estados por los que puede pasar una partida
	private boolean nuevoJuego, enPartida, enMovimiento;
	
	/******************************/
	private JPanel panelDerecho, panelIzqSuperior, panelIzqInferior, panelBotones;
	private JButton salir = new JButton("Quit");
	private JButton restart = new JButton("Restart");
	protected JButton random = new JButton("Random");
	protected JButton intelligent = new JButton("Intelligent");

	
	private void InitGUI(){
		JFrame ventanaPrincipal = new JFrame("Wolf And Sheep (View for player"+ getLocalPlayer() +")" );
		ventanaPrincipal.getContentPane().setLayout(new BorderLayout());
		
		panelIzqSuperior = new JPanel();
		panelIzqInferior = new JPanel();
		
		ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventanaPrincipal.setPreferredSize(new Dimension(750, 550));
		ventanaPrincipal.setVisible(true);
		
		ventanaPrincipal.add(panelIzqSuperior, BorderLayout.WEST);
		ventanaPrincipal.add(panelIzqInferior, BorderLayout.WEST);
	}
	
	
	
	
	@Override
	public void notifyEvent(GameEvent<S, A> e) {
		// TODO Auto-generated method stub
		
	}	

	@Override
	public void userActionAvailable(GameAction a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void randomActionButtonPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void smartActionButtonPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restartButtonPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void quitButtonPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerModeSelected() {
		// TODO Auto-generated method stub
		
	}

}
