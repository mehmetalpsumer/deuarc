
public class StackPointer extends Register{	
	/* Constructor */
	public StackPointer(){
		data = "0000";
	}
	
	/* Functions */
	public void increase(){
		int tmp = Integer.parseInt(Computer.convertNumber(data, 2, 10, 2));
		tmp++;
		data = Computer.convertNumber(String.valueOf(tmp), 10, 2, 4);
	}
	public void decrease(){
		int tmp = Integer.parseInt(Computer.convertNumber(data, 2, 10, 2));
		tmp--;
		data = Computer.convertNumber(String.valueOf(tmp), 10, 2, 4);
	}
}
