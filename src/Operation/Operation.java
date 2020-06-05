package Operation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Date;

/**
 *
 * @author Santiago
 */
public class Operation {

    private String name;
    private Date date;
    private String accountID;
    private String typeAccount;
    private String branchOffice;

    public Operation() {
    }

    public Operation(String name, Date date, String accountID, String typeAccount, String branchOffice) {
        this.name = name;
        this.date = date;
        this.accountID = accountID;
        this.typeAccount = typeAccount;
        this.branchOffice = branchOffice;
    }

}
