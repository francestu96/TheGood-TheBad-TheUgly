package goodbadugly.clientpc.maps;

import goodbadugly.clientpc.NodeClient;

import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ClientMap2 extends ClientMap {
	public ClientMap2() {
		nodes = new NodeClient[10];
		cooImgs = new Rectangle[10];
		cooPlayerLabel = new Point[10][4];

		spriteWidth = 30;
		spriteHeight = 45;
		font = new Font("Arial", Font.BOLD, 16);
		try {
			background = ImageIO.read(new File("bin/images/maps/mappa2.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			nodes[0] = new NodeClient("bodie", ImageIO.read(new File(
					"bin/images/cities/bodie.png")));
			cooImgs[0] = new Rectangle(60, 310, 142, 123);
			nodes[1] = new NodeClient("bannack", ImageIO.read(new File(
					"bin/images/cities/bannack.png")));
			cooImgs[1] = new Rectangle(100, 130, 130, 110);
			nodes[2] = new NodeClient("calico", ImageIO.read(new File(
					"bin/images/cities/calico.png")));
			cooImgs[2] = new Rectangle(720, 195, 170, 118);
			nodes[3] = new NodeClient("santafe", ImageIO.read(new File(
					"bin/images/cities/santafe.png")));
			cooImgs[3] = new Rectangle(950, 50, 200, 100);
			nodes[4] = new NodeClient("tucson", ImageIO.read(new File(
					"bin/images/cities/tucson.png")));
			cooImgs[4] = new Rectangle(975, 540, 156, 96);
			nodes[5] = new NodeClient("goldmine", ImageIO.read(new File(
					"bin/images/cities/goldmine.png")));
			cooImgs[5] = new Rectangle(150, 560, 228, 90);
			nodes[6] = new NodeClient("tombstone", ImageIO.read(new File(
					"bin/images/cities/tombstone.png")));
			cooImgs[6] = new Rectangle(635, 530, 206, 94);
			nodes[7] = new NodeClient("armadillo", ImageIO.read(new File(
					"bin/images/cities/armadillo.png")));
			cooImgs[7] = new Rectangle(1025, 280, 180, 105);
			nodes[8] = new NodeClient("blackwater", ImageIO.read(new File(
					"bin/images/cities/blackwater.png")));
			cooImgs[8] = new Rectangle(405, 50, 162, 95);
			nodes[9] = new NodeClient("gaptooth", ImageIO.read(new File(
					"bin/images/cities/gaptooth.png")));
			cooImgs[9] = new Rectangle(360, 300, 176, 100);

		} catch (IOException e) {
			e.printStackTrace();
		}

		// bodie
		cooPlayerLabel[0][0] = new Point(14, 335);
		cooPlayerLabel[0][1] = new Point(168, 350);
		cooPlayerLabel[0][2] = new Point(10, 456);
		cooPlayerLabel[0][3] = new Point(214, 435);
		// bannack
		cooPlayerLabel[1][0] = new Point(30, 191);
		cooPlayerLabel[1][1] = new Point(72, 126);
		cooPlayerLabel[1][2] = new Point(190, 121);
		cooPlayerLabel[1][3] = new Point(267, 220);
		// calico
		cooPlayerLabel[2][0] = new Point(680, 237);
		cooPlayerLabel[2][1] = new Point(824, 235);
		cooPlayerLabel[2][2] = new Point(670, 342);
		cooPlayerLabel[2][3] = new Point(870, 359);
		// santafe
		cooPlayerLabel[3][0] = new Point(890, 147);
		cooPlayerLabel[3][1] = new Point(940, 62);
		cooPlayerLabel[3][2] = new Point(1080, 69);
		cooPlayerLabel[3][3] = new Point(1131, 220);
		// tucson
		cooPlayerLabel[4][0] = new Point(1020, 530);
		cooPlayerLabel[4][1] = new Point(1100, 540);
		cooPlayerLabel[4][2] = new Point(1140, 660);
		cooPlayerLabel[4][3] = new Point(919, 650);
		// goldmine
		cooPlayerLabel[5][0] = new Point(83, 629);
		cooPlayerLabel[5][1] = new Point(196, 556);
		cooPlayerLabel[5][2] = new Point(329, 552);
		cooPlayerLabel[5][3] = new Point(391, 632);
		// tombstone
		cooPlayerLabel[6][0] = new Point(669, 534);
		cooPlayerLabel[6][1] = new Point(571, 634);
		cooPlayerLabel[6][2] = new Point(811, 560);
		cooPlayerLabel[6][3] = new Point(824, 670);
		// armadillo
		cooPlayerLabel[7][0] = new Point(970, 370);
		cooPlayerLabel[7][1] = new Point(1052, 274);
		cooPlayerLabel[7][2] = new Point(1150, 280);
		cooPlayerLabel[7][3] = new Point(966, 443);
		// blackwater
		cooPlayerLabel[8][0] = new Point(346, 129);
		cooPlayerLabel[8][1] = new Point(437, 235);
		cooPlayerLabel[8][2] = new Point(585, 68);
		cooPlayerLabel[8][3] = new Point(594, 205);
		// gaptooth
		cooPlayerLabel[9][0] = new Point(549, 403);
		cooPlayerLabel[9][1] = new Point(497, 307);
		cooPlayerLabel[9][2] = new Point(392, 311);
		cooPlayerLabel[9][3] = new Point(307, 385);

		nodes[0].setLinks(new NodeClient[] { nodes[5], nodes[9] });
		nodes[1].setLinks(new NodeClient[] { nodes[9], nodes[8] });
		nodes[2].setLinks(new NodeClient[] { nodes[8], nodes[5], nodes[4],
				nodes[7], nodes[9] });
		nodes[3].setLinks(new NodeClient[] { nodes[8] });
		nodes[4].setLinks(new NodeClient[] { nodes[2], nodes[6] });
		nodes[5].setLinks(new NodeClient[] { nodes[0], nodes[2], nodes[6] });
		nodes[6].setLinks(new NodeClient[] { nodes[5], nodes[4] });
		nodes[7].setLinks(new NodeClient[] { nodes[2] });
		nodes[8].setLinks(new NodeClient[] { nodes[1], nodes[2], nodes[3] });
		nodes[9].setLinks(new NodeClient[] { nodes[0], nodes[1], nodes[2] });
	}
}
