package org.example;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.MalformedInputException;
import java.util.List;

public class Lab_11 extends Frame implements ActionListener {
    Button bex = new Button("Exit");
    Button sea = new Button("Search");
    TextArea txa = new TextArea();

    public Lab_11() {
        super("Optimus Prime");
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

        this.show();
        this.setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == bex)
            System.exit(0);
        else if (ae.getSource() == sea) {
            String[] keywords = txa.getText().split(",");
            for (int j = 0; j < keywords.length; j++) {
                System.out.println(keywords[j]);
            }
            File f = new File("D:/Java Projects/AIPRP1/src/main/resources");
            ArrayList<File> files =
                    new ArrayList<File>(Arrays.asList(f.listFiles()));
            txa.setText("");
            List<String> results = new ArrayList<>();
            for (File elem : files) {
                int zcoincidence = test_url(elem, keywords);
                String res = "\n" + elem + "  :" + zcoincidence ;
                txa.append(res);
                results.add(res);
            }

            String maxFilePath = null;
            int maxMatches = -1;

            for (String result : results) {
                String[] parts = result.split("  :");
                if (parts.length == 2) {
                    String filePath = parts[0].trim();
                    int matches = Integer.parseInt(parts[1].trim());

                    if (matches > maxMatches) {
                        maxMatches = matches;
                        maxFilePath = filePath;
                    }
                }
            }
            System.out.println(maxFilePath);
            File openedFile = new File(maxFilePath);
            OpenHtmlFile(openedFile);
        }
    }

    public static int test_url(File elem, String[] keywords) {
        int res = 0;
        URL url = null;
        URLConnection con = null;
        int i;
        try {
            String ffele = "" + elem;
            url = new URL("file:/" + ffele.trim());
            con = url.openConnection();
            File file = new File("src/main/resources/result.html");
            BufferedInputStream bis = new BufferedInputStream(
                    con.getInputStream());
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file));
            String bhtml = ""; //file content in byte array

            while ((i = bis.read()) != -1) {
                bos.write(i);
                bhtml += (char) i;
            }
            bos.flush();
            bis.close();
            String htmlcontent =
                    (new String(bhtml)).toLowerCase(); //file content in string
            System.out.println("New url content is: " + htmlcontent);
            for (int j = 0; j < keywords.length; j++) {
                if (htmlcontent.indexOf(keywords[j].trim().toLowerCase()) >= 0)
                    res++;
            }
        } catch (MalformedInputException malformedInputException) {
            System.out.println("error " + malformedInputException.getMessage());
            return -1;
        } catch (IOException ioException) {
            System.out.println("error " + ioException.getMessage());
            return -1;
        } catch (Exception e) {
            System.out.println("error " + e.getMessage());
            return -1;
        }
        return res;
    }

    public static void OpenHtmlFile(File file)
    {
        try {

            if (file.exists()) {
                Desktop.getDesktop().browse(file.toURI());
            } else {
                System.out.println("Файл не найден: " + file.getAbsolutePath());
            }
        } catch (IOException e) {
            System.out.println("Ошибка при открытии файла: " + e.getMessage());
        }

    }

    public static void main(String[] args) {
        new Lab_11();
    }
}