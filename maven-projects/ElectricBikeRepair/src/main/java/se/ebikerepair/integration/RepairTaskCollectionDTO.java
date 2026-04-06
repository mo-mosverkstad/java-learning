package se.ebikerepair.integration;

import java.util.List;
import java.util.stream.Collectors;

import se.ebikerepair.constant.PrintoutFormat;
import se.ebikerepair.model.Cost;

/**
 * Immutable data transfer object representing a collection of proposed repair tasks.
 *
 * @param repairTasks the list of repair task DTOs
 */
public record RepairTaskCollectionDTO(int days, Cost cost, List<RepairTaskDTO> repairTasks) {

    @Override
    public String toString() {
        if (repairTasks == null || repairTasks.isEmpty()) {
            return "      (none)\n";
        }
        return String.format(PrintoutFormat.REPAIR_TASK_COLLECTION_PRINTOUT_FORMAT, days, cost, repairTasks.stream()
                .map(RepairTaskDTO::toString)
                .collect(Collectors.joining()));
    }
}
