package Data;

import Business.BaseProduct;
import Business.MenuItem;
import Business.Order;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FileWriterAndReader {

    private static Function<String, MenuItem> mapToItem = (line) -> {

        String[] p = line.split(",");// a CSV has comma separated lines

        MenuItem item = new BaseProduct();

        item.setTitle(p[0]);//<-- this is the first column in the csv file
        if (p[1] != null && p[1].trim().length() > 0) {
            item.setRating(Double.parseDouble(p[1]));
        }
        if (p[2] != null && p[2].trim().length() > 0) {
            item.setCalories(Integer.parseInt(p[2]));
        }
        if (p[3] != null && p[3].trim().length() > 0) {
            item.setProtein(Integer.parseInt(p[3]));
        }
        if (p[4] != null && p[4].trim().length() > 0) {
            item.setFat(Integer.parseInt(p[4]));
        }
        if (p[5] != null && p[5].trim().length() > 0) {
            item.setSodium(Integer.parseInt(p[5]));
        }
        if (p[6] != null && p[6].trim().length() > 0) {
            item.setPrice(Integer.parseInt(p[6]));
        }

        return item;
    };

    public static List<MenuItem> readProducts(){
        List<MenuItem> items = new ArrayList<>();
        try {
            File inputF = new File("src\\main\\resources\\products.csv");
            InputStream inputFS = new FileInputStream(inputF);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
            items = br.lines().skip(1).map(mapToItem).collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return items;
    }

    public static void writeReport(String report){
        try{
            FileWriter fw = new FileWriter("report.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(report);
            bw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void createBill(Order order, List<MenuItem> items){
          try{
            FileWriter fw = new FileWriter("bill.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.newLine();
            bw.write(order.toString());
            bw.newLine();
            bw.write("Ordered: \n");
            int price = 0;
            for (MenuItem itm: items) {
                bw.write("\t" + itm.getTitle() + "  " + itm.getPrice() + "$" + "\n");
                price += itm.getPrice();
            }
            bw.write("Total: " + price + "$\n");
            bw.close();
        }catch (IOException e){
              e.printStackTrace();
          }
    }
}
