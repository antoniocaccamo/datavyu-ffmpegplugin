/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openshapa.undoableedits;


import com.usermetrix.jclient.Logger;
import com.usermetrix.jclient.UserMetrix;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.CompoundEdit;
import javax.swing.undo.UndoManager;
import org.openshapa.OpenSHAPA;
import org.openshapa.controllers.project.ProjectController;
import org.openshapa.models.db.Cell;
import org.openshapa.models.db.Datastore;
import org.openshapa.models.db.DeprecatedVariable;
import org.openshapa.models.db.Variable;
import org.openshapa.models.db.legacy.DataCell;
import org.openshapa.models.db.legacy.DataColumn;
import org.openshapa.models.db.legacy.Database;
import org.openshapa.models.db.legacy.SystemErrorException;
import org.openshapa.views.OpenSHAPAView;
import org.openshapa.views.discrete.SpreadsheetCell;
import org.openshapa.views.discrete.SpreadsheetColumn;
import org.openshapa.views.discrete.SpreadsheetPanel;

/**
 *
 * @author harold
 */
  abstract class SpreadsheetEdit extends AbstractUndoableEdit {
    private static final Logger LOGGER = UserMetrix.getLogger(SpreadsheetEdit.class);
    protected ProjectController controller;
    protected Datastore model;
    protected Database db;  
    protected OpenSHAPAView view;
    
    public SpreadsheetEdit() {
        super();
        controller = OpenSHAPA.getProjectController();
        model = controller.getDB();
        db = controller.getLegacyDB().getDatabase();
        view = OpenSHAPA.getView();
    }

    @Override
    public String getPresentationName() {
        return super.getPresentationName();
    }

    @Override
    public void redo() throws CannotRedoException {
        super.redo();
    }

    @Override
    public void undo() throws CannotUndoException {
        super.undo();
    }
    
    protected Variable getVariable(String varName) {
        for (Variable v : model.getAllVariables()) {
            String variableName = v.getName();
            if (variableName.equals(varName)) {
                return v;
            }
        } 
        return null;
    }    
 
    protected DataCell getDataCell(CellPos cellPos) {
        try {        
            DataColumn col = (DataColumn) db.getColumn(cellPos.varName);
            int numCells = col.getNumCells();
            long ColID = col.getID();    
            DataCell cell = (DataCell)db.getCell(ColID, cellPos.ord);
            return cell;
        } catch (SystemErrorException e) {
            LOGGER.error("Unable to getDataCell", e);
            return null;
        }
    } 
    
    protected CellPos getCellPos(DataCell cell) {
        try {
            long colID = cell.getItsColID();
            DataColumn col = (DataColumn)db.getColumn(colID);
            String varName = col.getName();
            int ord = cell.getOrd(); 
            CellPos cellPos = new CellPos(varName, ord);
            return cellPos;
        } catch (SystemErrorException e) {
            LOGGER.error("Unable to getCellPos", e);
            return null;
        }
    }
    
    protected SpreadsheetCell getSpreadsheetCell(DataCell cell) {
        for (SpreadsheetColumn sCol : getSpreadsheet().getColumns()) {
            for (SpreadsheetCell sCell : sCol.getCells()) {
                if (sCell.getCellID() == cell.getID()) {   
                    return sCell;
                }
            }
        }  
        return null;
    }
    
    protected SpreadsheetColumn getSpreadsheetColumn(String columnName) {
        for (SpreadsheetColumn sCol : getSpreadsheet().getColumns()) {
            if (sCol.getColumnName().equals(columnName)) {   
                    return sCol;
            }
        }  
        return null;
    }    
    
    protected void unselectAll() {
        view.getSpreadsheetPanel().clearColumnSelection();
        view.getSpreadsheetPanel().clearCellSelection();        
    }
    
    protected class CellPos {
        public String varName; // Column
        public int ord;      // Row
        public CellPos(String varName, int index) {
            this.varName = varName;
            this.ord = index;
        }
        
    }
    
    protected SpreadsheetPanel getSpreadsheet() {
        return view.getSpreadsheetPanel();
    }
}
