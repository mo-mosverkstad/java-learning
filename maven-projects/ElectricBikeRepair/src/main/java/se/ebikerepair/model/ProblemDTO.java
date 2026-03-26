package se.ebikerepair.model;

import se.ebikerepair.constant.PrintoutFormat;
import se.ebikerepair.integration.BikeDTO;

public class ProblemDTO {
    private final String description;
    private final BikeDTO brokenBike;

    public ProblemDTO(String description, BikeDTO brokenBike){
        this.description = description;
        this.brokenBike = brokenBike;
    }

    public String getDescription(){
        return description;
    }

    public BikeDTO getBrokenBike(){
        return brokenBike;
    }

    public String toString(){
        return String.format(PrintoutFormat.PROBLEM_PRINTOUT_FORMAT, description, brokenBike);
    }
}
