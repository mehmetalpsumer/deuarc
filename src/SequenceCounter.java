
public class SequenceCounter {
	/* Attributes */
	int t;
	
	/* Constructor */
	public SequenceCounter(){
		t = 0;
	}
	
	/* Methods */
	public void clear(){
		t = 0;
	}
	public int getT(){
		return t;
	}
	public void increase(){
		t++;
	}
}
