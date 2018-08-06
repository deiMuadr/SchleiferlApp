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

import java.util.*;

public class Calculations {

	private String playerMatrix[][];
	private List<Player> playerList = new ArrayList<Player>();
	private List<Player> tempListFirstPlayers;
	private List<Player> tempListSecondPlayers;
	private List<Player> tempListPausedPlayers;

	private int gespielteRunden;

	int NumOfPlayers = playerList.size();

	// Konstruktor
	public Calculations() {
		gespielteRunden = 0;
	}

	// /**
	// * Kreiert die Matrix zum einlesen der SPieler und Werte in die
	// OrderTabelle
	// *
	// * @param playerList
	// * Liste mit Spielern
	// */
	// public void createMatrix(List<Player> playerList) {
	// int numbPlayers = playerList.size();
	//
	// playerMatrix = new String[numbPlayers][4];
	//
	// for (int i = 0; i < numbPlayers; i++) {
	// playerMatrix[i][0] = String.valueOf(playerList.get(i).getPlatz());
	// playerMatrix[i][1] = playerList.get(i).getName();
	// playerMatrix[i][2] = String.valueOf(playerList.get(i).getPoints());
	// playerMatrix[i][3] = String.valueOf(playerList.get(i).getDifference());
	// }
	// }
	//
	// /**
	// * Löscht alle Werte aus der Matrix, um sie neu beschreiben zu können.
	// *
	// * @param playerList
	// * Liste mit Spielern
	// */
	// public void deleteMatrix(ArrayList<Player> playerList) {
	// // TODO wird vermutlich nicht benötigt - Modellfüllung wird über Array
	// gelöst
	// }

	/**
	 * Sortiert die Playerliste nach den Punkten
	 */
	public void sortPlayerList() {

		Collections.sort(playerList, new Comparator<Player>() {

			@Override
			public int compare(Player p1, Player p2) {
				int pointsComp = p2.getPoints() - p1.getPoints();

				if (pointsComp != 0) {
					return pointsComp;
				} else {
					return p2.getDifference() - p1.getDifference();
				}

			}

		});

		// Platz updaten, nachdem neu sortiert wurde
		for (int i = 0; i < playerList.size(); i++) {
			Player p = playerList.get(i);
			if (i > 0) {
				if (p.getPoints() == playerList.get(i - 1).getPoints()
						&& p.getDifference() == playerList.get(i - 1)
								.getDifference()) {
					p.setPlatz(playerList.get(i - 1).getPlatz());
				} else {
					p.setPlatz(i + 1);
				}

			} else {
				p.setPlatz(i + 1);
			}
		}
	}

	/**
	 * Erstellt die Spielerliste obere Hälft spielt mit untere Hälfte
	 */
	public void createPairsTopandBottom() {

		tempListFirstPlayers = new ArrayList<Player>();
		tempListSecondPlayers = new ArrayList<Player>();
		tempListPausedPlayers = new ArrayList<Player>();

		int firstPlayer = 0;
		int secondPlayer = 0;
		int obereGrenze = 0;
		int untereGrenze = 0;

		int gespielt = 0;
		int nichtGespielt = 0;

		//TODO: gespielt nicht gespielt funktiniert noch nicht, d.h.aussetzen bis jeder einmal ausgesetzt hat
		for (Player p : playerList) {
			if (p.isGespielt()) {
				gespielt += 1;
			} else {
				nichtGespielt += 1;
			}
		}

		if (nichtGespielt == playerList.size()) {
			for (Player p : playerList) {
				p.setGespielt(true);
				gespielt = playerList.size();
				nichtGespielt = 0;
			}
		}

		while (playerList.size() > 0) {

			// 1. Auf flag "gespielt" prüfen. Spieler mit Flag bevorzugen.

			int i = 1;
			while (i < playerList.size() && gespielteRunden != 0) {

				if (!playerList.get(i).isGespielt()) {
					// partner suchen und beide löschen aus der liste
					// prüfen an welcher stelle der Spieler steht, wenn oben,
					// dann unten einen Parnter, wenn unten, dann oben einen
					// Partner.
					if (i < Math.round(playerList.size() / 2)) {

						// zufallszahl von mitte bis zum letzten Element
						obereGrenze = Math.round(playerList.size() / 2);
						untereGrenze = playerList.size() - 1;

						secondPlayer = i;

						while (i == secondPlayer) {
							secondPlayer = randInt(obereGrenze, untereGrenze);
						}

						tempListFirstPlayers.add(playerList.get(i));
						// TODO: wenn aAussetzen gespeichert werden soll und
						// erst aussetzen nachdem alle ausgestetzt haben möglich
						// sein soll, dann Zeile unten löschen.
						// playerList.get(i).setGespielt(true);
						tempListSecondPlayers.add(playerList.get(secondPlayer));

						// Erste 2. spieler, dann ersten entfernen, damit index
						// nicht verrutscht und ein falscher Spieler gelöscht
						// wird.
						playerList.remove(secondPlayer);
						playerList.remove(i);

					} else {

						untereGrenze = Math.round(playerList.size() / 2);
						obereGrenze = 0;

						firstPlayer = i;

						while (i == firstPlayer) {
							firstPlayer = randInt(obereGrenze, untereGrenze);
						}

						tempListSecondPlayers.add(playerList.get(i));
						// TODO: wenn aAussetzen gespeichert werden soll und
						// erst aussetzen nachdem alle ausgestetzt haben möglich
						// sein soll, dann Zeile unten löschen.
						// playerList.get(i).setGespielt(true);

						tempListFirstPlayers.add(playerList.get(firstPlayer));

						playerList.remove(i);
						playerList.remove(firstPlayer);

					}
					// Liste wieder von vorne durchlaufen
					i = 1;
				} else {
					i = i + 1;
				}
			}

			// mit normaler Auslosung weitermachen
			if (playerList.size() != 1) {
				// Zufallszahl von erstem Bis mittleren Element
				untereGrenze = Math.round(playerList.size() / 2);
				obereGrenze = 0;
				firstPlayer = randInt(obereGrenze, untereGrenze);

				// zufallszahl von mitte bis zum letzten Element, falls zweimal
				// der
				// gleiche Spieler gelost wird.
				// Endlosschleife vermeiden, wenn nur noch 2 Spieler in der
				// Liste.,
				// sonst normal losen

				secondPlayer = firstPlayer;
				while (secondPlayer == firstPlayer && playerList.size() > 2) {
					obereGrenze = Math.round((playerList.size() / 2));
					untereGrenze = playerList.size() - 1;
					secondPlayer = randInt(obereGrenze, untereGrenze);
				}

				if (playerList.size() == 2) {
					firstPlayer = 0;
					secondPlayer = 1;
				}

				// Spieler in die tempListen schreiben
				tempListFirstPlayers.add(playerList.get(firstPlayer));
				tempListSecondPlayers.add(playerList.get(secondPlayer));
				// Spieler aus der Playerslist löschen.
				playerList.remove(secondPlayer);
				playerList.remove(firstPlayer);
			}
			// bei ungerader Anzahl oder mehr wie 24 Spieler die öbrigen Spieler
			// in die Paused-Liste
			// schreiben und aus PlayerList löschen
			//TODO: bei nicht 6 Feldern muss die 12 entsprechend angepasst werden.
			if (playerList.size() == 1 || tempListFirstPlayers.size() >= 12) {
				int x = playerList.size();
				while (x > 0) {
					tempListPausedPlayers.add(playerList.get(0));
					playerList.get(0).setGespielt(false);
					playerList.remove(0);
					x--;

				}
			}
		}
		for (Player p : tempListPausedPlayers) {
			System.out.println(p.getName());
		}
	}

	/**
	 * Erstellt die Spielpaarungen. Gut spielt mit Gut
	 */
	public void createPairsTopandTop() {
		// TODO Fraglich, ob benötigt. Erst einmal mit nur einer Verison
	}

	public void calculateNewStatistics(String playerName, int result, int points) {
		for (Player p : playerList) {
			if (p.getName().equals(playerName)) {
				p.setPoints(p.getPoints() + result);
				p.setDifference(p.getDifference() + points);
				p.setRundenAufPlatz(p.getRundenAufPlatz() + 1);
				break;
			}

		}
	}

	/**
	 * Random Zahl zwischen min und max
	 *
	 * @param min
	 *            minimum
	 * @param max
	 *            maximum
	 * @return random Zahl
	 */
	public int randInt(int min, int max) {
		Random r = new Random();
		int random = r.nextInt((max - min) + 1) + min;

		return random;
	}

	// Getter und Setter
	public void setPlayerList(ArrayList<Player> playerList) {
		this.playerList = playerList;
	}

	public List<Player> getPlayerList() {
		return playerList;
	}

	public void addPlayer(Player p) {
		playerList.add(p);
	}

	public String[][] getPlayerMatrix() {
		return playerMatrix;
	}

	public void setPlayerMatrix(String playerMatrix[][]) {
		this.playerMatrix = playerMatrix;
	}

	public List<Player> getTempListFirstPlayers() {
		return tempListFirstPlayers;
	}

	public void setTempListFirstPlayers(List<Player> tempListFirstPlayers) {
		this.tempListFirstPlayers = tempListFirstPlayers;
	}

	public List<Player> getTempListSecondPlayers() {
		return tempListSecondPlayers;
	}

	public void setTempListSecondPlayers(List<Player> tempListSecondPlayers) {
		this.tempListSecondPlayers = tempListSecondPlayers;
	}

	public List<Player> getTempListPausedPlayers() {
		return tempListPausedPlayers;
	}

	public void setTempListPausedPlayers(List<Player> tempListPausedPlayers) {
		this.tempListPausedPlayers = tempListPausedPlayers;
	}

	public void setPlayerList(List<Player> playerList) {
		this.playerList = playerList;
	}

	public int getGespielteRunden() {
		return gespielteRunden;
	}

	public void setGespielteRunden(int gespielteRunden) {
		this.gespielteRunden = gespielteRunden;
	}

	public static void main(String[] args) {
		Player p1 = new Player("A");
		Player p2 = new Player("B");
		Player p3 = new Player("C");
		Player p4 = new Player("D");
		Player p5 = new Player("E");
		Player p6 = new Player("F");
		Player p7 = new Player("G");
		Player p8 = new Player("H");
		Player p9 = new Player("I");
		Player p10 = new Player("J");
		Player p11 = new Player("k");
		Player p12 = new Player("l");
		Player p13 = new Player("m");
		Player p14 = new Player("n");
		Player p15 = new Player("o");
		Player p16 = new Player("p");
		Player p17 = new Player("q");
		Player p18 = new Player("r");
		Player p19 = new Player("s");
		Player p20 = new Player("t");
		Player p21 = new Player("u");
		Player p22 = new Player("v");
		Player p23 = new Player("w");
		Player p24 = new Player("x");
		Player p25 = new Player("y");
		Player p26 = new Player("z");
		Player p27 = new Player("1");
		Player p28 = new Player("2");
		Player p29 = new Player("3");

		Calculations c = new Calculations();
		c.addPlayer(p1);
		c.addPlayer(p2);
		c.addPlayer(p3);
		c.addPlayer(p4);
		c.addPlayer(p5);
		c.addPlayer(p6);
		c.addPlayer(p7);
		c.addPlayer(p8);
		c.addPlayer(p9);
		c.addPlayer(p10);
		c.addPlayer(p11);
		c.addPlayer(p12);
		c.addPlayer(p13);
		c.addPlayer(p14);
		c.addPlayer(p15);
		c.addPlayer(p16);
		c.addPlayer(p17);
		c.addPlayer(p18);
		c.addPlayer(p19);
		c.addPlayer(p20);
		c.addPlayer(p21);
		c.addPlayer(p22);
		c.addPlayer(p23);
		c.addPlayer(p24);
		c.addPlayer(p25);
		c.addPlayer(p26);
		c.addPlayer(p27);
		c.addPlayer(p28);
		c.addPlayer(p29);

		c.createPairsTopandBottom();
		for (Player p : c.getTempListPausedPlayers()) {
			System.out.println(p.getName());
		}
		System.out.println();

		c.createPairsTopandBottom();
		for (Player p : c.getTempListPausedPlayers()) {
			System.out.println(p.getName());
		}
		System.out.println();

		c.createPairsTopandBottom();
		for (Player p : c.getTempListPausedPlayers()) {
			System.out.println(p.getName());
		}
		System.out.println();
		c.createPairsTopandBottom();
		for (Player p : c.getTempListPausedPlayers()) {
			System.out.println(p.getName());
		}
		System.out.println();
		c.createPairsTopandBottom();
		for (Player p : c.getTempListPausedPlayers()) {
			System.out.println(p.getName());
		}
		System.out.println();

		//
		//
		// c.sortPlayerList();
		//
		// // for(Player p : c.getPlayerList()) {
		// // System.out.println(p.getPoints() + " " + p.getName());
		// // }
		// //
		// c.createPairsTopandBottom();
		//
		// }
		// // Calculations cal = new Calculations();
		// // cal.addPlayer(p1);
		// // cal.addPlayer(p2);
		// // cal.addPlayer(p3);
		// //
		// // cal.createMatrix(cal.getPlayerList());
		// //
		// // for(int i = 0; i < cal.getPlayerList().size(); i++){
		// // System.out.print(cal.getPlayerMatrix()[i][0]);
		// // System.out.print(cal.getPlayerMatrix()[i][1]);
		// // System.out.print(cal.getPlayerMatrix()[i][2]);
		// // System.out.print(cal.getPlayerMatrix()[i][3]);
		// // System.out.println("");
		// // }
		// //
		// // }

		// public static void main(String[] args) {
		// for(int i = 1; i < 10; i++) {
		//
		// System.out.println(randInt(1, 5));
		// }
	}
}
