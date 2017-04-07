package es.ucm.fdi.tp.gui;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class MiBoton extends JButton{


	protected int _fila;
	protected int _columna;
	
	MiBoton(int fil, int col){
		_fila = fil;
		_columna = col;
	
	}

	public int get_fila() {
		return _fila;
	}

	public int get_columna() {
		return _columna;
	}
	/*public JButton getMiboton(){
		return this;
	}*/
	
}

