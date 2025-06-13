package vcf_reader.gui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.text.JTextComponent;

import vcf_reader.format.entities.Contact;
import vcf_reader.format.entities.ContactIterable;
import vcf_reader.format.models.Statistics;
import vcf_reader.gui.MyCellRenderer;

public class AppWindowContentPane extends JPanel {

	private static final long serialVersionUID = 6828491862536000990L;

	private final DefaultListModel<Contact> listModel;
	private final JScrollPane listScrollPane;
	private final JLabel noContactsJLabel;
	private final JTextComponent details;

	private ContactIterable contactIterable;
	
	public AppWindowContentPane() {
		super(new BorderLayout());
		this.listModel = new DefaultListModel<>();        
		JList<Contact> list = new JList<>(this.listModel);
        list.setCellRenderer(new MyCellRenderer());
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.setVisibleRowCount(5);
        this.listScrollPane = new JScrollPane(list);
        this.add(listScrollPane, BorderLayout.CENTER);

        this.noContactsJLabel = new JLabel("No contacts.");
        this.add(noContactsJLabel, BorderLayout.NORTH);
        
        this.details = new CopyableTextComponent(this);
        JScrollPane detailsScrollPane = new JScrollPane(this.details, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        detailsScrollPane.setBorder(BorderFactory.createEmptyBorder());
        detailsScrollPane.setPreferredSize(new Dimension(30, 150)); // Ensure it has height.
        this.add(detailsScrollPane, BorderLayout.SOUTH);
        
        this.setContactIterable(null);
	}
	
	public Optional<ContactIterable> getContactIterable() {
		return Optional.ofNullable(this.contactIterable);
	}

	public void setContactIterable(ContactIterable contactIterable) {
		this.contactIterable = contactIterable;
		if (contactIterable == null) {
			this.updateList(null);
			this.updateDetails(null);
		} else {
			this.updateList(contactIterable);
			Statistics stats = contactIterable.getStatistics();
			List<String> details = new ArrayList<>();
			details.add("Valid contacts: " + contactIterable.getValidContactCount() + ".");
			details.add("Contacts without a phone: " + stats.getNumberOfContactsWithoutPhone() + ".");
			details.add("Invalid contacts: " + stats.getInvalidContactVCardLines().size() + ".");
			this.updateDetails(details);
		}
	}
	
	private void updateList(ContactIterable contactIterable) {
		this.listModel.clear();
		boolean empty = true;
		if (contactIterable != null) {
			for (Contact contact : contactIterable) {
				this.listModel.addElement(contact);
				empty = false;
			}
		}
		this.noContactsJLabel.setVisible(empty);
		this.listScrollPane.setVisible(!empty);
	}
	
	private void updateDetails(List<String> details) {
		StringBuilder newText = new StringBuilder();
		if (details != null) {
			boolean first = true;
			for (String detail : details) {
				if (!first) {
					newText.append("\n");
				}
				newText.append(detail);
				first = false;
			}
		}
		this.details.setVisible(!newText.isEmpty());
		this.details.setText(newText.toString());
	}
}
