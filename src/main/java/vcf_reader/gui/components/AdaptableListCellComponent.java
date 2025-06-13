package vcf_reader.gui.components;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

public class AdaptableListCellComponent extends JPanel {

	private static final long serialVersionUID = -6496941466814065971L;
	
	public AdaptableListCellComponent(Iterable<String> lines) {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setOpaque(true); //content panes must be opaque
        this.setBackground(Color.WHITE);
        for (String line : lines) {
        	this.add(new JLabel(line));
        }        	
		MatteBorder border = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray);
		this.setBorder(border);
	}
}
