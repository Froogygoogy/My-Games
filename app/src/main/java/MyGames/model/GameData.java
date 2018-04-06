package MyGames.model;

/**
 * Created by jcamen on 17/02/18.
 */

public class GameData {
    public enum MyGameState {
        WANTING(1), PLAYING(2), PLAYED(3);

        private int value;

        MyGameState(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static MyGameState fromInt(int v) {
            for (MyGameState state: MyGameState.values()) {
                if (state.getValue() == v)
                    return state;
            }
            return null;
        }
    }

    public static final String NO_VALUE = "N/A";
    public static final int NO_NUMBER = -1;
    public static final String NO_COMMENT = "Not commented yet";

    private int id;
    private String name;
    private String summary;
    private double totalRating;
    private double rating;
    private String publishers;
    private String genres;
    private long releaseDate;
    private String platforms;
    private String cover;
    private String pegi;
    private MyGameState state;
    private String comment;

    public GameData(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public double getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(double totalRating) {
        this.totalRating = totalRating;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPublishers() {
        return publishers;
    }

    public void setPublishers(String publishers) {
        this.publishers = publishers;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public long getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(long releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPlatforms() {
        return platforms;
    }

    public void setPlatforms(String platforms) {
        this.platforms = platforms;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getPegi() {
        return pegi;
    }

    public void setPegi(String pegi) {
        this.pegi = pegi;
    }

    public MyGameState getState() {
        return state;
    }

    public void setState(MyGameState state) {
        this.state = state;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public GameData copy() {
        GameData other = new GameData(id);
        other.name = name;
        other.summary = summary;
        other.totalRating = totalRating;
        other.rating = rating;
        other.publishers = publishers;
        other.releaseDate = releaseDate;
        other.cover = cover;
        other.pegi = pegi;
        other.state = state;
        other.comment = comment;
        return other;
    }
}
