package Character;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import final_project.GamePanel;

public class Player extends Entity{
	GamePanel gp;
	public int weight=180,height=200,number = 1;
	
	public Player(GamePanel gp) {
		this.gp=gp;
		setValue();
		getPlayerImage();
	}
	private void setValue() {
		x=100;
		y=700;
		speed=3;
	}
	private void getPlayerImage() {
		try {
			cat= ImageIO.read(getClass().getResourceAsStream("/player/cat-removebg-preview.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
    public Rectangle getBounds() {
        return new Rectangle(x, y, weight, height);
    }
	public void update() {
		if(x > 490-weight) x = 490-weight;
		if(x < 0) x = 0;
		if(y > 800-height-50) y = 800-height-50;
		if(y < 50) y = 50;
	}
	public void draw(Graphics2D g2) {
		BufferedImage image = cat;
		g2.drawImage(image, x, y, weight, height, null);
	}
}
