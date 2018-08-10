/*
 ******************************************************************
 Copyright (c) 2017 Simon Kn\u00F6dler
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:

 * Redistributions of source code must retain the above copyright
 notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above
 copyright notice, this list of conditions and the following
 disclaimer in the documentation and/or other materials provided
 with the distribution.
 * Neither the name of the xmlunit.sourceforge.net nor the names
 of its contributors may be used to endorse or promote products
 derived from this software without specific prior written
 permission.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 POSSIBILITY OF SUCH DAMAGE.

 ******************************************************************
 */

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.table.*;

public class GUI_Schleiferl extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	// Global Classes
	private Calculations 		calc = new Calculations();
	private Log 				log = new Log();

	// Global Panels
	private JPanel panelLayout;
	private JPanel panelOrder;
	private JPanel panelMatches;
	private JPanel panelNames;

	// Global Labels
	private JLabel 				labelNumberOfPlayers;
	private JLabel 				labelNumberOfRounds;
	private JLabel[] 			labelA;
	private JLabel[] 			labelB;
	private JLabel[] 			labelCourts;
	private JLabel[] 			labelPlayer;

	// Global Input Fields
	private JTextField[] 		inputResultA;
	private JTextField[] 		inputResultB;
	private JTextField[] 		inputPlayer;

	// Global Buttons
	private JButton 			buttonReset;
	private JButton 			buttonEndRound;
	private JButton 			buttonNextRound;
	private JButton 			buttonAdd;
	private JButton 			buttonStart;

	// Global Radio Buttons / Radio Button Groups
	private JRadioButton 		radioTopTop;
	private JRadioButton 		radioTopBottom;
	private ButtonGroup 		buttonGroupMode;

	// Global Models
	private DefaultTableModel 	model = new DefaultTableModel();

	// Global Tables
	private JTable 				tblOrder = new JTable(model);

	// Global Variables
	private int 				pointsVictory = 2; 		// Points of Victory
	private int 				pointsDraw = 1;			// Points of Draw
	private int 				pointsLost = 0; 		// Points of Lost
	private int					numberOfCourts = 6;		// Number of Courts to play
	private int					numberAddPlayers = 10;	// Number of Player Inputs on Start Screen

	/**
	 * Constructor Method
	 */
	public GUI_Schleiferl() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Schleiferlturnier");

		// Icon f\u00FCr Bierminton setzen
		ArrayList<Image> images = new ArrayList<Image>();
		File directory = new File("Images/Icons/");

		for (File file : directory.listFiles()) {
			try {
				if (file.getName().toLowerCase().endsWith(".png")) {
					File pathToFile = new File(directory + "\\" + file.getName());
					Image image = ImageIO.read(pathToFile);
					images.add(image);
				}
			} catch (IOException e) {
				// Auto-generated catch block
				e.printStackTrace();
			}
		}

		this.setIconImages(images);

		setLayout(new GridBagLayout());
		GridBagConstraints grid = new GridBagConstraints();

		this.panelLayout = new JPanel();
		this.panelLayout.setLayout(new GridBagLayout());
        grid.gridx = grid.gridy = 0;
        grid.weightx = grid.weighty = 1;
        grid.fill = GridBagConstraints.BOTH;
        add(this.panelLayout, grid);

		// Add Sections
		this.initOrder();
		this.initPlayers();

		this.panelOrder.setVisible(true);
		this.panelNames.setVisible(true);

		pack();
        setSize(new Dimension(1400, 800));
        setMinimumSize(new Dimension(800, 600));
        setVisible(true);

	}

	/**
	 * Panel f\u00FCr Ranglisten Anzeige vorbereiten.
	 */
	private void initOrder() {

		//Panel f\u00FCr Rangliste vorbereiten
		GridBagConstraints grid = new GridBagConstraints();
		this.panelOrder = new JPanel();
		this.panelOrder.setLayout(new GridBagLayout());
		grid.weightx = grid.weighty = 1;
        grid.fill = GridBagConstraints.BOTH;

        //Abst\u00E4nde zum Panelrand
		grid.insets = new Insets(5, 5, 5, 5);
        this.panelLayout.add(panelOrder, grid);
//		grid.insets = new Insets(0, 0, 0, 0);

        //\u00DCberschrift f\u00FCr Ranglistentabelle
		JPanel header = new JPanel();
		header.setLayout(new GridBagLayout());
		grid.weightx = grid.weighty = 0;
        grid.fill = GridBagConstraints.HORIZONTAL;
        this.panelOrder.add(header, grid);
		JLabel labelRangliste = new JLabel("<html><h1>Rangliste</h1></html>");
		grid.gridx = grid.gridy = 0;
		header.add(labelRangliste, grid);

		JPanel contentTable = new JPanel();
		contentTable.setLayout(new GridBagLayout());
		grid.gridy = 1;
		grid.weightx = grid.weighty = 1;
        grid.fill = GridBagConstraints.BOTH;
        this.panelOrder.add(contentTable, grid);

        //Tabelle initialisieren
		initTblOrder(calc.getPlayerMatrix());
		JScrollPane scrollpane = new JScrollPane();
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollpane.getViewport().add(tblOrder);
		grid.gridy = 0;
		contentTable.add(scrollpane, grid);

	}

	/**
	 * Panel f\u00FCr Anzeige von Paarungen vorbereiten.
	 */
	private void initMatches() {

		//Panel f\u00FCr Spieler vorbereiten
		GridBagConstraints grid = new GridBagConstraints();
		this.panelMatches = new JPanel();
		this.panelMatches.setLayout(new GridBagLayout());
		grid.gridx = 1;
		grid.gridy = 0;
		grid.weightx = grid.weighty = 1;
        grid.fill = GridBagConstraints.BOTH;
		grid.insets = new Insets(5, 5, 5, 5);
        this.panelLayout.add(this.panelMatches, grid);
//		grid.insets = new Insets(0, 0, 0, 0);

        //\u00DCberschriften f\u00FCr Spielfelder und Ergebnisse
		JPanel header = new JPanel();
		header.setLayout(new GridBagLayout());
		grid.gridx = 0;
		grid.weighty = 0;
        grid.fill = GridBagConstraints.HORIZONTAL;
        this.panelMatches.add(header, grid);

		JLabel labelResults = new JLabel("<html><h1>Ergebnisse</h1></html>");
		grid.gridx = 0;
		grid.fill = GridBagConstraints.NONE;
		header.add(labelResults, grid);

		JPanel contentMatches = new JPanel();
		contentMatches.setLayout(new GridBagLayout());
		grid.gridy = 1;
		grid.weightx = grid.weighty = 1;
        grid.fill = GridBagConstraints.BOTH;
        this.panelMatches.add(contentMatches, grid);

		JPanel inputArea = new JPanel();
		inputArea.setLayout(new GridBagLayout());
		grid.gridy = 0;
		grid.fill = GridBagConstraints.HORIZONTAL;
		grid.anchor = GridBagConstraints.FIRST_LINE_START;
		contentMatches.add(inputArea, grid);

		JLabel labelTeams = new JLabel("<html><h3>Paarungen</h3></html>");
		grid.gridx = 1;
		grid.gridy = 0;
		grid.gridwidth = 3;
		grid.fill = GridBagConstraints.NONE;
		grid.anchor = GridBagConstraints.CENTER;
		inputArea.add(labelTeams, grid);

		JLabel labelResult = new JLabel("<html><h3>Ergebnis</h3></html>");
		grid.gridx = 4;
		grid.gridwidth = 3;
		inputArea.add(labelResult, grid);

		//Spielfelder und Paarungen zuweisen
		this.labelCourts = new JLabel[this.numberOfCourts];
		this.labelA = new JLabel[this.numberOfCourts];
		this.labelB = new JLabel[this.numberOfCourts];
		this.inputResultA = new JTextField[this.numberOfCourts];
		this.inputResultB = new JTextField[this.numberOfCourts];
		JLabel[] vsT = new JLabel[this.numberOfCourts];
		JLabel[] vsR = new JLabel[this.numberOfCourts];


		int index = 0;
		for (int y = 0; y < this.numberOfCourts; y++) {

			if ((index >= calc.getPlayerList().size()) || ((calc.getPlayerList().size() - index ) == 1)) {
				break;
			} else if ((calc.getPlayerList().size()  - index) >= 4) {
				index += 4;
			} else {
				index += 2;
			}

			//Label f\u00FCr Spielfeld
			int yVal = y + 1;
			this.labelCourts[y] = new JLabel("<html><h4>Feld " + yVal + "</h4></html>");
			grid.gridx = 0;
			grid.gridy = yVal;
			grid.gridwidth = 1;
			grid.anchor = GridBagConstraints.LINE_START;
			inputArea.add(this.labelCourts[y], grid);

			//Lavel f\u00FCr erste Paarung
			this.labelA[y] = new JLabel("");
			grid.gridx = 1;
			grid.gridy = yVal;
			grid.anchor = GridBagConstraints.CENTER;
			inputArea.add(this.labelA[y], grid);

			vsT[y] = new JLabel(":");
			grid.gridx = 2;
			grid.gridy = yVal;
			inputArea.add(vsT[y], grid);

			//Label f\u00FCr zweite Paarung
			this.labelB[y] = new JLabel("");
			grid.gridx = 3;
			grid.gridy = yVal;
			inputArea.add(this.labelB[y], grid);

			//Label f\u00FCr Ergebnis erste Paarung
			this.inputResultA[y] = new JTextField();
			this.inputResultA[y].setPreferredSize( new Dimension( 50, 24 ) );
			grid.gridx = 4;
			grid.gridy = yVal;
			inputArea.add(this.inputResultA[y], grid);

			vsR[y] = new JLabel(":");
			grid.gridx = 5;
			grid.gridy = yVal;
			inputArea.add(vsR[y], grid);

			//Label f\u00FCr Ergebnis zweite Paarung
			this.inputResultB[y] = new JTextField();
			this.inputResultB[y].setPreferredSize( new Dimension( 50, 24 ) );
			grid.gridx = 6;
			grid.gridy = yVal;
			inputArea.add(this.inputResultB[y], grid);
			yVal++;

		}

		JPanel actionArea = new JPanel();
		actionArea.setLayout(new GridBagLayout());
		grid.gridx = 0;
		grid.gridy = 2;
		grid.weighty = 0;
		grid.fill = GridBagConstraints.HORIZONTAL;
        this.panelMatches.add(actionArea, grid);

		this.labelNumberOfRounds = new JLabel("Gespielte Runden: 0");
		grid.gridy = 0;
		actionArea.add(this.labelNumberOfRounds, grid);

		JLabel labelMode = new JLabel("Rundenmodus w\u00E4hlen:");
		grid.gridy = 1;
		actionArea.add(labelMode, grid);

		this.radioTopBottom = new JRadioButton("Oben mit Unten");
		grid.gridy = 2;
		actionArea.add(this.radioTopBottom, grid);

		this.radioTopTop = new JRadioButton("Oben mit Oben");
		grid.gridy = 3;
		actionArea.add(this.radioTopTop, grid);

		this.radioTopBottom.setSelected(true);
		this.radioTopTop.setEnabled(false);

		this.buttonGroupMode = new ButtonGroup();
		this.buttonGroupMode.add(this.radioTopBottom);
		this.buttonGroupMode.add(this.radioTopTop);

		JPanel buttons = new JPanel();
		buttons.setLayout(new GridBagLayout());
		grid.gridy = 4;
		grid.fill = GridBagConstraints.NONE;
		grid.anchor = GridBagConstraints.LINE_END;
        actionArea.add(buttons, grid);

		// this.buttonReset = new JButton("Reset");
		// grid.anchor = GridBagConstraints.CENTER;
		// buttons.add(this.buttonReset, grid);
		// this.buttonReset.addMouseListener(reset);

		this.buttonEndRound = new JButton("Runde beenden");
		grid.gridx = 1;
		buttons.add(this.buttonEndRound, grid);
		this.buttonEndRound.addMouseListener(endRound);

		this.buttonNextRound = new JButton("N\u00E4chste Runde");
		grid.gridx = 2;
		buttons.add(this.buttonNextRound, grid);
		this.buttonNextRound.addMouseListener(nextRound);
		this.buttonNextRound.setEnabled(false);

	}

	/**
	 * Panel f\u00FCr die Eingabe von Spielern
	 */
	private void initPlayers() {

		//Panel vorbereiten
		GridBagConstraints grid = new GridBagConstraints();

		this.panelNames = new JPanel();
		this.panelNames.setLayout(new GridBagLayout());
		grid.gridx = 1;
		grid.weightx = grid.weighty = 1;
        grid.fill = GridBagConstraints.BOTH;
		grid.insets = new Insets(5, 5, 5, 5);
        this.panelLayout.add(this.panelNames, grid);
//		grid.insets = new Insets(0, 0, 0, 0);

        //Header einf\u00FCgen f\u00FCr die Teilnehmeransicht
		JPanel header = new JPanel();
		header.setLayout(new GridBagLayout());
		grid.gridx = 0;
		grid.weightx = grid.weighty = 0;
        grid.fill = GridBagConstraints.HORIZONTAL;
        this.panelNames.add(header, grid);
		JLabel labelAddPlayer = new JLabel("<html><h1>Teilnehmer hinzuf\u00FCgen</h1></html>");
		header.add(labelAddPlayer, grid);

		//Panel zur Spielereingabe
		JPanel contentNames = new JPanel();
		contentNames.setLayout(new GridBagLayout());
		grid.gridy = 1;
		grid.weightx = grid.weighty = 1;
        grid.fill = GridBagConstraints.BOTH;
        this.panelNames.add(contentNames, grid);

        //Panel welches die Felder zur Spielereingabe bereitstellt
		JPanel inputArea = new JPanel();
		inputArea.setLayout(new GridBagLayout());
		grid.gridy = 0;
		grid.fill = GridBagConstraints.NONE;
		grid.anchor = GridBagConstraints.FIRST_LINE_START;
        contentNames.add(inputArea, grid);

        //Labels und Textfelder auf das Panel hinzuf\u00FCgen zur Spielereingabe
		this.labelPlayer = new JLabel[this.numberAddPlayers];
		this.inputPlayer = new JTextField[this.numberAddPlayers];
		for (int y = 0; y < this.numberAddPlayers; y++) {

			this.labelPlayer[y] = new JLabel("<html><h4>Spieler eingeben</h4></html>");
			grid.gridx = 0;
			grid.gridy = y;
			grid.fill = GridBagConstraints.BOTH;
			grid.anchor = GridBagConstraints.CENTER;
			inputArea.add(this.labelPlayer[y], grid);

			this.inputPlayer[y] = new JTextField();
			this.inputPlayer[y].setPreferredSize( new Dimension( 200, 24 ) );
			grid.gridx = 1;
			inputArea.add(this.inputPlayer[y], grid);

		}

		//Panel f\u00FCr Hinweise und Spielsteuerung \u00FCber Buttons
		JPanel actionArea = new JPanel();
		actionArea.setLayout(new GridBagLayout());
		grid.gridx = 0;
		grid.gridy = 2;
		grid.weightx = grid.weighty = 0;
        grid.fill = GridBagConstraints.HORIZONTAL;
        this.panelNames.add(actionArea, grid);

        //Hinweise zur Bedienung
		JPanel notes = new JPanel();
		notes.setLayout(new GridBagLayout());
		notes.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Hinweis"));
		grid.gridy = 0;
		grid.weightx = 1;
        actionArea.add(notes, grid);
		JLabel labelNote = new JLabel("<html><p>Felder k\u00F6nnen beliebig oft bef\u00FCllt werden.<br>Durch hinzuf\u00FCgen werden die Spieler gespeichert<br>und neue k\u00F6nnen eingegeben werden.</p></html>");
		grid.fill = GridBagConstraints.NONE;
		grid.anchor = GridBagConstraints.LINE_START;
		notes.add(labelNote, grid);

		//Anzeigen der Spieleranzahl
		this.labelNumberOfPlayers = new JLabel("Anzahl Spieler: 0");
		grid.gridy = 1;
		actionArea.add(labelNumberOfPlayers, grid);

		//Buttons initiieren
		JPanel buttons = new JPanel();
		buttons.setLayout(new GridBagLayout());
		grid.gridy = 2;
		grid.anchor = GridBagConstraints.LINE_END;
        actionArea.add(buttons, grid);

        //Button zum Hinzuf\u00FCgen von Spielern
		this.buttonAdd = new JButton("Hinzuf\u00FCgen");
		grid.weightx = grid.weighty = 0;
		grid.fill = GridBagConstraints.BOTH;
		grid.anchor = GridBagConstraints.CENTER;
		buttons.add(this.buttonAdd, grid);
		this.buttonAdd.addMouseListener(listenerHinzu);

		//Button um das Turnier zu starten
		this.buttonStart = new JButton("Turnier starten");
		grid.gridx = 1;
		buttons.add(this.buttonStart, grid);
		this.buttonStart.addMouseListener(startGame);

	}

	/**
	 * MOdel f\u00FCr Table festlegen mit den Spielern als Rangliste bzw. Eintragen
	 *
	 * @param entries
	 *            Spieler mit Infos
	 */
	private void initTblOrder(String[][] entries) {
		String colNames[] = { "Platz", "Name", "Punkte", "Differenz" };
		model = new DefaultTableModel(entries, colNames);
		tblOrder = new JTable(model) {

			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tblOrder.setAutoCreateRowSorter(true);
		tblOrder.setRowSelectionAllowed(false);
	}

	/**
	 * Beschreibt die Labels mit den entsprechenden Spielpaarungen
	 */
	private void setPairs() {

		// Labels mit Spielern beschreiben; oben mit unten
		if (radioTopBottom.isSelected()) {
			calc.createPairsTopandBottom();
			// Pr\u00FCft, ob alles Doppelspiele sind; wenn ein einzel dabei ist,
			// wird unten nach dem if im else block weiter gemacht.
			// TODO - feldbef\u00FCllung in Methode auslagern, da f\u00FCr 2. Variante Gut
			// mit Gut auch ben\u00F6tigt wird.

			int singlePlayers = calc.getTempListFirstPlayers().size() % 2;
			int doubleTeams = calc.getTempListFirstPlayers().size() - singlePlayers;
			int index = 0;

			//Pr\u00FCfen ob es Einzelspieler gibt
			int newSinglePlayers = doubleTeams;
			for (int i = 0; i < singlePlayers; i++) {
				newSinglePlayers++;
			}
			singlePlayers = newSinglePlayers;

			//Mit Doppelpaarungen anfagen und die Spielfelder bef\u00FCllen
			int court = 0;
			if (doubleTeams != 0) {
				while (court < this.numberOfCourts) {
					if (index >= doubleTeams) {
						break;
					}
					labelA[court].setText(calc.getTempListFirstPlayers().get(index).getName() + " + " + calc.getTempListSecondPlayers().get(index).getName());
					index++;
					labelB[court].setText(calc.getTempListFirstPlayers().get(index).getName() + " + " + calc.getTempListSecondPlayers().get(index).getName());
					index++;
					court++;
				}
			}
			//Falls es Einzelspieler gibt, Felder mit Einzelspieler f\u00FCllen.
			if (singlePlayers != 0) {
				while (court < this.numberOfCourts) {
					if (index >= singlePlayers) {
						break;
					}
					labelA[court].setText(calc.getTempListFirstPlayers().get(index).getName());
					labelB[court].setText(calc.getTempListSecondPlayers().get(index).getName());
					index++;
					court++;
				}
			}

			// TODO: gut mit gut - siehe todo weiter oben; in Methode auslagern

			// Am Ende die Spieler wieder in die Playerlist schreiben, um dann
			// mit dieser Liste weiterarbeiten zu k\u00F6nnen f\u00FCr Berechnung neuer
			// Statistik.
			for (Player p : calc.getTempListFirstPlayers()) {
				calc.getPlayerList().add(p);
			}

			for (Player p : calc.getTempListSecondPlayers()) {
				calc.getPlayerList().add(p);
			}

			for (Player p : calc.getTempListPausedPlayers()) {
				//Aussetzende Spieler am Ende auch updaten und Punkte f\u00FCr gutschreiben
				p.setPoints(p.getPoints() + pointsVictory);
				calc.getPlayerList().add(p);
			}
		}
	}

	/**
	 * f\u00FCgt Spieler zur Liste hinzu vor dem Spiel.
	 */
	private void addPlayers() {
		// 1. Spieler erstellen und zur Liste hinzuf\u00FCgen

		String[] playerToAdd = new String[4];

//		Eingegebene Spieler durchlaufen
		for (int player = 0; player < numberAddPlayers; player++) {
			if (!inputPlayer[player].getText().isEmpty()) {
				Player playertmp = new Player(inputPlayer[player].getText());
				//Spieler zur Spielerliste hinzuf\u00FCgen
				calc.addPlayer(playertmp);
				//Spieler in die Rangliste hinzuf\u00FCgen
				playerToAdd[0] = String.valueOf(playertmp.getPlatz());
				playerToAdd[1] = playertmp.getName();
				playerToAdd[2] = String.valueOf(playertmp.getPoints());
				playerToAdd[3] = String.valueOf(playertmp.getDifference());
				model.addRow(playerToAdd);
				playerToAdd = new String[4];
			}
			//Reset f\u00FCr n\u00E4chsten Spieler
			inputPlayer[player].setText("");
		}
		//2. Tabelle updaten
		model.fireTableDataChanged();
		model.fireTableStructureChanged();
		this.labelNumberOfPlayers.setText("Anzahl Spieler: " + Integer.toString(calc.getPlayerList().size()));

	}

	/**
	 * Pr\u00FCft, ob in die Ergebnisfelder nur Integer eingegeben werden
	 * @return
	 */
	private Document onlyInteger() {

		Document doc = new PlainDocument() {
			/**
				 *
				 */
			private static final long serialVersionUID = 1L;

			@Override
			public void insertString(int offs, String str, AttributeSet a)
					throws BadLocationException {
				if (str.matches("[0-9]")) {
					super.insertString(offs, str, a);
				} else {
					Toolkit.getDefaultToolkit().beep();
				}
			}

		};

		return doc;

	}

	// Listener

	/**
	 * Listener zum Hinzuf\u00FCgen der Teilnehmer bevor das Turnier beginnt.
	 */
	MouseListener listenerHinzu = new MouseListener() {

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			addPlayers();
		}
	};

	/**
	 * Listener zum Starten des Spiels
	 */
	MouseListener startGame = new MouseListener() {

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			addPlayers();
			initMatches();
			panelNames.setVisible(false);
			panelOrder.setVisible(true);
			panelMatches.setVisible(true);
			radioTopBottom.setSelected(true);
			setPairs();
		}
	};

	MouseListener endRound = new MouseListener() {

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {

			// 0. pr\u00FCfen, ob wirklich beendet werden soll und Eingaben korrekt
			// sind.
			// TODO: pr\u00FCfung wieder enablen, am Ende.
			// int option = JOptionPane.showConfirmDialog(null,
			// "Sind alle Spiele korrekt eingegeben?", "Best\u00E4tigung",
			// JOptionPane.YES_NO_OPTION );
			// if (option == 0) {

			// Table model leeren
			model.setRowCount(0);

			// 1. Statistiken kalkulieren
			String playerNamesA[] = new String[2];
			String playerNamesB[] = new String[2];
			int result = 0;
			int index = 0;

			for (int court = 0; court < numberOfCourts; court++) {

				playerNamesA = new String[2];
				playerNamesB = new String[2];

				//break, sobald alle Player/Courts durchlaufen sind
				if (index >= calc.getPlayerList().size()) {
					break;
				}

				//Falls kein Ergebnis eingetragen ist, muss 0 gesetzt werden, damit die Kalkulation fehlerfrei funktioniert
				if (inputResultA[court].getText().isEmpty()) {
					inputResultA[court].setText("0");
				}
				if (inputResultB[court].getText().isEmpty()) {
					inputResultB[court].setText("0");
				}

				// Paarung A vom Feld nehmen
				if (labelA[court].getText().contains("+")) {
					playerNamesA = labelA[court].getText().split(Pattern.quote(" + "));
				} else {
					playerNamesA[0] = labelA[court].getText();
				}

				//Punkte f\u00FCr Ergebnis berechnen von Spielern A
				if (Integer.parseInt(inputResultA[court].getText()) > Integer.parseInt(inputResultB[court].getText())) {
					result = pointsVictory;
				} else if (Integer.parseInt(inputResultA[court].getText()) < Integer.parseInt(inputResultB[court].getText())) {
					result = pointsLost;
				} else {
					result = pointsDraw;
				}

				//Punkte in die Spielerstatistik von A kalkulieren und schreiben.
				for (String playerName : playerNamesA) {
					if (playerName != null && !playerName.isEmpty()) {
						calc.calculateNewStatistics(playerName, result, Integer.parseInt(inputResultA[court].getText()) - Integer.parseInt(inputResultB[court].getText()));
						index++;
					}
				}

				// Paarung B vom Feld nehmen
				if (labelB[court].getText().contains("+")) {
					playerNamesB = labelB[court].getText().split(Pattern.quote(" + "));
				} else {
					playerNamesB[0] = labelB[court].getText();
				}

				//Punkte f\u00FCr Ergebnis berechnen von Spielern B
				if (Integer.parseInt(inputResultB[court].getText()) > Integer.parseInt(inputResultA[court].getText())) {
					result = pointsVictory;
				} else if (Integer.parseInt(inputResultB[court].getText()) < Integer.parseInt(inputResultA[court].getText())) {
					result = pointsLost;
				} else {
					result = pointsDraw;
				}

				//Punkte in die Spielerstatistik von B kalkulieren und schreiben.
				for (String playerName : playerNamesB) {
					if (playerName != null && !playerName.isEmpty()) {
						calc.calculateNewStatistics(playerName, result, Integer.parseInt(inputResultB[court].getText()) - Integer.parseInt(inputResultA[court].getText()));
						index++;
					}
				}

				log.addLog(calc.getGespielteRunden() + 1, playerNamesA, playerNamesB, Integer.parseInt(inputResultA[court].getText()), Integer.parseInt(inputResultB[court].getText()));

				inputResultA[court].setEditable(false);
				inputResultB[court].setEditable(false);
			}

			calc.sortPlayerList();

			// 3. Model updaten
			String[] playerToAdd = new String[4];
			for (Player p : calc.getPlayerList()) {
				playerToAdd[0] = String.valueOf(p.getPlatz());
				playerToAdd[1] = p.getName();
				playerToAdd[2] = String.valueOf(p.getPoints());
				playerToAdd[3] = String.valueOf(p.getDifference());
				model.addRow(playerToAdd);
				playerToAdd = new String[4];
			}

			// 4. update table/Rangliste
			model.fireTableDataChanged();
			model.fireTableStructureChanged();
			buttonEndRound.setEnabled(false);
			buttonNextRound.setEnabled(true);
			calc.setGespielteRunden(calc.getGespielteRunden() + 1);
			labelNumberOfRounds.setText("Gespielte Runden: " + Integer.toString(calc.getGespielteRunden()));
		}
	};

	MouseListener nextRound = new MouseListener() {

		@Override
		public void mouseReleased(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {

			// 1. Textfelder enablen und leeren
			int index = 0;
			for (int court = 0; court < numberOfCourts; court++) {

				if ((index >= calc.getPlayerList().size()) || ((calc.getPlayerList().size()  - index) == 1)) {
					break;
				} else if ((calc.getPlayerList().size()  - index) >= 4) {
					index += 4;
				} else {
					index += 2;
				}

				inputResultA[court].setText("");
				inputResultA[court].setEditable(true);
				inputResultB[court].setText("");
				inputResultB[court].setEditable(true);
			}

			// 2. neue Runde auslosen
			setPairs();

			buttonEndRound.setEnabled(true);
			buttonNextRound.setEnabled(false);
		}
	};

	/**
	 * l\u00F6scht alle Listen und setzt alles auf 0; Anfangsbildschirm zum eingeben der Spieler wird angezeigt.
	 */
	MouseListener reset = new MouseListener() {
		//TODO: Implement
		@Override
		public void mouseReleased(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {

		}
	};

	/**
	 * Main methode
	 *
	 * @param args
	 *            args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_Schleiferl frame = new GUI_Schleiferl();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
