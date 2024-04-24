import javax.swing.*;
import java.awt.*;
import javax.swing.text.*;

public class WinnerOverlay extends JDialog {
    private JTextPane textPane;

    public WinnerOverlay(JFrame parent, String winner) {
        super(parent, "Winner", ModalityType.MODELESS); // Set modality type to modeless
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(100, 120);
        setLocationRelativeTo(parent);
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
            setLocationRelativeTo(parent);
        } else if (winner.length() < 1) {
            winnerText = "Race terminated. All horses have fallen.";
            setSize(180, 150);
            setLocationRelativeTo(parent);
            flagLabel.setIcon(getScaledImage("horses/grave.png", 60, 60));
        } else {
            winnerText = "The winner is " + winner.replaceAll(",\\s*", "");
        }

        try {
            doc.insertString(doc.getLength(), winnerText, boldStyle);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        getContentPane().add(textPane, BorderLayout.CENTER);
        getContentPane().add(flagLabel, BorderLayout.SOUTH);
    }

    private ImageIcon getScaledImage(String filename, int width, int height) {
        ImageIcon horseIcon = new ImageIcon(filename);
        Image image = horseIcon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public void closeDialog() {
        setVisible(false);
        dispose();
    }
}
