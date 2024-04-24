import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

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

    public Statistics(HorseRaceSimulatorGUI raceGUI) {
        if (instance != null && instance.isVisible()) {
            instance.dispose();
        }
        instance = this;
        initialise();
        if (getHeight() < 600)
            setLocation(raceGUI.getX(), raceGUI.getHeight() + 10);
    }

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

    public void appendText(String text) {
        raceTextArea.append(text);
    }

    public void clearText() {
        raceTextArea.setText("");
    }

    public void updateRace(String raceInfo) {
        raceTextArea.setText(raceInfo);
    }

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

    private double calculateAverage(Map<String, Double> map, double newValue, String horseName) {
        if (totalRaces > 0) {
            double newTotal = map.getOrDefault(horseName, 0.0) * totalRaces + newValue;
            return newTotal / (totalRaces + 1);
        }
        else {
            return 0.0;
        }
    }

    private double calculateBettingOdds(int totalWins, int totalFalls, double avgSpeed, double avgFinishTime) {
        return Math.abs(1 / ((totalWins * 0.4 - totalFalls * 0.1 + avgSpeed * 0.3 + avgFinishTime * 0.2) - 1));
    }
    
    private String formatNullValues(String field) {
        if (field.equals("null") || field.trim().isEmpty())
            return "0";
        else
            return field;
    }

    private Double formatInfinitiesAndNaNs(double number) {
        if (Double.isInfinite(number) || Double.isNaN(number))
            return 0.0;
        else
            return number;
    }

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