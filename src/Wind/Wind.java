package Wind;

import final_project.GamePanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Wind extends Rectangle{
    GamePanel gp;
    BufferedImage windL;
    BufferedImage windR;
    int x,y,weight,height;

    public Wind(GamePanel gp){
        this.gp = gp;
        getWindImage();
        this.x = 550;
        this.y = 50;
    }
    
    private void getWindImage() {
		try {
            windL = ImageIO.read(getClass().getResourceAsStream("/player/arrowL.png"));
            windR = ImageIO.read(getClass().getResourceAsStream("/player/arrowR.png"));
        }catch(IOException e){
			e.printStackTrace();
		}
	}

    public void draw(Graphics2D g2, int way) {
        BufferedImage image;
        if (way == 1) {
            image = windR;
            g2.drawImage(image, x, y, 150, 75, null);
        } else {
            image = windL;
            g2.drawImage(image,x,y,150,75, null);
        }
    }
}
