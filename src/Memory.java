
public class Memory {
	/* Attributes */
	protected boolean read;
	protected String data[];
	
	/* Constructor */
	public Memory(int row, int length){
		data = new String[row];
		read = true;
		
	}
	
	/* Methods */
	/* add new data */
	public void add(String val, int index){
		if(data[index] == null){
			data[index] = val;
			Main.tableInstruction.getModel().setValueAt(val, index, 1);
			Main.tableInstruction.getModel().setValueAt(Main.tableInstructionCounter, index, 0);
			Main.tableInstructionCounter++;
		}
		else
			System.out.println("The cell is already occupied.");
		
	}
	/* fetch data */
	public String get(int index){
		if(read) return data[index];
		System.out.println("Read is not enabled.");
		return null;
	}
}
