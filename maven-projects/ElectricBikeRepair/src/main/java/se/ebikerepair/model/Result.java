package se.ebikerepair.model;

public class Result {
    private boolean checked;
    private boolean toBeRepaired;
    private String description;

    public Result(boolean checked, boolean toBeRepaired, String description){
        this.checked = checked;
        this.toBeRepaired = toBeRepaired;
        this.description = description;
    }

    public Result(){
        this(false, false, "have not checked yet");
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

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setToBeRepaired(boolean toBeRepaired) {
        this.toBeRepaired = toBeRepaired;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void update(ResultDTO result) {
        this.checked = result.getChecked();
        this.toBeRepaired = result.getToBeRepaired();
        this.description = result.getDescription();
    }

    public String toString() {
        return String.format("Result: [checked: %s, to be repaired: %s] %s", checked, toBeRepaired, description);
    }
}
