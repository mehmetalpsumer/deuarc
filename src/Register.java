
public class Register {
	private boolean clock, load, clear;
	private int[] data;
	
	public Register(int size){
		data = new int[size];
		clock = false;
		load = false;
		clear = false;
	}
	
	
}
