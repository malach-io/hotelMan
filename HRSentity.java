package hotelMan;

import java.util.Vector;

import javax.swing.JOptionPane;

public class HRSentity{
	protected String name;
	protected String hotelID;
	protected String customerID;
	protected String reservationID;
	protected String phone;
	protected Entity entity;
	Vector tempVector = new Vector();
		
	public HRSentity(Entity entity, String hotelID) {
		this.entity = entity;
		this.hotelID = hotelID;
	}

	public HRSentity(Entity entity, String name, String phone) {
		this.entity = entity;
		this.name = name;
		this.phone = phone;
	}
	
	public HRSentity(Entity entity, String hotelID, String name, String phone){
		this.entity = entity;
		this.name = name;
		this.hotelID = hotelID;
		this.phone = phone;
		tempVector.add(this.hotelID);
		tempVector.add(this.name);
		tempVector.add(this.phone);
	}

	public enum Entity{
		hotel, customer, reservation;
	}
	
	public int toInt(String name){
		int tempInt = 0;
		if(name != null){
			tempInt = Integer.parseInt(name);
		}
		return tempInt;
	}

}

class HRShotel extends HRSentity{
	String address;
	String rate;
	int availableRooms;
	String[][][] roomArray = new String[365][6][7];
	
	public HRShotel(Entity entity, String hotelID, String name, String phone, String address, String rate){
		super(entity, hotelID, name, phone);
		this.address = address;
		this.rate = rate;
		tempVector.add(address);
		tempVector.add(rate);
		JOptionPane.showMessageDialog(null, "Stored: Hotel ID: " + this.hotelID + "\nName: " + this.name +
				"\nPhone Number: " + this.phone + "\nAddress: " + this.address + 
				"\nRoom Rate: " + this.rate, null, JOptionPane.PLAIN_MESSAGE);
	}
	
	public Vector getVector(){
		return tempVector;
	}
	
	public void setReservationID(String reservationID){
		this.reservationID = reservationID;
	}
	
	public String[][][] getRoomArray(){
		return roomArray;
	}
	public void setRoomArray(String duration, String date, String floor, String room){
		int tempDur = toInt(duration);
		int tempDate = toInt(date);
		int tempFloor = toInt(floor);
		int tempRoom = toInt(room);
		for(int x = 0; x < tempDur; x++, tempDate++){
			roomArray[tempDate][tempFloor][tempRoom] = reservationID;
		}
	}

}

class HRScustomer extends HRSentity{
	
	public HRScustomer(Entity entity, String customerID, String name, String phone){
		super(entity, name, phone);
		this.customerID = customerID;
		tempVector.add(this.reservationID);
		tempVector.add(this.customerID);
		JOptionPane.showMessageDialog(null, "Customer ID: " + this.customerID + "\nName: " + this.name +
				"\nPhone Number: " + this.phone, null, JOptionPane.PLAIN_MESSAGE);
	}
	
	public Vector getVector(){
		return tempVector;
	}	

}

class HRSreservation extends HRSentity{
	private int date;
	private int floor;
	private int room;
	private int duration;

	public HRSreservation(Entity entity, String reservationID, String hotelID, String customerID, 
			String duration, String date, String floor, String room){
		super(entity, hotelID);
		this.reservationID = reservationID;
		this.customerID = customerID;
		this.duration = toInt(duration);
		this.date = toInt(date);
		this.floor = toInt(floor);
		this.room = toInt(room);
		JOptionPane.showMessageDialog(null, "Stored: Reservation ID: " + this.reservationID + 
				"\nHotel ID: " + this.hotelID + "\nCustomer ID: " + this.customerID + "\nName: " + this.name +
				"\nPhone Number: " + this.phone + "\nDate: " + this.date + "\nFloor: " + this.floor + 
				"\nRoom: " + this.room + "\nDuration: " + this.duration, null, JOptionPane.PLAIN_MESSAGE);
	}
}