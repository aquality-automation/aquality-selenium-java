package manytools;

public class UserAgentForm extends ManyToolsForm<UserAgentForm> {
    public UserAgentForm() {
        super("User agent");
    }

    @Override
    protected String getUrlPart() {
        return "http-html-text/user-agent-string/";
    }
}
