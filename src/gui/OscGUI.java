package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import main.OSC;

public class OscGUI extends JPanel implements ActionListener, DocumentListener {
	
	OSC tocont;
	JComboBox modecb, decrcb;
	JTextField timetf;
	
	
	void buildgui()
	{
		this.setLayout(new GridLayout(3, 2));
		
		
		this.add(new JLabel("Signal Mode"));
		
		String[] msa = {"Sinus", "Square", "Triange", "Sawtooth"};
		modecb = new JComboBox(msa);
		modecb.setSelectedIndex(0);
		this.add(modecb);
		modecb.addActionListener(this);
		
		this.add(new JLabel("Fade Mode"));
		
		
		String[] dsa = {"Linear", "Square", "SquareRoot", "Circular"};
		decrcb = new JComboBox(dsa);
		decrcb.setSelectedIndex(0);
		this.add(decrcb);
		decrcb.addActionListener(this);
		
		this.add(new JLabel("Fade Time"));
		
		
		timetf = new JTextField();
		timetf.setText("0.0");
		timetf.getDocument().addDocumentListener(this);
		this.add(timetf);
		
		
		
	}

	public OscGUI(OSC so)
	{
		tocont = so;
		buildgui();
	}
	
	
	
	public void UpdeteFromOSC()
	{
		modecb.setSelectedIndex(tocont.GetMode());
		decrcb.setSelectedIndex(tocont.GetDecr());
		timetf.setText(Double.toString(tocont.GetFout()));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == modecb)
		{
			tocont.SetMode(modecb.getSelectedIndex());
		}
		if(e.getSource() == decrcb)
		{
			tocont.SetDecr(decrcb.getSelectedIndex());
		}
		
		
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		try
		{
		tocont.SetFOutTime(Double.parseDouble(timetf.getText()));
		}
		catch(NumberFormatException err)
		{
			
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		try
		{
		tocont.SetFOutTime(Double.parseDouble(timetf.getText()));
		}
		catch(NumberFormatException err)
		{
			
		}
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		try
		{
		tocont.SetFOutTime(Double.parseDouble(timetf.getText()));
		}
		catch(NumberFormatException err)
		{
			
		}
	}
	
	
	
	
	
	
}
