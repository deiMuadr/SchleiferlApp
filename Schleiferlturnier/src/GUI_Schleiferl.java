/*
 ******************************************************************
 Copyright (c) 2017 Simon Knödler
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
import java.util.*;
import java.util.regex.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.table.*;
import javax.swing.border.*;

public class GUI_Schleiferl extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	// Global Classes
	private Calculations 	calc = new Calculations();

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
	private DefaultTableModel 	modelLike;

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
	 * Panel für Ranglisten Anzeige vorbereiten.
	 */
	private void initOrder() {

		GridBagConstraints grid = new GridBagConstraints();
		this.panelOrder = new JPanel();
		this.panelOrder.setLayout(new GridBagLayout());
		grid.weightx = grid.weighty = 1;
        grid.fill = GridBagConstraints.BOTH;
		grid.insets = new Insets(5, 5, 5, 5);
        this.panelLayout.add(panelOrder, grid);
		grid.insets = new Insets(0, 0, 0, 0);

		JPanel header = new JPanel();
		header.setLayout(new GridBagLayout());
		grid.weightx = grid.weighty = 0;
        grid.fill = GridBagConstraints.HORIZONTAL;
        this.panelOrder.add(header, grid);

		JLabel labelRangliste = new JLabel("<html><h1>Rangliste</h1></html>");
		grid.gridx = grid.gridy = 0;
		header.add(labelRangliste, grid);

		JPanel content = new JPanel();
		content.setLayout(new GridBagLayout());
		grid.gridy = 1;
		grid.weightx = grid.weighty = 1;
        grid.fill = GridBagConstraints.BOTH;
        this.panelOrder.add(content, grid);

		initTblOrder(calc.getPlayerMatrix());
		JScrollPane scrollpane = new JScrollPane();
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollpane.getViewport().add(tblOrder);
		grid.gridy = 0;
		content.add(scrollpane, grid);

	}

	/**
	 * Panel für Anzeige von Paarungen vorbereiten.
	 */
	private void initMatches() {

		GridBagConstraints grid = new GridBagConstraints();
		this.panelMatches = new JPanel();
		this.panelMatches.setLayout(new GridBagLayout());
		grid.gridx = 1;
		grid.gridy = 0;
		grid.weightx = grid.weighty = 1;
        grid.fill = GridBagConstraints.BOTH;
		grid.insets = new Insets(5, 5, 5, 5);
        this.panelLayout.add(this.panelMatches, grid);
		grid.insets = new Insets(0, 0, 0, 0);

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

		JPanel content = new JPanel();
		content.setLayout(new GridBagLayout());
		grid.gridy = 1;
		grid.weightx = grid.weighty = 1;
        grid.fill = GridBagConstraints.BOTH;
        this.panelMatches.add(content, grid);

		JPanel inputArea = new JPanel();
		inputArea.setLayout(new GridBagLayout());
		grid.gridy = 0;
		grid.fill = GridBagConstraints.HORIZONTAL;
		grid.anchor = GridBagConstraints.FIRST_LINE_START;
		content.add(inputArea, grid);

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

		JLabel vs = new JLabel(":");
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

			int yVal = y + 1;
			this.labelCourts[y] = new JLabel("<html><h4>Feld " + yVal + "</h4></html>");
			grid.gridx = 0;
			grid.gridy = yVal;
			grid.gridwidth = 1;
			grid.anchor = GridBagConstraints.LINE_START;
			inputArea.add(this.labelCourts[y], grid);

			this.labelA[y] = new JLabel("");
			grid.gridx = 1;
			grid.gridy = yVal;
			grid.anchor = GridBagConstraints.CENTER;
			inputArea.add(this.labelA[y], grid);

			vsT[y] = new JLabel(":");
			grid.gridx = 2;
			grid.gridy = yVal;
			inputArea.add(vsT[y], grid);

			this.labelB[y] = new JLabel("");
			grid.gridx = 3;
			grid.gridy = yVal;
			inputArea.add(this.labelB[y], grid);

			this.inputResultA[y] = new JTextField();
			this.inputResultA[y].setPreferredSize( new Dimension( 50, 24 ) );
			grid.gridx = 4;
			grid.gridy = yVal;
			inputArea.add(this.inputResultA[y], grid);

			vsR[y] = new JLabel(":");
			grid.gridx = 5;
			grid.gridy = yVal;
			inputArea.add(vsR[y], grid);

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

		JLabel labelMode = new JLabel("Rundenmodus wählen:");
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

		this.buttonNextRound = new JButton("Nächste Runde");
		grid.gridx = 2;
		buttons.add(this.buttonNextRound, grid);
		this.buttonNextRound.addMouseListener(nextRound);
		this.buttonNextRound.setEnabled(false);

	}

	/**
	 * Panel für die Eigabe von Spielern
	 */
	private void initPlayers() {

		GridBagConstraints grid = new GridBagConstraints();

		this.panelNames = new JPanel();
		this.panelNames.setLayout(new GridBagLayout());
		grid.gridx = 1;
		grid.weightx = grid.weighty = 1;
        grid.fill = GridBagConstraints.BOTH;
		grid.insets = new Insets(5, 5, 5, 5);
        this.panelLayout.add(this.panelNames, grid);
		grid.insets = new Insets(0, 0, 0, 0);

		JPanel header = new JPanel();
		header.setLayout(new GridBagLayout());
		grid.gridx = 0;
		grid.weightx = grid.weighty = 0;
        grid.fill = GridBagConstraints.HORIZONTAL;
        this.panelNames.add(header, grid);

		JLabel labelAddPlayer = new JLabel("<html><h1>Teilnehmer hinzufügen</h1></html>");
		header.add(labelAddPlayer, grid);

		JPanel content = new JPanel();
		content.setLayout(new GridBagLayout());
		grid.gridy = 1;
		grid.weightx = grid.weighty = 1;
        grid.fill = GridBagConstraints.BOTH;
        this.panelNames.add(content, grid);

		JPanel inputArea = new JPanel();
		inputArea.setLayout(new GridBagLayout());
		grid.gridy = 0;
		grid.fill = GridBagConstraints.NONE;
		grid.anchor = GridBagConstraints.FIRST_LINE_START;
        content.add(inputArea, grid);

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

		JPanel actionArea = new JPanel();
		actionArea.setLayout(new GridBagLayout());
		grid.gridx = 0;
		grid.gridy = 2;
		grid.weightx = grid.weighty = 0;
        grid.fill = GridBagConstraints.HORIZONTAL;
        this.panelNames.add(actionArea, grid);

		JPanel notes = new JPanel();
		notes.setLayout(new GridBagLayout());
		notes.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Hinweis"));
		grid.gridy = 0;
		grid.weightx = 1;
        actionArea.add(notes, grid);

		JLabel labelNote = new JLabel("<html><p>Felder können beliebig oft befüllt werden.<br>Durch hinzufügen werden die Spieler gespeichert<br>und neue können eingegeben werden.</p></html>");
		grid.fill = GridBagConstraints.NONE;
		grid.anchor = GridBagConstraints.LINE_START;
		notes.add(labelNote, grid);

		this.labelNumberOfPlayers = new JLabel("Anzahl Spieler: 0");
		grid.gridy = 1;
		actionArea.add(labelNumberOfPlayers, grid);

		JPanel buttons = new JPanel();
		buttons.setLayout(new GridBagLayout());
		grid.gridy = 2;
		grid.anchor = GridBagConstraints.LINE_END;
        actionArea.add(buttons, grid);

		this.buttonAdd = new JButton("Hinzufügen");
		grid.weightx = grid.weighty = 0;
		grid.fill = GridBagConstraints.BOTH;
		grid.anchor = GridBagConstraints.CENTER;
		buttons.add(this.buttonAdd, grid);
		this.buttonAdd.addMouseListener(listenerHinzu);

		this.buttonStart = new JButton("Turnier starten");
		grid.gridx = 1;
		buttons.add(this.buttonStart, grid);
		this.buttonStart.addMouseListener(startGame);

	}

	/**
	 * MOdel für Table festlegen mit den Spielern als Rangliste bzw. Eintragen
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

		String namesPair1 = "";
		String namesPair2 = "";

		// Labels mit Spielern beschreiben; gut mit schlecht
		if (radioTopBottom.isSelected()) {
			calc.createPairsGoodandBad();
			// Prüft, ob alles Doppelspiele sind; wenn ein einzel dabei ist,
			// wird unten nach dem if im else block weiter gemacht.
			// TODO - feldbefüllung in Methode auslagern, da für 2. Variante Gut
			// mit Gut auch benötigt wird.

			int singlePlayers = calc.getTempListFirstPlayers().size() % 2;
			int doubleTeams = calc.getTempListFirstPlayers().size() - singlePlayers;
			int index = 0;

			int newSinglePlayers = doubleTeams;
			for (int i = 0; i < singlePlayers; i++) {
				newSinglePlayers++;
			}
			singlePlayers = newSinglePlayers;

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
			// mit dieser Liste weiterarbeiten zu können für Berechnung neuer
			// Statistik.
			for (Player p : calc.getTempListFirstPlayers()) {
				calc.getPlayerList().add(p);
			}
			for (Player p : calc.getTempListPausedPlayers()) {
				//Aussetzende Spieler am ende auch updaten und 3 Punkte gutschreiben
				p.setPoints(p.getPoints() + pointsVictory);
				calc.getPlayerList().add(p);
			}
			for (Player p : calc.getTempListSecondPlayers()) {
				calc.getPlayerList().add(p);
			}
		}
	}

	/**
	 * fügt Spieler zur Liste hinzu vor dem Spiel.
	 */
	private void addPlayers() {
		// 1. Spieler erstellen und zur Liste hinzufügen

		String[] playerToAdd = new String[4];

		for (int player = 0; player < numberAddPlayers; player++) {
			if (!inputPlayer[player].getText().isEmpty()) {
				Player playertmp = new Player(inputPlayer[player].getText());
				calc.addPlayer(playertmp);
				playerToAdd[0] = String.valueOf(playertmp.getPlatz());
				playerToAdd[1] = playertmp.getName();
				playerToAdd[2] = String.valueOf(playertmp.getPoints());
				playerToAdd[3] = String.valueOf(playertmp.getDifference());
				model.addRow(playerToAdd);
				playerToAdd = new String[4];
			}

			inputPlayer[player].setText("");
		}

		model.fireTableDataChanged();
		model.fireTableStructureChanged();
		this.labelNumberOfPlayers.setText("Anzahl Spieler: " + Integer.toString(calc.getPlayerList().size()));

	}

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
	 * Listener zum Hinzufügen der Teilnehmer bevor das Turnier beginnt.
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

			// 0. prüfen, ob wirklich beendet werden soll und Eingaben korrekt
			// sind.
			// TODO: prüfung wieder enablen, am Ende.
			// int option = JOptionPane.showConfirmDialog(null,
			// "Sind alle Spiele korrekt eingegeben?", "Bestätigung",
			// JOptionPane.YES_NO_OPTION );
			// if (option == 0) {

			// Table model leeren
			model.setRowCount(0);

			// 1. Statistiken kalkulieren
			String playerNames[] = new String[2];
			int result = 0;
			int index = 0;

			for (int court = 0; court < numberOfCourts; court++) {

				if (index >= calc.getPlayerList().size()) {
					break;
				}

				if (inputResultA[court].getText().isEmpty()) {
					inputResultA[court].setText("0");
				}

				if (inputResultB[court].getText().isEmpty()) {
					inputResultB[court].setText("0");
				}

				if (labelA[court].getText().contains("+")) {
					playerNames = labelA[court].getText().split(Pattern.quote(" + "));
				} else {
					playerNames[0] = labelA[court].getText();
				}
				if (Integer.parseInt(inputResultA[court].getText()) > Integer.parseInt(inputResultB[court].getText())) {
					result = pointsVictory;
				} else if (Integer.parseInt(inputResultA[court].getText()) < Integer.parseInt(inputResultB[court].getText())) {
					result = pointsLost;
				} else {
					result = pointsDraw;
				}
				for (String playerName : playerNames) {
					if (playerName != null && !playerName.isEmpty()) {
						calc.calculateNewStatistics(playerName, result, Integer.parseInt(inputResultA[court].getText()) - Integer.parseInt(inputResultB[court].getText()));
						index++;
					}
				}
				playerNames = new String[2];

				if (labelB[court].getText().contains("+")) {
					playerNames = labelB[court].getText().split(Pattern.quote(" + "));
				} else {
					playerNames[0] = labelB[court].getText();
				}
				if (Integer.parseInt(inputResultB[court].getText()) > Integer.parseInt(inputResultA[court].getText())) {
					result = pointsVictory;
				} else if (Integer.parseInt(inputResultB[court].getText()) < Integer.parseInt(inputResultA[court].getText())) {
					result = pointsLost;
				} else {
					result = pointsDraw;
				}
				for (String playerName : playerNames) {
					if (playerName != null && !playerName.isEmpty()) {
						calc.calculateNewStatistics(playerName, result, Integer.parseInt(inputResultB[court].getText()) - Integer.parseInt(inputResultA[court].getText()));
						index++;
					}
				}
				playerNames = new String[2];

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
	 * löscht alle Listen und setzt alles auf 0; Anfangsbildschirm zum eingeben der Spieler wird angezeigt.
	 */
	MouseListener reset = new MouseListener() {

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
