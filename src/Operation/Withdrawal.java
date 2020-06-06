package Operation;

/**
 * Clase retiro que contiene porcentajes de interés para los retiros en cada
 * caso de cuenta.
 */
public class Withdrawal extends Operation {
 
    private double SAVINGACCOUNTINTEREST;
    private double CURRENTACCOUNTINTEREST;

    public Withdrawal() {
    }

    public double getSAVINGACCOUNTINTEREST() {
        return SAVINGACCOUNTINTEREST = 0.020;
    }

    public double getCURRENTACCOUNTINTEREST() {
        return CURRENTACCOUNTINTEREST = 0.017;
    }

    


   
    
}
