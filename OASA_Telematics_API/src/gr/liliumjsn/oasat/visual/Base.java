package gr.liliumjsn.oasat.visual;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedHashMap;

import com.mashape.unirest.http.exceptions.UnirestException;

import gr.liliumjsn.oasat.api.Arrival;
import gr.liliumjsn.oasat.api.DataResponseCallback;
import gr.liliumjsn.oasat.api.Line;
import gr.liliumjsn.oasat.api.OASAT;
import gr.liliumjsn.oasat.api.OASAT_API;
import gr.liliumjsn.oasat.api.Route;
import gr.liliumjsn.oasat.api.Stop;

import javax.swing.*;

public class Base extends JFrame {

	private static final long serialVersionUID = 1L;
	public static OASAT_API myAPI = new OASAT();
	static DefaultComboBoxModel<Line> linesModel = new DefaultComboBoxModel<Line>();
	static DefaultComboBoxModel<Route> routesModel = new DefaultComboBoxModel<Route>();
	static DefaultComboBoxModel<Stop> stopsModel = new DefaultComboBoxModel<Stop>();
	static JTextArea arrivals = new JTextArea("Select stop first");
	static Stop myStop;
	static JCheckBox autoRefreshCB = new JCheckBox("Auto Refresh");

	public Base() {

		this.setSize(450, 300);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		int xPos = (dim.width / 2) - (this.getWidth() / 2);
		int yPos = (dim.height / 2) - (this.getHeight() / 2);
		this.setLocation(xPos, yPos);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("OASA Telematics API");		
		
		final JButton refButton = new JButton();
		refButton.setText("Refresh");
		refButton.setEnabled(false);
		refButton.setPreferredSize(new Dimension(70,22));
		refButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				getStopArrivals(myStop);				
			}			
		});
		
		JPanel main_panel = new JPanel();
		main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));
		
		JPanel panel_1_cont = new JPanel();
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(new GridBagLayout());
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setLayout(new GridBagLayout());
		

		JLabel linesText = new JLabel("Select line");
		JLabel routesText = new JLabel("Select route");
		JLabel stopsText = new JLabel("Select stop");
		
		ActionListener refAr = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	System.out.println("----------Auto Refresh Called---------");
            	if(myStop != null){
        			getStopArrivals(myStop);
        		}
            }
        };
        final Timer refTimer = new Timer(30000 ,refAr);
		
		
		autoRefreshCB.setSelected(true);
		autoRefreshCB.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED){
					refButton.setEnabled(false);
					refTimer.start();
				}else{
					refButton.setEnabled(true);
					System.out.println("----------Auto Refresh Stopped---------");
					refTimer.stop();
				}
			}			
		});

		

		final JComboBox<Line> linesBox = new JComboBox<Line>(linesModel);
		linesBox.setPreferredSize(new Dimension(300,20));
		linesBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					getRoutes((Line) event.getItem());
				}
			}
		});

		final JComboBox<Route> routesBox = new JComboBox<Route>(routesModel);
		routesBox.setPreferredSize(new Dimension(300,20));
		routesBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					getStops((Route) event.getItem());
				}
			}
		});

		final JComboBox<Stop> stopsBox = new JComboBox<Stop>(stopsModel);
		stopsBox.setPreferredSize(new Dimension(150,20));
		stopsBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					myStop = (Stop) event.getItem();
					arrivals.setText("");
					getStopArrivals(myStop);
				}
			}
		});
		
		GridBagConstraints c = new GridBagConstraints();
		
		Insets ins = new Insets(0,5,5,5);

		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		panel_1_1.add(stopsBox,c);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 1;
		c.gridy = 0;
		panel_1_1.add(refButton,c);
		c.fill = GridBagConstraints.NONE;
		c.gridx = 2;
		c.gridy = 0;
		panel_1_1.add(autoRefreshCB, c);
		
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.WEST;
		ins.set(0, 0, 5, 5);
		c.insets = ins;
		panel_1.add(linesText, c);		
		
		c.anchor = GridBagConstraints.EAST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		ins.set(0, 5, 5, 0);
		c.insets = ins;
		panel_1.add(linesBox, c);
		
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 1;
		ins.set(5, 0, 5, 5);
		c.insets = ins;
		panel_1.add(routesText, c);
		
		c.anchor = GridBagConstraints.EAST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
		ins.set(5, 5, 5, 0);
		c.insets = ins;
		panel_1.add(routesBox, c);
		
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 2;
		ins.set(5, 0, 0, 5);
		c.insets = ins;
		panel_1.add(stopsText, c);
		
		c.anchor = GridBagConstraints.EAST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		ins.set(5, 5, 0, 0);
		c.insets = ins;
		panel_1.add(panel_1_1, c);
		
		JScrollPane scrollPanel = new JScrollPane(arrivals);
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridwidth = 2;
		c.ipady = 100;
		c.gridy = 10;
		ins.set(25, 0, 5, 0);
		c.insets = ins;
		panel_1.add(scrollPanel, c);

		main_panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		panel_1_cont.add(panel_1);
		
		main_panel.add(panel_1_cont);
		

		// Get lines from web on startup
		getLines();

		this.add(main_panel);
		this.setVisible(true);
		refTimer.start();		
	}

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

		} catch (Exception e) {
			e.printStackTrace();
		}
		new Base();

	}

	public static void getLines() {
		myAPI.getLinesFromWeb(new DataResponseCallback<LinkedHashMap<String, Line>>() {
			public void onDataFailed(UnirestException arg0) {
				System.out.println("fff");
			}

			public void onDataReady(LinkedHashMap<String, Line> response) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (String k : response.keySet()) {
					linesModel.addElement(response.get(k));
					System.out.println("fff");
				}
			}
		});
	}

	public static void getRoutes(Line line) {
		myAPI.getRoutesForLineFromWeb(line, new DataResponseCallback<LinkedHashMap<Integer, Route>>() {
			public void onDataReady(LinkedHashMap<Integer, Route> response) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				routesModel.removeAllElements();
				for (int k : response.keySet()) {
					routesModel.addElement(response.get(k));
					System.out.println(response.get(k).getRouteDescrEng());
				}
			}

			public void onDataFailed(UnirestException arg0) {
				System.out.println("fff");
			}

		});
	}

	public static void getStops(Route route) {
		System.out.println("get stops called");
		myAPI.getStopsForRouteFromWeb(route, new DataResponseCallback<LinkedHashMap<Integer, Stop>>() {
			public void onDataReady(LinkedHashMap<Integer, Stop> response) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				stopsModel.removeAllElements();
				;
				System.out.println("Stops size: " + response.size());
				for (int key : response.keySet()) {
					stopsModel.addElement(response.get(key));
					System.out.println(response.get(key).getStopDescrEng());
				}
			}

			public void onDataFailed(UnirestException arg0) {
				System.out.println("fff");
			}

		});
	}

	public static void getStopArrivals(Stop stop) {
		myAPI.getArrivalsForStopFromWeb(stop, new DataResponseCallback<LinkedHashMap<Integer, Arrival>>() {
			public void onDataReady(LinkedHashMap<Integer, Arrival> response) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				myStop.setStopArrivals(response);
				getRoutesForStop(myStop);
			}

			public void onDataFailed(UnirestException arg0) {
				System.out.println("fff");
			}

		});
	}

	public static void getRoutesForStop(Stop stop) {
		myAPI.getRoutesForStopFromWeb(stop, new DataResponseCallback<LinkedHashMap<Integer, Route>>() {
			public void onDataReady(LinkedHashMap<Integer, Route> response) {
				myStop.setStopRoutes(response);
				arrivals.setText("");
				if (response != null) {
					for (int time : myStop.getStopArrivals().keySet()) {
						int routeCode = myStop.getStopArrivals().get(time).getRouteCode();
						String lineID = myStop.getStopRoutes().get(routeCode).getRouteLine().getLineID();
						// myStop.getStopRoutes().get(routeCode).getRouteDescrEng()
						String fomattedAr = lineID + " " + myStop.getStopRoutes().get(routeCode).getRouteDescrEng() + " " + time + "m\n";
						arrivals.append(fomattedAr);
						System.out.println(fomattedAr);
					}
				}
			}

			public void onDataFailed(UnirestException arg0) {
				System.out.println("fff");
			}

		});
	}

	public static void getClosestStops() {
		myAPI.getClosestStopsFromWeb(37.9784391D, 23.7665753D, new DataResponseCallback<LinkedHashMap<Double, Stop>>() {
			public void onDataReady(LinkedHashMap<Double, Stop> response) {
				System.out.println("Stop size is: " + response.size());
				for (Double key : response.keySet()) {
					System.out.println("Stop Name: " + response.get(key).getStopDescr() + " Stop distance: "
							+ response.get(key).getStopDistance());
				}
			}

			public void onDataFailed(UnirestException arg0) {
				System.out.println("fff");
			}

		});
	}

}
