
/**
 * Horse class provides a template for creating Horse objects,
 * representing horses in the Race simulation program. A horse
 * is represented by a single character and it has name, distance,
 * fallen and confidence level attributes to determine the
 * state and identity of each Horse object.
 * 
 * @author Aneeka Ahmad
 * @version 1 (23 March 2024)
 */
public class Horse
{
    /**
     * Encapsulated fields for horse objects
     */
    private String horseName;
    private char horseSymbol;
    private int distanceTravelled;
    private boolean hasFallen;
    private double horseConfidence;
    
      
    /**
     * Constructor for objects of class Horse
     * 
     * @param horseSymbol the symbol representing the horse
     * @param horseName the name of the horse
     * @param horseConfidence the confidence level of the horse
     */
    public Horse(char horseSymbol, String horseName, double horseConfidence)
    {
        this.horseSymbol = horseSymbol;
        this.horseName = horseName;
        this.horseConfidence = horseConfidence;
    }
    
    /**
     * Procedure to make the horse object fall
     */
    public void fall()
    {
        hasFallen = true;
    }

    /**
     * Returns the confidence level of the horse
     * @return the horse confidence level
     */
    public double getConfidence()
    {
        return this.horseConfidence;
    }

    /**
     * Returns the distance travelled by the horse
     * @return the horse distance travelled
     */
    public int getDistanceTravelled()
    {
        return this.distanceTravelled;
    }

    /**
     * Returns the name of the horse
     * @return the horse name
     */
    public String getName()
    {
        return this.horseName;
    }

    /**
     * Returns the symbol representing the horse
     * @return the horse symbol
     */
    public char getSymbol()
    {
        return this.horseSymbol;
    }

    /**
     * Procedure to reset the horse object
     * Resets distance to 0, fallen status to false
     */
    public void goBackToStart()
    {
        this.distanceTravelled = 0;
    }

    /**
     * Returns the fallen status of the horse
     * @return true if the horse has fallen, false otherwise
     */
    public boolean hasFallen()
    {
        return this.hasFallen;
    }

    /**
     * Procedure to move the horse object 1m forward
     */
    public void moveForward()
    {
        this.distanceTravelled++;
    }

    /**
     * Procedure to set the distance travelled by the horse
     * @param newDistance the new distance travelled by the horse
     */
    public void setConfidence(double newConfidence)
    {
        this.horseConfidence = newConfidence;
    }

    /**
     * Procedure to set the symbol of the horse
     * @param newSymbol the new symbol representing the horse
     */
    public void setSymbol(char newSymbol)
    {
        this.horseSymbol = newSymbol;
    }
    
}
