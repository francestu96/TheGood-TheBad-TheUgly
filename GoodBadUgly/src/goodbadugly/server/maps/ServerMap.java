package goodbadugly.server.maps;

import goodbadugly.server.NodeServer;

public abstract class ServerMap {
	protected NodeServer[] nodes;

	public NodeServer[] getNodes() {
		return nodes;
	}
}
