package es.ucm.fdi.tp.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;

@SuppressWarnings("serial")
public class PanelStatusMessages<S extends GameState<S, A>, A extends GameAction<S, A>> extends JPanel{

	private JTextArea texto;
	
	public PanelStatusMessages  (GameController<S,A> control){
		texto = new JTextArea(/*15, 7*/);
		texto.setEditable(false);
		JScrollPane scroll = new JScrollPane(texto);
		
		this.setBorder(BorderFactory.createTitledBorder("Status Messages"));
		this.setLayout(new GridLayout());
		this.add(scroll, BorderLayout.EAST);
		this.setPreferredSize(new Dimension(250, 280));
		texto.setEditable(false);
	}
	public JTextArea getTexto(){
		return texto;
	}
	public final void addMessage(String msg){
		texto.append("*" + msg + System.getProperty("line.separator"));
	}
}
