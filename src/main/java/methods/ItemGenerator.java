package methods;

import element.Candidate;
import element.Item;
import element.Worker;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ysuzuki on 2016/08/09.
 */
public class ItemGenerator {
    public List<Item> generateItems(int num, int variation) {
        List<Item> items = new ArrayList<>();
        try {
            SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
            for (int i = 0; i < num; i++) {
                items.add(new Item(i, number.nextInt(variation)));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return items;
    }


    public List<Worker> generateWorkers(int num) {
        List<Worker> workers = new ArrayList<>();
        try {
            SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
            for (int i = 0; i < num; i++) {
                workers.add(new Worker(i, number.nextDouble() /2 + 0.5));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return workers;
    }

    public List<Candidate> getCandidates(List<Item> trueItems) {
        List<Candidate> candidates = new ArrayList<>();
        for(Item i: trueItems){
            candidates.add(new Candidate(i.getID(), 0));
        }
        return candidates;
    }
}
