package element;

/**
 * Created by ysuzuki on 2016/08/09.
 * Candidate
 */
public class Candidate {
    private int id;
    private double priority;

    public Candidate(int id, double priority) {
        this.id = id;
        this.priority = priority;
    }

    public double getPriority() {
        return priority;
    }

    public int getID() {
        return id;
    }
}
