package main;

/**
 * Interface a leütött hangok elkapásához.
 * @author felrugo
 *
 */
public interface NoteListener {

	/**
	 * Egy hang leütésekor hívódik meg.
	 * @param n a leütött hang.
	 */
	public void onNotePress(Note n);
	
	/**
	 * Egy hang felengedésekor hívódik meg.
	 * @param n A felengedett hang.
	 */
	public void onNoteRelease(Note n);
	
}
