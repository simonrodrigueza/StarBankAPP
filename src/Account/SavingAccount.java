package Account;


import Operation.Operation;
import Operation.OperationManagerJSON;
import Operation.Withdrawal;
import java.util.ArrayList;
import java.util.Date;
import starbankapp.FXMLBranchOfficeController;

/**
*Cuenta de ahorros que se extiende de la clase Account y contiene
* las operaciones que se hacen sobre ella.
 */
public class SavingAccount extends Account {

    private double interest;

    public SavingAccount() {

    }

    public SavingAccount(double balance, boolean isActive, String clientID, String clientType, String key) {
        super(balance, isActive, clientID, clientType, key);
        this.id = generateID();

    }

    /**
     * Verifica consignación y la hace en caso de ser correcta.
     * @param value
     * @param accountKey
     * @param destinationID
     * @param destinationType
     * @return 
     */
    @Override
    public boolean consign(double value, String accountKey, String destinationID, String destinationType) {
        if ((this.isActive == false) || !(this.key.equalsIgnoreCase(accountKey)) || (this.balance - value < 10000)) {
            return false;
        }
        AccountFacade accFacade = AccountFacade.getInstance();

        Account destinationAccount;
        destinationAccount = accFacade.searchAccount(destinationID, destinationType);

        if (destinationAccount == null || destinationAccount.isActive == false) {
            return false;
        } else {
            Account sourceAccount = (Account) accFacade.searchAccount(this.id, "ahorros");
            sourceAccount.setBalance(sourceAccount.getBalance() - value);
            accFacade.editAccount(sourceAccount);
            destinationAccount.setBalance(destinationAccount.getBalance() + value);
            accFacade.editAccount(destinationAccount);

        }
        Date date = new Date();
        Operation operation = new Operation("Consignación a cuenta: " + destinationID, date, this.id, "Ahorros", FXMLBranchOfficeController.getBranch());
        OperationManagerJSON jsonop = new OperationManagerJSON();
        jsonop.addToJson(operation);
        return true;

    }

    
    /**
     * Verifica condiciones, realiza el retiro y envía la parte del interés a
     * la cuenta corriente de la sucursal.
     * @param value
     * @param accountKey
     * @return 
     */
    @Override
    public boolean withdraw(double value, String accountKey) {
        Withdrawal with = new Withdrawal();
        double interes = with.getSAVINGACCOUNTINTEREST();
        if (!(this.key.equalsIgnoreCase(accountKey)) || (this.isActive == false) || (value % 10000 != 0) || (value < 20000) || (this.balance <= 10000 + value + (value * interes))) {
            System.out.println("Error");
            return false;
        }
        AccountFacade accFacade = AccountFacade.getInstance();
        SavingAccount account = (SavingAccount) accFacade.searchAccount(this.id, "ahorros");
        account.setBalance(account.getBalance() - value);
        accFacade.editAccount(account);
        String branchID = FXMLBranchOfficeController.getBranchID();
        //CurrentAccount branchAccount = (CurrentAccount) accFacade.searchAccount(branchID, "corriente");
        double branchPart = value * interes;
        consign(branchPart, accountKey, branchID, "corriente");

        Date date = new Date();
        Operation op = new Operation("Retiro por un total de: " + value + " más el 2%", date, this.id, "Ahorros", FXMLBranchOfficeController.getBranch());
        OperationManagerJSON jsonop = new OperationManagerJSON();
        jsonop.addToJson(op);
        return true;
    }

    /**
     * Permite retirar todo el dinero cuando se desactiva la cuenta.
     * @param accountKey
     * @return 
     */
    @Override
    public boolean withdrawRetirement(String accountKey) {
        Withdrawal with = new Withdrawal();
        double interes = with.getSAVINGACCOUNTINTEREST();
        if (!(this.key.equalsIgnoreCase(accountKey)) || (this.isActive == false)) {
            System.out.println("Error");
            return false;
        }
        AccountFacade accFacade = AccountFacade.getInstance();
        SavingAccount account = (SavingAccount) accFacade.searchAccount(this.id, "ahorros");
        double finalBalance = account.getBalance();
        String branchID = FXMLBranchOfficeController.getBranchID();
        double branchPart = finalBalance * interes;
        consign(branchPart, accountKey, branchID, "corriente");
        //account.setBalance(0.0);
        //accFacade.editAccount(account);
        Date date = new Date();
        double balanceForClient = finalBalance - (finalBalance * interes);
        Operation op = new Operation("Desactivación de cuenta por un valor de: " + balanceForClient, date, this.id, "Ahorros", FXMLBranchOfficeController.getBranch());
        OperationManagerJSON jsonop = new OperationManagerJSON();
        jsonop.addToJson(op);
        return true;
    }

   /**
    * Añade fondos a la cuenta cuando se entrega el dinero físico en el banco.
    * @param value
    * @param accountKey
    * @return 
    */
    @Override
    public boolean addFounds(double value, String accountKey) {
        if (!(this.key.equalsIgnoreCase(accountKey)) || (this.isActive == false)) {
            System.out.println("Error");
            return false;
        }
        AccountFacade accFacade = AccountFacade.getInstance();
        Account account = accFacade.searchAccount(this.id, "ahorros");
        account.setBalance(account.getBalance() + value);
        accFacade.editAccount(account);
        Date date = new Date();
        Operation op = new Operation("Añadir fondos por un valor de: " + value, date, this.id, "Ahorros", FXMLBranchOfficeController.getBranch());
        OperationManagerJSON jsonop = new OperationManagerJSON();
        jsonop.addToJson(op);
        return true;
    }

    /**
     * Genera ID aleatorio alfanumérico sin repetición en su misma clase.
     * @return 
     */
    @Override
    protected String generateID() {
        SavingAccountJSON json = new SavingAccountJSON();
        ArrayList<SavingAccount> accounts = json.readJson();
        String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        int num;
        String id = "";
        for (int i = 0; i < 3; i++) {
            num = (int) (Math.random() * (letters.length));
            id += letters[num];
        }
        id += Integer.toString(accounts.size() + 1);
        return id;
    }

    public double getInterest() {
        return interest;
    }

}
