package Presentation;

import Business.DeliveryService;
import Business.MenuItem;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TreeTable extends JFrame {

    private JPanel jPanel = new JPanel();
    private final JButton addButton,updateButton,deleteButton,initButton;
    private final JTextField title,rating,calories,protein,fat,sodium,price;


    JTreePanelThree myTree;
    JScrollPane scrollPane;

    public TreeTable(){

        //Add button
        addButton = new JButton("+ Add");
        //Update button
        updateButton = new JButton("Update");
        //Delete button
        deleteButton = new JButton("Delete");
        //Initialize Button
        initButton = new JButton("Initialize");
        title = new JTextField("Avocado");
        rating = new JTextField("1");
        calories = new JTextField("400");
        protein = new JTextField("300");
        fat = new JTextField("0");
        sodium = new JTextField("12");
        price = new JTextField("1");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("JTree Basic Examples");

        setSize(500,500);
        setLayout(new BorderLayout());

// Comment the two lines below when using JTreePanelTwo
        myTree = new JTreePanelThree();
        scrollPane = new JScrollPane(myTree.getJpanel());
        add(scrollPane, "Center");

        JPanel btnPanel = new JPanel();
        //Add buttons to panel
        btnPanel.add(addButton);
        btnPanel.add(updateButton);
        btnPanel.add(deleteButton);
        btnPanel.add(initButton);

        JPanel textFieldPanel = new JPanel();


        title.setPreferredSize(new Dimension(80,20));
        rating.setPreferredSize(new Dimension(80,20));
        calories.setPreferredSize(new Dimension(80,20));
        protein.setPreferredSize(new Dimension(80,20));
        fat.setPreferredSize(new Dimension(80,20));
        sodium.setPreferredSize(new Dimension(80,20));
        price.setPreferredSize(new Dimension(80,20));

        textFieldPanel.add(title);
        textFieldPanel.add(rating);
        textFieldPanel.add(calories);
        textFieldPanel.add(protein);
        textFieldPanel.add(fat);
        textFieldPanel.add(sodium);
        textFieldPanel.add(price);

        initButton.addActionListener(e -> {

            List<Business.MenuItem> items = DeliveryService.getInstance().initMenu();
            for (MenuItem itm:
                    items) {
                myTree.addNode(itm.getTitle());
            }
        });

        deleteButton.addActionListener(e -> {

        });

        addButton.addActionListener(e ->{
            myTree.addNode(title.getText());
        });

        add(textFieldPanel,"North");
        add(btnPanel,"South");
        setVisible(true);
        pack();


    }

    public void rmNode(){
        myTree.removeNode();
    }
}
