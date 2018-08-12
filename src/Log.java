import org.json.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.FileWriter;
import java.io.IOException;

public class Log {
	
	private Calculations 		calc = Calculations.getInstance();
    private JSONArray 			rounds;

    public Log() {
        this.rounds = new JSONArray();
    }

    /*
     * Add log entry
     * 
     * @Int 		round
     * @String[]	teamA
     * @String[]	teamB
     * @Int			resultA
     * @Int			resultB
     */
    public void addLog(int round, String[] teamA, String[] teamB, int resultA, int resultB) {
        
    	// A team contains of two members
    	JSONArray newTeam = new JSONArray();
        
        // Loop at Team A and add members
        for (String name : teamA) {
        	if (name != null) {
        		Player member = calc.getPlayer(name);
            	JSONObject newTeamMember = new JSONObject();
            	newTeamMember.put("name", name);
            	newTeamMember.put("points", member.getPoints());
                newTeam.put(newTeamMember);
        	}
        }
        
        // Add team to match
    	JSONObject newMatch = new JSONObject();
    	newMatch.put("teamA", newTeam);
        
        // Clear team
    	newTeam = new JSONArray();
        
        // Loop at Team B and add members
        for (String name : teamB) {
        	if (name != null) {
        		Player member = calc.getPlayer(name);
            	JSONObject newTeamMember = new JSONObject();
            	newTeamMember.put("name", name);
            	newTeamMember.put("points", member.getPoints());
                newTeam.put(newTeamMember);
        	}
        }
        
        // Add team to match
    	newMatch.put("teamB", newTeam);
    	
    	// Add results to match
    	newMatch.put("resultA", resultA);
    	newMatch.put("resultB", resultB);
    	
    	// If round exists - append match
    	Boolean exists = false;
    	for(int r = 0; r < this.rounds.length(); r++) {
    		if (this.rounds.getJSONObject(r).get("round").equals(round)) {
    			exists = true;
    			this.rounds.getJSONObject(r).getJSONArray("matches").put(newMatch);
    			this.createUpdateJSONFile();
    		}
    	}
    	
    	// Else - create round
    	if (!exists) {
    		
        	// Add match to matches
        	JSONArray newMatches = new JSONArray();
        	newMatches.put(newMatch);
        	
        	// New round
        	JSONObject newRound = new JSONObject();
        	newRound.put("round", round);
        	newRound.put("matches", newMatches);
        	
        	// Append to existing round or create new round
            this.rounds.put(newRound);
    	}
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
		}finally {
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

}
