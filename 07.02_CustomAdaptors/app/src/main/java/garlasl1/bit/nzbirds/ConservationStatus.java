package garlasl1.bit.nzbirds;

/**
 * Created by GARLA on 21/05/2018.
 */

public enum ConservationStatus{
    N_ENDANGERED("Nationally Endangered"),
    N_CRITICAL("Nationally Critical"),
    N_VUNERABLE("Nationally Vunerable"),
    RECOVERING("Recovering");

    private String status;

    ConservationStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}