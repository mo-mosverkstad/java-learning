package se.ebikerepair.model;

import se.ebikerepair.integration.RepairOrderDTO;
import se.ebikerepair.model.RepairOrderObserver;

public abstract class AbstractRepairOrderObserver implements RepairOrderObserver {
    private RepairOrderDTO repairOrderDTO;

    @Override
    public final void updateRepairOrder(RepairOrderDTO repairOrderDTO) {
        this.repairOrderDTO = repairOrderDTO;
        handleRepairOrderUpdate();
    }

    private void handleRepairOrderUpdate() {
        try {
            doHandleRepairOrderUpdate();
        } catch (Exception e) {
            handleErrors(e);
        }
    }

    protected RepairOrderDTO getRepairOrderDTO() {
        return repairOrderDTO;
    }

    protected abstract void doHandleRepairOrderUpdate() throws Exception;

    protected abstract void handleErrors(Exception e);
}
