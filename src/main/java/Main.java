import element.EvaluationResult;
import element.Item;
import element.Result;
import element.Worker;
import methods.Assessment;
import methods.ItemGenerator;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by ysuzuki on 2016/08/09.
 */
public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Future<EvaluationResult>> futures = new ArrayList<>();
        Assessment assess = new Assessment(1000,2,100);
        futures.add(executor.submit(assess));
        executor.shutdown();

        for(Future<EvaluationResult> future: futures){
            try {
                EvaluationResult er = future.get();
                System.out.println(er.getRatio());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

    }
}
