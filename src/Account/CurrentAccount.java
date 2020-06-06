
package Account;

import Operation.Operation;
import Operation.OperationManagerJSON;
import Operation.Withdrawal;
import java.util.ArrayList;
import java.util.Date;
import starbankapp.FXMLBranchOfficeController;

/**
 *CuentaCorriente que extiende de cuenta
 * y contiene operaciones sobre la cuenta corriente.
 */
public class CurrentAccount extends Account {

    public CurrentAccount() {
    }

    public CurrentAccount(double balance, boolean isActive, String clientID, String clientType, String key) {
        super(balance, isActive, clientID, clientType, key);
        this.id = generateID();
    }

    
    /**
     * Verifica que estén las condiciones para hacer la consignación
     * busca la cuenta destino y hace la operación, además registra la operación
     * en el JSON de operaciones.
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
            Account sourceAccount = (Account) accFacade.searchAccount(this.id, "Corriente");
            sourceAccount.setBalance(sourceAccount.getBalance() - value);
            accFacade.editAccount(sourceAccount);
            destinationAccount.setBalance(destinationAccount.getBalance() + value);
            accFacade.editAccount(destinationAccount);

        }
        Date date = new Date();
        Operation operation = new Operation("Consignación a cuenta: " + destinationID, date, this.id, "Corriente", FXMLBranchOfficeController.getBranch());
        OperationManagerJSON jsonop = new OperationManagerJSON();
        jsonop.addToJson(operation);
        return true;

    }

    
    /**
     * Método retirar que verifica condiciones mínima realiza la operación 
     * y envía el interés a la cuenta corriente de la sucursal,este método
     * es boolean para poder hacer verificaciones en los controladores.
     * @param value
     * @param accountKey
     * @return 
     */
    @Override
    public boolean withdraw(double value, String accountKey) {
        Withdrawal with = new Withdrawal();
        double interes = with.getCURRENTACCOUNTINTEREST();
        if (!(this.key.equalsIgnoreCase(accountKey)) || (this.isActive == false) || (value % 10000 != 0) || (value < 20000) || (this.balance <= 10000 + value + (value * interes))) {
            System.out.println("Error");
            return false;
        }
        AccountFacade accFacade = AccountFacade.getInstance();
        CurrentAccount account = (CurrentAccount) accFacade.searchAccount(this.id, "corriente");
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
     * Si la cuenta está activa añade fondos a la cuenta cuando el cliente 
     * entregue la plata física en el banco y se actualiza la info.
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
        Account account = accFacade.searchAccount(this.id, "Corriente");
        account.setBalance(account.getBalance() + value);
        accFacade.editAccount(account);
        Date date = new Date();
        Operation op = new Operation("Añadir fondos por un valor de: " + value, date, this.id, "Corriente", FXMLBranchOfficeController.getBranch());
        OperationManagerJSON jsonop = new OperationManagerJSON();
        jsonop.addToJson(op);
        return true;
    }

    /**
     * Método que retira la plata total cuando se desactiva una cuenta y le da 
     * la parte al banco y el resto se lo entrega al cliente.
     * @param accountKey
     * @return 
     */
    @Override
    public boolean withdrawRetirement(String accountKey) {
        Withdrawal with = new Withdrawal();
        double interes = with.getCURRENTACCOUNTINTEREST();
        if (!(this.key.equalsIgnoreCase(accountKey)) || (this.isActive == false)) {
            System.out.println("Error");
            return false;
        }
        AccountFacade accFacade = AccountFacade.getInstance();
        CurrentAccount account = (CurrentAccount) accFacade.searchAccount(this.id, "Corriente");
        double finalBalance = account.getBalance();
        String branchID = FXMLBranchOfficeController.getBranchID();
        double branchPart = finalBalance * interes;
        consign(branchPart, accountKey, branchID, "corriente");
        Date date = new Date();
        double balanceForClient = finalBalance - (finalBalance * interes);
        Operation op = new Operation("Desactivación de cuenta por un valor de: " + balanceForClient, date, this.id, "Ahorros", FXMLBranchOfficeController.getBranch());
        OperationManagerJSON jsonop = new OperationManagerJSON();
        jsonop.addToJson(op);
        return true;
    }

    /**
     * Genera un número de cuenta alfanumérico para la cuenta
     * al azar y sin repetir con otras cuentas de la misma clase.
     * @return 
     */
    @Override
    protected String generateID() {
        CurrentAccountJSON json = new CurrentAccountJSON();
        ArrayList<CurrentAccount> accounts = json.readJson();
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

}
