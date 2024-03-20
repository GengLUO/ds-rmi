package hotel;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class BookingServer {
    public static void main(String[] args) {
        try {
            BookingManager manager = new BookingManager();
            IBookingManager stub = (IBookingManager) UnicastRemoteObject.exportObject(manager, 1099);

//            Registry registry = LocateRegistry.createRegistry(1099);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("BookingManager", stub);

            System.out.println("BookingManager is ready.");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
