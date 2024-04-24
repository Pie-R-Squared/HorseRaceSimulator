import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DarkThemeButton extends JButton {

    private Color defaultColor = new Color(30, 30, 30);
    private Color hoverColor = new Color(70, 70, 70);
    private Color clickColor = new Color(30, 30, 30);

    public DarkThemeButton(String text) {
        super(text);
        setForeground(Color.WHITE);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(defaultColor);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(clickColor);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setBackground(hoverColor);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        int width = getWidth();
        int height = getHeight();

        if (getModel().isPressed()) {
            g2d.setPaint(new GradientPaint(0, 0, clickColor, 0, height, Color.BLACK));
        } else if (getModel().isRollover()) {
            g2d.setPaint(new GradientPaint(0, 0, hoverColor, 0, height, Color.BLACK));
        } else {
            g2d.setPaint(new GradientPaint(0, 0, defaultColor, 0, height, Color.BLACK));
        }

        g2d.fillRect(0, 0, width, height);

        super.paintComponent(g);

        g2d.dispose();
    }
}