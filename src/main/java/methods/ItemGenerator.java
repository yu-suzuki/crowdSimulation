package methods;

import element.Candidate;
import element.Item;
import element.Worker;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

/**
 * Created by ysuzuki on 2016/08/09.
 * ItemGenerator
 */
public class ItemGenerator {
    Map<Integer, Item> generateItems(int num, int variation) {
        Map<Integer, Item> items = new HashMap<>();
        try {
            SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
            for (int i = 0; i < num; i++) {
                items.put(i, new Item(i, number.nextInt(variation)));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return items;
    }


    List<Worker> generateWorkers(int num) {
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

    List<Candidate> getCandidates(Map<Integer, Item> trueItems) {
        List<Candidate> candidates = new ArrayList<>();
        for(Item i: trueItems.values()){
            candidates.add(new Candidate(i.getID(), 0));
        }
        return candidates;
    }
}
