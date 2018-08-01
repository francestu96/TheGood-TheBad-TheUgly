package goodbadugly.server;

import goodbadugly.common.Packet;
import goodbadugly.common.Constants;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.EOFException;
import java.net.Socket;
import java.net.SocketException;

public class ClientConnection implements Runnable {
	private int ammo;
	private boolean team;
	private String nickname;

	private volatile ObjectOutputStream outObject;
	private volatile ObjectInputStream inObject;
	private volatile Server server;
	private volatile Socket socket;
	private volatile Socket socketChat;

	private volatile ObjectOutputStream outObjectChat;
	private volatile ObjectInputStream inObjectChat;

	public ClientConnection() {
	}

	public ClientConnection(Socket s, Server srv) throws IOException {
		ammo = 10 + (int) (Math.random() * ((50 - 10) + 1));
		team = Constants.WHITE;
		socket = s;
		server = srv;
		outObject = new ObjectOutputStream(s.getOutputStream());
		inObject = new ObjectInputStream(s.getInputStream());
	}

	@Override
	public void run() {
		String message, sendingNick;
		int i = 0;

		try {
			setNickname((String) inObject.readObject());
		} catch (IOException ioe) {
			System.err.println("I/O error: " + ioe.getMessage());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (!server.addClient(this)) {
			send(Constants.USEDNICK);
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}

		send(Constants.ACCEPTED);

		synchronized (this) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		while (true) {
			try {
				sendingNick = (String) inObjectChat.readObject();
				message = (String) inObjectChat.readObject();

				for (i = 0; i < server.getClients().size(); i++) {
					if (server.getClients().get(i).getTeam() == team
							&& server.getClients().get(i) != this) {
						server.getClients()
								.get(i)
								.sendChatMessage("<" + sendingNick + ">:  " + message + "\n");
					}
				}
			} catch (EOFException e) {
				try {
					socket.close();
					socketChat.close();
				}catch(IOException ex){ex.printStackTrace();}
				return;
			} catch (SocketException e) {
				try {
					socket.close();
					socketChat.close();
				}catch(IOException ex){ex.printStackTrace();}
				return;
			} catch (IOException e) {
				return;
			} catch (ClassNotFoundException e) {
				return;
			}
		}
	}

	public int readMove() {
		int indexPosition = 0;

		try {
			socket.setSoTimeout(Constants.TURN_TIMEOUT);

			indexPosition = (int) inObject.readObject();
		} catch (EOFException f) {
			return Constants.DISCONNECTED;
		} catch (SocketException s) {
			return Constants.DISCONNECTED;
		} catch (IOException e) {
			return Constants.NO_ANSWER;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return indexPosition;
	}

	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}

	public void setTeam(boolean team) {
		this.team = team;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getAmmo() {
		return ammo;
	}

	public boolean getTeam() {
		return team;
	}

	public String getNickname() {
		return nickname;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setChatSoket(Socket s, ObjectInputStream in,
			ObjectOutputStream out) throws IOException {
		socketChat=s;
		outObjectChat = out;
		inObjectChat = in;
	}

	public ObjectInputStream getStream() {
		return inObjectChat;
	}

	public void send(int message) {
		try {
			outObject.flush();
			outObject.writeObject(message);
		} catch (IOException e) {
		}
	}

	public ClientConnection send(String str, int pos, int ammo, boolean team) {
		try {
			Packet pack = new Packet(str, pos, ammo, team);
			outObject.writeObject(pack);
		} catch (SocketException e) {
			return this;
		} catch (IOException e) {
			return this;
		}
		return null;
	}

	public void sendChatMessage(String message) {
		try {
			outObjectChat.flush();
			outObjectChat.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
