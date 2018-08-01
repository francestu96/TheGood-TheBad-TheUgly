package goodbadugly.server;

import goodbadugly.common.Constants;

public class Renegade {
	private String nickname;
	private NodeServer current;

	public Renegade() {
		nickname = Constants.RENEGADE;
	}

	public String getNickname() {
		return nickname;
	}

	public void setCurrent(NodeServer node) {
		current = node;
	}

	public NodeServer getCurrent() {
		return current;
	}

	public void move() {
		int random = (int) (Math.random() * (current.getEdges().size()));

		current.getEdges().get(random).addRenegade(this);
		setCurrent(current.getEdges().get(random));
	}
}
