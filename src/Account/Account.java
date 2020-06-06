package Account;

/**
 * Clase abstracta de la clase Cuenta que contiene los métodos abstractos de las
 * operaciones sobre las cuentas.
 */
public abstract class Account {

    protected String id;
    protected double balance;
    protected boolean isActive;
    protected String clientID;
    protected String clientType;
    protected String key;

    public Account() {
    }

    public abstract boolean withdraw(double value, String accountKey);

    public abstract boolean consign(double value, String accountKey, String destinationID, String destinationType);

    public abstract boolean addFounds(double value, String accountKey);

    public abstract boolean withdrawRetirement(String accountKey);

    public Account(double balance, boolean isActive, String clientID, String clientType, String key) {
        this.balance = balance;
        this.isActive = isActive;
        this.clientID = clientID;
        this.clientType = clientType;
        this.key = key;
    }

    protected abstract String generateID();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
