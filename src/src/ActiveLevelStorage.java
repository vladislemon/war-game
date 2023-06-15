package src;


public class ActiveLevelStorage {
	private int lastID = 0;
	
	public int genUniqueId() {
		lastID++;
		return lastID;
	}
	public static ActiveLevelStorage instance =  new ActiveLevelStorage();
}
