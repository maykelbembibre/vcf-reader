package vcf_reader.gui;

import java.awt.Component;

import javax.swing.JOptionPane;

public class Tools {

	public static void printMessage(Component parentComponent, String message) {
		JOptionPane.showMessageDialog(parentComponent, message);
	}
}
