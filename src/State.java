
public class State {
	/* Attributes */

	/* Registers */
	private String inpr, outr, addressRegister;
	private String instructionRegister;
	private String rgs[];
	private String programCounter;
	private String stackPointer;
	
	/* Constructor */
	public State(InstructionMemory instructionMemory, DataMemory dataMemory, Register inpr, Register outr,
			Register addressRegister, InstructionRegister instructionRegister, Register[] rgs,
			ProgramCounter programCounter, StackPointer stackPointer) {
		this.rgs = new String[3];
		this.inpr = inpr.getData();
		this.outr = outr.getData();
		this.addressRegister = addressRegister.getData();
		this.instructionRegister = instructionRegister.getInstruction().getString();
		this.programCounter = String.valueOf(programCounter.getData());
		this.stackPointer = stackPointer.getData();
		this.rgs[0] = rgs[0].getData();
		this.rgs[1] = rgs[1].getData();
		this.rgs[2] = rgs[2].getData();
		
	}

	/* Functions */
	public void draw(){
		// Program counter
		Main.tf_pc.setText(Computer.convertNumber(programCounter, 10, Computer.base, 5));
		Main.tf_pc.repaint();
		
		// Address register
		Main.tf_ar.setText(Computer.convertNumber(addressRegister, 2, Computer.base, 4));
		Main.tf_ar.repaint();
		
		// Stack pointer
		Main.tf_sp.setText(Computer.convertNumber(stackPointer, 2, Computer.base, 4));
		Main.tf_sp.repaint();
		
		// Instruction register
		Main.tf_ir.setText(instructionRegister);
		Main.tf_ir.repaint();
		
		// Common registers
		Main.tf_r0.setText(Computer.convertNumber(rgs[0], 2, Computer.base, 4));
		Main.tf_r0.repaint();
		Main.tf_r1.setText(Computer.convertNumber(rgs[1], 2, Computer.base, 4));
		Main.tf_r1.repaint();
		Main.tf_r2.setText(Computer.convertNumber(rgs[2], 2, Computer.base, 4));
		Main.tf_r2.repaint();
		Main.tf_rin.setText(Computer.convertNumber(inpr, 2, Computer.base, 4));
		Main.tf_rin.repaint();
		Main.tf_rout.setText(Computer.convertNumber(outr, 2, Computer.base, 4));
		Main.tf_rout.repaint();
	}
	
	
}
