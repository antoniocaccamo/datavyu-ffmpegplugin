/**
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openshapa.views.discrete.datavalues;

import com.usermetrix.jclient.Logger;
import com.usermetrix.jclient.UserMetrix;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import javax.swing.text.JTextComponent;

import database.DataCell;
import database.Database;
import database.Matrix;
import database.NominalDataValue;
import database.PredDataValue;
import database.SystemErrorException;

/**
 * This class is the character editor of a NominalDataValue.
 */
public final class NominalDataValueEditor extends DataValueEditor {

    /**
     * String holding the reserved characters - these are characters that are
     * users are unable to enter into a nominal field.
     */
    // BugzID:524 - If Character is an escape key - ignore it.
    private static final String RESERVED_CHARS = ")(<>|,;\t\r\n\"\u001B";

    /** The logger for this class. */
    private static Logger LOGGER = UserMetrix.getLogger(NominalDataValueEditor.class);

    /**
     * Constructor.
     *
     * @param ta The parent JTextComponent the editor is in.
     * @param cell The parent data cell this editor resides within.
     * @param matrix Matrix holding the datavalue this editor will represent.
     * @param matrixIndex The index of the datavalue within the matrix.
     */
    public NominalDataValueEditor(final JTextComponent ta,
                                  final DataCell cell,
                                  final Matrix matrix,
                                  final int matrixIndex) {
        super(ta, cell, matrix, matrixIndex);
    }

    /**
     * Constructor.
     *
     * @param ta The parent JTextComponent the editor is in.
     * @param cell The parent data cell this editor resides within.
     * @param p The predicate holding the datavalue this editor will represent.
     * @param pi The index of the datavalue within the predicate.
     * @param matrix Matrix holding the datavalue this editor will represent.
     * @param matrixIndex The index of the datavalue within the matrix.
     */
    public NominalDataValueEditor(final JTextComponent ta,
                                  final DataCell cell,
                                  final PredDataValue p,
                                  final int pi,
                                  final Matrix matrix,
                                  final int matrixIndex) {
        super(ta, cell, p, pi, matrix, matrixIndex);
    }

    /**
     * Action to take when focus is lost for this editor.
     * @param fe Focus Event
     */
    @Override
    public void focusLost(final FocusEvent fe) {
        // BugzID:581 - Trim trailing spaces from nominal (apparently they are
        // not permitted.
        super.focusLost(fe);
        try {
            if (!Database.IsValidNominal(this.getText())
                && !this.getText().equals(this.getNullArg())) {
                NominalDataValue ndv = (NominalDataValue) getModel();
                this.setText(ndv.getItsValue());
            }
        } catch (SystemErrorException e) {
            LOGGER.error("Unable to determine if nominal is valid", e);
        }

        super.focusLost(fe);
    }

    /**
     * The action to invoke when a key is typed.
     *
     * @param e The KeyEvent that triggered this action.
     */
    @Override
    public void keyTyped(final KeyEvent e) {
        super.keyTyped(e);

        // Just a regular vanilla keystroke - insert it into nominal field.
        NominalDataValue ndv = (NominalDataValue) getModel();

        if (!e.isConsumed() && !e.isMetaDown() && !e.isControlDown()
            && !isReserved(e.getKeyChar())) {
            this.removeSelectedText();
            StringBuilder currentValue = new StringBuilder(getText());
            
            // If we have a delete or backspace key - do not insert.
            if (!(e.getKeyLocation() == KeyEvent.KEY_LOCATION_UNKNOWN
                  && e.getKeyChar() == '\u007F') &&
                !(e.getKeyLocation() == KeyEvent.KEY_LOCATION_UNKNOWN
                  && e.getKeyChar() == '\u0008')) {
                currentValue.insert(getCaretPosition(), e.getKeyChar());
            }

            // Advance caret over the top of the new char.
            int pos = this.getCaretPosition() + 1;
            this.setText(currentValue.toString());
            this.setCaretPosition(pos);
            e.consume();

        // All other keystrokes are consumed.
        } else {
            e.consume();
        }

        // Push the character changes into the database.
        try {
            if (Database.IsValidNominal(this.getText())) {
                ndv.setItsValue(this.getText());
                updateDatabase();

            // BugzID:668 - The user is reverting back to a 'placeholder' state.
            } else if (this.getText().equals("")) {
                ndv.clearValue();
                updateDatabase();
            }
        } catch (SystemErrorException se) {
            LOGGER.error("Unable to edit text string", se);
        }
    }

    /**
     * @param aChar Character to test
     * @return true if the character is a reserved character.
     */
    public boolean isReserved(final char aChar) {
        return (RESERVED_CHARS.indexOf(aChar) >= 0);
    }
}