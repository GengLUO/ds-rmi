package staff;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.util.Set;

import hotel.BookingDetail;
import hotel.BookingManager;
import hotel.IBookingManager;

public class BookingClient extends AbstractScriptedSimpleTest {

	private IBookingManager bm = null;

	public static void main(String[] args) throws Exception {
		String host = (args.length < 1) ? "dskul.australiaeast.cloudapp.azure.com" : args[0];
		int port = (args.length < 2) ? 1100: Integer.parseInt(args[1]);
		BookingClient client = new BookingClient(host, port);
		client.run();
	}

	/***************
	 * CONSTRUCTOR *
	 ***************/
	public BookingClient(String host, int port) {
		try {
			//Look up the registered remote instance
			Registry registry = LocateRegistry.getRegistry(host, port);
//			bm = new BookingManager();
			bm = (IBookingManager) registry.lookup("BookingManager");
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	@Override
	public boolean isRoomAvailable(Integer roomNumber, LocalDate date) {
		//Implement this method
		//return true;
		try {
			return bm.isRoomAvailable(roomNumber, date);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public void addBooking(BookingDetail bookingDetail) throws Exception {
		//Implement this method
		try {
			bm.addBooking(bookingDetail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Set<Integer> getAvailableRooms(LocalDate date) {
		//Implement this method
		try {
			return bm.getAvailableRooms(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Set<Integer> getAllRooms() {
//		return bm.getAllRooms();
		try {
			return bm.getAllRooms();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
