package multi.screen.d4t4_assessment;

public class Article {
    private Source source;
    private String author;
    private String title;
    private String description;
    private String url;


    public Article(Source source, String author, String title, String description, String url,
                   String urlToImage, String publishedAt, String content) {
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }


}


