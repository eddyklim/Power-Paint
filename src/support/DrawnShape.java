/*
 * TCSS 305 – Fall 2017
 * Assignment 5 - PowerPaint
 */

package support;

import java.awt.Color;
import java.awt.Shape;

/**
 * This is a custom class that stores a specific shape including its color and
 * stroke size.
 * 
 * @author eduardk
 * @version 15 Nov, 2017
 *
 */
public class DrawnShape {

    /** The Stroke size. */
    private final float myStrokeSize;
    /** The color. */
    private final Color myColor;
    /** The shape. */
    private final Shape myShape;

    /**
     * The constructor that takes in a stroke size, color, and shape.
     * 
     * @param theStrokeSize the stroke size.
     * @param theColor the color.
     * @param theShape the shape.
     */
    public DrawnShape(final float theStrokeSize, final Color theColor, final Shape theShape) {
        myStrokeSize = theStrokeSize;
        myColor = theColor;
        myShape = theShape;
    }

    /**
     * Getter method for stroke size.
     * 
     * @return the stroke size.
     */
    public final float getStrokeSize() {
        return myStrokeSize;
    }

    /**
     * Getter method for the color.
     * 
     * @return the color.
     */
    public final Color getColor() {
        return myColor;
    }

    /**
     * Getter method for the shape.
     * 
     * @return the shape.
     */
    public final Shape getShape() {
        return myShape;
    }
}
