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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ChangeEvent;

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
	static public JTextField tf_r0;
	static public JTextField tf_r1;
	static public JTextField tf_r2;
	static public JTextArea tf_rin;
	static public JTextField tf_rout;
	static public JTextField tf_ir;
	static public JTextField tf_ar;
	static public JTextField tf_sp;
	static public JTextField tf_pc;
	static public JLabel lblCurrentMicroOperation;
	static public JTable tableAssembly;
	static public JTextArea curMicro;
	static public JTextField tf_v;
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
		setBounds(100, 100, 738, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnChooseFile = new JButton("Choose file");
		btnChooseFile.setBounds(248, 442, 113, 25);
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
		scrollPaneInstructionMemory.setBounds(20, 27, 152, 160);
		contentPane.add(scrollPaneInstructionMemory);
		
		tableInstruction = new JTable();
		tableInstruction.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		scrollPaneInstructionMemory.setViewportView(tableInstruction);
		tableInstruction.setModel(new DefaultTableModel(
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
				"Adr", "Instruction"
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
		
		JLabel labelStack = new JLabel("Stack Memory");
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
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableLabel.getColumnModel().getColumn(0).setResizable(false);
		tableLabel.getColumnModel().getColumn(0).setPreferredWidth(65);
		tableLabel.getColumnModel().getColumn(1).setResizable(false);
		tableLabel.getColumnModel().getColumn(1).setPreferredWidth(80);
		tableLabel.getColumnModel().getColumn(2).setResizable(false);
		tableLabel.getColumnModel().getColumn(2).setPreferredWidth(80);
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
		
		tf_r0 = new JTextField();
		tf_r0.setBackground(Color.PINK);
		tf_r0.setEditable(false);
		sl_panel.putConstraint(SpringLayout.NORTH, tf_r0, 6, SpringLayout.SOUTH, lblRegister);
		sl_panel.putConstraint(SpringLayout.WEST, tf_r0, 10, SpringLayout.WEST, lblRegister);
		tf_r0.setHorizontalAlignment(JTextField.CENTER);
		tf_r0.setColumns(4);
		panel.add(tf_r0);
		
		tf_r1 = new JTextField();
		tf_r1.setBackground(Color.PINK);
		tf_r1.setEditable(false);
		sl_panel.putConstraint(SpringLayout.SOUTH, tf_r1, 0, SpringLayout.SOUTH, tf_r0);
		sl_panel.putConstraint(SpringLayout.EAST, tf_r1, -10, SpringLayout.EAST, lblRegister_1);
		tf_r1.setColumns(4);
		tf_r1.setHorizontalAlignment(JTextField.CENTER);
		panel.add(tf_r1);
		
		tf_r2 = new JTextField();
		tf_r2.setEditable(false);
		tf_r2.setBackground(Color.PINK);
		sl_panel.putConstraint(SpringLayout.WEST, tf_r2, 10, SpringLayout.WEST, lblRegister_2);
		sl_panel.putConstraint(SpringLayout.SOUTH, tf_r2, 0, SpringLayout.SOUTH, tf_r0);
		tf_r2.setColumns(4);
		tf_r2.setHorizontalAlignment(JTextField.CENTER);
		panel.add(tf_r2);
		
		tf_rin = new JTextArea();
		tf_rin.setBackground(Color.PINK);
		tf_rin.setEditable(true);
		sl_panel.putConstraint(SpringLayout.NORTH, tf_rin, 6, SpringLayout.SOUTH, lblRegisterIn);
		sl_panel.putConstraint(SpringLayout.WEST, tf_rin, 115, SpringLayout.EAST, tf_r2);
		tf_rin.setColumns(4);
		tf_rin.setRows(1);
		tf_rin.setAlignmentX(CENTER_ALIGNMENT);
		tf_rin.getDocument().addDocumentListener(new DocumentListener() {
			
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				if(pc!=null) pc.getInpr().setData(tf_rin.getText());
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				if(pc!=null) pc.getInpr().setData(tf_rin.getText());
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				if(pc!=null) pc.getInpr().setData(tf_rin.getText());
			}
		});
		panel.add(tf_rin);
		
		tf_rout = new JTextField();
		tf_rout.setEditable(false);
		tf_rout.setBackground(Color.PINK);
		sl_panel.putConstraint(SpringLayout.SOUTH, tf_rout, 0, SpringLayout.SOUTH, tf_r0);
		sl_panel.putConstraint(SpringLayout.EAST, tf_rout, -28, SpringLayout.EAST, panel);
		tf_rout.setColumns(4);
		tf_rout.setHorizontalAlignment(JTextField.CENTER);
		panel.add(tf_rout);
		
		tf_ir = new JTextField();
		sl_panel.putConstraint(SpringLayout.EAST, lblProgramCounter, 0, SpringLayout.EAST, tf_ir);
		tf_ir.setBackground(Color.LIGHT_GRAY);
		sl_panel.putConstraint(SpringLayout.NORTH, tf_ir, 6, SpringLayout.SOUTH, lblInstructionRegister);
		sl_panel.putConstraint(SpringLayout.WEST, tf_ir, 10, SpringLayout.WEST, lblInstructionRegister);
		tf_ir.setEditable(false);
		tf_ir.setHorizontalAlignment(JTextField.CENTER);
		tf_ir.setColumns(11);
		panel.add(tf_ir);
		
		tf_ar = new JTextField();
		sl_panel.putConstraint(SpringLayout.NORTH, tf_ar, 166, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, lblAddressRegister, -6, SpringLayout.NORTH, tf_ar);
		sl_panel.putConstraint(SpringLayout.WEST, tf_ar, 100, SpringLayout.WEST, panel);
		tf_ar.setBackground(Color.PINK);
		tf_ar.setEditable(false);
		tf_ar.setHorizontalAlignment(JTextField.CENTER);
		tf_ar.setColumns(4);
		panel.add(tf_ar);
		
		tf_sp = new JTextField();
		sl_panel.putConstraint(SpringLayout.NORTH, tf_sp, 166, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.SOUTH, lblStackPointer, -6, SpringLayout.NORTH, tf_sp);
		sl_panel.putConstraint(SpringLayout.EAST, tf_sp, -106, SpringLayout.EAST, panel);
		tf_sp.setEditable(false);
		tf_sp.setColumns(4);
		tf_sp.setBackground(Color.PINK);
		tf_sp.setHorizontalAlignment(JTextField.CENTER);
		panel.add(tf_sp);
		
		tf_pc = new JTextField();
		sl_panel.putConstraint(SpringLayout.NORTH, tf_pc, 0, SpringLayout.NORTH, tf_ar);
		sl_panel.putConstraint(SpringLayout.EAST, tf_pc, 0, SpringLayout.EAST, lblRegister_2);
		tf_pc.setEditable(false);
		tf_pc.setColumns(5);
		tf_pc.setBackground(Color.PINK);
		tf_pc.setHorizontalAlignment(JTextField.CENTER);
		panel.add(tf_pc);
		
		JSlider slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(slider.getValue()==0){
					Computer.base = 2;
				}
				else if(slider.getValue() == 1){
					Computer.base = 10;
				}
				else{
					Computer.base = 16;
				}
				if(pc!=null && pc.getLL()!=null && pc.getLL().getHead()!=null)
					pc.getLL().current().draw();
			}
		});
		sl_panel.putConstraint(SpringLayout.EAST, slider, -251, SpringLayout.EAST, panel);
		panel.add(slider);
		slider.setBackground(UIManager.getColor("PasswordField.inactiveBackground"));
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setValue(0);
		slider.setMaximum(2);
		sl_panel.putConstraint(SpringLayout.NORTH, slider, 6, SpringLayout.SOUTH, tf_pc);
		
		Panel panel_1 = new Panel();
		panel_1.setBounds(12, 495, 714, 196);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPaneAssembly = new JScrollPane();
		scrollPaneAssembly.setBounds(12, 12, 255, 172);
		panel_1.add(scrollPaneAssembly);
		
		tableAssembly = new JTable();
		scrollPaneAssembly.setViewportView(tableAssembly);
		tableAssembly.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableAssembly.setRowSelectionAllowed(false);
		tableAssembly.setModel(new DefaultTableModel(
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
				"Assembly"
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
		
		JButton btnNextLine = new JButton("Previous");
		btnNextLine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pc.getLL().prevLine().draw();
			}
		});
		btnNextLine.setBounds(300, 67, 113, 25);
		panel_1.add(btnNextLine);
		
		JButton btnNextMicro = new JButton("Next");
		btnNextMicro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pc.getLL().nextLine().draw();
			}
		});
		btnNextMicro.setBounds(300, 39, 113, 25);
		panel_1.add(btnNextMicro);
		
		JButton btnPrevious = new JButton("Previous");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pc.getLL().prevMicro().draw();
			}
		});
		btnPrevious.setBounds(300, 150, 113, 25);
		panel_1.add(btnPrevious);
		
		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pc.getLL().nextMicro().draw();
			}
		});
		btnNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		btnNext.setBounds(300, 122, 113, 25);
		panel_1.add(btnNext);
		
		JLabel lblLine = new JLabel("Line");
		lblLine.setBounds(342, 24, 39, 15);
		panel_1.add(lblLine);
		
		JLabel lblMicroOperation = new JLabel("Micro Operation");
		lblMicroOperation.setBounds(302, 105, 126, 15);
		panel_1.add(lblMicroOperation);
		
	    lblCurrentMicroOperation = new JLabel("Current Micro Operation");
		lblCurrentMicroOperation.setBounds(480, 72, 196, 15);
		panel_1.add(lblCurrentMicroOperation);
		
		curMicro = new JTextArea();
		curMicro.setFont(new Font("Cantarell", Font.BOLD, 12));
		curMicro.setToolTipText("");
		curMicro.setBackground(UIManager.getColor("Button.background"));
		curMicro.setBounds(490, 105, 212, 56);
		panel_1.add(curMicro);
		
		JButton btnNewButton = new JButton("Export MIF");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(pc!=null){
					String prefix = "DEPTH = 32;\nWIDTH = 11;\nADDRESS_RADIX = HEX;\nDATA_RADIX = BIN;\nCONTENT\nBEGIN\n";
					for (int i = 0; i < 32; i++) {
						prefix = prefix.concat(Computer.convertNumber(String.valueOf(i), 10, 16, 2)).concat(" : ");
						if(pc.getInstructionMemory().getInstruction(i)!=null)
							prefix = prefix.concat(pc.getInstructionMemory().getInstruction(i).getString()).concat("\n");
						else
							prefix = prefix.concat("00000000000\n");
						
					}
					String path = "/home/alp/out.mif";
					try {
						Files.write(Paths.get(path), prefix.getBytes(), StandardOpenOption.CREATE);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					

				    JFileChooser chooser = new JFileChooser();
				    chooser.setCurrentDirectory(new File("/home/alp"));
				    chooser.setDialogTitle("Choose location to save your .mif file");
				    chooser.setSelectedFile(new File("/home/alp/instruction.mif"));
				    int retrival = chooser.showSaveDialog(null);
				    if (retrival == JFileChooser.APPROVE_OPTION) {
				    	String chPath = chooser.getSelectedFile().getAbsolutePath();
				    	try {
							Files.write(Paths.get(chPath), prefix.getBytes(), StandardOpenOption.CREATE);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				    }
				  
				}
			}
			
		});
		btnNewButton.setBounds(385, 442, 117, 25);
		contentPane.add(btnNewButton);
		
		tf_v = new JTextField();
		tf_v.setEditable(false);
		tf_v.setBackground(Color.PINK);
		tf_v.setBounds(121, 445, 25, 19);
		tf_v.setHorizontalAlignment((JTextField.CENTER));
		tf_v.setColumns(1);
		contentPane.add(tf_v);
		
		JLabel lblOverflow = new JLabel("Overflow");
		lblOverflow.setBounds(100, 425, 70, 15);
		contentPane.add(lblOverflow);
		tableAssembly.getColumnModel().getColumn(0).setResizable(false);
		tableAssembly.getColumnModel().getColumn(0).setPreferredWidth(140);
		tableAssembly.getColumnModel().getColumn(0).setMaxWidth(500);
		
	}
}
