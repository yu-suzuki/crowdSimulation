package element;

import java.util.List;

/**
 * Created by ysuzuki on 2016/08/09.
 * Result
 */
public class Result {
    private List<Integer> answers;
    private int itemID;

    public Result(int itemID, List<Integer> answers) {
        this.itemID = itemID;
        this.answers = answers;
    }

    public List<Integer> getAnswers() {
        return answers;
    }
}
