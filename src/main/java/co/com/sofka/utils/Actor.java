package co.com.sofka.utils;

public enum Actor {
    ACTOR_NAME("Student");

    private final String value;

    Actor(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
