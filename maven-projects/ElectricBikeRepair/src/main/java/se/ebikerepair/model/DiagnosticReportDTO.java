package se.ebikerepair.model;


public class DiagnosticReportDTO {
    private final String description;

    public DiagnosticReportDTO(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    @Override
    public String toString(){
        return String.format("DiagnosticReportDTO: %s", description);
    }
}
