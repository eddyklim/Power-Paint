/*
 * TCSS 305 – Fall 2017 
 * Assignment 5 - PowerPaint
 */

package support;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import javax.swing.Icon;

/**
 * This is custom icon class that implements the Icon interface. Standard GUI
 * icons are 14 by 14 pixels. Takes in a color and paints the icon.
 * 
 * @author eduardk
 * @version 15 Nov, 2017
 *
 */
public class ChooserIcon implements Icon {

    /** The standard icon width. */
    private static final int WIDTH = 14;
    /** The standard icon height. */
    private static final int HEIGHT = 14;
    /** The color to paint. */
    private final Color myColor;

    /**
     * Default constructor which takes in a color.
     * 
     * @param theColor The Color.
     */
    public ChooserIcon(final Color theColor) {
        myColor = theColor;
    }

    /** Will always return 14. */
    @Override
    public int getIconWidth() {
        return WIDTH;
    }
    
    /** Will always return 14. */
    @Override
    public int getIconHeight() {
        return HEIGHT;
    }

    /** Colors the entire icon with the specified color. */
    @Override
    public void paintIcon(final Component theComponent, final Graphics theGraphics,
                          final int theX, final int theY) {
        theGraphics.setColor(myColor);
        theGraphics.fillRect(theX, theY, WIDTH, HEIGHT);
    }
}
