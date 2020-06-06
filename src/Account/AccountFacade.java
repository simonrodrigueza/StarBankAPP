/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Account;

import java.util.ArrayList;

/**
 * Clase fachada para la cuenta, la cual busca, edita y añade
 * cuentas en el JSON, usa Singleton.
 */
public class AccountFacade {

    private JSONAccount accountPersist;
    private static AccountFacade accountFacadeInstance;

   /**
    * Retonar la instancia (singleton) de la clase.
    * @return 
    */
    public static AccountFacade getInstance() {
        if (accountFacadeInstance == null) {
            accountFacadeInstance = new AccountFacade();
        }
        return accountFacadeInstance;
    }
    /**
     * Busca una cuenta mediante su id y el tipo de cuenta y
     * retonar un objeto de tipo cuenta.
     * @param id
     * @param typeAccount
     * @return 
     */
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

   /**
    * Añade en el JSON una cuenta que se le pasa por parámetro
    * y guarda nuevamente la info actualizada.
    * @param account 
    */
    public void addAccount(Account account) {
        if (account instanceof SavingAccount) {
            accountPersist = new SavingAccountJSON();
            accountPersist.addToJson(account);
        } else if (account instanceof CurrentAccount) {
            accountPersist = new CurrentAccountJSON();
            accountPersist.addToJson(account);
        }
    }

    /**
     * Edita el JSON sobre la cuenta que se manda como 
     * parámetro y vuelva a guardar el JSON con los 
     * datos actualizados.
     * @param account 
     */
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
