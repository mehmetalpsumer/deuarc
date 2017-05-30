public class ProgramCounter{
	/* Attributes */
	int data;
	
	/* Constructor */
	public ProgramCounter(){
		data = 0;
	}
	
	/* Methods */
	public void increase(){
		data++;
	}
	public int getData() {
		return data;
	}
	public void setData(int data) {
		this.data = data;
	}
}
