import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import org.apache.commons.io.FileUtils;
import javax.swing.JToolBar;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.Label;
import java.awt.FlowLayout;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.ListSelectionModel;

public class Main extends JFrame {
	Computer pc;
	private JPanel contentPane;
	private JFileChooser jfc;
	private File file;
	private Scanner jfs;
	static public JTable tableInstruction;
	private JScrollPane scrollPaneInstructionMemory;
	private JTable tableData;
	private JTable tableStack;
	private JTable tableLabel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings("deprecation")
	public Main() {
		setTitle("DEUARC");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 738, 492);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnChooseFile = new JButton("Choose file");
		btnChooseFile.setBounds(311, 425, 113, 25);
		btnChooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Assembly code(*.asm)", "asm");
				jfc = new JFileChooser();
				jfc.setFileFilter(filter);
				if(jfc.showOpenDialog(contentPane) == JFileChooser.APPROVE_OPTION){
					file = jfc.getSelectedFile();
			        jfs = null;
					try{
						jfs = new Scanner(file);
					} catch(FileNotFoundException e1){
						e1.printStackTrace();
					}
					try {
						//System.out.println(FileUtils.readFileToString(file));
						pc = new Computer(FileUtils.readFileToString(file));
						//Parser parser = new Parser(FileUtils.readFileToString(file));
					} catch (IOException e1) {
						// TODO: handle exception
					}
				}
			}
		});
		contentPane.setLayout(null);
		contentPane.add(btnChooseFile);
		
		scrollPaneInstructionMemory = new JScrollPane();
		scrollPaneInstructionMemory.setViewportBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		scrollPaneInstructionMemory.setBounds(27, 27, 145, 160);
		contentPane.add(scrollPaneInstructionMemory);
		
		tableInstruction = new JTable();
		tableInstruction.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		scrollPaneInstructionMemory.setViewportView(tableInstruction);
		tableInstruction.setModel(new DefaultTableModel(
			new Object[][] {
				{"1", pc==null ? "00000000000":pc.instructionMemory.get(0)},
				{"2", null},
				{"3", null},
				{"4", null},
				{"5", null},
				{"6", null},
				{"7", null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
			},
			new String[] {
				"#", "Instruction"
			}
		) {
			Class[] columnTypes = new Class[] {
				Integer.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableInstruction.getColumnModel().getColumn(0).setResizable(false);
		tableInstruction.getColumnModel().getColumn(0).setPreferredWidth(15);
		tableInstruction.getColumnModel().getColumn(1).setResizable(false);
		
		JLabel lblInstructionMemory = new JLabel("Instruction Memory");
		lblInstructionMemory.setBounds(30, 5, 142, 15);
		contentPane.add(lblInstructionMemory);
		
		JLabel labelDataMemory = new JLabel("Data Memory");
		labelDataMemory.setBounds(219, 5, 142, 15);
		contentPane.add(labelDataMemory);
		
		JScrollPane scrollPaneDataMemory = new JScrollPane();
		scrollPaneDataMemory.setViewportBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		scrollPaneDataMemory.setBounds(200, 27, 145, 160);
		contentPane.add(scrollPaneDataMemory);
		
		tableData = new JTable();
		tableData.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
			},
			new String[] {
				"Address", "Data"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableData.getColumnModel().getColumn(0).setResizable(false);
		tableData.getColumnModel().getColumn(1).setResizable(false);
		tableData.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		scrollPaneDataMemory.setViewportView(tableData);
		
		JScrollPane scrollPaneStackMemory = new JScrollPane();
		scrollPaneStackMemory.setViewportBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		scrollPaneStackMemory.setBounds(373, 27, 145, 160);
		contentPane.add(scrollPaneStackMemory);
		
		tableStack = new JTable();
		tableStack.setModel(new DefaultTableModel(
			new Object[][] {
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
				{null},
			},
			new String[] {
				"Data"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableStack.getColumnModel().getColumn(0).setResizable(false);
		tableStack.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		scrollPaneStackMemory.setViewportView(tableStack);
		
		JLabel labelStack = new JLabel("Stack Pointer");
		labelStack.setBounds(394, 5, 142, 15);
		contentPane.add(labelStack);
		
		JScrollPane scrollPaneLabelTable = new JScrollPane();
		scrollPaneLabelTable.setViewportBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		scrollPaneLabelTable.setBounds(557, 27, 145, 160);
		contentPane.add(scrollPaneLabelTable);
		
		tableLabel = new JTable();
		tableLabel.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"Label", "Value", "Adr"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tableLabel.getColumnModel().getColumn(0).setResizable(false);
		tableLabel.getColumnModel().getColumn(1).setResizable(false);
		tableLabel.getColumnModel().getColumn(2).setResizable(false);
		tableLabel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		scrollPaneLabelTable.setViewportView(tableLabel);
		
		JLabel labelLabelTable = new JLabel("Labels");
		labelLabelTable.setBounds(600, 5, 142, 15);
		contentPane.add(labelLabelTable);
		
	}
}
