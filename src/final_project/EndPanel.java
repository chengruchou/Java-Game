package final_project;

import javax.swing.*;
import History.FileIO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class EndPanel extends JPanel {
    private JFrame window;
    Graphics2D g2;

    public EndPanel(JFrame window,String winner) {
        this.window = window;
        window.getContentPane().removeAll();
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("<html><span style='font-size:60px'>GAME OVER! " + winner + " WINS</span></html>");
        titleLabel.setBounds(150,50,1000,400);

        JButton HistoryButton = new JButton("History");
        HistoryButton.setFont(new Font("Serif", Font.BOLD, 24));
        HistoryButton.setForeground(Color.ORANGE);
        HistoryButton.setBounds(380, 410, 440, 70); 
        HistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.getContentPane().removeAll();
                ArrayList<String> file = FileIO.readfile();
                displayHistory(file, winner);
            }
        });

        JButton RestartButton = new JButton("Play Again");
        RestartButton.setFont(new Font("Serif", Font.BOLD, 24));
        RestartButton.setForeground(Color.ORANGE);
        RestartButton.setBounds(380, 500, 440, 70); 
        RestartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showGamePanel();
            }
        });
        JButton ExitButton = new JButton("Exit Game");
        ExitButton.setFont(new Font("Serif", Font.BOLD, 24));
        ExitButton.setForeground(Color.ORANGE);
        ExitButton.setBounds(380, 590, 440, 70); 
        ExitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1200,800));
        layeredPane.setBackground(Color.orange);
        layeredPane.add(titleLabel,JLayeredPane.PALETTE_LAYER);
        layeredPane.add(HistoryButton,JLayeredPane.PALETTE_LAYER);
        layeredPane.add(RestartButton,JLayeredPane.PALETTE_LAYER);
        layeredPane.add(ExitButton,JLayeredPane.PALETTE_LAYER);
        window.add(layeredPane, BorderLayout.CENTER);
        window.setVisible(true);
    }

    private void showGamePanel() {
        window.getContentPane().removeAll();
        GamePanel gamePanel = new GamePanel(window);
        window.add(gamePanel);
        window.revalidate();
        window.repaint();
        gamePanel.startgameThread();
    }

    private void displayHistory(ArrayList<String> fileContent,String winner) {
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
                EndPanel end = new EndPanel(window, winner);
                window.add(end);
                window.revalidate();
                window.repaint();
            }
        });
        window.add(backButton, BorderLayout.SOUTH);
        window.revalidate();
        window.repaint();
    }
}
