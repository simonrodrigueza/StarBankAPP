/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Account;

import Account.Account;
import java.util.ArrayList;

/**
 *
 * @author SIMON
 */
public interface JSONAccount {

    public abstract ArrayList readJson();
    public abstract void addToJson(Account acc);
    public abstract void editJson(Account acc);

}
