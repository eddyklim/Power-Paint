/*
 * TCSS 305 – Fall 2017
 * Assignment 5 - PowerPaint
 */

package tools;

import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;

/**
 * This class stores two points and creates a line based on mouse events.
 * 
 * @author eduardk
 * @version 15 Nov, 2017
 *
 */
public class LineTool extends AbstractTool {
    
    /** {@inheritDoc} */
    @Override
    public String getDescription() {
        return "Line";
    }
    
    /** {@inheritDoc} */
    @Override
    public char getChar() {
        return 'L';
    }

    /** {@inheritDoc} */
    @Override
    public Shape startShape(final MouseEvent theEvent) {
        this.setMyStartPoint(theEvent.getPoint());
        this.setMyFinalPoint(this.getMyStartPoint());
        return new Line2D.Double(this.getMyStartPoint(), this.getMyFinalPoint()); 
    }

    /** {@inheritDoc} */
    @Override
    public Shape dragShape(final MouseEvent theEvent) {
        this.setMyFinalPoint(theEvent.getPoint());
        return new Line2D.Double(this.getMyStartPoint(), this.getMyFinalPoint()); 
    }
}
