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
package org.openshapa.models.component;

import java.util.concurrent.TimeUnit;


/**
 * Constants for the mixer interface.
 */
public interface MixerConstants {

    static final long DEFAULT_DURATION = TimeUnit.MILLISECONDS.convert(1,
            TimeUnit.MINUTES);
    static final double DEFAULT_ZOOM = 0.0;
    static final int VSCROLL_WIDTH = 17;
    static final int HSCROLL_HEIGHT = 17;
    static final int R_EDGE_PAD = 5;
    static final int MIXER_MIN_WIDTH = 785;

    static final int FILLER_ZORDER = 0;
    static final int TIMESCALE_ZORDER = 5;
    static final int TRACKS_ZORDER = 10;
    static final int REGION_ZORDER = 20;
    static final int NEEDLE_ZORDER = 30;
    static final int MARKER_ZORDER = 50;
    static final int TRACKS_SB_ZORDER = 60;

}
