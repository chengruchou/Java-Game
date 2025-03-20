package Bone;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import Character.Player;
import Character.Player2;
import final_project.GamePanel;
import final_project.MouseHandler;

public class Bone extends Rectangle {
	GamePanel gp;
	MouseHandler mouse1;
	BufferedImage bone;
	BufferedImage boom;
	public int whos_turn = 1;
	public int x,y;
	public int weight=100,height=100;
	double angle=Math.PI/4;
	final double gravity=0.2; 
	boolean isThrowing;
    double xVelocity;
    double yVelocity;
    boolean clicked_once=false;
	int speed;
	public int damage;
	
	public Bone(GamePanel gp,MouseHandler mouse1,int x ,int y,int whos_turn) {	
		this.gp=gp;
		this.mouse1=mouse1;
		this.speed = 3;
		setValue(x,y);
		getBoneImage();
		getBoomImage();
		this.whos_turn=whos_turn;
		this.damage=5;
	}

    public void increaseDamage(int amount) {
        this.damage += amount;
    }

    public void resetDamage() {
        this.damage = 5;
    }

	private void setValue(int x,int y) {
		this.x=x+50;
		this.y=y-50;
	}

    public Rectangle getBounds() {
        return new Rectangle(x, y, weight, height);
    }
    
	private void getBoneImage() {
		try {
			bone = ImageIO.read(getClass().getResourceAsStream("/player/bone.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	private void getBoomImage(){
		try {
			boom = ImageIO.read(getClass().getResourceAsStream("/player/boom.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update(boolean collide,Player p1,Player2 p2,int wind) {
        if (mouse1.mouse_clicked && !clicked_once) {
            isThrowing = true;
        } else if (isThrowing) {
            double initialVelocity = (mouse1.mouseReleasedTime - mouse1.mousePressedTime) / 70.0;
            xVelocity =  (1.0+(double)wind/10.0) *initialVelocity * Math.cos(angle);
            yVelocity = -(1.0+(double)wind/10.0)*initialVelocity * Math.sin(angle);

            clicked_once=true;
            if(whos_turn==2) xVelocity=-xVelocity;
            isThrowing = false; 
            
        }
        if(collide) {
        	if(whos_turn==1)whos_turn=2;
			else whos_turn=1;
			if(whos_turn==1) {
				x = p1.x+50;
				y = p1.y-50;
			}else {
				x = p2.x+70;
				y = p2.y-60;
			}
        	
        	yVelocity = 0;
            xVelocity = 0;
            clicked_once=false;
        }
		
        if (!isThrowing && (xVelocity != 0 || yVelocity != 0)) {
            x += xVelocity;
            yVelocity += gravity;
            y += yVelocity;        
        }
	}

	public void showBoom(Graphics2D g2, int xc, int yc) {
		BufferedImage image = boom;
		if(whos_turn==2)
			g2.drawImage(image, xc, yc+90, 100, 100, null);
		else
			g2.drawImage(image, xc, yc+100, 100, 100, null);
	}

	private void drawPowerArc(Graphics2D g2) {
        if (mouse1.mouse_clicked) {
            long duration = System.currentTimeMillis() - mouse1.mousePressedTime;
            duration = Math.min(duration, 2000); 
            int angle = (int) (360 * (duration / 2000.0));
            g2.setColor(Color.PINK);
            int arcX = (whos_turn == 1) ? 10 : gp.getWidth() - 150;
            int arcY = gp.getHeight() - 200;
            g2.fillArc(arcX, arcY, 100, 100, 0, angle);
        }
    }

	public void draw(Graphics2D g2, int buff) {
		int w,h,newy;
		if(buff != 0){
			w = weight*2;
			h = height*2;
			newy = y - 100;
		}
		else{
			w = weight;
			h = height;
			newy = y;
		}
		BufferedImage image = bone;
		g2.drawImage(image, x, newy, w, h, null);
		drawPowerArc(g2);
	}
	
}