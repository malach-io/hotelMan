package hotelMan;

import hotelManager.HRSentity.Entity;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Vector;

class JoinLabel{
	public JTextField field = new JTextField(10);
	public JLabel label;
	public JoinLabel(String name, MPanel panel){
		label = new JLabel(name);
		panel.north.add(label, BorderLayout.NORTH);
		panel.north.add(field, BorderLayout.NORTH);
	}
	public void removeLabel(MPanel panel){
		panel.north.remove(label);
		panel.north.remove(field);
	}
}

class JoinCombo{
	public JComboBox comboBox = new JComboBox();
	public JLabel label;
	public String tempString;
	public JoinCombo(String name, MPanel panel, String...list){
		for(String i : list){
			comboBox.addItem(i);
		}
		label = new JLabel(name);
		panel.center.add(label, BorderLayout.NORTH);
		panel.center.add(comboBox, BorderLayout.NORTH);
		
	}
	
	public JoinCombo(String name, MPanel panel){
		label = new JLabel(name);
		panel.center.add(label, BorderLayout.NORTH);
		panel.center.add(comboBox, BorderLayout.NORTH);
	}
	
	public JoinCombo(String name, JPanel panel){
		label = new JLabel(name);
		panel.add(label, BorderLayout.NORTH);
		panel.add(comboBox, BorderLayout.NORTH);
	}
}

class MPanel extends JPanel{
	public JPanel center = new JPanel();
	public JPanel north = new JPanel();
	public JPanel east = new JPanel();
	public JPanel west = new JPanel();
	public JPanel south = new JPanel();
	
	public MPanel(){
		center.setBorder(BorderFactory.createTitledBorder(""));
		north.setBorder(BorderFactory.createTitledBorder(""));
		south.setBorder(BorderFactory.createTitledBorder(""));
		add(north, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(south, BorderLayout.SOUTH);
	}
}

class EntryPanel{
	private String title;
	MPanel panel = new MPanel();
	public JoinLabel id = new JoinLabel(null, panel);
	public JoinLabel name = new JoinLabel("Name", panel);
	public JoinLabel phone = new JoinLabel("Phone Number", panel);
	public JButton confirmButton = new JButton("Confirm Entry");
	public EntryPanel(String title){
		this.title = title;
		id.label.setText(title + " ID");
		panel.setBorder(BorderFactory.createTitledBorder("Create " + title + " Entry"));
		panel.south.add(confirmButton);
		id.field.setEditable(false);
	}
}

public class HRSgui extends JFrame{
	private JTabbedPane tabPane = new JTabbedPane();
	private JTextArea roomChartDisplay = new JTextArea();
	private JScrollPane centerDisplay_scroll = new JScrollPane(roomChartDisplay, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
																				 JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	private JPanel southPanel = new JPanel();
	private MPanel mainPanel = new MPanel();
	private MPanel custViewPanel = new MPanel();
	private MPanel hotelViewPanel = new MPanel();
	private EntryPanel reservationPanel = new EntryPanel("Reservation");
	private EntryPanel hotelEntryPanel = new EntryPanel("Hotel");
	private EntryPanel custEntryPanel = new EntryPanel("Customer");
	private JoinLabel address = new JoinLabel("Address", hotelEntryPanel.panel);
	private JoinLabel rate = new JoinLabel("Room Rate", hotelEntryPanel.panel);
	private JoinLabel duration = new JoinLabel("Duration", reservationPanel.panel);
	private JoinLabel room = new JoinLabel("Room Number", reservationPanel.panel);
	private JoinCombo comboCustomer = new JoinCombo("Customer", reservationPanel.panel.north);
	private JoinCombo comboHotel = new JoinCombo("Hotel", reservationPanel.panel);
	private JoinCombo comboDate = new  JoinCombo("Date", reservationPanel.panel);
	private JoinCombo comboFloor = new JoinCombo("Floor", reservationPanel.panel, "1", "2", "3", "4", "5", "6", "7");
	private JoinCombo comboRoom = new JoinCombo("Room", reservationPanel.panel, "A", "B", "C", "D", "E", "F", "G", "H");
	private JButton exitButton = new JButton("Exit");
	private HashMap<String, Integer> roomMap = new HashMap<String, Integer>();
	private TreeMap<String, ArrayList<String>> hotelMapOld = new TreeMap<String, ArrayList<String>>();
	private TreeMap<String, ArrayList<String>> customerMapOld = new TreeMap<String, ArrayList<String>>();
	private TreeMap<String, HRSentity> hotelMap = new TreeMap<String, HRSentity>();
	private TreeMap<String, HRSentity> customerMap = new TreeMap<String, HRSentity>();
	private TreeMap<String, HRSentity> reservationMap = new TreeMap<String, HRSentity>();
	private Object[] tempObj ={1, 2, 3, 4, 5, 6};
	private Object[] hotelObj ={"hotel ID", "name", "phone", "address", "rate"};
	private Object[] customerObj = {"reservation ID", "customer ID", "hotel ID", "name", "phone", "date", "floor",
			"room", "duration"};
	HRSreport hotelCon = new HRSreport(hotelMapOld, "C:/Users/Super Kai/Documents/hotelReport.txt");
	HRSreport customerCon = new HRSreport(customerMapOld, "C:/Users/Super Kai/Documents/customerReport.txt");
	private char[][] roomArray ={
			{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'},
			{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'},
			{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'},
			{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'},
			{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'},
			{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'},
			{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'},};
	private char[][] reports = roomArray;
	private HashMap<String, HRSreport> reportsMap = new HashMap<String, HRSreport>();
	
	public HRSgui(){
		mainFrame();
		mainPanel();
		genViewPanel(custViewPanel, "Customer Entries", customerObj, customerMap);
		genViewPanel(hotelViewPanel, "Hotel Entries", hotelObj, hotelMap);
		reservationPanel();
		actionListener();
	}
	
	private void mainFrame(){
		setTitle("Hotel Management System");  
		setSize(1120,400);
		getContentPane().add(tabPane);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tabPane.addTab("Hotel Management System", mainPanel);
		tabPane.addTab("View Hotel Entries", hotelViewPanel);
		tabPane.addTab("View Customer Entries", custViewPanel);
		tabPane.addTab("Create Hotel Entry", hotelEntryPanel.panel);
		tabPane.addTab("Create Customer Entry", custEntryPanel.panel);
		tabPane.addTab("Create Reservation Entry", reservationPanel.panel);
		southPanel.setBorder(BorderFactory.createTitledBorder(""));
		southPanel.add(exitButton);		
		add(southPanel, BorderLayout.SOUTH);
		setVisible(true);
	}
	
	private void mainPanel(){
		JTextArea centerTextArea = new JTextArea();
		centerTextArea.append("Welcome to HRS :: Hotel Management System");
		centerTextArea.setFont(new Font(null, Font.BOLD, 12));
		LookAndFeel.installColorsAndFont(centerTextArea, "TableHeader.background", "TableHeader.foreground", "TableHeader.font");
		centerTextArea.setEditable(false);
		mainPanel.center.add(centerTextArea);
		for(int x = 1; x < 366; x++){
			comboDate.comboBox.addItem(Integer.toString(x));
		}
	}
	
	private void genViewPanel(MPanel panel, String name, Object[] obj, TreeMap map){
		panel.setBorder(BorderFactory.createTitledBorder("View " + name));
		JoinLabel recordsFound = new JoinLabel("Records Found", panel);
		recordsFound.field.setEditable(false);
		JTable centerJTable = new JTable();
		JScrollPane scrollPane = new JScrollPane(centerJTable);
	    scrollPane.setBorder(BorderFactory.createTitledBorder(name));
	    updateJTable(obj, map, centerJTable);
		recordsFound.field.setText(Integer.toString(map.size()));
		panel.south.add(scrollPane, BorderLayout.CENTER);
		//pack();
	}

	private void reservationPanel(){
		room.field.setEditable(false);
		reservationPanel.name.removeLabel(reservationPanel.panel);
		reservationPanel.phone.removeLabel(reservationPanel.panel);
		reservationPanel.id.field.setText("RES000");
		reservationPanel.panel.center.add(centerDisplay_scroll, BorderLayout.NORTH);
		centerDisplay_scroll.setBorder(BorderFactory.createTitledBorder("Rooms List"));
		displayRoomChartDefault();
	}
	
	private void actionListener(){
		exitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		
		tabPane.addChangeListener(new ChangeListener(){
	        public void stateChanged(ChangeEvent e){
	        	if(tabPane.getSelectedIndex() == 5){
	        		//displayRoomChart(comboDate.tempString, comboHotel.tempString);
	        	}
	        	if(hotelMap.size() == 0){
	    			if(tabPane.getSelectedIndex() == 5){
	            		JOptionPane.showMessageDialog(null, "No Hotel entries found\nCreate hotel entry for customer reservation.",
	            				null, JOptionPane.PLAIN_MESSAGE);
	            		tabPane.setSelectedIndex(3);
	            	}
	    		}
	        	if(customerMap.size() == 0){
	    			if(tabPane.getSelectedIndex() == 5){
	            		JOptionPane.showMessageDialog(null, "No Customer entries found\nCreate hotel entry for customer reservation.",
	            				null, JOptionPane.PLAIN_MESSAGE);
	            		tabPane.setSelectedIndex(4);
	            	}
	    		}
	        	if(tabPane.getSelectedIndex() >= 0){
	        		idNumber(hotelEntryPanel.id, "HOTEL", hotelMap);
	        		idNumber(custEntryPanel.id, "CUST", customerMap);
	        		idNumber(reservationPanel.id, "RES", reservationMap);
	    		}
	        }
	    });
		
		comboCustomer.comboBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JComboBox jcmbType = (JComboBox) e.getSource();
				String selectedReport = (String)jcmbType.getSelectedItem();
				String[] temp = selectedReport.split(" ");
				comboHotel.tempString = temp[0];
			}
		});
		
		comboHotel.comboBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JComboBox jcmbType = (JComboBox) e.getSource();
				String selectedReport = (String)jcmbType.getSelectedItem();
				String[] temp = selectedReport.split(" ");
				comboHotel.tempString = temp[0];
			}
		});
		comboDate.comboBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JComboBox jcmbType = (JComboBox) e.getSource();
				String selectedReport = (String)jcmbType.getSelectedItem();
				displayRoomChart(comboDate.tempString, comboHotel.tempString);
				comboDate.tempString = selectedReport;
			}
		});
		comboFloor.comboBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JComboBox jcmbType = (JComboBox) e.getSource();
				String selectedReport = (String)jcmbType.getSelectedItem();
				comboFloor.tempString = selectedReport;
			}
		});
		comboRoom.comboBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JComboBox jcmbType = (JComboBox) e.getSource();
				String selectedReport = (String)jcmbType.getSelectedItem();
				comboRoom.tempString = selectedReport;
			}
		});

		reservationPanel.confirmButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setDateToHotel(comboHotel.tempString, duration.field.getText(), comboDate.tempString, comboFloor.tempString, comboRoom.tempString);
				room.field.setText(comboFloor.tempString + comboRoom.tempString);
				if(confirmContent(reservationPanel.id, duration) == true &&
				   confirmContent(comboHotel, comboCustomer, comboDate, comboFloor, comboRoom) == true){
					customerMap.put(custEntryPanel.id.field.getText(), new HRSreservation(Entity.reservation, reservationPanel.id.field.getText(), comboHotel.tempString,
							comboCustomer.tempString, duration.field.getText(), comboDate.tempString, comboFloor.tempString, comboRoom.tempString));
					setText(null, custEntryPanel.id, custEntryPanel.name, custEntryPanel.phone, address, rate);
					tabPane.setSelectedIndex(0);
				}
			}
		});
		
		hotelEntryPanel.confirmButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(confirmContent(hotelEntryPanel.id, hotelEntryPanel.name, hotelEntryPanel.phone, address, rate) == true){
						hotelMap.put(hotelEntryPanel.id.field.getText(), new HRShotel(Entity.hotel, hotelEntryPanel.id.field.getText(), 
						hotelEntryPanel.name.field.getText(), hotelEntryPanel.phone.field.getText(), address.field.getText(), 
						rate.field.getText()));
						comboHotel.comboBox.addItem(hotelEntryPanel.id.field.getText() + " " + hotelEntryPanel.name.field.getText());
						setText(null, hotelEntryPanel.id, hotelEntryPanel.name, hotelEntryPanel.phone, address, rate);
						tabPane.setSelectedIndex(0);
				}
			}
		});

		custEntryPanel.confirmButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(confirmContent(custEntryPanel.id, custEntryPanel.name, custEntryPanel.phone) == true){
					customerMap.put(custEntryPanel.id.field.getText(), new HRScustomer(Entity.customer, custEntryPanel.id.field.getText(),
							custEntryPanel.name.field.getText(), custEntryPanel.phone.field.getText()));
					comboCustomer.comboBox.addItem(custEntryPanel.id.field.getText() + " " + custEntryPanel.name.field.getText());
					setText(null, custEntryPanel.id, custEntryPanel.name, custEntryPanel.phone, address, rate);
					tabPane.setSelectedIndex(0);
				}
			}
		});
	}
	
	public void setText(String text, JoinLabel... name){
		for(JoinLabel x : name){
			x.field.setText(text);
		}
	}
	
	public boolean confirmContent(JoinLabel... name){
		boolean fieldsNotEmpty = true;
		for(JoinLabel x : name){
			if(x.field.getText().isEmpty()){
				JOptionPane.showMessageDialog(null, "Please Enter " + x.label.getText(), null, JOptionPane.PLAIN_MESSAGE);
				fieldsNotEmpty = false;
			}
		}
		return fieldsNotEmpty;
	}
	
	public boolean confirmContent(JoinCombo... name){
		boolean fieldsNotEmpty = true;
		for(JoinCombo x : name){
			if(x.tempString == null){
				JOptionPane.showMessageDialog(null, "Please Select " + x.label.getText(), null, JOptionPane.PLAIN_MESSAGE);
				fieldsNotEmpty = false;
			}
		}
		return fieldsNotEmpty;
	}
	
	private void idNumber(JoinLabel id, String name, TreeMap map){
		id.field.setText(name + "00" + Integer.toString(map.size()));
		
	}
	
	private void displayRoomChart(String date, String entity){
		roomChartDisplay.setText("");
		if(hotelMap.isEmpty() == false){
			HRShotel y = (HRShotel) hotelMap.get(entity);
			String[][] arr = new String[6][7];
			int tempDate = 0;
			if(date != null){
				tempDate = Integer.parseInt(date);
			}
			for(int row = 0; row < arr.length; row++){
				for(int col = 0; col < arr[row].length; col++){
					print((row+1) + "");
					if(y.getRoomArray() != null){
						print("[" + y.getRoomArray()[tempDate][row][col] + "]  ");
					}
				}
				print("\n");
			}
		}
	}
	
	private void displayRoomChartDefault(){
		String[][] arr = new String[6][7];
		for(int row = 0; row < arr.length; row++){
			for(int col = 0; col < arr[row].length; col++){
				print((row+1) + "");
				print("[]  ");
			}
			print("\n");
		}
	}
	
	private void setDateToHotel(String entityID, String duration, String date, String floor, String room){
		HRShotel y = (HRShotel) hotelMap.get(entityID);
		y.setRoomArray(duration, date, floor, room);
	}
	
	private TableModel toTableModel(Object[] obj, TreeMap<String, HRSentity> map){
	    DefaultTableModel model = new DefaultTableModel(null, obj);
	    for(Entry<String, HRSentity> entry : map.entrySet()){
	    	Vector tempVector = new Vector();
	    	if(entry.getValue().entity.toString().equalsIgnoreCase("hotel")){
	    		HRShotel y = (HRShotel) entry.getValue();
	    		model.addRow(y.getVector());
	    	}
	    	if(entry.getValue().entity.toString().equalsIgnoreCase("reservation")){
	    		HRScustomer y = (HRScustomer) entry.getValue();
	    		model.addRow(y.getVector());
	    	}
		}
	    return model;
	}
	
	private void updateJTable(final Object[] obj, final TreeMap<String, HRSentity> map, final JTable centerJTable){
		tabPane.addChangeListener(new ChangeListener(){
	        public void stateChanged(ChangeEvent e){
	        	if(tabPane.getSelectedIndex() == 0 || tabPane.getSelectedIndex() == 1){
	        		centerJTable.setModel(toTableModel(obj, map));
	    		}
	        }
	    });
	}
	
	private void print(String text){
		int pos = roomChartDisplay.getText().length();
		roomChartDisplay.insert(text,pos);
	}
	
	private void deleteEntry(HashMap map, String entryID){
		map.remove(entryID);
	}
	
	private void modifyEntry(HashMap map, String entryID, Object obj){
		map.remove(entryID);
		map.put(entryID, obj);
	}
	
	
	public static void main (String []args){
		HRSgui tab = new HRSgui();
	}

}