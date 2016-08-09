package element;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

/**
 * Created by ysuzuki on 2016/08/09.
 */
public class Worker {
    int id;
    double probability;
    private double ratio;

    public Worker(int id, double probability) {
        this.id = id;
        this.probability = probability;
        this.ratio  = -1;
    }

    public double getRatio() {
        return ratio;
    }

    public int getID() {
        return id;
    }

    public double getProbability() {
        return probability;
    }

    public int rank(List<Worker> workers) {
        int rank = 1;
        for(Worker w: workers){
            if (w.getRatio() >= 0 && w.getRatio() > this.ratio){
                rank++;
            }
        }
        return rank;
    }

    public int select(Item trueItem, int select) {
        int selectItem = -1;
        try {
            SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
            if(this.probability > number.nextDouble()){
                selectItem = trueItem.getAns();
            } else {
                do {
                    selectItem = number.nextInt(select);
                }while(selectItem == trueItem.getAns());
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return selectItem;
    }
}
