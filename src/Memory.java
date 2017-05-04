
public class Memory {
	/* Attributes */
	private boolean read, write;
	private String data[];
	
	/* Constructor */
	public Memory(int row, int length){
		data = new String[row];
		read = true;
		write = true;
	}
	
	/* Methods */
	/* add new data */
	public void add(String val, int index){
		if(write){
			if(data[index] == null)
				data[index] = val;
			else
				System.out.println("The cell is already occupied.");
		}
		else{
			System.out.println("Writing is not enabled to memory.");
		}
	}
	/* remove data */
	public void remove(int index){
		if(write){
			if(data[index] != null) data[index] = null;
			else System.out.println("The cell is already empty.");
		}
		else System.out.println("");
	}
	/* fetch data */
	public String get(int index){
		if(read) return data[index];
		System.out.println("Read is not enabled.");
		return null;
	}
}
