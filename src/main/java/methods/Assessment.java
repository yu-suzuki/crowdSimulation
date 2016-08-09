package methods;

import element.*;

import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * Created by ysuzuki on 2016/08/09.
 */
public class Assessment implements Callable{
    int itemNum,select,workerNum;
    public Assessment(int itemNum, int select, int workerNum) {
        this.itemNum = itemNum;
        this.select = select;
        this.workerNum = workerNum;
    }


    @Override
    public EvaluationResult call() throws Exception {
        ItemGenerator ig = new ItemGenerator();
        List<Item> trueItems = ig.generateItems(itemNum,select);
        List<Worker> workers = ig.generateWorkers(workerNum);
        List<Result> results = new ArrayList<>();
        List<Vote> votes = new ArrayList<>();
        SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
        List<Candidate> candidates = ig.getCandidates(trueItems);
        int count = 0;
        while(true){

            Collections.sort(candidates, new CandidateComparator());
            Worker worker = workers.get(number.nextInt(workerNum));
            int rank = worker.rank(workers);
            int activeWorker = getAcriveWorker(workers);
            Candidate c = candidates.get(number.nextInt(candidates.size()));
            Item trueItem = trueItems.get(c.getID());
            int workerSelect = worker.select(trueItem, select);

            System.out.println(worker.getID()+":" + rank+"/"+activeWorker+":"+workerSelect);
            if(workerSelect == trueItem.getAns()){
                System.out.println("正解");
            } else {
                System.out.println("不正解");
            }
            if(count++ > 10) break;
        }

        double ratio = 1.0;
        return new EvaluationResult(itemNum,select,workerNum, ratio);
    }

    private int getAcriveWorker(List<Worker> workers) {
        int num = 1;
        for(Worker w: workers){
            if(w.getRatio() >= 0) num++;
        }
        return num;
    }

    private class CandidateComparator implements Comparator<Candidate> {
        @Override
        public int compare(Candidate o1, Candidate o2) {
            return Double.compare(o2.getPriority(), o1.getPriority());
        }
    }
}
