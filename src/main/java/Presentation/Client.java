package Presentation;

import Business.DeliveryService;
import Business.MenuItem;
import Business.Order;
import Data.Serializator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Client extends JFrame {

    private int clientID;

    private List<MenuItem> filteredItems;
    private List<MenuItem> cartItems = new ArrayList<>();

    private static final String[] header = {"Title","Rating","Calories","Protein","Fat","Sodium","Price"};

    private JButton backBtn,searchBtn, addToCart, orderBtn, clearBtn;
    private JLabel min = new JLabel("Min"),max = new JLabel("Max");
    private JLabel searchLbl = new JLabel("Search:");
    private JLabel ratingLbl = new JLabel("Rating");
    private JLabel caloriesLbl = new JLabel("Calories");
    private JLabel proteinsLbl = new JLabel("Proteins");
    private JLabel fatsLbl = new JLabel("Fats");
    private JLabel sodiumLbl = new JLabel("Sodium");
    private JLabel priceLbl = new JLabel("Price");

    private JTextField search,minRating,maxRating,minCalories,maxCalories,minProteins,maxProteins,minFats,maxFats,minSodium,maxSodium,minPrice,maxPrice;
    private JTable table, cartTable;
    private DefaultTableModel model, cartModel;
    private JScrollPane scrollPane, cartScrollPane;
    private DeliveryService ds;


    public Client(int clientID) {
        this.clientID = clientID;

        setSize(1920, 1000);
        setLayout(null);
        setTitle("Table");
        setLocationRelativeTo(null);
        setResizable(false);

        ds = DeliveryService.getInstance();

        filteredItems = ds.getItems();

        min.setBounds(900,150,150,20);
        max.setBounds(1070,150,150,20);
        searchLbl.setBounds(800,100,320,20);
        ratingLbl.setBounds(800,170,320,20);
        caloriesLbl.setBounds(800,200,320,20);
        proteinsLbl.setBounds(800,230,320,20);
        fatsLbl.setBounds(800,260,320,20);
        sodiumLbl.setBounds(800,290,320,20);
        priceLbl.setBounds(800,320,320,20);

        add(min);
        add(max);
        add(searchLbl);
        add(ratingLbl);
        add(caloriesLbl);
        add(proteinsLbl);
        add(fatsLbl);
        add(sodiumLbl);
        add(priceLbl);

        search = new JTextField();
        minRating = new JTextField();
        maxRating = new JTextField();
        minCalories = new JTextField();
        maxCalories = new JTextField();
        minProteins = new JTextField();
        maxProteins = new JTextField();
        minFats = new JTextField();
        maxFats = new JTextField();
        minSodium = new JTextField();
        maxSodium = new JTextField();
        minPrice = new JTextField();
        maxPrice = new JTextField();

        search.setBounds(900,100,320,20);
        minRating.setBounds(900,170,150,20);
        maxRating.setBounds(1070,170,150,20);
        minCalories.setBounds(900,200,150,20);
        maxCalories.setBounds(1070,200,150,20);
        minProteins.setBounds(900,230,150,20);
        maxProteins.setBounds(1070,230,150,20);
        minFats.setBounds(900,260,150,20);
        maxFats.setBounds(1070,260,150,20);
        minSodium.setBounds(900,290,150,20);
        maxSodium.setBounds(1070,290,150,20);
        minPrice.setBounds(900,320,150,20);
        maxPrice.setBounds(1070,320,150,20);

        add(search);
        add(minRating);
        add(maxRating);
        add(minCalories);
        add(maxCalories);
        add(minProteins);
        add(maxProteins);
        add(minFats);
        add(maxFats);
        add(minSodium);
        add(maxSodium);
        add(minPrice);
        add(maxPrice);

        backBtn = new JButton("<-");
        searchBtn = new JButton("Search");
        addToCart = new JButton("Add to cart");
        clearBtn = new JButton("Clear");
        orderBtn = new JButton("Order");

        backBtn.setBounds(10,10,50,20);
        searchBtn.setBounds(985,350,150,20);
        addToCart.setBounds(600,70,150,20);
        clearBtn.setBounds(430,760,150,20);
        orderBtn.setBounds(600,760,150,20);

        add(backBtn);
        add(searchBtn);
        add(addToCart);
        add(clearBtn);
        add(orderBtn);

        orderBtn.addActionListener(e -> {
            try {
                for(MenuItem itm: cartItems)
                    itm.incrementTimesOrdered();
                for (MenuItem itm: filteredItems) {
                    System.out.println(itm.getTimesOrdered());
                }

                List<MenuItem> items = new ArrayList<>(cartItems);
                ds.addOrder(clientID,items);
                Serializator.Serialize(ds);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            JOptionPane.showMessageDialog(null,"Order registered!");
        });

        clearBtn.addActionListener(e ->{
            cartItems.clear();
            cartModel.setRowCount(0);
        });

        addToCart.addActionListener(e -> {
            int []row = table.getSelectedRows();
            for (int j : row) {
                MenuItem itm = filteredItems.get(j);
                cartItems.add(itm);

                //add item to cart table
                cartModel.addRow(new Object[]{
                        itm.getTitle(),
                        itm.getRating(),
                        itm.getCalories(),
                        itm.getProtein(),
                        itm.getFat(),
                        itm.getSodium(),
                        itm.getPrice()
                });
            }
        });

        backBtn.addActionListener(e -> {
            Login.getInstance().setVisible(true);
            dispose();
        });

        searchBtn.addActionListener(e -> {
            Double minRate = 0.0;
            Double maxRate = Double.MAX_VALUE;
            int minCal;
            int maxCal;
            int minProts;
            int maxProts;
            int minFat;
            int maxFat;
            int minSod;
            int maxSod;
            int minPrc;
            int maxPrc;

            try {
                minRate = Double.parseDouble(minRating.getText());
            }catch (NumberFormatException exception){
                minRate = 0.0;
            }
            try {
                maxRate = Double.parseDouble(maxRating.getText());
            }catch (NumberFormatException exception){
                maxRate = Double.MAX_VALUE;
            }

            try {
                minCal = Integer.parseInt(minCalories.getText());
            }catch (NumberFormatException exception){
                minCal = 0;
            }
            try {
                maxCal = Integer.parseInt(maxCalories.getText());
            }catch (NumberFormatException exception){
                maxCal = Integer.MAX_VALUE;
            }

            try {
                minProts = Integer.parseInt(minProteins.getText());
            }catch (NumberFormatException exception){
                minProts = 0;
            }
            try {
                maxProts = Integer.parseInt(maxProteins.getText());
            }catch (NumberFormatException exception){
                maxProts = Integer.MAX_VALUE;
            }

            try {
                minFat = Integer.parseInt(minFats.getText());
            }catch (NumberFormatException exception){
                minFat = 0;
            }
            try {
                maxFat = Integer.parseInt(maxFats.getText());
            }catch (NumberFormatException exception){
                maxFat = Integer.MAX_VALUE;
            }

            try {
                minSod = Integer.parseInt(minSodium.getText());
            }catch (NumberFormatException exception){
                minSod = 0;
            }
            try {
                maxSod = Integer.parseInt(maxSodium.getText());
            }catch (NumberFormatException exception){
                maxSod = Integer.MAX_VALUE;
            }

            try {
                minPrc = Integer.parseInt(minPrice.getText());
            }catch (NumberFormatException exception){
                minPrc = 0;
            }
            try {
                maxPrc = Integer.parseInt(maxPrice.getText());
            }catch (NumberFormatException exception){
                maxPrc = Integer.MAX_VALUE;
            }

            Double finalMinRate = minRate;
            Double finalMaxRate = maxRate;
            int finalMaxCal = maxCal;
            int finalMinCal = minCal;
            int finalMaxProts = maxProts;
            int finalMinProts = minProts;
            int finalMinFat = minFat;
            int finalMaxFat = maxFat;
            int finalMinSod = minSod;
            int finalMaxSod = maxSod;
            int finalMinPrc = minPrc;
            int finalMaxPrc = maxPrc;

            filteredItems = ds.search(search.getText(), finalMinRate, finalMaxRate, finalMinCal, finalMaxCal, finalMinProts, finalMaxProts, finalMinFat, finalMaxFat, finalMinSod, finalMaxSod, finalMinPrc, finalMaxPrc);
            updateTable(filteredItems);
        });

        model = new DefaultTableModel(header,0);
        table = new JTable(model);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30,100,720,400);
        add(scrollPane);

        cartModel = new DefaultTableModel(header,0);
        cartTable = new JTable(cartModel);
        cartScrollPane = new JScrollPane(cartTable);
        cartScrollPane.setBounds(30,520,720,230);
        add(cartScrollPane);

        updateTable(filteredItems);
        setVisible(true);
    }

    private void updateTable(List<MenuItem> itemList){
        model.setRowCount(0);
        for (MenuItem itm:
             itemList) {
            model.addRow(new Object[]{
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
