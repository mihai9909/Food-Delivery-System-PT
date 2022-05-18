package Presentation;

import Business.*;
import Business.MenuItem;
import Data.FileWriterAndReader;
import Data.Serializator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Map;
import java.util.stream.Collectors;

public class Administrator extends JFrame{
    private DeliveryService ds;

    private final JButton addButton,updateButton,deleteButton,initButton,selectItmBtn,composeBtn, clearCompose, saveBtn,backBtn;
    private final JTextField title,rating,calories,protein,fat,sodium,price, minHour,maxHour,minNbProdOrdered,minTimesOrdered,minPrice,day;

    private static final String[] header = {"Title","Rating","Calories","Protein","Fat","Sodium","Price"};
    private JScrollPane prodScrollPane, composeScrollPane,detailsScrollPane;
    private DefaultTableModel prodModel,composeModel,detailsModel;
    private JTable prodTable;
    private JTable composeTable;
    private JTable detailsTable;

    private final JButton reportButton1, reportButton2, reportButton3, reportButton4;
    public Administrator() {

        ds = DeliveryService.getInstance();

        prodModel = new DefaultTableModel(header,0);
        composeModel = new DefaultTableModel(header,0);
        detailsModel = new DefaultTableModel(header,0);

        setSize(1920, 1000);
        setLayout(null);
        setTitle("Table");
        setLocationRelativeTo(null);
        setResizable(false);


        prodTable = new JTable(prodModel);
        composeTable = new JTable(composeModel);
        detailsTable = new JTable(detailsModel);

        initTable(ds.getItems());

        //Add button
        addButton = new JButton("+ Add");
        //Update button
        updateButton = new JButton("Update");
        //Delete button
        deleteButton = new JButton("Delete");
        //Initialize Button
        initButton = new JButton("Initialize");
        //Select item button
        selectItmBtn = new JButton("->");
        //Compose button
        composeBtn = new JButton("Compose");
        //Clear button
        clearCompose = new JButton("Clear");
        //Save button
        saveBtn = new JButton("Save");
        //Back button
        backBtn = new JButton("<-");
        //Report button
        reportButton1 = new JButton("Report 1");
        reportButton2 = new JButton("Report 2");
        reportButton3 = new JButton("Report 3");
        reportButton4 = new JButton("Report 4");


        title = new JTextField("Avocado");
        rating = new JTextField("1");
        calories = new JTextField("400");
        protein = new JTextField("300");
        fat = new JTextField("0");
        sodium = new JTextField("12");
        price = new JTextField("1");
        minHour = new JTextField();
        maxHour = new JTextField();
        minNbProdOrdered = new JTextField();
        minTimesOrdered = new JTextField();
        minPrice = new JTextField();
        day = new JTextField();

        //Add buttons to panel
        add(addButton);
        add(updateButton);
        add(deleteButton);
        add(initButton);
        add(selectItmBtn);
        add(composeBtn);
        add(clearCompose);
        add(saveBtn);
        add(backBtn);
        add(reportButton1);
        add(reportButton2);
        add(reportButton3);
        add(reportButton4);
        //Add text fields to panel
        add(title);
        add(rating);
        add(calories);
        add(protein);
        add(fat);
        add(sodium);
        add(price);
        add(minHour);
        add(maxHour);
        add(minNbProdOrdered);
        add(minTimesOrdered);
        add(minPrice);
        add(day);


        title.setBounds(30,60,720,20);
        rating.setBounds(30,80,720,20);
        calories.setBounds(30,100,720,20);
        protein.setBounds(30,120,720,20);
        fat.setBounds(30,140,720,20);
        sodium.setBounds(30,160,720,20);
        price.setBounds(30,180,720,20);

        addButton.setBounds(30,390,100,40);
        updateButton.setBounds(150,390,100,40);
        deleteButton.setBounds(270,390,100,40);
        initButton.setBounds(390,390,100,40);
        saveBtn.setBounds(510,390,100,40);
        selectItmBtn.setBounds(630,390,100,40);
        backBtn.setBounds(10,10,50,20);
        composeBtn.setBounds(900,440,100,40);
        clearCompose.setBounds(780,440,100,40);
        //report buttons
        int x = 870;
        reportButton1.setBounds(x,600,100,20);
        reportButton2.setBounds(x+150,600,100,20);
        reportButton3.setBounds(x+300,600,100,20);
        reportButton4.setBounds(x+450,600,100,20);
        //report fields
        minHour.setBounds(870,570,50,20);
        maxHour.setBounds(920,570,50,20);
        minNbProdOrdered.setBounds(1020,570,100,20);
        minTimesOrdered.setBounds(1170,570,50,20);
        minPrice.setBounds(1220,570,50,20);
        day.setBounds(1320,570,100,20);

        reportButton1.addActionListener(e ->{
            Map<Order,List<MenuItem>> orders = ds.getOrders();
            int minHr,maxHr;
            try {
                minHr = Integer.parseInt(minHour.getText());
            }catch (NumberFormatException exception){
                minHr = 0;
            }

            try {
                maxHr = Integer.parseInt(maxHour.getText());
            }catch (NumberFormatException exception){
                maxHr = 24;
            }

            int finalMaxHr = maxHr;
            int finalMinHr = minHr;

            ds.generateReport1(finalMinHr,maxHr);

            JOptionPane.showMessageDialog(null,"Report generated!");
        });

        reportButton2.addActionListener(e -> {

            int minNb;
            try {
                minNb = Integer.parseInt(minNbProdOrdered.getText());
            }catch (NumberFormatException exception){
                minNb = 0;
            }
            int finalMinNb = minNb;

            ds.generateReport2(finalMinNb);

            JOptionPane.showMessageDialog(null,"Report generated!");
        });

        reportButton3.addActionListener(e -> {
            int minimumOrders;
            int minimumAmount;
            try {
                minimumOrders = Integer.parseInt(minTimesOrdered.getText());
            }catch (NumberFormatException exception){
                minimumOrders = 0;
            }
            try{
                minimumAmount = Integer.parseInt(minPrice.getText());
            }catch (NumberFormatException exception){
                minimumAmount = 0;
            }

            int finalMinimumAmount = minimumAmount;
            int finalMinimumOrders = minimumOrders;

            ds.generateReport3(finalMinimumOrders,finalMinimumAmount);

            JOptionPane.showMessageDialog(null,"Report generated!");
        });

        reportButton4.addActionListener(e -> {
            int specifiedDay;
            try{
                specifiedDay = Integer.parseInt(day.getText());
            }catch (NumberFormatException exception){
                specifiedDay = 1;
            }

            int finalSpecifiedDay = specifiedDay;

            ds.generateReport4(finalSpecifiedDay);

            JOptionPane.showMessageDialog(null,"Report generated!");
        });

        prodTable.getSelectionModel().addListSelectionListener(e -> {
            int i = prodTable.getSelectedRow();
            if (i != -1) {
                title.setText(prodModel.getValueAt(i, 0).toString());
                rating.setText(prodModel.getValueAt(i, 1).toString());
                calories.setText(prodModel.getValueAt(i, 2).toString());
                protein.setText(prodModel.getValueAt(i, 3).toString());
                fat.setText(prodModel.getValueAt(i, 4).toString());
                sodium.setText(prodModel.getValueAt(i, 5).toString());
                price.setText(prodModel.getValueAt(i, 6).toString());

                detailsModel.setRowCount(0);
                CompositeProduct cp = ds.getCompositeProduct(i);
                if(cp==null) //if not composite product then leave composite table empty
                    return;
                List<MenuItem> itmList = cp.getItemList(); //otherwise populate it with the respective items
                for (MenuItem itm :
                        itmList) {
                    detailsModel.addRow(new Object[]{
                            itm.getTitle(),
                            itm.getRating(),
                            itm.getCalories(),
                            itm.getProtein(),
                            itm.getFat(),
                            itm.getSodium(),
                            itm.getPrice()
                    });
                }
            }
        });

        addButton.addActionListener(e ->{
            try {
                ds.addItem(
                        new BaseProduct(title.getText(),
                                Double.parseDouble(rating.getText()),
                                Integer.parseInt(calories.getText()),
                                Integer.parseInt(protein.getText()),
                                Integer.parseInt(fat.getText()),
                                Integer.parseInt(sodium.getText()),
                                Integer.parseInt(price.getText())));

                prodModel.addRow(new Object[]{
                        title.getText(),
                        rating.getText(),
                        calories.getText(),
                        protein.getText(),
                        fat.getText(),
                        sodium.getText(),
                        price.getText()
                });
            }catch (NumberFormatException exception){
                JOptionPane.showMessageDialog(null,"Wrong input!");
            }
        });

        updateButton.addActionListener(e -> {
            int row = prodTable.getSelectedRow();
            if(row == -1)
                return;

                try {
                    ds.updateItem(row,
                            title.getText(),
                            Double.parseDouble(rating.getText()),
                            Integer.parseInt(calories.getText()),
                            Integer.parseInt(protein.getText()),
                            Integer.parseInt(fat.getText()),
                            Integer.parseInt(sodium.getText()),
                            Integer.parseInt(price.getText())
                    );

                    prodModel.setValueAt(title.getText(), row, 0);
                    prodModel.setValueAt(Double.parseDouble(rating.getText()), row, 1);
                    prodModel.setValueAt(Integer.parseInt(calories.getText()), row, 2);
                    prodModel.setValueAt(Integer.parseInt(protein.getText()), row, 3);
                    prodModel.setValueAt(Integer.parseInt(fat.getText()), row, 4);
                    prodModel.setValueAt(Integer.parseInt(sodium.getText()), row, 5);
                    prodModel.setValueAt(Integer.parseInt(price.getText()), row, 6);
                }catch (NumberFormatException exception){
                    JOptionPane.showMessageDialog(null,"Wrong input!");
                }

        });

        deleteButton.addActionListener(e -> {
            if(prodTable.getSelectedRow() != -1) {
                // remove the selected row from the table model
                int []row = prodTable.getSelectedRows();
                for (int j : row) {
                    prodModel.removeRow(j);
                    ds.deleteItem(j);
                }
                JOptionPane.showMessageDialog(null, "Deleted successfully");
            }
        });

        initButton.addActionListener(e -> {
            prodModel.setRowCount(0);

            List<MenuItem> items = ds.initMenu();
            for (MenuItem itm:
                 items) {
                prodModel.addRow(new Object[]{itm.getTitle(),
                        itm.getRating().toString(),
                        itm.getCalories()+"",
                        itm.getProtein()+"",
                        itm.getFat()+"",
                        itm.getSodium()+"",
                        itm.getPrice()+""});
            }
        });

        selectItmBtn.addActionListener(e -> {
            int []row = prodTable.getSelectedRows();
            for (int j : row) {
                composeModel.addRow(new Object[]{
                        prodTable.getValueAt(j, 0),
                        prodTable.getValueAt(j, 1),
                        prodTable.getValueAt(j, 2),
                        prodTable.getValueAt(j, 3),
                        prodTable.getValueAt(j, 4),
                        prodTable.getValueAt(j, 5),
                        prodTable.getValueAt(j, 6)
                });
            }
        });

        composeBtn.addActionListener(e -> {
            CompositeProduct cp = new CompositeProduct();
            for(int i = 0;i < composeModel.getRowCount();i++){
                BaseProduct bp = new BaseProduct(
                        composeModel.getValueAt(i, 0).toString(),
                        Double.parseDouble(composeModel.getValueAt(i, 1).toString()),
                        Integer.parseInt(composeModel.getValueAt(i, 2).toString()),
                        Integer.parseInt(composeModel.getValueAt(i, 3).toString()),
                        Integer.parseInt(composeModel.getValueAt(i, 4).toString()),
                        Integer.parseInt(composeModel.getValueAt(i, 5).toString()),
                        Integer.parseInt(composeModel.getValueAt(i, 6).toString())
                );
                cp.addItem(bp);
            }
            String title = JOptionPane.showInputDialog("Enter name of the menu");
            if(title == null)
                title = "Default menu";
            cp.setTitle(title);
            cp.computeRating();
            ds.addItem(cp);
            prodModel.addRow(new Object[]{
                    cp.getTitle(),
                    cp.getRating(),
                    cp.getCalories(),
                    cp.getProtein(),
                    cp.getFat(),
                    cp.getSodium(),
                    cp.getPrice()
            });
        });

        saveBtn.addActionListener(e -> {
            try {
                Serializator.Serialize(ds);
            } catch (IOException ioException) {
                ioException.printStackTrace();
                System.err.println("Error when serializing!");
            }
        });

        clearCompose.addActionListener(e -> {
            composeModel.setRowCount(0);
        });

        backBtn.addActionListener(e -> {
            dispose();
            Login.getInstance().setVisible(true);
        });

        prodTable.setCellSelectionEnabled(true);
        prodTable.setBounds(40,50,200,400);
        prodTable.setDefaultEditor(Object.class,null);

        composeTable.setCellSelectionEnabled(true);
        composeTable.setBounds(40,50,200,400);
        composeTable.setDefaultEditor(Object.class,null);

        detailsTable.setCellSelectionEnabled(true);
        detailsTable.setBounds(40,50,200,400);
        detailsTable.setDefaultEditor(Object.class,null);

        prodScrollPane = new JScrollPane(prodTable);
        prodScrollPane.setBounds(30,230,720,140);

        composeScrollPane = new JScrollPane(composeTable);
        composeScrollPane.setBounds(780,30,720,400);

        detailsScrollPane = new JScrollPane(detailsTable);
        detailsScrollPane.setBounds(30,450,720,300);

        add(composeScrollPane);
        add(prodScrollPane);
        add(detailsScrollPane);

        setVisible(true);
    }

    private void initTable(List<MenuItem> itemList){
        prodModel.setRowCount(0);
        for (MenuItem itm:
                itemList) {
            prodModel.addRow(new Object[]{
                    itm.getTitle(),
                    itm.getRating(),
                    itm.getCalories(),
                    itm.getProtein(),
                    itm.getFat(),
                    itm.getSodium(),
                    itm.getPrice()
            });
        }
    }
}
