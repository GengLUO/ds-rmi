package hotel;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BookingManager implements IBookingManager{

	private Room[] rooms;

	public BookingManager() throws RemoteException{
		this.rooms = initializeRooms();
	}

	public synchronized Set<Integer> getAllRooms() {
		Set<Integer> allRooms = new HashSet<>();
		for (Room room : rooms) {
			allRooms.add(room.getRoomNumber());
		}
		return allRooms;
	}

	public synchronized boolean isRoomAvailable(Integer roomNumber, LocalDate date) {
		for (Room room : rooms) {
			if (room.getRoomNumber().equals(roomNumber)) {
				for (BookingDetail booking : room.getBookings()) {
					if (booking.getDate().equals(date)) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	public synchronized void addBooking(BookingDetail bookingDetail) throws RemoteException {
		//implement this method
		if (!isRoomAvailable(bookingDetail.getRoomNumber(), bookingDetail.getDate())) {
			throw new RemoteException("Room " + bookingDetail.getRoomNumber() + " is not available on " + bookingDetail.getDate());
		}
		for (Room room : rooms) {
			if (room.getRoomNumber().equals(bookingDetail.getRoomNumber())) {
				room.getBookings().add(bookingDetail);
				return;
			}
		}
	}

	public synchronized Set<Integer> getAvailableRooms(LocalDate date) {
		//implement this method
		//return null;
		Set<Integer> availableRooms = new HashSet<>();
		for (Room room : rooms) {
			if (isRoomAvailable(room.getRoomNumber(), date)) {
				availableRooms.add(room.getRoomNumber());
			}
		}
		return availableRooms;
	}

	@Override
	public IBookingSession createBookingSession() throws RemoteException {
        return new BookingSession(this); // Automatically exports the object and returns the stub to the client
	}

	private static Room[] initializeRooms() {
		Room[] rooms = new Room[4];
		rooms[0] = new Room(101);
		rooms[1] = new Room(102);
		rooms[2] = new Room(201);
		rooms[3] = new Room(203);

		return rooms;
	}
}
