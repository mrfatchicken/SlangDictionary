public class searchHistory {
    private String date;
    private String type;
    private String content;

    public searchHistory(String date, String type, String content) {
        this.date = date;
        this.type = type;
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}
