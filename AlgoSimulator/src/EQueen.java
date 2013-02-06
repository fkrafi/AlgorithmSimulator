import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class EQueen extends JApplet implements ActionListener {

	JPanel jPanelBoard, jPanelFooter, jPanelMenu, jPanelCopyWrite;
	JButton jButtonStart, jButtonPlay, jButtonStep, jButtonRefresh;
	JLabel jLabelGrid[][] = new JLabel[9][9];

	Border border;

	public void init() {
		setLayout(new BorderLayout());

		border = BorderFactory.createLineBorder(Color.BLACK);
		jPanelBoard = new JPanel(new GridLayout(8, 8));
		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				jLabelGrid[i][j] = new JLabel("");
				jLabelGrid[i][j].setBorder(border);
				jLabelGrid[i][j].setFont(new Font("Serif", Font.PLAIN, 24));
				if (i % 2 != j % 2) {
					jLabelGrid[i][j].setForeground(Color.WHITE);
					jLabelGrid[i][j].setBackground(Color.BLACK);
				} else {
					jLabelGrid[i][j].setBackground(Color.WHITE);
				}
				jLabelGrid[i][j].setHorizontalAlignment(JLabel.CENTER);
				jLabelGrid[i][j].setVerticalAlignment(JLabel.CENTER);
				jLabelGrid[i][j].setOpaque(true);
				jPanelBoard.add(jLabelGrid[i][j]);
			}
		}
		add(jPanelBoard, BorderLayout.CENTER);

		jPanelFooter = new JPanel(new GridLayout(2, 1));

		jPanelMenu = new JPanel();

		jButtonStart = new JButton("Start");
		jButtonStart.addActionListener(this);

		jButtonStep = new JButton("Step");
		jButtonStep.addActionListener(this);

		jButtonRefresh = new JButton("Refresh");
		jButtonRefresh.addActionListener(this);

		jPanelMenu.add(jButtonStart);
		jPanelMenu.add(jButtonStep);
		jPanelMenu.add(jButtonRefresh);
		jPanelFooter.add(jPanelMenu);
		JLabel label = new JLabel(
				"Developed By :: Md. Fasihul Kabir (ID : 09.01.04.065)");
		label.setHorizontalAlignment(JLabel.RIGHT);
		jPanelFooter.add(label);
		add(jPanelFooter, BorderLayout.SOUTH);
		setSize(500, 550);
	}

	private int x[] = new int[10];

	private int ABS(int n) {
		return (n < 0) ? -n : n;
	}

	private boolean Place(int k, int i) {
		int j;
		for (j = 1; j <= k - 1; j++) {
			if ((x[j] == i) || (ABS(x[j] - i) == ABS(j - k)))
				return false;
		}
		return true;
	}

	private void BtKt(int r, int n) {
		if (r > n) {
			return;
		}
		for (int c = 1; c <= n; c++) {
			if (Place(r, c)) {
				x[r] = c;
				jLabelGrid[r][c].setText("Q");
				BtKt(r + 1, n);
				jLabelGrid[r][c].setText("");
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub
		if (ae.getSource() == jButtonStart) {
			BtKt(1, 8);
		} else if (ae.getSource() == jButtonStep) {
		}
	}
}
