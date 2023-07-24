package mada.android.models;

import java.util.ArrayList;
import java.util.List;

public class Destination {
    private String _id;
    private String title;
    private String description;
    private String content;
    private String image;
    private String video;
    private String localisation;
    public Destination(String _id, String title, String description) {
        this._id = _id;
        this.title = title;
        this.description = description;
    }
    public static List<Destination> generateSampleDestinations() {
        List<Destination> destinations = new ArrayList<>();

        // Generate 5 rows with different values
        for (int i = 1; i <= 5; i++) {
            String id = "ID_" + i;
            String title = "Title " + i;
            String description = "Description " + i;

            Destination destination = new Destination(id, title, description);
            destinations.add(destination);
        }

        return destinations;
    }
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }
}
