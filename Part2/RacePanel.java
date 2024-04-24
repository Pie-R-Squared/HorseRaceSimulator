import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
/**
 * RacePanel class which is a custom JPanel for displaying
 * the horse race using a user-friendly interface. The track
 * is drawn out either using the default set of horses or
 * custom horses selected by the user. During the race, the
 * horse positions are updated on timer ticks
 * 
 * @author Aneeka
 * @version 1.0 (24th April 2024)
 */
public class RacePanel extends JPanel {
    private List<JLabel> horseLabels;
    private List<String> selectedHorses;
    private Color chosenColour;

    /**
     * Constructor for objects of class RacePanel. The
     * track is drawn out using the specified number of
     * tracks and racelength. Custom horses are used
     * or a default set used if none are provided. The
     * colour of the track is set using the chosen colour
     * 
     * @param tracks number of tracks/lanes
     * @param raceLength length of the race track
     * @param selectedHorses list of chosen horse image filenames
     * @param chosenColour the selected colour of the track
     * @param customHorses list of custom horse objects
     */
=======
public class RacePanel extends JPanel{
    private List<JLabel> horseLabels;
    private List<String> selectedHorses;
    Color chosenColour;

>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
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

<<<<<<< HEAD
    /**
     * Scales the image to a size of 40x40 pixels to be
     * added to a JLabel. The scaled image is returned
     * 
     * @param filename the file path of the image to be scaled
     * @return ImageIcon the scaled image icon
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    private ImageIcon getScaledImage(String filename) {
        ImageIcon horseIcon = new ImageIcon(filename);
        Image image = horseIcon.getImage();
        Image scaledImage = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

<<<<<<< HEAD
    /**
     * Updates the race panel to show the current horse
     * positions. If a horse has fallen, the horse icon
     * is replaced with a grave icon. Otherwise, the horse
     * icon position is updated based on distance travelled
     * 
     * @param horses list of horse objects to update
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
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