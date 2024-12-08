package View;

import Connection.InternetConnectionChecker;
import Controller.CategoryController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class AddCategoryView extends JFrame {
    private JTextField categoryTypeField;
    private JButton addButton;
    private ImageIcon img;
    private JLabel imagelabel;
    private CategoryController cc = new CategoryController();
    private InternetConnectionChecker icc = new InternetConnectionChecker();

    public AddCategoryView() {
        // Set up the frame
        setTitle("Add Category");
        setBounds(100, 100, 800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        // Load the image
        img = new ImageIcon("src/resources/login.png");

        // Image label
        imagelabel = new JLabel(img);
        imagelabel.setBounds(0, 0, 400, 600);
        add(imagelabel);

        // Create components
        JLabel categoryTypeLabel = new JLabel("Category Type:");
        categoryTypeLabel.setBounds(450, 200, 150, 25);

        categoryTypeField = new JTextField();
        categoryTypeField.setBounds(600, 200, 150, 25);

        addButton = new JButton("Add");
        addButton.setBounds(600, 250, 100, 30);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isconnected = icc.startChecking();
                if (isconnected) {
                    String type = categoryTypeField.getText();
                    cc.redirectinsertRequest(type);
                    dispose();
                } else {
                    storeCategoryDataintempfile(categoryTypeField.getText());
                }
            }
        });

        add(categoryTypeLabel);
        add(categoryTypeField);
        add(addButton);

        setVisible(true);
    }

    void storeCategoryDataintempfile(String category) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter("AddCategory.txt", true));
            String data = category;
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public static void main (String[]args){
        new AddCategoryView();

    }
}



