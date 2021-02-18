package agency.enumeration;

public enum OutcomeStatus {

    SUCCEEDED("SUCCEEDED"),FAILED("FAILED");

    private String picked;

    OutcomeStatus(String picked) {

        this.picked = picked;

    }
}
