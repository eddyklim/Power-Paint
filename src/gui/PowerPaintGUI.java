/*
 * TCSS 305 – Fall 2017
 * Assignment 5 - PowerPaint
 */

package gui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon; 
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.MouseInputAdapter;
import support.ChooserIcon;
import support.DrawnShape;
import tools.AbstractTool;
import tools.EllipseTool;
import tools.EraserTool;
import tools.LineTool;
import tools.PencilTool;
import tools.RectangleTool;

/**
 * This class holds all the GUI components of PowerPaint, a main is required to
 * run this GUI. This program allows the user to paint on a white canvas using
 * different shapes. Shapes include line, rectangle, ellipse, pencil, and an
 * eraser. Options menu allows the user to pick the primary and secondary color,
 * which corresponds to left-click and right-click and also an option to clear
 * the canvas. Tool menu allows the user to pick the shape they wish to draw
 * with which is linked with the tool bar down below. Help menu gives more
 * information about the program.
 * 
 * @author eduardk
 * @version 15 Nov, 2017
 *
 */
public class PowerPaintGUI extends JPanel {
    /** The serialVersionUID. */
    private static final long serialVersionUID = -3298154175892621837L;
    /** The panel width. */
    private static final int DRAWING_PANEL_WIDTH = 500;
    /** The panel height. */
    private static final int DRAWING_PANEL_HEIGHT = 300;
    /** The stroke size. */
    private static final int DEFAULT_STROKE_SIZE = 10;
    /** The major tick spacing. */
    private static final int SLIDER_LARGE_TICK = 5;
    /** The max slider value. */
    private static final int SLIDER_MAX_VALUE = 20;
    /** The hidden shape coordinate. */
    private static final int HIDDEN_SHAPE_COORDINATE = -500;
    /** The width and height of any small icon. */
    private static final int ICON_SIZE = 14;
    /** The initial primary color. */
    private static final Color UW_PURPLE = new Color(51, 0, 111);
    /** The initial secondary color. */
    private static final Color UW_GOLD = new Color(232, 211, 162);
    /** The program's logo. */
    private final ImageIcon myLogo;
    /** The main window. */
    private final JFrame myWindow;
    /** The list of tools. */
    private final List<AbstractTool> myAbList;
    /** The list of drawn shapes. */
    private final List<DrawnShape> myList;
    /** A list of color actions. */
    private final List<ToolAction> myToolActions;
    /** The stroke size. */
    private float myStrokeSize;
    /** The main color tab. */
    private JMenuItem myPrimaryColorItem;
    /** The second color tab. */
    private JMenuItem mySecondaryColorItem;
    /** The clear tab. */
    private JMenuItem myClearItem;
    /** The current color. */
    private Color myColor;
    /** The first color. */
    private Color myPrimaryColor;
    /** The second color. */
    private Color mySecondaryColor;
    /** Icon showing current color. */
    private ChooserIcon myPrimaryIcon;
    /** Icon showing secondary color. */
    private ChooserIcon mySecondaryIcon;
    /** The tool in use. */
    private AbstractTool myTool;
    /** The shape being made. */
    private Shape myCurrentShape;

    /** Default constructor that initializes some fields. */
    PowerPaintGUI() {
        super();
        myWindow = new JFrame("Assignment 5");
        myAbList = new ArrayList<AbstractTool>();
        myToolActions = new ArrayList<ToolAction>();
        myList = new ArrayList<DrawnShape>();
        myLogo = new ImageIcon("./images/logo.gif");
    }

    /** Creates and shows the GUI. */
    public void start() {
        myWindow.setContentPane(this);
        setUpComponents();
        myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myWindow.setVisible(true);
        myWindow.pack();
        requestFocus();
    }

    /**
     * Builds the different parts of the GUI such as the menu bar and tool bar and
     * other defaults. Actions and listeners are also added here.
     */
    public void setUpComponents() {
        setLayout(new BorderLayout());
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        setPreferredSize(new Dimension(DRAWING_PANEL_WIDTH, DRAWING_PANEL_HEIGHT));
        setBackground(Color.WHITE);
        addMouseListener(new MouseListener());
        addMouseMotionListener(new MouseListener());
        setupActions();
        myWindow.setJMenuBar(createMenuBar());
        myWindow.setIconImage(myLogo.getImage().getScaledInstance
                              (ICON_SIZE , ICON_SIZE , java.awt.Image.SCALE_SMOOTH));
        myTool = new LineTool();
        myWindow.add(setUpToolBar(), BorderLayout.SOUTH);
    }

    /**
     * Sets up the menu bar by adding all menus. Options menu contains two items
     * "Primary Color" and "Secondary Color" which have a corresponding color
     * preview in the icon. Options menu also contains a clear button which removes
     * all drawn shapes.
     * 
     * @return menuBar the built menu bar.
     */
    private JMenuBar createMenuBar() {
        final JMenuBar menuBar = new JMenuBar();
        final JMenu optionMenu = new JMenu("Options");
        add(optionMenu);
        optionMenu.setMnemonic('O');
        optionMenu.add(setUpSliderMenu());

        // TODO icon as current color
        myPrimaryIcon = new ChooserIcon(UW_PURPLE);
        mySecondaryIcon = new ChooserIcon(UW_GOLD);
        myPrimaryColor = UW_PURPLE;
        mySecondaryColor = UW_GOLD;
        myColor = myPrimaryColor;
        optionMenu.addSeparator();
        myPrimaryColorItem = new JMenuItem("Primary Color...",
                                           myPrimaryIcon);
        optionMenu.add(myPrimaryColorItem);
        myPrimaryColorItem.setMnemonic('P');
        myPrimaryColorItem.addActionListener((theEvent) -> {
            final Color result =
                            JColorChooser.showDialog(null, "Select Primary", myPrimaryColor);
            myPrimaryColor = result;
            myColor = result;
            myPrimaryIcon = new ChooserIcon(result);
            // TODO
            myPrimaryColorItem.setIcon(myPrimaryIcon);

        });

        mySecondaryColorItem = new JMenuItem("Secondary Color...",
                                             mySecondaryIcon);
        optionMenu.add(mySecondaryColorItem);
        mySecondaryColorItem.setMnemonic('S');
        mySecondaryColorItem.addActionListener((theEvent) -> {
            final Color result = JColorChooser.showDialog(null, "Select Secondary",
                                                          mySecondaryColor);
            mySecondaryColor = result;
            mySecondaryIcon = new ChooserIcon(result);
            // TODO
            mySecondaryColorItem.setIcon(mySecondaryIcon);
        });

        optionMenu.addSeparator();
        myClearItem = new JMenuItem("Clear");
        optionMenu.add(myClearItem);
        myClearItem.setMnemonic('C');
        myClearItem.setEnabled(false);
        myClearItem.addActionListener((theEvent) -> {
            myList.clear();
            myClearItem.setEnabled(false);
            myCurrentShape = new Line2D.Double(HIDDEN_SHAPE_COORDINATE,
                                               HIDDEN_SHAPE_COORDINATE,
                                               HIDDEN_SHAPE_COORDINATE,
                                               HIDDEN_SHAPE_COORDINATE);
            repaint();
        });

        menuBar.add(optionMenu);
        menuBar.add(setUpToolMenu());
        menuBar.add(setUpHelpMenu());

        return menuBar;
    }

    /**
     * Sets up the slider menu by adding a slider item with specific values. Slider
     * changes the stroke size.
     * 
     * @return sliderSub the built slider menu.
     */
    private JMenu setUpSliderMenu() {
        final JMenu sliderSub = new JMenu("Thickness");
        sliderSub.setMnemonic('T');
        final JSlider strokeSlider = new JSlider(JSlider.HORIZONTAL, 0, SLIDER_MAX_VALUE, 1);
        sliderSub.add(strokeSlider);
        strokeSlider.setMajorTickSpacing(SLIDER_LARGE_TICK);
        strokeSlider.setMinorTickSpacing(1);
        strokeSlider.setPaintLabels(true);
        strokeSlider.setPaintTicks(true);
        strokeSlider.setValue((int) DEFAULT_STROKE_SIZE);
        strokeSlider.addChangeListener((theEvent) -> {
            myStrokeSize = strokeSlider.getValue();
        });
        myStrokeSize = DEFAULT_STROKE_SIZE;
        return sliderSub;
    }

    /**
     * Sets up the tool menu by adding buttons and adds said buttons to a button
     * group.
     * 
     * @return toolMenu the built tool menu.
     */
    private JMenu setUpToolMenu() {
        final JMenu toolMenu = new JMenu("Tools");
        toolMenu.setMnemonic(KeyEvent.VK_T);
        final ButtonGroup bg = new ButtonGroup();
        for (final ToolAction a : myToolActions) {
            final JRadioButtonMenuItem b = new JRadioButtonMenuItem(a);
            b.setIcon(null);
            bg.add(b);
            toolMenu.add(b);
            
        }
        return toolMenu;
    }

    /**
     * Sets up the help menu by adding an "About" item and a pop up dialog.
     * 
     * @return helpMenu the built help menu.
     */
    private JMenu setUpHelpMenu() {
        final JMenu helpMenu = new JMenu("Help");
        add(helpMenu);
        helpMenu.setMnemonic(KeyEvent.VK_H);
        final JMenuItem aboutItem = new JMenuItem("About...");
        helpMenu.add(aboutItem);
        aboutItem.setMnemonic('A');
        aboutItem.addActionListener((theEvent) -> {
            JOptionPane.showMessageDialog(null,
                                         "Eduard Klimenko\nAutumn 2017\nTCSS 305 Assignment 5",
                                          "About", JOptionPane.INFORMATION_MESSAGE,
                                          myLogo);
        });

        return helpMenu;
    }

    /**
     * Sets up the tool bar by adding buttons and adds said buttons to a button
     * group.
     * 
     * @return toolBar the built tool bar.
     */
    private JToolBar setUpToolBar() {
        final JToolBar toolBar = new JToolBar();
        final ButtonGroup bg = new ButtonGroup();
        Border compound;
        final Border grayline = BorderFactory.createLineBorder(new Color(153, 153, 153));
        final Border whiteline = BorderFactory.createLineBorder(Color.WHITE);
        final Border emptyb = BorderFactory.createEmptyBorder(4, 4, 4, 4);
        compound = BorderFactory.createCompoundBorder(
                                                      grayline, emptyb);
        compound = BorderFactory.createCompoundBorder(
                                                      whiteline, compound);
        for (final ToolAction a : myToolActions) {
            final JToggleButton b = new JToggleButton(a);
            if (a.getDescription().equals(new LineTool().getDescription())) {
                b.setSelected(true);
            }
            b.setBorder(compound);
            bg.add(b);
            toolBar.add(b);
        }
        return toolBar;
    }

    /** Sets up all the Actions. */
    private void setupActions() {
        myAbList.add(new PencilTool());
        myAbList.add(new LineTool());
        myAbList.add(new RectangleTool());
        myAbList.add(new EllipseTool());
        myAbList.add(new EraserTool());

        for (final AbstractTool t : myAbList) {
            myToolActions.add(new ToolAction(t.getDescription(),
                                             new ImageIcon("./images/"
                                             + t.getDescription().toLowerCase(Locale.US)
                                             + ".gif"), t, t.getChar()));
        }
    }
    
    /** Paints the panel by drawing all previous shapes and the current shape. */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        // for better graphics display
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);

        for (int i = 0; i < myList.size(); i++) {
            g2d.setStroke(new BasicStroke(myList.get(i).getStrokeSize()));
            g2d.setColor(myList.get(i).getColor());
            g2d.draw(myList.get(i).getShape());
        }
        g2d.setStroke(new BasicStroke(myStrokeSize));
        g2d.setColor(myColor);
        if (myCurrentShape != null && myStrokeSize != 0) {
            g2d.draw(myCurrentShape);
        }
    }
    
    /**
     * This is an inner class that holds the action for any given tool.
     * 
     * @author eduardk
     * @version 15 Nov, 2017
     *
     */
    private class MouseListener extends MouseInputAdapter {
        /**
         * Generates a shape. If pressed with right button, color is set to secondary.
         * If the eraser is selected, the color is set to white.
         */
        @Override
        public void mousePressed(final MouseEvent theEvent) {
            myColor = myPrimaryColor;
            if (SwingUtilities.isRightMouseButton(theEvent)) {
                myColor = mySecondaryColor;
            }
            if (myTool.getDescription().equals(new EraserTool().getDescription())) {
                myColor = Color.WHITE;
            }
            myCurrentShape = myTool.startShape(theEvent);
            repaint();
        }

        /**
         * Generates a preview of the shape.
         */
        @Override
        public void mouseDragged(final MouseEvent theEvent) {
            myCurrentShape = myTool.dragShape(theEvent);
            repaint();
        }

        /**
         * Stores the shape into the collection when button is released. Stroke size
         * must not be 0.
         */
        @Override
        public void mouseReleased(final MouseEvent theEvent) {
            if (myStrokeSize != 0) {
                myList.add(new DrawnShape(myStrokeSize, myColor, myCurrentShape));
                myClearItem.setEnabled(true);
            }
        }
    }

    /**
     * This is an inner class that holds the action for any given tool.
     * 
     * @author eduardk
     * @version 15 Nov, 2017
     *
     */
    private class ToolAction extends AbstractAction {

        /** A generated serialization ID. */
        private static final long serialVersionUID = -4619403424184757232L;
        /** The tool to use. */
        private final AbstractTool myActionTool;

        /**
         * Constructs an action with the specified name and icon to set the tool.
         * 
         * @param theName The name.
         * @param theIcon The icon.
         * @param theTool The tool.
         * @param theChar The char.
         */
        ToolAction(final String theName, final Icon theIcon, final AbstractTool theTool,
                   final char theChar) {
            super(theName);
            putValue(Action.SMALL_ICON, theIcon);
            putValue(Action.MNEMONIC_KEY, KeyEvent.getExtendedKeyCodeForChar(theChar));
            putValue(Action.SELECTED_KEY, true);
            myActionTool = theTool;
        }

        /** Sets the action tool to the specified tool. */
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myTool = myActionTool;
        }
        
        /**
         * Gets the description.
         * 
         * @return The description.
         */
        public String getDescription() {
            return myActionTool.getDescription();
        }
    }
}
