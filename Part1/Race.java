import java.util.concurrent.TimeUnit;
import java.lang.Math;
import java.util.ArrayList;

/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author Aneeka
 * @version 2.0 (23rd April 2024)
 */
public class Race
{
    private int raceLength;
    private int tracks;
    private ArrayList<Horse> horses;

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * 
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance)
    {
        raceLength = distance;
        horses = new ArrayList<Horse>();
        tracks = 3;
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
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished. Race updates every
     * 100 milliseconds and accounts for no
     * winners or multiple winners
     */
    public void startRace()
    {
        boolean finished = false;
        boolean noWinners = false;
        
        for (Horse h : horses)
            h.goBackToStart();
                      
        while (!finished) {

            for (Horse horse : horses) {
                moveHorse(horse);
            }

            printRace();

            for (Horse horse: horses) {
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
            
            if (finished && !noWinners) {
                printRace();
                System.out.print("\nAnd the winner is");
                for (Horse h : horses) {
                    if (raceWonBy(h))
                        System.out.print(" " + h.getName());
                }
            } else if (finished) {
                System.out.println("Race terminated. All horses have fallen.");
            }
           
            try{ 
                TimeUnit.MILLISECONDS.sleep(100);
            } catch(Exception e){}
        }
    }
    
    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating. Falling decreases rating.
     * A fallen horse cannot move
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
            
            /* the probability that the horse will fall is very small (max is 0.1)
               but will also will depends exponentially on confidence 
               so if you double the confidence, the probability that it will fall is *2
            */
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
    private boolean raceWonBy(Horse theHorse)
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
     * Print the race on the terminal
     */
    private void printRace()
    {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {}  //clear the terminal window
        
        multiplePrint('=',raceLength+3);
        System.out.println();

        for (Horse horse : horses) {
            printLane(horse);
            System.out.println();
        }
        
        multiplePrint('=',raceLength+3);
        System.out.println();    
    }
    
    /**
     * print a horse's lane during the race
     * for example
     * |           X                      |
     * to show how far the horse has run
     */
    private void printLane(Horse theHorse)
    {
        int spacesBefore = theHorse.getDistanceTravelled();
        int spacesAfter = raceLength - theHorse.getDistanceTravelled();
        
        System.out.print('|');
        
        multiplePrint(' ',spacesBefore);
        
        if(theHorse.hasFallen())
        {
            System.out.print('\u2717');
        }
        else
        {
            System.out.print(theHorse.getSymbol());
        }
        
        multiplePrint(' ',spacesAfter);
        
        System.out.print('|');
        System.out.print(" " + theHorse.getName().toUpperCase() + " (Current confidence " + String.format("%.1f", theHorse.getConfidence()) + ")");
    }
        
    
    /***
     * print a character a given number of times.
     * e.g. printmany('x',5) will print: xxxxx
     * 
     * @param aChar the character to Print
     */
    private void multiplePrint(char aChar, int times)
    {
        int i = 0;
        while (i < times)
        {
            System.out.print(aChar);
            i = i + 1;
        }
    }
}