package se.ebikerepair.model;

import se.ebikerepair.integration.RepairOrderDTO;
import se.ebikerepair.model.RepairOrderObserver;

public abstract class AbstractRepairOrderObserver implements RepairOrderObserver {
    @Override
    public final void updateRepairOrder(RepairOrderDTO repairOrderDTO) {
        try {
            doHandleRepairOrderUpdate(repairOrderDTO);
        } catch (Exception e) {
            handleErrors(e);
        }
    }

    protected abstract void doHandleRepairOrderUpdate(RepairOrderDTO repairOrderDTO) throws Exception;

    protected abstract void handleErrors(Exception e);
}
