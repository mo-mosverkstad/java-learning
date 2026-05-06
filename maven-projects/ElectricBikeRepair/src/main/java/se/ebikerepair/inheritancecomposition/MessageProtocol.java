package se.ebikerepair.inheritancecomposition;

import se.ebikerepair.inheritancecomposition.IPV4;

public class MessageProtocol {
    private IPV4 sourceAdress;
    private IPV4 destinationAdress;
    private String title;
    private String description;

    public MessageProtocol(IPV4 sourceAdress, IPV4 destinationAdress, String title, String description){
        this.sourceAdress = sourceAdress;
        this.destinationAdress = destinationAdress;
        this.title = title;
        this.description = description;
    }

    public IPV4 getSourceAdress(){
        return sourceAdress;
    }

    public IPV4 getDestinationAdress(){
        return destinationAdress;
    }
}
