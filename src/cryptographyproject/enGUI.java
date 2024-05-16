package cryptographyproject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class enGUI extends JFrame {

    JLabel l1, l2, l3, l4, l5;
    JTextField t1, t2, t3, t4, t5;
    JButton b1, b2;
    String output = "";

    public enGUI(String plainText) {
        output += "The plain text before encryption:\n" + plainText + "\n\n";
        setTitle("Encryption");
        setVisible(true);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2, 3, 3));

        l1 = new JLabel("ceasar key (single character):");
        l2 = new JLabel("playfair key (only character\\s):");
        l3 = new JLabel("vigenere key (only character\\s):");
        l4 = new JLabel("Row Transposation key(intger value[1-9]):");
        t1 = new JTextField();
        t2 = new JTextField();
        t3 = new JTextField();
        t4 = new JTextField();
        b1 = new JButton("encrypt");
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ck = t1.getText().toUpperCase(), pk = t2.getText().toUpperCase(), vk = t3.getText().toUpperCase(), tk = t4.getText().toUpperCase();
                if (ck.length() != 1 || !ck.matches("[A-Z]")) {
                    JOptionPane.showMessageDialog(null, "caesar key invalid", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!pk.matches("[A-Z]+")) {
                    JOptionPane.showMessageDialog(null, "playfair key invalid", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!vk.matches("[A-Z]+")) {
                    JOptionPane.showMessageDialog(null, "vigenere key invalid", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!tk.matches("[1-9]+")) {
                    JOptionPane.showMessageDialog(null, "transposation key invalid", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    for (int i = 0; i < tk.length(); i++) {
                        for (int j = i + 1; j < tk.length(); j++) {
                            if (tk.charAt(i) == tk.charAt(j)) {
                                JOptionPane.showMessageDialog(null, "transposation key invalid", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }

                    }
                    int biggest = Character.getNumericValue(tk.charAt(0));
                    for (int i = 1; i < tk.length(); i++) {
                        if (Character.getNumericValue(tk.charAt(i)) > biggest) {
                            biggest = Character.getNumericValue(tk.charAt(i));
                        }
                    }
                    if (tk.length() < biggest) {
                        JOptionPane.showMessageDialog(null, "Row transposation key invalid", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                String c = caesar(plainText, ck);
                String p = playfair(c, pk);
                String v = vigenere(p, vk);
                String t = rowTransposition(v, tk);
                new result(output, t);
                dispose();
            }
        });
        b2 = new JButton("cancle");
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });
        add(l1);
        add(t1);
        add(l2);
        add(t2);
        add(l3);
        add(t3);
        add(l4);
        add(t4);
        add(b1);
        add(b2);

    }

    /////////caesar cipher///////////////
    public String caesar(String p, String k) {
        int key = (int) k.charAt(0) - 65;
        String r = "";
        for (int i = 0; i < p.length(); i++) {
            char c = p.charAt(i);
            r += (char) (((((int) c - 65) + key) % 26) + 65);

        }
        output += "ciphertext after caesar cipher:\n" + r + "\n\n";
        return r;
    }

    ////////playfair cipher//////////////
    public String playfair(String p, String k) {
        char[][] table = new char[5][5];
        String alphabitic = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        p = p.replaceAll("J", "I");
        k = k.replaceAll("J", "I");
        String r = "";
        StringBuilder key = new StringBuilder();
        for (int i = 0; i < k.length() + alphabitic.length(); i++) {
            boolean cheaker = false;
            if (i < k.length()) {
                char c = k.charAt(i);
                for (int j = 0; j < key.length(); j++) {
                    if (key.charAt(j) == c) {
                        cheaker = true;
                    }
                }
                if (!cheaker) {
                    key.append(c);
                }
            } else {
                char c = alphabitic.charAt(i - k.length());
                for (int j = 0; j < key.length(); j++) {
                    if (key.charAt(j) == c) {
                        cheaker = true;
                    }
                }
                if (!cheaker) {
                    key.append(c);
                }
            }
        }
        if (key.length() != 25) {
            JOptionPane.showMessageDialog(null, "somthing wrong", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        ///////////////fill the matrix
        int index = 0;
        for (int i = 0; i < table[0].length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                table[i][j] = key.charAt(index);
                index++;
            }
        }

        /////////encryption
        for (int i = 0; i < p.length();) {
            if (i == p.length() - 1) {
                int frow = find_index(table, p.charAt(i))[0];
                int fcol = find_index(table, p.charAt(i))[1];
                int srow = find_index(table, 'X')[0];
                int scol = find_index(table, 'X')[1];
                if (frow == srow) {
                    r += table[frow][(fcol + 1) % 5];
                    r += table[frow][(scol + 1) % 5];
                    i++;
                } else if (fcol == scol) {
                    r += table[fcol][(frow + 1) % 5];
                    r += table[fcol][(srow + 1) % 5];
                    i++;
                } else {
                    r += table[frow][scol];
                    r += table[srow][fcol];
                    i++;
                }
            } else if (p.charAt(i) == p.charAt(i + 1)) {
                int frow = find_index(table, p.charAt(i))[0];
                int fcol = find_index(table, p.charAt(i))[1];
                int srow = find_index(table, 'X')[0];
                int scol = find_index(table, 'X')[1];
                if (frow == srow) {
                    r += table[frow][(fcol + 1) % 5];
                    r += table[frow][(scol + 1) % 5];
                    i++;
                } else if (fcol == scol) {
                    r += table[fcol][(frow + 1) % 5];
                    r += table[fcol][(srow + 1) % 5];
                    i++;
                } else {
                    r += table[frow][scol];
                    r += table[srow][fcol];
                    i++;
                }
            } else {
                int frow = find_index(table, p.charAt(i))[0];
                int fcol = find_index(table, p.charAt(i))[1];
                int srow = find_index(table, p.charAt(i + 1))[0];
                int scol = find_index(table, p.charAt(i + 1))[1];
                if (frow == srow) {
                    r += table[frow][(fcol + 1) % 5];
                    r += table[frow][(scol + 1) % 5];
                    i += 2;
                } else if (fcol == scol) {
                    r += table[(frow + 1) % 5][fcol];
                    r += table[(srow + 1) % 5][fcol];
                    i += 2;
                } else {
                    r += table[frow][scol];
                    r += table[srow][fcol];
                    i += 2;
                }
            }

        }
        output += "ciphertext after playfair cipher:\n" + r + "\n\n";
        return r;
    }

    ///////vigenere cipher/////////////
    public String vigenere(String p, String k) {
        String r = "";
        String fkey = "";
        for (int i = 0; i < p.length(); i++) {
            fkey += k.charAt(i % k.length());
        }
        for (int i = 0; i < p.length(); i++) {
            int key = (int) fkey.charAt(i) - 65;
            char c = p.charAt(i);
            r += (char) (((((int) c - 65) + key) % 26) + 65);
        }
        output += "ciphertext after vigenere cipher:\n" + r + "\n\n";
        return r;
    }

    ///////transposition chipher/////////
    public String rowTransposition(String p, String k) {
        String r = "";
        int row = (int) (Math.ceil((double) p.length() / k.length()));
        //////indexes
        int index = 1;
        int[] order = new int[k.length()];
        for (int i = 0; i < order.length; i++) {
            for (int j = 0; j < k.length(); j++) {
                if (Character.getNumericValue(k.charAt(j)) == index) {
                    order[i] = j;
                    index++;
                    break;
                }
            }

        }

        for (int i = 0; i < k.length(); i++) {
            int shift = order[i];
            for (int j = 0; j < row; j++) {
                if (shift < p.length()) {
                    r += p.charAt(shift);
                }
                shift += k.length();
            }

        }

        output += "ciphertext after row transposition cipher:\n" + r + "\n\n";
        return r;

    }

    public int[] find_index(char arr[][], char c) {
        int[] index = new int[2];
        for (int i = 0; i < arr[0].length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                if (arr[i][j] == c) {
                    index[0] = i;
                    index[1] = j;
                    return index;
                }
            }
        }
        JOptionPane.showMessageDialog(null, "find_index error", "Error", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
        return new int[]{-1, -1};
    }
}
