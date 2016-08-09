package element;

/**
 * Created by ysuzuki on 2016/08/09.
 */
public class EvaluationResult {
    int itemNum, select, workerNum;
    double ratio;
    public EvaluationResult(int itemNum, int select, int workerNum, double ratio) {
        this.itemNum = itemNum;
        this.select = select;
        this.workerNum = workerNum;
        this.ratio = ratio;
    }

    public double getRatio() {
        return ratio;
    }
}
