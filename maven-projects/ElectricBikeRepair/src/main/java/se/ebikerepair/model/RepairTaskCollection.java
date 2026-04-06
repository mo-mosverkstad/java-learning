package se.ebikerepair.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import se.ebikerepair.integration.RepairTaskCollectionDTO;
import se.ebikerepair.integration.RepairTaskDTO;

/**
 * Encapsulates a collection of proposed repair tasks.
 */
public class RepairTaskCollection {
    private final List<RepairTask> repairTasks;

    /**
     * Creates an empty repair task collection.
     */
    public RepairTaskCollection() {
        this.repairTasks = new ArrayList<>();
    }

    /**
     * Adds a repair task to the collection.
     *
     * @param repairTask the repair task to add
     */
    void addRepairTask(RepairTaskDTO repairTaskDTO) {
        RepairTask repairTask = new RepairTask(repairTaskDTO.name(), repairTaskDTO.description(), repairTaskDTO.cost(),
                repairTaskDTO.estimatedDays());
        repairTasks.add(repairTask);
    }

    /**
     * Returns the total estimated days across all repair tasks.
     *
     * @return the total estimated days
     */
    int getTotalDays() {
        return repairTasks.stream().mapToInt(RepairTask::getEstimatedDays).sum();
    }

    /**
     * Returns the total cost accumulated from all repair tasks.
     *
     * @return the total cost
     */
    Cost getTotalCost() {
        Cost total = new Cost();
        for (RepairTask task : repairTasks) {
            total.calculate(task.getCost());
        }
        return total;
    }

    /**
     * Converts this collection to an immutable DTO with deep-copied tasks.
     *
     * @return a RepairTaskCollectionDTO snapshot of this collection
     */
    public RepairTaskCollectionDTO toDTO() {
        return new RepairTaskCollectionDTO(
            getTotalDays(),
            getTotalCost(),
            repairTasks.stream().map(RepairTask::toDTO).collect(Collectors.toList())
        );
    }
}
