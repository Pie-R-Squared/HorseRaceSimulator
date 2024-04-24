import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.util.Random;

/**
 * Horse Bets class which displays current track conditions,
 * horse odds and allows the user to place bets on horses.
 * The user can view their betting history upon a race
 * ending after placing a bet
 * 
 * @author Aneeka
 * @version 1.0 (24th April 2024)
 */
public class HorseBets extends JFrame {

    private Account account;
    private static HorseBets instance;
    private List<JRadioButton> options = new ArrayList<>();
    private List<Horse> horses = new ArrayList<>();
    private double betAmount;
    private double totalBetWinnings;
    private JTextArea betHistoryTextArea;
    private JTextPane trackConditionsText;
    private String[] conditions;
    private int tracks;

    /**
     * Constructor for objects of class HorseBets
     * Initialises the betting window and layout.
     * Radio buttons are used for selecting horses
     * and a text field for entering the bet amount.
     * A panel is provided for track conditions and
     * another panel for betting history
     * 
     * @param account the user account object
     * @param horses the list of horses in the race
     * @param tracks the number of tracks
     */
    public HorseBets(Account account, List<Horse> horses, int tracks) {
        if (instance != null && instance.isVisible()) {
            instance.dispose();
        }
        instance = this;

        this.account = account;
        this.horses = horses;
        this.tracks = tracks;
        
        setTitle("Horse Betting");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(550, horses.size() < 6 ? 600 : horses.size() < 10 ? 700 : 800);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(30,30,30));

        JPanel placeBetPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ButtonGroup horseGroup = new ButtonGroup();
        JTextField betAmountText = new JTextField(5);
        JButton placeBetButton = new DarkThemeButton("Place Bet");

        JPanel trackConditionsPanel = new JPanel();
        trackConditionsText = new JTextPane();
        trackConditionsText.setBackground(new Color(45,45,48));
        trackConditionsText.setForeground(Color.WHITE);
        trackConditionsPanel.add(trackConditionsText);

        betHistoryTextArea = new JTextArea(20, 30);
        betHistoryTextArea.setEditable(false);
        betHistoryTextArea.setBackground(new Color(30,30,30));
        betHistoryTextArea.setForeground(Color.WHITE);
        JScrollPane betHistory = new JScrollPane(betHistoryTextArea);

        for (int i = 0; i < horses.size() && i < tracks; i++) {
            JRadioButton horseButton = new JRadioButton(horses.get(i).getName());
            horseButton.setBackground(new Color(45,45,48));
            horseButton.setForeground(Color.WHITE);
            options.add(horseButton);
            horseGroup.add(horseButton);
            placeBetPanel.add(horseButton);
        }

        placeBetPanel.add(betAmountText);
        placeBetPanel.add(placeBetButton);
        placeBetPanel.setBackground(new Color(45,45,48));

        placeBetButton.addActionListener(e -> {
            try {
                betAmount = Double.parseDouble(betAmountText.getText());
                if (!isOptionSelected()) {
                    JOptionPane.showMessageDialog(HorseBets.this, "Please select a horse");
                } else if (betAmount > account.getBalance()) {
                    JOptionPane.showMessageDialog(HorseBets.this, "Insufficient funds");
                } else if (betAmount < 50) {
                    JOptionPane.showMessageDialog(HorseBets.this, "Minimum bet is 50 Ʊ");
                } else {
                    account.subtractAmount(betAmount);
                    JOptionPane.showMessageDialog(HorseBets.this, "Bet placed");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(HorseBets.this, "Invalid entry");
            }
        });

        add(trackConditionsPanel, BorderLayout.NORTH);
        add(placeBetPanel, BorderLayout.CENTER);
        add(betHistory, BorderLayout.SOUTH);
        updateTrackConditionsDisplay();
    }

    /**
     * Updates the user's account if they have made a
     * valid bet. The names of winning horses is
     * compared to the selected radio buttons. If they
     * match, the user receives a profit of the bet
     * amount x odds. If the user loses, they lose
     * the bet amount. Losing results in a hint window
     * being displayed. The winnings/losses are saved
     * into a file
     * 
     * @param winner the winning horse(s) separated by ","
     */
    public void updateAccount(String winner) {
        String[] horseNames = winner.split(", ");
        totalBetWinnings = 0;
        boolean betWon = false;
        for (int i = 0; i < horses.size() && i < tracks; i++) {
            for (String horseName : horseNames) {
                if (!options.toString().contains(horseName))
                    continue;
                if (options.get(horses.indexOf(horses.get(i))).isSelected() && horses.get(i).getName().equals(horseName)) {
                    totalBetWinnings = betAmount + betAmount * getOdds(horses.get(i));
                    account.addAmount(totalBetWinnings);
                    betWon = true;
                }
            }
        }

        if (betAmount > 0) {
            JOptionPane.showMessageDialog(HorseBets.this, betWon ? "Congratulations, you won " + (totalBetWinnings - betAmount) + " Ʊ" : "Bet lost -" + betAmount + " Ʊ\nRemaining balance: " + account.getBalance() + " Ʊ");
            if (!betWon)
                JOptionPane.showMessageDialog(HorseBets.this, "Hint: Low betting odds have better chances of winning.\nHigher odds have higher payouts.");
            saveToFile(betWon);
            displayHistory();
        }

        for (JRadioButton option : options) {
            option.setSelected(false);
            betAmount = 0;
            totalBetWinnings = 0;
        }
        updateTrackConditionsDisplay();
    }

    /**
     * Validates the user has selected one of the options
     * 
     * @return true if an option is selected, false otherwise
     */
    private boolean isOptionSelected() {
        for (JRadioButton option : options) {
            if (option.isSelected()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Updates the track conditions panel with randomised
     * track conditions. The odds of each horse are displayed
     * as well as the user's current balance
     */
    private void updateTrackConditionsDisplay() {
        conditions = generateRandomConditions();
        trackConditionsText.setText("Funds: " + account.getBalance() + " Ʊ\n- - - - - -\nTrack Conditions: " + conditions[0] + "\nTemperature: " + conditions[1] + "\nCurrent Odds:");
        for (int i = 0; i < horses.size() && i < tracks; i++) {
            trackConditionsText.setText(trackConditionsText.getText() + "\n" + horses.get(i).getName() + " - " + getOdds(horses.get(i)) + ":1");
        }
        trackConditionsText.setEditable(false);
    }

    /**
     * Generates a random track firmness level and a weather state
     * Firmness ranges from "firm" to "heavy", where "firm" is better
     * Weather states range from "sunny" to "snowy"
     * 
     * @return String[] array of track firmness and weather state
     */
    private String[] generateRandomConditions() {
        String[] firmness = {"firm", "good", "soft", "heavy"}; // Firmness of track
        String[] weather = {"sunny", "cloudy", "rainy", "snowy"}; // Weather state
        Random randomStream = new Random();
        int randomFirmIndex = randomStream.nextInt(firmness.length);
        int randomWeatherIndex = randomStream.nextInt(weather.length);
        return new String[] {firmness[randomFirmIndex], weather[randomWeatherIndex]};
    }

    /**
     * Retrieves the odds from the file that was updated in the
     * Statistics class. Applies additional track conditions factor
     * to the odds. Good conditions increase the odds by 1, bad
     * conditions further increase it
     * 
     * @param horse the horse object to retrieve odds for
     * @return int the modified odds of the horse
     */
    private int getOdds(Horse horse) {
        try (BufferedReader horseStats = new BufferedReader(new FileReader("horse_statistics.csv"))) {
            String line;

            while ((line = horseStats.readLine()) != null) {
                String[] fields = line.split(", ");
                if (fields[0].equals(horse.getName())) {
                    int odds = Integer.parseInt(fields[6].substring(0, fields[6].indexOf(":")));
                    if (conditions[0].equals("firm") || conditions[1].equals("sunny")) {
                        return odds;
                    } else if (conditions[0].equals("good") || conditions[1].equals("cloudy")) {
                        return odds + 1;
                    } else if (conditions[0].equals("soft") || conditions[1].equals("rainy")) {
                        return odds + 2;
                    } else if (conditions[0].equals("heavy") || conditions[1].equals("snowy")) {
                        return odds + 3;
                    } else {
                        return odds;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Saves the user's latest bet to the history file. Add to winnings
     * if bet won, or add to losses if bet lost. The user's name, winnings,
     * losses, bet amount and date timestamp are saved to the file
     * 
     * @param betWon true if the user won the bet, false otherwise
     */
    private void saveToFile(boolean betWon) {
        double winnings = 0.0;
        double losses = 0.0;

        try (BufferedReader bettingHistory = new BufferedReader(new FileReader("betting_history.csv"))) {
            String line;

            while ((line = bettingHistory.readLine()) != null) {
                String[] fields = line.split(", ");
                if (fields[0].equals(account.getName())) {
                    winnings = Double.parseDouble(fields[1]);
                    losses = Double.parseDouble(fields[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (PrintWriter bettingHistory = new PrintWriter(new FileWriter("betting_history.csv", true))) {
            String date = new java.util.Date().toString();

            if (betWon) {
                winnings += betAmount;
            } else {
                losses += betAmount;
            }
            bettingHistory.println(account.getName() + ", " + winnings + ", " + losses + ", " + (totalBetWinnings - betAmount) + ", " + date);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the user's betting history in the text area using
     * the information added to the bet history file
     */
    private void displayHistory() {
        betHistoryTextArea.setText("");
        try (BufferedReader bettingHistory = new BufferedReader(new FileReader("betting_history.csv"))) {
            String line;

            while ((line = bettingHistory.readLine()) != null) {
                String[] fields = line.split(", ");
                for (String field : fields) {
                    betHistoryTextArea.append(field + "\t");
                }
                betHistoryTextArea.append("\n--------------------------------------------------------------------\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
