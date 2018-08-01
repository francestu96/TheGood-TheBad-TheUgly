package goodbadugly.clientpc.maps;

import goodbadugly.clientpc.NodeClient;

import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ClientMap1 extends ClientMap {

	public ClientMap1() {
		nodes = new NodeClient[7];
		cooImgs = new Rectangle[7];
		cooPlayerLabel = new Point[7][4];
		spriteWidth = 60;
		spriteHeight = 75;
		font = new Font("Arial", Font.BOLD, 18);
		try {
			background = ImageIO.read(new File("bin/images/maps/mappa1.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			nodes[0] = new NodeClient("bodie", ImageIO.read(new File(
					"bin/images/cities/bodie.png")));
			cooImgs[0] = new Rectangle(500, 130, 150, 120);
			nodes[1] = new NodeClient("bannack", ImageIO.read(new File(
					"bin/images/cities/bannack.png")));
			cooImgs[1] = new Rectangle(70, 50, 160, 120);
			nodes[2] = new NodeClient("calico", ImageIO.read(new File(
					"bin/images/cities/calico.png")));
			cooImgs[2] = new Rectangle(1000, 65, 163, 120);
			nodes[3] = new NodeClient("santafe", ImageIO.read(new File(
					"bin/images/cities/santafe.png")));
			cooImgs[3] = new Rectangle(770, 300, 200, 120);
			nodes[4] = new NodeClient("tucson", ImageIO.read(new File(
					"bin/images/cities/tucson.png")));
			cooImgs[4] = new Rectangle(995, 537, 150, 120);
			nodes[5] = new NodeClient("goldmine", ImageIO.read(new File(
					"bin/images/cities/goldmine.png")));
			cooImgs[5] = new Rectangle(95, 540, 350, 115);
			nodes[6] = new NodeClient("tombstone", ImageIO.read(new File(
					"bin/images/cities/tombstone.png")));
			cooImgs[6] = new Rectangle(69, 270, 280, 115);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// bodie
		cooPlayerLabel[0][0] = new Point(432, 248);
		cooPlayerLabel[0][1] = new Point(610, 200);
		cooPlayerLabel[0][2] = new Point(590, 340);
		cooPlayerLabel[0][3] = new Point(450, 320);
		// bannack
		cooPlayerLabel[1][0] = new Point(20, 178);
		cooPlayerLabel[1][1] = new Point(240, 138);
		cooPlayerLabel[1][2] = new Point(170, 220);
		cooPlayerLabel[1][3] = new Point(260, 230);
		// calico
		cooPlayerLabel[2][0] = new Point(950, 190);
		cooPlayerLabel[2][1] = new Point(1160, 185);
		cooPlayerLabel[2][2] = new Point(940, 110);
		cooPlayerLabel[2][3] = new Point(1000, 250);
		// santafe
		cooPlayerLabel[3][0] = new Point(720, 410);
		cooPlayerLabel[3][1] = new Point(935, 344);
		cooPlayerLabel[3][2] = new Point(868, 333);
		cooPlayerLabel[3][3] = new Point(960, 410);
		// tucson
		cooPlayerLabel[4][0] = new Point(930, 630);
		cooPlayerLabel[4][1] = new Point(1030, 530);
		cooPlayerLabel[4][2] = new Point(1170, 628);
		cooPlayerLabel[4][3] = new Point(1080, 550);
		// goldmine
		cooPlayerLabel[5][0] = new Point(20, 645);
		cooPlayerLabel[5][1] = new Point(120, 555);
		cooPlayerLabel[5][2] = new Point(460, 627);
		cooPlayerLabel[5][3] = new Point(350, 555);
		// tombstone
		cooPlayerLabel[6][0] = new Point(20, 365);
		cooPlayerLabel[6][1] = new Point(172, 455);
		cooPlayerLabel[6][2] = new Point(350, 360);
		cooPlayerLabel[6][3] = new Point(230, 460);

		nodes[0].setLinks(new NodeClient[] { nodes[1], nodes[2], nodes[5] });
		nodes[1].setLinks(new NodeClient[] { nodes[0] });
		nodes[2].setLinks(new NodeClient[] { nodes[0], nodes[3], nodes[4] });
		nodes[3].setLinks(new NodeClient[] { nodes[2] });
		nodes[4].setLinks(new NodeClient[] { nodes[2], nodes[5] });
		nodes[5].setLinks(new NodeClient[] { nodes[0], nodes[4], nodes[6] });
		nodes[6].setLinks(new NodeClient[] { nodes[5] });
	}
}
