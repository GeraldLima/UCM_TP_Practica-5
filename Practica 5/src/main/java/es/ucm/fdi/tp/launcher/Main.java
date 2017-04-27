package es.ucm.fdi.tp.launcher;

import es.ucm.fdi.tp.base.console.ConsolePlayer;
import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameError;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.ttt.TttState;
import es.ucm.fdi.tp.view.ConsoleView;
import es.ucm.fdi.tp.was.WolfAndSheepState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;


public class Main {
	
	final private static GameInfo DEFAULT_GAME = GameInfo.Ttt;
	final private static ViewInfo DEFAULT_VIEW = ViewInfo.CONSOLE;
	final private static PlayerMode DEFAULT_PLAYERMODE = PlayerMode.MANUAL;
	
	//aniadido
	private static GameState gameState;
	private static ViewInfo view;
	private static Integer dimRows, dimCols;
	
	enum GameInfo{		
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
	
	enum PlayerMode{		
		MANUAL("manual", "jugador manual"), RANDOM("rand", "jugador aleatorio"), INTELLIGENT("smart","jugador inteligente");
		
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
	
	enum ViewInfo {
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
	
	private static void parseArgs(String[] args) {

		Options cmdLineOptions = new Options();
		cmdLineOptions.addOption(constructHelpOption()); // -h or --help
		cmdLineOptions.addOption(constructGameOption()); // -g or --game
		cmdLineOptions.addOption(constructViewOption()); // -v or --view
		cmdLineOptions.addOption(constructPlayersOption()); // -p or --players
		cmdLineOptions.addOption(constructDimensionOption()); // -d or --dim
		
		// parse the command line
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseHelpOption(line, cmdLineOptions);		
			parseDimOptionn(line);
			parseGameOption(line);
			parseViewOption(line);
			parsePlayersOptions(line);
			
		
			// si algo va mal
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException | GameError e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}
	
	/********contructor options********/
	private static Option constructHelpOption() {
		return new Option("h", "help", false, "Print this message");
	}
	
	private static Option constructGameOption() {
		String optionInfo = "The game to play ( ";
		for (GameInfo i : GameInfo.values()) {
			optionInfo += i.getGameInfo() + " [for " + i.getGameDescription() + "] ";
		}
		optionInfo += "). By defualt, " + DEFAULT_GAME.getGameInfo() + ".";
		Option opt = new Option("g", "game", true, optionInfo);
		opt.setArgName("game identifier");
		return opt;
	}
	
	private static Option constructViewOption() {
		String optionInfo = "The view to use ( ";
		for (ViewInfo i : ViewInfo.values()) {
			optionInfo += i.getId() + " [for " + i.getDesc() + "] ";
		}
		optionInfo += "). By defualt, " + DEFAULT_VIEW.getId() + ".";
		Option opt = new Option("v", "view", true, optionInfo);
		opt.setArgName("view identifier");
		return opt;
	}
	
	private static Option constructPlayersOption() {
		String optionInfo = "A player has the form A:B, and B is the player mode (";
		for (PlayerMode i : PlayerMode.values()) {
			optionInfo += i.getPlayerMode() + " [for " + i.getPlayerModeDescription() + "] ";
		}
		optionInfo += "). If B is not given, the default mode '" + DEFAULT_PLAYERMODE.getPlayerMode();

		Option opt = new Option("p", "players", true, optionInfo);
		opt.setArgName("list of players");
		return opt;
	}
	
	private static Option constructDimensionOption() {
		return new Option("d", "dim", true,
				"The board size (if allowed by the selected game). It must has the form ROWSxCOLS.");
	}
	
	/*******parse options******/
	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}
	private static void parseDimOptionn(CommandLine line) throws ParseException {
		String dimVal = line.getOptionValue("d");
		if (dimVal != null) {
			try {
				String[] dim = dimVal.split("x");
				if (dim.length == 2) {
					dimRows = Integer.parseInt(dim[0]);
					dimCols = Integer.parseInt(dim[1]);
				} else {
					throw new ParseException("Invalid dimension: " + dimVal);
				}
			} catch (NumberFormatException e) {
				throw new ParseException("Invalid dimension: " + dimVal);
			}
		}

	}
	private static void parseGameOption(CommandLine line) throws ParseException {
		String gameVal = line.getOptionValue("g", DEFAULT_GAME.getGameInfo());
		GameInfo selectedGame = null;

		for( GameInfo g : GameInfo.values() ) {
			if ( g.getGameInfo().equals(gameVal) ) {
				selectedGame = g;
				break;
			}
		}
		if ( selectedGame == null ) {
			throw new ParseException("Uknown game '" + gameVal + "'");
		}
	
		switch ( selectedGame ) {
			case Ttt:
				gameState = new TttState(dimRows);
				break;
			case Was:
				gameState = new WolfAndSheepState(dimRows);
				break;
				
			default:
				throw new UnsupportedOperationException("Algo ha ido mal en peor!! ");
		}
	}
	private static void parseViewOption(CommandLine line) throws ParseException {
		String viewVal = line.getOptionValue("v", DEFAULT_VIEW.getId());
		// view type
		for (ViewInfo v : ViewInfo.values()) {
			if (viewVal.equals(v.getId())) {
				view = v;
			}
		}
		if (view == null) {
			throw new ParseException("Uknown view '" + viewVal + "'");
		}
	}
	//TODO falta!!
	private static void parsePlayersOptions(CommandLine line) throws ParseException {

		String playersVal = line.getOptionValue("p");
		//.....
		//.......
		//...
	}
	
	/////////////lo que pone en el PDF:

	private static GameTable<?, ?> createGame(String gType) {
		return null;
		// c r e a t e a game with a GameState depending on the value of gType
	}
	
	private static <S extends GameState<S, A>, A extends GameAction<S, A>>
				void startConsoleMode(String gType, GameTable<S, A> game, String playerModes[]) {
		// c r e a t e the l i s of p l a y e r s as i n assignemnt 4
		// . . .
		List<GamePlayer> players = new ArrayList<GamePlayer>();
		int playerCount = 0;
		for (GamePlayer p : players) {
			p.join(playerCount++); // welcome each player, and assign
									// playerNumber
		}
		
		new ConsoleView<S,A>(game);
		new ConsoleModeControler<S,A>(players,game).run();
	}
	
	private static <S extends GameState<S, A>, A extends GameAction<S, A>>
				void startGUIMode(String gType, GameTable<S, A> game) {
		// add code here
		//...
	}
	
	private static void usage() {
		// p r i n t usage of main
		//...
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
		
		String[] playerModes = Arrays.copyOfRange(args, 2, args.length);
		/*if (game.getState().getPlayerCount() != playerModes.length ) {
			System.err.println("Invalid number of players");
			System.exit(1);
		}*/
		
		//String gType = null;
		switch (args[1]) {
		case "console":
			startConsoleMode(gType,game,playerModes);
		break;
		case "gui":
			startGUIMode(args[0],game);
		break;
		default:
			System.err.println("Invalid view mode: "+args[1]);
			usage();
			System.exit(1);
		}
	}
	/* METODOS DE LA PRACTICA 4*/
	/*
	
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
	*/
	/* FINAL METODOS DE LA PRACTICA 4*/
}
