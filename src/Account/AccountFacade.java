/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Account;

import java.util.ArrayList;

/**
 *
 * @author SIMON
 */
public class AccountFacade {

    private JSONAccount accountPersist;
    private static AccountFacade accountFacadeInstance;

    public static AccountFacade getInstance() {
        if (accountFacadeInstance == null) {
            accountFacadeInstance = new AccountFacade();
        }
        return accountFacadeInstance;
    }

    public Account searchAccount(String id, String typeAccount) {
        if (typeAccount.equalsIgnoreCase("ahorros")) {
            accountPersist = new SavingAccountJSON();
            ArrayList<SavingAccount> accounts = accountPersist.readJson();
            for (Account account : accounts) {
                if (account.getId().equalsIgnoreCase(id)) {
                    System.out.println("Cuenta Encontrada");
                    return account;
                }
            }
        } else if (typeAccount.equalsIgnoreCase("corriente")) {
            accountPersist = new CurrentAccountJSON();
            ArrayList<CurrentAccount> accounts = accountPersist.readJson();
            for (Account account : accounts) {
                if (account.getId().equalsIgnoreCase(id)) {
                    System.out.println("Cuenta Corriente Encontrada");
                    return account;
                }
            }
        }
        return null;
    }

    public void addAccount(Account account) {
        if (account instanceof SavingAccount) {
            accountPersist = new SavingAccountJSON();
            accountPersist.addToJson(account);
        } else if (account instanceof CurrentAccount) {
            accountPersist = new CurrentAccountJSON();
            accountPersist.addToJson(account);
        }
    }

    public void editAccount(Account account) {
        if (account instanceof SavingAccount) {
            accountPersist = new SavingAccountJSON();
            accountPersist.editJson(account);
        } else if (account instanceof CurrentAccount) {
            accountPersist = new CurrentAccountJSON();
            accountPersist.editJson(account);

        }
    }

}
