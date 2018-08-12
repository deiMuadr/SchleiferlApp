import org.json.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.FileWriter;
import java.io.IOException;

public class Log {

    private JSONArray rounds;

    public Log() {
        this.rounds = new JSONArray();
    }

    public void addLog(int round, String[] teamA, String[] teamB, int resultA, int resultB) {
        
    	// Team A contains of two members
    	JSONArray newTeamA = new JSONArray();
        
        // Loop at Team A and add members
        for (String name : teamA) {
        	if (name != null) {
            	JSONObject newTeamMember = new JSONObject();
            	newTeamMember.put("name", name);
                newTeamA.put(newTeamMember);
        	}
        }
        // Team B contains of two members
    	JSONArray newTeamB = new JSONArray();
        
        // Loop at Team B and add members
        for (String name : teamB) {
        	if (name != null) {
            	JSONObject newTeamMember = new JSONObject();
            	newTeamMember.put("name", name);
                newTeamB.put(newTeamMember);
        	}
        }
        
        // New match
    	JSONObject newMatch = new JSONObject();
    	newMatch.put("teamA", teamA);
    	newMatch.put("teamB", teamB);
    	newMatch.put("resultA", resultA);
    	newMatch.put("resultB", resultB);
    	
    	Boolean exists = false;
    	for(int r = 0; r < this.rounds.length(); r++) {
    		if (this.rounds.getJSONObject(r).get("round").equals(round)) {
    			exists = true;
    			this.rounds.getJSONObject(r).getJSONArray("matches").put(newMatch);
    			this.createUpdateJSONFile();
    		}
    	}
    	
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
    
    private void createUpdateJSONFile() {
    	try (FileWriter file = new FileWriter("log_" + new SimpleDateFormat("MM-dd-yyyy").format(new Date()).toString() + ".json")) {
			file.write(this.rounds.toString());
			file.flush();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
    }

}
