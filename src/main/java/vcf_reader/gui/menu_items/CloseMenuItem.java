package vcf_reader.gui.menu_items;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import vcf_reader.gui.AppWindow;
import vcf_reader.gui.components.AppWindowContentPane;

public class CloseMenuItem extends JMenuItem {

	private static final long serialVersionUID = -1834649816900326806L;

	public CloseMenuItem(AppWindow appWindow) {
		super("Close");
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				appWindow.setTitle(null);
				AppWindowContentPane appWindowContentPane = appWindow.getAppWindowContentPane();
				appWindowContentPane.setContactIterable(null);
			}}
		);
	}
}
