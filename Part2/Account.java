public class Account {
    private String name;
    private double balance;

    public Account(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public void addAmount(double amount) {
        balance += amount;
    }

    public void subtractAmount(double amount) {
        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }
}