package es.ucm.fdi.tp.mvc;

import java.util.List;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent.EventType;

/**
 * An event-driven game engine.
 * Keeps a list of players and a state, and notifies observers
 * of any changes to the game.
 */
public class GameTable<S extends GameState<S, A>, A extends GameAction<S, A>> implements GameObservable<S, A> {

    // define fields here
	//TODO aniadidos
	private GameState<S, A> game;
	private boolean finalizar;
	private List<GameObservable<S, A>> observers;
	
	private GameEvent<S,A> gameEvent;
	

    public GameTable(S initState) {
        // add code here
    	game = initState;
    	finalizar = false;
    	//gameEvent = new GameEvent<S,A>(EventType.Start, A, S, error, description);
    }
    public void start() {
        // add code here
    	//gameEvent = new GameEvent<S, A>(Type, action, state, error, description)
    }
    public void stop() {
        // add code here
    	finalizar = true;
    }
    public void execute(A action) {
        // add code here
    }
    public S getState() {
        // add code here
    	return (S) game;
    }

    public void addObserver(GameObserver<S, A> o) {
        // add code here
    	observers.add((GameObservable<S, A>) o);
    }
    public void removeObserver(GameObserver<S, A> o) {
        // add code here
    	observers.remove(o);
    }
}
