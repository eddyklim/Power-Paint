/*
 * TCSS 305 – Fall 2017
 * Assignment 5 - PowerPaint
 */

package tools;

import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

/**
 * This class stores two points and creates an rectangle based on mouse events.
 * 
 * @author eduardk
 * @version 15 Nov, 2017
 *
 */
public class RectangleTool extends AbstractTool {

    /** {@inheritDoc} */
    @Override
    public String getDescription() {
        return "Rectangle";
    }
    
    /** {@inheritDoc} */
    @Override
    public char getChar() {
        return 'R';
    }

    /** {@inheritDoc} */
    @Override
    public Shape startShape(final MouseEvent theEvent) {
        this.setMyStartPoint(theEvent.getPoint());
        this.setMyFinalPoint(this.getMyStartPoint());
        return new Rectangle2D.Double(this.getMyStartPoint().getX(),
                                      this.getMyStartPoint().getY(), 0, 0);
    }
    
    /** {@inheritDoc} */
    @Override
    public Shape dragShape(final MouseEvent theEvent) {
        this.setMyFinalPoint(theEvent.getPoint());
        // makes the the end point the start point
        return new Rectangle2D.Double(Math.min(this.getMyStartPoint().getX(),
                                               this.getMyFinalPoint().getX()),
                                      Math.min(this.getMyStartPoint().getY(),
                                               this.getMyFinalPoint().getY()),
                                      Math.abs(this.getMyFinalPoint().getX()
                                               - this.getMyStartPoint().getX()),
                                      Math.abs(this.getMyFinalPoint().getY()
                                               - this.getMyStartPoint().getY()));
    }
}
