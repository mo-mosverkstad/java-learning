package se.ebikerepair.model;

public class ResultDTO {
    private final boolean checked;
    private final boolean toBeRepaired;
    private final String description;

    public ResultDTO(boolean checked, boolean toBeRepaired, String description){
        this.checked = checked;
        this.toBeRepaired = toBeRepaired;
        this.description = description;
    }

    public boolean getChecked() {
        return checked;
    }

    public boolean getToBeRepaired() {
        return toBeRepaired;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return String.format("Result: [checked: %s, to be repaired: %s] %s", checked, toBeRepaired, description);
    }
}
