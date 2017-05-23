
public class DataMemory extends Memory {

	/* Attributes */
	private boolean write;
	
	/* Constructor */
	public DataMemory(int row, int length) {
		super(row, length);
		// TODO Auto-generated constructor stub
		data = new String[row];
		read = true;
		write = true;
	}
	
	/* Functions */
	/* add new data (initialize)*/
	static int address = 0;
	public void add(String val, int index){
		if(data[index] == null){
			data[index] = val;
			Main.tableData.getModel().setValueAt(val, index, 1);
			index++;
		}
		else
			System.out.println("The cell is already occupied.");
		
	}
	/* write new data */
	public void write(String value, int index){
		if(write && index<data.length) data[index] = value;
		else System.out.println("Write is not enabled.");
	}
	/* read data */
	public String read(int index){
		if(read && index<data.length) return data[index];
		else return null;
	}
}
