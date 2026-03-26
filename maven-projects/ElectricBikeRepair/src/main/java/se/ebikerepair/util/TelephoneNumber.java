package se.ebikerepair.util;

public class TelephoneNumber {
    private String telephoneNumber;
    public TelephoneNumber(String telephoneNumber){
        if (telephoneNumber == null || telephoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid phone number");
        }
        this.telephoneNumber = telephoneNumber.trim().replaceAll("[^\\d+]", "");
    }

    public String toE164(){
        if (telephoneNumber.startsWith("+")) return telephoneNumber;
        if (telephoneNumber.startsWith("00")) return "+" + telephoneNumber.substring(2);
        if (telephoneNumber.startsWith("0")) return "+46" + telephoneNumber.substring(1);
        throw new IllegalArgumentException("Telephone number format problem");
    }
}
