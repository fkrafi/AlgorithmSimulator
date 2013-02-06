package edu.fkrafi.applet.learn;

import java.awt.BorderLayout;

import javax.swing.JApplet;
import javax.swing.JTable;

public class AppletTable extends JApplet {

	public void init() {
		String columns[] = { "Name", "Age", "Gender" };
		Object data[][] = { { "Tom", new Integer(20), "Male" },
				{ "Tina", new Integer(18), "Female" },
				{ "Raj", new Integer(19), "Male" } };
		JTable table = new JTable(data, columns);
		//add(table.getTableHeader(), BorderLayout.PAGE_START);
		add(table);
	}

}
