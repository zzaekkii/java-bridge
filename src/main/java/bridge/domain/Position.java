package bridge.domain;

public enum Position {
    UP("U"),
    DOWN("D");

    private final String value;

    Position(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
