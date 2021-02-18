package agency.enumeration;


public enum Status {

    AVAILABLE("AVAILABLE"), EXPIRED("EXPIRED"), INCARCERATED("INCARCERATED"),
    RETIRED("RETIRED"), PLANNING("PLANNING"), READY("READY"), IN_PROGRESS("IN_PROGRESS"), FINISHED("FINISHED");


    private String picked;

    Status(String picked) {

        this.picked = picked;

    }
}
