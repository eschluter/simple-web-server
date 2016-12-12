package http;

import java.io.*;
import java.net.Socket;

/**
 * Class with static methods to examine a request and
 * return a corresponding Request or Response
 */
public class Requests {
    public static Request getRequest(BufferedReader in) {
        String inputLine;
        Request request = null;
        
        try {
	    // If connection is persistent we hang until another request is sent
	    // or the socket disconnects (from timeout, browser close...)
	    inputLine = in.readLine();

            if (inputLine == null) { return request; }

            // Read the request line into an object
            RequestLine requestLine = new RequestLine(inputLine.trim());

            switch(requestLine.getMethod()) {
            case GET:
                request = new GETRequest(requestLine);
                break;
            case HEAD:
                request = new HEADRequest(requestLine);
                break;
            default:
                System.out.println("sending bad request response");
                request = new BADRequest(requestLine);
            }

            // Now read all the lines into the request's
            // header fields array
            while (!"".equals((inputLine = in.readLine()))) {
                request.addHeaderField(inputLine);
            }

        } catch (IOException e) {
            System.err.println("System error while reading: " + e.getMessage());

        }

        return request;
    }

    /** 
     * Send appropriate response for the request onto the 
     * output stream.
     */
    public static void sendResponse(Request request, OutputStream out) {
        try{
            byte[] response = request.getResponse();
            if (response != null) {
                out.write(request.getResponse());
            }
        } catch (IOException e) {
            System.err.println("Could not write response: " + e.getMessage());
        }
    }

}
