import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
/**
 * Class for the main GUI window of the Horse Racing Simulator
 * Contains update methods to update the display of other windows
 * Navigation buttons are included to access other windows
 * Contains the main method to execute the program on the EDT
 * 
 * @author Aneeka 
 * @version 1.0 (24th April 2024)
 */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
public class HorseRaceSimulatorGUI extends JFrame {
    public Race race;
    public RacePanel racePanel;
    public Statistics stats;
    public HorseBets bets;
<<<<<<< HEAD
=======
    private JTextArea raceTextArea;
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    private int raceLength = 10;
    private int tracks = 3;
    private List<String> selectedHorses = List.of("horses/black_horse.png", "horses/brown_horse.png", "horses/darkbrown_horse.png", "horses/lightbrown_horse.png", "horses/lighterbrown_horse.png", "horses/tan_horse.png");
    private Color chosenColour = Color.LIGHT_GRAY;
    private List<Horse> customHorses;
    private boolean raceStarted = false;
    private WinnerOverlay overlay;

<<<<<<< HEAD
    /**
     * Constructor for objects of class HorseRaceSimulatorGUI
     * Initialises buttons and event listeners for the main window
     * Establishes a user-friendly interface using a dark theme
     * 
     * @param race the race class object which carries out the
     * main logic. The GUI updates other windows based on race
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    public HorseRaceSimulatorGUI(Race race) {

        UIManager.put("OptionPane.background", new Color(30, 30, 30));
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
        UIManager.put("TextField.background", new Color(30, 30, 30));
        UIManager.put("TextField.foreground", Color.WHITE);
        UIManager.put("Panel.background", new Color(30, 30, 30));

        this.race = race;
        setTitle("Horse Racing Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(460, 300);
        setLayout(new FlowLayout());
        setLocation(getX() + 10, getY() + 10);
        getContentPane().setBackground(new Color(30,30,30));

<<<<<<< HEAD
=======
        raceTextArea = new JTextArea(2, 30);
        raceTextArea.setEditable(false);
        raceTextArea.setBackground(new Color(30,30,30));
        raceTextArea.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(raceTextArea);
        scrollPane.setBorder(null);
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
        racePanel = new RacePanel(tracks, raceLength, selectedHorses, chosenColour, customHorses);
        JPanel buttonsPanel = new JPanel();

        getContentPane().add(racePanel, BorderLayout.CENTER);
<<<<<<< HEAD
=======
        getContentPane().add(scrollPane, BorderLayout.CENTER);
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c

        JButton startButton = new DarkThemeButton("Start Race");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
<<<<<<< HEAD
=======
                clearText();
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
                race.startRaceGUI(customHorses);
                raceStarted = true;
                closeWinnerDialog();
            }
        });

        JButton customiseButton = new DarkThemeButton("Customise");
        customiseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!raceStarted) {
                    Customisations customisations = new Customisations(HorseRaceSimulatorGUI.this);
                    customisations.reloadCustomisations(raceLength, tracks, chosenColour);
                    customisations.setVisible(true);
                }
            }
        });

        JButton statsButton = new DarkThemeButton("Statistics");
        statsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stats = race.stats;
                if (stats == null)
                    stats = new Statistics(HorseRaceSimulatorGUI.this);
                stats.setVisible(true);
                stats.displayStats();
            }
        });

        JButton betButton = new DarkThemeButton("Betting");
        betButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bets = new HorseBets(new Account("User", 1000.0), customHorses != null && !customHorses.isEmpty() ? customHorses : race.getHorses(), tracks);
                bets.setVisible(true);
            }
        });

        buttonsPanel.add(startButton);
        buttonsPanel.add(customiseButton);
        buttonsPanel.add(statsButton);
        buttonsPanel.add(betButton);
        getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
    }

<<<<<<< HEAD
    /**
     * Method to display the winner(s) in a custom dialog box
     * 
     * @param winner the name of the winning horse(s) separated by ","
     */
=======
    public void clearText() {
        raceTextArea.setText("");
    }

>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    public void displayWinner(String winner) {
        closeWinnerDialog();
        overlay = new WinnerOverlay(this, winner);
        overlay.setVisible(true);
    }

<<<<<<< HEAD
    /**
     * Removes the winner dialog box
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    private void closeWinnerDialog() {
        if (overlay != null) {
            overlay.closeDialog();
        }
    }

<<<<<<< HEAD
    /**
     * Updates the racepanel and tracks the race finished status
     * Sets the raceStarted flag to false when the race finishes
     * 
     * @param horses arraylist of horse objects from the Race class
     * @param finished flag which tracks if the race is finished
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    public void updateRace(ArrayList<Horse> horses, boolean finished) {
        racePanel.updateRace(horses);
        if (finished) {
            raceStarted = false;
        }
    }

<<<<<<< HEAD
    /**
     * Updates the GUI display based on values passed from Customisations
     * Re-initialises attributes and updates the race panel
     * 
     * @param raceLength length of the track (2-30)
     * @param tracks number of tracks (2-14)
     * @param selectedHorses list of image paths of selected horses
     * @param chosenColour the chosen background colour
     * @param customHorses list of custom horse objects (name, confidence)
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    public void updateRaceGUI(int raceLength, int tracks, List<String> selectedHorses, Color chosenColour, List<Horse> customHorses) {
        this.raceLength = raceLength;
        this.tracks = tracks;
        this.selectedHorses = selectedHorses;
        this.chosenColour = chosenColour;
        this.customHorses = customHorses;
        race.setRaceLength(raceLength);
        race.setTracks(tracks);
        updateRacePanel();
        closeWinnerDialog();
    }

<<<<<<< HEAD
    /**
     * Updates the race panel display, passing in customisations
     * Re-sizes the GUI window to accommodate the race panel
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    private void updateRacePanel() {
        getContentPane().remove(racePanel);
        racePanel = new RacePanel(tracks, raceLength, selectedHorses, chosenColour, customHorses);
        getContentPane().add(racePanel, BorderLayout.CENTER);
        revalidate();
        repaint();

        int racePanelWidth = racePanel.getPreferredSize().width + 20;
        int racePanelHeight = racePanel.getPreferredSize().height + 150;
        int maxGUIWidth = 1200;
        int maxGUIHeight = 850;
        int currentGUIWidth = getWidth();
        int currentGUIHeight = getHeight();

        if (racePanelWidth > currentGUIWidth && racePanelWidth <= maxGUIWidth) {
            int newGUIWidth = Math.min(racePanelWidth, maxGUIWidth);
            setSize(newGUIWidth, getHeight());
        } else if (racePanelWidth > 460) {
            setSize(racePanelWidth, getHeight());
        } else {
            setSize(460, getHeight());
        }
        if (racePanelHeight > currentGUIHeight && racePanelHeight <= maxGUIHeight) {
            int newGUIHeight = Math.min(racePanelHeight, maxGUIHeight);
            setSize(getWidth(), newGUIHeight);
        } else if (racePanelHeight > 300) {
            setSize(getWidth(), racePanelHeight);
        } else {
            setSize(getWidth(), 300);
        }
    }

<<<<<<< HEAD
    /**
     * Main method which starts the application
     * Invoke Later used to ensure events execute on EDT
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Race race = new Race(10);

            HorseRaceSimulatorGUI raceGUI = new HorseRaceSimulatorGUI(race);
            race.setGUI(raceGUI);

            race.addHorse(new Horse('\u2658', "Jack", 0.4), 1);
            race.addHorse(new Horse('\u265E', "Joey", 0.5), 2);
            race.addHorse(new Horse('\u265A', "Max", 0.7), 3);

            raceGUI.setVisible(true);
        });
    }
}