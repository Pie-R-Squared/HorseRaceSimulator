<<<<<<< HEAD
/**
 * Account class for holding the account holder name
 * and balance. This is updated when the user wins or
 * loses a bet. Accessor methods provide access to the
 * balance and name of the account holder
 * 
 * @author Aneeka
 * @version 1.0 (24th April 2024)
 */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
public class Account {
    private String name;
    private double balance;

<<<<<<< HEAD
    /**
     * Initialises the name and balance of the account
     * 
     * @param name the name of the account holder
     * @param balance the balance of the account holder
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    public Account(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

<<<<<<< HEAD
    /**
     * Adds the specified amount to the balance
     * 
     * @param amount the amount to be added
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    public void addAmount(double amount) {
        balance += amount;
    }

<<<<<<< HEAD
    /**
     * Subtracts the specified amount from the balance
     * 
     * @param amount the amount to be subtracted
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    public void subtractAmount(double amount) {
        balance -= amount;
    }

<<<<<<< HEAD
    /**
     * Returns the balance of the account holder
     * 
     * @return double balance of the account holder
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    public double getBalance() {
        return balance;
    }

<<<<<<< HEAD
    /**
     * Returns the name of the account holder
     * 
     * @return String name of the account holder
     */
=======
>>>>>>> 609657cdc898bcfc0b0f04bce046e1a8ad794e4c
    public String getName() {
        return name;
    }
}