/**
 * Class for testing the race class and the horse class.
 * Adds three horses: Jack, Joey and Max with confidence
 * levels 0.4, 0.5 and 0.6 respectively. Characters
 * used to represent the horses are ♘, ♞ and ♚
 * 
 * @author Aneeka 
 * @version 2.0 (3rd April 2024)
 */
public class Test {
    public static void main(String[] args) {
        Race r = new Race(10);
        r.addHorse(new Horse('\u2658', "Jack", 0.4), 1);
        r.addHorse(new Horse('\u265E', "Joey", 0.5), 2);
        r.addHorse(new Horse('\u265A', "Max", 0.6), 3);
        r.startRace();
    }
}
