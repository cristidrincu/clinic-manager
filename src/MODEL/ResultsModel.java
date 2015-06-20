package MODEL;
import java.sql.*;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class ResultsModel extends AbstractTableModel {
	
	//class data members
	//storage container for column names from database table
	private String[] columnNames = new String[0]; //string array to hold the column names
	private Vector<String[]> dataRows = new Vector<String[]>();//vector that holds String[] elements that store row data
	
	public void setResultSet(ResultSet results){
		try{
			ResultSetMetaData metadata = results.getMetaData();//must investigate metadata for result sets
			int numberOfColumns = metadata.getColumnCount();//finds out how many columns are in the database table
			columnNames=new String[numberOfColumns];
			
			//now we can find out the column names
			for(int i=0;i<numberOfColumns;i++){
				columnNames[i] = metadata.getColumnLabel(i+1); //gets the column name at position 1, 2, 3... n, where n is the number of columns
			}
			
			//get all the rows
			dataRows.clear(); //empty vector to store the data
			String[] rowData; //a String array that will store row data
			while(results.next()){
				rowData = new String[numberOfColumns];//the string array will contain row storage space based on the number of columns
				for(int i=0;i<numberOfColumns;i++){
					rowData[i] = results.getString(i+1); 
				}
				dataRows.addElement(rowData);//store the row in the vector
			}
			fireTableChanged(null);//Signal the table there is new model data
			//fireTableDataChanged();
			
		}catch(SQLException sqle){
			System.out.println(sqle.getMessage());
		}
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return dataRows == null ? 0:dataRows.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		return dataRows.elementAt(row)[column];
	}

	public String getColumnName(int column) {
		return columnNames[column]==null? "No name":columnNames[column];
	}

	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}
	
	public boolean isCellEditable(int row, int col){
	      return false;
	   }
			
	public void setValueAt(Object value, int row, int col){
		dataRows.elementAt(row)[col] = (String)value;  
        fireTableCellUpdated(row, col);
	}
}