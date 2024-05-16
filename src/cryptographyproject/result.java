package cryptographyproject;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.*;

public class result extends JFrame {

    JButton b1, b2;
    JPanel p1;
    JLabel l1;
    JTextArea t1;

    public result(String r, String fr) {
        setTitle("Result");
        setVisible(true);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        l1 = new JLabel("Finshed!");
        l1.setAlignmentX(CENTER_ALIGNMENT);
        t1 = new JTextArea(r + "\n\n the final result : \n" + fr);
        t1.setBorder(new TitledBorder("The Result"));
        t1.setFont(new Font("Arial Bold", Font.BOLD, 15));
        t1.setEditable(false);
        JScrollPane sc=new JScrollPane(t1);
        sc.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sc.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        b1 = new JButton("Save into new file");
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String input = JOptionPane.showInputDialog("enter the name of the new file ");
                    String desktopPath = System.getProperty("user.home") + File.separator + input + ".txt";
                    File file = new File("D:\\Desktop\\cryptoTESTS\\"+ input + ".txt");
                    PrintWriter p = new PrintWriter(file);
                    p.println(fr);
                    p.close();
                    JOptionPane.showMessageDialog(null, "new file have been created!\n the path : " + file.getAbsolutePath());
                } catch (FileNotFoundException eee) {
                }
            }
        });
        b2 = new JButton("try it again");
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new GUI();
                dispose();
            }
        });
        p1 = new JPanel(new GridLayout(2, 1, 1, 1));
        p1.add(b1);
        p1.add(b2);
        add(sc);
        add(l1, BorderLayout.NORTH);
        add(p1, BorderLayout.SOUTH);
    }

}
