package Character;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import final_project.GamePanel;

public class Player2 extends Entity{
	GamePanel gp;
	public int weight = 240,height = 160,number = 2;
	
	public Player2(GamePanel gp) {
		this.gp=gp;		
		setValue();
		getPlayerImage();
	}
	public void setValue() {
		x=900;
		y=550;
		speed=3;
	}
    public Rectangle getBounds() {
        return new Rectangle(x, y, weight, height);
    }
	private void getPlayerImage() {
		try {
			dog= ImageIO.read(getClass().getResourceAsStream("/player/dog-removebg-preview.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public void update() {
		if(x > 1200 - weight) x = 1200 - weight;
		if(x < 580 + weight) x = 580 + weight;
		if(y > 800-height-50) y = 800-height-50;
		if(y < 50) y = 50;
		
		
	}
	public void draw(Graphics2D g2) {
		BufferedImage image = dog;
		g2.drawImage(image, x, y, weight, height, null);
	}
}

