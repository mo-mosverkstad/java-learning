package se.ebikerepair.inheritancecomposition;

import java.io.File;
import java.io.RandomAccessFile;

import main.java.se.ebikerepair.inheritancecomposition.IPV4;

public class InheritedProtocolInterpreterAdapter extends RandomAccessFile {
    public InheritedProtocolInterpreterAdapter(File file){
        super(file, "r");
    }

    public MessageProtocol readProtocol(){
        IPV4 sourceAdress = readIPV4();
        IPV4 destinationAdress = readIPV4();

        String title = "No title"; // Update the title here!!!
        String description = "No description"; // Update the description here!!!
        

        return MessageProtocol(sourceAdress, destinationAdress, title, description);
    }

    public IPV4 readIPV4(){
        // Read IPV4 here
        return new IPV4(0, 0, 0, 0);
    }

}
