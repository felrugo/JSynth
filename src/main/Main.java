package main;

import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;



public class Main {

	private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel("Hello World");
        frame.getContentPane().add(label);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
	
	public static void main(String[] args) {
	
		VirtualKeyboard mvk = new VirtualKeyboard();
		
		OSC mo = new OSC();
		
		Mixer mix = new Mixer();
		
		mix.RegisterAO(mo);
		
		//Pulse p = new Pulse();
		
		//BeepTest bt = new BeepTest();
		
		mvk.RegisterNoteListener(mo);
		
		
		//mix.RegisterAO(mo);
		
		JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel("Hello World");
        frame.getContentPane().add(label);
 
        //frame.addKeyListener(mvk);
        
        JSlider js = new JSlider(0, 10000);
        
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
        
        frame.add(js, BorderLayout.NORTH);
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
		
        long ctime;
        
		while(true)
		{
			mix.update();
		}
		}
		
	}


