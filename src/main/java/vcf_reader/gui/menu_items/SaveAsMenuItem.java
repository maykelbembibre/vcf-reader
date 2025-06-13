package vcf_reader.gui.menu_items;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Optional;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;

import vcf_reader.format.entities.ContactIterable;
import vcf_reader.gui.AppWindow;
import vcf_reader.gui.Tools;
import vcf_reader.gui.components.AppWindowContentPane;
import vcf_reader.input_output.OutputTools;
import vcf_reader.input_output.models.FileName;

public class SaveAsMenuItem extends JMenuItem {

	private static final long serialVersionUID = -1834649816900326806L;

	public SaveAsMenuItem(AppWindow appWindow) {
		super("Save as...");
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppWindowContentPane appWindowContentPane = appWindow.getAppWindowContentPane();
				Optional<ContactIterable> opContactIterable = appWindowContentPane.getContactIterable();
				if (opContactIterable.isEmpty()) {
					Tools.printMessage(appWindowContentPane, "No contacts to save.");
				} else {
					File previousFile = appWindow.getFile();
					JFileChooser fileChooser = new JFileChooser();
					if (previousFile != null) {
						FileName fileName = new FileName(previousFile);
						fileName.rename(fileName.getName() + "-improved");
						File improvedFile = new File(previousFile.getParent(), fileName.getNameWithExtension());
						fileChooser.setSelectedFile(improvedFile);
					}
					int returnVal = fileChooser.showSaveDialog(SaveAsMenuItem.this);
			        if (returnVal == JFileChooser.APPROVE_OPTION) {
			            File file = fileChooser.getSelectedFile();
			            ContactIterable contacts = opContactIterable.get();
			    		OutputTools.write(contacts, file);
			    		appWindow.setFile(file);
			    		appWindow.resetHasChanges();
			        }
				}
			}}
		);
	}
}
