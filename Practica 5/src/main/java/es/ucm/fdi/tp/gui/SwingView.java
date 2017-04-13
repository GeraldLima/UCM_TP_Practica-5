package es.ucm.fdi.tp.gui;

import javax.swing.JFrame;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObserver;

public class SwingView extends JFrame implements GameObserver{

	@Override
	public void notifyEvent(GameEvent e) {
		// TODO Auto-generated method stub
		return null;
	}

}
