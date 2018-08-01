package goodbadugly.clientpc;

import goodbadugly.common.Constants;
import goodbadugly.common.Packet;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.net.Socket;
import java.net.ConnectException;
import java.lang.ClassNotFoundException;

public class MyConnection {
	private Socket socket;
	private ObjectOutputStream outObject;
	private ObjectInputStream inObject;

	public int connect(int port) {
		try {
			socket = new Socket(Constants.SERVER_ADDR, port);
			outObject = new ObjectOutputStream(socket.getOutputStream());
			inObject = new ObjectInputStream(socket.getInputStream());
		} catch (ConnectException e) {
			return Constants.SERVER_UNKNOWN;
		} catch (UnknownHostException e) {
			return Constants.SERVER_UNKNOWN;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Constants.SUCCESSFULLY_CONNECT;
	}

	public void write(int i) throws IOException {
		outObject.writeObject(i);
	}

	public void write(String s) throws IOException {
		outObject.writeObject(s);
	}

	public void write(Packet p) throws IOException {
		outObject.writeObject(p);
	}

	public Object read() throws IOException, ClassNotFoundException {
		return inObject.readObject();
	}

	public int readInt() throws IOException, ClassNotFoundException {
		return (int) inObject.readObject();
	}

	public Packet readPacket() throws IOException, ClassNotFoundException {
		return (Packet) inObject.readObject();
	}
}
