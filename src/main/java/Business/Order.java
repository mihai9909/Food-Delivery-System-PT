package Business;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    private int orderID;
    private int clientID;
    private Date orderDate;

    public int getOrderID() {
        return orderID;
    }

    public int getClientID() {
        return clientID;
    }

    public Date getOrderDate() {
        return orderDate;
    }


    public Order(int orderID, int clientID, Date orderDate) {
        this.orderID = orderID;
        this.clientID = clientID;
        this.orderDate = orderDate;
    }

    public int hashCode(){
        return orderID+clientID+orderDate.hashCode();
    }

    @Override
    public String toString(){
        return "ID:" + orderID + ", Client:" + clientID + ", Date: " + orderDate.toString();
    }
}
