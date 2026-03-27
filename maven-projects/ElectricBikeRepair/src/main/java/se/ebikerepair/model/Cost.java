package se.ebikerepair.model;

public class Cost {
    private float amount;
    private String currency;

    public Cost(float amount, String currency){
        this.amount = amount;
        this.currency = currency;
    }

    public Cost() {
        this(0, "SEK");
    }

    public float getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void calculate(Cost another) throws NullPointerException{
        if (another == null) throw new NullPointerException();
        this.amount += another.amount;
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", amount, currency);
    }
}
