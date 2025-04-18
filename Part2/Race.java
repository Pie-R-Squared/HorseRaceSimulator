import java.lang.Math;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A multiple-horse race between 2-14 tracks, each horse running
 * in its own lane for a given distance between 2-30m
 * 
 * @author Aneeka
 * @version 2.0 (24th April 2024)
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
     * Initially there are no horses in the lanes and there
     * are 3 lanes by default. The race is updated on a timer
     * which moves horses along or makes them fall. Winners
     * are determined by the first horse to reach the end
     * but multiple winners can be declared in the event of a draw.
     * Stats and bet windows are updated through this class
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

                    endRaceUpdates(timer, raceLength, startTime, winner);

                } else if (finished) {
                    raceGUI.updateRace(horses, finished);
                    stats.appendText("Race terminated. All horses have fallen.");

                    endRaceUpdates(timer, raceLength, startTime, winner);
                }
            }
        });
    }
    
    /**
     * Re-initialises race length attribute to given value
     * 
     * @param raceLength the length of the racetrack
     */
    public void setRaceLength(int raceLength) {
        this.raceLength = raceLength;
    }

    /**
     * Re-initialises tracks attribute to given value
     * 
     * @param tracks the number of tracks/lanes
     */
    public void setTracks(int tracks) {
        this.tracks = tracks;
    }
    
    /**
     * Initialises the main GUI window so updates can be passed
     * directly from the Race class
     * 
     * @param raceGUI the main GUI window
     */
    public void setGUI(HorseRaceSimulatorGUI raceGUI) {
        this.raceGUI = raceGUI;
    }

    /**
     * Generates a random confidence level between 0.1 and 0.9
     * 
     * @return double random confidence level
     */
    private double generateRandomConfidence() {
        Random randomStream = new Random();
        int randomInt = randomStream.nextInt(9) + 1;
        double randomConfidenceLevel = randomInt / 10.0;
        return randomConfidenceLevel;
    }

    /**
     * Returns the horses in the race. If the number of horses
     * is less than the tracks, unnamed horses are added to fill
     * each lane. The updated list is then returned
     * 
     * @return List<Horse> the horses in the race
     */
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
     * The horses in the list are brought to the start and
     * confidence values adjusted so they're in the range
     * 0.1 to 0.9. Extra horses that don't have a lane
     * are removed. The timer is started and a new stats
     * instance is created by passing the horses
     * 
     * @param customHorses the customised list of horses to race
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

    /**
     * Stops the race timer and updates the other windows using
     * results from the constructor. Stats, bets and the main
     * GUI window are updated and the winner displayed
     * 
     * @param timer timer that coordinates the race on ticks
     * @param raceLength length of the racetrack
     * @param startTime time the race began in milliseconds
     * @param winner the winning horse(s) separated by commas
     */
    private void endRaceUpdates(Timer timer, int raceLength, long startTime, String winner) {
        timer.stop();
        endTime = System.currentTimeMillis();
        stats.updateProgressBars(raceLength, startTime, finished);
        stats.updateStatistics(this, startTime, endTime, raceLength);
        raceGUI.displayWinner(winner);
        
        if (raceGUI.bets != null)
            raceGUI.bets.updateAccount(winner);
    }
    
    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating. A fallen horse cannot move.
     * The probability that the horse will fall is very small (max is 0.1)
     * But will also depend exponentially on confidence so if
     * you double the confidence, the probability that it will fall is *2
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse)
    {
        
        if  (!theHorse.hasFallen())
        {
            if (Math.random() < theHorse.getConfidence())
            {
               theHorse.moveForward();
            }
            
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
     * Constructs a textual view of the race track, lane
     * by lane using horses current positions
     * 
     * @return String representation of current race frame
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
     * Return a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     * 
     * @param theHorse the horse to be printed
     * @return String representation of the horse's lane
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
     * Return a character appended a given number of times.
     * e.g. multiplePrint('x',5) will return: xxxxx
     * 
     * @param aChar the character in question
     * @param times the number of times to repeat the character
     * @return String the character repeated the given number of times
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