
public class InstructionMemory {
	
	/* Attributes */
	private Instruction[] memory;
	
	/* Constructor */
	public InstructionMemory(){
		memory = new Instruction[32];	
	}
	
	/* Functions */
	public Instruction getInstruction(int index){
		if(index>=0 && index<32)
			return memory[index];
		return null;
	}
	public void add(Instruction code, int index){
		memory[index] = code;
	}
}
