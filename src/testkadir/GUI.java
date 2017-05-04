package testkadir;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class GUI extends JFrame {

	private JPanel contentPane;
	private JTable InstMemory;
	private JTable table;
	private JScrollPane scrollPane_1;
	private JTable table_1;
	private JTable table_2;
	private JTextField txtDeuarcSimulation;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
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
	public GUI() {
		setTitle("DeuArc");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 695, 503);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(224, 255, 255));
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel InstLabel = new JLabel("Instruction Memory");
		InstLabel.setForeground(new Color(220, 20, 60));
		InstLabel.setBackground(new Color(0, 0, 0));
		InstLabel.setFont(new Font("Stencil", Font.PLAIN, 13));
		InstLabel.setBounds(168, 41, 150, 14);
		contentPane.add(InstLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(174, 66, 133, 256);
		contentPane.add(scrollPane);
		
		InstMemory = new JTable();
		InstMemory.setBackground(new Color(240, 128, 128));
		InstMemory.setForeground(Color.BLACK);
		scrollPane.setViewportView(InstMemory);
		InstMemory.setModel(new DefaultTableModel(
			new Object[][] {
				{null},{null},{null},{null},{null},{null},{null},{null},{null},
				{null},{null},{null},{null},{null},{null},{null},{null},{null},{null},
				{null},{null},{null},{null},{null},{null},{null},{null},{null},{null},{null},{null},{null},
			},
			new String[] {
				""
			}
		));
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(351, 66, 133, 256);
		contentPane.add(scrollPane_1);
		
		table = new JTable();
		table.setBackground(new Color(240, 128, 128));
		scrollPane_1.setViewportView(table);
		table.setModel(new DefaultTableModel(
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
				"Data", "Address"
			}
		));
		
		JLabel lblDataMemory = new JLabel("DATA MEMORY");
		lblDataMemory.setForeground(new Color(220, 20, 60));
		lblDataMemory.setFont(new Font("Stencil", Font.PLAIN, 13));
		lblDataMemory.setBounds(369, 41, 91, 14);
		contentPane.add(lblDataMemory);
		
		JLabel lblStackMemory = new JLabel("STACK MEMORY");
		lblStackMemory.setForeground(new Color(220, 20, 60));
		lblStackMemory.setFont(new Font("Stencil", Font.PLAIN, 13));
		lblStackMemory.setBounds(537, 41, 114, 14);
		contentPane.add(lblStackMemory);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(533, 66, 114, 256);
		contentPane.add(scrollPane_2);
		
		table_1 = new JTable();
		table_1.setBackground(new Color(240, 128, 128));
		scrollPane_2.setViewportView(table_1);
		table_1.setModel(new DefaultTableModel(
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
				""
			}
		));
		
		JTextArea textArea = new JTextArea();
		textArea.setBackground(new Color(250, 128, 114));
		textArea.setBounds(24, 306, 114, 16);
		contentPane.add(textArea);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBackground(new Color(250, 128, 114));
		textArea_1.setBounds(24, 360, 114, 16);
		contentPane.add(textArea_1);
		
		JTextArea textArea_2 = new JTextArea();
		textArea_2.setBackground(new Color(250, 128, 114));
		textArea_2.setBounds(24, 422, 114, 16);
		contentPane.add(textArea_2);
		
		JTextArea textArea_3 = new JTextArea();
		textArea_3.setBackground(new Color(250, 128, 114));
		textArea_3.setBounds(193, 360, 114, 16);
		contentPane.add(textArea_3);
		
		JTextArea textArea_4 = new JTextArea();
		textArea_4.setBackground(new Color(250, 128, 114));
		textArea_4.setBounds(193, 422, 114, 16);
		contentPane.add(textArea_4);
		
		JTextArea textArea_5 = new JTextArea();
		textArea_5.setBackground(new Color(250, 128, 114));
		textArea_5.setBounds(346, 360, 114, 16);
		contentPane.add(textArea_5);
		
		JTextArea textArea_6 = new JTextArea();
		textArea_6.setBackground(new Color(250, 128, 114));
		textArea_6.setBounds(346, 422, 114, 16);
		contentPane.add(textArea_6);
		
		JTextArea textArea_7 = new JTextArea();
		textArea_7.setBackground(new Color(250, 128, 114));
		textArea_7.setBounds(506, 360, 114, 16);
		contentPane.add(textArea_7);
		
		JTextArea textArea_8 = new JTextArea();
		textArea_8.setBackground(new Color(250, 128, 114));
		textArea_8.setBounds(506, 422, 114, 16);
		contentPane.add(textArea_8);
		
		JLabel lblNewLabel = new JLabel("Address Register");
		lblNewLabel.setFont(new Font("Stencil", Font.PLAIN, 11));
		lblNewLabel.setForeground(new Color(255, 0, 0));
		lblNewLabel.setBounds(36, 281, 102, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblProgramCounter = new JLabel("Program Counter");
		lblProgramCounter.setForeground(Color.RED);
		lblProgramCounter.setFont(new Font("Stencil", Font.PLAIN, 11));
		lblProgramCounter.setBounds(34, 335, 112, 14);
		contentPane.add(lblProgramCounter);
		
		JLabel lblStackPointer = new JLabel("Stack Pointer");
		lblStackPointer.setForeground(Color.RED);
		lblStackPointer.setFont(new Font("Stencil", Font.PLAIN, 11));
		lblStackPointer.setBounds(34, 397, 112, 14);
		contentPane.add(lblStackPointer);
		
		JLabel lblInputRegister = new JLabel("Input Register");
		lblInputRegister.setForeground(Color.RED);
		lblInputRegister.setFont(new Font("Stencil", Font.PLAIN, 11));
		lblInputRegister.setBounds(206, 333, 112, 14);
		contentPane.add(lblInputRegister);
		
		JLabel lblOutputRegister = new JLabel("Output Register");
		lblOutputRegister.setForeground(Color.RED);
		lblOutputRegister.setFont(new Font("Stencil", Font.PLAIN, 11));
		lblOutputRegister.setBounds(203, 397, 112, 14);
		contentPane.add(lblOutputRegister);
		
		JLabel lblInstruductionRegister = new JLabel("Instruduction Register");
		lblInstruductionRegister.setForeground(Color.RED);
		lblInstruductionRegister.setFont(new Font("Stencil", Font.PLAIN, 11));
		lblInstruductionRegister.setBounds(328, 333, 145, 14);
		contentPane.add(lblInstruductionRegister);
		
		JLabel lblRegister = new JLabel("Register 0");
		lblRegister.setForeground(Color.RED);
		lblRegister.setFont(new Font("Stencil", Font.PLAIN, 11));
		lblRegister.setBounds(351, 396, 112, 14);
		contentPane.add(lblRegister);
		
		JLabel lblRegister_1 = new JLabel("Register 1");
		lblRegister_1.setForeground(Color.RED);
		lblRegister_1.setFont(new Font("Stencil", Font.PLAIN, 11));
		lblRegister_1.setBounds(518, 333, 112, 14);
		contentPane.add(lblRegister_1);
		
		JLabel lblRegister_2 = new JLabel("Register 2");
		lblRegister_2.setForeground(Color.RED);
		lblRegister_2.setFont(new Font("Stencil", Font.PLAIN, 11));
		lblRegister_2.setBounds(516, 387, 112, 14);
		contentPane.add(lblRegister_2);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(43, 94, 85, 183);
		contentPane.add(panel);
		panel.setLayout(null);
		
		table_2 = new JTable();
		table_2.setBounds(6, 16, 73, 160);
		panel.add(table_2);
		table_2.setBackground(new Color(240, 128, 128));
		table_2.setModel(new DefaultTableModel(
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
			},
			new String[] {
				"New column"
			}
		));
		
		JLabel lblLabelTable = new JLabel("Label Table\r\n");
		lblLabelTable.setForeground(new Color(220, 20, 60));
		lblLabelTable.setFont(new Font("Stencil", Font.PLAIN, 13));
		lblLabelTable.setBackground(Color.BLACK);
		lblLabelTable.setBounds(51, 79, 95, 14);
		contentPane.add(lblLabelTable);
		
		txtDeuarcSimulation = new JTextField();
		txtDeuarcSimulation.setText("DeuArc Simulation");
		txtDeuarcSimulation.setFont(new Font("Snap ITC", Font.PLAIN, 18));
		txtDeuarcSimulation.setBackground(new Color(224, 255, 255));
		txtDeuarcSimulation.setBounds(10, 11, 198, 20);
		contentPane.add(txtDeuarcSimulation);
		txtDeuarcSimulation.setColumns(10);
	}
}