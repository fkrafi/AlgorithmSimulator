import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class InsertionSort extends JApplet implements ActionListener {

	private Integer I;
	ArrayList<Integer> tempInput = new ArrayList<Integer>();

	private Border border;
	private JPanel jPanelHeader, jPanelMiddle, jPanelArray, jPanelFooter;
	private JTextField jTextFieldArr;
	private JButton jButtonStart, jButtonNext, jButtonRefresh;
	private JLabel jLabelGridArr[];

	public void init() {
		setLayout(new BorderLayout());
		jPanelHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jPanelHeader.setBackground(Color.YELLOW);
		jPanelHeader.add(new JLabel("Array"));
		jTextFieldArr = new JTextField(60);
		jPanelHeader.add(jTextFieldArr);
		jButtonStart = new JButton("Start");
		jButtonStart.addActionListener(this);
		jPanelHeader.add(jButtonStart);
		jButtonNext = new JButton("Next");
		jButtonNext.setEnabled(false);
		jButtonNext.addActionListener(this);
		jPanelHeader.add(jButtonNext);
		jButtonRefresh = new JButton("Refresh");
		jButtonRefresh.addActionListener(this);
		jPanelHeader.add(jButtonRefresh);
		add(jPanelHeader, BorderLayout.NORTH);

		jPanelMiddle = new JPanel(new BorderLayout());
		jPanelArray = new JPanel();
		jPanelMiddle.add(jPanelArray, BorderLayout.CENTER);
		add(jPanelMiddle, BorderLayout.CENTER);

		jPanelFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		jPanelFooter.setBackground(Color.BLACK);
		JLabel label = new JLabel(
				"Developed By :: Md. Fasihul Kabir (ID :: 09.01.04.065");
		label.setForeground(Color.WHITE);
		jPanelFooter.add(label);
		add(jPanelFooter, BorderLayout.SOUTH);
		setSize(950, 125);

		border = BorderFactory.createLineBorder(Color.BLACK);
		RandomGenerator();
	}

	private void RandomGenerator() {
		String t;
		String r = (new Integer((int) (Math.random() * 100))).toString();
		int cnt = 1;
		while (cnt < 10) {
			t = (new Integer((int) (Math.random() * 100))).toString();
			;
			r += ", " + t;
			cnt++;
		}
		jTextFieldArr.setText(r);
	}

	private void Init() {
		String temp;
		tempInput.clear();
		StringTokenizer st = new StringTokenizer(jTextFieldArr.getText(), ",");
		while (st.hasMoreTokens()) {
			temp = st.nextToken().trim();
			if (temp.length() > 0) {
				tempInput.add(Integer.parseInt(temp));
			}
		}
		if (tempInput.size() == 0) {
			ShowMessage("Please Insert An Array To Search");
			return;
		} else if (tempInput.size() > 25) {
			ShowMessage("Array Cannot Be Greater Than 25");
			return;
		}
		jLabelGridArr = new JLabel[tempInput.size()];
		jPanelArray.setLayout(new GridLayout(1, tempInput.size()));
		for (int i = 0; i < tempInput.size(); i++) {
			jLabelGridArr[i] = new JLabel(tempInput.get(i).toString());
			jLabelGridArr[i].setBorder(border);
			// jLabelGridArr[i].setBackground(Color.LIGHT_GRAY);
			jLabelGridArr[i].setFont(new Font("Serif", Font.PLAIN, 24));
			jLabelGridArr[i].setHorizontalAlignment(JLabel.CENTER);
			jLabelGridArr[i].setVerticalAlignment(JLabel.CENTER);
			jLabelGridArr[i].setOpaque(true);
			jPanelArray.add(jLabelGridArr[i]);
		}
		I = 1;
		jButtonStart.setEnabled(false);
		jButtonNext.setEnabled(true);
		refresh();
	}

	private void refresh() {
		setSize(1950, 500);
		setSize(950, 125);
	}

	private void insertion_sort() {
		int j = I;
		int t = tempInput.get(j);
		while (j > 0 && tempInput.get(j - 1) > t) {
			tempInput.set(j, tempInput.get(j - 1));
			j--;
		}
		tempInput.set(j, t);
	}

	private void ShowMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	private void changeLook() {
		for (int i = 0; i < tempInput.size(); i++) {
			jLabelGridArr[i].setText(tempInput.get(i).toString());
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == jButtonStart) {
			Init();
			jTextFieldArr.setEditable(false);
		} else if (ae.getSource() == jButtonNext) {
			if (I < tempInput.size()) {
				insertion_sort();
				changeLook();
				I++;
			} else {
				jButtonNext.setEnabled(false);
				changeLook();
				ShowMessage("Sorted!");
			}
		} else if (ae.getSource() == jButtonRefresh) {
			jTextFieldArr.setEditable(true);
			jButtonStart.setEnabled(true);
			jButtonNext.setEnabled(false);
			jPanelArray.removeAll();
			refresh();
			RandomGenerator();
		}
	}
}
