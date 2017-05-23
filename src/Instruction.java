
public class Instruction {
	
	/* Attributes */
	private String q, opcode, d, s1, s2;
	
	/* Constructor */
	public Instruction(String instruction){
		q = instruction.substring(0, 1);
		opcode = instruction.substring(1, 5);
		d = instruction.substring(5,7);
		s1 = instruction.substring(7, 9);
		s2 = instruction.substring(9, 11);
	}

	/* Functions */
	public String getQ() {
		return q;
	}
	public String getOpcode() {
		return opcode;
	}
	public String getD() {
		return d;
	}
	public String getS1() {
		return s1;
	}
	public String getS2() {
		return s2;
	}
	public String getString(){
		return (""+q+opcode+d+s1+s2);
	}
}
