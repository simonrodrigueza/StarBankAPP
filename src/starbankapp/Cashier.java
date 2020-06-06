package starbankapp;

import Account.Account;
import Account.AccountFacade;
import Account.CurrentAccount;
import Account.SavingAccount;
import Client.Client;
import Client.NaturalPerson;
import Client.Company;
import Client.ClientFacade;

/**
 *Cuenta que maneja las responsabilidades del cajero, es decir
 * las interacciones con las clases cuenta y cliente.
 * @author simonrodrigueza
 */
public class Cashier {

    private AccountFacade accountFacade = AccountFacade.getInstance();
    private ClientFacade clientFacade = ClientFacade.getInstance();

   /**
    * Añade una persona natural al sistema.
    * @param client 
    */
    public void addNaturalClient(NaturalPerson client) {
        clientFacade.addClient(client);
    }
    /**
     * Añade compañia al sistema.
     * @param client 
     */
    public void addCompanyClient(Company client) {
        clientFacade.addClient(client);
    }
    /**
     * Añade cuenta corriente al sistema.
     * @param account 
     */
    public void addCurrentAccount(CurrentAccount account) {
        accountFacade.addAccount(account);

    }
    /**
     * Añade cuenta de ahorros al sistema.
     * @param account 
     */
    public void addSavignAccount(SavingAccount account) {
        accountFacade.addAccount(account);
    }
    /**
     * Activa la cuenta cuando está desactivada.
     * @param accountID
     * @param accountType
     * @param value
     * @return 
     */
    public boolean activateAccount(String accountID, String accountType, double value) {
        if (value < 20000) {
            return false;
        }
        Account account = accountFacade.searchAccount(accountID, accountType);
        if (account == null) {
            return false;
        }
        String clientID = account.getClientID();
        String clientType = account.getClientType();
        Client client = clientFacade.searchClient(clientID, clientType);
        if (!client.isIsSubscribed()) {
            activateClient(client);
        }
        account.setBalance(value);
        account.setIsActive(true);
        accountFacade.editAccount(account);
        return true;
    }

    /**
     * Desactiva la cuenta y se entrega el dinero al cliente.
     * @param account
     * @return 
     */
    public boolean desactivateAccount(Account account) {
        String key = account.getKey();
        String ClientID = account.getClientID();
        String clientType = account.getClientType();
        ClientFacade cFacade = ClientFacade.getInstance();
        Client client = cFacade.searchClient(ClientID, clientType);
        boolean correct = account.withdrawRetirement(key);
        String accountID = account.getId();
        AccountFacade accFacade = AccountFacade.getInstance();
        Account account2 = accFacade.searchAccount(accountID, clientType);
        if (correct) {
            substractNumberOfAccountToClient(client);
            account.setIsActive(false);
            account.setBalance(0);
            accFacade.editAccount(account);
        } else {
            System.out.println("Clave incorrecta o La cuenta ya estaba inactiva.");
        }
        return correct;
    }

    /**
     * Activa un cliente cuando tiene al menos una cuenta activa.
     * @param client 
     */
    public void activateClient(Client client) {
        client.setIsSubscribed(true);
        clientFacade.editClient(client);
    }

    /**
     * Desactiva al cliente cuando no tiene cuentas activas.
     * @param client 
     */
    public void desactivateClient(Client client) {
        client.setIsSubscribed(false);
        clientFacade.editClient(client);
    }

    /**
     * Añade cuentas activas al cliente cuando se activa una nueva.
     * @param client 
     */
    public void addNumberOfAccountToClient(Client client) {
        if (client.getNumberOfAccounts() == 0) {
            client.setNumberOfAccounts(client.getNumberOfAccounts() + 1);
            activateClient(client);
        } else {
            client.setNumberOfAccounts(client.getNumberOfAccounts() + 1);
            clientFacade.editClient(client);
        }

    }
    /**
     * Resta una cuenta al cliente cuando desactiva una cuenta y si es la última
     * desactiva al cliente.
     * @param client 
     */
    public void substractNumberOfAccountToClient(Client client) {
        client.setNumberOfAccounts(client.getNumberOfAccounts() - 1);
        if (client.getNumberOfAccounts() == 0) {
            desactivateClient(client);
        } else {
            clientFacade.editClient(client);
        }

    }

}
