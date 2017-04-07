package es.ucm.fdi.tp.launcher;

import es.ucm.fdi.tp.base.console.ConsolePlayer;
import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameError;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
import es.ucm.fdi.tp.ttt.TttState;
import es.ucm.fdi.tp.was.WolfAndSheepState;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Demo main, used to test game functionality. You can use it as an inspiration,
 * but we expect you to build your own main-class.
 */
public class Main {

	
	enum GameInfo{
		
		Ttt("ttt"), Was("was");
		
		private String id;
		
		GameInfo(String id) {
			this.id = id;
		}

		public String getGameInfo() {
			return id;
		}

		@Override
		public String toString() {
			return id;
		}
		
		
	}
	
	
enum PlayerMode{
		
		CONSOLE("console"), RANDOM("rand"), INTELLIGENT("smart");
		
		private String id;
		
		PlayerMode(String id) {
			this.id = id;
		}

		public String getPlayerMode() {
			return id;
		}

		@Override
		public String toString() {
			return id;
		}
		
		
	}
	public static <S extends GameState<S, A>, A extends GameAction<S, A>> 
				int playGame(GameState<S, A> initialState, List<GamePlayer> players) {
		int playerCount = 0;
		for (GamePlayer p : players) {
			p.join(playerCount++); // welcome each player, and assign
									// playerNumber
		}
		@SuppressWarnings("unchecked")
		S currentState = (S) initialState;
		System.out.println("Original State:\n" + currentState);
		int cont= 1;
		while (!currentState.isFinished()) {
			// request move
			A action = players.get(currentState.getTurn()).requestAction(currentState);
			// apply move
			currentState = action.applyTo(currentState);
			System.out.println("After " + cont + " action:\n" + currentState);
			cont++;
			if (currentState.isFinished()) {
				// game over
				String endText = "The game ended: ";
				int winner = currentState.getWinner();
				if (winner == -1) {
					endText += "draw!";
				} else {
					endText += "player " + (winner + 1) + " (" + players.get(winner).getName() + ") won!";
				}
				System.out.println(endText);
			}
		}
		return currentState.getWinner();
	}

	
	
	public static GameState<?,?> createInitialState(String gameName){
		
		if(gameName.equalsIgnoreCase(GameInfo.Ttt.getGameInfo())){
			return new TttState(3);
		}
		
		else if(gameName.equalsIgnoreCase(GameInfo.Was.getGameInfo())){
			return new WolfAndSheepState(8);
		}
		
		else
			return null;	
		
	}

	public static GamePlayer createPlayer(String gameName, String playerType, String playerName, Scanner s){
		
		
		if(playerType.equalsIgnoreCase(PlayerMode.CONSOLE.getPlayerMode())){
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
	 * Plays tick-tack-toe with a console player against a smart player. The
	 * smart player should never lose.
	 */
	public static void testTtt() {
		try (Scanner s = new Scanner(System.in)) {
			List<GamePlayer> players = new ArrayList<GamePlayer>();
			GameState<?, ?> game = new TttState(3);
			players.add(new ConsolePlayer("Alice", s));
			players.add(new SmartPlayer("AiBob", 5));
			playGame(game, players);
		} // <-- closes the scanner when the try-block ends
	}

	
	public static boolean compruebaArgumentos(String [] palabras){
		if(palabras.length == 3){
			
			if(!(palabras[0].equalsIgnoreCase("ttt") || palabras[0].equalsIgnoreCase("was")))
				throw new GameError("Error: Nombre de juego incorrecto");
		
			if(!(palabras[1].equalsIgnoreCase("console") || palabras[1].equalsIgnoreCase("rand") || palabras[1].equalsIgnoreCase("smart")))
				throw new GameError("Error: El tipo de jugador ´´" + palabras[1].toString() + "`` no está definido");
		
			else if(!(palabras[2].equalsIgnoreCase("console") || palabras[2].equalsIgnoreCase("rand") || palabras[2].equalsIgnoreCase("smart")))
				throw new GameError("Error: El tipo de jugador ´´" + palabras[2].toString() + "`` no está definido");
			return true;
		}
		else{
			throw new GameError("Error: Número de argumentos inválido");
		}
	}
	/**
	 * Main method.
	 * 
	 * @param args
	 */
	public static void main(String... args) {
		// testTtt();
		
		try(Scanner s = new Scanner(System.in)){
			
		String [] palabras;
		String  line;
		
		System.out.print("> ");
		
		line = s.nextLine();
		line = line.trim();
		palabras = line.split(" +");
		
		
		if (compruebaArgumentos(palabras)){

			List<GamePlayer> players = new ArrayList<GamePlayer>();
			GameState<?, ?> game = createInitialState(palabras[0]);
			players.add(createPlayer(palabras[0], palabras[1], "WOLF", s));
			players.add(createPlayer(palabras[0], palabras[2], "SHEEPS", s));
			playGame(game, players);
		}
	
		}catch(GameError e){ //muestra todo el paquete no se porqueeeeeeeeeee
			System.out.println(e);
		}
	}
}
