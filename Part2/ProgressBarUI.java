import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;

import java.awt.*;

/**
 * Custom Progress bar UI for Windows and Mac compatibility
 * 
 * @author Aneeka
 * @version 1.0 (11th April 2025)
 */
public class ProgressBarUI extends BasicProgressBarUI {

    private Color backgroundColour = new Color(45,45,48);
    private Color foregroundColour = new Color(192, 192, 192);
    private Color textColour = new Color(100, 150, 180);

    /*
     * Background and foreground colour of progress bars
     */
    @Override
    protected Color getSelectionBackground() {
        return backgroundColour;
    }

    @Override
    protected Color getSelectionForeground() {
        return foregroundColour;
    }

    /*
     * Ensure the progress bar fills the container rectangle.
     * Set the colour of the text to be blue when unfilled
     * and white when filled
     */
    @Override
    protected void paintDeterminate(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g;
        Insets border = progressBar.getInsets();
        int width = progressBar.getWidth();
        int height = progressBar.getHeight();
        int barRectWidth = progressBar.getWidth() - (border.left + border.right);
        int barRectHeight = progressBar.getHeight() - (border.top + border.bottom);
        int fill = getAmountFull(border, barRectWidth, barRectHeight);

        g2.setColor(getSelectionBackground());
        g2.fillRect(border.left, border.top, barRectWidth, barRectHeight);
        g2.setColor(getSelectionForeground());
        g2.fillRect(border.left, border.top, fill, barRectHeight);

        if (progressBar.isStringPainted()) {
            String text = progressBar.getString();
            Font font = progressBar.getFont().deriveFont(Font.BOLD);
            g2.setFont(font);
            FontMetrics fm = g2.getFontMetrics();
            int stringWidth = fm.stringWidth(text);
            int stringHeight = fm.getAscent();
            int x = (width - stringWidth) / 2;
            int y = (height + stringHeight) / 2 - 2;

            Shape originalClip = g2.getClip();

            g2.setClip(fill, 0, width - fill, height);
            g2.setColor(textColour);
            g2.drawString(text, x, y);

            g2.setClip(0, 0, fill, height);
            g2.setColor(Color.WHITE);
            g2.drawString(text, x, y);

            g2.setClip(originalClip);
        }

        g2.dispose();
    }
}
