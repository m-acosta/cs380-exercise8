import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Michael Acosta
 */
public class WebServer {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {
			ServerSocket mySocket = new ServerSocket(8080);
			while (true) {
				Socket connection = mySocket.accept();
				BufferedReader input = new BufferedReader(
						new InputStreamReader(connection.getInputStream()));
				String userRequest = "";
				String temp = input.readLine();
				while (!temp.isEmpty()) {
					userRequest += temp;
					temp = input.readLine();
				}
				PrintWriter output = new PrintWriter(
						connection.getOutputStream(), true);
				if (userRequest.contains("hello.html")) {
					String serverResponse =  "<html>\n" +
						"<head>\n" +
						"<title>Hello, World!</title>\n" +
						"</head>\n" +
						"<body>\n" +
						"<p>Hello, World!</p>\n"+
						"</body>\n" +
						"<html>\n";
					output.println("HTTP/1.1 200 OK" +
						"Content-type: text/html" +
						"Content-length: " + serverResponse.length());
					output.println("");
					output.println(serverResponse);
					output.close();
				} else {
					String serverResponse =  "<html>\n" +
						"<head>\n" +
						"<title>Not Found</title>\n" +
						"</head>\n" +
						"<body>Sorry, the object you" +
						"requested was not found.</body>\n" +
						"<html>\n";
					output.println("HTTP/1.1 404 Not Found" +
						"Content-type: text/html" +
						"Content-length: " + serverResponse.length());
					output.println("");
					output.println(serverResponse);
					output.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}