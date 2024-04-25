import javax.swing.*;
import java.awt.*;
import javax.swing.text.*;

/**
 * Winning horses display which is a custom JDialog window
 * 
 * @author Aneeka 
 * @version 1.0 (24th April 2024)
 */
public class WinnerOverlay extends JDialog {

    private JTextPane textPane;

    /**
     * Constructor for overlay window which displays
     * a textpane for the winning horses along with a
     * flag icon
     * 
     * @param raceGUI the main GUI window
     * @param winner the winning horse(s)
     */
    public WinnerOverlay(JFrame raceGUI, String winner) {
        super(raceGUI, "Results", ModalityType.MODELESS);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(150, 120);
        setLocationRelativeTo(raceGUI);
        setLayout(new BorderLayout());

        JLabel flagLabel = new JLabel(getScaledImage("horses/finish_flag.png", 60, 40));

        textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setBackground(new Color(30, 30, 30));
        textPane.setForeground(Color.WHITE);

        StyledDocument doc = textPane.getStyledDocument();
        Style defaultStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
        Style boldStyle = doc.addStyle("Bold", defaultStyle);
        StyleConstants.setBold(boldStyle, true);

        String[] winners = winner.trim().split(", ");
        String winnerText = "";

        if (winners.length > 1) {
            winnerText = "It's a draw between ";
            for (int i = 0; i < winners.length - 1; i++) {
                winnerText += winners[i] + " and ";
            }
            winnerText += winners[winners.length - 1].replaceAll(",\\s*", "");
            setSize(180, 130);
        } else if (winner.length() < 1) {
            winnerText = "Race terminated. All horses have fallen.";
            setSize(180, 150);
            flagLabel.setIcon(getScaledImage("horses/grave.png", 60, 60));
        } else {
            winnerText = "The winner is " + winner.replaceAll(",\\s*", "");
        }
        setLocationRelativeTo(raceGUI);

        try {
            doc.insertString(doc.getLength(), winnerText, boldStyle);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        getContentPane().add(textPane, BorderLayout.CENTER);
        getContentPane().add(flagLabel, BorderLayout.SOUTH);
    }

    /**
     * Returns a scaled image icon that can be added to a JLabel
     * 
     * @param filename path to the image file
     * @param width width to scale the image
     * @param height height to scale the image
     * @return ImageIcon the scaled image icon
     */
    private ImageIcon getScaledImage(String filename, int width, int height) {
        ImageIcon horseIcon = new ImageIcon(filename);
        Image image = horseIcon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    /**
     * Closes the dialog window and resets visibility
     */
    public void closeDialog() {
        setVisible(false);
        dispose();
    }
}
