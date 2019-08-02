/*
 * TCSS 305 – Fall 2017
 * Assignment 5 - PowerPaint
 */

package tools;

import java.awt.Point;
import java.awt.Shape;
import java.awt.event.MouseEvent;

/**
 * This is an abstract class that stores two points and creates a shape based on
 * mouse events.
 * 
 * @author eduardk
 * @version 15 Nov, 2017
 *
 */
public abstract class AbstractTool {

    /** The start point. */
    private Point myStartPoint;
    /** The end point. */
    private Point myFinalPoint;

    /**
     * Method for getting the name of the tool.
     * 
     * @return the tool name.
     */
    public abstract String getDescription();

    /**
     * Method for getting the char of the tool.
     * 
     * @return the tool name.
     */
    public abstract char getChar();
    
    /**
     * Returns the shape with initial coordinates.
     * 
     * @param theEvent The mouse press.
     * @return The shape.
     */
    public abstract Shape startShape(MouseEvent theEvent);

    /**
     * Returns the shape with new coordinates.
     * 
     * @param theEvent The mouse dragged.
     * @return The shape.
     */
    public abstract Shape dragShape(MouseEvent theEvent);

    /**
     * Getter method for the starting point.
     * 
     * @return The start point.
     */
    public Point getMyStartPoint() {
        return myStartPoint;
    }
    
    /**
     * Setter method for the starting point.
     * 
     * @param thePoint A point.
     */
    public void setMyStartPoint(final Point thePoint) {
        this.myStartPoint = thePoint;
    }

    /**
     * Getter method for the end point.
     * 
     * @return The final point.
     */
    public Point getMyFinalPoint() {
        return myFinalPoint;
    }

    /**
     * Setter method for the end point.
     * 
     * @param thePoint A point.
     */
    public void setMyFinalPoint(final Point thePoint) {
        this.myFinalPoint = thePoint;
    }
}
