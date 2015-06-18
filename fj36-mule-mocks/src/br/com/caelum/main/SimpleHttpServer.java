package br.com.caelum.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class SimpleHttpServer {

	private int porta = 8089;

	public SimpleHttpServer(int porta) {
		this.porta = porta;
	}

	public void roda() {
		try (ServerSocket serverSocket = new ServerSocket(this.porta)) {
			System.out.println("*** HttpServer rodando ");
			System.out.println("http://localhost:" + this.porta + "/mule/ebook");
			System.out.println("http://localhost:" + this.porta + "/mule/financeiro");

			while (true) {
				Socket socket = serverSocket.accept();
//				System.out.println("Nova conexão confirmada "); // socket.getInetAddress()
																// + ":" +
																// socket.getPort());

				HttpRequestHandler request = new HttpRequestHandler(socket);
				Thread thread = new Thread(request);
				thread.start();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}

class HttpRequestHandler implements Runnable {

	private static final String CRLF = "\r\n";
	private final String statusLine = "HTTP/1.0 200 OK";
	private final String serverLine = "Server: Simple Java Http Server";
	private final String RESPOSTA = "<resposta>ok</resposta>";

	private PrintWriter out;
	private BufferedReader reader;
	private Socket socket;

	public HttpRequestHandler(Socket socket) throws IOException {
		this.socket = socket;
		this.out = new PrintWriter(socket.getOutputStream());
		this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	public void run() {
		try {
			processRequest();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void processRequest() throws Exception {
		while (true) {

			String headerLine = reader.readLine();
			if (headerLine == null || headerLine.equals(CRLF) || headerLine.equals(""))
				break;

			StringTokenizer s = new StringTokenizer(headerLine);
			String method = s.nextToken();

			if (!(method.equals("GET") || method.equals("POST")))
				break;

			String path = s.nextToken();
			
			if (!(path.equals("/mule/ebook") || path.equals("/mule/financeiro")))
				break;
			
			out.println(statusLine);
			out.println(serverLine);
			out.println();
			out.println(RESPOSTA);
			out.println();

			System.out.println(method + " - " + path + " - " + statusLine);
		}

		out.close();
		reader.close();
		socket.close();
	}

}