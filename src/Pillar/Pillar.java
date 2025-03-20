package Pillar;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import final_project.GamePanel;

public class Pillar extends Rectangle {
	GamePanel gp;
	BufferedImage pillar;
	int x,y,width=100,height=250;
	
	public Pillar(GamePanel gp) {
		this.gp=gp;
		x=550;
		y=550;
		getPillarImage();
	}

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

	private void getPillarImage() {
		try {
			pillar = ImageIO.read(getClass().getResourceAsStream("/player/pillar.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		
	}

	public void draw(Graphics2D g2) {
		BufferedImage image = pillar;
		g2.drawImage(image, x, y, width, height, null);
	}
}
