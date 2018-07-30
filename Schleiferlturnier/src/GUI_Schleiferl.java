import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

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

public class GUI_Schleiferl extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Variables

	private JPanel content;
	private JPanel PnlOrder;
	private JPanel PnlMatches;
	private JPanel PnlNames;

	private JLabel lblPaarungen;
	private JLabel lblErgebnis;
	private JLabel lblRangliste;
	private JLabel lblPlayers;
	private JLabel lblHinweis;
	private JLabel lblAnzahlSpieler;
	private JLabel lblAnzahlRunden;
	private JLabel lblRundenmodus;

	private JLabel lblPlayerName1;
	private JLabel lblPlayerName2;
	private JLabel lblPlayerName3;
	private JLabel lblPlayerName4;
	private JLabel lblPlayerName5;

	private JTextField tfPlayer1;
	private JTextField tfPlayer2;
	private JTextField tfPlayer3;
	private JTextField tfPlayer4;
	private JTextField tfPlayer5;

	private JLabel lblFeld1;
	private JLabel lblFeld2;
	private JLabel lblFeld3;
	private JLabel lblFeld4;
	private JLabel lblFeld5;
	private JLabel lblFeld6;

	private JTextField tfFeld1T1;
	private JTextField tfFeld1T2;
	private JTextField tfFeld2T1;
	private JTextField tfFeld2T2;
	private JTextField tfFeld3T1;
	private JTextField tfFeld3T2;
	private JTextField tfFeld4T1;
	private JTextField tfFeld4T2;
	private JTextField tfFeld5T1;
	private JTextField tfFeld5T2;
	private JTextField tfFeld6T1;
	private JTextField tfFeld6T2;

	private JLabel lblFeld1Paarung1;
	private JLabel lblFeld1Paarung2;
	private JLabel lblFeld2Paarung1;
	private JLabel lblFeld2Paarung2;
	private JLabel lblFeld3Paarung1;
	private JLabel lblFeld3Paarung2;
	private JLabel lblFeld4Paarung1;
	private JLabel lblFeld4Paarung2;
	private JLabel lblFeld5Paarung1;
	private JLabel lblFeld5Paarung2;
	private JLabel lblFeld6Paarung1;
	private JLabel lblFeld6Paarung2;

	DefaultTableModel model = new DefaultTableModel();
	DefaultTableModel modelLike;
	JTable tblOrder = new JTable(model);

	private JButton btnReset;
	private JButton btnEndRound;
	private JButton btnNextRound;
	private JButton btnHinzu;
	private JButton btnStart;

	private JRadioButton rbtnGutmitGut;
	private JRadioButton rbtnGutMitSchlecht;
	private ButtonGroup btngModus;

	private Calculations calc = new Calculations();
	
	private int pointsVictory = 2;
	private int pointsDraw = 1;
	private int pointsLost = 0;

	/*
	 * Konstruktor
	 */
	public GUI_Schleiferl() {

		/*
		 * GUI bauen
		 */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1400, 800);
		setTitle("Schleiferlturnier");

		content = new JPanel();
		content.setLayout(null);
		setContentPane(content);

		/*
		 * Panels initiieren.
		 */
		this.initOrder();
		this.initPlayers();
		this.initMatches();

		PnlOrder.setVisible(true);
		PnlNames.setVisible(true);
		PnlMatches.setVisible(false);

	}

	/**
	 * Panel für Ranglisten Anzeige vorbereiten.
	 */
	private void initOrder() {

		PnlOrder = new JPanel();
		PnlOrder.setLayout(null);
		PnlOrder.setBounds(10, 10, 570, 650);
		PnlOrder.setBorder(BorderFactory.createLineBorder(Color.blue));
		content.add(PnlOrder);

		lblRangliste = new JLabel("Rangliste");
		lblRangliste.setBounds(250, 20, 100, 30);
		PnlOrder.add(lblRangliste);

		// Scrollpane und Tabelle einfügen

		initTblOrder(calc.getPlayerMatrix());

		JScrollPane scrollpane = new JScrollPane();

		scrollpane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		tblOrder.setBorder(BorderFactory.createLineBorder(Color.green));
		scrollpane.getViewport().add(tblOrder);
		scrollpane.setPreferredSize(new Dimension(500, 580));

		Border inside = new EmptyBorder(5, 10, 10, 10);
		Border margin = new EmptyBorder(50, 15, 15, 15);
		scrollpane.setBorder(new CompoundBorder(margin, inside));

		PnlOrder.setLayout(new BorderLayout(20, 0));
		PnlOrder.add(scrollpane, BorderLayout.CENTER);

	}

	/**
	 * Panel für Anzeige von Paarungen vorbereiten.
	 */
	private void initMatches() {

		PnlMatches = new JPanel();
		PnlMatches.setLayout(null);
		PnlMatches.setBounds(600, 10, 570, 650);
		PnlMatches.setBorder(BorderFactory.createLineBorder(Color.red));
		content.add(PnlMatches);

		lblPaarungen = new JLabel("Paarungen");
		lblPaarungen.setBounds(250, 20, 100, 30);
		PnlMatches.add(lblPaarungen);

		lblErgebnis = new JLabel("Ergebnis");
		lblErgebnis.setBounds(415, 50, 100, 30);
		PnlMatches.add(lblErgebnis);

		// Feld 1
		lblFeld1 = new JLabel("Feld 1");
		lblFeld1.setBounds(10, 90, 100, 30);
		PnlMatches.add(lblFeld1);

		lblFeld1Paarung1 = new JLabel("");
		lblFeld1Paarung1.setBounds(50, 120, 180, 30);
		PnlMatches.add(lblFeld1Paarung1);

		lblFeld1Paarung2 = new JLabel("");
		lblFeld1Paarung2.setBounds(210, 120, 180, 30);
		PnlMatches.add(lblFeld1Paarung2);

		tfFeld1T1 = new JTextField();
		tfFeld1T1.setBounds(380, 120, 50, 30);
		tfFeld1T1.setDocument(onlyInteger());
		PnlMatches.add(tfFeld1T1);

		tfFeld1T2 = new JTextField();
		tfFeld1T2.setBounds(450, 120, 50, 30);
		tfFeld1T2.setDocument(onlyInteger());
		PnlMatches.add(tfFeld1T2);

		// Feld 2
		lblFeld2 = new JLabel("Feld 2");
		lblFeld2.setBounds(10, 160, 100, 30);
		PnlMatches.add(lblFeld2);

		lblFeld2Paarung1 = new JLabel("");
		lblFeld2Paarung1.setBounds(50, 190, 180, 30);
		PnlMatches.add(lblFeld2Paarung1);

		lblFeld2Paarung2 = new JLabel("");
		lblFeld2Paarung2.setBounds(210, 190, 180, 30);
		PnlMatches.add(lblFeld2Paarung2);

		tfFeld2T1 = new JTextField();
		tfFeld2T1.setBounds(380, 190, 50, 30);
		tfFeld2T1.setDocument(onlyInteger());
		PnlMatches.add(tfFeld2T1);

		tfFeld2T2 = new JTextField();
		tfFeld2T2.setBounds(450, 190, 50, 30);
		tfFeld2T2.setDocument(onlyInteger());
		PnlMatches.add(tfFeld2T2);

		// Feld 3
		lblFeld3 = new JLabel("Feld 3");
		lblFeld3.setBounds(10, 230, 100, 30);
		PnlMatches.add(lblFeld3);

		lblFeld3Paarung1 = new JLabel("");
		lblFeld3Paarung1.setBounds(50, 260, 180, 30);
		PnlMatches.add(lblFeld3Paarung1);

		lblFeld3Paarung2 = new JLabel("");
		lblFeld3Paarung2.setBounds(210, 260, 180, 30);
		PnlMatches.add(lblFeld3Paarung2);

		tfFeld3T1 = new JTextField();
		tfFeld3T1.setBounds(380, 260, 50, 30);
		tfFeld3T1.setDocument(onlyInteger());
		PnlMatches.add(tfFeld3T1);

		tfFeld3T2 = new JTextField();
		tfFeld3T2.setBounds(450, 260, 50, 30);
		tfFeld3T2.setDocument(onlyInteger());
		PnlMatches.add(tfFeld3T2);

		// Feld 4
		lblFeld4 = new JLabel("Feld 4");
		lblFeld4.setBounds(10, 300, 100, 30);
		PnlMatches.add(lblFeld4);

		lblFeld4Paarung1 = new JLabel("");
		lblFeld4Paarung1.setBounds(50, 330, 180, 30);
		PnlMatches.add(lblFeld4Paarung1);

		lblFeld4Paarung2 = new JLabel("");
		lblFeld4Paarung2.setBounds(210, 330, 180, 30);
		PnlMatches.add(lblFeld4Paarung2);

		tfFeld4T1 = new JTextField();
		tfFeld4T1.setBounds(380, 330, 50, 30);
		tfFeld4T1.setDocument(onlyInteger());
		PnlMatches.add(tfFeld4T1);

		tfFeld4T2 = new JTextField();
		tfFeld4T2.setBounds(450, 330, 50, 30);
		tfFeld4T2.setDocument(onlyInteger());
		PnlMatches.add(tfFeld4T2);

		// Feld 5
		lblFeld5 = new JLabel("Feld 5");
		lblFeld5.setBounds(10, 370, 100, 30);
		PnlMatches.add(lblFeld5);

		lblFeld5Paarung1 = new JLabel("");
		lblFeld5Paarung1.setBounds(50, 400, 180, 30);
		PnlMatches.add(lblFeld5Paarung1);

		lblFeld5Paarung2 = new JLabel("");
		lblFeld5Paarung2.setBounds(210, 400, 180, 30);
		PnlMatches.add(lblFeld5Paarung2);

		tfFeld5T1 = new JTextField();
		tfFeld5T1.setBounds(380, 400, 50, 30);
		tfFeld5T1.setDocument(onlyInteger());
		PnlMatches.add(tfFeld5T1);

		tfFeld5T2 = new JTextField();
		tfFeld5T2.setBounds(450, 400, 50, 30);
		tfFeld5T2.setDocument(onlyInteger());
		PnlMatches.add(tfFeld5T2);

		// Feld 6
		lblFeld6 = new JLabel("Feld 6");
		lblFeld6.setBounds(10, 440, 100, 30);
		PnlMatches.add(lblFeld6);

		lblFeld6Paarung1 = new JLabel("");
		lblFeld6Paarung1.setBounds(50, 470, 180, 30);
		PnlMatches.add(lblFeld6Paarung1);

		lblFeld6Paarung2 = new JLabel("");
		lblFeld6Paarung2.setBounds(210, 470, 180, 30);
		PnlMatches.add(lblFeld6Paarung2);

		tfFeld6T1 = new JTextField();
		tfFeld6T1.setBounds(380, 470, 50, 30);
		tfFeld6T1.setDocument(onlyInteger());
		PnlMatches.add(tfFeld6T1);

		tfFeld6T2 = new JTextField();
		tfFeld6T2.setBounds(450, 470, 50, 30);
		tfFeld6T2.setDocument(onlyInteger());
		PnlMatches.add(tfFeld6T2);

		// Lbl Anzahl Runden und Spielmodus

		lblAnzahlRunden = new JLabel("Gespielte Runden: 0");
		lblAnzahlRunden.setBounds(380, 530, 150, 30);
		PnlMatches.add(lblAnzahlRunden);

		lblRundenmodus = new JLabel("Rundenmodus wählen:");
		lblRundenmodus.setBounds(380, 560, 150, 30);
		PnlMatches.add(lblRundenmodus);

		// Radiobuttons und Buttongroup setzen.

		rbtnGutmitGut = new JRadioButton("Oben mit Oben");
		rbtnGutmitGut.setBounds(380, 580, 150, 30);
		PnlMatches.add(rbtnGutmitGut);

		rbtnGutMitSchlecht = new JRadioButton("Oben mit Unten");
		rbtnGutMitSchlecht.setBounds(380, 600, 150, 30);
		PnlMatches.add(rbtnGutMitSchlecht);

		rbtnGutMitSchlecht.setSelected(true);

		btngModus = new ButtonGroup();
		btngModus.add(rbtnGutMitSchlecht);
		btngModus.add(rbtnGutmitGut);
	}

	/**
	 * Panel für die Eigabe von Spielern
	 */
	private void initPlayers() {
		PnlNames = new JPanel();
		PnlNames.setLayout(null);
		PnlNames.setBounds(600, 10, 570, 650);
		PnlNames.setBorder(BorderFactory.createLineBorder(Color.magenta));
		content.add(PnlNames);

		lblPlayers = new JLabel("Teilnehmer hinzufügen");
		lblPlayers.setBounds(250, 20, 150, 30);
		PnlNames.add(lblPlayers);

		// Spieler Eingabefelder
		// Spieler 1
		lblPlayerName1 = new JLabel("Spieler eingeben");
		lblPlayerName1.setBounds(20, 50, 100, 30);
		PnlNames.add(lblPlayerName1);

		tfPlayer1 = new JTextField();
		tfPlayer1.setBounds(150, 50, 150, 30);
		PnlNames.add(tfPlayer1);

		// Spieler 2
		lblPlayerName2 = new JLabel("Spieler eingeben");
		lblPlayerName2.setBounds(20, 100, 100, 30);
		PnlNames.add(lblPlayerName2);

		tfPlayer2 = new JTextField();
		tfPlayer2.setBounds(150, 100, 150, 30);
		PnlNames.add(tfPlayer2);

		// Spieler 3
		lblPlayerName3 = new JLabel("Spieler eingeben");
		lblPlayerName3.setBounds(20, 150, 100, 30);
		PnlNames.add(lblPlayerName3);

		tfPlayer3 = new JTextField();
		tfPlayer3.setBounds(150, 150, 150, 30);
		PnlNames.add(tfPlayer3);

		// Spieler 4
		lblPlayerName4 = new JLabel("Spieler eingeben");
		lblPlayerName4.setBounds(20, 200, 100, 30);
		PnlNames.add(lblPlayerName4);

		tfPlayer4 = new JTextField();
		tfPlayer4.setBounds(150, 200, 150, 30);
		PnlNames.add(tfPlayer4);

		// Spieler 5
		lblPlayerName5 = new JLabel("Spieler eingeben");
		lblPlayerName5.setBounds(20, 250, 100, 30);
		PnlNames.add(lblPlayerName5);

		tfPlayer5 = new JTextField();
		tfPlayer5.setBounds(150, 250, 150, 30);
		PnlNames.add(tfPlayer5);

		btnHinzu = new JButton("Hinzufügen");
		btnHinzu.setBounds(70, 320, 100, 30);
		PnlNames.add(btnHinzu);

		btnStart = new JButton("Turnier starten");
		btnStart.setBounds(200, 320, 150, 30);
		PnlNames.add(btnStart);

		lblHinweis = new JLabel(
				"<html>Felder können beliebig oft befüllt werden. Durch hinzufügen werden die Spieler gespeichert und <br> neue können eingegeben werden.</html>");
		lblHinweis.setBounds(10, 380, 550, 30);
		PnlNames.add(lblHinweis);

		lblAnzahlSpieler = new JLabel("Anzahl Spieler: 0");
		lblAnzahlSpieler.setBounds(10, 420, 150, 30);
		PnlNames.add(lblAnzahlSpieler);

		// Listener hinzufügen.
		btnHinzu.addMouseListener(listenerHinzu);
		btnStart.addMouseListener(startGame);
	}

	/**
	 * Buttons für Spielfluss vorbereiten.
	 */
	private void initButtons() {
		btnReset = new JButton("Reset");
//		btnReset.setBounds(250, 700, 100, 30);
		btnReset.setBounds(1200, 480, 150, 30);
		content.add(btnReset);

		btnEndRound = new JButton("Runde beenden");
//		btnEndRound.setBounds(730, 700, 150, 30);
		btnEndRound.setBounds(1200, 130, 150, 30);
		content.add(btnEndRound);
		btnEndRound.setEnabled(false);

		btnNextRound = new JButton("Nächste Runde");
//		btnNextRound.setBounds(910, 700, 150, 30);
		btnNextRound.setBounds(1200, 180, 150, 30);
		content.add(btnNextRound);
		btnNextRound.setEnabled(false);

	}

	/**
	 * MOdel für Table festlegen mit den Spielern als Rangliste bzw. Einträgen
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
		tblOrder.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	/**
	 * Beschreibt die Labels mit den entsprechenden Spielpaarungen
	 */
	private void setPairs() {

		String namesPair1 = "";
		String namesPair2 = "";

		// Labels mit Spielern beschreiben; gut mit schlecht
		if (rbtnGutMitSchlecht.isSelected()) {
			calc.createPairsGoodandBad();
			// Prüft, ob alles Doppelspiele sind; wenn ein einzel dabei ist,
			// wird unten nach dem if im else block weiter gemacht.
			// TODO - feldbefüllung in Methode auslagern, da für 2. Variante Gut
			// mit Gut auch benötigt wird.
			if (calc.getTempListFirstPlayers().size() % 2 == 0) {

				switch (calc.getTempListFirstPlayers().size()) {
				case 2:
					namesPair1 = calc.getTempListFirstPlayers().get(0)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(0).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(1)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(1).getName();
					lblFeld1Paarung1.setText(namesPair1);
					lblFeld1Paarung2.setText(namesPair2);
					lblFeld2Paarung1.setText("");
					tfFeld2T1.setEditable(false);
					lblFeld2Paarung2.setText("");
					tfFeld2T2.setEditable(false);
					lblFeld3Paarung1.setText("");
					tfFeld3T1.setEditable(false);
					lblFeld3Paarung2.setText("");
					tfFeld3T2.setEditable(false);
					lblFeld4Paarung1.setText("");
					tfFeld4T1.setEditable(false);
					lblFeld4Paarung2.setText("");
					tfFeld4T2.setEditable(false);
					lblFeld5Paarung1.setText("");
					tfFeld5T1.setEditable(false);
					lblFeld5Paarung2.setText("");
					tfFeld5T2.setEditable(false);
					lblFeld6Paarung1.setText("");
					tfFeld6T1.setEditable(false);
					lblFeld6Paarung2.setText("");
					tfFeld6T2.setEditable(false);
					break;

				case 4:
					// Feld 1 setzen (4 Spieler)
					namesPair1 = calc.getTempListFirstPlayers().get(0)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(0).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(1)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(1).getName();
					lblFeld1Paarung1.setText(namesPair1);
					lblFeld1Paarung2.setText(namesPair2);

					// Feld 2 setzen (8 Spieler)
					namesPair1 = calc.getTempListFirstPlayers().get(2)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(2).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(3)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(3).getName();
					lblFeld2Paarung1.setText(namesPair1);
					lblFeld2Paarung2.setText(namesPair2);

					lblFeld3Paarung1.setText("");
					tfFeld3T1.setEditable(false);
					lblFeld3Paarung2.setText("");
					tfFeld3T2.setEditable(false);
					lblFeld4Paarung1.setText("");
					tfFeld4T1.setEditable(false);
					lblFeld4Paarung2.setText("");
					tfFeld4T2.setEditable(false);
					lblFeld5Paarung1.setText("");
					tfFeld5T1.setEditable(false);
					lblFeld5Paarung2.setText("");
					tfFeld5T2.setEditable(false);
					lblFeld6Paarung1.setText("");
					tfFeld6T1.setEditable(false);
					lblFeld6Paarung2.setText("");
					tfFeld6T2.setEditable(false);
					break;
				case 6:
					// Feld 1 setzen (4 Spieler)
					namesPair1 = calc.getTempListFirstPlayers().get(0)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(0).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(1)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(1).getName();
					lblFeld1Paarung1.setText(namesPair1);
					lblFeld1Paarung2.setText(namesPair2);
					// Feld 2 setzen (8 Spieler)
					namesPair1 = calc.getTempListFirstPlayers().get(2)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(2).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(3)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(3).getName();
					lblFeld2Paarung1.setText(namesPair1);
					lblFeld2Paarung2.setText(namesPair2);
					// Feld 3 setzen (12 Spieler)
					namesPair1 = calc.getTempListFirstPlayers().get(4)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(4).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(5)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(5).getName();
					lblFeld3Paarung1.setText(namesPair1);
					lblFeld3Paarung2.setText(namesPair2);
					lblFeld4Paarung1.setText("");
					tfFeld4T1.setEditable(false);
					lblFeld4Paarung2.setText("");
					tfFeld4T2.setEditable(false);
					lblFeld5Paarung1.setText("");
					tfFeld5T1.setEditable(false);
					lblFeld5Paarung2.setText("");
					tfFeld5T2.setEditable(false);
					lblFeld6Paarung1.setText("");
					tfFeld6T1.setEditable(false);
					lblFeld6Paarung2.setText("");
					tfFeld6T2.setEditable(false);
					break;
				case 8:
					// Feld 1 setzen (4 Spieler)
					namesPair1 = calc.getTempListFirstPlayers().get(0)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(0).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(1)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(1).getName();
					lblFeld1Paarung1.setText(namesPair1);
					lblFeld1Paarung2.setText(namesPair2);
					// Feld 2 setzen (8 Spieler)
					namesPair1 = calc.getTempListFirstPlayers().get(2)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(2).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(3)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(3).getName();
					lblFeld2Paarung1.setText(namesPair1);
					lblFeld2Paarung2.setText(namesPair2);
					// Feld 3 setzen (12 Spieler)
					namesPair1 = calc.getTempListFirstPlayers().get(4)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(4).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(5)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(5).getName();
					lblFeld3Paarung1.setText(namesPair1);
					lblFeld3Paarung2.setText(namesPair2);
					// Feld 4 setzen (16 Spieler)
					namesPair1 = calc.getTempListFirstPlayers().get(6)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(6).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(7)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(7).getName();
					lblFeld4Paarung1.setText(namesPair1);
					lblFeld4Paarung2.setText(namesPair2);
					lblFeld5Paarung1.setText("");
					tfFeld5T1.setEditable(false);
					lblFeld5Paarung2.setText("");
					tfFeld5T2.setEditable(false);
					lblFeld6Paarung1.setText("");
					tfFeld6T1.setEditable(false);
					lblFeld6Paarung2.setText("");
					tfFeld6T2.setEditable(false);
					break;
				case 10:
					// Feld 1 setzen (4 Spieler)
					namesPair1 = calc.getTempListFirstPlayers().get(0)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(0).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(1)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(1).getName();
					lblFeld1Paarung1.setText(namesPair1);
					lblFeld1Paarung2.setText(namesPair2);
					// Feld 2 setzen (8 Spieler)
					namesPair1 = calc.getTempListFirstPlayers().get(2)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(2).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(3)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(3).getName();
					lblFeld2Paarung1.setText(namesPair1);
					lblFeld2Paarung2.setText(namesPair2);
					// Feld 3 setzen (12 Spieler)
					namesPair1 = calc.getTempListFirstPlayers().get(4)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(4).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(5)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(5).getName();
					lblFeld3Paarung1.setText(namesPair1);
					lblFeld3Paarung2.setText(namesPair2);
					// Feld 4 setzen (16 Spieler)
					namesPair1 = calc.getTempListFirstPlayers().get(6)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(6).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(7)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(7).getName();
					lblFeld4Paarung1.setText(namesPair1);
					lblFeld4Paarung2.setText(namesPair2);
					// Feld 5 setzen (20 Spieler)
					namesPair1 = calc.getTempListFirstPlayers().get(8)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(8).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(9)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(9).getName();
					lblFeld5Paarung1.setText(namesPair1);
					lblFeld5Paarung2.setText(namesPair2);
					lblFeld6Paarung1.setText("");
					tfFeld6T1.setEditable(false);
					lblFeld6Paarung2.setText("");
					tfFeld6T2.setEditable(false);
					break;
				case 12:
					// Feld 1 setzen (4 Spieler)
					namesPair1 = calc.getTempListFirstPlayers().get(0)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(0).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(1)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(1).getName();
					lblFeld1Paarung1.setText(namesPair1);
					lblFeld1Paarung2.setText(namesPair2);
					// Feld 2 setzen (8 Spieler)
					namesPair1 = calc.getTempListFirstPlayers().get(2)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(2).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(3)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(3).getName();
					lblFeld2Paarung1.setText(namesPair1);
					lblFeld2Paarung2.setText(namesPair2);
					// Feld 3 setzen (12 Spieler)
					namesPair1 = calc.getTempListFirstPlayers().get(4)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(4).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(5)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(5).getName();
					lblFeld3Paarung1.setText(namesPair1);
					lblFeld3Paarung2.setText(namesPair2);
					// Feld 4 setzen (16 Spieler)
					namesPair1 = calc.getTempListFirstPlayers().get(6)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(6).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(7)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(7).getName();
					lblFeld4Paarung1.setText(namesPair1);
					lblFeld4Paarung2.setText(namesPair2);
					// Feld 5 setzen (20 Spieler)
					namesPair1 = calc.getTempListFirstPlayers().get(8)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(8).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(9)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(9).getName();
					lblFeld5Paarung1.setText(namesPair1);
					lblFeld5Paarung2.setText(namesPair2);
					// Feld 6 setzen (24 Spieler)
					namesPair1 = calc.getTempListFirstPlayers().get(10)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(10).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(11)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(11).getName();
					lblFeld6Paarung1.setText(namesPair1);
					lblFeld6Paarung2.setText(namesPair2);
					break;
				}

			} else {

				switch (calc.getTempListFirstPlayers().size()) {
				case 1:
					// 1 Eintelpaarung
					lblFeld1Paarung1.setText(calc.getTempListFirstPlayers()
							.get(0).getName());
					lblFeld1Paarung2.setText(calc.getTempListSecondPlayers()
							.get(0).getName());
					lblFeld2Paarung1.setText("");
					tfFeld2T1.setEditable(false);
					lblFeld2Paarung2.setText("");
					tfFeld2T2.setEditable(false);
					lblFeld3Paarung1.setText("");
					tfFeld3T1.setEditable(false);
					lblFeld3Paarung2.setText("");
					tfFeld3T2.setEditable(false);
					lblFeld4Paarung1.setText("");
					tfFeld4T1.setEditable(false);
					lblFeld4Paarung2.setText("");
					tfFeld4T2.setEditable(false);
					lblFeld5Paarung1.setText("");
					tfFeld5T1.setEditable(false);
					lblFeld5Paarung2.setText("");
					tfFeld5T2.setEditable(false);
					lblFeld6Paarung1.setText("");
					tfFeld6T1.setEditable(false);
					lblFeld6Paarung2.setText("");
					tfFeld6T2.setEditable(false);
					break;
				case 3:
					// 1 Doppel und 1 Einzel
					namesPair1 = calc.getTempListFirstPlayers().get(0)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(0).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(1)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(1).getName();
					lblFeld1Paarung1.setText(namesPair1);
					lblFeld1Paarung2.setText(namesPair2);
					lblFeld2Paarung1.setText(calc.getTempListFirstPlayers()
							.get(2).getName());
					lblFeld2Paarung2.setText(calc.getTempListSecondPlayers()
							.get(2).getName());
					lblFeld3Paarung1.setText("");
					tfFeld3T1.setEditable(false);
					lblFeld3Paarung2.setText("");
					tfFeld3T2.setEditable(false);
					lblFeld4Paarung1.setText("");
					tfFeld4T1.setEditable(false);
					lblFeld4Paarung2.setText("");
					tfFeld4T2.setEditable(false);
					lblFeld5Paarung1.setText("");
					tfFeld5T1.setEditable(false);
					lblFeld5Paarung2.setText("");
					tfFeld5T2.setEditable(false);
					lblFeld6Paarung1.setText("");
					tfFeld6T1.setEditable(false);
					lblFeld6Paarung2.setText("");
					tfFeld6T2.setEditable(false);
					break;
				case 5:
					// 2 Doppel und 1 Einzel
					namesPair1 = calc.getTempListFirstPlayers().get(0)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(0).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(1)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(1).getName();
					lblFeld1Paarung1.setText(namesPair1);
					lblFeld1Paarung2.setText(namesPair2);
					namesPair1 = calc.getTempListFirstPlayers().get(2)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(2).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(3)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(3).getName();
					lblFeld2Paarung1.setText(namesPair1);
					lblFeld2Paarung2.setText(namesPair2);
					lblFeld3Paarung1.setText(calc.getTempListFirstPlayers()
							.get(4).getName());
					lblFeld3Paarung2.setText(calc.getTempListSecondPlayers()
							.get(4).getName());
					lblFeld4Paarung1.setText("");
					tfFeld4T1.setEditable(false);
					lblFeld4Paarung2.setText("");
					tfFeld4T2.setEditable(false);
					lblFeld5Paarung1.setText("");
					tfFeld5T1.setEditable(false);
					lblFeld5Paarung2.setText("");
					tfFeld5T2.setEditable(false);
					lblFeld6Paarung1.setText("");
					tfFeld6T1.setEditable(false);
					lblFeld6Paarung2.setText("");
					tfFeld6T2.setEditable(false);
					break;
				case 7:
					// 3 Doppel und 1 Einzel
					namesPair1 = calc.getTempListFirstPlayers().get(0)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(0).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(1)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(1).getName();
					lblFeld1Paarung1.setText(namesPair1);
					lblFeld1Paarung2.setText(namesPair2);
					namesPair1 = calc.getTempListFirstPlayers().get(2)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(2).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(3)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(3).getName();
					lblFeld2Paarung1.setText(namesPair1);
					lblFeld2Paarung2.setText(namesPair2);
					namesPair1 = calc.getTempListFirstPlayers().get(4)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(4).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(5)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(5).getName();
					lblFeld3Paarung1.setText(namesPair1);
					lblFeld3Paarung2.setText(namesPair2);
					lblFeld4Paarung1.setText(calc.getTempListFirstPlayers()
							.get(6).getName());
					lblFeld4Paarung2.setText(calc.getTempListSecondPlayers()
							.get(6).getName());
					lblFeld5Paarung1.setText("");
					tfFeld5T1.setEditable(false);
					lblFeld5Paarung2.setText("");
					tfFeld5T2.setEditable(false);
					lblFeld6Paarung1.setText("");
					tfFeld6T1.setEditable(false);
					lblFeld6Paarung2.setText("");
					tfFeld6T2.setEditable(false);
					break;
				case 9:
					// 4 Doppel und 1 Einzel
					namesPair1 = calc.getTempListFirstPlayers().get(0)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(0).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(1)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(1).getName();
					lblFeld1Paarung1.setText(namesPair1);
					lblFeld1Paarung2.setText(namesPair2);
					namesPair1 = calc.getTempListFirstPlayers().get(2)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(2).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(3)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(3).getName();
					lblFeld2Paarung1.setText(namesPair1);
					lblFeld2Paarung2.setText(namesPair2);
					namesPair1 = calc.getTempListFirstPlayers().get(4)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(4).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(5)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(5).getName();
					lblFeld3Paarung1.setText(namesPair1);
					lblFeld3Paarung2.setText(namesPair2);
					namesPair1 = calc.getTempListFirstPlayers().get(6)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(6).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(7)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(7).getName();
					lblFeld4Paarung1.setText(namesPair1);
					lblFeld4Paarung2.setText(namesPair2);
					lblFeld5Paarung1.setText(calc.getTempListFirstPlayers()
							.get(8).getName());
					lblFeld5Paarung2.setText(calc.getTempListSecondPlayers()
							.get(8).getName());
					lblFeld6Paarung1.setText("");
					tfFeld6T1.setEditable(false);
					lblFeld6Paarung2.setText("");
					tfFeld6T2.setEditable(false);
					break;
				case 11:
					// 5 Doppel und 1 Einzel
					namesPair1 = calc.getTempListFirstPlayers().get(0)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(0).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(1)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(1).getName();
					lblFeld1Paarung1.setText(namesPair1);
					lblFeld1Paarung2.setText(namesPair2);
					namesPair1 = calc.getTempListFirstPlayers().get(2)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(2).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(3)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(3).getName();
					lblFeld2Paarung1.setText(namesPair1);
					lblFeld2Paarung2.setText(namesPair2);
					namesPair1 = calc.getTempListFirstPlayers().get(4)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(4).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(5)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(5).getName();
					lblFeld3Paarung1.setText(namesPair1);
					lblFeld3Paarung2.setText(namesPair2);
					namesPair1 = calc.getTempListFirstPlayers().get(6)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(6).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(7)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(7).getName();
					lblFeld4Paarung1.setText(namesPair1);
					lblFeld4Paarung2.setText(namesPair2);
					namesPair1 = calc.getTempListFirstPlayers().get(8)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(8).getName();
					namesPair2 = calc.getTempListFirstPlayers().get(9)
							.getName()
							+ " + "
							+ calc.getTempListSecondPlayers().get(9).getName();
					lblFeld5Paarung1.setText(namesPair1);
					lblFeld5Paarung2.setText(namesPair2);
					lblFeld6Paarung1.setText(calc.getTempListFirstPlayers()
							.get(10).getName());
					lblFeld6Paarung2.setText(calc.getTempListSecondPlayers()
							.get(10).getName());
					break;
				}

			} // TODO: gut mit gut - siehe todo weiter oben; in Methode auslagern

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

		Player playertmp = new Player();
		String[] playerToAdd = new String[4];

		String player1name = tfPlayer1.getText();
		if (!player1name.isEmpty()) {
			playertmp = new Player(player1name);
			calc.addPlayer(playertmp);

			playerToAdd[0] = String.valueOf(playertmp.getPlatz());
			playerToAdd[1] = playertmp.getName();
			playerToAdd[2] = String.valueOf(playertmp.getPoints());
			playerToAdd[3] = String.valueOf(playertmp.getDifference());

			model.addRow(playerToAdd);

			playerToAdd = new String[playerToAdd.length];
		}

		String player2name = tfPlayer2.getText();
		if (!player2name.isEmpty()) {
			playertmp = new Player(player2name);
			calc.addPlayer(playertmp);

			playerToAdd[0] = String.valueOf(playertmp.getPlatz());
			playerToAdd[1] = playertmp.getName();
			playerToAdd[2] = String.valueOf(playertmp.getPoints());
			playerToAdd[3] = String.valueOf(playertmp.getDifference());

			model.addRow(playerToAdd);

			playerToAdd = new String[playerToAdd.length];
		}

		String player3name = tfPlayer3.getText();
		if (!player3name.isEmpty()) {
			playertmp = new Player(player3name);
			calc.addPlayer(playertmp);

			playerToAdd[0] = String.valueOf(playertmp.getPlatz());
			playerToAdd[1] = playertmp.getName();
			playerToAdd[2] = String.valueOf(playertmp.getPoints());
			playerToAdd[3] = String.valueOf(playertmp.getDifference());

			model.addRow(playerToAdd);

			playerToAdd = new String[playerToAdd.length];
		}

		String player4name = tfPlayer4.getText();
		if (!player4name.isEmpty()) {
			playertmp = new Player(player4name);
			calc.addPlayer(playertmp);

			playerToAdd[0] = String.valueOf(playertmp.getPlatz());
			playerToAdd[1] = playertmp.getName();
			playerToAdd[2] = String.valueOf(playertmp.getPoints());
			playerToAdd[3] = String.valueOf(playertmp.getDifference());

			model.addRow(playerToAdd);

			playerToAdd = new String[playerToAdd.length];
		}
		String player5name = tfPlayer5.getText();
		if (!player5name.isEmpty()) {
			playertmp = new Player(player5name);
			calc.addPlayer(playertmp);

			playerToAdd[0] = String.valueOf(playertmp.getPlatz());
			playerToAdd[1] = playertmp.getName();
			playerToAdd[2] = String.valueOf(playertmp.getPoints());
			playerToAdd[3] = String.valueOf(playertmp.getDifference());

			model.addRow(playerToAdd);

			playerToAdd = new String[playerToAdd.length];
		}

		// 2. Tabelle befüllen und aktualisieren
		model.fireTableDataChanged();
		model.fireTableStructureChanged();

		// 3. Felder leeren für neue eingabe

		tfPlayer1.setText("");
		tfPlayer2.setText("");
		tfPlayer3.setText("");
		tfPlayer4.setText("");
		tfPlayer5.setText("");

		// 4. Label "AnzahlSpieler updaten

		int Anzahl = calc.getPlayerList().size();
		String text = "Anzahl Spieler: " + Anzahl;
		lblAnzahlSpieler.setText(text);

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
			// 0. noch übrige Spieler hinzufügen
			addPlayers();
			// 1. Init Names Panel disablen
			PnlNames.setVisible(false);
			// content.remove(PnlNames);
			// 2. Ranglisten Panel anzeigen
			PnlOrder.setVisible(true);
			// 3. Spielbuttons einblenden
			initButtons();
			// 4. Paarungs Panel einblenden.
			PnlMatches.setVisible(true);
			content.updateUI();
			// 5. Anzahl Spieler lbl updaten
			lblAnzahlSpieler.setBounds(20, 530, 150, 30);
			PnlMatches.add(lblAnzahlSpieler);
			// 6. Spiepaarungen auslosen
			rbtnGutMitSchlecht.setSelected(true);
			setPairs();
			// 7. Listener für Spiel hinzufügen.
			btnEndRound.setEnabled(true);
			btnEndRound.addMouseListener(endRound);
			//btnReset.addMouseListener(reset);
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

			// Array für model-Update
			String[] playerToAdd = new String[4];

			// Table model leeren
			model.setRowCount(0);

			// 1. Statistiken kalkulieren
			String playerNames[] = {};
			int playerPointsTeam1;
			int playerPointsTeam2;
			int result;
			// 1.1 first team
			if (!tfFeld1T1.getText().equals("")) {
				// Prüfen, ob Doppel oder Einzel auf Feld spielt
				if (lblFeld1Paarung1.getText().contains("+")) {
					playerNames = lblFeld1Paarung1.getText().split(
							Pattern.quote(" + "));
				} else {
					playerNames[0] = lblFeld1Paarung1.getText();
				}

				playerPointsTeam1 = Integer.parseInt(tfFeld1T1.getText());
				playerPointsTeam2 = Integer.parseInt(tfFeld1T2.getText());

				// prüfen, des Spielresultats
				if (playerPointsTeam1 > playerPointsTeam2) {
					result = pointsVictory;
				} else if (playerPointsTeam1 < playerPointsTeam2) {
					result = pointsLost;
				} else {
					result = pointsDraw;
				}

				calc.calculateNewStatistics(playerNames[0], result,
						playerPointsTeam1 - playerPointsTeam2);

				if (playerNames[1] != "") {
					calc.calculateNewStatistics(playerNames[1], result,
							playerPointsTeam1 - playerPointsTeam2);
					playerNames[1] = "";
				}
				tfFeld1T1.setEditable(false);
				playerNames[0] = "";
			}

			// 1.2 second team
			if (!tfFeld1T2.getText().equals("")) {
				if (lblFeld1Paarung2.getText().contains("+")) {
					playerNames = lblFeld1Paarung2.getText().split(
							Pattern.quote(" + "));
				} else {
					playerNames[0] = lblFeld1Paarung2.getText();
				}
				playerPointsTeam1 = Integer.parseInt(tfFeld1T1.getText());
				playerPointsTeam2 = Integer.parseInt(tfFeld1T2.getText());
				// prüfen, des Spielresultats
				if (playerPointsTeam2 > playerPointsTeam1) {
					result = pointsVictory;
				} else if (playerPointsTeam2 < playerPointsTeam1) {
					result = pointsLost;
				} else {
					result = pointsDraw;
				}

				calc.calculateNewStatistics(playerNames[0], result,
						playerPointsTeam2 - playerPointsTeam1);
				if (playerNames[1] != "") {
					calc.calculateNewStatistics(playerNames[1], result,
							playerPointsTeam2 - playerPointsTeam1);
					playerNames[1] = "";
				}
				tfFeld1T2.setEditable(false);
				playerNames[0] = "";

			}

			// 1.3 third team
			if (!tfFeld2T1.getText().equals("")) {
				// Prüfen, ob Doppel oder Einzel auf Feld spielt
				if (lblFeld2Paarung1.getText().contains("+")) {
					playerNames = lblFeld2Paarung1.getText().split(
							Pattern.quote(" + "));
				} else {
					playerNames[0] = lblFeld2Paarung1.getText();
				}

				playerPointsTeam1 = Integer.parseInt(tfFeld2T1.getText());
				playerPointsTeam2 = Integer.parseInt(tfFeld2T2.getText());

				// prüfen, des Spielresultats
				if (playerPointsTeam1 > playerPointsTeam2) {
					result = pointsVictory;
				} else if (playerPointsTeam1 < playerPointsTeam2) {
					result = pointsLost;
				} else {
					result = pointsDraw;
				}

				calc.calculateNewStatistics(playerNames[0], result,
						playerPointsTeam1 - playerPointsTeam2);
				if (playerNames[1] != "") {
					calc.calculateNewStatistics(playerNames[1], result,
							playerPointsTeam1 - playerPointsTeam2);
					playerNames[1] = "";
				}
				tfFeld2T1.setEditable(false);
				playerNames[0] = "";

			}

			// 1.4 4.team
			if (!tfFeld2T2.getText().equals("")) {
				// Prüfen, ob Doppel oder Einzel auf Feld spielt
				if (lblFeld2Paarung2.getText().contains("+")) {
					playerNames = lblFeld2Paarung2.getText().split(
							Pattern.quote(" + "));
				} else {
					playerNames[0] = lblFeld2Paarung2.getText();
				}

				playerPointsTeam1 = Integer.parseInt(tfFeld2T1.getText());
				playerPointsTeam2 = Integer.parseInt(tfFeld2T2.getText());

				// prüfen, des Spielresultats
				if (playerPointsTeam2 > playerPointsTeam1) {
					result = pointsVictory;
				} else if (playerPointsTeam2 < playerPointsTeam1) {
					result = pointsLost;
				} else {
					result = pointsDraw;
				}

				calc.calculateNewStatistics(playerNames[0], result,
						playerPointsTeam2 - playerPointsTeam1);
				if (playerNames[1] != "") {
					calc.calculateNewStatistics(playerNames[1], result,
							playerPointsTeam2 - playerPointsTeam1);
					playerNames[1] = "";
				}
				tfFeld2T2.setEditable(false);
				playerNames[0] = "";

			}

			// 1.5 5.team
			if (!tfFeld3T1.getText().equals("")) {
				// Prüfen, ob Doppel oder Einzel auf Feld spielt
				if (lblFeld3Paarung1.getText().contains("+")) {
					playerNames = lblFeld3Paarung1.getText().split(
							Pattern.quote(" + "));
				} else {
					playerNames[0] = lblFeld3Paarung1.getText();
				}
				
				playerPointsTeam1 = Integer.parseInt(tfFeld3T1.getText());
				playerPointsTeam2 = Integer.parseInt(tfFeld3T2.getText());

				// prüfen, des Spielresultats
				if (playerPointsTeam1 > playerPointsTeam2) {
					result = pointsVictory;
				} else if (playerPointsTeam1 < playerPointsTeam2) {
					result = pointsLost;
				} else {
					result = pointsDraw;
				}

				calc.calculateNewStatistics(playerNames[0], result,
						playerPointsTeam1 - playerPointsTeam2);
				if (playerNames[1] != "") {
					calc.calculateNewStatistics(playerNames[1], result,
							playerPointsTeam1 - playerPointsTeam2);
					playerNames[1] = "";
				}
				tfFeld3T1.setEditable(false);
				playerNames[0] = "";

			}

			// 1.6 6.team
			if (!tfFeld3T2.getText().equals("")) {
				// Prüfen, ob Doppel oder Einzel auf Feld spielt
				if (lblFeld3Paarung2.getText().contains("+")) {
					playerNames = lblFeld3Paarung2.getText().split(
							Pattern.quote(" + "));
				} else {
					playerNames[0] = lblFeld3Paarung2.getText();
				}

				playerPointsTeam1 = Integer.parseInt(tfFeld3T1.getText());
				playerPointsTeam2 = Integer.parseInt(tfFeld3T2.getText());

				
				// prüfen, des Spielresultats
				if (playerPointsTeam2 > playerPointsTeam1) {
					result = pointsVictory;
				} else if (playerPointsTeam2 < playerPointsTeam1) {
					result = pointsLost;
				} else {
					result = pointsDraw;
				}

				calc.calculateNewStatistics(playerNames[0], result,
						playerPointsTeam2 - playerPointsTeam1);
				if (playerNames[1] != "") {
					calc.calculateNewStatistics(playerNames[1], result,
							playerPointsTeam2 - playerPointsTeam1);

					playerNames[1] = "";
				}
				tfFeld3T2.setEditable(false);
				playerNames[0] = "";

			}

			// 1.7 7.team
			if (!tfFeld4T1.getText().equals("")) {
				if (lblFeld4Paarung1.getText().contains("+")) {
					playerNames = lblFeld4Paarung1.getText().split(
							Pattern.quote(" + "));
				} else {
					playerNames[0] = lblFeld4Paarung1.getText();
				}
				playerPointsTeam1 = Integer.parseInt(tfFeld4T1.getText());
				playerPointsTeam2 = Integer.parseInt(tfFeld4T2.getText());
				// prüfen, des Spielresultats
				if (playerPointsTeam1 > playerPointsTeam2) {
					result = pointsVictory;
				} else if (playerPointsTeam1 < playerPointsTeam2) {
					result = pointsLost;
				} else {
					result = pointsDraw;
				}

				calc.calculateNewStatistics(playerNames[0], result,
						playerPointsTeam1 - playerPointsTeam2);
				if (playerNames[1] != "") {
					calc.calculateNewStatistics(playerNames[1], result,
							playerPointsTeam1 - playerPointsTeam2);
					playerNames[1] = "";
				}

				tfFeld4T1.setEditable(false);
				playerNames[0] = "";
			}

			// 1.8 8.team
			if (!tfFeld4T2.getText().equals("")) {
				if (lblFeld4Paarung2.getText().contains("+")) {
					playerNames = lblFeld4Paarung2.getText().split(
							Pattern.quote(" + "));
				} else {
					playerNames[0] = lblFeld4Paarung2.getText();
				}
				playerPointsTeam1 = Integer.parseInt(tfFeld4T1.getText());
				playerPointsTeam2 = Integer.parseInt(tfFeld4T2.getText());
				// prüfen, des Spielresultats
				if (playerPointsTeam2 > playerPointsTeam1) {
					result = pointsVictory;
				} else if (playerPointsTeam2 < playerPointsTeam1) {
					result = pointsLost;
				} else {
					result = pointsDraw;
				}

				calc.calculateNewStatistics(playerNames[0], result,
						playerPointsTeam2 - playerPointsTeam1);
				if (playerNames[1] != "") {
					calc.calculateNewStatistics(playerNames[1], result,
							playerPointsTeam2 - playerPointsTeam1);
					playerNames[1] = "";
				}

				tfFeld4T2.setEditable(false);
				playerNames[0] = "";
			}

			// 1.9 9.team
			if (!tfFeld5T1.getText().equals("")) {
				if (lblFeld5Paarung1.getText().contains("+")) {
					playerNames = lblFeld5Paarung1.getText().split(
							Pattern.quote(" + "));
				} else {
					playerNames[0] = lblFeld5Paarung1.getText();
				}
				playerPointsTeam1 = Integer.parseInt(tfFeld5T1.getText());
				playerPointsTeam2 = Integer.parseInt(tfFeld5T2.getText());
				// prüfen, des Spielresultats
				if (playerPointsTeam1 > playerPointsTeam2) {
					result = pointsVictory;
				} else if (playerPointsTeam1 < playerPointsTeam2) {
					result = pointsLost;
				} else {
					result = pointsDraw;
				}

				calc.calculateNewStatistics(playerNames[0], result,
						playerPointsTeam1 - playerPointsTeam2);
				if (playerNames[1] != "") {
					calc.calculateNewStatistics(playerNames[1], result,
							playerPointsTeam1 - playerPointsTeam2);
					playerNames[1] = "";
				}

				tfFeld5T1.setEditable(false);
				playerNames[0] = "";
			}

			// 1.10 10.team
			if (!tfFeld5T2.getText().equals("")) {
				if (lblFeld5Paarung2.getText().contains("+")) {
					playerNames = lblFeld5Paarung2.getText().split(
							Pattern.quote(" + "));
				} else {
					playerNames[0] = lblFeld5Paarung2.getText();
				}
				playerPointsTeam1 = Integer.parseInt(tfFeld5T1.getText());
				playerPointsTeam2 = Integer.parseInt(tfFeld5T2.getText());
				// prüfen, des Spielresultats
				if (playerPointsTeam2 > playerPointsTeam1) {
					result = pointsVictory;
				} else if (playerPointsTeam2 < playerPointsTeam1) {
					result = pointsLost;
				} else {
					result = pointsDraw;
				}

				calc.calculateNewStatistics(playerNames[0], result,
						playerPointsTeam2 - playerPointsTeam1);
				if (playerNames[1] != "") {
					calc.calculateNewStatistics(playerNames[1], result,
							playerPointsTeam2 - playerPointsTeam1);
					playerNames[1] = "";
				}

				tfFeld5T2.setEditable(false);
				playerNames[0] = "";
			}

			// 1.11 11.team
			if (!tfFeld6T1.getText().equals("")) {
				if (lblFeld6Paarung1.getText().contains("+")) {
					playerNames = lblFeld6Paarung1.getText().split(
							Pattern.quote(" + "));
				} else {
					playerNames[0] = lblFeld6Paarung1.getText();
				}
				playerPointsTeam1 = Integer.parseInt(tfFeld6T1.getText());
				playerPointsTeam2 = Integer.parseInt(tfFeld6T2.getText());
				// prüfen, des Spielresultats
				if (playerPointsTeam1 > playerPointsTeam2) {
					result = pointsVictory;
				} else if (playerPointsTeam1 < playerPointsTeam2) {
					result = pointsLost;
				} else {
					result = pointsDraw;
				}

				calc.calculateNewStatistics(playerNames[0], result,
						playerPointsTeam1 - playerPointsTeam2);
				if (playerNames[1] != "") {
					calc.calculateNewStatistics(playerNames[1], result,
							playerPointsTeam1 - playerPointsTeam2);
					playerNames[1] = "";
				}

				tfFeld6T1.setEditable(false);
				playerNames[0] = "";
			}

			// 1.12 12.team
			if (!tfFeld6T2.getText().equals("")) {
				if (lblFeld6Paarung2.getText().contains("+")) {
					playerNames = lblFeld6Paarung2.getText().split(
							Pattern.quote(" + "));
				} else {
					playerNames[0] = lblFeld6Paarung2.getText();
				}
				playerPointsTeam1 = Integer.parseInt(tfFeld6T1.getText());
				playerPointsTeam2 = Integer.parseInt(tfFeld6T2.getText());
				// prüfen, des Spielresultats
				if (playerPointsTeam2 > playerPointsTeam1) {
					result = pointsVictory;
				} else if (playerPointsTeam2 < playerPointsTeam1) {
					result = pointsLost;
				} else {
					result = pointsDraw;
				}

				calc.calculateNewStatistics(playerNames[0], result,
						playerPointsTeam2 - playerPointsTeam1);
				if (playerNames[1] != "") {
					calc.calculateNewStatistics(playerNames[1], result,
							playerPointsTeam2 - playerPointsTeam1);
					playerNames[1] = "";
				}

				tfFeld6T2.setEditable(false);
				playerNames[0] = "";
			}

			// 2. Playerlist sortieren mit methode sortPlayerlist.
			calc.sortPlayerList();

			// 3. Model updaten
			for (Player p : calc.getPlayerList()) {
				playerToAdd[0] = String.valueOf(p.getPlatz());
				playerToAdd[1] = p.getName();
				playerToAdd[2] = String.valueOf(p.getPoints());
				playerToAdd[3] = String.valueOf(p.getDifference());

				model.addRow(playerToAdd);

				playerToAdd = new String[playerToAdd.length];
			}

			// 4. update table/Rangliste
			model.fireTableDataChanged();
			model.fireTableStructureChanged();
			// 5. Button disablen/enablen und listener hinzufügen/entfernen
			btnEndRound.setEnabled(false);
			btnEndRound.removeMouseListener(endRound);

			// 6. button nextround enablen + Listener
			btnNextRound.setEnabled(true);
			btnNextRound.addMouseListener(nextRound);

			// 7. Rundenzähler hochsetzen.
			calc.setGespielteRunden(calc.getGespielteRunden() + 1);
			lblAnzahlRunden.setText(lblAnzahlRunden.getText().substring(0, 17)
					+ " " + String.valueOf(calc.getGespielteRunden()));

		}
		// }
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
			tfFeld1T1.setText("");
			tfFeld1T1.setEditable(true);
			tfFeld1T2.setText("");
			tfFeld1T2.setEditable(true);
			tfFeld2T1.setText("");
			tfFeld2T1.setEditable(true);
			tfFeld2T2.setText("");
			tfFeld2T2.setEditable(true);
			tfFeld3T1.setText("");
			tfFeld3T1.setEditable(true);
			tfFeld3T2.setText("");
			tfFeld3T2.setEditable(true);
			tfFeld4T1.setText("");
			tfFeld4T1.setEditable(true);
			tfFeld4T2.setText("");
			tfFeld4T2.setEditable(true);
			tfFeld5T1.setText("");
			tfFeld5T1.setEditable(true);
			tfFeld5T2.setText("");
			tfFeld5T2.setEditable(true);
			tfFeld6T1.setText("");
			tfFeld6T1.setEditable(true);
			tfFeld6T2.setText("");
			tfFeld6T2.setEditable(true);

			// 2. neue Runde auslosen
			setPairs();

			// 3. "endRound button" enablen und listener hinzufügen.
			btnEndRound.setEnabled(true);
			btnEndRound.addMouseListener(endRound);

			// 4. diesen Button disablen und listerner entfernen
			btnNextRound.setEnabled(false);
			btnNextRound.removeMouseListener(nextRound);
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
//			//1. Alle Spieler (Liste) löschen
//			calc.getPlayerList().clear();
//			System.out.println(calc.getPlayerList().size());
//			//2. Tabelle aktualisieren
//			while(model.getRowCount() > 0) {
//				model.removeRow(0);
//			}
//			model.fireTableDataChanged();
//			model.fireTableStructureChanged();
//			//3. alle Labels auf 0 setzen
//			lblFeld1Paarung1.setText("");
//			lblFeld1Paarung2.setText("");
//			lblFeld2Paarung1.setText("");
//			lblFeld2Paarung2.setText("");
//			lblFeld3Paarung1.setText("");
//			lblFeld3Paarung2.setText("");
//			lblFeld4Paarung1.setText("");
//			lblFeld4Paarung2.setText("");
//			lblFeld5Paarung1.setText("");
//			lblFeld5Paarung2.setText("");
//			lblFeld6Paarung1.setText("");
//			lblFeld6Paarung2.setText("");
//			
//			lblAnzahlRunden.setText("");
//			lblAnzahlSpieler.setText("");
//			
//			//3.1 Zählvariablen auf 0 setzen
//			calc.setGespielteRunden(0);
//			
//			//3.2 Ergebnissfelder auf 0 setzen
//			tfFeld1T1.setText("");
//			tfFeld1T2.setText("");
//			tfFeld2T1.setText("");
//			tfFeld2T2.setText("");
//			tfFeld3T1.setText("");
//			tfFeld3T2.setText("");
//			tfFeld4T1.setText("");
//			tfFeld4T2.setText("");
//			tfFeld5T1.setText("");
//			tfFeld5T2.setText("");
//			tfFeld6T1.setText("");
//			tfFeld6T2.setText("");
//			
//			//4. Eingabescreen anzeigen
//			
//			PnlNames.setVisible(true);
//			PnlMatches.setVisible(false);
//			
//			//5. Alle Listener von Spielflussbuttons entfernen
////			btnEndRound.removeMouseListener(endRound);
////			btnNextRound.removeMouseListener(nextRound);
////			btnReset.removeMouseListener(reset);
//			//6. Buttons vom Spielfluss ausblenden
//			btnEndRound.setVisible(false);
//			btnNextRound.setVisible(false);
//			btnReset.setVisible(false);
			
			
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
