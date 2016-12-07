package main;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

/**
 * Osztály a billentyűzet kezeléshez. A billentyűzet bevitelét hangbevitellé alakítja.
 * @author felrugo
 *
 */
public class VirtualKeyboard {

	private volatile HashSet<NoteListener> nls;
	private volatile TreeSet<Note> pressed;
	private boolean active;
	
	
	/**
	 * Konstruktor.
	 */
	public VirtualKeyboard()
	{
		active = true;
		nls = new HashSet<NoteListener>();
		pressed = new TreeSet<Note>();
		
		
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
		  .addKeyEventDispatcher(new KeyEventDispatcher() {
		      @Override
		      public boolean dispatchKeyEvent(KeyEvent e) {
		        if(active)
		    	 switch(e.getID())
		        {
		        case KeyEvent.KEY_PRESSED:
		        	keyPressed(e);
		        	break;
		        case KeyEvent.KEY_RELEASED:
		        	keyReleased(e);
		        	break;
		        default:
		        	break;
		        }
		        return false;
		      }
		});
		
	}
	
	/**
	 * Aktiválja a billentyűk elkapását.(alapból aktív)
	 * @param set A beállítandó érték.
	 */
	public void SetActive(boolean set)
	{
		active = set;
	}
	
	public boolean isActive()
	{
		return active;
	}
	
	
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		Note n = null;
		switch(e.getKeyChar())
		{
		case 'y':
			n = new Note("C", 4);
				break;
		case 's':
			n = new Note("C#", 4);
				break;
		case 'x':
			n = new Note("D", 4);
				break;
		case 'd':
			n = new Note("D#", 4);
				break;
		case 'c':
			n = new Note("E", 4);
				break;
		case 'v':
			n = new Note("F", 4);
				break;
		case 'g':
			n = new Note("F#", 4);
				break;
		case 'b':
			n = new Note("G", 4);
				break;
		case 'h':
			n = new Note("G#", 4);
				break;
		case 'n':
			n = new Note("A", 4);
				break;
		case 'j':
			n = new Note("Bb", 4);
				break;
		case 'm':
			n = new Note("B", 4);
				break;
		case ',':
			n = new Note("C", 5);
				break;
			default:
				return;
		}
		
		if(!pressed.contains(n))
		{
			pressed.add(n);
			for(NoteListener nl : nls)
			{
				nl.onNotePress(n);
			}
		}
	}

	
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		Note n = null;
		switch(e.getKeyChar())
		{
		case 'y':
			n = new Note("C", 4);
				break;
		case 's':
			n = new Note("C#", 4);
				break;
		case 'x':
			n = new Note("D", 4);
				break;
		case 'd':
			n = new Note("D#", 4);
				break;
		case 'c':
			n = new Note("E", 4);
				break;
		case 'v':
			n = new Note("F", 4);
				break;
		case 'g':
			n = new Note("F#", 4);
				break;
		case 'b':
			n = new Note("G", 4);
				break;
		case 'h':
			n = new Note("G#", 4);
				break;
		case 'n':
			n = new Note("A", 4);
				break;
		case 'j':
			n = new Note("Bb", 4);
				break;
		case 'm':
			n = new Note("B", 4);
				break;
		case ',':
			n = new Note("C", 5);
				break;
			default:
				return;
		}
		
		if(pressed.contains(n))
		{
			pressed.remove(n);
			for(NoteListener nl : nls)
			{
				nl.onNoteRelease(n);
			}
		}
		
	}
	
	/**
	 * Beregisztrál egy hang leütés figyelőt.
	 * @param nl A figyelő.
	 */
	public void RegisterNoteListener(NoteListener nl)
	{
		nls.add(nl);
	}
	
	
}
