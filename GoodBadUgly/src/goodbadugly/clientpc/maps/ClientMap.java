package goodbadugly.clientpc.maps;

import goodbadugly.clientpc.NodeClient;

import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class ClientMap {
	protected NodeClient[] nodes;
	protected Rectangle[] cooImgs;
	protected Point[][] cooPlayerLabel;

	public Font font;
	public int spriteWidth;
	public int spriteHeight;
	public Image background;

	public NodeClient[] getNodes() {
		return nodes;
	}

	public Point[][] getCooPlayerLabe() {
		return cooPlayerLabel;
	}

	public Rectangle[] getCooImgs() {
		return cooImgs;
	}
}
