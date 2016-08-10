package element;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ysuzuki on 2016/08/09.
 * Worker
 */
public class Worker {
    private int id;
    private double probability;
    private double ratio;
    private List<Integer> items;

    public Worker(int id, double probability) {
        this.id = id;
        this.probability = probability;
        this.ratio  = -1;
        this.items = new ArrayList<>();
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

    public void addItem(int itemID) {
        this.items.add(itemID);
    }

    public boolean containItem(int id) {
        return this.items.contains(id);
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }
}
