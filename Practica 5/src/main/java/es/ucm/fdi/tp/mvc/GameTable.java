package es.ucm.fdi.tp.mvc;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameError;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent.EventType;

/**
 * An event-driven game engine.
 * Keeps a list of players and a state, and notifies observers
 * of any changes to the game.
 */
public class GameTable<S extends GameState<S, A>, A extends GameAction<S, A>> implements GameObservable<S, A> {

	
	private boolean finalizar;
	private List<GameObserver<S, A>> observers;
	private GameEvent<S,A> gameEvent;
	private S estadoInicial;
	private S estado;
	private GameError error;

    public GameTable(S initState) {
    	this.estado = initState;
    	this.estadoInicial = initState;
    	finalizar = false;
    	observers = new ArrayList<GameObserver<S, A>>();
    	this.error = new GameError("Game Error");
    }
    
    private void enviarEvento(GameEvent<S,A> e){
    	for(GameObserver<S, A> o : observers){
    		o.notifyEvent(e);
    	}
    }
    
    public void start() {
    	
    	estado = estadoInicial;
    	//finalizar = false;
    	enviarEvento(new GameEvent<S,A>(EventType.Start, null, estado, null, "Start Game"));
    	
    }
    
    
    public void stop() {
       
    	if(finalizar){
    		enviarEvento(new GameEvent<S,A>(EventType.Error, null, null, error, ""));
    		throw error;
    	}
    	
    	
    	else{

    			finalizar = true;
				String endText = "The game ended: ";
				int winner = getState().getWinner();
				
				if (winner == -1) {
					endText += "draw!";
				} else {
					endText += "player " + winner + " won!";
				}
			
    		enviarEvento(new GameEvent<S,A>(EventType.Stop, null, estado, null, endText));
    	}
    }
    
    
    public void execute(A action) {
    	
    	S stateAux = null;
    	stateAux = action.applyTo(estado);
    	
    	if(finalizar || stateAux == null){ 
			enviarEvento(new GameEvent<S,A> (EventType.Error, null, null, error, "Not the turn of this player"));
			throw error;
		}
    		
    	if(stateAux != null){
    		estado = stateAux;
    		enviarEvento(new GameEvent<S,A> (EventType.Info, action, estado, null, ""));
    		enviarEvento(new GameEvent<S,A> (EventType.Change, action, estado, null, "After action:\n" + getState()));
    	}
    	
    	
    }
    
    public S getState() {
      
    	return estado;
    }

    /**
     * Añade un observador a la lista de observadores
     */
    public void addObserver(GameObserver<S, A> o) {
    	
    	observers.add(o);
    }
    
    
    public void removeObserver(GameObserver<S, A> o) {
    	
    	observers.remove(o);
    }
}
