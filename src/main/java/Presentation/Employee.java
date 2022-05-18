package Presentation;

import Business.Observable;

public class Employee implements Observer {

    public Employee(Observable observable){
        observable.register(this);
    }

    public void notifyEmployee(String orderInfo){
        //TODO:Write to file
        System.out.println("Employee notified of order: " + orderInfo);
    }
}
