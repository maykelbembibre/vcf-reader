package vcf_reader.gui.components;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

public class ListCellComponent extends JPanel {

	private static final long serialVersionUID = -6496941466814065971L;
	
	private final JLabel firstLine;
	private final JLabel secondLine;
	
	public ListCellComponent() {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setOpaque(true); //content panes must be opaque
        this.setBackground(Color.WHITE);
        this.firstLine = new JLabel();
		this.add(this.firstLine);
		this.secondLine = new JLabel();
		this.add(this.secondLine);
		MatteBorder border = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray);
		this.setBorder(border);
	}
	
	public void setFirstLine(String text) {
		this.firstLine.setText(text);
	}
	
	public void setSecondLine(String text) {
		this.secondLine.setText(text);
	}
}
