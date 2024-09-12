package org.example;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Lab_12 extends Frame implements ActionListener {
    Button bex = new Button("Exit");
    Button sea = new Button("Search");
    TextArea txa = new TextArea();

    public Lab_12() {
        super("Bumble Bee");
        setLayout(null);
        setBackground(new Color(150, 200, 100));
        setSize(450, 250);
        add(bex);
        add(sea);
        add(txa);
        bex.setBounds(110, 190, 100, 20);
        bex.addActionListener(this);
        sea.setBounds(110, 165, 100, 20);
        sea.addActionListener(this);
        txa.setBounds(20, 50, 300, 100);

        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == bex) {
            System.exit(0);
        } else if (ae.getSource() == sea) {
            String[] keywords = txa.getText().toLowerCase().split(",");
            File directory = new File("D:/Java Projects/AIPRP1/src/main/java/res4lab12");
            File[] files = directory.listFiles();

            if (files != null) {
                txa.setText("");
                for (File file : files) {
                    String result = puppyFinder(file, keywords);
                    if (!result.isEmpty()) {
                        txa.append(result);
                    }
                }
            }
        }
    }

    public static String puppyFinder(File file, String[] keywords) {
        StringBuilder results = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String attributes = parts[0].toLowerCase().trim();
                    String dogName = parts[1].trim();
                    boolean allKeywordsMatch = true;
                    for (String keyword : keywords) {
                        if (!attributes.contains(keyword.trim())) {
                            allKeywordsMatch = false;
                            break;
                        }
                    }
                    if (allKeywordsMatch) {
                        results.append(dogName).append("\n");
                    }
                }
            }
        } catch (IOException ioException) {
            System.out.println("Error: " + ioException.getMessage());
        }
        return results.toString();
    }

    public static void main(String[] args) {
        new Lab_12();
    }
}