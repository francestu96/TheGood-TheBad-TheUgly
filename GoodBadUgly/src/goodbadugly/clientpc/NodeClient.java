package goodbadugly.clientpc;

import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

public class NodeClient {
	private int ammo;
	private String name;
	private Image img;
	private Image unreachImg;
	private boolean reachable;
	private NodeClient[] links;

	public NodeClient(String name, BufferedImage img) {
		this.name = name;
		this.img = img;
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorConvertOp op = new ColorConvertOp(cs, null);
		unreachImg = op.filter(img, null);
	}

	public boolean isAdiacent(NodeClient nod) {
		for (int i = 0; i < links.length; i++)
			if (links[i] == nod)
				return true;
		return false;
	}

	public Image getImage() {
		return reachable ? img : unreachImg;
	}

	public void setLinks(NodeClient[] links) {
		this.links = links;
	}

	public void setReachable(boolean reachable) {
		this.reachable = reachable;
	}

	public boolean isReachable() {
		return reachable;
	}

	public void setAmmo(int i) {
		ammo = i;
	}

	public String getName() {
		return name;
	}

	public String getAmmostr() {
		return String.valueOf(ammo);
	}
}
