package final_project;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1200, 800);
        window.setResizable(false);
        window.setTitle("Dog Cat Fight");

        StartPanel startPanel = new StartPanel(window);
        window.add(startPanel);

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
