package se.ebikerepair.model;

/**
 * Represents a monetary cost with amount and currency.
 */
public class Cost {
    private float amount;
    private String currency;

    /**
     * Creates a cost with the specified amount and currency.
     *
     * @param amount the monetary amount
     * @param currency the currency code (e.g. "SEK")
     */
    public Cost(float amount, String currency){
        this.amount = amount;
        this.currency = currency;
    }

    /**
     * Creates a cost with zero amount and SEK currency.
     */
    public Cost() {
        this(0, "SEK");
    }

    /** @return the monetary amount */
    public float getAmount() {
        return amount;
    }

    /** @return the currency code */
    public String getCurrency() {
        return currency;
    }

    /**
     * Adds another cost's amount to this cost.
     *
     * @param another the cost to add
     * @throws NullPointerException if another is null
     */
    public void calculate(Cost another) throws NullPointerException{
        if (another == null) throw new NullPointerException();
        this.amount += another.amount;
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", amount, currency);
    }
}
