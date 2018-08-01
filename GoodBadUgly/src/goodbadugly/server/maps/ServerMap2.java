package goodbadugly.server.maps;

import goodbadugly.server.NodeServer;

public class ServerMap2 extends ServerMap {
	public ServerMap2() {
		nodes = new NodeServer[10];

		nodes[0] = new NodeServer("Bodie", 10 + (int) (Math.random() * 51));
		nodes[1] = new NodeServer("Bannack", 10 + (int) (Math.random() * 51));
		nodes[2] = new NodeServer("Calico", 10 + (int) (Math.random() * 51));
		nodes[3] = new NodeServer("Satafe", 10 + (int) (Math.random() * 51));
		nodes[4] = new NodeServer("Tucson", 10 + (int) (Math.random() * 51));
		nodes[5] = new NodeServer("Goldimine", 10 + (int) (Math.random() * 51));
		nodes[6] = new NodeServer("Tombstone", 10 + (int) (Math.random() * 51));
		nodes[7] = new NodeServer("Armadillo", 10 + (int) (Math.random() * 51));
		nodes[8] = new NodeServer("Blackwater", 10 + (int) (Math.random() * 51));
		nodes[9] = new NodeServer("Gaptooth", 10 + (int) (Math.random() * 51));

		nodes[0].addEdge(nodes[9]);
		nodes[0].addEdge(nodes[5]);
		nodes[1].addEdge(nodes[9]);
		nodes[1].addEdge(nodes[8]);
		nodes[2].addEdge(nodes[8]);
		nodes[2].addEdge(nodes[5]);
		nodes[2].addEdge(nodes[4]);
		nodes[2].addEdge(nodes[7]);
		nodes[2].addEdge(nodes[9]);
		nodes[3].addEdge(nodes[8]);
		nodes[4].addEdge(nodes[2]);
		nodes[4].addEdge(nodes[6]);
		nodes[5].addEdge(nodes[0]);
		nodes[5].addEdge(nodes[2]);
		nodes[5].addEdge(nodes[6]);
		nodes[6].addEdge(nodes[5]);
		nodes[6].addEdge(nodes[4]);
		nodes[7].addEdge(nodes[2]);
		nodes[8].addEdge(nodes[1]);
		nodes[8].addEdge(nodes[2]);
		nodes[8].addEdge(nodes[3]);
		nodes[9].addEdge(nodes[0]);
		nodes[9].addEdge(nodes[1]);
		nodes[9].addEdge(nodes[2]);
	}
}
