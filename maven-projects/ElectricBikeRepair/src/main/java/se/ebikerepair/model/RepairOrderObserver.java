package se.ebikerepair.model;

import se.ebikerepair.integration.RepairOrderDTO;

public interface RepairOrderObserver {
    public void updateRepairOrder(RepairOrderDTO repairOrderDTO);
}
