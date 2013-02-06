import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MergeSort extends JApplet implements ActionListener {
	private JPanel jPanelMenu, jPanelMain, jPanelGrid, jPanelFooter,
			jPanelRow[];
	private JLabel[][] jLabelGrid;
	private JTextField jTextFieldArr;
	private JButton jButtonStart, jButtonNext, jButtonClear;

	ArrayList<Integer> arr = new ArrayList<Integer>();

	public void init() {
		setLayout(new BorderLayout());
		this.setSize(950, 600);
		jPanelMenu = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jPanelMenu.setBackground(Color.RED);
		jTextFieldArr = new JTextField(60);
		jButtonStart = new JButton("Start");
		jButtonStart.addActionListener(this);
		jButtonNext = new JButton("Next");
		jButtonNext.addActionListener(this);
		jButtonClear = new JButton("Clear");
		jButtonClear.addActionListener(this);
		jPanelMenu.add(new JLabel("Array"));
		jPanelMenu.add(jTextFieldArr);
		jPanelMenu.add(jButtonStart);
		jPanelMenu.add(jButtonNext);
		jPanelMenu.add(jButtonClear);
		add(jPanelMenu, BorderLayout.NORTH);

		jPanelMain = new JPanel();
		add(jPanelMain, BorderLayout.CENTER);

		RandomGenerator();
	}

	private void RandomGenerator() {
		String t;
		String r = (new Integer((int) (Math.random() * 100))).toString();
		int cnt = 1;
		while (cnt < 10) {
			t = (new Integer((int) (Math.random() * 100))).toString();
			r += ", " + t;
			cnt++;
		}
		jTextFieldArr.setText(r);
	}

	private void Init() {
		String temp;
		arr.clear();
		StringTokenizer st = new StringTokenizer(jTextFieldArr.getText(), ",");
		while (st.hasMoreTokens()) {
			temp = st.nextToken().trim();
			if (temp.length() > 0) {
				arr.add(Integer.parseInt(temp));
			}
		}
		if (arr.size() == 0 || arr.size() > 15) {
			ShowMessage("Array size must have to be between 1-15");
			return;
		}
		jPanelMain.removeAll();
		jPanelGrid = new JPanel(new GridLayout(arr.size(), 1));
		jPanelMain.add(jPanelGrid);
		jPanelRow = new JPanel[arr.size()];
		jLabelGrid = new JLabel[arr.size() + 2][2 * arr.size() + 5];
		for (int i = 1; i <= arr.size(); i++) {
			jPanelRow[i - 1] = new JPanel(new GridLayout(1, arr.size()));
			jPanelGrid.add(jPanelRow[i - 1]);
			for (int j = 0; j < arr.size(); i++) {
				jLabelGrid[i - 1][j] = new JLabel(arr.get(j).toString());
				jPanelRow[i - 1].add(jLabelGrid[i - 1][j]);
			}
			return;
		}
		refresh();
	}

	private void refresh() {
		setSize(1950, 500);
		setSize(950, 600);
	}

	private void ShowMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if (ae.getSource() == jButtonStart) {
			Init();
		}
	}
}
