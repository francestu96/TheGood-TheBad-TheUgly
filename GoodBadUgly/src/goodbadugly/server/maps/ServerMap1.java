package goodbadugly.server.maps;

import goodbadugly.server.NodeServer;

public class ServerMap1 extends ServerMap {
	public ServerMap1() {
		nodes = new NodeServer[7];

		nodes[0] = new NodeServer("Bodie", 10 + (int) (Math.random() * 51));
		nodes[1] = new NodeServer("Bannack", 10 + (int) (Math.random() * 51));
		nodes[2] = new NodeServer("Calico", 10 + (int) (Math.random() * 51));
		nodes[3] = new NodeServer("Satafe", 10 + (int) (Math.random() * 51));
		nodes[4] = new NodeServer("Tucson", 10 + (int) (Math.random() * 51));
		nodes[5] = new NodeServer("Goldimine", 10 + (int) (Math.random() * 51));
		nodes[6] = new NodeServer("Tombstone", 10 + (int) (Math.random() * 51));
		nodes[0].addEdge(nodes[2]);
		nodes[0].addEdge(nodes[5]);
		nodes[1].addEdge(nodes[0]);
		nodes[2].addEdge(nodes[4]);
		nodes[2].addEdge(nodes[0]);
		nodes[2].addEdge(nodes[3]);
		nodes[3].addEdge(nodes[2]);
		nodes[4].addEdge(nodes[2]);
		nodes[4].addEdge(nodes[5]);
		nodes[5].addEdge(nodes[0]);
		nodes[5].addEdge(nodes[6]);
		nodes[5].addEdge(nodes[4]);
		nodes[6].addEdge(nodes[5]);
	}
}
