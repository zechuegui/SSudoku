package Frames;

import Main.PrologCell;
import org.jpl7.Compound;
import org.jpl7.Query;
import org.jpl7.Term;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainFrame {

	private static JFrame frame;
	private static JTextField [][] gameBoard;
	private static final String fontName = "Courier New";

	private static final ArrayList<PrologCell> emptyCells = new ArrayList<>();
	private static final ArrayList<PrologCell> numberedCells = new ArrayList<>();

	private static final int topPadding = 80;
	private static final int leftPadding = 40;

	public static void setup() {

		gameBoard = new JTextField[9][9];

		frame = new JFrame("SSudoku");

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);       //Main Frame
		frame.setSize(650, 700);
		frame.setLayout(null);
		frame.setResizable(false);

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {

				// Horizontal Labels
				JLabel hLabel = new JLabel("__");
				hLabel.setFont(new Font(fontName, (j % 3 == 0)  ? Font.BOLD : Font.PLAIN, 40));
				hLabel.setVisible(true);
				hLabel.setBounds(i * 60 + 18 + leftPadding, j * 60 - 32 + topPadding, 50, 70);
				frame.add(hLabel);

				// Vertical Labels
				JLabel labelVert = new JLabel("|");
				labelVert.setFont(new Font(fontName, (i % 3 == 0) ? Font.BOLD : Font.PLAIN, 40));
				labelVert.setVisible(true);
				labelVert.setBounds(i * 60 + leftPadding, j * 60 + 3 + topPadding, 50, 100);
				frame.add(labelVert);

				// TextFields for inputs
				JTextField label = new JTextField();
				label.setFont(new Font(fontName, Font.BOLD, 40));
				label.setVisible(true);
				label.setBounds(i * 60 + 18 + leftPadding, j * 60 + 30 + topPadding, 50, 50);
				frame.add(label);

				gameBoard[i][j] = label;
			}
		}

		// This is for the right and bottom borders
		for (int i = 0; i < 9; i++) {

			// Horizontal Labels
			JLabel hLabel = new JLabel("__");
			hLabel.setFont(new Font(fontName, Font.BOLD, 40));
			hLabel.setVisible(true);
			hLabel.setBounds(i * 60 + 18 + leftPadding, (9 * 60 - 32) + topPadding, 50, 70);
			frame.add(hLabel);

			// Vertical Labels
			JLabel labelVert = new JLabel("|");
			labelVert.setFont(new Font(fontName, Font.BOLD, 40));
			labelVert.setVisible(true);
			labelVert.setBounds(9 * 60 + leftPadding, i * 60 + 3 + topPadding, 50, 100);
			frame.add(labelVert);

		}

		JButton calculateButton = new JButton("Solve");
		calculateButton.setBounds(325 - 50, 30, 100, 33);
		calculateButton.addActionListener(e -> calculatePressed());
		frame.add(calculateButton);

		frame.setVisible(true);

	}

	private static void calculatePressed() {

		try {

			int y = 1;
			for (JTextField[] arr : gameBoard) {
				int x = 1;
				for (JTextField cell : arr) {

					if (!cell.getText().equals("") ) {
						int n = Integer.parseInt(cell.getText());
						if (!(n > 0 && n < 10)) {
							throw new Exception();
						}
					}

					if (cell.getText().isEmpty()) {
						PrologCell pCell = new PrologCell(x, y, 0);
						emptyCells.add(pCell);
					} else {
						PrologCell pCell = new PrologCell(x, y, Integer.parseInt(cell.getText()));
						numberedCells.add(pCell);
					}

					x++;
				}
				y++;
			}

			String prologString = createStartState();
			createPrologQuery(prologString);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(frame, "Invalid input: only numbers 1 to 9 are allowed");
		}
	}

	private static String createStartState() {

		String startStateString = "estado_inicial(e(" + emptyCells + "," + numberedCells + ")):-numeros(D).";

		StringSelection stringSelection = new StringSelection(startStateString);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, null);

		JOptionPane.showMessageDialog(frame, "String has been copied to clipboard");

		return startStateString;
	}

	private static void createPrologQuery(String prologString) throws IOException {
		
		BufferedWriter bw = null;
		try {

			FileWriter fw = new FileWriter("src/main/resources/Files/sudoku.pl");
			bw = new BufferedWriter(fw);
			bw.write(prologString);

		} finally {
			if (bw != null) {
				bw.close();
			}
		}

		Query q = new Query("mod1:consult('src/main/resources/Files/sudoku.pl')");

		Term teste = new Compound("p.");
		Query aiai = new Query(teste);

		while (aiai.hasNext()) {
			int a = 0;
		}
		
	}
	
	    /*FileWriter fw = new FileWriter(fileName, true);
	    BufferedWriter bw = new BufferedWriter(fw);
	    bw.write("Spain");
	    bw.newLine();
	    bw.close();*/
}

