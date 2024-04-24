import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class HorseRaceSimulatorGUI extends JFrame {
    public Race race;
    public RacePanel racePanel;
    public Statistics stats;
    public HorseBets bets;
    private JTextArea raceTextArea;
    private int raceLength = 10;
    private int tracks = 3;
    private List<String> selectedHorses = List.of("horses/black_horse.png", "horses/brown_horse.png", "horses/darkbrown_horse.png", "horses/lightbrown_horse.png", "horses/lighterbrown_horse.png", "horses/tan_horse.png");
    private Color chosenColour = Color.LIGHT_GRAY;
    private List<Horse> customHorses;
    private boolean raceStarted = false;
    private WinnerOverlay overlay;

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

        raceTextArea = new JTextArea(2, 30);
        raceTextArea.setEditable(false);
        raceTextArea.setBackground(new Color(30,30,30));
        raceTextArea.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(raceTextArea);
        scrollPane.setBorder(null);
        racePanel = new RacePanel(tracks, raceLength, selectedHorses, chosenColour, customHorses);
        JPanel buttonsPanel = new JPanel();

        getContentPane().add(racePanel, BorderLayout.CENTER);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        JButton startButton = new DarkThemeButton("Start Race");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearText();
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

    public void clearText() {
        raceTextArea.setText("");
    }

    public void displayWinner(String winner) {
        closeWinnerDialog();
        overlay = new WinnerOverlay(this, winner);
        overlay.setVisible(true);
    }

    private void closeWinnerDialog() {
        if (overlay != null) {
            overlay.closeDialog();
        }
    }

    public void updateRace(ArrayList<Horse> horses, boolean finished) {
        racePanel.updateRace(horses);
        if (finished) {
            raceStarted = false;
        }
    }

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