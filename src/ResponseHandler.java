import java.net.*;
import java.io.*;
import http.*;


/**
 * TODO : this should probably be renamed...now thinking of it 
 * more as a generic request handler/coordinator that will be responsible
 * for orchestrating our objects to take a request
 * and finally spit out a response
 */
public class ResponseHandler extends Thread {
    private Socket socket;
    private boolean persist = false;

    public ResponseHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStream out = socket.getOutputStream();) {
		do {
		    Request request = Requests.getRequest(in);

		    if (request == null) { break; }

                    Requests.sendResponse(request, out);
                
		    persist = request.isPersistent();
		} while (persist);
		socket.close();
            } catch(IOException e) {
            System.err.println("System reported error: " + e.getMessage());
        }
    }
}
