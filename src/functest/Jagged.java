package functest;

public class Jagged {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String inputNo;
		String convertedNo;
		int toBase, fromBase, intVal;

			//To read the number and the bases in which the conversion needs to be performed
			String x = "a a a\nv vb fgfg dsf n\n as fdf g";
			String a[] = x.split("\n");
			String b[][] = new String[a.length][];
			for (int i = 0; i < b.length; i++) {
				b[i] = new String[a[i].split(" ").length];
			}

			System.out.println(x);

	}

}
