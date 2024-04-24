/**
 * Class for creating horse objects to be used in the Race class
 * Contains accessor methods to return the state of the horse (fallen, distance travelled, name, symbol, confidence)
 * Contains mutator methods to set the state of the horse (move, confidence, symbol, fallen)
 * 
 * @author Aneeka 
 * @version 2.0 (3rd April 2024)
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
        this.hasFallen = false;
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