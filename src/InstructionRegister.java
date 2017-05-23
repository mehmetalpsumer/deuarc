
public class InstructionRegister {
	Instruction inst;
	
	public InstructionRegister(){
		inst = null;
	}
	
	public Instruction getInstruction(){
		return inst;
	}
	
	public void setInstruction(Instruction inst){
		this.inst = inst;
	}
}
