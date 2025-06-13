package vcf_reader.gui.components;

import java.awt.Component;

import javax.swing.JTextArea;

public class CopyableTextComponent extends JTextArea {

	private static final long serialVersionUID = 793798810159898558L;

	public CopyableTextComponent(Component parentComponent) {
		super(5, 20);
        this.setBackground(parentComponent.getBackground());
        this.setEditable(false);
        this.setLineWrap(true);
	}
}