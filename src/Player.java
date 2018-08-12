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

public class Player {

	private String name;
	private int points;
	private int difference;
	private int platz;
	private int rundenAufPlatz;
	private boolean gespielt;

	public Player() {
	}

	public Player(String name) {
		this.platz = 1;
		this.name = name;
		this.points = 0;
		this.difference = 0;
		this.rundenAufPlatz = 0;
		this.gespielt = true;

	}

	public Player(int platz, String name, int points, int difference, int rundenAufPlatz, boolean gespielt) {
		this.platz = platz;
		this.name = name;
		this.points = points;
		this.difference = difference;
		this.rundenAufPlatz = rundenAufPlatz;
		this.gespielt = gespielt;
	}

	// Getters und Setters

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getDifference() {
		return difference;
	}

	public void setDifference(int difference) {
		this.difference = difference;
	}

	public int getPlatz() {
		return platz;
	}

	public void setPlatz(int platz) {
		this.platz = platz;
	}

	public int getRundenAufPlatz() {
		return rundenAufPlatz;
	}

	public void setRundenAufPlatz(int rundenAufPlatz) {
		this.rundenAufPlatz = rundenAufPlatz;
	}

	public boolean isGespielt() {
		return gespielt;
	}

	public void setGespielt(boolean gespielt) {
		this.gespielt = gespielt;
	}

}
