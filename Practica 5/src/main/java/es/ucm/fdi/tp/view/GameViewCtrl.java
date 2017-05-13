package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.launcher.Main.PlayerMode;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.mvc.GameEvent.EventType;


public class GameViewCtrl<S extends GameState<S,A>, A extends GameAction<S,A>> implements GameController<S, A> {

	
	private GamePlayer randPlayer;
	
	private GamePlayer smartPlayer;
	
	private PlayerMode playerMode;
	
	private int playerId;
	
	private GameTable<S, A> game;
	

	public GameViewCtrl(int playerId, GamePlayer randPlayer, GamePlayer smartPlayer, GameTable<S, A> game) {
		this.playerId = playerId;
		this.randPlayer = randPlayer;
		this.smartPlayer = smartPlayer;
		this.game = game;
		this.playerMode = PlayerMode.MANUAL;
	}
	

	@Override
	public void makeManualMove(A a) {
		if(a.getPlayerNumber() == playerId){
			game.execute(a);
		}
		
		if(game.getState().isFinished()){
			game.stop();
    	}
		
	}

	@Override
	public void makeRandomMove() {
		
		if(randPlayer.getPlayerNumber() == playerId){
			
			A action = randPlayer.requestAction(game.getState());
			
				game.execute(action);
		}
		
		if(game.getState().isFinished()){
			game.stop();
    	}
		
		
	}

	@Override
	public void makeSmartMove() {
		if(smartPlayer.getPlayerNumber() == playerId){
			A action = smartPlayer.requestAction(game.getState());
			
				game.execute(action);
		}
		
		if(game.getState().isFinished()){
			game.stop();
    	}
		
	}

	@Override
	public void restartGame() {
		game.start();
		
	}

	@Override
	public void stopGame() {
		game.stop();
		
	}

	@Override
	public void changePlayerMode(PlayerMode p) {
		playerMode = p;
		decideMakeAutomaticMove();
		
	}


	@Override
	public PlayerMode getPlayerMode() {
		
		return playerMode;
	}

	
	@Override
	public int getPlayerId() {
		
		return playerId;
	}
	
	@Override
	public void handleEvent(GameEvent<S, A> event) {
		if (((event.getType().equals(EventType.Info)) || (event.getType().equals(EventType.Change))) && (event.getState().getTurn() == playerId)){
			decideMakeAutomaticMove();
		}
		
	}

	@Override
	public GameTable<S, A> getGame() {
		
		return game;
	}
	
	
	/**
	 * Decide si debe hacer un movimiento random o inteligente
	 */
	private void decideMakeAutomaticMove() {
		if (playerMode.equals(PlayerMode.RANDOM))
			 makeRandomMove();
		
		if (playerMode.equals(PlayerMode.INTELLIGENT))
			 makeSmartMove();
	}
		
}

