package es.ucm.fdi.tp.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class PlayerInfoModel extends AbstractTableModel{

	/**
	 * array de los nombres de las columnas
	 */
	private String[] colNames;
	private List<Integer> pieces;
	
	/**
	 * Constructora que inicializa las nombres de las columnas de la tabla
	 */
	PlayerInfoModel (List<Integer> players) {
		pieces = players;
		this.colNames = new String[] {"#Player", "Color"};
	}
	
	/**
	 * Devuelve el nombre de la columna indicada
	 */
	@Override
	public String getColumnName(int col) {
		return colNames[col];
	}
	
	/**
	 * Devuelve el numero de columnas
	 */
	@Override
	public int getColumnCount() {
		return colNames.length;
	}
	
	/**
	 * Devuelve la informacion de las columnas de la tabla: el nombre del jugador y vacia que es el color del jugador
	 */
	@Override
	public int getRowCount() {
		return pieces != null ? pieces.size() : 0;
	}

	@Override
	public Object getValueAt(int row, int col) {
		int p = pieces.get(row);
         
         if(col == 0){
             return p;
         }
         else{
         	 return "";
         }
    }
	
	/**
	 * Refresca la informacion de la tabla
	 */
	public void refresh() {
		fireTableDataChanged();
	}	
}
