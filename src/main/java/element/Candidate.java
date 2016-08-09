package element;

/**
 * Created by ysuzuki on 2016/08/09.
 */
public class Candidate {
    int id;
    double priority;
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
