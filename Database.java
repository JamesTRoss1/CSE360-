package sample;

import java.util.Random;
import java.io.*;
import java.util.ArrayList;

public class Database {
    public Database() {
        File file = new File("orders.ser");
        if(!file.isFile()) {
            createDatabase();
        }
    }

    public void deleteDatabase() {
        File file = new File("orders.ser");
        file.delete();
    }

    //clears the database
    public void createDatabase() {
        try {
            FileOutputStream fileOut = new FileOutputStream("orders.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.close();
            fileOut.close();
        } catch (IOException i) {
        }
    }
    public boolean addOrder(Order order) {
        try {
            FileOutputStream fileOut = new FileOutputStream("orders.ser", true);
            AppendingObjectOutputStream out = new AppendingObjectOutputStream(fileOut);
            out.writeObject(order);
            out.close();
            fileOut.close();
            return true;
        } catch (IOException i) {
            return false;
        }
    }

    public ArrayList<Order> getAllOrders() {
        FileInputStream fileIn = null;
        ArrayList<Order> orders = new ArrayList<Order>();
        try {
            fileIn = new FileInputStream("orders.ser");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(fileIn);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            while(true) {
                orders.add((Order) in.readObject());
            }
        }
        catch (EOFException e) {
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
        }
        finally {
            try {
                fileIn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return orders;
    }

    public boolean foundOrderNumber(int orderNum) {
        ArrayList<Order> list = getAllOrders();
        boolean found = false;
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getOrderNumber() == orderNum) {
                found = true;
            }
        }
        return found;
    }

    public Order getOrder(int orderNum) {
        Order order = null;
        ArrayList<Order> list = getAllOrders();
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getOrderNumber() == orderNum) {
                order = list.get(i);
            }
        }
        return order;
    }

    public void changeOrderStatus(int orderNum, String status) {
        ArrayList<Order> list = getAllOrders();
        createDatabase();
        for(int i = 0; i < list.size(); i++) {
            if(list.get(i).getOrderNumber() == orderNum) {
                list.get(i).setStatus(status);
            }
            addOrder(list.get(i));
        }
    }

    public void printOrders() {
        ArrayList<Order> list = getAllOrders();
        System.out.println("\n");
        for(int i = 0; i < list.size(); i++) {
            System.out.println("ASURITE: " + list.get(i).getAsu());
            System.out.println("Order #: " + list.get(i).getOrderNumber());
            System.out.println("Pizza: " + list.get(i).getPizza().toString());
            System.out.println("Status: " + list.get(i).getStatus());
            System.out.println("Price: " + list.get(i).getPrice());
            System.out.println("Pickup Time: " + list.get(i).getPickup().toString());
        }
        System.out.println("\n");
    }

    public boolean deleteOrder(int orderNum) {
        boolean deleted = false;
        ArrayList<Order> list = getAllOrders();
        createDatabase();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getOrderNumber() != orderNum) {
                addOrder(list.get(i));
            }
            else {
                deleted = true;
            }
        }
        return deleted;
    }

    public int generateOrderNumber() {
        Random rd = new Random();
        int orderNumber = rd.nextInt(100);
        while(foundOrderNumber(orderNumber)) {
          orderNumber = rd.nextInt(100);
        }
        return orderNumber;
    }

}
