package starbankapp;

    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Account.CurrentAccount;
import java.util.ArrayList;


/**
 *
 * @author Santiago
 */
public class BranchOffice {
    private String id;
    private String address;
    private String name;
    private String city;
    private String branchAccountID;

    public BranchOffice() {
    }
    
    
    public BranchOffice(String id, String address, String name, String city, String branchAccountID) {
        this.id = id;
        this.address = address;
        this.name = name;
        this.city = city;
        this.branchAccountID = branchAccountID;
    }
    
    public ArrayList createOffices(){
       BranchOffice office0 = new BranchOffice("0","Centro", "StarBank Centro", "Medellín", "AAA0");
       BranchOffice office1 = new BranchOffice("1","Carrera 23A","StarBank Carrera 23A", "Bogotá","AAA1");
       BranchOffice office2 = new BranchOffice("2", "Edificio Coltejer", "StarBank Coltejer", "Medellín","AAA2");
       ArrayList<String> offices = new ArrayList<>();
       offices.add("StarBank Centro");
       offices.add("StarBank Carrera 23A");
       offices.add("StarBank Coltejer");
       return offices;
    }
    
    
   
}
