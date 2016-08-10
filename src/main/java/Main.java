import element.*;
import methods.Assessment;
import methods.ItemGenerator;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by ysuzuki on 2016/08/09.
 * Main
 */
public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(8);
        List<Future<EvaluationResult>> futures = new ArrayList<>();
        Map<Condition, List<EvaluationResult>> results = new HashMap<>();
        for(int i = 0; i < 1000; i++) {
            Assessment a = new Assessment(100,2,5,2);
            Future<EvaluationResult> f = executor.submit(a);
            futures.add(f);
        }
        executor.shutdown();

        for(Future<EvaluationResult> future: futures){
            try {
                EvaluationResult er = future.get();
                Condition condition = er.getCondition();
                results.putIfAbsent(condition, new ArrayList<>());
                List<EvaluationResult> result = results.get(condition);
                result.add(er);
                results.put(condition, result);
                System.out.println(er.getRatio()+"/"+er.getVoteCount());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        for(Map.Entry<Condition, List<EvaluationResult>> e: results.entrySet()){

            double accuracySum = 0;
            double voteCountSum = 0;
            for(EvaluationResult er: e.getValue()){
                accuracySum += er.getRatio();
                voteCountSum += er.getVoteCount();
            }

            System.out.println(e.getKey().getItemNum()+":"+e.getValue().size()+":"+accuracySum/(double)e.getValue().size());
        }

    }
}
