package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import gui.OscGUI;

/**
 * A főablak osztálya.
 * @author felrugo
 *
 */
public class Main extends JFrame {

	
	VirtualKeyboard mvk;
	OSC mo;
	OscGUI og;
	Mixer mix;
	JSlider js;
	JMenuItem jmiss, jmils;
	
	
	/**
	 * Elmenti az alkalmazás állapotát.
	 * @param f A mentéshez használt File.
	 */
	public void saveState(File f)
	{
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(f));
			pw.println(mo.GetAmp());
			pw.println(mo.GetMode());
			pw.println(mo.GetDecr());
			pw.println(mo.GetFout());
			pw.println(js.getValue());
			pw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Betölti az alakalmazás állapotát.
	 * @param f A betöltéshez használt File.
	 */
	public void loadState(File f)
	{
		 try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			mo.setAmp(Double.parseDouble(br.readLine()));
			mo.SetMode(Integer.parseInt(br.readLine()));
			mo.SetDecr(Integer.parseInt(br.readLine()));
			mo.SetFOutTime(Double.parseDouble(br.readLine()));
			js.setValue(Integer.parseInt(br.readLine()));
			og.UpdeteFromOSC();
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Megépíti az alaklmazás kezelőfelőletét.
	 */
	public void buildGUI()
	{
		JFrame alias = this; 
		
		JMenuBar jmb = new JMenuBar();
		JMenu jm = new JMenu("File");
		jmiss = new JMenuItem("Save State");
		jmiss.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mvk.SetActive(false);
				JFileChooser fileChooser = new JFileChooser();
				if (fileChooser.showSaveDialog(alias) == JFileChooser.APPROVE_OPTION) {
				  File file = fileChooser.getSelectedFile();
				  // save to file
				  saveState(file);
				}
				mvk.SetActive(true);
			}});
		jmils = new JMenuItem("Load State");
		jmils.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				mvk.SetActive(false);
				JFileChooser fileChooser = new JFileChooser();
				if (fileChooser.showOpenDialog(alias) == JFileChooser.APPROVE_OPTION) {
				  File file = fileChooser.getSelectedFile();
				  // load from file
				  loadState(file);
				}
				mvk.SetActive(true);
				
			}});
		jm.add(jmiss);
		jm.add(jmils);
		jmb.add(jm);
		
		this.setJMenuBar(jmb);
		
		js = new JSlider(0, 10000);
        
        double amp = Math.pow(2.0, (double) js.getValue() / 2000.0) - 1;
		mo.setAmp(amp);
        
        js.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				JSlider slide = (JSlider) e.getSource();
				double amp = Math.pow(2.0, (double) slide.getValue() / 2000.0) - 1;
				mo.setAmp(amp);
			}});
        
        this.add(js, BorderLayout.NORTH);
        
        og = new OscGUI(mo);
        this.add(og, BorderLayout.CENTER);
        
        
        
        JButton rec = new JButton("Start");
        rec.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(rec.getText().equals("Start"))
					rec.setText("Stop");
				else
					rec.setText("Start");
				mix.TogleRecording();
			}} );
        
        this.add(rec, BorderLayout.SOUTH);
        
        //Display the window.
        this.pack();
        this.setVisible(true);
	}
	
	
	/**
	 * Konstruktor.
	 */
	public Main()
	{
		super("JSynth");
		
		mvk = new VirtualKeyboard();
		
		mo = new OSC();
		
		mix = new Mixer(this);
		
		mix.RegisterAO(mo);
		
		mvk.RegisterNoteListener(mo);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		buildGUI();
		
		while(true)
		{
			mix.update();
		}
	}
	
	
	public VirtualKeyboard GetVK()
	{
		return mvk;
	}
	
	public OSC GetOSC()
	{
		return mo;
	}
	
	/**
	 * A belépési pont.
	 * @param args Az argumentumok.
	 */
	public static void main(String[] args) {
		
	
		
		Main m = new Main();
		
		}
		
	}


