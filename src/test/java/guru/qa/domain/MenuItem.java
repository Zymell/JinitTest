package guru.qa.domain;

public enum MenuItem {
    What("What"),Origin ("Origin"),Use("Use");

    public final String engName;

    MenuItem(String engName) {
        this.engName = engName;
    }
}
