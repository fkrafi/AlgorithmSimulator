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

@SuppressWarnings("serial")
public class BinarySearch extends JApplet implements ActionListener, Runnable {
	private String z;
	private Integer imax, imin, Z;
	ArrayList<Integer> tempInput = new ArrayList<Integer>();
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
		setSize(1000, 125);

		border = BorderFactory.createLineBorder(Color.BLACK);
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
			if (cnt == 6)
				jTextFieldZ.setText(t);
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
		Collections.sort(tempInput);
		arr.clear();
		for (int i = 0; i < tempInput.size(); i++) {
			arr.add(tempInput.get(i).toString());
		}
		if (arr.size() == 0) {
			ShowMessage("Please Insert An Array To Search");
			return;
		} else if (arr.size() > 25) {
			ShowMessage("Array Cannot Be Greater Than 25");
			return;
		}
		z = jTextFieldZ.getText().trim();
		Z = Integer.parseInt(z);
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
		imin = 0;
		imax = arr.size() - 1;
		jButtonStart.setEnabled(false);
		jButtonNext.setEnabled(true);
		refresh();
	}

	private void refresh() {
		setSize(1950, 500);
		setSize(1000, 125);
	}

	void fade() {
		for (int i = 0; i < arr.size(); i++) {
			jLabelGridArr[i].setBackground(Color.LIGHT_GRAY);
			jLabelGridArr[i].setOpaque(true);
		}
	}

	void binary_search() {
		if (imax < imin) {
			flag = false;
			jButtonNext.setEnabled(false);
			ShowMessage("Not Found!");
		} else {
			fade();
			for (int i = imin; i <= imax; i++) {
				jLabelGridArr[i].setBackground(Color.cyan);
				jLabelGridArr[i].setOpaque(true);
			}
			int imid = (imin + imax) / 2;
			jLabelGridArr[imid].setBackground(Color.RED);
			jLabelGridArr[imid].setOpaque(true);
			if (arr.get(imid).equals(z)) {
				flag = false;
				jButtonNext.setEnabled(false);
				ShowMessage("Found at index " + (imid + 1));
			} else if (tempInput.get(imid) > Z) {
				imax = imid - 1;
			} else {
				imin = imid + 1;
			}
		}
	}

	private void ShowMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == jButtonStart) {
			jButtonAutoPlay.setEnabled(false);
			Init();
		} else if (ae.getSource() == jButtonNext) {
			binary_search();
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
			binary_search();
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
