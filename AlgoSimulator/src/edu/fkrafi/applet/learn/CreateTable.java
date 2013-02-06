package edu.fkrafi.applet.learn;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class CreateTable {
	JTable table;

	public static void main(String[] args) {
		new CreateTable();
	}

	public CreateTable() {
		JFrame frame = new JFrame("Create table in java");
		JPanel panel = new JPanel();
		String data[][] = { { "Vinod", "Computer" }, { "Ravi", "Computer" },
				{ "Deepak", "Biology" }, { "Santosh", "Photoshop" },
				{ "Amardeep", "MCA" }, { "Suman", "MCA" } };
		String col[] = { "Name", "Course" };
		DefaultTableModel modeltable = new DefaultTableModel();
		modeltable.setDataVector(data, col);
		table = new JTable(modeltable);
		Dimension dimen = new Dimension(20, 1);
		table.setIntercellSpacing(new Dimension(dimen));
		SetRowHight(table);
		table.setColumnSelectionAllowed(true);
		JTableHeader head = table.getTableHeader();
		head.setBackground(Color.pink);
		JScrollPane pane = new JScrollPane(table);
		panel.add(pane);
		frame.add(panel);
		frame.setSize(500, 230);
		// frame.setUndecorated(true);
		// frame.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void SetRowHight(JTable table) {
		int height = table.getRowHeight();
		table.setRowHeight(height + 10);
	}
}