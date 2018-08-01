package goodbadugly.clientpc;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import javax.swing.ImageIcon;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

public class DuelGUI extends JFrame {
	private static final long serialVersionUID = 5469838739131169L;
	private JPanel statePanel;
	JLabel state;
	JPanel buttonsPanel;
	JLabel duelLabel;
	private JButton rock, scissor, paper;

	public DuelGUI(DuelWorker worker) {
		super("DUEL");

		setResizable(false);
		setPreferredSize(new Dimension(702, 300));

		state = new JLabel("And the winner is...");
		statePanel = new JPanel();
		statePanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		statePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		statePanel.add(state);
		add(statePanel, BorderLayout.NORTH);

		rock = new JButton(new ImageIcon(new ImageIcon("bin/images/duels/rock.jpg").getImage().getScaledInstance(234, 265,java.awt.Image.SCALE_SMOOTH)));
		rock.addActionListener(MyListener.getInstance());
		rock.setActionCommand("rock");
		scissor = new JButton(new ImageIcon(new ImageIcon("bin/images/duels/scissor.jpg").getImage().getScaledInstance(234,	265, java.awt.Image.SCALE_SMOOTH)));
		scissor.addActionListener(MyListener.getInstance());
		scissor.setActionCommand("scissor");
		paper = new JButton(new ImageIcon(new ImageIcon("bin/images/duels/paper.jpg").getImage().getScaledInstance(234, 265,java.awt.Image.SCALE_SMOOTH)));
		paper.addActionListener(MyListener.getInstance());
		paper.setActionCommand("paper");

		MyListener.setWorker(worker);

		buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(1, 3));
		buttonsPanel.add(rock);
		buttonsPanel.add(scissor);
		buttonsPanel.add(paper);

		add(buttonsPanel, BorderLayout.CENTER);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
