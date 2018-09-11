import java.awt.*;
import javax.swing.*;

public class Menu extends JPanel {
	
	public Menu() {
		
		setLayout(new GridBagLayout());
		
		// Init menu panel
		GridBagConstraints grid = new GridBagConstraints();
		JPanel panelMenu = new JPanel();
		panelMenu.setLayout(new GridBagLayout());
		grid.gridx = grid.gridy = 0;
		grid.weightx = 2;
		grid.fill = GridBagConstraints.BOTH;
		grid.anchor = GridBagConstraints.LINE_START;
		add(panelMenu, grid);
		
		// Header
		JPanel header = new JPanel();
		header.setLayout(new GridBagLayout());
		grid.weightx = 1;
		grid.fill = GridBagConstraints.HORIZONTAL;
		grid.anchor = GridBagConstraints.CENTER;
		panelMenu.add(header, grid);
		
		// Label
		JLabel labelMenu = new JLabel("<html><h1>Men\u00FC</h1></html>");
		header.add(labelMenu, grid);
		
	}

}
