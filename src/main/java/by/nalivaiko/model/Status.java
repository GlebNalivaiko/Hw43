package by.nalivaiko.model;

public enum Status {
    ARRIVED("The order has arrived!"),
    PACKING("The order is being packed!"),
    BEIJING("Shipped from Beijing!"),
    EUROPE("Somewhere in Europe!"),
    NEARBY("It's about to arrive");

    private String title;

    Status(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "DayOfWeek{" +
                "title='" + title + '\'' +
                '}';
    }
}
