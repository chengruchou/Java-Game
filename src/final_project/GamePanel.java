package final_project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import Background.Background;
import Bone.Bone;
import Character.Player;
import Character.Player2;
import Health.Health;
import History.FileIO;
import Pillar.Pillar;
import Skills.Skill;
import Skills.SkillAction;
import Sound.BackgroundMusic;
import Wind.Wind;

public class GamePanel extends JPanel implements Runnable {
    Thread gameThread;
    Graphics2D g2;
    Random rand = new Random();
    MouseHandler mouse1 = new MouseHandler();
    Pillar pillar = new Pillar(this);
    Background background = new Background(this);
    Player player1 = new Player(this);
    Player2 player2 = new Player2(this);
    int winds = 0, maxHealth = 20;
    boolean collide1 = false, collide2 = false, bone_hits_pillar = false, hits_bound = false, collide_or_not;
    Bone bone;
    Bone extraBone = null;
    Health health1 = new Health(this, maxHealth);
    Health health2 = new Health(this, maxHealth);
    JLabel wind_label = new JLabel("<html><span style='font-size:20px'>Winds : " + winds + "</span></html>");
    JLabel blood1 = new JLabel("<html><span style='font-size:10px'>blood : " + health1.currentHealth + "/" + maxHealth + "</span></html>");
    JLabel blood2 = new JLabel("<html><span style='font-size:10px'>blood : " + health2.currentHealth + "/" + maxHealth +  "</span></html>");
    JButton jButton = new JButton("Pause");
    JFrame JF;
    Wind wind = new Wind(this);
    private boolean gameRunning = true;
    private boolean boneGrown = false;
    private boolean buttonPressed = false;
    private boolean showBoomImage = false;
    private Timer boomTimer;
    private TimerTask boomTask;
    private boolean boneSize = true;
    private boolean exBoneSize = true;
    int FPS = 120;
    Skill growBoneSkill;
    Skill doubleBoneSkill;
    Skill increaseDamageSkill;
    Skill healSkill;
    private BackgroundMusic BackgroundMusic;
    

    public GamePanel(JFrame JF) {
        this.setLayout(null);
        this.setBackground(Color.white);
        this.setPreferredSize(new Dimension(1200, 800));
        this.addMouseListener(mouse1);
        this.setFocusable(true);
        this.JF = JF;
        BackgroundMusic = new BackgroundMusic();
        BackgroundMusic.play("res/music/background_music.wav");

        wind_label.setBounds(550, 0, 200, 30);
        blood1.setBounds(60,10,100,20);
        blood2.setBounds(1030,10,100,20);
        this.add(wind_label);
        this.add(blood1);
        this.add(blood2);


        addSkillButtons(1000,2);
        addSkillButtons(0,1);

        bone = new Bone(this, mouse1, player1.x+15, player1.y-150,1);
    }

    private void initializeSkills(int number) {
        growBoneSkill = new Skill("Grow Bone",number,bone, new SkillAction() {
            @Override
            public void execute() {
                if (!buttonPressed) {
                    if((bone.whos_turn == 1 && number == 1) || (bone.whos_turn == 2 && number == 2)){
                        boneSize = false;
                        if (extraBone != null) {
                            exBoneSize = false;
                        }
                        boneGrown = true;
                        buttonPressed = true;
                    }
                    
                }
            }
        });

        doubleBoneSkill = new Skill("Double Bone",number,bone, new SkillAction() {
            @Override
            public void execute() {
                if (!buttonPressed) {
                    if (extraBone == null && (bone.whos_turn == 1 && number == 1) || (bone.whos_turn == 2 && number == 2)) {
                        if (bone.whos_turn == 1) {
                            extraBone = new Bone(GamePanel.this, mouse1, player1.x, player1.y - 100,1);
                        } else {
                            extraBone = new Bone(GamePanel.this, mouse1, player2.x, player2.y - 100,2);
                        }
                        buttonPressed = true;
                    }
                    
                }
            }
        });


        increaseDamageSkill = new Skill("Increase Damage",number,bone, new SkillAction() {
            @Override
            public void execute() {
                if((bone.whos_turn == 1 && number == 1) || (bone.whos_turn == 2 && number == 2)){
                    if (!buttonPressed) {
                        bone.increaseDamage(5); 
                        buttonPressed = true;
                    }
                }
            }
        });

        healSkill = new Skill("Heal",number,bone, new SkillAction() {
            @Override
            public void execute() {
                if (!buttonPressed) {
                    if (number == 1) {
                        health1.increaseHealth(20);
                    } else {
                        health2.increaseHealth(20); 
                    }
                    buttonPressed = true;
                }
            }
        });
    }

    private void addSkillButtons(int x,int number) {
        initializeSkills(number);
        growBoneSkill.getButton().setBounds(x, 50, 200, 30);
        this.add(growBoneSkill.getButton());

        doubleBoneSkill.getButton().setBounds(x, 100, 200, 30);
        this.add(doubleBoneSkill.getButton());

        increaseDamageSkill.getButton().setBounds(x, 150, 200, 30);
        this.add(increaseDamageSkill.getButton());

        healSkill.getButton().setBounds(x, 200, 200, 30);
        this.add(healSkill.getButton());
    }

    public void startgameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2 = (Graphics2D) g;
        background.draw(g2);
        if (gameRunning) {
            player2.draw(g2);
            player1.draw(g2);
            if(boneSize) bone.draw(g2,0);
            else{
                bone.draw(g2,1);
            }
            if(showBoomImage){
                bone.showBoom(g2, bone.x, bone.y);
            }   
            if (extraBone != null) {
                if(exBoneSize) extraBone.draw(g2,0);
                else{
                    extraBone.draw(g2,1);
                }
            }
            if((bone.whos_turn == 1 && winds >= 0) || (bone.whos_turn == 2 && winds < 0)){
                wind.draw(g2,1);
            }
            else{
                wind.draw(g2,2);
            }
            pillar.draw(g2);
            health1.draw(g2, 10, 10);
            health2.draw(g2, 980, 10);
        }
    }

    @Override
    public void run() {
        double interval = 1000000000 / FPS;
        double nextTime = System.nanoTime() + interval;

        while (gameRunning) {
            if (bone.whos_turn == 1) {
                if (bone.getBounds().intersects(player2.getBounds())) {
                    collide1 = true;
                    winds = rand.nextInt(10) - 5;
                    health2.updateHealth(bone.damage);
                    showBoomForDuration(bone.x, bone.y);
                } else {
                    collide1 = false;
                }
                collide2 = false;
            } else {
                if (bone.getBounds().intersects(player1.getBounds())) {
                    collide2 = true;
                    winds = rand.nextInt(10) - 5;
                    health1.updateHealth(bone.damage);
                    showBoomForDuration(bone.x, bone.y);
                } else {
                    collide2 = false;
                }
                collide1 = false;
            }

            if (bone.getBounds().intersects(pillar.getBounds())) {
                bone_hits_pillar = true;
                winds = rand.nextInt(10) - 5;
            } else {
                bone_hits_pillar = false;
            }
            if ((bone.y >= 800 || bone.x >= 1200 || bone.x <= 0)) {
                hits_bound = true;
                winds = rand.nextInt(10) - 5;
            } else {
                hits_bound = false;
            }
            collide_or_not = collide1 || collide2 || bone_hits_pillar || hits_bound;

            if (extraBone != null) {
                if (extraBone.whos_turn == 1) {
                    if (extraBone.getBounds().intersects(player2.getBounds())) {
                        collide1 = true;
                        winds = rand.nextInt(10) - 5;
                        health2.updateHealth(bone.damage);
                        showBoomForDuration(extraBone.x, extraBone.y);
                    } else {
                        collide1 = false;
                    }
                    collide2 = false;
                } else {
                    if (extraBone.getBounds().intersects(player1.getBounds())) {
                        collide2 = true;
                        winds = rand.nextInt(10) - 5;
                        health1.updateHealth(bone.damage);
                        showBoomForDuration(extraBone.x, extraBone.y);
                    } else {
                        collide2 = false;
                    }
                    collide1 = false;
                }

                if (extraBone.getBounds().intersects(pillar.getBounds())) {
                    bone_hits_pillar = true;
                    winds = rand.nextInt(10) - 5;
                } else {
                    bone_hits_pillar = false;
                }
                if ((extraBone.y >= 800 || extraBone.x >= 1200 || extraBone.x <= 0)) {
                    hits_bound = true;
                    winds = rand.nextInt(10) - 5;
                } else {
                    hits_bound = false;
                }
                collide_or_not = collide1 || collide2 || bone_hits_pillar || hits_bound;
            }

            update();
            repaint();

            try {
                double remainTime = nextTime - System.nanoTime();
                remainTime = remainTime / 1000000;
                if (remainTime < 0) remainTime = 0;

                Thread.sleep((long) remainTime);
                nextTime += interval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (health1.currentHealth == 0) setWinner(2);
            else if (health2.currentHealth == 0) setWinner(1);
        }
    }


    private void showBoomForDuration(int x, int y) {
        showBoomImage = true;
        if (boomTimer != null) {
            boomTimer.cancel();
        }
        boomTimer = new Timer();
        boomTask = new TimerTask() {
            @Override
            public void run() {
                showBoomImage = false;
                repaint();
            }
        };
        boomTimer.schedule(boomTask, 1000);
    }


    private void update() {
        player2.update();
        player1.update();
        bone.update(collide_or_not, player1, player2, winds);
        if (extraBone != null) {
            extraBone.update(collide_or_not, player1, player2, winds);
        }
        wind_label.setText("<html><span style='font-size:20px'>Winds : " + (winds) + "</span></html>");
        wind_label.revalidate();
        wind_label.repaint();
        blood1.setText("<html><span style='font-size:10px'>blood : " + health1.currentHealth + "/" + maxHealth + "</span></html>");
        blood1.revalidate();
        blood1.repaint();
        blood2.setText("<html><span style='font-size:10px'>blood : " + health2.currentHealth + "/" + maxHealth + "</span></html>");
        blood2.revalidate();
        blood2.repaint();
        
        if (collide_or_not) {
            if (boneGrown) {
                boneSize = true;
                if (extraBone != null) {
                    exBoneSize = true;
                }
                boneGrown = false;
            }

            if (extraBone != null) {
                extraBone = null;
            }

            bone.resetDamage();
            buttonPressed = false;
        }
    }

    private void setWinner(int winnerNum) {
        gameRunning = false;
        repaint();
        FileIO.writeToFile(winnerNum == 1 ? "Cat" : "Dog");
        EndPanel endPanel = new EndPanel(JF, winnerNum == 1 ? "CAT" : "DOG");
        JF.add(endPanel);
        JF.revalidate();
        JF.repaint();
    }
}