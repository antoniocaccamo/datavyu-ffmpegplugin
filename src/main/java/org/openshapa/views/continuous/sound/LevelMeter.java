package org.openshapa.views.continuous.sound;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

/**
 * This class performs the functions of an equaliser, displaying the differing
 * intensities for each of the frequencies at the current point in time of the
 * audio file.
 */
class LevelMeter extends Canvas {

   /**
    * Desired number of frequency bands to be displayed- determined by width.
    * Old frequency bands were 200, 400, 800, 1600, 3200, 6400, 12800, 21000,
    * but these are only accurate for 8 bands. Actual frequencies are some
    * other figures for anything other than 8 bands.
    */

    /** The minimum window height to display a meter. */
    private static final int MINHEIGHT = 30;

    /** The maximum number of bars. Note that a bar is simply a small vertical
     chunk; a number of these (up to 40) make up each band. */
    private static final int MAXBARS = 40;

    /**
     * The maximum number of frequency bands that can be used, which is
     * currently fixed as the number of bands which will be displayed. For 8
     * frequency bands, we have frequencies 200, 400, 800, 1600, 3200, 6400,
     * 12800, 21000; however, for 20 bands, the actual frequencies will take
     * values other than these bands.
     *
     * Note that a band is a column of bars representing the intensity of a
     * certain frequency range.
     */
    private static final int MAXBANDS = 8;

    /** The divisor used to normalise sound level bars. */
    private static final float BARDIV = 255.0F;

    /** The colour cutoff for red colouring. */
    static final int REDCUT = (int) Math.floor(MAXBARS * 0.85);

    /** The colour cutoff for yellow colouring. */
    static final int YELLOWCUT = (int) Math.floor(MAXBARS * 0.7);

    /** The recorded sound intensities. */
    private int[] levels;

    /** Massive array with all the audio intensity data stored. */
    private int[] audioData;

    /** Stores the current time of the audio file. */
    private long audioTime = 0;

    /** The maximum value a red, green or blue value can take. */
    private static final int CMAX = 255;


    /**
     * @return The number of frequency bands for the equaliser.
     */
    public int getNumBands() {
        return MAXBANDS;
    }

    /**
     * @return True if the canvas is ready, false otherwise.
     */
    public boolean isReady() {
        return (audioData != null);
    }

    /**
     * Sets the stored audioData to the passed in array.
     * @param ad The audio data to pass in.
     */
    public void setAudioData(final int[] ad) {
        audioData = ad;
    }

    /**
     * Sets the current time of the audio file.
     * @param newTime The audio file's time.
     */
    public void setAudioTime(final long newTime) {
        audioTime = newTime;
    }

    /**
     * Tracks whether the canvas is in the middle of a draw.
     */
    private boolean dirtyCanvas = true;

    @Override
    public synchronized void repaint() {
        dirtyCanvas = true;
        super.repaint();
    }

    /**
     * @return True if the canvas is busy, false otherwise.
     */
    public synchronized boolean isDirty() {
        return dirtyCanvas;
    }

    /**
     * Refreshes the canvas with new equaliser data.
     * @param g The graphics to be painted.
     */
    @Override
    public synchronized void paint(final Graphics g) {
        // Check pre-conditions. Check isReady, return.
        if (!isReady()) {
            return;
        }
        int gHeight = this.getHeight();
        int gWidth = this.getWidth();
        if (gHeight > MINHEIGHT) {
            levels = new int[MAXBANDS];
            for (int i = 0; i < MAXBANDS; i++) {
                if (audioData.length > (((int) audioTime * MAXBANDS) + i)) {
                    levels[i] = audioData[((int) audioTime * MAXBANDS) + i];
                }
            }
            int maxHeight = gHeight - 1;
            int barWidth = gWidth / MAXBANDS;
            int segInterval = gHeight / MAXBARS;
            for (int i = 0; i < MAXBANDS; i++) {
                // calculate height of each set of boxes,
                // proportional to level
                float levPct = ((float) levels[i]) / BARDIV;
                // math is a little weird here; y axis has 0 at top,
                // but we have 0 at bottom of this graph
                int barHeight = (int) (levPct * maxHeight);
                // draw the bar as set of 0-MAXBARS rectangles
                int barCount = 0;
                for (int j = maxHeight; j > (maxHeight - barHeight);
                    j -= segInterval) {

                    int red = Math.min(CMAX, barCount * CMAX / MAXBARS);
                    int green = Math.min(CMAX, Math.max(0,
                       (MAXBARS - barCount) * CMAX / MAXBARS));

                    g.setColor(new Color(red, green, 0));

                    g.fillRect(i * barWidth,
                            j - segInterval,
                            barWidth - 1,
                            segInterval - 1);
                    barCount++;
                }
            }
            dirtyCanvas = false;
        }
    }
}

