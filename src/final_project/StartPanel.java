package final_project;

import javax.imageio.ImageIO;
import javax.swing.*;
import History.FileIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class StartPanel extends JPanel {
    private JFrame window;

    public StartPanel(JFrame window) {
        this.window = window;
        window.getContentPane().removeAll();
        setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel();
        ImageIcon imageIcon = null;
        try {
            BufferedImage img = ImageIO.read(getClass().getResource("/player/start.jpg"));
            if (img != null) {
                Image scaledImage = img.getScaledInstance(1200, 800, Image.SCALE_SMOOTH);
                imageIcon = new ImageIcon(scaledImage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (imageIcon != null) {
            titleLabel.setIcon(imageIcon);
        } else {
            titleLabel.setText("Start Game");
            titleLabel.setFont(new Font("Serif", Font.BOLD, 48));
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }


        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("Serif", Font.BOLD, 24));
        startButton.setForeground(Color.ORANGE);
        startButton.setBounds(380, 590, 440, 70); 
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGamePanel();
            }
        });

        JButton HistoryButton = new JButton("History");
        HistoryButton.setFont(new Font("Serif", Font.BOLD, 24));
        HistoryButton.setForeground(Color.ORANGE);
        HistoryButton.setBounds(380, 690, 440, 70); 
        HistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.getContentPane().removeAll();
                ArrayList<String> file = FileIO.readfile();
                displayHistory(file);
            }
        });

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1200, 800));
        
        titleLabel.setBounds(0, 0, 1200, 800);
        layeredPane.add(titleLabel, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(startButton, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(HistoryButton, JLayeredPane.PALETTE_LAYER);
        add(layeredPane, BorderLayout.CENTER);
    }

    private void showGamePanel() {
        window.getContentPane().removeAll();
        GamePanel gamePanel = new GamePanel(window);
        window.add(gamePanel);
        window.revalidate();
        window.repaint();
        gamePanel.startgameThread();
    }

    private void displayHistory(ArrayList<String> fileContent) {
        window.getContentPane().removeAll();

        JLabel HisLabel;
        setLayout(null);
        for(int i = 0;i <= fileContent.size();i ++){
            if(i < fileContent.size()) HisLabel = new JLabel("<html><span style='font-size:20px'>Round" + (i+1) + ": " + fileContent.get(i) + "</span></html>");
            else HisLabel = new JLabel();
            HisLabel.setBounds(250,30+50*i,1000,30);
            window.add(HisLabel);
        }

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Serif", Font.BOLD, 24));
        backButton.setForeground(Color.ORANGE);
        backButton.setBounds(380, 680, 440, 70); 
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartPanel start = new StartPanel(window);
                window.add(start);
                window.revalidate();
                window.repaint();
            }
        });
        window.add(backButton, BorderLayout.SOUTH);
        window.revalidate();
        window.repaint();
    }
}
