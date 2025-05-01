package app;

public class ScoreEntry {
    private final String initials;
    private final int score;

    public ScoreEntry(String initials, int score) {
        this.initials = initials;
        this.score = score;
    }

    public String getInitials() {
        return initials;
    }

    public int getScore() {
        return score;
    }
}
