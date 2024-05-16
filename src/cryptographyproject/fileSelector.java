package cryptographyproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class fileSelector {

    public fileSelector(int i) {
        String plainText = "";
        JFileChooser f = new JFileChooser();
        f.setCurrentDirectory(new File("D:\\Desktop\\cryptoTESTS\\"));
        FileNameExtensionFilter fi = new FileNameExtensionFilter("Text files", "txt");
        f.setFileFilter(fi);
        int result = f.showOpenDialog(null);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }
        try {
            if (!(f.getSelectedFile().getName().endsWith(".txt") || f.getSelectedFile().getName().endsWith(".TXT"))) {
                JOptionPane.showMessageDialog(null, "the is not a text file!", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
            Scanner s = new Scanner(f.getSelectedFile());
            while (s.hasNextLine()) {
                plainText += s.nextLine();
            }
            if (plainText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "the file is empty",  "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "file not found",  "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        for (int j = 0; j < plainText.length(); j++) {
            if (((int) plainText.charAt(j) < 65) || ((int) plainText.charAt(j) > 122) || ((int) plainText.charAt(j) > 90 && (int) plainText.charAt(j) < 97)) {
                plainText = plainText.replace(plainText.charAt(j) + "", "");
                j--;
            }
        }
        if (i == 1) {
            new enGUI(plainText.toUpperCase());
        } else {
            new deGUI(plainText.toUpperCase());
        }
    }
}
