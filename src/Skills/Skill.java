package Skills;

import javax.swing.JButton;
import Bone.Bone;

public class Skill {
    private String name;
    private SkillAction action;
    private JButton button;
    public int number;

    public Skill(String name,int num,Bone bone, SkillAction action) {
        this.name = name;
        this.action = action;
        this.number = num;
        this.button = new JButton(name);
        this.button.addActionListener(e -> {
            action.execute();
            button.setEnabled(false); 
        });
    }

    public JButton getButton() {
        return button;
    }
}
