package http;

import utils.CommonConstants;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;

/**
 * Generic interface representing things that our server cares about
 * in order to serve requests
 */
public abstract class Request {
    private HttpMethods method;
    private boolean persistentConnection;
    private Path path;
    private String httpVersion;

    private List<String> headerFields;

    public Request(RequestLine requestLine) {
        this.headerFields = new ArrayList<>();
        this.method = requestLine.getMethod();
	this.persistentConnection = false;
        this.path = requestLine.getRequestURI();
        this.httpVersion = requestLine.getHttpVersion();
    }

    /**
     * Return the method for the request
     */
    public HttpMethods getMethod() {
        return method;
    }

    /**
     * Return the extension for the request
     */
    public String getExtension() {
        // TODO: this could be better

        Path fileName = path.getFileName();
        if (fileName == null) {
            return "";
        }
        
        String[] fileNameParts = path.getFileName().toString().split("\\.");

        if (fileNameParts.length == 2) {
            return fileNameParts[1];
        }

        return "";
    }

    /**
     * Get the MIME type for the request, based on extension
     */
    public String getMimeType() {
        return CommonConstants.EXTENSION_TO_MIME_TYPE_MAP.get(getExtension());
    }

    // Indicates whether we should leave the connection open
    public boolean isPersistent() {
	return persistentConnection;
    }

    /**
     * Return the path for the request
     */
    public Path getPath() {
        return path;
    }

    /**
     * Return the original header 
     */
    public List<String> getHeaderFields() {
        return null;
    }


    /**
     * Add to the existing field array
     */
    public void addHeaderField(String fieldLine) {
        headerFields.add(fieldLine);
	// check for connection flag
	if (fieldLine.contains("Connection:")) {
		if ((fieldLine.toLowerCase()).contains("keep-alive")) {
			persistentConnection = true;
		} else if ((fieldLine.toLowerCase()).contains("close")) {
			persistentConnection = false;
		}
	}
    }

    /**
     * Return the bytes for a response body, including the header
     * TODO: this could be way more efficient 
     */
    public abstract byte[] getResponse();

}
