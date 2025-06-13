package vcf_reader.gui.menu_items;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Optional;

import javax.swing.JMenuItem;

import vcf_reader.format.entities.Contact;
import vcf_reader.format.entities.ContactIterable;
import vcf_reader.gui.AppWindow;
import vcf_reader.gui.Tools;
import vcf_reader.gui.components.AppWindowContentPane;
import vcf_reader.improvements.Improvement;
import vcf_reader.improvements.ImprovementApplier;

public class ImprovementMenuItem extends JMenuItem {

	private static final long serialVersionUID = -1834649816900326806L;

	public ImprovementMenuItem(AppWindow appWindow, String title, Improvement improvement) {
		super(title);
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AppWindowContentPane appWindowContentPane = appWindow.getAppWindowContentPane();
				Optional<ContactIterable> opContactIterable = appWindowContentPane.getContactIterable();
				if (opContactIterable.isEmpty()) {
					Tools.printMessage(appWindowContentPane, "No contacts to apply this improvement to.");
				} else {
					ContactIterable contactIterable = opContactIterable.get();
					ImprovementApplier applier = new ImprovementApplier();
					applier.addImprovement(improvement);
					Collection<Contact> changedContacts = applier.apply(contactIterable);
					appWindow.addChange(!changedContacts.isEmpty());
					appWindowContentPane.setContactIterable(contactIterable); // Force list model update.
				}
			}}
		);
	}
}
