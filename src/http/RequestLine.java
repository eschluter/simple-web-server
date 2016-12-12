package http;

import utils.CommonConstants;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Represents the request line in any HTTP request.
 * RequestLine name and fields reflect HTTP spec terminology.
 */
public class RequestLine {
    private HttpMethods method;
    private Path requestURI;
    private String httpVersion;

    public RequestLine(String request) {
        String[] requestSegments = request.split("\\s+");

        if (requestSegments.length != CommonConstants.REQUEST_PIECES_COUNT) {
            System.err.println("Cannot interpret invalid request line");
        }

        String methodString = requestSegments[0].trim();
        if (methodString.equals(HttpMethods.GET.toString())) {
            method = HttpMethods.GET;
        } else if (methodString.equals(HttpMethods.HEAD.toString())) {
            method = HttpMethods.HEAD;
        } else {
            method = HttpMethods.UNRECOGNIZED;
        }

        requestURI = Paths.get(requestSegments[1].trim());
        httpVersion = requestSegments[2].trim();
    }

    public HttpMethods getMethod() {
        return method;
    }

    public Path getRequestURI() {
        return requestURI;
    }

    public String getHttpVersion() {
        return httpVersion;
    }
                                    
}
