import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

<<<<<<< HEAD
/**
 * Custom button class for a dark themed button. This
 * has a dark gradient background with modified hover
 * and click effects
 * 
 * @author Aneeka
 * @version 1.0 (24th April 2024)
 */
public class DarkThemeButton extends JButton {

    private Color defaultColour = new Color(30, 30, 30);
    private Color hoverColour = new Color(70, 70, 70);
    private Color clickColour = new Color(30, 30, 30);

    /**
     * Constructor for objects of class DarkThemeButton.
     * This overrides the mouse events to change default
     * colours of hover and click
     * 
     * @param text the text to be displayed on the button
     */
=======
public class DarkThemeButton extends JButton {

    private Color defaultColor = new Color(30, 30, 30);
    private Color hoverColor = new Color(70, 70, 70);
    private Color clickColor = new Color(30, 30, 30);

>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    public DarkThemeButton(String text) {
        super(text);
        setForeground(Color.WHITE);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
<<<<<<< HEAD
                setBackground(hoverColour);
=======
                setBackground(hoverColor);
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
            }

            @Override
            public void mouseExited(MouseEvent e) {
<<<<<<< HEAD
                setBackground(defaultColour);
=======
                setBackground(defaultColor);
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
            }

            @Override
            public void mousePressed(MouseEvent e) {
<<<<<<< HEAD
                setBackground(clickColour);
=======
                setBackground(clickColor);
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
            }

            @Override
            public void mouseReleased(MouseEvent e) {
<<<<<<< HEAD
                setBackground(hoverColour);
=======
                setBackground(hoverColor);
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
            }
        });
    }

<<<<<<< HEAD
    /**
     * Overriden paint component which paints a gradient onto
     * the button. This is required for using gradients
     * instead of solid colours. The gradient changes slightly
     * upon pressing the button and hovering over it
     * 
     * @param g the graphics object to paint the button
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        int width = getWidth();
        int height = getHeight();

        if (getModel().isPressed()) {
<<<<<<< HEAD
            g2d.setPaint(new GradientPaint(0, 0, clickColour, 0, height, Color.BLACK));
        } else if (getModel().isRollover()) {
            g2d.setPaint(new GradientPaint(0, 0, hoverColour, 0, height, Color.BLACK));
        } else {
            g2d.setPaint(new GradientPaint(0, 0, defaultColour, 0, height, Color.BLACK));
=======
            g2d.setPaint(new GradientPaint(0, 0, clickColor, 0, height, Color.BLACK));
        } else if (getModel().isRollover()) {
            g2d.setPaint(new GradientPaint(0, 0, hoverColor, 0, height, Color.BLACK));
        } else {
            g2d.setPaint(new GradientPaint(0, 0, defaultColor, 0, height, Color.BLACK));
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
        }

        g2d.fillRect(0, 0, width, height);

        super.paintComponent(g);

        g2d.dispose();
    }
}