package agency.enumeration;

public enum Sex {

    M("Male"), F("Female");

    private String name;


    Sex(String name) {
        this.name = name;

    }

    public String getName() {
        return name;
    }

}
