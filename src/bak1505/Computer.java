package bak1505;
import java.util.HashMap;

public class Computer {
	private Memory instructionMemory;
	private DataMemory dataMemory;
	private String[][] labelTable;
	public Computer(String assembly){
		labelTable = new String[16][3];
		instructionMemory = new Memory(32,11);
		dataMemory = new DataMemory(16, 4);
		convert(assembly);
		updateLabels();
	}
	
	
	/* Functions */
	/* updates label table on GUI */
	public void updateLabels(){
		int i=0;
		for(String[] label:labelTable){
			if(label[0]!=null){
				Main.tableLabel.getModel().setValueAt(label[0], i, 0);
				Main.tableLabel.getModel().setValueAt(convertNumber(label[1],10,2), i, 1);
				Main.tableLabel.getModel().setValueAt(convertNumber(label[2], 10, 2), i, 2);
				i++;
			}
		}
		
	}
	/* CONVERTER */
	/* Converter attr. */
	String content[][];
	boolean parseFail;
	HashMap<String, String> opcodes, registers;
	/* Converter funcs */
	public void convert(String text){	
		parseFail = false;
		
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
			char a = text.charAt(i);
	
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
		
		
		identify();
	}
	/* Parse the expression */
	public void identify(){
		String out;
		boolean flag = true; // instruction or data
		int count = 0;
		for (int i = 0; i < content.length; i++) {
			out = "";
			if(flag){
				if(!opcodes.containsKey(content[i][0])){
					if(!content[i][0].equals("ORG") && !content[i][0].equals("\rORG")){
						parseFail = true;
						out = "-1";
					}
					else if(content[i][1].equals("D")){ 
						flag = false;
						count = Integer.parseInt(content[i][2]);
						continue;
					}
					else if(content[i][1].equals("C")){
						count = Integer.parseInt(content[i][2]);
						Main.tableInstructionCounter = count;
						continue;
					}
			
				}
				else if(content[i][0].equals("HLT")){
					out = "01000000000";
				}
				else{
					out = out.concat(opcodes.get(content[i][0]));
					String arguments[] = content[i][1].split(",");
					for (int j = 0; j < arguments.length; j++) {
						if(registers.containsKey(arguments[j]))
							out = out.concat(registers.get(arguments[j]));
						else if(arguments[arguments.length-1].charAt(0)=='#'){
							out = "1".concat(out);
							out = out.concat(convertNumber(arguments[1].substring(1), 10, 2));
						}
						else if(arguments[arguments.length-1].charAt(0)=='@'){
							out = "0".concat(out);
						}
						else{
							parseFail = true;
							out = "-1";
						}
					}
				}
				if(out.length()==10)
					out = "0".concat(out);
				for (int j = out.length(); j < 11; j++) {
					out = out.concat("0");
				}
				instructionMemory.add(out, count);
				count++;
			}
			else{
				if(content[i][0].contains("END"))
					break;
				
				if(content[i][0].contains(":")){
					String label, value, adr;
					label = content[i][0].split(":")[0].trim();
					switch(content[i][1]){
					case "HEX":
						value = convertNumber(content[i][2], 16, 2);
						break;
					case "DEC":
						value = convertNumber(content[i][2], 10, 2);
						break;
					default:
						System.out.println("Unexpected number base.");
						value = "";
						break;
						
					}
					//value = content[i][2];
					for (int j = value.length(); j < 4; j++) {
						value = "0".concat(value);
					}
					adr = String.valueOf(count);
					labelTable[count][0] = label;
					labelTable[count][1] = value;
					labelTable[count][2] = adr;
					dataMemory.add(value, count);
					count++;
				}
				else{
					parseFail=true;
					out = "-1";
				}
			}
		}
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
	public String convertNumber(String num, int fromBase, int toBase){
		int intVal;
		String result;
		intVal = Integer.valueOf(num, fromBase);
		result = Integer.toString(intVal, toBase);
		for (int i = result.length(); i < 4; i++) {
			result = "0".concat(result);
		}
		return result.toUpperCase();
	}
	
}
