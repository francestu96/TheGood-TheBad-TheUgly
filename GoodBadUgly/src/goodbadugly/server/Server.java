package goodbadugly.server;

import goodbadugly.common.Constants;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Server extends ThreadPoolExecutor {
	private ServerSocket listener;
	private ServerSocket listenerChat;
	private ArrayList<ClientConnection> clients;

	public Server() throws IOException {
		super(Constants.CORE_POOL_SIZE, Constants.MAX_POOL_SIZE,
				Constants.TIME_ALIVE, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(Constants.MAX_POOL_SIZE));

		listener = new ServerSocket(Constants.PORT_NO);
		listenerChat = new ServerSocket(Constants.PORT_NO - 1);
		listener.setSoTimeout(Constants.SERVER_TIMEOUT);
		clients = new ArrayList<ClientConnection>();
		System.out.println("Listening on port " + Constants.PORT_NO);

		runServer();
	}

	private void runServer() {
		Socket socket;
		ClientConnection connection;
		try {
			while (true) {
				socket = listener.accept();
				connection = new ClientConnection(socket, this);
				try {
					execute(connection);
				} catch (RejectedExecutionException e) {
					connection.send(Constants.FULLSRV);
					socket.close();
				}
			}
		} catch (IOException ioe) {
			try {
				listener.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println("Time to join the match expired");

			createChat();

			new GameServer(clients);
			shutdownNow();
			return;
		}
	}

	public void createChat() {
		Socket socketChat;
		ObjectInputStream inNicknameToMatch;
		ObjectOutputStream outObject;
		String nickToMatch;
		int i, clientsMatched = 0;

		while (clientsMatched < clients.size()) {
			try {
				socketChat = listenerChat.accept();
				outObject = new ObjectOutputStream(socketChat.getOutputStream());
				inNicknameToMatch = new ObjectInputStream(
						socketChat.getInputStream());
				try {
					nickToMatch = (String) inNicknameToMatch.readObject();
					for (i = 0; i < clients.size(); i++) {
						if (clients.get(i).getNickname().equals(nickToMatch)) {
							clients.get(i).setChatSoket(socketChat,
									inNicknameToMatch, outObject);
							synchronized (clients.get(i)) {
								clients.get(i).notify();
							}
						}
					}

					clientsMatched++;
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean addClient(ClientConnection client) {
		// check if the nickname is already used. If not it's added to the
		// players list, otherwise it does't
		String nickname = client.getNickname();
		boolean alreadyUsed = false;

		for (int i = 0; i < clients.size(); i++) {
			if (clients.get(i).getNickname().equals(nickname)) {
				alreadyUsed = true;
				break;
			}
		}

		if (!alreadyUsed) {
			synchronized (clients) {
				clients.add(client);
				System.out.println("Connection accepted");
				return true;
			}
		}

		System.out.println("Connection refused: nickname already used");
		return false;
	}

	public ArrayList<ClientConnection> getClients() {
		return clients;
	}

	public static void main(String[] args) {
		try {
			System.out.println("Server starting");
			new Server();
		} catch (IOException ioe) {
			System.err.println("Unable to create server socket");
		}
	}
}
