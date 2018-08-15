import org.json.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.FileWriter;
import java.io.IOException;

public class Log {

	private Calculations calc = Calculations.getInstance();
	private JSONArray rounds;

	public Log() {
		this.rounds = new JSONArray();
	}

	/*
	 * Add log entry
	 * 
	 * @Int round
	 * 
	 * @String[] teamA
	 * 
	 * @String[] teamB
	 * 
	 * @Int resultA
	 * 
	 * @Int resultB
	 */
	public void addLog(int round, String[] teamA, String[] teamB, int resultA, int resultB) {

		//if round does not exists, create containers for values
		if (round != rounds.length()) {
			JSONArray newMatches = new JSONArray();
			JSONObject newRound = new JSONObject();
			newRound.put("round", round);
			newRound.put("matches", newMatches);

			// Append created round
			this.rounds.put(newRound);
		}

		// Add teamA to match
		JSONObject newMatch = new JSONObject();
		// Add teamA to match
		newMatch.put("teamA", this.addMembers(teamA));
		// Add teamB to match
		newMatch.put("teamB", this.addMembers(teamB));

		// Add results to match
		newMatch.put("resultA", resultA);
		newMatch.put("resultB", resultB);

		// append matches to current round
		for (int r = 0; r < this.rounds.length(); r++) {
			if (this.rounds.getJSONObject(r).get("round").equals(round)) {
				this.rounds.getJSONObject(rounds.length() - 1).getJSONArray("matches").put(newMatch);
				}
			}
			//update file in the end.
			this.createUpdateJSONFile();
		}

	/*
	 * Create and update JSON file
	 */
	private void createUpdateJSONFile() {

		FileWriter file = null;
		try {
			file = new FileWriter("log_" + new SimpleDateFormat("MM-dd-yyyy").format(new Date()).toString() + ".json");
			file.write(this.rounds.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (file != null) {
					file.flush();
					file.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * add members and results to the JSONArray
	 * 
	 * @param team Team of Court
	 * @return Array entry inluding data of the team
	 */
	public JSONArray addMembers(String[] team) {

		JSONArray newTeam = new JSONArray();

		// Loop at Team, add members and points
		for (String name : team) {
			if (name != null) {
				Player member = calc.getPlayer(name);
				JSONObject newTeamMember = new JSONObject();
				newTeamMember.put("name", name);
				newTeamMember.put("points", member.getPoints());
				newTeam.put(newTeamMember);
			}
		}
		return newTeam;
	}

}
