package Health;
import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import final_project.GamePanel;

public class Health extends JPanel {
	public int maxHealth;
	public int currentHealth;
	GamePanel gp;
	
	public Health(GamePanel gp,int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.gp=gp;
	}

	public void draw(Graphics2D g2,int x, int y) {
        g2.setColor(Color.GRAY);
        g2.fillRect(x, y, 200, 20);
        double healthRatio = (double) currentHealth / maxHealth;
        g2.setColor(Color.RED);
        int width = (int) (healthRatio * 200); 
        g2.fillRect(x, y, width, 20); 
	}

	public void updateHealth(int gg_health) {
		currentHealth=currentHealth-gg_health;
	}
	
    public void increaseHealth(int amount) {
        currentHealth += amount;
        if (currentHealth > maxHealth) {
            currentHealth = maxHealth;
        }
    }

}
