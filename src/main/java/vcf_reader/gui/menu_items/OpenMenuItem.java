package vcf_reader.gui.menu_items;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import vcf_reader.format.converters.ContactIterableConverter;
import vcf_reader.format.entities.ContactIterable;
import vcf_reader.gui.AppWindow;
import vcf_reader.gui.Tools;
import vcf_reader.gui.components.AppWindowContentPane;

public class OpenMenuItem extends JMenuItem {

	private static final long serialVersionUID = -1834649816900326806L;

	public OpenMenuItem(AppWindow appWindow) {
		super("Open");
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int returnVal = fileChooser.showOpenDialog(OpenMenuItem.this);
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fileChooser.getSelectedFile();
		            //This is where a real application would open the file.
		            ContactIterable contacts;
		            if (file == null) {
		            	contacts = null;
		            } else {
		            	try {
							contacts = ContactIterableConverter.vcfToModel(file);
						} catch (FileNotFoundException e1) {
							contacts = null;
						}
		            }
		            if (contacts == null) {
		            	Tools.printMessage(OpenMenuItem.this, "Invalid file.");
		            } else {
		            	appWindow.setFile(file);
		            	appWindow.resetHasChanges();
		            	AppWindowContentPane appWindowContentPane = appWindow.getAppWindowContentPane();
		            	appWindowContentPane.setContactIterable(contacts);
		            }
		        }
			}}
		);
	}
}
