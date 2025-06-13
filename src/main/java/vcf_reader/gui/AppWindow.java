package vcf_reader.gui;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import vcf_reader.gui.components.AppWindowContentPane;
import vcf_reader.gui.menu_items.CloseMenuItem;
import vcf_reader.gui.menu_items.ImprovementMenuItem;
import vcf_reader.gui.menu_items.OpenMenuItem;
import vcf_reader.gui.menu_items.SaveAsMenuItem;
import vcf_reader.improvements.EmailOrganisationImprovement;
import vcf_reader.improvements.PhoneOrganisationImprovement;

public class AppWindow extends JFrame {

	private static final long serialVersionUID = -706300805865875624L;

	private final AppWindowContentPane contentPane;
	boolean hasChanges;
	private String normalTitle;
	private File file;
	
	/**
	 * Creates the GUI and shows it. For thread safety, this constructor
	 * should be invoked from the event-dispatching thread.
	 */
	public AppWindow() {
		this.setTitle(null);
		
		// Create and set up the content pane.
		this.contentPane = new AppWindowContentPane();
		this.contentPane.setOpaque(true); //content panes must be opaque
        this.setContentPane(this.contentPane);
		
        // Create menu bar.
        JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem openMenuItem = new OpenMenuItem(this);
		fileMenu.add(openMenuItem);
		menuBar.add(fileMenu);
		JMenuItem closeMenuItem = new CloseMenuItem(this);
		fileMenu.add(closeMenuItem);
		JMenuItem saveAsMenuItem = new SaveAsMenuItem(this);
		fileMenu.add(saveAsMenuItem);
		JMenu improvementsMenu = new JMenu("Improvements");
		JMenuItem phoneOrganisation = new ImprovementMenuItem(this, "Organise phones", new PhoneOrganisationImprovement());
		improvementsMenu.add(phoneOrganisation);
		JMenuItem emailOrganisation = new ImprovementMenuItem(this, "Organise emails", new EmailOrganisationImprovement());
		improvementsMenu.add(emailOrganisation);
		menuBar.add(improvementsMenu);
		this.setJMenuBar(menuBar);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 500);
		this.setVisible(true);
	}
	
	@Override
	public void setTitle(String title) {
		StringBuilder titleBuilder = new StringBuilder("VCF reader");
		if (title != null && !title.isEmpty()) {
			titleBuilder.append(" - " + title);
		}
		String newTitle = titleBuilder.toString();
		super.setTitle(newTitle);
		this.normalTitle = newTitle;
	}

	public void resetHasChanges() {
		this.setHasChanges(false);
	}
	
	public void addChange(boolean changed) {
		this.setHasChanges(this.hasChanges || changed);
	}
	
	public AppWindowContentPane getAppWindowContentPane() {
		return this.contentPane;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
		if (file != null) {
			this.setTitle(file.getName());
		}
	}
	
	private void setHasChanges(boolean hasChanges) {
		this.hasChanges = hasChanges;
		StringBuilder title = new StringBuilder(this.normalTitle);
		if (hasChanges) {
			title.append(" *");
		}
		super.setTitle(title.toString());
	}
}
