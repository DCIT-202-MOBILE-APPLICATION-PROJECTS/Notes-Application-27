package notes.digerati.scribble.data;

import com.google.firebase.Timestamp;

public class NoteModel {

    private String title;
    private int color;
    private String content;
    Timestamp timestamp;

//    public NoteModel(String title, int color) {
//        this.title = title;
//        this.color = color;
//    }

    public String getTitle() {
        return title;
    }

    public int getColor() {
        return color;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
