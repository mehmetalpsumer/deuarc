
public class InstructionRegister {
	/* Attributes */
	private Instruction inst;
	
	/* Constructor */
	public InstructionRegister(){
		inst = null;
	
	}
	
	/* Functions */
	public Instruction getInstruction(){	
		return inst;
	}
	public void setInstruction(Instruction inst){
		this.inst = inst;
	}
}
