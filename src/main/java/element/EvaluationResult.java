package element;

/**
 * Created by ysuzuki on 2016/08/09.
 * EvaluationResult
 */
public class EvaluationResult {
    private int voteCount;
    private double ratio;
    private Condition condition;
    public EvaluationResult(int itemNum, int select, int workerNum, double ratio, int voteCount) {
        this.condition = new Condition(itemNum, select, workerNum);
        this.ratio = ratio;
        this.voteCount = voteCount;
    }

    public double getRatio() {
        return ratio;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public Condition getCondition() {
        return condition;
    }
}
