package sg.edu.rp.c346.id20008460.demodatabase;

public class task {

    private int id;
    private String description;
    private String date;

    public task(int id, String description, String date) {
        this.id = id;
        this.description = description;
        this.date = date;
    }

    public int getId() { return id; }

    public String getDescription() { return description; }

    public String getDate() { return date;}

    public String toString() {
        return id + "\n" + description + "\n" + date;
    }

}
