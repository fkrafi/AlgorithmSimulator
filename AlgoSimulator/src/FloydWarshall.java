import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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
public class FloydWarshall extends JApplet implements ActionListener, Runnable {

	Integer N, I, J, K;
	private Border border;
	JPanel jPanelHeader, jPanelGrid, jPanelGridLeft, jPanelGridRight,
			jPanelFooter;
	JTextField jTextFieldN;
	JButton jButtonInit, jButtonStart, JButtonNext, jButtonAutoPlay,
			jButtonRefresh, jButtonLeftGrid[][];

	JLabel jLabelRightGrid[][];

	private boolean flag;
	private Thread thread;

	public void init() {
		setLayout(new BorderLayout());
		jPanelHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
		jPanelHeader.add(new JLabel("No of Vertices"));
		jTextFieldN = new JTextField(10);
		jButtonInit = new JButton("Init");
		jButtonInit.addActionListener(this);
		jButtonStart = new JButton("Start");
		jButtonStart.setEnabled(false);
		jButtonStart.addActionListener(this);
		JButtonNext = new JButton("Next");
		JButtonNext.setEnabled(false);
		JButtonNext.addActionListener(this);
		jButtonAutoPlay = new JButton("Auto Play");
		jButtonAutoPlay.setEnabled(false);
		jButtonAutoPlay.addActionListener(this);
		jButtonRefresh = new JButton("Refresh");
		jButtonRefresh.addActionListener(this);
		jPanelHeader.add(jTextFieldN);
		jPanelHeader.add(jButtonInit);
		jPanelHeader.add(jButtonStart);
		jPanelHeader.add(JButtonNext);
		jPanelHeader.add(jButtonAutoPlay);
		jPanelHeader.add(jButtonRefresh);
		add(jPanelHeader, BorderLayout.NORTH);

		jPanelGrid = new JPanel(new GridLayout(1, 2));
		add(jPanelGrid, BorderLayout.CENTER);

		jPanelFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		jPanelFooter.add(new JLabel(
				"Developed By :: Md. Fasihul Kabir (ID :: 09.01.04.065)"));
		add(jPanelFooter, BorderLayout.SOUTH);

		border = BorderFactory.createLineBorder(Color.BLACK);
		setSize(1000, 500);
	}

	private void refresh() {
		setSize(1950, 700);
		setSize(1000, 500);
	}

	private void ShowMessage(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	void Init() {
		if (jTextFieldN.getText().isEmpty()
				|| isNum(jTextFieldN.getText()) == false) {
			ShowMessage("No of vertices must have to between 2 and 15");
			return;
		}
		N = Integer.parseInt(jTextFieldN.getText());
		if (N < 2 || N > 15) {
			ShowMessage("No of vertices must have to between 2 and 15");
			return;
		}
		jPanelGrid.removeAll();
		jPanelGridLeft = new JPanel(new GridLayout(N, N));
		jPanelGridLeft.setBackground(Color.cyan);
		jButtonLeftGrid = new JButton[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				jButtonLeftGrid[i][j] = new JButton("inf");
				jButtonLeftGrid[i][j].setBorder(border);
				jButtonLeftGrid[i][j].setBackground(Color.pink);
				jButtonLeftGrid[i][j]
						.setFont(new Font("Serif", Font.PLAIN, 24));
				jButtonLeftGrid[i][j].setHorizontalAlignment(JLabel.CENTER);
				jButtonLeftGrid[i][j].setVerticalAlignment(JLabel.CENTER);
				jButtonLeftGrid[i][j].addActionListener(this);
				jButtonLeftGrid[i][j].setOpaque(true);
				jPanelGridLeft.add(jButtonLeftGrid[i][j]);
			}
		}
		jPanelGrid.add(jPanelGridLeft);

		jPanelGridRight = new JPanel(new GridLayout(N, N));
		jPanelGridRight.setBackground(Color.cyan);
		jLabelRightGrid = new JLabel[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				jLabelRightGrid[i][j] = new JLabel("nil");
				jLabelRightGrid[i][j].setBorder(border);
				jLabelRightGrid[i][j].setBackground(Color.cyan);
				jLabelRightGrid[i][j]
						.setFont(new Font("Serif", Font.PLAIN, 24));
				jLabelRightGrid[i][j].setHorizontalAlignment(JLabel.CENTER);
				jLabelRightGrid[i][j].setVerticalAlignment(JLabel.CENTER);
				jLabelRightGrid[i][j].setOpaque(true);
				jPanelGridRight.add(jLabelRightGrid[i][j]);
			}
		}
		jPanelGrid.add(jPanelGridRight);
		jButtonInit.setEnabled(false);
		jButtonStart.setEnabled(true);
		jButtonAutoPlay.setEnabled(true);
		refresh();
	}

	private boolean isNum(String text) {
		int i, len = text.length();
		for (i = 0; i < len; i++) {
			if (text.charAt(i) >= '0' && text.charAt(i) <= '9') {
				continue;
			}
			return false;
		}
		return true;
	}

	private boolean isRightGridButton(Object source) {
		int i, j;
		for (i = 0; i < N; i++) {
			for (j = 0; j < N; j++) {
				if (jButtonLeftGrid[i][j] == source) {
					I = i;
					J = j;
					return true;
				}
			}
		}
		return false;
	}

	private void DisableLeftGridButton() {
		int i, j;
		for (i = 0; i < N; i++) {
			for (j = 0; j < N; j++) {
				jButtonLeftGrid[i][j].setEnabled(false);
			}
		}
	}

	void floyd_warshall() {
		if (flag == false)
			return;
		if (I >= 0 && J >= 0) {
			jButtonLeftGrid[I][J].setBackground(Color.pink);
		}
		J = (J + 1) % N;
		if (J == 0) {
			I = (I + 1) % N;
		}
		if (I == 0 && J == 0) {
			K = K + 1;
		}
		if (K >= N) {
			flag = false;
			finalState();
			return;
		}
		K = (K < 0) ? 0 : K;
		jButtonLeftGrid[I][J].setBackground(Color.red);
		Integer ij = (jButtonLeftGrid[I][J].getText().equals("inf")) ? 1 << 25
				: Integer.parseInt(jButtonLeftGrid[I][J].getText());
		Integer ik = (jButtonLeftGrid[I][K].getText().equals("inf")) ? 1 << 25
				: Integer.parseInt(jButtonLeftGrid[I][K].getText());
		Integer kj = (jButtonLeftGrid[K][J].getText().equals("inf")) ? 1 << 25
				: Integer.parseInt(jButtonLeftGrid[K][J].getText());
		Integer min = Math.min(ij, ik + kj);
		jButtonLeftGrid[I][J]
				.setText(min >= (1 << 25) ? "inf" : min.toString());
		if (min >= (1 << 25)) {
			jLabelRightGrid[I][J].setText("nil");
		} else {
			jLabelRightGrid[I][J].setText(min == ij ? I.toString() : K
					.toString());
		}
	}

	void finalState() {
		JButtonNext.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == jButtonInit) {
			Init();
		} else if (ae.getSource() == jButtonStart) {
			Initialize();
		} else if (ae.getSource() == JButtonNext) {
			floyd_warshall();
		} else if (ae.getSource() == jButtonRefresh) {
			jPanelGrid.removeAll();
			jButtonInit.setEnabled(true);
			jButtonStart.setEnabled(false);
			JButtonNext.setEnabled(false);
			jButtonAutoPlay.setEnabled(false);
		} else if (ae.getSource() == jButtonAutoPlay) {
			autorun();
		} else if (isRightGridButton(ae.getSource())) {
			String temp = JOptionPane.showInputDialog(null, "Path Cost of " + I
					+ " => " + J, "Add Cost of The Path", 1);
			if (isNum(temp) == false) {
				ShowMessage("Invalid Path Cost.");
				return;
			}
			jButtonLeftGrid[I][J].setText(temp);
		}
	}

	private void Initialize() {
		flag = true;
		jButtonStart.setEnabled(false);
		JButtonNext.setEnabled(true);
		jButtonAutoPlay.setEnabled(false);
		DisableLeftGridButton();
		I = J = K = -1;
	}

	private void autorun() {
		Initialize();
		JButtonNext.setEnabled(false);
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		while (flag) {
			floyd_warshall();
			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
