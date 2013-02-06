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
public class SelectionSort extends JApplet implements ActionListener, Runnable {
	private Integer I, minj;
	ArrayList<Integer> tempInput = new ArrayList<Integer>();

	private Border border;
	private JPanel jPanelHeader, jPanelMiddle, jPanelArray, jPanelFooter;
	private JTextField jTextFieldArr;
	private JButton jButtonStart, jButtonNext, jButtonRefresh, jButtonAutoPlay;
	private JLabel jLabelGridArr[];

	private boolean flag;
	private Thread thread;

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
			jLabelGridArr[i].setBackground(new Color(0xe3e3e3));
			jLabelGridArr[i].setFont(new Font("Serif", Font.PLAIN, 24));
			jLabelGridArr[i].setHorizontalAlignment(JLabel.CENTER);
			jLabelGridArr[i].setVerticalAlignment(JLabel.CENTER);
			jLabelGridArr[i].setOpaque(true);
			jPanelArray.add(jLabelGridArr[i]);
		}
		I = 0;
		jButtonStart.setEnabled(false);
		jButtonNext.setEnabled(true);
		refresh();
	}

	private void refresh() {
		setSize(1950, 500);
		setSize(1050, 125);
	}

	private void selection_sort() {
		int minx;
		minj = I;
		minx = tempInput.get(I);
		for (int j = I + 1; j < tempInput.size(); j++) {
			if (tempInput.get(j) < minx) {
				minj = j;
				minx = tempInput.get(j);
			}
		}
		tempInput.set(minj, tempInput.get(I));
		tempInput.set(I, minx);
	}

	private void ShowMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	private void changeLook() {
		for (int i = 0; i < tempInput.size(); i++) {
			jLabelGridArr[i].setText(tempInput.get(i).toString());
			if (i <= I) {
				jLabelGridArr[i].setBackground(new Color(0x222222));
				jLabelGridArr[i].setForeground(Color.white);
			} else {
				jLabelGridArr[i].setBackground(new Color(0xe3e3e3));
			}
			jLabelGridArr[i].setOpaque(true);
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == jButtonStart) {
			Init();
			jButtonAutoPlay.setEnabled(false);
			jTextFieldArr.setEditable(false);
		} else if (ae.getSource() == jButtonNext) {
			sort();
		} else if (ae.getSource() == jButtonRefresh) {
			jTextFieldArr.setEditable(true);
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

	private void sort() {
		if (I < tempInput.size() - 1) {
			selection_sort();
			changeLook();
			jLabelGridArr[I].setBackground(Color.RED);
			jLabelGridArr[I].setOpaque(true);
			jLabelGridArr[minj].setBackground(Color.RED);
			jLabelGridArr[minj].setOpaque(true);
			I++;
		} else {
			flag = false;
			jButtonNext.setEnabled(false);
			changeLook();
			ShowMessage("Sorted!");
		}
	}

	public void autorun() {
		Init();
		jButtonNext.setEnabled(false);
		flag = true;
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		while (flag) {
			sort();
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
