import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class LCS extends JApplet implements ActionListener, Runnable {
	private String s1, s2, res;
	private int len1, len2;
	private int I, J;
	private final int leftTop = 1;
	private final int top = 2;
	private final int left = 3;

	private Integer B[][], C[][];
	private final String direction[] = { " ", "\u2196", "\u2190", "\u2191" };

	private Border border;
	private JTextField jTextFieldS1, jTextFieldS2;
	private JLabel jLabelGridB[][], jLabelGridC[][];
	private JButton jButtonStart, jButtonNext, jButtonRefresh, jButtonAutoPlay;
	private JPanel jPanelMain, jPanelHeader, jPanelFooter, jPanelMiddle,
			jPanelTableB, jPanelTableC;

	private JLabel jlabelLCSLen, jLabelLCSString;

	private boolean flag;
	private Thread thread;

	public void init() {
		jPanelMain = new JPanel();
		jPanelMain.setLayout(new BorderLayout());

		/************ North ****************/
		jPanelHeader = new JPanel();
		jPanelHeader.setLayout(new FlowLayout(FlowLayout.LEFT));
		jPanelHeader.setBackground(Color.YELLOW);
		jTextFieldS1 = new JTextField(25);
		jTextFieldS2 = new JTextField(25);
		jButtonStart = new JButton("Start");
		jButtonStart.addActionListener(this);
		jButtonNext = new JButton("Next");
		jButtonNext.setEnabled(false);
		jButtonNext.addActionListener(this);
		jButtonRefresh = new JButton("Refresh");
		jButtonRefresh.addActionListener(this);
		jButtonAutoPlay = new JButton("Auto Play");
		jButtonAutoPlay.addActionListener(this);
		jPanelHeader.add(new Label("String 1"));
		jPanelHeader.add(jTextFieldS1);
		jPanelHeader.add(new Label("String 2"));
		jPanelHeader.add(jTextFieldS2);
		jPanelHeader.add(jButtonStart);
		jPanelHeader.add(jButtonNext);
		jPanelHeader.add(jButtonRefresh);
		jPanelHeader.add(jButtonAutoPlay);
		jPanelMain.add(jPanelHeader, BorderLayout.NORTH);

		/*************** Center *********************/
		jPanelMiddle = new JPanel();
		jPanelMiddle.setLayout(new GridLayout(1, 2));
		jPanelTableB = new JPanel();
		jPanelTableC = new JPanel();
		jPanelMiddle.add(jPanelTableB);
		jPanelTableB.setBackground(Color.GREEN);
		jPanelMiddle.add(jPanelTableC);
		jPanelTableC.setBackground(Color.CYAN);
		jPanelMain.add(jPanelMiddle, BorderLayout.CENTER);

		/************ South ****************/
		jPanelFooter = new JPanel();
		jPanelFooter.setLayout(new GridLayout(2, 2));

		jlabelLCSLen = new JLabel(" ");
		ChangeLabelSytle(jlabelLCSLen, Color.GREEN, Color.BLACK);

		jLabelLCSString = new JLabel(" ");
		ChangeLabelSytle(jLabelLCSString, Color.CYAN, Color.BLACK);

		JLabel label1 = new JLabel("");
		ChangeLabelSytle(label1, Color.DARK_GRAY, Color.WHITE);
		JLabel label2 = new JLabel(
				"Developed By :: Md. Fasihul Kabir (ID: 09.01.04.065)");
		label2.setHorizontalAlignment(JLabel.RIGHT);
		ChangeLabelSytle(label2, Color.DARK_GRAY, Color.WHITE);

		jPanelFooter.add(jlabelLCSLen);
		jPanelFooter.add(jLabelLCSString);
		jPanelFooter.add(label1);
		jPanelFooter.add(label2);

		jPanelMain.add(jPanelFooter, BorderLayout.SOUTH);

		setSize(1000, 500);
		add(jPanelMain);

		border = BorderFactory.createLineBorder(Color.BLACK);
	}

	private void ShowMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	private void lcs(int i, int j) {
		if (i <= 0 || j <= 0 || i > len1 || j > len2) {
			return;
		} else if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
			B[i][j] = B[i - 1][j - 1] + 1;
			C[i][j] = leftTop;
		} else if (B[i - 1][j] > B[i][j - 1]) {
			B[i][j] = B[i - 1][j];
			C[i][j] = top;
		} else {
			B[i][j] = B[i][j - 1];
			C[i][j] = left;
		}
	}

	private void Init() {
		res = "";
		s1 = jTextFieldS1.getText();
		s2 = jTextFieldS2.getText();
		len1 = s1.length();
		len2 = s2.length();
		if (len1 < 1) {
			ShowMessage("First String cannot be empty");
			return;
		} else if (len1 > 20) {
			ShowMessage("First String greater than 20 characters");
			return;
		} else if (len2 < 1) {
			ShowMessage("Second String cannot be empty");
			return;
		} else if (len2 > 20) {
			ShowMessage("Second String is greater than 20 characters");
			return;
		}
		B = new Integer[len1 + 1][len2 + 1];
		C = new Integer[len1 + 1][len2 + 1];
		for (int i = 0; i <= len1; i++) {
			for (int j = 0; j <= len2; j++) {
				B[i][j] = C[i][j] = 0;
			}
		}
		I = 1;
		J = 0;

		jLabelGridB = new JLabel[len1 + 2][len2 + 2];
		jLabelGridC = new JLabel[len1 + 2][len2 + 2];

		DrawTables();
		jButtonStart.setEnabled(false);
		jButtonNext.setEnabled(true);
		jTextFieldS1.setEditable(false);
		jTextFieldS2.setEditable(false);
	}

	private void DrawTables() {
		int i, j;
		jLabelGridB[0][0] = new JLabel(" ");
		jLabelGridC[0][0] = new JLabel(" ");
		for (i = 1; i <= len1; i++) {
			jLabelGridB[i][0] = new JLabel("" + s1.charAt(i - 1));
			jLabelGridC[i][0] = new JLabel("" + s1.charAt(i - 1));
		}
		for (i = 1; i <= len2; i++) {
			jLabelGridB[0][i] = new JLabel("" + s2.charAt(i - 1));
			jLabelGridC[0][i] = new JLabel("" + s2.charAt(i - 1));
		}
		for (i = 1; i <= len1; i++) {
			for (j = 1; j <= len2; j++) {
				jLabelGridB[i][j] = new JLabel(" ");
				jLabelGridC[i][j] = new JLabel(direction[C[i][j]]);
			}
		}
		jPanelTableB.removeAll();
		jPanelTableB.setLayout(new GridLayout(len1 + 1, len2 + 1));
		for (i = 0; i <= len1; i++) {
			for (j = 0; j <= len2; j++) {
				jLabelGridB[i][j].setBorder(border);
				jLabelGridB[i][j].setHorizontalAlignment(JLabel.CENTER);
				jLabelGridB[i][j].setVerticalAlignment(JLabel.CENTER);
				jPanelTableB.add(jLabelGridB[i][j]);
			}
		}
		jPanelTableC.removeAll();
		jPanelTableC.setLayout(new GridLayout(len1 + 1, len2 + 1));
		for (i = 0; i <= len1; i++) {
			for (j = 0; j <= len2; j++) {
				jLabelGridC[i][j].setBorder(border);
				jLabelGridC[i][j].setHorizontalAlignment(JLabel.CENTER);
				jLabelGridC[i][j].setVerticalAlignment(JLabel.CENTER);
				jPanelTableC.add(jLabelGridC[i][j]);
			}
		}
		refresh();
	}

	private void Refresh() {
		s1 = s2 = "";
		jTextFieldS1.setEditable(true);
		jTextFieldS1.setText("");
		jTextFieldS2.setEditable(true);
		jTextFieldS2.setText("");
		jPanelTableB.removeAll();
		jPanelTableC.removeAll();
		jButtonStart.setEnabled(true);
		jButtonNext.setEnabled(false);
		jlabelLCSLen.setText("");
		jLabelLCSString.setText("");
		refresh();
	}

	private void refresh() {
		this.setSize(1950, 500);
		this.setSize(1000, 500);
	}

	void PrintLCS(int i, int j) {
		if (i <= 0 || j <= 0)
			return;
		if (C[i][j] == leftTop) {
			PrintLCS(i - 1, j - 1);
			ChangeLabelSytle(jLabelGridC[i][j], Color.RED, Color.WHITE);
			res += s1.charAt(i - 1);
		} else if (C[i][j] == top) {
			PrintLCS(i - 1, j);
		} else {
			PrintLCS(i, j - 1);
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == jButtonStart) {
			Init();
		} else if (ae.getSource() == jButtonNext) {
			Traves();
		} else if (ae.getSource() == jButtonRefresh) {
			Refresh();
		} else if (ae.getSource() == jButtonAutoPlay) {
			autorun();
		}
	}

	private void FinalState(int i, int j) {
		ChangeLabelSytle(jLabelGridC[i][j], Color.CYAN, Color.BLACK);
		jlabelLCSLen.setText("Length of LCS is :: " + B[len1][len2].toString());
		ShowMessage("Length of Longest Common Sequence is :: " + B[len1][len2]);
		PrintLCS(len1, len2);
		jLabelLCSString.setText("LCS is :: " + res);
		ShowMessage("Longest Common Sequence is :: " + res);
		jButtonNext.setEnabled(false);
		flag = false;
	}

	private void Traves() {
		ChangeLabelSytle(jLabelGridB[I][J], Color.GREEN, Color.BLACK);
		ChangeLabelSytle(jLabelGridC[I][J], Color.cyan, Color.BLACK);
		J++;
		if (J > len2) {
			I++;
			J = 1;
			if (I > len1)
				I = 1;
		}
		lcs(I, J);
		jLabelGridB[I][J].setText(B[I][J].toString());
		ChangeLabelSytle(jLabelGridB[I][J], Color.RED, Color.WHITE);
		jLabelGridC[I][J].setText(direction[C[I][J]]);
		ChangeLabelSytle(jLabelGridC[I][J], Color.RED, Color.WHITE);
		if (I == len1 && J == len2) {
			FinalState(I, J);
		}
	}

	private void ChangeLabelSytle(JLabel label, Color bColor, Color fColor) {
		label.setBackground(bColor);
		label.setForeground(fColor);
		label.setOpaque(true);
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
			Traves();
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
