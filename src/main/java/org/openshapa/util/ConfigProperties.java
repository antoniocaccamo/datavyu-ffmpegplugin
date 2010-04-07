package org.openshapa.util;

import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;

/**
 * The properties to use for configuration. Access to the configuration
 * properties should be done via org.openshapa.Configuration. This class is a
 * JavaBean so that it can be easily dumped to disk via the XMLEncoder.
 */
public final class ConfigProperties implements Serializable {

    /** The unique ID for this serial version. */
    private static final long serialVersionUID = 4L;

    /** The spreadsheet data font. */
    private Font spreadsheetDataFont;

    /** The spreadsheet label font. */
    private Font spreadsheetLabelFont;

    /** The spreadsheet background colour. */
    private Color spreadsheetBackgroundColour;

    /** The spreadsheet foreground colour. */
    private Color spreadsheetForegroundColour;

    /** The spreadsheet selection colour. */
    private Color spreadsheetSelectedColour;

    /** The spreadsheet selection colour. */
    private Color spreadsheetOverlapColour;

    /** The last location the user navigated too using a file chooser. */
    private String lastChooserDirectory;

    /** User has given permission to send usage logs to UserMetrix. */
    private Boolean canSendLogs;

    /** Fill colour of a carriage in the unselected/normal state */
    private Color mixerInterfaceNormalCarriageColour;

    /** Outline colour of a carriage in the unselected/normal state */
    private Color mixerInterfaceNormalOutlineColour;

    /** Fill colour of a carriage in the selected state */
    private Color mixerInterfaceSelectedCarriageColour;

    /** Outline colour of a carriage in the selected state */
    private Color mixerInterfaceSelectedOutlineColour;

    /**
     * Default constructor.
     */
    public ConfigProperties() {
    }

    /**
     * Sets the spreadsheet data font.
     * 
     * @param font
     *            The new font to use for spreadsheet data.
     */
    public void setSSDataFont(final Font font) {
        spreadsheetDataFont = font;
    }

    /**
     * @return The spreadsheet data font.
     */
    public Font getSSDataFont() {
        return spreadsheetDataFont;
    }

    /**
     * Sets the spreadsheet data font.
     *
     * @param font
     *            The new font to use for spreadsheet data.
     */
    public void setSSLabelFont(final Font font) {
        spreadsheetLabelFont = font;
    }

    /**
     * @return The spreadsheet data font.
     */
    public Font getSSLabelFont() {
        return spreadsheetLabelFont;
    }

    /**
     * Sets the spreadsheet background colour.
     * 
     * @param newColour
     *            The new colour to use for the spreadsheet background.
     */
    public void setSSBackgroundColour(final Color newColour) {
        spreadsheetBackgroundColour = newColour;
    }

    /**
     * @return The spreadsheet background colour.
     */
    public Color getSSBackgroundColour() {
        return spreadsheetBackgroundColour;
    }

    /**
     * Sets the spreadsheet foreground colour.
     * 
     * @param newColour
     *            The new colour to use for the spreadsheet foreground.
     */
    public void setSSForegroundColour(final Color newColour) {
        spreadsheetForegroundColour = newColour;
    }

    /**
     * @return The spreadsheet foreground colour.
     */
    public Color getSSForegroundColour() {
        return spreadsheetForegroundColour;
    }

    /**
     * Sets the spreadsheet selected colour.
     * 
     * @param newColour
     *            The new colour to use for spreadsheet selections.
     */
    public void setSSSelectedColour(final Color newColour) {
        spreadsheetSelectedColour = newColour;
    }

    /**
     * @return The spreadsheet selections colour.
     */
    public Color getSSSelectedColour() {
        return spreadsheetSelectedColour;
    }

    /**
     * Sets the spreadsheet overlap colour.
     * 
     * @param newColour
     *            The new colour to use for spreadsheet overlaps.
     */
    public void setSSOverlapColour(final Color newColour) {
        spreadsheetOverlapColour = newColour;
    }

    /**
     * @return The spreadsheet overlap colour.
     */
    public Color getSSOverlapColour() {
        return spreadsheetOverlapColour;
    }

    /**
     * Sets the last chooser directory that the user nominated.
     * 
     * @param location
     *            The last location the user nominated.
     */
    public void setLCDirectory(final String location) {
        lastChooserDirectory = location;
    }

    /**
     * @return The last chooser directory that the user nominated.
     */
    public String getLCDirectory() {
        return lastChooserDirectory;
    }

    /**
     * Set if we are able to transmit logs or not to UserMetrix.
     * 
     * @param send
     *            Can we send logs to UserMetrix?
     */
    public void setCanSendLogs(final Boolean send) {
        canSendLogs = send;
    }

    /**
     * @return True if we can send usage logs to UserMetrix.
     */
    public Boolean getCanSendLogs() {
        return canSendLogs;
    }

    /**
     * @return the mixerInterfaceNormalCarriageColour
     */
    public Color getMixerInterfaceNormalCarriageColour() {
        return mixerInterfaceNormalCarriageColour;
    }

    /**
     * @param newColour
     *            the mixerInterfaceNormalCarriageColour to set
     */
    public void setMixerInterfaceNormalCarriageColour(final Color newColour) {
        mixerInterfaceNormalCarriageColour = newColour;
    }

    /**
     * @return the mixerInterfaceNormalOutlineColour
     */
    public Color getMixerInterfaceNormalOutlineColour() {
        return mixerInterfaceNormalOutlineColour;
    }

    /**
     * @param newColour
     *            the mixerInterfaceNormalOutlineColour to set
     */
    public void setMixerInterfaceNormalOutlineColour(final Color newColour) {
        mixerInterfaceNormalOutlineColour = newColour;
    }

    /**
     * @return the mixerInterfaceSelectedCarriageColour
     */
    public Color getMixerInterfaceSelectedCarriageColour() {
        return mixerInterfaceSelectedCarriageColour;
    }

    /**
     * @param newColour
     *            the mixerInterfaceSelectedCarriageColour to set
     */
    public void setMixerInterfaceSelectedCarriageColour(final Color newColour) {
        mixerInterfaceSelectedCarriageColour = newColour;
    }

    /**
     * @return the mixerInterfaceSelectedOutlineColour
     */
    public Color getMixerInterfaceSelectedOutlineColour() {
        return mixerInterfaceSelectedOutlineColour;
    }

    /**
     * @param newColour
     *            the mixerInterfaceSelectedOutlineColour to set
     */
    public void setMixerInterfaceSelectedOutlineColour(final Color newColour) {
        mixerInterfaceSelectedOutlineColour = newColour;
    }
}
