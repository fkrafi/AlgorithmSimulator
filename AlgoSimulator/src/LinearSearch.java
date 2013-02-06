import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class LinearSearch extends JApplet implements ActionListener, Runnable {

	private Integer I;

	private String z;
	private ArrayList<String> arr = new ArrayList<String>();

	private Border border;
	private JPanel jPanelHeader, jPanelMiddle, jPanelArray, jPanelFooter;
	private JTextField jTextFieldArr, jTextFieldZ;
	private JButton jButtonStart, jButtonNext, jButtonRefresh, jButtonAutoPlay;
	private JLabel jLabelGridArr[];

	private boolean flag;
	private Thread thread;

	public void init() {
		setLayout(new BorderLayout());
		jPanelHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jPanelHeader.setBackground(Color.YELLOW);
		jPanelHeader.add(new JLabel("Array"));
		jTextFieldArr = new JTextField(25);
		jPanelHeader.add(jTextFieldArr);
		jPanelHeader.add(new JLabel("Search"));
		jTextFieldZ = new JTextField(25);
		jPanelHeader.add(jTextFieldZ);
		jButtonStart = new JButton("Start");
		jButtonStart.addActionListener(this);
		jPanelHeader.add(jButtonStart);
		jButtonNext = new JButton("Next");
		jButtonNext.setEnabled(false);
		jButtonNext.addActionListener(this);
		jPanelHeader.add(jButtonNext);
		jButtonAutoPlay = new JButton("Auto Play");
		jButtonAutoPlay.addActionListener(this);
		jPanelHeader.add(jButtonAutoPlay);
		jButtonRefresh = new JButton("Refresh");
		jButtonRefresh.addActionListener(this);
		jPanelHeader.add(jButtonRefresh);
		add(jPanelHeader, BorderLayout.NORTH);

		jPanelMiddle = new JPanel(new BorderLayout());
		jPanelArray = new JPanel();
		jPanelMiddle.add(jPanelArray, BorderLayout.CENTER);
		add(jPanelMiddle, BorderLayout.CENTER);

		jPanelFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		jPanelFooter.add(new JLabel(
				"Developed By :: Md. Fasihul Kabir (ID :: 09.01.04.065)"));
		add(jPanelFooter, BorderLayout.SOUTH);
		setSize(1050, 125);

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
			if (cnt == 6)
				jTextFieldZ.setText(t);
		}
		jTextFieldArr.setText(r);
	}

	private void Init() {
		String temp;
		StringTokenizer st = new StringTokenizer(jTextFieldArr.getText(), ",");
		arr.clear();
		while (st.hasMoreTokens()) {
			temp = st.nextToken().trim();
			if (temp.length() > 0) {
				arr.add(temp);
			}
		}
		if (arr.size() == 0) {
			ShowMessage("Please Insert An Array To Search");
			return;
		} else if (arr.size() > 25) {
			ShowMessage("Array Cannot Be Greater Than 25");
			return;
		}
		z = jTextFieldZ.getText().trim();
		if (z.trim().equals("")) {
			ShowMessage("Please Insert A Searching Value");
			return;
		}
		jLabelGridArr = new JLabel[arr.size()];
		jPanelArray.setLayout(new GridLayout(1, arr.size()));
		for (int i = 0; i < arr.size(); i++) {
			jLabelGridArr[i] = new JLabel(arr.get(i));
			jLabelGridArr[i].setBorder(border);
			jLabelGridArr[i].setBackground(Color.LIGHT_GRAY);
			jLabelGridArr[i].setFont(new Font("Serif", Font.PLAIN, 24));
			jLabelGridArr[i].setHorizontalAlignment(JLabel.CENTER);
			jLabelGridArr[i].setVerticalAlignment(JLabel.CENTER);
			jLabelGridArr[i].setOpaque(true);
			jPanelArray.add(jLabelGridArr[i]);
		}
		I = -1;
		jButtonStart.setEnabled(false);
		jButtonNext.setEnabled(true);
		refresh();
	}

	private void refresh() {
		setSize(1950, 500);
		setSize(1050, 125);
	}

	private void Search() {
		if (I >= 0)
			jLabelGridArr[I].setBackground(Color.LIGHT_GRAY);
		I++;
		if (I < arr.size()) {
			if (jLabelGridArr[I].getText().equals(z)) {
				flag = false;
				jLabelGridArr[I].setBackground(Color.GREEN);
				jButtonNext.setEnabled(false);
				ShowMessage("Found!");
			} else {
				jLabelGridArr[I].setBackground(Color.RED);
				jLabelGridArr[I].setOpaque(true);
			}
		} else {
			flag = false;
			jButtonNext.setEnabled(false);
			ShowMessage("Not Found!");
		}
	}

	private void ShowMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == jButtonStart) {
			Init();
			jButtonAutoPlay.setEnabled(false);
		} else if (ae.getSource() == jButtonNext) {
			Search();
		} else if (ae.getSource() == jButtonRefresh) {
			jButtonStart.setEnabled(true);
			jButtonNext.setEnabled(false);
			jButtonAutoPlay.setEnabled(true);
			jPanelArray.removeAll();
			refresh();
			RandomGenerator();
		} else if (ae.getSource() == jButtonAutoPlay) {
			autorun();
		}
	}

	private void autorun() {
		Init();
		jButtonNext.setEnabled(false);
		flag = true;
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		while (flag) {
			Search();
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
