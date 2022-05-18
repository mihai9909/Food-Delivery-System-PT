package Data;

import Business.DeliveryService;

import java.io.*;

public class Serializator {
    public static void Serialize(DeliveryService ds) throws IOException {
        FileOutputStream fileOutputStream
                = new FileOutputStream("file.txt");
        ObjectOutputStream objectOutputStream
                = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(ds);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public static DeliveryService Deserialize() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream
                = new FileInputStream("file.txt");
        ObjectInputStream objectInputStream
                = new ObjectInputStream(fileInputStream);
        DeliveryService deliveryService = (DeliveryService) objectInputStream.readObject();
        objectInputStream.close();
        return deliveryService;
    }
}
