package Operation;

import Operation.Operation;
import java.sql.Time;
import java.util.Date;
import starbankapp.BranchOffice;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Santiago
 */
public class Withdrawal extends Operation {
    private double value;
    private double SAVINGACCOUNTINTEREST;
    private double CURRENTACCOUNTINTEREST;

    public Withdrawal() {
    }

    public Withdrawal(double value) {
        this.value = value;
    }

    public double getSAVINGACCOUNTINTEREST() {
        return SAVINGACCOUNTINTEREST = 0.020;
    }

    public double getCURRENTACCOUNTINTEREST() {
        return CURRENTACCOUNTINTEREST = 0.017;
    }

    


   
    
}
