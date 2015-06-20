package MODEL;

import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import MODEL.DatabaseBrowse;

public class TableListener implements TableModelListener {

	
	
	
	@Override
	public void tableChanged(TableModelEvent e) {
		int firstRow = e.getFirstRow();
		int lastRow = e.getLastRow();
		int mColIndex = e.getColumn();
		
		switch(e.getType()){
			case TableModelEvent.INSERT:
			//the inserted rows are in the range [firstRow, lastRow]
				for(int r = firstRow;r<=lastRow;r++){
					//row r was inserted
					
				}
			break;
		}
		
	}

}
