public class Results {
    private Boolean playerResult = false;
    private int totalWins = 0;

    public void setPlayerResult(Boolean result) {
        this.playerResult = result;
    }

    public void setTotalWins(int totalWins) {
        this.totalWins = totalWins;
    }

    public String getPlayerResult() {
        if (playerResult == true) {
            return ("Player Wins!");
        } else {
            return ("Player Lost!");
        }
    }

    public int getTotalWins() {
        return this.totalWins;
    }
}
