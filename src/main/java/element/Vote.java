package element;

/**
 * Created by ysuzuki on 2016/08/09.
 * Element
 */
public class Vote {
    private int itemID, workerID, select;
    private boolean success;

    public Vote(int itemID, int workerID, int select, boolean success) {
        this.itemID = itemID;
        this.workerID = workerID;
        this.select = select;
        this.success = success;
    }

    public int getItemID() {
        return itemID;
    }

    public int getSelect() {
        return select;
    }

    public int getWorkerID() {
        return workerID;
    }
}
