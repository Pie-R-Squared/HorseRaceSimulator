import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

/**
 * Customisations interface for modifying the default race track
 * and horses. Allows user to specify race length, number of tracks, 
 * track colour, horse colour (+breed/accessory included), and
 * custom horse details (name, confidence level)
 * 
 * @author Aneeka 
 * @version 1.0 (24th April 2024)
 */
public class Customisations extends JFrame {
    private String[] horseImages = {"horses/black_horse.png", "horses/brown_horse.png", "horses/darkbrown_horse.png", "horses/lightbrown_horse.png", "horses/lighterbrown_horse.png", "horses/tan_horse.png"};
    private int selectedHorse = 0;
    private List<String> selectedHorses;
    private Color chosenColour = Color.LIGHT_GRAY;
    private static Customisations instance;
    private List<Horse> customHorses;
    private JTextArea addedHorsesDisplay;
    private JTextField tracksAmount;
    private JTextField raceLengthField;
    private JButton saveButton;
    private Timer timer;

    /**
     * Constructor for Customisations class which sets up the interface
     * for customising the race track and horses. Disposes of previous
     * customisations instances so only one window is open at a time.
     * Added validation to ensure racelength is between 2-30 and tracks
     * between 2-14. Name of horse must be alphabetic (up to 18 chars)
     * and confidence must be between 0.1 and 0.9
     * 
     * @param raceGUI main GUI window which this window is placed relative to
     */
    public Customisations(HorseRaceSimulatorGUI raceGUI) {
        if (instance != null && instance.isVisible()) {
            instance.dispose();
        }
        instance = this;
        
        setTitle("Customise Race Track");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 410);
        setLayout(new BorderLayout());

        if (raceGUI.getWidth() > 1000)
            setLocationRelativeTo(null);
        else
            setLocation(raceGUI.getWidth() + 5, raceGUI.getY());

        getContentPane().setBackground(new Color(30,30,30));
        UIManager.put("Label.foreground", Color.WHITE);
        UIManager.put("Button.background", new Color(30, 30, 30));
        UIManager.put("RadioButton.background", new Color(30, 30, 30));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("RadioButton.foreground", Color.WHITE);
        UIManager.put("TextField.border", BorderFactory.createLineBorder(Color.DARK_GRAY));
        UIManager.put("TextField.caretForeground", Color.LIGHT_GRAY);

        JPanel inputRacePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        tracksAmount = new JTextField(5);
        raceLengthField = new JTextField(5);
        raceLengthField.setText("10");
        tracksAmount.setText("3");
        inputRacePanel.setBackground(new Color(37,37,38));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        buttonPanel.setBackground(new Color(45,45,48));

        saveButton = new DarkThemeButton("Save Changes");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String raceLengthText = raceLengthField.getText();
                String tracksAmountText = tracksAmount.getText();
                
                try {
                    int raceLength = Integer.parseInt(raceLengthText);
                    int tracks = Integer.parseInt(tracksAmountText);

                    if (isValidRange(raceLength, 2, 30) && isValidRange(tracks, 2, 14)) {
                        if (customHorses.size() > tracks) {
                            tracks = customHorses.size();
                            if (customHorses.size() > 14)
                                tracks = 14;
                        }

                        while (selectedHorses.size() < Integer.parseInt(tracksAmountText)) {
                            selectedHorses.add(horseImages[selectedHorse]);
                            selectedHorse = (selectedHorse + 1) % horseImages.length;
                        }

                        raceGUI.updateRaceGUI(raceLength, tracks, selectedHorses, chosenColour, customHorses);
                        dispose();
                    } else
                        JOptionPane.showMessageDialog(Customisations.this, "Race length must be between 2-30\nTracks must be between 2-14");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(Customisations.this, "Invalid entry");
                }
            }
        });
        buttonPanel.add(saveButton);

        selectedHorses = new ArrayList<>();

        JPanel colourChooser = new JPanel(new FlowLayout());
        colourChooser.setBackground(new Color(30,30,30));
        JButton selectTrackColour = new DarkThemeButton("\uD83D\uDD8C");
        selectTrackColour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColour = JColorChooser.showDialog(null, "Choose a Colour", chosenColour);
                if (newColour != null) {
                    chosenColour = newColour;
                    highlightSaveButton();
                }
            }
        });
        colourChooser.add(selectTrackColour);

        addedHorsesDisplay = new JTextArea(6, 30);
        JScrollPane addedHorsesScrollPane = new JScrollPane(addedHorsesDisplay);
        addedHorsesScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        addedHorsesScrollPane.setBorder(null);
        addedHorsesDisplay.setEditable(false);
        addedHorsesDisplay.setBackground(new Color(30, 30, 30));
        addedHorsesDisplay.setForeground(Color.WHITE);
        addedHorsesDisplay.setText("Horses");

        customHorses = new ArrayList<>();
        JPanel addHorsePanel = new JPanel(new FlowLayout());
        addHorsePanel.setBackground(new Color(30,30,30));
        JLabel addHorseLabel = new JLabel("Add Horse");
        JLabel addHorseNameLabel = new JLabel("Name");
        JLabel addHorseConfidenceLabel = new JLabel("Confidence");
        JTextField addHorseName = new JTextField(10);
        JTextField addHorseConfidence = new JTextField(10);
        JButton addHorseButton = new DarkThemeButton("Add");
        addHorseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String horseName = addHorseName.getText();
                String horseConfidence = addHorseConfidence.getText();
                if (horseName.isEmpty() || horseConfidence.isEmpty()) {
                    JOptionPane.showMessageDialog(Customisations.this, "Please fill in all fields");
                } else {
                    if (isValidEntry(horseConfidence) && horseName.matches("[a-zA-Z]+[\\s[a-zA-Z]+]*") && horseName.length() < 19) {
                        Horse horse = new Horse('\u2658', horseName, Double.parseDouble(horseConfidence));
                        customHorses.add(horse);
                        updateAddedHorses(addedHorsesDisplay, horse);
                        selectedHorses.add(horseImages[selectedHorse]);
                        highlightSaveButton();
                    } else if (!isValidEntry(horseConfidence)) {
                        JOptionPane.showMessageDialog(Customisations.this, "Invalid entry. Confidence must be a decimal between 0.0 and 1.0 exclusive.");
                    } else {
                        JOptionPane.showMessageDialog(Customisations.this, "Invalid entry. Name must be a string of up to 18 alphabetic characters only.");
                    }
                    addHorseName.setText("");
                    addHorseConfidence.setText("");
                }
            }
        });

        JPanel horseSelectPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        horseSelectPanel.setBackground(new Color(30,30,30));
        JLabel horseLabel = new JLabel();
        updateHorseLabel(horseLabel);
        JButton backButton = new DarkThemeButton(String.valueOf('\u2B98'));
        JButton forwardButton = new DarkThemeButton(String.valueOf('\u2B9A'));
        backButton.addActionListener(e -> moveBackward(horseLabel));
        forwardButton.addActionListener(e -> moveForward(horseLabel));
        horseSelectPanel.add(backButton, BorderLayout.WEST);
        horseSelectPanel.add(horseLabel, BorderLayout.CENTER);
        horseSelectPanel.add(forwardButton, BorderLayout.EAST);

        inputRacePanel.add(new JLabel("Race Length: "));
        inputRacePanel.add(raceLengthField);
        inputRacePanel.add(new JLabel("Tracks: "));
        inputRacePanel.add(tracksAmount);
        inputRacePanel.add(new JLabel("Track Colour: "));
        inputRacePanel.add(colourChooser);

        JPanel addCustomHorsePanel = new JPanel(new BorderLayout());
        addCustomHorsePanel.setBackground(new Color(30,30,30));

        addHorsePanel.add(addHorseLabel);
        addHorsePanel.add(addHorseNameLabel);
        addHorsePanel.add(addHorseName);
        addHorsePanel.add(addHorseConfidenceLabel);
        addHorsePanel.add(addHorseConfidence);
        addHorsePanel.add(addHorseButton);
        addHorsePanel.add(addedHorsesScrollPane);

        addCustomHorsePanel.add(horseSelectPanel, BorderLayout.NORTH);
        addCustomHorsePanel.add(addHorsePanel, BorderLayout.CENTER);

        getContentPane().add(inputRacePanel, BorderLayout.NORTH);
        getContentPane().add(addCustomHorsePanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Reloads the customisations from previous selection.
     * Horses need to be selected again, but tracks, racelength
     * and track colour are preserved
     * 
     * @param raceLength length of race (2-30)
     * @param tracks number of tracks (2-14)
     * @param chosenColour track colour selected from colour chooser
     */
    public void reloadCustomisations(int raceLength, int tracks, Color chosenColour) {
        raceLengthField.setText(Integer.toString(raceLength));
        tracksAmount.setText(Integer.toString(tracks));
        this.chosenColour = chosenColour;
    }
    
    /**
     * Validates number is between valid range inclusive
     * 
     * @param number an integer number
     * @param low a lower bound which the number must be greater than or equal to
     * @param high an upper bound which the number must be less than or equal to
     * @return true if number is within the range, false otherwise
     */
    private boolean isValidRange(int number, int low, int high) {
        return number >= low && number <= high;
    }

    /**
     * Moves image slider backwards, updating the selectHorse index.
     * Updates the horse label to reflect the change and highlights
     * the save button to indicate changes have been made
     * 
     * @param horseLabel the label displaying the current selected image
     */
    private void moveBackward(JLabel horseLabel) {
        if (selectedHorse == 0)
            selectedHorse = horseImages.length - 1;
        else
            selectedHorse--;
        updateHorseLabel(horseLabel);
        highlightSaveButton();
    }

    /**
     * Moves image slider forwards, updating the selectHorse index.
     * Updates the horse label to reflect the change and highlights
     * the save button to indicate changes have been made
     * 
     * @param horseLabel the label displaying the current selected image
     */
    private void moveForward(JLabel horseLabel) {
        if (selectedHorse == horseImages.length - 1)
            selectedHorse = 0;
        else
            selectedHorse++;
        updateHorseLabel(horseLabel);
        highlightSaveButton();
    }

    /**
     * Scales down the horse image to 100x100 pixels and updates
     * the icon of the horse label with the image
     * 
     * @param horseLabel the label displaying the current selected image
     */
    private void updateHorseLabel(JLabel horseLabel) {
        ImageIcon horseIcon = new ImageIcon(horseImages[selectedHorse]);
        Image image = horseIcon.getImage();
        Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        horseLabel.setIcon(new ImageIcon(scaledImage));
    }

    /**
     * Appends the name and confidence of each added horse to the display
     * 
     * @param addedHorsesDisplay the text area displaying the added horses
     * @param horse the horse object to be added
     */
    private void updateAddedHorses(JTextArea addedHorsesDisplay, Horse horse) {
        String gap = "\t\t";
        if (horse.getName().length() > 14)
            gap = "\t";

        addedHorsesDisplay.append("\t" + horse.getName() + gap + String.format("%.1f", horse.getConfidence()) + "\n");
    }

    /**
     * Validates the input is a decimal between 0.0 and 1.0 exclusive
     * 
     * @param input the string input to be validated
     * @return true if the input is a valid decimal, false otherwise
     */
    private boolean isValidEntry(String input) {
        try {
            double entry = Double.parseDouble(input);
            if (entry > 0.0 && entry < 1.0)
                return true;
            else
                return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Highlights the save button by changing its background and foreground
     * colours to create a fade and flickering effect, based on timer
     */
    private void highlightSaveButton() {
        if (timer != null && timer.isRunning())
            timer.stop();
        
        timer = new Timer(50, new ActionListener() {
            private float shine = 0.0f;
            private boolean increase = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (increase) {
                    shine += 0.05f;
                    if (shine >= 1.0f)
                        increase = false;
                } else {
                    shine -= 0.05f;
                    if (shine <= 0.0f)
                        increase = true;
                }

                saveButton.setBackground(Color.getHSBColor(0.0f, 0.0f, shine));
                saveButton.setForeground(Color.getHSBColor(0.0f, 0.0f, 1.0f - shine));
            }
        });
        timer.start();
    }
}