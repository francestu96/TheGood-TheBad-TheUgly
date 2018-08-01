package goodbadugly.server;

import goodbadugly.server.maps.*;
import goodbadugly.common.Constants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashSet;

public class GameServer {
	private Iterator<ClientConnection> iterator;
	private ServerMap map;
	private ArrayList<ClientConnection> clients;
	private HashSet<ClientConnection> clientsDisconnected;
	private Renegade renegade;

	public GameServer(ArrayList<ClientConnection> clients) {
		this.clients = clients;
		clientsDisconnected = new HashSet<ClientConnection>();
		iterator = clientsDisconnected.iterator();
		renegade = new Renegade();
		init();
		startGame();

		System.out.println("Game over!");
	}

	public void init() {
		int renegadeRandomPos, i, j;

		// choose the map.getNodes() and set the players based on their number
		map = clients.size() < 6 ? new ServerMap1() : new ServerMap2();

		// set teams
		for (i = 0; i < clients.size() / 2; i++)
			clients.get(i).setTeam(Constants.BLACK);

		// set randomly the players in the map.getNodes()
		i = 0;
		while (i != clients.size())
			for (j = 0; j < map.getNodes().length; j++)
				if (map.getNodes()[j].getPlayers().size() == 0)
					if (Math.random() > 0.5) {
						map.getNodes()[j].addClientConnection(clients.get(i));
						i++;
						break;
					}

		renegadeRandomPos = (int) (Math.random() * (7));
		map.getNodes()[renegadeRandomPos].addRenegade(renegade);
		renegade.setCurrent(map.getNodes()[renegadeRandomPos]);

		sendPositions();
	}

	// communicates to the clients list argument the information of all players
	// in the map.getNodes()
	public void sendPositions() {
		int i, k, j;

		for (i = 0; i < clients.size(); i++) {
			for (k = 0; k < clients.size(); k++) {
				for (j = 0; j < map.getNodes().length; j++) {
					if (map.getNodes()[j].getPlayers().contains(clients.get(k))) {
						if (clients.get(k).getNickname()
								.equals(clients.get(i).getNickname()))
							clientsDisconnected.add(clients.get(i).send("You",
									j, clients.get(k).getAmmo(),
									clients.get(k).getTeam()));
						else
							clientsDisconnected.add(clients.get(i).send(
									clients.get(k).getNickname(), j,
									clients.get(k).getAmmo(),
									clients.get(k).getTeam()));
					}
				}
			}
			clients.get(i).send(Constants.RENEGADE,
					getIndex(renegade.getCurrent().getCity()), -1, false);
			clients.get(i).send("", -1, -1, false);
		}

		while (iterator.hasNext()) {
			ClientConnection element = (ClientConnection) iterator.next();
			if (element != null)
				removeClient(element);
		}
		clientsDisconnected.clear();

		// send the ammos
		for (j = 0; j < clients.size(); j++)
			for (k = 0; k < map.getNodes().length; k++)
				clients.get(j).send(map.getNodes()[k].getAmmo());
	}

	public void startGame() {
		boolean found;
		int answer;
		int i, j, k;
		long startTime = System.currentTimeMillis();

		while ((System.currentTimeMillis() - startTime) < 600000) {
			if (clients.size() < 1) {
				System.out.println("No player still gaming!");
				return;
			}
			for (i = 0; i < clients.size(); i++) {
				clients.get(i).send(Constants.YOURTURN);
				sendPositions();

				answer = clients.get(i).readMove();

				if (answer == Constants.DISCONNECTED) {
					removeClient(clients.get(i));
					i--;
				} else if (answer == Constants.NO_ANSWER)
					System.out.println("Clients "+ clients.get(i).getNickname() + " lost its turn!");

				else {
					moveClient(clients.get(i), answer);
					updateAmmos(clients.get(i), answer);

					if (map.getNodes()[answer].getPlayers().size() > 1) {
						found = map.getNodes()[answer].getPlayers().get(0)
								.getTeam();
						for (k = 1; k < map.getNodes()[answer].getPlayers()
								.size(); k++)
							if (map.getNodes()[answer].getPlayers().get(k)
									.getTeam() != found)
								duel(map.getNodes()[answer].getPlayers().get(0),
										map.getNodes()[answer].getPlayers()
												.get(k));
					}
				}
			}
			renegade.move();
			for (j = 0; j < renegade.getCurrent().getPlayers().size(); j++) {
				if (renegade.getCurrent().getPlayers().get(j).getAmmo() > 4)
					renegade.getCurrent()
							.getPlayers()
							.get(j)
							.setAmmo(
									renegade.getCurrent().getPlayers().get(j)
											.getAmmo() - 5);
				else
					renegade.getCurrent().getPlayers().get(j).setAmmo(0);
			}
		}
	}

	public void duel(ClientConnection player0, ClientConnection player1) {
		int movePlayer0, movePlayer1;

		player0.send(Constants.DUELYOU);
		player1.send(Constants.DUELYOU);

		for (int i = 0; i < clients.size(); i++) {
			if (clients.get(i) != player0 && clients.get(i) != player1) {
				clients.get(i).send(Constants.DUELOTH);
			}
		}
		sendPositions();

		movePlayer0 = player0.readMove();
		movePlayer1 = player1.readMove();

		if(movePlayer0==Constants.DISCONNECTED){
			player1.send(Constants.WIN);
			player1.setAmmo(player0.getAmmo() + player1.getAmmo());
			player1.send(player1.getAmmo());
			removeClient(player0);
		}
		if(movePlayer1==Constants.DISCONNECTED){
			player0.send(Constants.WIN);
			player0.setAmmo(player1.getAmmo() + player0.getAmmo());
			player0.send(player0.getAmmo());
			removeClient(player1);
		}

		if (movePlayer0 == movePlayer1) {
			player0.send(Constants.TIE);
			player1.send(Constants.TIE);
			player1.send(player1.getAmmo());
			player0.send(player0.getAmmo());
		}

		else if ((movePlayer0 == Constants.SCISSOR && movePlayer1 == Constants.PAPER)	|| (movePlayer0 == movePlayer1 - 1) || movePlayer1==Constants.NO_ANSWER){
			player0.send(Constants.WIN);
			player1.send(Constants.LOSE);
			player0.setAmmo(player0.getAmmo() + player1.getAmmo());
			player1.setAmmo(0);
			player0.send(player0.getAmmo());
			player1.send(player1.getAmmo());
		}

		else if ((movePlayer1 == Constants.SCISSOR && movePlayer0 == Constants.PAPER)	|| (movePlayer1 == movePlayer0 - 1) || movePlayer0==Constants.NO_ANSWER){
			player0.send(Constants.LOSE);
			player1.send(Constants.WIN);
			player0.setAmmo(0);
			player1.setAmmo(player0.getAmmo() + player1.getAmmo());
			player0.send(0);
			player1.send(player0.getAmmo() + player1.getAmmo());
		}

		for (int i = 0; i < clients.size(); i++)
			clients.get(i).send(Constants.DUELFINISHED);
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

	public void removeClient(ClientConnection client) {
		// remove the client from the players list and from the map.getNodes()
		System.out.println("Client " + client.getNickname()
				+ " disconnected! It's been removed from the match!");
		clients.remove(client);
		for (int j = 0; j < map.getNodes().length; j++)
			map.getNodes()[j].removeClientConnection(client);

		try {
			client.getSocket().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void moveClient(ClientConnection client, int newPos) {
		int j, k;
		boolean found = false;

		map.getNodes()[newPos].addClientConnection(client);
		for (j = 0; j < map.getNodes().length; j++) {
			for (k = 0; k < map.getNodes()[j].getPlayers().size(); k++) {
				if (j != newPos) {
					if (map.getNodes()[j].getPlayers().get(k).getNickname()
							.equals(client.getNickname())) {
						map.getNodes()[j].removeClientConnection(client);
						found = true;
						break;
					}
				}
			}
			if (found)
				break;
		}
	}

	public void updateAmmos(ClientConnection client, int newPos) {
		if (map.getNodes()[newPos].getAmmo() > 4) {
			map.getNodes()[newPos]
					.setAmmo(map.getNodes()[newPos].getAmmo() - 5);
			client.setAmmo(client.getAmmo() + 5);
		} else {
			client.setAmmo(client.getAmmo() + map.getNodes()[newPos].getAmmo());
			map.getNodes()[newPos].setAmmo(0);
		}
	}

	public int getIndex(String city) {
		for (int i = 0; i < map.getNodes().length; i++)
			if (city.equals(map.getNodes()[i].getCity()))
				return i;
		return -1;
	}

	public ArrayList<ClientConnection> getClients() {
		return clients;
	}
}
