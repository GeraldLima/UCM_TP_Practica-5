package es.ucm.fdi.tp.view;

import javax.swing.table.AbstractTableModel;


@SuppressWarnings("serial")
public class TableModelSwing extends AbstractTableModel {			
	
	private final String[] columnNames/* ={"Player", "Color"}*/;
	private int fils = 0;
	private int cols = 0;
	//private Map<Integer,GamePlayer> playerMode;
	//private GameState state;
	private Object[][] data;
		
	public TableModelSwing (){
		//players = jugadores;
		columnNames = new String[] {"Player", "Color"};
		data = new Object[fils][cols];
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col].toString();
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public int getRowCount() {
		//return (players == null ? 0: players.length);
		return data.length;
	}
	
	public void addPlayer(String idPlayer, Object color) {
		fils++;
		Object[][] nuevoData = new Object[fils][cols];
		// rellena con los datos anteriores
		for (int i = 0; i < fils - 1; i++) {
			for (int j = 0; j < cols; j++) {
				nuevoData[i][j] = data[i][j];
			}
		}
		data = nuevoData;

		// datos nueva juagdor
		data[fils - 1][0] = idPlayer;
		data[fils - 1][1] = color;

		fireTableRowsInserted(fils - 1, fils - 1);
	}
	
	public void refresh(){
		this.fireTableDataChanged();
	}
	
	public void removeAll(){
		fils = 0;
		data = new Object[fils][cols];
	}

	@Override
	public Object getValueAt(int row, int col) {
		return data[row][col];
		//Object object = new Object[row][col];
		
		/*Object object = null;
		if(players != null){
			int[] jugadores = players;
			switch(col){
			case 0:
				object = jugadores;
				break;
			case 1:
				object = playerMode.get(jugadores);
				break;
			case 2:
				object = state.evaluate(jugadores[0]);
				break;
			default:
				break;
			}
		}
		return object;*/
	}
	
	@Override
	public void setValueAt(Object value, int row, int col){
		data[row][col] = value;
		fireTableCellUpdated(row, col);
	}
	
	@Override
	public boolean isCellEditable(int row, int col){
		return false;
	}
	
	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("Datos: " + System.lineSeparator());
		for (int i = 0; i < fils; i++) {
			for (int j = 0; j < cols; j++) {
				s.append(data[i][j]);
				s.append("  ");
			}
			s.append(System.lineSeparator());
		}
		s.append("Tipos de los objetos: " + System.lineSeparator());
		for (int i = 0; i < fils; i++) {
			for (int j = 0; j < cols; j++) {
				if (data[i][j] != null)
					s.append(data[i][j].getClass().getName() + "  ");
				else
					s.append("null  ");
			}
			s.append(System.lineSeparator());
		}
		return s.toString();
	}
	


}
