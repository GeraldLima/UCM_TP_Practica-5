
package es.ucm.fdi.tp.launcher;

import es.ucm.fdi.tp.base.console.ConsolePlayer;
import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.ttt.TttState;
import es.ucm.fdi.tp.view.ConsoleController;
import es.ucm.fdi.tp.view.ConsoleView;
import es.ucm.fdi.tp.view.GameController;
import es.ucm.fdi.tp.view.GameViewCtrl;
import es.ucm.fdi.tp.view.TttView;
import es.ucm.fdi.tp.view.WasView;
import es.ucm.fdi.tp.was.WolfAndSheepState;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.SwingUtilities;


public class Main {
	
	public enum GameInfo{		
		Ttt("ttt", "Tres en Raya"), Was("was", "Wolf and sheep");
		
		private String id;
		private String desc;
		
		GameInfo(String id, String desc) {
			this.id = id;
			this.desc = desc;
		}

		public String getGameInfo() {
			return id;
		}
		public String getGameDescription() {
			return desc;
		}

		@Override
		public String toString() {
			return id;
		}		
	}
	
	public enum PlayerMode{		
		MANUAL("manual", "jugador manual"), RANDOM("random", "jugador aleatorio"), INTELLIGENT("smart","jugador inteligente");
		
		private String id;
		private String desc;
		
		PlayerMode(String id, String desc) {
			this.id = id;
			this.desc = desc;
		}

		public String getPlayerMode() {
			return id;
		}
		public String getPlayerModeDescription() {
			return desc;
		}

		@Override
		public String toString() {
			return id;
		}		
	}
	
	public enum ViewInfo {
		WINDOW("window", "Modo Swing"), CONSOLE("console", "Modo Consola");

		private String id;
		private String desc;

		ViewInfo(String id, String desc) {
			this.id = id;
			this.desc = desc;
		}

		public String getId() {
			return id;
		}

		public String getDesc() {
			return desc;
		}

		@Override
		public String toString() {
			return id;
		}
	}
	
	
	private static GameTable<?, ?> createGame(String gType) { 
		
		if(gType.equals(GameInfo.Ttt.getGameInfo())){ 
			return new GameTable<>(new TttState(3)); 
		}
		
		if(gType.equals(GameInfo.Was.getGameInfo())){
			return new GameTable<>(new WolfAndSheepState(8) );
		}
		else
			return null;
		
	}
	
	
	
	@SuppressWarnings("resource")
	public static GamePlayer createPlayer(String playerType, String playerName){
		
		Scanner s = new Scanner(System.in);
				
		if(playerType.equalsIgnoreCase(PlayerMode.MANUAL.getPlayerMode())){
			  return new ConsolePlayer(playerName, s);
		}
		
		else if(playerType.equalsIgnoreCase(PlayerMode.RANDOM.getPlayerMode())){
			return new  RandomPlayer(playerName);
		}
		
		else if(playerType.equalsIgnoreCase(PlayerMode.INTELLIGENT.getPlayerMode())){
			return new  SmartPlayer(playerName, 5);
		}
		
		else
			return null;
	}

	/**
	 * Metodo para gestionar el modo consola
	 * @param gType
	 * @param game
	 * @param playerModes
	 */
	private static <S extends GameState<S, A>, A extends GameAction<S, A>> 
				void startConsoleMode(String gType, GameTable<S, A> game, 
	String playerModes[]) {
		
		
		List<GamePlayer> players = new ArrayList<GamePlayer>(); //crea lista vacia
		int playerCount = 0;
		GamePlayer player1, player2;
		
		if (playerModes.length != 2) {
			usage();
			System.exit(1);
		}
		
		if(gType.equals("ttt")){
				
			player1 = (createPlayer(playerModes[0], "Andrea"));
			player2 = (createPlayer(playerModes[1] ,"Gerald"));
						
		}
		
		else{ //lobos y ovejas
			
			player1 = (createPlayer(playerModes[0], "WOLF"));
			player2 = (createPlayer(playerModes[1] ,"SHEEPS"));
		}
		
		if(player1 == null || player2 == null){
			System.err.println("Invalid player");
			System.exit(1);
		}
		
		else{
			players.add(player1);
			players.add(player2);
		}
		
		new ConsoleView<S,A>(game);
		new ConsoleController<S,A>(players,game).run();
	}
	
	
	private static <S extends GameState<S, A>, A extends GameAction<S, A>> 
				void startGUIMode(String gType, GameTable<S, A> game, String[] playerMode) {
		
		if (playerMode.length != 0) {
			usage();
			System.exit(1);
		}
		
		for (int i = 0; i < game.getState().getPlayerCount(); i++) {
			
			GamePlayer random;
			GamePlayer smart;
			int playerId = i;
			random = createPlayer("random", "");
			smart = createPlayer("smart", "");
			random.join(i);
			smart.join(i);
			
			try{
				SwingUtilities.invokeAndWait(new Runnable(){ 
					@SuppressWarnings({ "unchecked", "rawtypes" })
					public void run(){
						GameController<S, A> gameCtrl = new GameViewCtrl<>(playerId, random, smart, game);
						
						if(gType.equals(GameInfo.Ttt.getGameInfo())){
							new TttView(playerId, random, smart, game, gameCtrl); 
						}
						else 
							new WasView(playerId, random, smart, game, gameCtrl);
					}
				});
			} 
			 
			catch (InvocationTargetException | InterruptedException e){
				System.err.println("Some error occurred while creating the view ...");
				System.exit(1);
			}
			 
		}
		
		SwingUtilities.invokeLater(new Runnable(){ 
			public void run(){ 
				game.start(); 
			} 
		});		
	}	
	
	private static void usage() {
		System.out.println("Invalid commands number");
	}
		
	public static void main(String[] args) {
		
		if (args.length < 2) {
			usage();
			System.exit(1);
		}
		
		GameTable<?, ?> game = createGame(args[0]);
		
		if (game == null) {
			System.err.println("Invalid game");
			System.exit(1);
		}
		
		String[] others = Arrays.copyOfRange(args, 2, args.length);
		
		
		switch (args[1]) {
		
		case "console":
			startConsoleMode(args[0],game,others);
		break;
		
		case "gui":
			startGUIMode(args[0],game,others);
		break;
		
		default:
			System.err.println("Invalid view mode: "+args[1]);
			usage();
			System.exit(1);
		}
	}
}
