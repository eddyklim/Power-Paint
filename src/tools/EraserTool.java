/*
 * TCSS 305 – Fall 2017
 * Assignment 5 - PowerPaint
 */

package tools;

/**
 * This class stores two points and creates an path based on mouse events.
 * 
 * @author eduardk
 * @version 15 Nov, 2017
 *
 */
public class EraserTool extends PencilTool {

    /** {@inheritDoc} */
    @Override
    public String getDescription() {
        return "Eraser";
    }
    
    /** {@inheritDoc} */
    @Override
    public char getChar() {
        return 'A';
    }
}
