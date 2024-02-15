package w3schools;

public enum W3SchoolsPage {
    SELECT_MULTIPLE("tryhtml_select_multiple");

    private static final String BASE_URL = "https://www.w3schools.com/tags/tryit.asp";

    private final String postfix;

    W3SchoolsPage(String postfix) {
        this.postfix = postfix;
    }

    public String getAddress() {
        return BASE_URL.concat(String.format("?filename=%s", postfix));
    }
}
