package es.ucm.fdi.tp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellRenderer;

import es.ucm.fdi.tp.base.Utils;
import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameError;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.extra.jcolor.ColorChooser;
import es.ucm.fdi.tp.launcher.Main.PlayerMode;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObservable;
import es.ucm.fdi.tp.mvc.GameObserver;

@SuppressWarnings("serial")
public abstract class GameWindow< S extends GameState<S,A>, A extends GameAction<S,A> > 
													extends JFrame implements GameObserver<S, A>{


	public GameWindow(int playerId, GamePlayer randPlayer, GamePlayer smartPalyer, 
						GameObservable<S,A> gameView, GameController<S,A> gameCtrl){
		localPlayer = playerId;
		this.randPlayer = randPlayer;
		this.smartPlayer = smartPalyer;
		this.gameView = gameView;
		gameControl = gameCtrl;
		nameGame = getNameGame();
		playerMode = new HashMap<Integer,PlayerMode>();
		colorJugador = new HashMap<Integer,Color>();
		vectorMode = new Vector<PlayerMode>();
		vectorMode.add(PlayerMode.MANUAL);
		if (randPlayer != null)
			vectorMode.addElement(PlayerMode.RANDOM);
		if (smartPalyer != null)
			vectorMode.add(PlayerMode.INTELLIGENT);
		players.add(0);
		players.add(1);
		
		
		
		/***INICIAR EL JUEGO ***/
		InitGUI();
		gameView.addObserver(this);
	}
	
		//final private int ID_PLAYER = 0;
		//final private int COLOR_PLAYER = 1;
	
	//estados de un jugador a modo automatico. Y estado para diferenciar el primer click del segundo
	protected boolean automatic, clicked;
	
	//estados por los que puede pasar una partida
	//private boolean nuevoJuego, enPartida, enMovimiento;
	
	//Modo del Jugador
	private Map<Integer,PlayerMode> playerMode;
		
	public Map<Integer, PlayerMode> getPlayerMode() {
		return playerMode;
	}
	public PlayerMode setPlayerMode(int player, PlayerMode mode){
		return playerMode.put(player, mode);
	}
	private Vector<PlayerMode> vectorMode;
	
	//Tipo de Color
	private Map<Integer,Color> colorJugador;
	
	public Map<Integer, Color> getColorJugador() {
		return colorJugador;
	}
	private void setInitialPlayersColors(){
		colorJugador.put(players.get(0), Color.ORANGE);
		colorJugador.put(players.get(1), Color.LIGHT_GRAY);
	}
	
	//Tipo de Color dado un jugador
	public Color getColorJugadorByid(int player) {
		return colorJugador.get(player);
	}	
	public void setColorJugador(int player, Color color) {
		colorJugador.put(player,color);
	}
	
	//jugador y lista de jugadores
	private List<Integer> players = new ArrayList<Integer>();
	private int turno;
	
	final protected List<Integer> getPlayers() {
		return this.players;
	} 
	final protected int getTurn(){
		return this.turno;
	}
	//inicializar los modos iniciales de los jugadores
	private void setInitialPlayerModes(){
		for (int i = 0; i < players.size(); i++) {
			playerMode.put(players.get(i), PlayerMode.MANUAL);
		}
	}
	
	//Tablero
	protected int[][] board;
	protected String nameGame;
	private S state;
	protected boolean editable;
	
	//Controlador
	protected GameController<S,A> gameControl;
	
	//Game
	protected GameObservable<S,A> gameView;
	
	//Jugador al que pertenece la "Ventana Activa"
	private int localPlayer;
	public int getLocalPlayer(){
		return localPlayer;
	}
	
	protected int getPlayerId(){
		return localPlayer;
	}
	//Tipo de jugador
	private GamePlayer randPlayer, smartPlayer;
	
	
	/***Metodos abstractos***/	
	protected abstract String getNameGame();
	protected abstract boolean isNameGame();
	protected abstract void initBoardGUI();
	protected abstract void activateBoard();
	protected abstract void deActivateBoard();
	
	
	///////////////////////////////////////
	/************ SWING ******************/
	
	private JPanel panelTablero, panelDerecho;
	protected PanelStatusMessages panelStatusMessages;
	//private JFrame ventanaPrincipal;
	
	protected void setBoardArea(JComponent comp){
		panelTablero.add(comp, BorderLayout.CENTER);
	}
	
	private void InitGUI(){
		
		this.getContentPane().setLayout(new BorderLayout());
		
			this.panelSuperior();
			this.panelPlayerInformation();
		panelDerecho = new JPanel();
		panelTablero = new JPanel(new BorderLayout());
		panelTablero.setLayout(new GridLayout());
		//panelTablero.setBorder(new TitledBorder("TABLERO"));
		panelTablero.setPreferredSize(new Dimension(550, 550));
		
		//panelDerecho.setLayout(new FlowLayout(FlowLayout.CENTER));
		initBoardGUI();
		panelSuperior.add(panelTablero, BorderLayout.CENTER);
		
		
		/** Panel Status Messages **/
		panelStatusMessages = new PanelStatusMessages(gameControl);
		panelDerecho.add(panelStatusMessages/*, BorderLayout.NORTH*/);
		

		panelDerecho.add(playerInformationPanel/*, BorderLayout.SOUTH*/);
		
		this.getContentPane().setLayout(new BorderLayout());
		
		//this.add(panelSuperior/*, BorderLayout.WEST*/);
		this.getContentPane().add(panelSuperior);
		//this.getContentPane().add(panelTablero);
		this.add(panelDerecho, BorderLayout.EAST);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		this.setVisible(true);
		this.setSize(1150,660);
		//this.setPreferredSize(new Dimension(1150,650));
		//this.pack();
		
	}

	
	/** Panel de Player Information ***/
	
	protected JPanel playerInformationPanel;
	//TODO protected TableModelSwing playerInformationModel;
		protected PlayerInfoModel playerInformationModel;
	private JTable playerInformationTable;
	private ColorChooser colorChooser;
	private Color initialColor;
	
	private void panelPlayerInformation() {
		playerInformationPanel = new JPanel();
		//playerInformationPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		playerInformationPanel.setLayout(new GridLayout());
		playerInformationPanel.setPreferredSize(new Dimension(250, 280));
		//playerInformationPanel.setBorder(BorderFactory.createTitledBorder("Player Information"));
		playerInformationPanel.setBorder(new TitledBorder("Player Information"));
		
		//TODO OTRO MODELOS DE TABLA
		//playerInformationModel = new TableModelSwing();
		playerInformationModel = new PlayerInfoModel(getPlayers());
		
		playerInformationTable = new JTable(playerInformationModel) {	
			//cabecera de la tabla
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
				/*Component component = super.prepareRenderer(renderer, row, col);
				int idPlayer = (int) getValueAt(row, ID_PLAYER);
				//component.setBackground(colorJugador.get(players.get(row)));
				component.setBackground(getColorJugadorByid(idPlayer));
				
				return component;
				*/
				JLabel celda = (JLabel) super.prepareRenderer(renderer, row, col);
				
				if (row == 0 && col == 1)					
					celda.setBackground(colorJugador.get(players.get(row)));
				else if (row == 1 && col == 1) 
					celda.setBackground(colorJugador.get(players.get(row)));
				else
					celda.setBackground(Color.WHITE);
				
				return celda;
			}
			
			// tabla ineditable
			@Override
			public boolean isCellEditable(int row, int col){
				return false;
			}
		};
		
		playerInformationTable.addMouseListener(new MouseListener() {
			
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
				int fila = playerInformationTable.rowAtPoint(e.getPoint());
				int columna = playerInformationTable.columnAtPoint(e.getPoint());
				
				if (columna == 1){
					colorChooser = new ColorChooser(new JFrame(), "Seleccionar Color", initialColor);
					if (colorChooser.getColor() != null){
						if (fila == 0)
							setColorJugador(players.get(0), colorChooser.getColor());
						else if (fila == 1)
							setColorJugador(players.get(1), colorChooser.getColor());
					}					
				}
				repaint();
				
			}
		});

		//_playerInformationTable.setBackground(getForeground());
		playerInformationTable.setEnabled(false);
		playerInformationTable.setVisible(true);
		playerInformationTable.setPreferredScrollableViewportSize(new Dimension(10, 50));
		playerInformationTable.setFillsViewportHeight(true);
		JScrollPane scrollTable = new JScrollPane(playerInformationTable);
		//playerInformationTable.setFillsViewportHeight(true);
		scrollTable.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		playerInformationPanel.add(scrollTable,playerInformationTable);

	}
	
	
	/** Panel de botones y player Modes ***/
	
	private JButton botonSalir = new JButton("Quit");
	private JButton botonRestart = new JButton("Restart");
	protected JButton botonRandom = new JButton("Random");
	protected JButton botonIntelligent = new JButton("Intelligent");
	private JPanel panelSuperior;
	private JComboBox<String> comboPlayer;
	
	private void panelSuperior(){
		panelSuperior = new JPanel();
		//panelSuperior.setBorder(BorderFactory.createTitledBorder("Panel Superior"));
		//panelSuperior.setLayout(new GridLayout());
		//panelSuperior.setSize(new Dimension(350, 420));
		
		//TODO FIXME!!!
		/*comboPlayer = new JComboBox<PlayerMode>(new DefaultComboBoxModel<PlayerMode>(){
			@Override
			public void setSelectedItem(Object o){
				super.setSelectedItem(o);
				if (playerMode.get(o) != PlayerMode.MANUAL)
					comboPlayer.setSelectedItem(randPlayer);
				else if (playerMode.get(o) != PlayerMode.RANDOM)
					comboPlayer.setSelectedItem(smartPlayer);
			}			
		});*/
		
		comboPlayer = new JComboBox<String>();
		comboPlayer.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				comboPlayer.getSelectedItem();
			}
		});
		comboPlayer.addActionListener(new ActionListener() {
			//seleccionamos un modo del ComboBox
			@Override
			public void actionPerformed(ActionEvent e) {
				setPlayerMode(localPlayer, vectorMode.get(comboPlayer.getSelectedIndex()));
				
				if(vectorMode.get(comboPlayer.getSelectedIndex()).equals(PlayerMode.RANDOM)
				|| vectorMode.get(comboPlayer.getSelectedIndex()).equals(PlayerMode.INTELLIGENT)){
					decideMakeAutomaticMove(randPlayer, smartPlayer);
					setAutomaticMode();
					
					if (clicked && automatic && nameGame.equals(getNameGame())){
						panelStatusMessages.addMessage("You've canceled the move");
						clicked = false;
					}
				}
				repaint();	
			}
		});
		
		comboPlayer.addItem(PlayerMode.MANUAL.getPlayerModeDescription());
		//if (randPlayer != null)
			comboPlayer.addItem(PlayerMode.RANDOM.getPlayerModeDescription());
		
		//if (smartPlayer != null)
			comboPlayer.addItem(PlayerMode.INTELLIGENT.getPlayerModeDescription());
		
		panelSuperior.add(botonSalir);
		panelSuperior.add(botonRestart);
		panelSuperior.add(botonIntelligent);
		panelSuperior.add(botonRandom);
		
		panelSuperior.add(comboPlayer);
		
		this.quitButtonPressed(botonSalir);
		botonSalir.setIcon(new ImageIcon(Utils.loadImage("exit.png")));
		this.restartButtonPressed(botonRestart);
		botonRestart.setIcon(new ImageIcon(Utils.loadImage("restart.png")));
		this.smartActionButtonPressed(botonIntelligent);
		botonIntelligent.setIcon(new ImageIcon(Utils.loadImage("nerd.png")));
		this.randomActionButtonPressed(botonRandom);
		botonRandom.setIcon(new ImageIcon(Utils.loadImage("dice.png")));
	}
	
	
	/***** ....Demas metodos....  ****/
	
	public void randomActionButtonPressed(JButton ramdomize) {
		ramdomize.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gameControl.makeRandomMove();
				repaint();
			}
		});
		
	}

	public void smartActionButtonPressed(JButton smarty) {
		smarty.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gameControl.makeSmartMove();
				repaint();
			}
		});
	}

	public void restartButtonPressed(JButton reset) {
		reset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String message = "¿Estas seguro que quieres reiniciar?";
				int get = JOptionPane.showOptionDialog(new Frame(), message, "Restart", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,null,null);
				if (get == 0){
					panelStatusMessages.addMessage("Partida Reiniciada");
					gameControl.restartGame();
					repaint();
				}				
			}
		});	
	}

	public void quitButtonPressed(JButton salir) {
		salir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String message = "¿Estas seguro que quieres salir?";
				int get = JOptionPane.showOptionDialog(new Frame(), message, "Salir", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,null,null);
				if (get == 0){
					try {
						gameControl.stopGame();
					}catch (GameError error){
						
					}
					setFocusable(false);
					dispose();
					System.exit(0);
				}
	
			}
		});		
	}	
	
	protected void decideMakeManualMove(A a){
		gameControl.makeManualMove(a);
	}
	
	protected void decideMakeAutomaticMove(GamePlayer rand, GamePlayer smart){
		if (!gameControl.getGame().getState().isFinished()){
			if (playerMode.get(turno).equals(PlayerMode.RANDOM))		
				gameControl.makeRandomMove();
			else if (playerMode.get(turno).equals(PlayerMode.INTELLIGENT))		
				gameControl.makeSmartMove();
		}
		repaint();
	}
	
	protected boolean cancelMove(boolean clicked){
		if (automatic){
			automatic = false;
			return false;
		}
		else return clicked;
	}
	
	private void setAutomaticMode(){
		automatic = true;
	}

	
	/**** METODOS HEREDADOS Y/O IMPLEMENTADOS DE CLASE PADRE ****/
	
	@Override
	public void notifyEvent(GameEvent<S, A> e) {
	}
	
	@Override
	public void onGameStart(int[][] board, String gameDesc, List<Integer> pieces, int turn) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				handleOnGameStart(board, gameDesc, pieces, turn);
			}
		});
	}
	
	@Override
	public void onGameOver(int[][] board, S state, int winner) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				handleOnGameOver(board, state, winner);				
			}
		});
	}
	
	@Override
	public void onMoveStart(int[][] board, int turn) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				handleOnMoveStart(board, turn);
			}
		});
	}
	
	@Override
	public void onMoveEnd(int[][] board, int turn, boolean success) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				handleOnMoveEnd();
			}
		});
	}
	
	@Override
	public void onChangeTurn(int[][] board, int turn) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				handleOnChangeTurn(board, turn);
			}
		});
		repaint();
	}
	
	@Override
	public void onError(String msg) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				handleOnError(msg);
			}
		});
	}
	

	/*** Metodos handle ***/
	
	protected void handleOnGameStart(int[][] board, String gameDesc, List<Integer> pieces, int turn) {
		this.turno = turn;
		this.board = board;
		this.players = pieces;
		this.nameGame = gameDesc;
		
		this.setTitle(this.nameGame + " (view for player " + localPlayer + ")" );	        
        panelStatusMessages.getTexto().setText("");
        comboPlayer.setEnabled(true);
        comboPlayer.setSelectedItem(PlayerMode.MANUAL.getPlayerModeDescription());
        
        if(localPlayer == this.turno){ 
        	activateBoard();
        	botonRandom.setEnabled(true);
        	botonIntelligent.setEnabled(true);        	
        	panelStatusMessages.addMessage("Turn for You " + "(" +  turn + ")");
            	
            if(this.nameGame.equals(this.getNameGame())){ 
            	panelStatusMessages.addMessage("Click on an origin piece");
            }
            	
            else{
            	panelStatusMessages.addMessage("Click on an emty cell");
            }            	
        }
        else{ //la ficha que no es su turno
        	deActivateBoard();
        	botonRandom.setEnabled(false);
        	botonIntelligent.setEnabled(false);        	
        	panelStatusMessages.addMessage("Turn for player " + turn);
        }
        
        setInitialPlayerModes();
        setInitialPlayersColors();        
        repaint();
	}
	
	protected void handleOnGameOver(int[][] board, S state, int winner) {
    	this.board = board;
    	this.state = state;
    	deActivateBoard();
    	botonRandom.setEnabled(false);
    	botonIntelligent.setEnabled(false);
    	comboPlayer.setEnabled(false);
    	
    	if(localPlayer != winner){
    		if(this.state.isFinished() && state.getWinner() != -1){
    			panelStatusMessages.addMessage("Game Over!");
    			panelStatusMessages.addMessage("The Winner is: " + winner);
    		}
    		else if(this.state.isFinished() && state.getWinner() == -1){
    			panelStatusMessages.addMessage("Game Over! ");
    			panelStatusMessages.addMessage("Draw! ");
    		}
    	}
    	else if(localPlayer == winner){
    		if(this.state.isFinished() && state.getWinner() == winner){
        		panelStatusMessages.addMessage("Game Over! ");
        		panelStatusMessages.addMessage("The Winner is You! " + "(" + winner + ")");
        	}
        }
	}
	
	protected void handleOnMoveStart(int[][] board, int turn) {
	}
	
	protected void handleOnMoveEnd() {
		repaint();
	}
	
	protected void handleOnChangeTurn(int[][] board, int turn){
		this.turno = turn;
        this.board = board;
        
        if(localPlayer == turn){
      	  activateBoard();
      	  botonRandom.setEnabled(true);
      	  botonIntelligent.setEnabled(true);
        }
        else{
        	deActivateBoard();
        	botonRandom.setEnabled(false);
        	botonIntelligent.setEnabled(false);
        }
        //preguntamos al controlador si ha terminado el juego
        if(!gameControl.getGame().getState().isFinished()){
        	
      	  if((localPlayer != this.turno)){
      		  panelStatusMessages.addMessage("Turn for " + turn);
      	  }
      	  else {
      		  panelStatusMessages.addMessage("Turn for You " + "(" +  turn + ")");
      	  }
        }
        
        //si es un jugador manual
        if(playerMode.get(turn).equals(PlayerMode.MANUAL)){
      	  if(!gameControl.getGame().getState().isFinished()){
      		  if(localPlayer == this.turno){
      			  //TODO OJOJOJOJOJ nameGame
      			  if(this.nameGame.equals(this.getNameGame())){
      				  panelStatusMessages.addMessage("Click on an origin piece");
      			  }
      			  else
      				  panelStatusMessages.addMessage("Click on an emty cell");
      		  }
      	  }
      	  //habilitar botones
      	  if(randPlayer == null)
      		  botonRandom.setEnabled(true);      	  
      	  if(smartPlayer == null)
      		  botonIntelligent.setEnabled(true);
        }
        
        //si es un jugador automatico
        else{
        	//deshabilitar botones
      	  if(randPlayer != null)
      		  botonRandom.setEnabled(false);
      	  if(smartPlayer != null)
      		  botonIntelligent.setEnabled(false);
      		 
      	  deActivateBoard();
      	  decideMakeAutomaticMove(randPlayer, smartPlayer);
        }
        repaint();
	}
	
	protected void handleOnError(String msg){
		//TODO
	}

}
