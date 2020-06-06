
package Client;


/**
 *Clase company que hereda de cliente.
 */
public class Company extends Client {
    private String nit;
    private String companyName;
    private String commercialSector;

    public Company() {
    }

    
    public Company(String nit, String companyName, String commercialSector) {
        this.nit = nit;
        this.companyName = companyName;
        this.commercialSector = commercialSector;
    }

    public Company(String nit, String companyName, String commercialSector, String id, String name, String phone, String address, String ocupation, boolean isSubscribed) {
        super(id, name, phone, address, ocupation, isSubscribed);
        this.nit = nit;
        this.companyName = companyName;
        this.commercialSector = commercialSector;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCommercialSector() {
        return commercialSector;
    }

    public void setCommercialSector(String commercialSector) {
        this.commercialSector = commercialSector;
    }
    
    
    
}
