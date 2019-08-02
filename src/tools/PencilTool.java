/*
 * TCSS 305 – Fall 2017
 * Assignment 5 - PowerPaint
 */

package tools;

import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

/**
 * This class stores two points and creates an path based on mouse events.
 * 
 * @author eduardk
 * @version 15 Nov, 2017
 *
 */
public class PencilTool extends AbstractTool {

    /** The path. */
    private Path2D myPath;

    /** {@inheritDoc} */
    @Override
    public String getDescription() {
        return "Pencil";
    }
    
    /** {@inheritDoc} */
    @Override
    public char getChar() {
        return 'P';
    }

    /** {@inheritDoc} */
    @Override
    public Shape startShape(final MouseEvent theEvent) {
        this.setMyStartPoint(theEvent.getPoint());
        this.setMyFinalPoint(this.getMyStartPoint());
        myPath = new Path2D.Double(new Line2D.Double(this.getMyStartPoint(),
                                                     this.getMyStartPoint()));
        return myPath;
    }

    /** {@inheritDoc} */
    @Override
    public Shape dragShape(final MouseEvent theEvent) {
        this.setMyStartPoint(getMyFinalPoint());
        this.setMyFinalPoint(theEvent.getPoint());
        myPath.append(new Line2D.Double(this.getMyStartPoint(), this.getMyFinalPoint()), true);
        return myPath;
    }
}
