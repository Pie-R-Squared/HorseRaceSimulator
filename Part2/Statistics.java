import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

<<<<<<< HEAD
/**
 * Statistics class which displays the statistics of the horses
 * in the race. Calculates and stores details such as wins, falls,
 * average speed, finish times, win ratio and betting odds in
 * HashMaps. Stats are saved to a file for future reference and
 * loaded into a JTextArea for display
 * 
 * @author Aneeka
 * @version 1.0 (24th April 2024)
 */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
public class Statistics extends JFrame {
    private static Map<String, Integer> horseWins = new HashMap<>();
    private static Map<String, Integer> horseFalls = new HashMap<>();
    private static Map<String, Double> horseAvgSpeed = new HashMap<>();
    private static Map<String, Double> horseFinishTimes = new HashMap<>();
    private static Map<String, Double> horseWinRatio = new HashMap<>();
    private static Map<String, Double> horseBettingOdds = new HashMap<>();
    public List<Horse> horses;
    private static int totalRaces = 0;
    private JTextArea raceTextArea;
    private JPanel graphPanel;
    private JTextArea statsArea;
    private static Statistics instance;

<<<<<<< HEAD
    /**
     * First constructor for objects of class Statistics
     * Initialises the window and sets the location relative
     * to the main GUI window
     * 
     * @param raceGUI main GUI window
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    public Statistics(HorseRaceSimulatorGUI raceGUI) {
        if (instance != null && instance.isVisible()) {
            instance.dispose();
        }
        instance = this;
        initialise();
        if (getHeight() < 600)
            setLocation(raceGUI.getX(), raceGUI.getHeight() + 10);
    }

<<<<<<< HEAD
    /**
     * Second constructor for objects of class Statistics
     * Initialises the window and displays the statistics.
     * This constructor is intended to be called from the
     * Race class
     * 
     * @param horses the list of horses in the race
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    public Statistics(ArrayList<Horse> horses) {
        if (instance != null && instance.isVisible()) {
            instance.dispose();
            setVisible(true);
        }
        instance = this;
        this.horses = horses;
        totalRaces++;
        initialise();
        displayStats();
        if (getHeight() < 600)
            setLocation(10, 410);
    }

<<<<<<< HEAD
    /**
     * Initialises the window layout and organises panels
     * for different statistics components
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    private void initialise() {
        setTitle("Analyse Race Statistics");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(30,30,30));

        raceTextArea = new JTextArea(8, 30);
        raceTextArea.setEditable(false);
        raceTextArea.setBackground(Color.LIGHT_GRAY);

        JPanel miniRaceWindow = new JPanel(new FlowLayout(FlowLayout.CENTER));
        miniRaceWindow.setBackground(new Color(37,37,38));
        JScrollPane scrollPane = new JScrollPane(raceTextArea);
        miniRaceWindow.add(scrollPane);

        graphPanel = new JPanel();
        graphPanel.setLayout(new BoxLayout(graphPanel, BoxLayout.Y_AXIS));
        add(graphPanel, BorderLayout.SOUTH);
        getContentPane().add(miniRaceWindow, BorderLayout.NORTH);
    }

<<<<<<< HEAD
    /**
     * Re-initalises the graph panel with new progress bars
     * to allow them to updated on the Race timer ticks. Also
     * resizes the window to fit the content if needed
     * 
     * @param horses the list of horses in the race
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    public void reinitialise(ArrayList<Horse> horses) {
        graphPanel.removeAll();
        if (horses != null) {
            for (int i = 0; i < horses.size(); i++) {
                JProgressBar progressBar = new JProgressBar(0, 100);
                progressBar.setStringPainted(true);
                progressBar.setBackground(new Color(192, 192, 192));
                progressBar.setForeground(new Color(45,45,48));
                progressBar.setBorder(BorderFactory.createLineBorder(new Color(45,45,48)));
                graphPanel.add(progressBar);
            }
        }
        add(graphPanel, BorderLayout.SOUTH);
        revalidate();
        repaint();
        resizeToFitContent();
    }

<<<<<<< HEAD
    /**
     * Appends text to the mini race window
     * 
     * @param text the text to be added
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    public void appendText(String text) {
        raceTextArea.append(text);
    }

<<<<<<< HEAD
    /**
     * Updates the textual view of the race in the mini race window
     * 
     * @param raceInfo the textual display of the race
     */
=======
    public void clearText() {
        raceTextArea.setText("");
    }

>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    public void updateRace(String raceInfo) {
        raceTextArea.setText(raceInfo);
    }

<<<<<<< HEAD
    /**
     * Updates the statistics by retrieving past values from the HashMaps
     * and adding the new values. Calculates win ratio, averages and betting
     * odds. Saves the stats to a file for future reference
     * 
     * @param race race object which is used to obtain win status of horses
     * @param startTime starting time of the race
     * @param endTime ending time of the race
     * @param raceLength total length of race track
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    public void updateStatistics(Race race, long startTime, long endTime, int raceLength) {

        for (Horse horse : horses) {
            Integer totalWins = horseWins.getOrDefault(horse.getName(), 0);
            Integer totalFalls = horseFalls.getOrDefault(horse.getName(), 0);
            Double winRatio = (double) horseWins.getOrDefault(horse.getName(), 0) / totalRaces;
            Double avgSpeed = horseAvgSpeed.getOrDefault(horse.getName(), 0.0);
            Double avgFinishTime = horseFinishTimes.getOrDefault(horse.getName(), 0.0);

            if (race.raceWonBy(horse)) {
                totalWins++;
                horseWins.put(horse.getName(), totalWins);
            }

            if (horse.hasFallen()) {
                totalFalls++;
                horseFalls.put(horse.getName(), totalFalls);
            }

            double speed = (double) horse.getDistanceTravelled() / ((endTime - startTime)/1000.0);
            double finishTime = raceLength / speed;

            if (horse.hasFallen())
                finishTime = 0.0;
            else {
                avgSpeed = calculateAverage(horseAvgSpeed, speed, horse.getName());
                avgFinishTime = calculateAverage(horseFinishTimes, finishTime, horse.getName());
            }

            horseWinRatio.put(horse.getName(), formatInfinitiesAndNaNs(winRatio));
            horseAvgSpeed.put(horse.getName(), formatInfinitiesAndNaNs(avgSpeed));
            horseFinishTimes.put(horse.getName(), formatInfinitiesAndNaNs(avgFinishTime));
            horseBettingOdds.put(horse.getName(), formatInfinitiesAndNaNs( calculateBettingOdds(totalWins, totalFalls, avgSpeed, avgFinishTime)));
        }

        saveToFile();
    }

<<<<<<< HEAD
    /**
     * Saves calculated statistics to a file for later reference.
     * File format is CSV for easier reading and editing. Data
     * is formatted to 2 decimal places for speed and finish times,
     * and to 1 decimal place for win ratio. Betting odds are a
     * whole number formatted as 'odds:1'
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    private void saveToFile() {
        try (PrintWriter horseStats = new PrintWriter("horse_statistics.csv")) {
            horseStats.println("Horse, Wins, Falls, Avg Spd(m/s), Finish(s), Win Ratio, Bet Odds");
            for (Horse horse : horses) {
                String odds = (int) Math.round(horseBettingOdds.get(horse.getName())) + ":1";
                horseStats.println(horse.getName() + ", " +
                horseWins.get(horse.getName()) + ", " +
                horseFalls.get(horse.getName()) + ", " +
                String.format("%.2f", horseAvgSpeed.get(horse.getName())) + ", " +
                String.format("%.2f", horseFinishTimes.get(horse.getName())) + ", " +
                String.format("%.1f", horseWinRatio.get(horse.getName())) + ", " +
                odds);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

<<<<<<< HEAD
    /**
     * Displays the stats by appending to the text area. 
     * Information is read from the file and null values
     * are formatted as 0.0. The window is resized if needed
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    public void displayStats() {
        statsArea = new JTextArea(100, 100);
        statsArea.setEditable(false);
        statsArea.setBackground(new Color(30,30,30));
        statsArea.setForeground(Color.WHITE);
        statsArea.append("Total races: " + totalRaces + "\n\n- - - - - - History - - - - - - - - - - - - - - - -\n\n");
        
        List<String> horseFileStats = new ArrayList<>();

        try (BufferedReader horseStats = new BufferedReader(new FileReader("horse_statistics.csv"))) {
            String line;

            while ((line = horseStats.readLine()) != null) {
                String[] fields = line.split(", ");
                horseFileStats.add(formatNullValues(fields[0]) + "\t" +
                formatNullValues(fields[1]) + "\t" + formatNullValues(fields[2]) + "\t" +
                formatNullValues(fields[3]) + "\t" + formatNullValues(fields[4]) + "\t" +
                formatNullValues(fields[5]) + "\t" + formatNullValues(fields[6]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String line : horseFileStats) {
            statsArea.append(line + "\n");
        }

        getContentPane().add(statsArea, BorderLayout.CENTER);
        resizeToFitContent();
    }
    
<<<<<<< HEAD
    /**
     * Adjusts the window size to fit the content, with
     * varying dimensions, depending on graph panel and 
     * textarea heights
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    private void resizeToFitContent() {
        if (graphPanel.getHeight() > raceTextArea.getHeight()/2 && graphPanel.getHeight() < 150){
            setSize(getWidth(), 500);
            setLocationRelativeTo(null);
        } else if (graphPanel.getHeight() > raceTextArea.getHeight()/2) {
            setSize(getWidth(), 600);
            setLocationRelativeTo(null);
        } else
            setSize(600, 400);
    }

<<<<<<< HEAD
    /**
     * Calculates the average for a particular statistic Map.
     * Adds new value to the total and divides by total races
     * 
     * @param map HashMap containing previous average of stat
     * @param newValue new value to be added
     * @param horseName name of the horse to search map
     * @return double new average value
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    private double calculateAverage(Map<String, Double> map, double newValue, String horseName) {
        if (totalRaces > 0) {
            double newTotal = map.getOrDefault(horseName, 0.0) * totalRaces + newValue;
            return newTotal / (totalRaces + 1);
        }
        else {
            return 0.0;
        }
    }

<<<<<<< HEAD
    /**
     * Calculates betting odds based on performance metrics.
     * A percentage weight of each metric is used, then the
     * absolute value of the odds is taken to account
     * for negatives
     * 
     * @param totalWins total wins of the horse
     * @param totalFalls total falls of the horse
     * @param avgSpeed current average speed of the horse
     * @param avgFinishTime current average finish time of the horse
     * @return double betting odds for the horse
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    private double calculateBettingOdds(int totalWins, int totalFalls, double avgSpeed, double avgFinishTime) {
        return Math.abs(1 / ((totalWins * 0.4 - totalFalls * 0.1 + avgSpeed * 0.3 + avgFinishTime * 0.2) - 1));
    }
    
<<<<<<< HEAD
    /**
     * Formats null values so that they are displayed as 0.0
     * 
     * @param field the field read from the file
     * @return String formatted field
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    private String formatNullValues(String field) {
        if (field.equals("null") || field.trim().isEmpty())
            return "0";
        else
            return field;
    }

<<<<<<< HEAD
    /**
     * Formats Infinity, -Infinity and NaN values to 0.0
     * This ensures that the stats are valid
     * 
     * @param number the number to be formatted
     * @return Double formatted number
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    private Double formatInfinitiesAndNaNs(double number) {
        if (Double.isInfinite(number) || Double.isNaN(number))
            return 0.0;
        else
            return number;
    }

<<<<<<< HEAD
    /**
     * Updates the progress bars every race timer tick.
     * Progress bars display the distance travelled, average
     * speed and finish time of each horse in real-time
     * 
     * @param distance total race distance
     * @param startTime starting time of the race
     * @param finished flag to check if the race has finished
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    public void updateProgressBars(int distance, long startTime, boolean finished) {
        long time = System.currentTimeMillis();
        double elapsedTime = (time - startTime) / 1000.0;
        int horseIndex = 0;
        for (Horse horse : horses) {
            int progress = horse.getDistanceTravelled();
            
            if (horseIndex < graphPanel.getComponentCount()) {
                JProgressBar progressBar = (JProgressBar) graphPanel.getComponent(horseIndex);
                double speed = progress/elapsedTime;
                double finishTime = 0.0;
                if (horse.hasFallen())
                    speed = 0.0;
                else if (finished)
                    finishTime = distance / speed;
                progressBar.setValue(progress * 100 / distance);
                progressBar.setString(horse.getName() + ": " + progress + "m travelled   |   Avg speed: " + String.format("%.2f", speed) + "m/s   |   Finish time: " + String.format("%.2f", finishTime) + "s");
            }
            
            horseIndex++;
        }
    }
}