package Background;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import final_project.GamePanel;

public class Background extends Rectangle {
	GamePanel gp;
	public BufferedImage background;
	int backgroundNum = 1;
	Random r = new Random();
	
	public Background(GamePanel gp) {
		this.gp=gp;
		x=0;
		y=0;
		while(backgroundNum == 1) {
			backgroundNum = r.nextInt(6)+1;
		}
		getBackgroundImage();
	}

	private void getBackgroundImage() {
		try {
			background = ImageIO.read(getClass().getResourceAsStream("/player/background" + backgroundNum + ".png"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2) {
		BufferedImage image = background;
		g2.drawImage(image, x, y, 1200, 800, null);
	}
}
