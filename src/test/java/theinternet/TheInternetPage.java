package theinternet;

public enum TheInternetPage {
    CHECKBOXES,
    DOWNLOAD,
    DROPDOWN,
    DYNAMIC_CONTROLS,
    DYNAMIC_LOADING("dynamic_loading/1"),
    JAVASCRIPT_ALERTS,
    LOGIN,
    REDIRECTOR,
    STATUS_CODES,
    TABLES,
    CHALLENGING_DOM;

    private static final String BASE_URL = "http://the-internet.herokuapp.com/";

    private final String postfix;

    TheInternetPage() {
        this.postfix = name().toLowerCase();
    }

    TheInternetPage(String postfix) {
        this.postfix = postfix;
    }

    public String getAddress() {
        return BASE_URL.concat(postfix);
    }
}
