package Business;

import Data.FileWriterAndReader;
import Data.Serializator;
import Presentation.Observer;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class DeliveryService extends Observable implements IDeliveryServiceProcessing{
    private static final long serialVersionUID = 6529685098267757690L;

    private Observer employee;

    Map<Order, List<MenuItem>> orderInfo;
    List<MenuItem> items;
    private static DeliveryService instance;

    private DeliveryService(){
        items = new ArrayList<>();
        orderInfo = new HashMap<>();
    }

    public void addOrder(int clientId, List<MenuItem> items){
        assert items != null : "items list is null";
        Order order = new Order(orderInfo.size()+1,clientId,new Date());
        orderInfo.put(order,items);

        FileWriterAndReader.createBill(order,items);
        employee.notifyEmployee(orderInfo.size()+"");
    }

    public static DeliveryService getInstance(){
        if(instance == null) {
            try {
                instance = Serializator.Deserialize();
            }catch (IOException | ClassNotFoundException e) {
                instance = new DeliveryService();
            }
        }
        return instance;
    }

    public Map<Order,List<MenuItem>> getOrders(){
        return orderInfo;
    }

    public CompositeProduct getCompositeProduct(int idx){
        if(items.get(idx) instanceof CompositeProduct){
            return (CompositeProduct) items.get(idx);
        }
        return null;
    }

    public List<MenuItem> initMenu(){
        items = FileWriterAndReader.readProducts();
        return items;
    }

    public void addItem(MenuItem item){
        assert item != null : "Item is null";
        items.add(items.size(),item);
    }

    public void deleteItem(int index){
        items.remove(index);
    }

    public void updateItem(int index, String title, Double rating,int calories,int protein,int fat,int sodium,int price){
        //items.set(index,item);
        MenuItem item = items.get(index);

        item.setTitle(title);
        item.setRating(rating);
        item.setCalories(calories);
        item.setProtein(protein);
        item.setFat(fat);
        item.setSodium(sodium);
        item.setPrice(price);

        items.set(index,item);
    }

    public List<MenuItem> getItems() {
        return items;
    }

    @Override
    public void register(Observer employee) {
        this.employee = employee;
    }

    public void generateReport1(int min, int max){
        assert min >= 0 && max <=24 : "Invalid values for min / max";

        List<Map.Entry<Order, List<MenuItem>>> result = orderInfo.entrySet().stream().filter(map -> map.getKey().getOrderDate().getHours() < max &&
                map.getKey().getOrderDate().getHours() >= min).collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();
        sb.append("Orders between hours ").append(min).append(" and ").append(max).append(":\n");
        for (Map.Entry<Order,List<MenuItem>> ent : result
        ) {
            sb.append("\t").append("ID:").append(ent.getKey().getOrderID()).append(" ").append(ent.getKey().getOrderDate()).append(" Client ID:").append(ent.getKey().getClientID()).append("\n");
        }
        sb.append("\n");
        FileWriterAndReader.writeReport(sb.toString());

    }

    public void generateReport2(int min){
        assert min >= 0 : "Invalid value for min";

        List<MenuItem> list = items.stream().filter(itm -> itm.getTimesOrdered() > min).collect(Collectors.toList());
        StringBuilder sb = new StringBuilder("Products ordered more than ");
        sb.append(min).append(" times:\n");
        for (MenuItem itm: list) {
            sb.append("\t").append(itm.getTitle()).append("\n");
        }
        sb.append("\n");

        FileWriterAndReader.writeReport(sb.toString());
    }

    public void generateReport3(int minOrders, int minAmount){
        assert minOrders >= 0 && minAmount >= 0: "Invalid value for minOrder / minAmount";

        StringBuilder sb = new StringBuilder("The clients that have ordered more than ");
        sb.append(minOrders).append(" times and the value of the order was higher than ").append(minAmount).append(" are:\n");
        //sum greater than set amount

        List<Map.Entry<Order,List<MenuItem>>> entries = orderInfo.entrySet().stream().
                filter(t -> t.getValue().stream().mapToInt(MenuItem::getPrice).sum() > minAmount).collect(Collectors.toList());

        List<Integer> clients = entries.stream().map(Map.Entry::getKey).map(Order::getClientID).distinct().collect(Collectors.toList());
        for (Integer c: clients) {
            long nbTimesClientOrdered = entries.stream().map(Map.Entry::getKey).filter(key -> key.getClientID() == c).count();
            if(nbTimesClientOrdered >= minOrders) {
                sb.append("\t").append(c).append("\n");
            }
        }
        sb.append("\n");
        FileWriterAndReader.writeReport(sb.toString());
    }

    public void generateReport4(int day){
        assert day >= 1  && day <= 31: "Invalid value for day";
        StringBuilder sb = new StringBuilder("Products ordered on day ");
        sb.append(day).append(" are:\n");

        List<MenuItem> items = orderInfo.entrySet().stream().filter(entry -> entry.getKey().getOrderDate().getDate() == day).map(Map.Entry::getValue).collect(ArrayList::new, List::addAll, List::addAll);
        List<MenuItem> distinctItems = items.stream().distinct().collect(Collectors.toList());
        for (MenuItem item: distinctItems) {
            sb.append("\t").append(item.getTitle()).append(" ").append(item.getTimesOrdered()).append("\n");
        }
        sb.append("\n");
        FileWriterAndReader.writeReport(sb.toString());
    }

    public List<MenuItem> search(String search,Double finalMinRate,Double finalMaxRate,int finalMinCal,int finalMaxCal,int finalMinProts,int finalMaxProts,int finalMinFat,int finalMaxFat,int finalMinSod,int finalMaxSod,int finalMinPrc,int finalMaxPrc){
        return getItems().stream().filter(
                itm -> itm.getTitle().contains(search)&&
                        itm.getRating()>= finalMinRate &&itm.getRating()<= finalMaxRate&&
                        itm.getCalories()>= finalMinCal && itm.getCalories()<= finalMaxCal&&
                        itm.getProtein()>= finalMinProts && itm.getProtein()<= finalMaxProts&&
                        itm.getFat()>= finalMinFat && itm.getFat()<= finalMaxFat&&
                        itm.getSodium()>= finalMinSod && itm.getSodium()<= finalMaxSod&&
                        itm.getPrice()>= finalMinPrc &&itm.getPrice()<= finalMaxPrc).collect(Collectors.toList());
    }
}
