package Business;

import Data.FileWriterAndReader;
import Presentation.Observer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface IDeliveryServiceProcessing extends Serializable {

    /**
     * @precondition the list shouldn't be null
     * @postcondition a new entry is added to the hashmap
     * */
    void addOrder(int clientID, List<MenuItem> items);

    /**
     * @precondition min must be positive and max less than 24
     * @postcondition report.txt is updated
     * */
    void generateReport1(int min, int max);

    /**
     * @precondition min must be positive
     * @postcondition report.txt is updated
     * */
    void generateReport2(int min);

    /**
     * @precondition minOrders and minAmount must be positive
     * @postcondition report.txt is updated
     * */
    void generateReport3(int minOrders, int minAmount);

    /**
     * @precondition day must be a day of the calendar
     * @postcondition report.txt is updated
     * */
    void generateReport4(int day);

    /**
     * @precondition item shouldn't be null
     * @postcondition item is added to the list of items
     * */
    void addItem(MenuItem item);

    void updateItem(int index, String title, Double rating,int calories,int protein,int fat,int sodium,int price);

    /**
     * @postcondition item at index is deleted from the list
     * */
    void deleteItem(int index);
}
