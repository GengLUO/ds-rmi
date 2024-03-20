package hotel;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class BookingSession extends UnicastRemoteObject implements IBookingSession {

    private List<BookingDetail> bookingDetails = new ArrayList<>();
    private BookingManager bookingManager;

    protected BookingSession(BookingManager bookingManager) throws RemoteException {
        this.bookingManager = bookingManager;
    }

    @Override
    public void addBookingDetail(BookingDetail bookingDetail) {
        synchronized (bookingDetails) {
            bookingDetails.add(bookingDetail);
        }
    }

    @Override
    public boolean bookAll() throws RemoteException {
        synchronized (bookingDetails) {
            for (BookingDetail detail : bookingDetails) {
                if (!bookingManager.isRoomAvailable(detail.getRoomNumber(), detail.getDate())) {
                    return false; // If any room is not available, cancel the transaction
                }
            }
            for (BookingDetail detail : bookingDetails) {
                bookingManager.addBooking(detail); // Book each room, assuming this is atomic
            }
            bookingDetails.clear(); // Clear the shopping cart after booking
            return true;
        }
    }
}
