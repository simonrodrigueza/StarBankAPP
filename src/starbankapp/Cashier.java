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
 *
 * @author simonrodrigueza
 */
public class Cashier {

    private AccountFacade accountFacade = AccountFacade.getInstance();
    private ClientFacade clientFacade = ClientFacade.getInstance();

    public void addNaturalClient(NaturalPerson client) {
        clientFacade.addClient(client);
    }

    public void addCompanyClient(Company client) {
        clientFacade.addClient(client);
    }

    public void addCurrentAccount(CurrentAccount account) {
        accountFacade.addAccount(account);

    }

    public void addSavignAccount(SavingAccount account) {
        accountFacade.addAccount(account);
    }

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

    public void activateClient(Client client) {
        client.setIsSubscribed(true);
        clientFacade.editClient(client);
    }

    public void addNumberOfAccountToClient(Client client) {
        if (client.getNumberOfAccounts() == 0) {
            client.setNumberOfAccounts(client.getNumberOfAccounts() + 1);
            activateClient(client);
        } else {
            client.setNumberOfAccounts(client.getNumberOfAccounts() + 1);
            clientFacade.editClient(client);
        }

    }

    public void substractNumberOfAccountToClient(Client client) {
        client.setNumberOfAccounts(client.getNumberOfAccounts() - 1);
        if (client.getNumberOfAccounts() == 0) {
            desactivateClient(client);
        } else {
            clientFacade.editClient(client);
        }

    }

    public void desactivateClient(Client client) {
        client.setIsSubscribed(false);
        clientFacade.editClient(client);
    }

}
