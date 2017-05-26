import java.util.ArrayList;
import java.util.HashMap;

public class Computer {
	/* ATTRIBUTES */
	/* Memories */
	private InstructionMemory instructionMemory;
	private DataMemory dataMemory;
	/* Registers */
	private Register inpr, outr, addressRegister;
	private InstructionRegister instructionRegister;
	private Register rgs[];
	private ProgramCounter programCounter;
	private StackPointer stackPointer;
	
	/* Others */
	private SequenceCounter sc;
	private String[][] labelTable;
	private LinkedList ll;
	public static int base = 2;
	public static int current = 0;
	/* CONSTRUCTOR */
	public Computer(String assembly){
		/* GENERATE */
		/* label table */
		labelTable = new String[16][3];
		/* memory segments */
		instructionMemory = new InstructionMemory();
		dataMemory = new DataMemory(16, 4);
		/* registers */
		addressRegister = new Register();
		instructionRegister = new InstructionRegister();
		programCounter = new ProgramCounter();
		stackPointer = new StackPointer();
		sc = new SequenceCounter();
		rgs = new Register[3];
		for(int i=0; i<rgs.length; i++)
			rgs[i] = new Register();
		inpr= new Register();
		outr = new Register();
		
		/* states */
		//states = new State[32][5];
		ll = new LinkedList();
	
		/* RUN */
		convert(assembly.replaceAll("\\r", ""));
		updateLabels();

		while(iterate()){
			System.out.println("a");
		}
	}
	
	
	/* METHODS */
	/* run the computer after instructions are retrieved */
	String op;
	String q, s2, s1, d;
	public boolean iterate(){
		int scTemp = sc.getT();
		int pcTemp = programCounter.getData();
		// end of code
		if(sc.getT()==0 && instructionMemory.getInstruction(programCounter.getData())==null){
			return false;
		}
		/* Fetch */
		if(sc.getT() == 0){
			instructionRegister.setInstruction(instructionMemory.getInstruction(programCounter.data));
			sc.increase();
			
			if(ll.getTail()!=null){
				ll.getTail().setEndOfLine(true);
			}
		}
		else if(sc.getT() == 1){
			programCounter.increase();
			sc.increase();
		}
		
		/* Decode */
		else if(sc.getT() == 2){
			q = instructionRegister.getInstruction().getString().charAt(0)+"";
			op = instructionRegister.getInstruction().getString().substring(1, 5);
			d = instructionRegister.getInstruction().getString().substring(5, 7);
			s1 = instructionRegister.getInstruction().getString().substring(7,9);
			s2 = instructionRegister.getInstruction().getString().substring(9);
			sc.clear();
		}
		
		/* Execute */
		else if(sc.getT() == 3){
			
			/*switch(op){
			
			case "0000":
				rgs[Integer.parseInt(convertNumber(d, 2, 10, 2))].setData(convertNumber(String.valueOf(), 10, 2, 4));
				break;
			}*/
		}
	
		ll.add(new State( instructionMemory, dataMemory, inpr, outr, addressRegister, instructionRegister, rgs, programCounter, stackPointer));
		return true;
		
	}	
	/* updates label table on GUI */
	public void updateLabels(){
		int i=0;
		for(String[] label:labelTable){
			if(label[0]!=null){
				Main.tableLabel.getModel().setValueAt(label[0], i, 0);
				Main.tableLabel.getModel().setValueAt(label[1], i, 1);
				Main.tableLabel.getModel().setValueAt(convertNumber(label[2], 10, 2, 4), i, 2);
				i++;
			}
			Main.tableLabel.repaint();
		}
		
	}
	
	/* Converter */
	/* Converter attributes */
	private String content[][];
	private HashMap<String, String> opcodes, registers;
	/* Converter functions */
	public void convert(String text){	
		
		String tempa[] = text.split("\n");
		String contentJagged[][] = new String[tempa.length][];
		for (int i = 0; i < contentJagged.length; i++) {
			contentJagged[i] = new String[tempa[i].split(" ").length];
		}
		
		/* Describe opcodes */
		opcodes = new HashMap<String, String>();
		opcodes.put("ADD", "0000");
		opcodes.put("INC", "0001");
		opcodes.put("DBL", "0010");
		opcodes.put("DBT", "0011");
		opcodes.put("NOT", "0100");
		opcodes.put("AND", "0101");
		opcodes.put("LD" , "0110");
		opcodes.put("ST" , "0111");
		opcodes.put("CAL", "1010");
		opcodes.put("RET", "1011");
		opcodes.put("JMP", "1100");
		opcodes.put("JMR", "1101");
		opcodes.put("PSH", "1110");
		opcodes.put("POP", "1111");
		opcodes.put("HLT", "1000");
		
		/* Describe registers */
		registers = new HashMap<String, String>();
		registers.put("R0", "00");
		registers.put("R1", "01");
		registers.put("R2", "10");
		registers.put("R3", "11");
		registers.put("INPR", "11");
		registers.put("OUTR", "11");
		
		/* Determine line number of assembly code */
		int count = 0;
		for (int i = 0; i < text.length(); i++) {
			if(text.charAt(i)=='\n')
				count++;
		}
		content = new String[count][6];
		
		/* Remove comments and parse word by word and line by line */
		int row = 0, column = 0;
		String temp = "";
		boolean isComment = false;
		for (int i = 0; i < text.length(); i++) {
			if(text.charAt(i) == '%')
				isComment = true;
	
			if(!isComment){
				if(text.charAt(i) != ' ' && text.charAt(i) != '\n'){
					temp = temp.concat(""+text.charAt(i));
				}
				else if(!temp.trim().equals("") && text.charAt(i) == ' '){
					content[row][column] = temp;
					temp = "";
					column++;
				}
			}
			if(text.charAt(i) == '\n'){
				isComment = false;
				row++;
				column = 0;
			}
				
		}
		for (int i = 0; i < content.length; i++) {
			Main.tableAssembly.getModel().setValueAt(String.join(" ", content[i]).replaceAll("null", ""), i, 0);
			Main.tableAssembly.changeSelection(i, 0, false, false);
		}
		
		identify();
	}
	/* Parse the expression */
	public void identify(){
		String code[] = new String[32];
		String rgs[];
		boolean isMemory = false; // instruction or data
		int mc=0, pc=0, lc=0;
		String tmp;
		
		/* Go through all lines */
		for (int i = 0; i < content.length; i++) {
			tmp = "";
			rgs = null;
			
			switch(content[i][0]){
			/* Beginning */
			case "ORG":
				/* Instruction Segment */
				if(content[i][1].equals("C")){
					isMemory = false;
					pc = Integer.parseInt(content[i][2]); // set program counter
					programCounter.setData(pc);
					Main.tf_pc.setText(convertNumber(String.valueOf(pc), 10, 2, 4));
				}
				/* Data Memory Segment */
				else if(content[i][1].equals("D")){
					isMemory = true;
					mc = Integer.parseInt(content[i][2]); // memory counter
				}
				break;
			
			/* Halt */
			case "HLT":
				code[pc] = "01000000000";
				pc++;
				break;
			
			/***** Arithmetic and Logic Operations *****/
			/* Addition */
			case "ADD":
				rgs = content[i][1].split(","); 
				tmp = "0".concat(opcodes.get("ADD").concat(registers.get(rgs[0]).concat(registers.get(rgs[1]).concat(registers.get(rgs[2])))));
				code[pc] = tmp;
				pc++;
				break;
			
			/* Increment */
			case "INC":
				rgs = content[i][1].split(","); 
				tmp = "0".concat(opcodes.get("INC").concat(registers.get(rgs[0]).concat(registers.get(rgs[1]).concat("00"))));
				code[pc] = tmp;
				pc++;
				break;
			
			
			/* Double */
			case "DBL":
				rgs = content[i][1].split(","); 
				tmp = "0".concat(opcodes.get("DBL").concat(registers.get(rgs[0]).concat(registers.get(rgs[1]).concat("00"))));
				code[pc] = tmp;
				pc++;
				break;
				
			/* Divide by Two */
			case "DBT":
				rgs = content[i][1].split(","); 
				tmp = "0".concat(opcodes.get("DBT").concat(registers.get(rgs[0]).concat(registers.get(rgs[1]).concat("00"))));
				code[pc] = tmp;
				pc++;
				break;
				
			/* NOT - Complement */
			case "NOT":
				rgs = content[i][1].split(","); 
				tmp = "0".concat(opcodes.get("NOT").concat(registers.get(rgs[0]).concat(registers.get(rgs[1]).concat("00"))));
				code[pc] = tmp;
				pc++;
				break;
				
			/* And */
			case "AND":
				rgs = content[i][1].split(","); 
				tmp = "0".concat(opcodes.get("AND").concat(registers.get(rgs[0]).concat(registers.get(rgs[1]).concat(registers.get(rgs[2])))));
				code[pc] = tmp;
				pc++;
				break;
			
			/***** Data Transfer *****/
			/* Load */
			case "LD":
				rgs = content[i][1].split(",");
				if(rgs[rgs.length-1].contains("#")) tmp = "1";
				else tmp = "0";
				tmp = tmp.concat(opcodes.get("LD").concat(registers.get(rgs[0])));
				if(tmp.charAt(0)=='1') tmp = tmp.concat(convertNumber(rgs[rgs.length-1].substring(1), 10, 2, 4));
				else{ 
					for (int j = 0; j < content.length; j++) {
						if(content[j][0]!=null && content[j][0].replaceAll(":", "").equals(rgs[rgs.length-1].replaceAll("@", ""))){
							if(content[j][1].equals("DEC")){
								tmp = tmp.concat(convertNumber(content[j][2], 10, 2, 4));
							}
							else if(content[j][1].equals("HEX")){
								tmp = tmp.concat(convertNumber(content[j][2], 16, 2, 4));
							}
							else{
								tmp = tmp.concat(convertNumber(content[j][2], 2, 2, 4));
							}
						}
					}
				}
				code[pc] = tmp;
				pc++;
				break;	
			
			/* Store */
			case "ST":
				rgs = content[i][1].split(",");
				if(rgs[rgs.length-1].contains("#")) tmp = "1";
				else tmp = "0";
				tmp = tmp.concat(opcodes.get("ST").concat(registers.get(rgs[0])));
				if(tmp.charAt(0)=='1') tmp.concat(convertNumber(rgs[rgs.length-1].substring(1), 10, 2, 4));
				else{ 
					for (int j = 0; j < content.length; j++) {
						if(content[j][0]!=null && content[j][0].replaceAll(":", "").equals(rgs[rgs.length-1].replaceAll("@", ""))){
							if(content[j][1].equals("DEC")){
								tmp = tmp.concat(convertNumber(content[j][2], 10, 2, 4));
							}
							else if(content[j][1].equals("HEX")){
								tmp = tmp.concat(convertNumber(content[j][2], 16, 2, 4));
							}
							else{
								tmp = tmp.concat(convertNumber(content[j][2], 2, 2, 4));
							}
						}
					}
				}
				code[pc] = tmp;
				pc++;
				break;
				
			/* Transfer */
			case "TSF":
				rgs = content[i][1].split(",");
				tmp = tmp.concat("0").concat(opcodes.get("TSF")).concat(registers.get(rgs[0]).concat(registers.get(rgs[1]).concat("00")));
				code[pc] = tmp;
				pc++;
				break;
				
			/***** Program Control *****/
			/* Call */
			case "CAL":
				tmp = tmp.concat("0").concat(opcodes.get("CAL")).concat("0");
				/* checks if it is an integer */
				if(content[i][1].matches("^-?\\d+$"))
					tmp = tmp.concat(convertNumber(content[i][1], 10, 2, 5));
				else{
					int pctemp = pc+1;
					for (int j = i+1; j < rgs.length; j++) {
						if(content[j][0].substring(0, content[j][0].length()-1).equals(content[i][1])){
							break;
						}
						else{
							pctemp++;
						}
					}
					tmp = tmp.concat(convertNumber(String.valueOf(pctemp), 10, 2, 5));
				}
				code[pc] = tmp;
				pc++;
				break;
			
			/* Return */
			case "RET":
				tmp = "0".concat(opcodes.get("RET").concat("000000"));
				code[pc] = tmp;
				pc++;
				break;
				
			/* Jump */
			case "JMP":
				
			
			
			/* Jump Relatively */
			case "JMR":
				tmp = "0".concat(opcodes.get("JMR")).concat("00");
				if(content[i][1].contains("-")){
					String num = convertNumber(content[i][1].substring(1), 10, 2, 4);
					String numTmp = "";
					boolean flag = false;
					for (int j = num.length()-1; j >= 0; j--) {
						if(flag){
							if(num.charAt(j)=='1') numTmp = numTmp.concat("0");
							else if(num.charAt(j)=='0') numTmp = numTmp.concat("1");	
						}
						else{
						    if(num.charAt(j)=='1'){ 
						    	numTmp = numTmp.concat("1");
						    	flag = true;
						    }
							else if(num.charAt(j)=='0') numTmp = numTmp.concat("0");	
						}
						
					}
					tmp = tmp.concat(new StringBuilder(numTmp).reverse().toString());
				}
				else{
					tmp = tmp.concat(convertNumber(content[i][1], 10, 2, 4));
				}
				code[pc] = tmp;
				pc++;
				break;
				
			/* Push */
			case "PSH":
				tmp = "0".concat(opcodes.get("PSH").concat("0"));
				if(content[i][1].matches("^-?\\d+$"))
					tmp = tmp.concat(convertNumber(content[i][1], 10, 2, 5));
				code[pc] = tmp;
				pc++;
				break;
				
			/* Pop */
			case "POP":
				tmp = "0".concat(opcodes.get("POP").concat("0"));
				if(content[i][1].matches("^-?\\d+$"))
					tmp = tmp.concat(convertNumber(content[i][1], 10, 2, 5));
				code[pc] = tmp;
				pc++;
				break;
			
			case "END":
				break;
			/* Label */
			default:
				if(!isMemory){
					labelTable[lc] = new String[] {content[i][0].replace(":", ""), "0", String.valueOf(pc)};
					lc++;
				}
				else{
					String val = "";
					if(content[i][1].equals("BIN"))
						val = content[i][2];
					else if(content[i][1].equals("DEC"))
						val = convertNumber(content[i][2], 10, 2, 4);
					else if(content[i][1].equals("HEX"))
						val = convertNumber(content[i][2], 16, 2, 4);
					val = val.substring(val.length()-4);
					dataMemory.add(val, mc);
					labelTable[lc] = new String[3];
					labelTable[lc][0] = content[i][0].replace(":", "");
					labelTable[lc][1] = val;
					labelTable[lc][2] = String.valueOf(mc);
					mc++;
					lc++;
				}
			}
			
		}
		Instruction inst;
		for (int j = 0; j < code.length; j++) {
			if(code[j]!=null){
				inst = new Instruction(code[j]);
				instructionMemory.add(inst, j);
				Main.tableInstruction.getModel().setValueAt(code[j], j, 1);
			}
		}
		
	}
	
	/* Get value of label */
	public String getLabelValue(String label, boolean adr){
		String result = "";
		for(String[] row:labelTable){
			if(row[0].equals("label")){
				if(!adr) result = convertNumber(row[1], 10, 2, 4);
				else result = convertNumber(row[2], 10, 2, 5);
			}
		}
		return result;
	}
	/* Take sub array */
	public String[] slice(String[] array, int start, int end){
		String[] temp = new String[end-start];
		int j = 0;
		for (int i = start; i < end; i++) {
			temp[j] = array[i];
			j++;
		}
		return temp;
	}
	/* Convert decimal to 4 digit n based */
	public static String convertNumber(String num, int fromBase, int toBase, int completeDigit){
		int intVal;
		String result;
		intVal = Integer.valueOf(num, fromBase);
		result = Integer.toString(intVal, toBase);
		for (int i = result.length(); i < completeDigit; i++) {
			result = "0".concat(result);
		}
		return result.toUpperCase();
	}

	
	/* Get - set */
	public InstructionMemory getInstructionMemory() {
		return instructionMemory;
	}
	public DataMemory getDataMemory() {
		return dataMemory;
	}
	public Register getInpr() {
		return inpr;
	}
	public Register getOutr() {
		return outr;
	}
	public Register getAddressRegister() {
		return addressRegister;
	}
	public InstructionRegister getInstructionRegister() {
		return instructionRegister;
	}
	public Register[] getRgs() {
		return rgs;
	}
	public ProgramCounter getProgramCounter() {
		return programCounter;
	}
	public StackPointer getStackPointer() {
		return stackPointer;
	}
	public SequenceCounter getSc() {
		return sc;
	}
	public String[][] getLabelTable() {
		return labelTable;
	}
	public LinkedList getLL(){
		return ll;
	}



	
}
