package bak1505;
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
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JSlider;
import javax.swing.UIManager;

public class Main extends JFrame {
	Computer pc;
	private JPanel contentPane;
	private JFileChooser jfc;
	private File file;
	private Scanner jfs;
	private JScrollPane scrollPaneInstructionMemory;
	
	static public JTable tableStack;
	static public JTable tableLabel;
	static public JTable tableData;
	static public JTable tableInstruction;
	static int tableInstructionCounter = 1;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
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
		setBounds(100, 100, 738, 520);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnChooseFile = new JButton("Choose file");
		btnChooseFile.setBounds(310, 453, 113, 25);
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
		
		JLabel lblHex = new JLabel("HEX");
		lblHex.setBounds(444, 415, 45, 15);
		contentPane.add(lblHex);
		
		JLabel lblDec = new JLabel("DEC");
		lblDec.setBounds(358, 415, 45, 15);
		contentPane.add(lblDec);
		
		JLabel lblBin = new JLabel("BIN");
		lblBin.setBounds(275, 415, 45, 15);
		contentPane.add(lblBin);
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
				{"0", null},
				{"1", null},
				{"2", null},
				{"3", null},
				{"4", null},
				{"5", null},
				{"6", null},
				{"7", null},
				{"8", null},
				{"9", null},
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
				{"0000", null},
				{"0001", null},
				{"0010", null},
				{"0011", null},
				{"0100", null},
				{"0101", null},
				{"0110", null},
				{"0111", null},
				{"1000", null},
				{"1001", null},
				{"1010", null},
				{"1011", null},
				{"1100", null},
				{"1101", null},
				{"1110", null},
				{"1111", null},
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
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 199, 714, 214);
		contentPane.add(panel);
		SpringLayout sl_panel = new SpringLayout();
		panel.setLayout(sl_panel);
		
		JLabel lblRegister = new JLabel("Register 0");
		sl_panel.putConstraint(SpringLayout.NORTH, lblRegister, 10, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, lblRegister, 10, SpringLayout.WEST, panel);
		panel.add(lblRegister);
		
		JLabel lblRegister_1 = new JLabel("Register 1");
		sl_panel.putConstraint(SpringLayout.NORTH, lblRegister_1, 0, SpringLayout.NORTH, lblRegister);
		sl_panel.putConstraint(SpringLayout.WEST, lblRegister_1, 62, SpringLayout.EAST, lblRegister);
		panel.add(lblRegister_1);
		
		JLabel lblRegister_2 = new JLabel("Register 2");
		sl_panel.putConstraint(SpringLayout.NORTH, lblRegister_2, 0, SpringLayout.NORTH, lblRegister);
		sl_panel.putConstraint(SpringLayout.WEST, lblRegister_2, 96, SpringLayout.EAST, lblRegister_1);
		panel.add(lblRegister_2);
		
		JLabel lblRegisterIn = new JLabel("Register IN");
		sl_panel.putConstraint(SpringLayout.NORTH, lblRegisterIn, 0, SpringLayout.NORTH, lblRegister);
		sl_panel.putConstraint(SpringLayout.WEST, lblRegisterIn, 81, SpringLayout.EAST, lblRegister_2);
		panel.add(lblRegisterIn);
		
		JLabel lblRegisterOut = new JLabel("Register OUT");
		sl_panel.putConstraint(SpringLayout.NORTH, lblRegisterOut, 0, SpringLayout.NORTH, lblRegister);
		sl_panel.putConstraint(SpringLayout.EAST, lblRegisterOut, -10, SpringLayout.EAST, panel);
		panel.add(lblRegisterOut);
		
		JLabel lblInstructionRegister = new JLabel("Instruction Register");
		sl_panel.putConstraint(SpringLayout.NORTH, lblInstructionRegister, 47, SpringLayout.SOUTH, lblRegister_2);
		sl_panel.putConstraint(SpringLayout.WEST, lblInstructionRegister, 281, SpringLayout.WEST, panel);
		panel.add(lblInstructionRegister);
		
		JLabel lblProgramCounter = new JLabel("Program Counter");
		panel.add(lblProgramCounter);
		
		JLabel lblAddressRegister = new JLabel("Address Register");
		sl_panel.putConstraint(SpringLayout.NORTH, lblProgramCounter, 0, SpringLayout.NORTH, lblAddressRegister);
		sl_panel.putConstraint(SpringLayout.WEST, lblAddressRegister, 65, SpringLayout.WEST, panel);
		panel.add(lblAddressRegister);
		
		JLabel lblStackPointer = new JLabel("Stack Pointer");
		sl_panel.putConstraint(SpringLayout.EAST, lblStackPointer, -87, SpringLayout.EAST, panel);
		panel.add(lblStackPointer);
		
		textField = new JTextField();
		textField.setBackground(Color.PINK);
		textField.setEditable(false);
		sl_panel.putConstraint(SpringLayout.NORTH, textField, 6, SpringLayout.SOUTH, lblRegister);
		sl_panel.putConstraint(SpringLayout.WEST, textField, 10, SpringLayout.WEST, lblRegister);
		panel.add(textField);
		textField.setColumns(4);
		
		textField_1 = new JTextField();
		textField_1.setBackground(Color.PINK);
		textField_1.setEditable(false);
		sl_panel.putConstraint(SpringLayout.SOUTH, textField_1, 0, SpringLayout.SOUTH, textField);
		sl_panel.putConstraint(SpringLayout.EAST, textField_1, -10, SpringLayout.EAST, lblRegister_1);
		textField_1.setColumns(4);
		panel.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setBackground(Color.PINK);
		sl_panel.putConstraint(SpringLayout.WEST, textField_2, 10, SpringLayout.WEST, lblRegister_2);
		sl_panel.putConstraint(SpringLayout.SOUTH, textField_2, 0, SpringLayout.SOUTH, textField);
		textField_2.setColumns(4);
		panel.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setBackground(Color.PINK);
		textField_3.setEditable(false);
		sl_panel.putConstraint(SpringLayout.NORTH, textField_3, 6, SpringLayout.SOUTH, lblRegisterIn);
		sl_panel.putConstraint(SpringLayout.WEST, textField_3, 115, SpringLayout.EAST, textField_2);
		textField_3.setColumns(4);
		panel.add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setEditable(false);
		textField_4.setBackground(Color.PINK);
		sl_panel.putConstraint(SpringLayout.SOUTH, textField_4, 0, SpringLayout.SOUTH, textField);
		sl_panel.putConstraint(SpringLayout.EAST, textField_4, -28, SpringLayout.EAST, panel);
		textField_4.setColumns(4);
		panel.add(textField_4);
		
		textField_5 = new JTextField();
		sl_panel.putConstraint(SpringLayout.EAST, lblProgramCounter, 0, SpringLayout.EAST, textField_5);
		textField_5.setBackground(Color.LIGHT_GRAY);
		sl_panel.putConstraint(SpringLayout.NORTH, textField_5, 6, SpringLayout.SOUTH, lblInstructionRegister);
		sl_panel.putConstraint(SpringLayout.WEST, textField_5, 10, SpringLayout.WEST, lblInstructionRegister);
		textField_5.setEditable(false);
		panel.add(textField_5);
		textField_5.setColumns(11);
		
		textField_6 = new JTextField();
		sl_panel.putConstraint(SpringLayout.NORTH, textField_6, 166, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, lblAddressRegister, -6, SpringLayout.NORTH, textField_6);
		sl_panel.putConstraint(SpringLayout.WEST, textField_6, 100, SpringLayout.WEST, panel);
		textField_6.setBackground(Color.PINK);
		textField_6.setEditable(false);
		panel.add(textField_6);
		textField_6.setColumns(4);
		
		textField_7 = new JTextField();
		sl_panel.putConstraint(SpringLayout.NORTH, textField_7, 166, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, lblStackPointer, -6, SpringLayout.NORTH, textField_7);
		sl_panel.putConstraint(SpringLayout.EAST, textField_7, -106, SpringLayout.EAST, panel);
		textField_7.setEditable(false);
		textField_7.setColumns(4);
		textField_7.setBackground(Color.PINK);
		panel.add(textField_7);
		
		textField_8 = new JTextField();
		sl_panel.putConstraint(SpringLayout.NORTH, textField_8, 0, SpringLayout.NORTH, textField_6);
		sl_panel.putConstraint(SpringLayout.EAST, textField_8, 0, SpringLayout.EAST, lblRegister_2);
		textField_8.setEditable(false);
		textField_8.setColumns(5);
		textField_8.setBackground(Color.PINK);
		panel.add(textField_8);
		
		JSlider slider = new JSlider();
		sl_panel.putConstraint(SpringLayout.EAST, slider, -251, SpringLayout.EAST, panel);
		panel.add(slider);
		slider.setBackground(UIManager.getColor("PasswordField.inactiveBackground"));
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setValue(0);
		slider.setMaximum(2);
		sl_panel.putConstraint(SpringLayout.NORTH, slider, 6, SpringLayout.SOUTH, textField_8);
		
	}
}
