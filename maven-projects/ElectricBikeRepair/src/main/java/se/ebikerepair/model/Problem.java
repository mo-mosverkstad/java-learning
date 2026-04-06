package se.ebikerepair.model;

import se.ebikerepair.integration.BikeDTO;
import se.ebikerepair.integration.ProblemDTO;
import se.ebikerepair.constant.PrintoutFormat;

/**
 * Mutable entity representing a problem reported by a customer, including the broken bike.
 */
public class Problem {
    private String description;
    private BikeDTO brokenBike;

    /**
     * Creates a problem entity.
     *
     * @param description the problem description
     * @param brokenBike the bike that has the problem, or null if unknown
     */
    public Problem(String description, BikeDTO brokenBike){
        this.description = description;
        this.brokenBike = brokenBike;
    }

    /**
     * Creates a default problem with no description and no bike.
     */
    public Problem(){
        this(null, null);
    }

    /**
     * Sets the problem description.
     *
     * @param description the problem description
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * Returns the problem description.
     *
     * @return the problem description
     */
    public String getDescription(){
        return description;
    }

    /**
     * Sets the broken bike associated with this problem.
     *
     * @param brokenBike the bike that has the problem, or null if unknown
     */
    public void setBrokenBike(BikeDTO brokenBike){
        this.brokenBike = brokenBike;
    }

    /**
     * Returns the broken bike associated with this problem.
     *
     * @return the broken bike, or null if unknown
     */
    public BikeDTO getBrokenBike(){
        return brokenBike;
    }

    /**
     * Updates this problem with values from a ProblemDTO.
     *
     * @param problemDTO the DTO containing the new values
     */
    protected void update(ProblemDTO problemDTO) {
        this.description = problemDTO.description();
        this.brokenBike = problemDTO.brokenBike();
    }

    /**
     * Converts this problem to an immutable DTO.
     *
     * @return a ProblemDTO snapshot of this problem
     */
    public ProblemDTO toDTO() {
        return new ProblemDTO(description, brokenBike);
    }
}
