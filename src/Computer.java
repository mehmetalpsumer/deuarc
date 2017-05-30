import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Computer {
	/* ATTRIBUTES */
	/* Memories */
	private InstructionMemory instructionMemory;
	private DataMemory dataMemory;
	private Memory stackMemory;
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
	private int v;
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
		stackMemory = new Memory(16, 5);
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
		v = 0;
		/* states */
		//states = new State[32][5];
		ll = new LinkedList();

		/* RUN */
		convert(assembly.replaceAll("\\r", ""));


		while(iterate()){
			continue;
		}
		ll.getTail().setEndOfLine(true);
		ll.getHead().getValue().draw();
	}

	/* METHODS */
	/* run the computer after instructions are retrieved */
	String op;
	String q, s2, s1, d;
	public boolean iterate(){
		int temp = 0; // to do ba
		System.out.println("T is:"+sc.getT()+ " Op:" + op);
		// end of code
		if(sc.getT()==0 && instructionMemory.getInstruction(programCounter.getData())==null){
			return false;
		}


		/* Fetch */
		if(sc.getT() == 0){
			instructionRegister.setInstruction(instructionMemory.getInstruction(programCounter.data));
			sc.increase();
			System.out.println(instructionRegister.getInstruction().getString());
			if(ll.getTail()!=null){
				ll.getTail().setEndOfLine(true);
			}
			Main.curMicro.setText("IR<-IM[PC]");
		}
		else if(sc.getT() == 1){
			programCounter.increase();
			sc.increase();
			Main.curMicro.setText("PC<-PC+1");
		}

		/* Decode */
		else if(sc.getT() == 2){
			q = instructionRegister.getInstruction().getString().charAt(0)+"";
			op = instructionRegister.getInstruction().getString().substring(1, 5);
			d = instructionRegister.getInstruction().getString().substring(5, 7);
			s1 = instructionRegister.getInstruction().getString().substring(7,9);
			s2 = instructionRegister.getInstruction().getString().substring(9);
			sc.increase();
			Main.curMicro.setText("Q<-IR[10], S2<-IR[1..0], S1<-IR[3..2], D<-IR[5..4]");
		}

		/* Execute */
		else if(sc.getT() == 3){

			switch(op){

			/* arithmetic logic */
			case "0000":
				int temp2 = 0;
				temp = 0;
				if(s1.equals("11")){
					temp = Integer.parseInt(convertNumber(inpr.getData(), 2, 10, 2));
				}
				else{
					temp = Integer.parseInt(convertNumber(rgs[Integer.parseInt(convertNumber(s1, 2, 10, 2))].getData(), 2, 10, 2));
				}
				if(s2.equals("11")){
					temp2 = Integer.parseInt(convertNumber(inpr.getData(), 2, 10, 2));
				}
				else{
					temp2 = Integer.parseInt(convertNumber(rgs[Integer.parseInt(convertNumber(s2, 2, 10, 2))].getData(), 2, 10, 2));
				}
				if(d.equals("11")){
					outr.setData(convertNumber(String.valueOf(temp+temp2), 10, 2, 4));
				}
				else{
					rgs[Integer.parseInt(convertNumber(d, 2, 10, 2))].setData(convertNumber(String.valueOf(temp+temp2), 10, 2, 4));
				}
				
				if((convertNumber(String.valueOf(temp), 10, 2, 4).charAt(0)=='1' || convertNumber(String.valueOf(temp2), 10, 2, 4).charAt(0)=='1') && convertNumber(String.valueOf(temp+temp2), 10, 2, 4).charAt(0)=='0')
					v=1;
				else
					v=0;
				sc.clear();
				Main.curMicro.setText("D<-S1+S2, SC<-0");
				break;
			case "0001":
				temp = 0;
				if(s1.equals("11")){
					temp = Integer.parseInt(convertNumber(inpr.getData(), 2, 10, 2));
					if(inpr.getData().charAt(0)=='1')
						v=1;
					else 
						v=0;
				}
				else{
					temp = Integer.parseInt(convertNumber(rgs[Integer.parseInt(convertNumber(s1, 2, 10, 2))].getData(), 2, 10, 2));
					if(rgs[Integer.parseInt(convertNumber(s1, 2, 10, 2))].getData().charAt(0)=='1')
						v=1;
					else 
						v=0;
				}
				temp++;
				if(d.equals("11")){
					outr.setData(convertNumber(String.valueOf(temp), 10, 2, 4));
				}
				else{
					rgs[Integer.parseInt(convertNumber(d, 2, 10, 2))].setData(convertNumber(String.valueOf(temp), 10, 2, 4));
				}
				sc.clear();
				Main.curMicro.setText("D<-S1+1, SC<-0");
				break;
			case "0010":
				temp = 0;
				if(s1.equals("11")){
					temp = Integer.parseInt(convertNumber(inpr.getData(), 2, 10, 2));
				}
				else{
					temp = Integer.parseInt(convertNumber(rgs[Integer.parseInt(convertNumber(s1, 2, 10, 2))].getData(), 2, 10, 2));
				}
				temp*=2;
				if(d.equals("11")){
					outr.setData(convertNumber(String.valueOf(temp), 10, 2, 4));
				}
				else{
					rgs[Integer.parseInt(convertNumber(d, 2, 10, 2))].setData(convertNumber(String.valueOf(temp), 10, 2, 4));
				}
				Main.curMicro.setText("D<-S1+S1, SC<-0");
				break;
				
			case "0011":
				temp = 0;
				if(s1.equals("11")){
					temp = Integer.parseInt(convertNumber(inpr.getData(), 2, 10, 2));
				}
				else{
					temp = Integer.parseInt(convertNumber(rgs[Integer.parseInt(convertNumber(s1, 2, 10, 2))].getData(), 2, 10, 2));
				}
				
				if(d.equals("11")){
					outr.setData(Integer.toBinaryString(temp>>1));
				}
				else{
					rgs[Integer.parseInt(convertNumber(d, 2, 10, 2))].setData(Integer.toBinaryString(temp>>1));
				}
				sc.clear();
				Main.curMicro.setText("D<-S1>>, SC<-0");
				break;
				
			case "0100":
				String not = "", tmp="";
				if(s1.equals("11")){
					tmp = inpr.getData();
				}
				else{
					tmp = rgs[Integer.parseInt(convertNumber(s1, 2, 10, 2))].getData();
				}
				
				for (int i = 0; i < tmp.length(); i++) {
					if(tmp.charAt(i)=='0')
						not = not.concat("1");
					else
						not = not.concat("0");
				}
				
				if(d.equals("11")){
					outr.setData(not);
				}
				else{
					rgs[Integer.parseInt(convertNumber(d, 2, 10, 2))].setData(not);
				}
				sc.clear();
				Main.curMicro.setText("D<-S1', SC<-0");
				break;
			
			case "0101":
				String tmp1, tmp2, and="";
				if(s1.equals("11")){
					tmp1 = inpr.getData();
				}
				else{
					tmp1 = rgs[Integer.parseInt(convertNumber(s1, 2, 10, 2))].getData();
				}
				if(s2.equals("11")){
					tmp2 = inpr.getData();
				}
				else{
					tmp2 = rgs[Integer.parseInt(convertNumber(s2, 2, 10, 2))].getData();
				}
				
				for (int i = 0; i < 4; i++) {
					if(tmp1.charAt(i)=='1' && tmp2.charAt(i)=='1')
						and = and.concat("1");
					else
						and = and.concat("0");
				}
				
				if(d.equals("11")){
					outr.setData(and);
				}
				else{
					rgs[Integer.parseInt(convertNumber(d, 2, 10, 2))].setData(and);
				}
				sc.clear();
				Main.curMicro.setText("D<-S1^S2, SC<-0");
				break;
				
				
			/* load */
			case "0110":
				if(q.charAt(0)=='1'){
					if(d.equals("11")){
						outr.setData(s1+s2);
					}
					else{
						rgs[Integer.parseInt(convertNumber(d, 2, 10, 2))].setData(s1+s2);
					}
					sc.clear();
					Main.curMicro.setText("D<-S1S2, SC<-0");
				}
				else{
					addressRegister.setData(s1+s2);
					sc.increase();
					Main.curMicro.setText("AR<-S1S2");
				}
			
				break;
			/* store */
			case "0111":
				if(q.charAt(0) == '1'){
					if(s2.equals("11")){
						if(d.equals("11"))
							outr.setData(inpr.getData());
						else
							outr.setData(rgs[Integer.valueOf(convertNumber(d, 10, 2, 2))].getData());
					}
					else{
						if(d.equals("11"))
							rgs[Integer.valueOf(convertNumber(s2, 10, 2, 2))].setData(inpr.getData());
						else
							rgs[Integer.valueOf(convertNumber(s2, 10, 2, 2))].setData(rgs[Integer.valueOf(convertNumber(d, 10, 2, 2))].getData());
					}
					sc.clear();
					Main.curMicro.setText("S2<-D, SC<-0");
				}
				else{
					addressRegister.setData(s1+s2);
					sc.increase();
					Main.curMicro.setText("AR<-S1S2, SC<-0");
				}
				break;
				/* transfer */
			case "1001":
				if(d.equals("11")){
					if(s1.equals("11"))
						outr.setData(inpr.getData());
					else
						outr.setData(rgs[Integer.valueOf(convertNumber(s1, 10, 2, 2))].getData());
				}
				else{
					if(s1.equals("11"))
						rgs[Integer.valueOf(convertNumber(d, 10, 2, 2))].setData(inpr.getData());
					else
						rgs[Integer.valueOf(convertNumber(d, 10, 2, 2))].setData(rgs[Integer.valueOf(convertNumber(s1, 10, 2, 2))].getData());
				}
				sc.clear();
				Main.curMicro.setText("D<-S1, SC<-0");
				break;

			case "1010":
				stackMemory.getData()[Integer.valueOf(convertNumber(stackPointer.getData(), 2, 10, 2))] = convertNumber(String.valueOf(programCounter.getData()), 10, 2, 5);
				sc.increase();
				Main.curMicro.setText("SM[SP]<-PC");
				break;
			case "1011":
				stackPointer.decrease();
				sc.increase();
				Main.curMicro.setText("SP<-SP-1");
				break;

			case "1100":
				if(q.charAt(0) == '0'){
					programCounter.setData(Integer.parseInt(convertNumber((s1+s2), 2, 10, 2)));
					sc.clear();
					Main.curMicro.setText("PC<-IR[4..0], SC<-0");
				}
				else{
					programCounter.setData(Integer.parseInt(convertNumber((s1+s2), 2, 10, 2)));
					sc.clear();
					Main.curMicro.setText("IF V=1 THEN PC<-IR[4..0], SC<-0");
				}
				break;
			case "1101":
				int offset = 0;
				offset = Integer.parseInt(convertNumber(((s1+s2).substring(1)), 2, 10, 2));
				if(s1.charAt(0) == '1')
					offset *= -1;
				programCounter.setData(programCounter.getData()+offset);
				sc.clear();
				Main.curMicro.setText("PC<-PC+IR[3..0]");
				break;
			case "1110":
				addressRegister.setData(s1+s2);
				sc.increase();
				Main.curMicro.setText("AR<-IR[3..0]");
				break;
			case "1111":
				addressRegister.setData(s1+s2);
				sc.increase();
				Main.curMicro.setText("AR<-IR[3..0]");
				break;
			case "1000":
				sc.clear();
				Main.curMicro.setText("SC<-0");
				return false;
				//break;
			default: 
				Main.curMicro.setText("SC<-0");
				sc.clear();
				break;
			}

		}
		else if(sc.getT() == 4){
			switch(op){

			/* load */
			case "0110":
				if(d.equals("11")){
					outr.setData(dataMemory.get(Integer.parseInt(convertNumber(addressRegister.getData(), 2, 10, 2))));
				}
				else{
					rgs[Integer.parseInt(convertNumber(d, 2, 10, 2))].setData(dataMemory.get(Integer.parseInt(convertNumber(addressRegister.getData(), 2, 10, 2))));
				}
				sc.clear();
				Main.curMicro.setText("D<-DM[AR], SC<-0");
				break;

			case "0111":
				if(d=="11")
					dataMemory.getData()[Integer.parseInt(convertNumber(addressRegister.getData(), 2, 10, 2))] =  inpr.getData();
				else
					dataMemory.getData()[Integer.parseInt(convertNumber(addressRegister.getData(), 2, 10, 2))] =  rgs[Integer.valueOf(convertNumber(d, 2, 10, 2))].getData();
				sc.clear();
				Main.curMicro.setText("DM[AR]<-D, SC<-0");
				break;
			case "1010":
				int newadr = Integer.valueOf(convertNumber(s1.concat(s2), 2, 10, 2));
				programCounter.setData(newadr);
				stackPointer.increase();
				sc.clear();
				Main.curMicro.setText("PC<-IR[4..0], SP<-SP+1, SC<-0");
				break;
			case "1011":
				programCounter.setData(Integer.valueOf(convertNumber(stackMemory.get(Integer.valueOf(convertNumber(stackPointer.getData(), 2, 10, 2))),2, 10, 2)));
				sc.clear();
				Main.curMicro.setText("PC<-SM[SP], SC<-0");
				break;
			case "1110":
				stackMemory.getData()[Integer.valueOf(convertNumber(stackPointer.getData(), 2, 10, 2))] = dataMemory.get(Integer.parseInt(convertNumber(addressRegister.getData(), 2, 10, 2)));
				sc.increase();
				Main.curMicro.setText("SM[SP]<-DM[AR]");
				break;
			case "1111":
				stackPointer.decrease();
				sc.increase();
				Main.curMicro.setText("SP<-SP-1");
				break;
			default:
				sc.clear();
				Main.curMicro.setText("SC<-0");
				break;
			}
			sc.clear();
		}
		else if(sc.getT() == 5){
			switch(op){
			case "1110":
				stackPointer.increase();
				Main.curMicro.setText("SP<-SP+1");
				break;

			case "1111":
				dataMemory.getData()[Integer.parseInt(convertNumber(addressRegister.getData(), 2, 10, 2))] = stackMemory.get(Integer.valueOf(convertNumber(stackPointer.getData(), 2, 10, 2)));
				Main.curMicro.setText("DM[AR]<-SM[SP]");
				break;

			default: 
				sc.clear();
				Main.curMicro.setText("SC<-0");
				break;
			}	
			sc.clear();
		}
		else{
			Main.curMicro.setText("SC<-0");
			sc.clear();
		}

		ll.add(new State( instructionMemory, dataMemory, inpr, outr, addressRegister, instructionRegister, rgs, programCounter, stackPointer, labelTable, v, stackMemory, Main.curMicro.getText()));
		return true;

	}	
	/* Converter */
	/* Converter attributes */
	private String content[][];
	private HashMap<String, String> opcodes, registers;
	/* Converter functions */
	public void convert(String text){	


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
		opcodes.put("HLT", "1000");
		opcodes.put("TSF", "1001");
		opcodes.put("CAL", "1010");
		opcodes.put("RET", "1011");
		opcodes.put("JMP", "1100");
		opcodes.put("JMR", "1101");
		opcodes.put("PSH", "1110");
		opcodes.put("POP", "1111");


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

		String[] lines = text.split("\n");
		for (int i = 0; i < lines.length; i++) {
			lines[i] = lines[i].split("%")[0].replaceAll(" +", " ");
			content[i] = trimArray(lines[i].split(" "));
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
		String splitArray[];
		boolean isMemory = false; // instruction or data
		int mc=0, pc=0, lc=0, k=0;
		String tmp, tmpContent;

		/* determine labels */ 
		for (int i = 0; i < content.length; i++) {
			if(content[i][0].equals("HLT")){
				isMemory = false;
			}
			else if(isMemory && content[i][0].contains(":")){
				String val = "";
				if(content[i][1].equals("BIN"))
					val = content[i][2];
				else if(content[i][1].equals("DEC"))
					val = convertNumber(content[i][2], 10, 2, 4);
				else if(content[i][1].equals("HEX"))
					val = convertNumber(content[i][2], 16, 2, 4);
				dataMemory.add(val, mc);
				labelTable[lc] = new String[3];
				labelTable[lc][0] = content[i][0].replace(":", "");
				labelTable[lc][1] = val;
				labelTable[lc][2] = String.valueOf(mc);
				mc++;
				lc++;
			}
			else if(!isMemory && content[i][0].contains(":")){
				pc++;
				labelTable[lc] = new String[3];
				labelTable[lc][0] = content[i][0].replace(":", "");
				labelTable[lc][1] = "-1";
				labelTable[lc][2] = String.valueOf(pc);
				lc++;
			}
			else if(content[i][0].equals("ORG") && content[i][1].equals("D")){
				isMemory = true;
				mc = Integer.valueOf(content[i][2]);
			}
			else if(content[i][0].equals("ORG") && content[i][1].equals("C")){
				isMemory = false;
				pc = Integer.valueOf(content[i][2]);
			}
			else if(!isMemory){
				pc++;
			}

		}
		pc = 0;
		/* Go through all lines */
		for (int i = 0; i < content.length; i++) {

			tmp = "";
			splitArray = null;

			if(!isMemory && content[i][0].contains(":")){
				content[i] = sliceArray(content[i], 1, content[i].length);
			}
			/* Beginning */
			if(content[i][0].equals("ORG")){
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

			}
			/* Halt */
			else if(content[i][0].equals("HLT")){
				code[pc] = "01000000000";
				pc++;
			}

			/***** Arithmetic and Logic Operations *****/
			/* Addition */
			else if(content[i][0].equals("ADD")){
				splitArray = content[i][1].split(","); 
				tmp = "0".concat(opcodes.get("ADD").concat(registers.get(splitArray[0]).concat(registers.get(splitArray[1]).concat(registers.get(splitArray[2])))));
				code[pc] = tmp;
				pc++;
			}

			/* Increment */
			else if(content[i][0].equals("INC")){
				splitArray = content[i][1].split(","); 
				tmp = "0".concat(opcodes.get("INC").concat(registers.get(splitArray[0]).concat("0000")));
				code[pc] = tmp;
				pc++;
			}


			/* Double */
			else if(content[i][0].equals("DBL")){
				splitArray = content[i][1].split(","); 
				tmp = "0".concat(opcodes.get("DBL").concat(registers.get(splitArray[0]).concat("0000")));
				code[pc] = tmp;
				pc++;
			}

			/* Divide by Two */
			else if(content[i][0].equals("DBT")){
				splitArray = content[i][1].split(","); 
				tmp = "0".concat(opcodes.get("DBT").concat(registers.get(splitArray[0]).concat("0000")));
				code[pc] = tmp;
				pc++;
			}

			/* NOT - Complement */
			else if(content[i][0].equals("NOT")){
				splitArray = content[i][1].split(","); 
				tmp = "0".concat(opcodes.get("NOT").concat(registers.get(splitArray[0]).concat("0000")));
				code[pc] = tmp;
				pc++;
			}

			/* And */
			else if(content[i][0].equals("AND")){
				splitArray = content[i][1].split(","); 
				tmp = "0".concat(opcodes.get("AND").concat(registers.get(splitArray[0]).concat(registers.get(splitArray[1]).concat(registers.get(splitArray[2])))));
				code[pc] = tmp;
				pc++;
			}		
			/***** Data Transfer *****/
			/* Load */
			else if(content[i][0].equals("LD")){
				splitArray = content[i][1].split(",");
				if(splitArray[splitArray.length-1].contains("#")) tmp = "1";
				else tmp = "0";
				tmp = tmp.concat(opcodes.get("LD").concat(registers.get(splitArray[0])));
				if(tmp.charAt(0)=='1') tmp = tmp.concat(convertNumber(splitArray[splitArray.length-1].substring(1), 10, 2, 4));
				else{ 
					for (int j = 0; j < labelTable.length; j++) {
						if(labelTable[j][0]!=null && labelTable[j][0].equals(splitArray[splitArray.length-1].replaceAll("@", ""))){
							tmp = tmp.concat(convertNumber(labelTable[j][2], 10, 2, 4));
							break;
						}
					}
				}
				code[pc] = tmp;
				pc++;
			}	

			/* Store */
			else if(content[i][0].equals("ST")){
				splitArray = content[i][1].split(",");
				if(splitArray[splitArray.length-1].contains("#")) tmp = "1";
				else tmp = "0";
				tmp = tmp.concat(opcodes.get("ST").concat(registers.get(splitArray[0])));
				if(tmp.charAt(0)=='1') tmp.concat(convertNumber(splitArray[splitArray.length-1].substring(1), 10, 2, 4));
				else{ 
					for (int j = 0; j < labelTable.length; j++) {
						if(labelTable[j][0]!=null && labelTable[j][0].equals(splitArray[splitArray.length-1].replaceAll("@", ""))){
							tmp = tmp.concat(convertNumber(labelTable[j][2], 10, 2, 4));
							break;
						}
					}
				}
				code[pc] = tmp;
				pc++;
			}

			/* Transfer */
			else if(content[i][0].equals("TSF")){
				splitArray = content[i][1].split(",");
				tmp = tmp.concat("0").concat(opcodes.get("TSF")).concat(registers.get(splitArray[0]).concat(registers.get(splitArray[1]).concat("00")));
				code[pc] = tmp;
				pc++;
			}

			/***** Program Control *****/
			/* Call */
			else if(content[i][0].equals("CAL")){
				tmp = tmp.concat("0").concat(opcodes.get("CAL")).concat("0");
				/* checks if it is an integer */
				if(content[i][1].matches("^-?\\d+$"))
					tmp = tmp.concat(convertNumber(content[i][1], 10, 2, 5));
				else{

					for (int j = 0; j < labelTable.length; j++) {
						if(labelTable[j][0].replace(":", "").equals(content[i][1])){
							tmp = tmp.concat(convertNumber(labelTable[j][2], 10, 2, 5));
							break;
						}
					}

				}
				code[pc] = tmp;
				pc++;
			}

			/* Return */
			else if(content[i][0].equals("RET")){
				tmp = "0".concat(opcodes.get("RET").concat("000000"));
				code[pc] = tmp;
				pc++;
			}

			/* Jump */
			else if(content[i][0].equals("JMP")){
				if(content[i].length == 3 && content[i][2].equals("V")){
					tmp = "1";
				}
				else{
					tmp = "0";
				}
				tmp = tmp.concat(opcodes.get("JMP").concat("00"));
				if(content[i][1].matches("^-?\\d+$"))
					tmp = tmp.concat(convertNumber(content[i][1], 10, 2, 5));
				else{
					for (int j = 0; j < labelTable.length; j++) {
						if(labelTable[j][0].replace(":", "").equals(content[i][1])){
							tmp = tmp.concat(convertNumber(labelTable[j][2], 10, 2, 5));
							break;
						}
					}
				}
				code[pc] = tmp;
				pc++;
			}


			/* Jump Relatively */
			else if(content[i][0].equals("JMR")){
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
			}

			/* Push */
			else if(content[i][0].equals("PSH")){
				tmp = "0".concat(opcodes.get("PSH").concat("0"));
				if(content[i][1].matches("^-?\\d+$"))
					tmp = tmp.concat(convertNumber(content[i][1], 10, 2, 5));
				code[pc] = tmp;
				pc++;
			}

			/* Pop */
			else if(content[i][0].equals("POP")){
				tmp = "0".concat(opcodes.get("POP").concat("0"));
				if(content[i][1].matches("^-?\\d+$"))
					tmp = tmp.concat(convertNumber(content[i][1], 10, 2, 5));
				code[pc] = tmp;
				pc++;
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

	/* Take sub array */
	public String[] sliceArray(String[] array, int start, int end){
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
		if(intVal<0)
			return num;
		result = Integer.toString(intVal, toBase);
		if(result.length()>completeDigit){
			return result.substring(result.length()-4, result.length());
		}
		for (int i = result.length(); i < completeDigit; i++) {
			result = "0".concat(result);
		}
		return result.toUpperCase();
	}
	/* Removes null elements from array */
	public String[] trimArray(String[] firstArray){
		List<String> list = new ArrayList<String>();

		for(String s : firstArray) {
			if(s != null && s.length() > 0) {
				list.add(s);
			}
		}

		return list.toArray(new String[list.size()]);
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
	public int getV(){
		return v;
	}

}
