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
package org.openshapa.views;

import javax.swing.JDialog;

/**
 * Generic OpenSHAPA dialog - handles work common to all dialogs (handling of
 * keystrokes, etc).
 */
public abstract class OpenSHAPADialog extends JDialog {

    /**
     * Constructor. Creates a new OpenSHAPADialog.
     *
     * @param parent The parent of this form.
     * @param modal Should the dialog be modal or not?
     */
    public OpenSHAPADialog(final java.awt.Frame parent, final boolean modal) {
        super(parent, modal);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
}
