package http;

import utils.CommonConstants;

/**
 * An enum to represent the response dispositions available in the server
 * along with some associated convenience methods
 */
public enum ResponseDispositions {
    OK("200", "OK"),
    MOVED_PERMANENTLY("301", "Moved Permanently"),
    NOT_FOUND("404", "Not Found"),
    FORBIDDEN("403", "Forbidden");

    private String responseCode;
    private String friendlyMessage;

    ResponseDispositions(String responseCode, String friendlyMessage) {
        this.responseCode = responseCode;
        this.friendlyMessage = friendlyMessage;
    }

    public String getDispositionRepresentation() {
        return CommonConstants.HTTP_VERSION + " " + this.responseCode + " " + this.friendlyMessage;
    }
}