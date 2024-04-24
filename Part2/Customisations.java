import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

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
    private int index = 0;

    public Customisations(HorseRaceSimulatorGUI raceGUI) {
        if (instance != null && instance.isVisible()) {
            instance.dispose();
        }
        instance = this;
        
        setTitle("Customise Race Track");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());
        setLocation(raceGUI.getWidth() + 5, raceGUI.getY());
        getContentPane().setBackground(new Color(30,30,30));
        UIManager.put("Label.foreground", Color.WHITE);
        UIManager.put("Button.background", new Color(30, 30, 30));
        UIManager.put("RadioButton.background", new Color(30, 30, 30));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("RadioButton.foreground", Color.WHITE);
        UIManager.put("TextField.border", BorderFactory.createLineBorder(Color.DARK_GRAY));

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
                    if (isValidEntry(horseConfidence) && horseName.matches("[a-zA-Z]+[\\s[a-zA-Z]+]*")) {
                        Horse horse = new Horse('\u2658', horseName, Double.parseDouble(horseConfidence));
                        customHorses.add(horse);
                        updateAddedHorses(addedHorsesDisplay, horse);
                        selectedHorses.add(horseImages[selectedHorse]);
                        index++;
                        highlightSaveButton();
                    } else if (!isValidEntry(horseConfidence)) {
                        JOptionPane.showMessageDialog(Customisations.this, "Invalid entry. Confidence must be a decimal between 0.0 and 1.0 exclusive.");
                    } else {
                        JOptionPane.showMessageDialog(Customisations.this, "Invalid entry. Name must be a string of alphabetic characters only.");
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
        backButton.addActionListener(e -> moveBackward(horseLabel, index));
        forwardButton.addActionListener(e -> moveForward(horseLabel, index));
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

    public void reloadCustomisations(int raceLength, int tracks, Color chosenColour) {
        raceLengthField.setText(Integer.toString(raceLength));
        tracksAmount.setText(Integer.toString(tracks));
        this.chosenColour = chosenColour;
    }
    
    private boolean isValidRange(int number, int low, int high) {
        return number >= low && number <= high;
    }

    private void moveBackward(JLabel horseLabel, int index) {
        if (selectedHorse == 0)
            selectedHorse = horseImages.length - 1;
        else
            selectedHorse--;
        updateHorseLabel(horseLabel);
        highlightSaveButton();
    }

    private void moveForward(JLabel horseLabel, int index) {
        if (selectedHorse == horseImages.length - 1)
            selectedHorse = 0;
        else
            selectedHorse++;
        updateHorseLabel(horseLabel);
        highlightSaveButton();
    }

    private void updateHorseLabel(JLabel horseLabel) {
        ImageIcon horseIcon = new ImageIcon(horseImages[selectedHorse]);
        Image image = horseIcon.getImage();
        Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        horseLabel.setIcon(new ImageIcon(scaledImage));
    }

    private void updateAddedHorses(JTextArea addedHorsesDisplay, Horse horse) {
        addedHorsesDisplay.append("\t" + horse.getName() + "\t" + String.format("%.1f", horse.getConfidence()) + "\n");
    }

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