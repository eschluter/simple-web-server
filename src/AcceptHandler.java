import java.net.*;
import javax.net.ssl.*;
import java.security.KeyStore;
import java.io.*;

/* This class creates separate threads that listen on different ports.
 * Different port numbers must be specified per socket created otherwise 
 * it'll spit an exception and exit. The constructor takes two arguments:
 * first the port number to bind to and then a boolean flag specifying
 * SSL encryption on the port.
 */

public class AcceptHandler extends Thread {

	private int portNum;
	private boolean shouldUseSSL;

	public AcceptHandler (int portNum, boolean secure) {
		this.portNum      = portNum;
		this.shouldUseSSL = secure;
	}

	public void run() {
		if (shouldUseSSL) {
			AcceptHandler.executeSSLSocket(portNum);
		} else {
			AcceptHandler.executeSocket(portNum);
		}
	}

	// creates an SSL socket to listen on
	private static void executeSSLSocket(int port) {

		System.out.println("Binding SSL port: " + Integer.toString(port));
		SSLServerSocketFactory sslFactory = null;
		try {
			SSLContext context;
			KeyManagerFactory manager;
			KeyStore store;
			char[] password = "networks".toCharArray();

			context = SSLContext.getInstance("TLS");
			manager = KeyManagerFactory.getInstance("SunX509");
			store   = KeyStore.getInstance("JKS");

			store.load(new FileInputStream("server.jks"), password);
			manager.init(store, password);
			context.init(manager.getKeyManagers(), null, null);
			
			sslFactory = context.getServerSocketFactory();
		} catch (Exception e) {
			System.err.println("System reported a problem: " + e.getMessage());
		}

		try (
			SSLServerSocket secureSocket = (SSLServerSocket) sslFactory.createServerSocket(port)
		) {
			while(true) {
				new ResponseHandler(secureSocket.accept()).start();
			}
		} catch (Exception c) {
			System.err.println("System reported a problem: " + c.getMessage());
			System.exit(1);
		}
	}

	// creates an unsecures socket to listen on
	private static void executeSocket(int port) {

		System.out.println("Binding port: " + Integer.toString(port));
		try (
			ServerSocket serverSocket = new ServerSocket(port);
		) {
			while(true) {
				new ResponseHandler(serverSocket.accept()).start();
			}
		} catch (Exception e) {
			System.err.println("System reported a problem: " + e.getMessage());
			System.exit(1);
		}
	}
}
