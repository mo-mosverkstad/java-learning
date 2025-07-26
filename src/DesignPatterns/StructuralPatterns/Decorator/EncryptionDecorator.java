package DesignPatterns.StructuralPatterns.Decorator;

import java.util.Base64;

public class EncryptionDecorator extends DataSourceDecorator {

    public EncryptionDecorator(DataSource source) {
        super(source);
    }

    @Override
    public void writeData(String data) {
        super.writeData(encode(data));
    }

    @Override
    public String readData() {
        return decode(super.readData());
    }

    private String encode(String data) {
        byte[] result = data.getBytes();
        for (int i = 0; i < result.length; i++) {
            result[i] += (byte) 1;
        }
        System.out.println("Encrypted data: " + new String(result));
        System.out.println("Encoded data: " + Base64.getEncoder().encodeToString(result));
        return Base64.getEncoder().encodeToString(result);
    }

    private String decode(String data) {
        byte[] result = Base64.getDecoder().decode(data);
        System.out.println("Decoded data: " + new String(result));
        
        for (int i = 0; i < result.length; i++) {
            result[i] -= (byte) 1;
        }
        System.out.println("Decrypted data: " + new String(result));
        return new String(result);
    }
}
