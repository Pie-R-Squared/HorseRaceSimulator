<<<<<<< HEAD
=======
//import java.util.concurrent.TimeUnit;
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
import java.lang.Math;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
<<<<<<< HEAD
 * A multiple-horse race between 2-14 tracks, each horse running
 * in its own lane for a given distance between 2-30m
 * 
 * @author Aneeka
 * @version 2.0 (24th April 2024)
=======
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author McFarewell
 * @version 1.0
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
 */
public class Race
{
    private int raceLength;
    private int tracks;
    private ArrayList<Horse> horses;
    private HorseRaceSimulatorGUI raceGUI;
    private Timer timer;
    private boolean finished;
    private boolean noWinners;
    private long startTime;
    private long endTime;
    public Statistics stats;

    /**
     * Constructor for objects of class Race
<<<<<<< HEAD
     * Initially there are no horses in the lanes and there
     * are 3 lanes by default. The race is updated on a timer
     * which moves horses along or makes them fall. Winners
     * are determined by the first horse to reach the end
     * but multiple winners can be declared in the event of a draw.
     * Stats and bet windows are updated through this class
=======
     * Initially there are no horses in the lanes
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance)
    {
        horses = new ArrayList<Horse>();
        raceLength = distance;
        tracks = 3;

        timer = new Timer(100, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                for (Horse horse : horses) {
                    moveHorse(horse);
                }
                
                String raceInfo = printRace();
                stats.updateRace(raceInfo);
                raceGUI.updateRace(horses, finished);
                stats.reinitialise(horses);
                stats.updateProgressBars(raceLength, startTime, finished);

                for (Horse horse : horses) {
                    if (raceWonBy(horse)) {
                        finished = true;
                        noWinners = false;
                        if (horse.getConfidence() < 0.9)
                            horse.setConfidence(horse.getConfidence()+0.1);
                        break;
                    }
                }
                
                if (!finished) {
                    boolean allHorsesFallen = true;
                    for (Horse horse : horses) {
                        if (!horse.hasFallen()) {
                            allHorsesFallen = false;
                            break;
                        }
                    }
                    if (allHorsesFallen) {
                        finished = true;
                        noWinners = true;
                    }
                }
                
                String winner = "";
                if (finished && !noWinners) {
                    raceInfo = printRace();
                    raceGUI.updateRace(horses, finished);
                    stats.updateRace(raceInfo);
                    stats.appendText("\nAnd the winner is");
                    for (Horse h : horses) {
                        if (raceWonBy(h)) {
                            stats.appendText(" " + h.getName());
                            winner += h.getName() + ", ";
                        }
                    }
                    timer.stop();
                    endTime = System.currentTimeMillis();
                    stats.updateProgressBars(raceLength, startTime, finished);
                    updateStats();
                    raceGUI.displayWinner(winner);
                    if (raceGUI.bets != null)
                        raceGUI.bets.updateAccount(winner);
                } else if (finished) {
                    raceGUI.updateRace(horses, finished);
                    stats.appendText("Race terminated. All horses have fallen.");
                    timer.stop();
                    endTime = System.currentTimeMillis();
                    stats.updateProgressBars(raceLength, startTime, finished);
                    updateStats();
                    raceGUI.displayWinner(winner);
                    if (raceGUI.bets != null)
                        raceGUI.bets.updateAccount(winner);
                }
            }
        });
    }
    
<<<<<<< HEAD
    /**
     * Re-initialises race length attribute to given value
     * 
     * @param raceLength the length of the racetrack
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    public void setRaceLength(int raceLength) {
        this.raceLength = raceLength;
    }

<<<<<<< HEAD
    /**
     * Re-initialises tracks attribute to given value
     * 
     * @param tracks the number of tracks/lanes
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    public void setTracks(int tracks) {
        this.tracks = tracks;
    }
    
<<<<<<< HEAD
    /**
     * Initialises the main GUI window so updates can be passed
     * directly from the Race class
     * 
     * @param raceGUI the main GUI window
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    public void setGUI(HorseRaceSimulatorGUI raceGUI) {
        this.raceGUI = raceGUI;
    }

<<<<<<< HEAD
    /**
     * Generates a random confidence level between 0.1 and 0.9
     * 
     * @return double random confidence level
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    private double generateRandomConfidence() {
        Random randomStream = new Random();
        int randomInt = randomStream.nextInt(9) + 1;
        double randomConfidenceLevel = randomInt / 10.0;
        return randomConfidenceLevel;
    }

<<<<<<< HEAD
    /**
     * Returns the horses in the race. If the number of horses
     * is less than the tracks, unnamed horses are added to fill
     * each lane. The updated list is then returned
     * 
     * @return List<Horse> the horses in the race
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    public List<Horse> getHorses() {
        int num = horses.size();

        if (num == tracks)
            return horses;

        ArrayList<Horse> newHorses = new ArrayList<Horse>(horses);

        while (num < tracks) {
            Horse h = new Horse('\u2658', "unnamed"+num, generateRandomConfidence());
            newHorses.add(h);
            num++;
        }

        while (tracks < newHorses.size())
            newHorses.remove(newHorses.size()-1);

        return newHorses;
    }
    
    /**
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void addHorse(Horse theHorse, int laneNumber)
    {
        if (laneNumber <= tracks)
            horses.add(laneNumber-1, theHorse);
        else
            System.out.println("Cannot add horse to lane " + laneNumber + " because there is no such lane");
    }
    
    /**
     * Start the race
<<<<<<< HEAD
     * The horses in the list are brought to the start and
     * confidence values adjusted so they're in the range
     * 0.1 to 0.9. Extra horses that don't have a lane
     * are removed. The timer is started and a new stats
     * instance is created by passing the horses
     * 
     * @param customHorses the customised list of horses to race
=======
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
     */
    public void startRaceGUI(List<Horse> customHorses)
    {
        if (customHorses != null && !customHorses.isEmpty())
            horses = new ArrayList<Horse>(customHorses);

        for (Horse h : horses) {
            h.goBackToStart();
            if (h.getConfidence() > 0.9)
                h.setConfidence(0.9);
            else if (h.getConfidence() < 0.1)
                h.setConfidence(0.1);
        }

        while (tracks < horses.size())
            horses.remove(horses.size()-1);
            
        startTime = System.currentTimeMillis();
        timer.start();  
        finished = false;
        noWinners = false;
        stats = new Statistics(horses);
    }

<<<<<<< HEAD
    /**
     * Updates statistics using start and end times as well
     * as the race length
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    private void updateStats() {
        stats.updateStatistics(this, startTime, endTime, raceLength);
    }
    
    /**
     * Randomly make a horse move forward or fall depending
<<<<<<< HEAD
     * on its confidence rating. A fallen horse cannot move.
     * The probability that the horse will fall is very small (max is 0.1)
     * But will also depend exponentially on confidence so if
     * you double the confidence, the probability that it will fall is *2
=======
     * on its confidence rating
     * A fallen horse cannot move
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse)
    {
        
        if  (!theHorse.hasFallen())
        {
<<<<<<< HEAD
=======
            //the probability that the horse will move forward depends on the confidence;
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
            if (Math.random() < theHorse.getConfidence())
            {
               theHorse.moveForward();
            }
            
<<<<<<< HEAD
=======
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence()))
            {
                if (!raceWonBy(theHorse)) {
                    theHorse.fall();
                    if (theHorse.getConfidence() > 0.1)
                        theHorse.setConfidence(theHorse.getConfidence()-0.1);
                }
            }
        }
    }
        
    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    public boolean raceWonBy(Horse theHorse)
    {
        if (theHorse.getDistanceTravelled() == raceLength)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /***
<<<<<<< HEAD
     * Constructs a textual view of the race track, lane
     * by lane using horses current positions
     * 
     * @return String representation of current race frame
=======
     * Print the race on the terminal
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
     */
    private String printRace()
    {
        StringBuffer sb = new StringBuffer();
        sb.append('\u000C');
        
        sb.append(multiplePrint('=',raceLength+3) + "\n");

        int num = 0;
        for (Horse h : horses) {
            sb.append(printLane(h) + "\n");
            num++;
        }
        
        while (num < tracks) {
            Horse h = new Horse('\u2658', "unnamed"+num, generateRandomConfidence());
            horses.add(h);
            sb.append(printLane(h) + "\n");
            num++;
        }
        
        sb.append(multiplePrint('=',raceLength+3) + "\n");
        return sb.toString();  
    }
    
    /**
<<<<<<< HEAD
     * Return a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     * 
     * @param theHorse the horse to be printed
     * @return String representation of the horse's lane
=======
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
     */
    private String printLane(Horse theHorse)
    {
        StringBuffer sb = new StringBuffer();

        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();
        
        sb.append('|');
        sb.append(multiplePrint(' ',spacesBefore*2));
        
        if(theHorse.hasFallen())
        {
            sb.append('\u2717');
        }
        else
        {
            sb.append(theHorse.getSymbol());
        }
        
        sb.append(multiplePrint(' ',spacesAfter*2));
        
        sb.append('|');
        sb.append(" " + theHorse.getName().toUpperCase() + " (Current confidence " + String.format("%.1f", theHorse.getConfidence()) + ")");
        return sb.toString();
    }
        
    
    /***
<<<<<<< HEAD
     * Return a character appended a given number of times.
     * e.g. multiplePrint('x',5) will return: xxxxx
     * 
     * @param aChar the character in question
     * @param times the number of times to repeat the character
     * @return String the character repeated the given number of times
=======
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
     */
    private String multiplePrint(char aChar, int times)
    {
        StringBuffer sb = new StringBuffer();
        int i = 0;
        while (i < times)
        {
            sb.append(aChar);
            i = i + 1;
        }
        return sb.toString();
    }
}