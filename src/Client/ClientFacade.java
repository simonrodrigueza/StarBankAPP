/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import java.util.ArrayList;

/**
 *
 * @author SIMON
 */
public class ClientFacade {

    JSONClient clientPersist;
    private static ClientFacade clientFacadeInstance;

    //Organizar cuando no lo encuentra.
    public Client searchClient(String id, String clientType) {
        if (clientType.equalsIgnoreCase("natural")) {
            clientPersist = new NaturalPersonJSON();
            ArrayList<NaturalPerson> clients = clientPersist.readJson();
            for (Client client : clients) {
                if (client.getId().equalsIgnoreCase(id)) {
                    System.out.println("Encontrado");
                    return client;
                }
            }
        } else if (clientType.equalsIgnoreCase("compañia")) {
            clientPersist = new CompanyJSON();
            ArrayList<NaturalPerson> company = clientPersist.readJson();
            for (Client client : company) {
                if (client.getId().equalsIgnoreCase(id)) {
                    System.out.println("Encontrado");
                    return client;
                }
            }
        }

        return null;

    }

    public void addClient(Client client) {
        if (client instanceof NaturalPerson) {
            clientPersist = new NaturalPersonJSON();
            clientPersist.addToJson(client);
        } else if (client instanceof Company) {
            clientPersist = new CompanyJSON();
            clientPersist.addToJson(client);
        }

    }

    public void editClient(Client client) {
        if (client instanceof NaturalPerson) {
            clientPersist = new NaturalPersonJSON();
            clientPersist.editJson(client);
        } else if (client instanceof Company) {
            clientPersist = new CompanyJSON();
            clientPersist.editJson(client);
        }
    }
    public void activateClient(Client client){
        
    }

    public static ClientFacade getInstance() {
        if (clientFacadeInstance == null) {
            clientFacadeInstance = new ClientFacade();
        }
        return clientFacadeInstance;
    }

}
