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
package org.openshapa.controllers;

import com.usermetrix.jclient.Logger;
import org.openshapa.OpenSHAPA;
import database.DataCell;
import database.SystemErrorException;
import database.TimeStamp;
import org.openshapa.util.Constants;
import org.openshapa.views.discrete.SpreadsheetPanel;

import com.usermetrix.jclient.UserMetrix;

/**
 * Controller for setting all selected cells to have the specified start time /
 * onset.
 */
public class SetSelectedCellStartTimeC {

    /** The logger for this class. */
    private static Logger LOGGER = UserMetrix.getLogger(SetSelectedCellStartTimeC.class);

    /**
     * Sets all selected cells to have the specified start time / onset.
     * 
     * @param milliseconds
     *            The time in milliseconds to use for all selected cells onset /
     *            start time.
     */
    public SetSelectedCellStartTimeC(final long milliseconds) {

        LOGGER.event("set selected cell onset");

        // Get the view for this controller (the main spreadsheet panel.
        SpreadsheetPanel view =
                (SpreadsheetPanel) OpenSHAPA.getApplication().getMainView()
                        .getComponent();

        try {
            for (DataCell c : view.getSelectedCells()) {
                c.setOnset(new TimeStamp(Constants.TICKS_PER_SECOND,
                        milliseconds));
                OpenSHAPA.getProjectController().getLegacyDB().getDatabase().replaceCell(c);
            }
        } catch (SystemErrorException se) {
            LOGGER.error("Unable to set selected cell onset", se);
        }
    }
}
