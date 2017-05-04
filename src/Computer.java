import java.util.HashMap;

public class Computer {
	Memory instructionMemory;
	
	public Computer(String assembly){
		instructionMemory = new Memory(32,11);
		convert(assembly);
	}
	
	/* Converter */
	/* Converter attr. */
	String content[][];
	boolean parseFail;
	HashMap<String, String> opcodes, registers;
	/* Convert funcs */
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
					System.out.println(row+","+column+" "+temp);
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
		
		/* Give meanings to the commands */
		identify();
	}
	public void identify(){
		String out;
		int count = 0;
		for (int i = 0; i < content.length; i++) {
			out = "";
			if(!opcodes.containsKey(content[i][0])){
				if(!content[i][0].equals("ORG")){
					parseFail = true;
					out = "-1";
				}
			}
			else if(content[i][0].equals("HLT")){
				break;
			}
			else{
				out = out.concat(opcodes.get(content[i][0]));
				String arguments[] = content[i][1].split(",");
				for (int j = 0; j < arguments.length; j++) {
					if(registers.containsKey(arguments[j]))
						out = out.concat(registers.get(arguments[j]));
					else if(arguments[arguments.length-1].charAt(0)=='#'){
						out = "1".concat(out);
						out = out.concat(convertNumber(arguments[1].substring(1), 2));
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
			for (int j = out.length(); j < 11; j++) {
				out = out.concat("0");
			}
			System.out.println(out);
			instructionMemory.add(out, count);
			count++;
		}
	}
	
	/* Take subarray */
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
	public String convertNumber(String num, int base){
		int tmp = Integer.parseInt(num);
		String result = "";
		
		result = Integer.toString(tmp, base);
		
		for (int i = result.length(); i < 4; i++) {
			result = "0".concat(result);
		}
		
		return result;
	}
	
}
