package goodbadugly.server;

import java.util.ArrayList;

public class NodeServer {
	private String city;
	private int ammo;
	private ArrayList<ClientConnection> players;
	private ArrayList<NodeServer> edges;
	private Renegade renegade;

	public NodeServer(String city, int ammo) {
		edges = new ArrayList<NodeServer>();
		players = new ArrayList<ClientConnection>();
		renegade = null;
		this.ammo = ammo;
		this.city = city;
	}

	public void removeClientConnection(ClientConnection player) {
		players.remove(player);
	}

	public void addClientConnection(ClientConnection player) {
		players.add(player);
	}

	public void addEdge(NodeServer node) {
		edges.add(node);
	}

	public ArrayList<NodeServer> getEdges() {
		return edges;
	}

	public ArrayList<ClientConnection> getPlayers() {
		return players;
	}

	public String getCity() {
		return city;
	}

	public int getAmmo() {
		return ammo;
	}

	public Renegade getRenegade() {
		return renegade;
	}

	public void addRenegade(Renegade r) {
		renegade = r;
	}

	public void removeRenegade() {
		renegade = null;
	}

	public void setAmmo(int newAmmo) {
		ammo = newAmmo;
	}
}
