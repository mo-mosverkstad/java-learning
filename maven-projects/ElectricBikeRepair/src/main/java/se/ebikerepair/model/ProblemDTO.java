package se.ebikerepair.model;

import se.ebikerepair.constant.PrintoutFormat;
import se.ebikerepair.integration.BikeDTO;

/**
 * Data transfer object representing a problem reported by a customer, including the broken bike.
 */
public class ProblemDTO {
    private final String description;
    private final BikeDTO brokenBike;

    /**
     * Creates a problem DTO.
     *
     * @param description the problem description
     * @param brokenBike the bike that has the problem, or null if unknown
     */
    public ProblemDTO(String description, BikeDTO brokenBike){
        this.description = description;
        this.brokenBike = brokenBike;
    }

    /** @return the problem description */
    public String getDescription(){
        return description;
    }

    /** @return the broken bike, or null if unknown */
    public BikeDTO getBrokenBike(){
        return brokenBike;
    }

    public String toString(){
        String bike = brokenBike == null? "no bike" : String.format("%s %s (S/N: %s)", brokenBike.getBrand(), brokenBike.getModel(), brokenBike.getSerialNumber());
        return String.format(PrintoutFormat.PROBLEM_PRINTOUT_FORMAT, description, bike);
    }
}
