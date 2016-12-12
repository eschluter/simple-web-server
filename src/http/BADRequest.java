package http;

import utils.CommonConstants;
import http.ResponseDispositions;

public class BADRequest extends Request {
    public BADRequest(RequestLine requestLine) {
        super(requestLine);
    }

    public byte[] getResponse() {
	    return  (ResponseDispositions.FORBIDDEN.getDispositionRepresentation() + CommonConstants.CRLF).getBytes();
    }
}
