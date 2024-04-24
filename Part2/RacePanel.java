import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RacePanel extends JPanel{
    private List<JLabel> horseLabels;
    private List<String> selectedHorses;
    Color chosenColour;

    public RacePanel(int tracks, int raceLength, List<String>selectedHorses, Color chosenColour, List<Horse> customHorses) {
        setLayout(new GridLayout(tracks, raceLength));
        horseLabels = new ArrayList<>();
        this.selectedHorses = selectedHorses;
        this.chosenColour = chosenColour;
        if (customHorses == null || customHorses.size() == 0) {
            customHorses = new ArrayList<>();
            customHorses.add(new Horse('\u2658', "Jack", 0.4));
            customHorses.add(new Horse('\u265E', "Joey", 0.5));
            customHorses.add(new Horse('\u265A', "Max", 0.7));
        }

        if (customHorses.size() < tracks) {
            for (int i = customHorses.size(); i < tracks; i++) {
                customHorses.add(new Horse('\u2658', "Unnamed" + (i), 0.5));
            }
        }

        for (int i = 1; i <= tracks; i++) {
            JPanel trackPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            trackPanel.setPreferredSize(new Dimension(40 + raceLength * 40, 40));
            JLabel horseLabel = new JLabel(getScaledImage(selectedHorses.get((i-1)%6)));
            horseLabels.add(horseLabel);
            trackPanel.add(horseLabel);
            JLabel ghostHorseText = new JLabel(customHorses.get(i-1).getName());
            ghostHorseText.setForeground(Color.DARK_GRAY);
            ghostHorseText.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
            trackPanel.add(ghostHorseText);
            trackPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            trackPanel.setBackground(this.chosenColour);
            add(trackPanel);
        }
    }

    private ImageIcon getScaledImage(String filename) {
        ImageIcon horseIcon = new ImageIcon(filename);
        Image image = horseIcon.getImage();
        Image scaledImage = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public void updateRace(ArrayList<Horse> horses) {
        for (int i = 0; i < horses.size(); i++) {
            if (horses.get(i).hasFallen()) {
                horseLabels.get(i).setIcon(getScaledImage("horses/grave.png"));
            } else {
                horseLabels.get(i).setIcon(getScaledImage(selectedHorses.get(i%6)));
            }
            int maxDistance = (getWidth() - 40) / 40;
            int horsePosition = Math.min(horses.get(i).getDistanceTravelled(), maxDistance);
    
            horseLabels.get(i).setLocation(horsePosition * 40, horseLabels.get(i).getY());
        }
    }
}