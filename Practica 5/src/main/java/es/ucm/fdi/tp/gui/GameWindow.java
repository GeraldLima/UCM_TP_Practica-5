package es.ucm.fdi.tp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.cert.CRLReason;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellRenderer;

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
		gameControl = gameCtrl;
		playerMode = new HashMap<Integer,GamePlayer>();
		colorJugador = new HashMap<Integer,Color>();
		
		//Iniciar EL JUEGO.....
		
	}
	public GameWindow(){
		//InitGUI();
	}
	
	final private int ID_PLAYER = 0;
	final private int COLOR_PLAYER = 1;
	
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
	public Color getColorJugadorByid(int player) {
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
	
	//Controlador
	private GameViewCtrl/*<S extends GameState<S, A>, A extends GameAction<S, A>>*/ control;
	private GameController<S,A> gameControl;
	
	//Game View
	GameView<S,A> gameView;
	//Jugador al que pertenece la "Ventana Activa"
	private int localPlayer;
	public int getLocalPlayer(){
		return localPlayer;
	}
	//Tipo de jugador
	private GamePlayer manualPlayer, randPlayer, smartPlayer;
	
	//estados por los que puede pasar una partida
	private boolean nuevoJuego, enPartida, enMovimiento;
	
	/************ swing ******************/
	private JPanel panelDerecho, panelDerInferior;
	private PanelStatusMessages panelStatusMessages;

	
	private void InitGUI(){
		JFrame ventanaPrincipal = new JFrame("Wolf And Sheep (View for player"+ getLocalPlayer() +")" );
		ventanaPrincipal.getContentPane().setLayout(new BorderLayout());
		
		this.panelSuperior();
		this.panelPlayerInformation();
		panelDerecho = new JPanel();
		panelDerInferior = new JPanel();
		//panelDerecho.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		
		/** Panel Status Messages **/
		panelStatusMessages = new PanelStatusMessages(gameControl);
		panelDerecho.add(panelStatusMessages/*, BorderLayout.NORTH*/);
		
		//panelDerecho.add(panelDerInferior);
		panelDerecho.add(playerInformationPanel/*, BorderLayout.SOUTH*/);
		
		ventanaPrincipal.getContentPane().setLayout(new BorderLayout());
		
		ventanaPrincipal.add(panelSuperior/*, BorderLayout.WEST*/);
		ventanaPrincipal.getContentPane().add(panelSuperior);
		
		ventanaPrincipal.add(panelDerecho, BorderLayout.EAST);

		ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		ventanaPrincipal.setVisible(true);
		ventanaPrincipal.setSize(850,550);
		//ventanaPrincipal.setPreferredSize(new Dimension(750, 550));

		
	}
	
	
	/** Panel de Player Information ***/
	
	protected JPanel playerInformationPanel;
	protected TableModelSwing playerInformationModel;
	private JTable playerInformationTable;	
	
	private void panelPlayerInformation() {
		playerInformationPanel = new JPanel();
		//playerInformationPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		playerInformationPanel.setLayout(new GridLayout());
		playerInformationPanel.setPreferredSize(new Dimension(250, 280));
		//playerInformationPanel.setBorder(BorderFactory.createTitledBorder("Player Information"));
		playerInformationPanel.setBorder(new TitledBorder("Player Information"));
		
		playerInformationModel = new TableModelSwing();
		
		playerInformationTable = new JTable(playerInformationModel) {		
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
				Component component = super.prepareRenderer(renderer, row, col);
				int idPlayer = (int) getValueAt(row, ID_PLAYER);
				//component.setBackground(colorJugador.get(players.length));
				component.setBackground(getColorJugadorByid(idPlayer));
				
				return component;
			}
		};

		//_playerInformationTable.setBackground(getForeground());
		playerInformationTable.setEnabled(false);
		//_playerInformationTable.setVisible(true);
		playerInformationTable.setPreferredScrollableViewportSize(new Dimension(10, 50));
		playerInformationTable.setFillsViewportHeight(false);
		JScrollPane scrollTable = new JScrollPane(playerInformationTable);
		scrollTable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		playerInformationPanel.add(scrollTable,playerInformationTable);
		//panelDerInferior.add(_playerInformationPanel.add(scroll));
		//panelDerInferior.add(playerInformationPanel);
	}
	
	
	/** Panel de botones y player Modes ***/
	
	private JButton salir = new JButton("Quit");
	private JButton restart = new JButton("Restart");
	protected JButton random = new JButton("Random");
	protected JButton intelligent = new JButton("Intelligent");
	private JPanel panelSuperior;
	private JComboBox<GamePlayer> comboPlayer;
	
	private void panelSuperior(){
		panelSuperior = new JPanel();
		panelSuperior.setBorder(BorderFactory.createTitledBorder("Panel Superior"));
		//panelSuperior.setLayout(new FlowLayout(FlowLayout.CENTER));
		//panelSuperior.setSize(new Dimension(350, 420));
				
		comboPlayer = new JComboBox<GamePlayer>(new DefaultComboBoxModel<GamePlayer>(){
			@Override
			public void setSelectedItem(Object o){
				super.setSelectedItem(o);
				if (playerMode.get(o) != manualPlayer)
					comboPlayer.setSelectedItem(randPlayer);
				else
					comboPlayer.setSelectedItem(manualPlayer);
			}			
		});
		
		comboPlayer.addItem(manualPlayer);
		if (randPlayer != null)
			comboPlayer.addItem(randPlayer);
		
		if (smartPlayer != null)
			comboPlayer.addItem(smartPlayer);
		
		panelSuperior.add(salir);
		panelSuperior.add(restart);
		panelSuperior.add(intelligent);
		panelSuperior.add(random);
		
		panelSuperior.add(comboPlayer);
		
		this.quitButtonPressed(salir);
		this.restartButtonPressed(restart);
		this.smartActionButtonPressed();
		this.randomActionButtonPressed();		
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
	public void restartButtonPressed(JButton reset) {
		reset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String message = "¿Estas seguro que quieres reiniciar?";
				int get = JOptionPane.showOptionDialog(new Frame(), message, "Restart", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,null,null);
				if (get == 0){
					panelStatusMessages.addMessage("Partida Reiniciada");
					//gameControl.restart();
				}				
			}
		});	
	}

	@Override
	public void quitButtonPressed(JButton salir) {
		salir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String message = "¿Estas seguro que quieres salir?";
				int get = JOptionPane.showOptionDialog(new Frame(), message, "Salir", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,null,null);
				if (get == 0)
					System.exit(0);
			}
		});		
	}

	@Override
	public void playerModeSelected() {
		// TODO Auto-generated method stub
		
	}
	
	
	/*     PRUEBA!!    ***********/
	public static void main(String []args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				GameWindow view = new GameWindow();
				view.InitGUI();
			}
		});
	}

}
