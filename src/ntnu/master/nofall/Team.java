package ntnu.master.nofall;

public class Team {
	private String teamName;
	private int teamWins;

	public Team(String name, int wins) {
		teamName = name;
		teamWins = wins;
	}

	public String getTeamName() {
		return teamName;
	}

	public int getTeamWins() {
		return teamWins;
	}
}
