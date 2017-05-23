
public class Register {
	/* Attributes */
	String data;
	boolean load, clear, clock;
	
	/* Constructor*/
	public Register(){
		clock = true;
		clear = false;
		load = false;
		data = "0000";
	}
	
	/* Methods */
	public void setData(String data){
		this.data = data;
	}
	public String getData(){
		return data;
	}
}
