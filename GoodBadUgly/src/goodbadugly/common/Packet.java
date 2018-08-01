package goodbadugly.common;

import java.io.Serializable;

public class Packet implements Serializable {

	private static final long serialVersionUID = -3733619288053395991L;
	private String player;
	private int node;
	private int ammo;
	private boolean team;

	public Packet(String nick, int pos) {
		player = nick;
		node = pos;
	}

	public Packet(String nick, int pos, int ammo, boolean team) {
		player = nick;
		node = pos;
		this.ammo = ammo;
		this.team = team;
	}

	public String getPlayer() {
		return player;
	}

	public int getNode() {
		return node;
	}

	public boolean getTeam() {
		return team;
	}

	public int getAmmo() {
		return ammo;
	}
}
