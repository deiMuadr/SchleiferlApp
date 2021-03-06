
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

import java.util.*;

public class Calculations {

	// Instance
	private static Calculations _instance;
	
	// Global strings
	private String playerMatrix[][];

	// Global arrays
	private List<Player> playerList = new ArrayList<Player>();
	private List<Player> tempListFirstPlayers;
	private List<Player> tempListSecondPlayers;
	private List<Player> tempListPausedPlayers;

	// Global integers
	private int gespielteRunden;
	private int numOfcourts;

	// Constructor is now private
	private Calculations() {

		gespielteRunden = 0;

	}

	// Call this method to get an instance
	public static Calculations getInstance() {

		if (_instance == null)
			_instance = new Calculations();
		return _instance;

	}

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
						&& p.getDifference() == playerList.get(i - 1).getDifference()) {
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
	 * Erstellt die Spielerliste obere H\u00E4lft spielt mit untere H\u00E4lfte
	 */
	public void createPairsTopandBottom() {

		tempListFirstPlayers = new ArrayList<Player>();
		tempListSecondPlayers = new ArrayList<Player>();
		tempListPausedPlayers = new ArrayList<Player>();

		int firstPlayer;
		int secondPlayer;
		int obereGrenze;
		int untereGrenze;

		// reset "gespielt" once all players played.
		this.resetPlayed();

		while (playerList.size() > 0) {

			// 1. Random Spieler suchen
			firstPlayer = randInt(0, playerList.size() - 1);
			// 2. pruefen, ob isGespielt, wenn ja neuen Spieler suchen
			firstPlayer = checkIsGespielt(playerList.size() * 2, playerList.size() - 1, 0, firstPlayer);

			// 3. Pruefen, ob Spieler 1 in oberer oder unteren Haelfte
			if (firstPlayer < playerList.size() / 2) {
				// obere Haelfte, also in unterer suchen

				obereGrenze = Math.round(playerList.size() / 2);
				if (playerList.size() % 2 != 0) {
					obereGrenze = (playerList.size() - 1) / 2;
				} else {
					obereGrenze = playerList.size() / 2;
				}

				untereGrenze = playerList.size() - 1;

				secondPlayer = firstPlayer;
				// Pruefen, ob zweiter Spieler isGespielt = true hat, dann neu losen.
				while (secondPlayer == firstPlayer) {
					secondPlayer = randInt(obereGrenze, untereGrenze);
					secondPlayer = checkIsGespielt(playerList.size() * 2, untereGrenze, obereGrenze, secondPlayer);
				}

				// Spieler zu den Listen hinzfuegen.
				tempListSecondPlayers.add(playerList.get(secondPlayer));
				tempListFirstPlayers.add(playerList.get(firstPlayer));
				// erst schlechteren Spieler loeschen, dann den besseren, da sonst der Index
				// falsch ist.

				playerList.remove(secondPlayer);
				playerList.remove(firstPlayer);

			} else {
				// untere Haelfte, also in oberer suchen

				obereGrenze = 0;
				if (playerList.size() % 2 != 0) {
					untereGrenze = (playerList.size() - 1) / 2;
				} else {
					untereGrenze = playerList.size() / 2;
				}

				secondPlayer = firstPlayer;
				// Pruefen, ob zweiter Spiler isGespielt = true hat, dann neu losen.
				while (secondPlayer == firstPlayer) {
					secondPlayer = randInt(obereGrenze, untereGrenze);
					secondPlayer = checkIsGespielt(playerList.size() * 2, untereGrenze, obereGrenze, secondPlayer);
				}

				// Spieler zu den Listen hinzfuegen.
				tempListSecondPlayers.add(playerList.get(secondPlayer));
				tempListFirstPlayers.add(playerList.get(firstPlayer));

				// erst schlechteren Spieler loeschen, dann den besseren, da sonst der Index
				// falsch ist.
				playerList.remove(firstPlayer);
				playerList.remove(secondPlayer);

			}

			if (playerList.size() == 2) {
				firstPlayer = 0;
				secondPlayer = 1;

				// Spieler in die tempListen schreiben
				tempListFirstPlayers.add(playerList.get(firstPlayer));
				tempListSecondPlayers.add(playerList.get(secondPlayer));
				// Spieler aus der Playerslist l\u00F6schen.
				playerList.remove(secondPlayer);
				playerList.remove(firstPlayer);
			}
			// bei ungerader Anzahl oder mehr wie maximal moegliche Spieleranzahl die
			// \u00F6brigen Spieler
			// in die Paused-Liste
			// schreiben und aus PlayerList l\u00F6schen
			if (playerList.size() == 1 || tempListFirstPlayers.size() >= this.numOfcourts * 2) {
				int x = playerList.size();
				while (x > 0) {
					tempListPausedPlayers.add(playerList.get(0));
					playerList.get(0).setGespielt(false);
					playerList.remove(0);
					x--;
				}
			}
		}
//		for (Player p : tempListPausedPlayers) {
//			System.out.println(p.getName());
//		}
	}

	/**
	 * Erstellt die Spielpaarungen. Gut spielt mit Gut
	 */
	public void createPairsTopandTop() {
//TODO: ueberpruefen, da es nicht ganz sauber lost.
		tempListFirstPlayers = new ArrayList<Player>();
		tempListSecondPlayers = new ArrayList<Player>();
		tempListPausedPlayers = new ArrayList<Player>();

		int firstPlayer;
		int secondPlayer;
		int obereGrenze;
		int untereGrenze;

		// reset "gespielt" once all players played.
		this.resetPlayed();

		while (playerList.size() > 0) {

			// 1. Random Spieler suchen
			firstPlayer = randInt(0, playerList.size() - 1);
			// 2. pruefen, ob isGespielt, wenn ja neuen Spieler suchen
			firstPlayer = checkIsGespielt(playerList.size() * 2, playerList.size() - 1, 0, firstPlayer);

			// 3. Pruefen, ob Spieler 1 in oberer oder unteren Haelfte
			if (firstPlayer < playerList.size() / 2) {
				// obere Haelfte, also in oberer Haelfte Spieler 2 suchen

				obereGrenze = 0;
				if (playerList.size() % 2 != 0) {
					untereGrenze = (playerList.size() - 1) / 2;
				} else {
					untereGrenze = playerList.size() / 2;
				}

				secondPlayer = firstPlayer;
				// Pruefen, ob zweiter Spiler isGespielt = true hat, dann neu losen.
				while (secondPlayer == firstPlayer) {
					secondPlayer = randInt(obereGrenze, untereGrenze);
					secondPlayer = checkIsGespielt(playerList.size() * 2, untereGrenze, obereGrenze, secondPlayer);
				}

				// Spieler zu den Listen hinzfuegen.
				tempListSecondPlayers.add(playerList.get(secondPlayer));
				tempListFirstPlayers.add(playerList.get(firstPlayer));
				// erst schlechteren Spieler loeschen, dann den besseren, da sonst der Index
				// falsch ist.

				if (firstPlayer > secondPlayer) {
					playerList.remove(firstPlayer);
					playerList.remove(secondPlayer);
				} else {
					playerList.remove(secondPlayer);
					playerList.remove(firstPlayer);
				}

			} else {
				// untere Haelfte, also in unterer Haelfte Spieler 2 suchen

				obereGrenze = Math.round(playerList.size() / 2);
				if (playerList.size() % 2 != 0) {
					obereGrenze = (playerList.size() - 1) / 2;
				} else {
					obereGrenze = playerList.size() / 2;
				}

				untereGrenze = playerList.size() - 1;

				secondPlayer = firstPlayer;
				// Pruefen, ob zweiter Spieler isGespielt = true hat, dann neu losen.
				while (secondPlayer == firstPlayer) {
					secondPlayer = randInt(obereGrenze, untereGrenze);
					secondPlayer = checkIsGespielt(playerList.size() * 2, untereGrenze, obereGrenze, secondPlayer);
				}

				// Spieler zu den Listen hinzfuegen.
				tempListSecondPlayers.add(playerList.get(secondPlayer));
				tempListFirstPlayers.add(playerList.get(firstPlayer));

				// erst schlechteren Spieler loeschen, dann den besseren, da sonst der Index
				// falsch ist.
				if (firstPlayer > secondPlayer) {
					playerList.remove(firstPlayer);
					playerList.remove(secondPlayer);
				} else {
					playerList.remove(secondPlayer);
					playerList.remove(firstPlayer);
				}
			}

			if (playerList.size() == 2) {
				firstPlayer = 0;
				secondPlayer = 1;

				// Spieler in die tempListen schreiben
				tempListFirstPlayers.add(playerList.get(firstPlayer));
				tempListSecondPlayers.add(playerList.get(secondPlayer));
				// Spieler aus der Playerslist l\u00F6schen.
				playerList.remove(secondPlayer);
				playerList.remove(firstPlayer);
			}
			// bei ungerader Anzahl oder mehr wie maximal moegliche Spieleranzahl die
			// \u00F6brigen Spieler
			// in die Paused-Liste
			// schreiben und aus PlayerList l\u00F6schen
			if (playerList.size() == 1 || tempListFirstPlayers.size() >= this.numOfcourts * 2) {
				int x = playerList.size();
				while (x > 0) {
					tempListPausedPlayers.add(playerList.get(0));
					playerList.get(0).setGespielt(false);
					playerList.remove(0);
					x--;
				}
			}
		}
//		for (Player p : tempListPausedPlayers) {
//			System.out.println(p.getName());
//		}
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
	 * @param min minimum
	 * @param max maximum
	 * @return random Zahl
	 */
	public int randInt(int min, int max) {
		Random r = new Random();
		int random = r.nextInt((max - min) + 1) + min;

		return random;
	}

	/**
	 * Hilfsmethode zur Pruefung von isGespielt und somit zur Spielerauswahl
	 * 
	 * @param maxDraws   maximum number of draws - should be 2*playerList.size
	 * @param upperLimit upperLimit for random number
	 * @param lowerLimit lower Limit for random number
	 * @param Player     player to check
	 */
	public int checkIsGespielt(int maxDraws, int lowerLimit, int upperLimit, int player) {
		// 1. Auf flag "gespielt" pr\u00FCfen. Spieler mit Flag =false bevorzugen
		// 2. Falls nicht gespielt, dann nehmen (break), sonst neu losen bis maximal
		// 2*Playerlist.size durchlaufen ist. Dann break
		int draws = 0;
		while (playerList.get(player).isGespielt()) {
			player = randInt(upperLimit, lowerLimit);

			if (!playerList.get(player).isGespielt()) {
				break;
			}

			if (draws == maxDraws) {
				break;
			}
			draws += 1;
		}
		return player;
	}

	/**
	 * Methode zum reset von isGespielt, sobald alle einmal ausgesetzt haben.
	 */
	public void resetPlayed() {
		int i = 0;
		for (Player p : playerList) {
			if (!p.isGespielt()) {
				i += 1;
			}
		}
		if (i == playerList.size()) {
			for (Player p : playerList) {
				p.setGespielt(true);
			}
		}
	}

	// Getter und Setter
	public String[][] getPlayerMatrix() {
		return playerMatrix;
	}

	public void setPlayerMatrix(String playerMatrix[][]) {
		this.playerMatrix = playerMatrix;
}
	
	public void setPlayerList(ArrayList<Player> playerList) {
		this.playerList = playerList;
	}

	public List<Player> getPlayerList() {
		return playerList;
	}

	public void addPlayer(Player p) {
		playerList.add(p);
	}

	// Get specific player data for JSON log
	public Player getPlayer(String playerName) {
		Player player = new Player();
		for (Player p : playerList) {
			if (p.getName().equals(playerName)) {
				player = p;
				break;
			}
		}
		return player;
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

	public int getNumOfcourts() {
		return numOfcourts;
	}

	public void setNumOfcourts(int numOfcourts) {
		this.numOfcourts = numOfcourts;
	}

//	public static void main(String[] args) {
//		Player p1 = new Player("A");
//		Player p2 = new Player("B");
//		Player p3 = new Player("C");
//		Player p4 = new Player("D");
//		Player p5 = new Player("E");
//		Player p6 = new Player("F");
//		Player p7 = new Player("G");
//		Player p8 = new Player("H");
//		Player p9 = new Player("I");
//		Player p10 = new Player("J");
//		Player p11 = new Player("k");
//		Player p12 = new Player("l");
//		Player p13 = new Player("m");
//		Player p14 = new Player("n");
//		Player p15 = new Player("o");
//		Player p16 = new Player("p");
//		Player p17 = new Player("q");
//		Player p18 = new Player("r");
//		Player p19 = new Player("s");
//		Player p20 = new Player("t");
//		Player p21 = new Player("u");
//		Player p22 = new Player("v");
//		Player p23 = new Player("w");
//		Player p24 = new Player("x");
//		Player p25 = new Player("y");
//		Player p26 = new Player("z");
//		Player p27 = new Player("1");
//		Player p28 = new Player("2");
//		Player p29 = new Player("3");
//		p3.setGespielt(false);
//
//		Calculations c = new Calculations();
//		c.addPlayer(p1);
//		c.addPlayer(p2);
//		c.addPlayer(p3);
//		c.addPlayer(p4);
//		c.addPlayer(p5);
//		c.addPlayer(p6);
//		c.addPlayer(p7);
//		c.addPlayer(p8);
//		c.addPlayer(p9);
//		c.addPlayer(p10);
//		c.addPlayer(p11);
//		c.addPlayer(p12);
//		c.addPlayer(p13);
//		c.addPlayer(p14);
//		c.addPlayer(p15);
//		c.addPlayer(p16);
//		c.addPlayer(p17);
//		c.addPlayer(p18);
//		c.addPlayer(p19);
//		c.addPlayer(p20);
//		c.addPlayer(p21);
//		c.addPlayer(p22);
//		c.addPlayer(p23);
//		c.addPlayer(p24);
//		c.addPlayer(p25);
//		c.addPlayer(p26);
//		c.addPlayer(p27);
//		c.addPlayer(p28);
//		c.addPlayer(p29);
//
//		c.setNumOfcourts(2);
//		c.createPairsTopandBottom();
//
//	}
//		for (Player p : c.getTempListPausedPlayers()) {
//			System.out.println(p.getName());
//		}
//		System.out.println();
//
//		c.createPairsTopandBottom();
//		for (Player p : c.getTempListPausedPlayers()) {
//			System.out.println(p.getName());
//		}
//		System.out.println();
//
//		c.createPairsTopandBottom();
//		for (Player p : c.getTempListPausedPlayers()) {
//			System.out.println(p.getName());
//		}
//		System.out.println();
//		c.createPairsTopandBottom();
//		for (Player p : c.getTempListPausedPlayers()) {
//			System.out.println(p.getName());
//		}
//		System.out.println();
//		c.createPairsTopandBottom();
//		for (Player p : c.getTempListPausedPlayers()) {
//			System.out.println(p.getName());
//		}
//		System.out.println();

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
//	}
}
