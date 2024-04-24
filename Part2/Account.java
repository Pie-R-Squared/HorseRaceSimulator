/**
 * Account class for holding the account holder name
 * and balance. This is updated when the user wins or
 * loses a bet. Accessor methods provide access to the
 * balance and name of the account holder
 * 
 * @author Aneeka
 * @version 1.0 (24th April 2024)
 */
public class Account {
    private String name;
    private double balance;

    /**
     * Initialises the name and balance of the account
     * 
     * @param name the name of the account holder
     * @param balance the balance of the account holder
     */
    public Account(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    /**
     * Adds the specified amount to the balance
     * 
     * @param amount the amount to be added
     */
    public void addAmount(double amount) {
        balance += amount;
    }

    /**
     * Subtracts the specified amount from the balance
     * 
     * @param amount the amount to be subtracted
     */
    public void subtractAmount(double amount) {
        balance -= amount;
    }

    /**
     * Returns the balance of the account holder
     * 
     * @return double balance of the account holder
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Returns the name of the account holder
     * 
     * @return String name of the account holder
     */
    public String getName() {
        return name;
    }
}