package cryptographyproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {

    JLabel l1;
    JButton en, de;
    JPanel p1, p2;

    public GUI() {
        setTitle("Encrypt/Decrypt program");
        setVisible(true);
        setSize(400, 100);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        l1 = new JLabel("what do you want to do :");
        en = new JButton("ENCRYPT");
        de = new JButton("DECRYPT");
        p1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p1.add(l1);
        add(p1, BorderLayout.NORTH);
        p2.add(en);
        p2.add(de);
        add(p2);
        en.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new fileSelector(1);
                dispose();
            }
        });
        de.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new fileSelector(2);
                dispose();
            }
        });
    }

}
