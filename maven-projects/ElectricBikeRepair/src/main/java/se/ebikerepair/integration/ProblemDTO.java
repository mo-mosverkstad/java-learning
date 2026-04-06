package se.ebikerepair.integration;

import se.ebikerepair.constant.PrintoutFormat;

/**
 * Immutable data transfer object representing a problem reported by a customer, including the broken bike.
 *
 * @param description the problem description
 * @param brokenBike the bike that has the problem, or null if unknown
 */
public record ProblemDTO(String description, BikeDTO brokenBike) {

    @Override
    public String toString() {
        String bike = brokenBike == null ? "no bike" : String.format("%s %s (S/N: %s)", brokenBike.brand(), brokenBike.model(), brokenBike.serialNumber());
        return String.format(PrintoutFormat.PROBLEM_PRINTOUT_FORMAT, description, bike);
    }
}
