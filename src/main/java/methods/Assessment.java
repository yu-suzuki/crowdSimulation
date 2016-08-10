package methods;

import element.*;

import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * Created by ysuzuki on 2016/08/09.
 * Assessment
 */
public class Assessment implements Callable{
    private int itemNum,select,workerNum,maxWorkerPerItem;
    private List<Vote> votes;
    private Map<Integer, Result> results;
    private Map<Integer, Item> trueItems;
    private List<Worker> workers;
    private List<Candidate> candidates;

    public Assessment(int itemNum, int select, int workerNum, int maxWorkerPerItem) {
        this.itemNum = itemNum;
        this.select = select;
        this.workerNum = workerNum;
        this.maxWorkerPerItem = maxWorkerPerItem;
        votes = new ArrayList<>();
        results = new HashMap<>();
        ItemGenerator ig = new ItemGenerator();
        trueItems = ig.generateItems(itemNum,select);
        workers = ig.generateWorkers(workerNum);
        candidates = ig.getCandidates(trueItems);
    }


    @Override
    public EvaluationResult call() throws Exception {

        SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
        int count = 0;
        while(true){
            Collections.sort(candidates, new CandidateComparator());
            Worker worker = workers.get(number.nextInt(workerNum));
            int rank = worker.rank(workers);
            int activeWorker = getAcriveWorker(workers);
            Candidate c = candidates.get(0);
            if(worker.containItem(c.getID())) continue;
            Item trueItem = trueItems.get(c.getID());
            int workerSelect = worker.select(trueItem, select);
            worker.addItem(c.getID());
            votes.add(new Vote(c.getID(), worker.getID(), workerSelect, workerSelect == trueItem.getAns()));

            List<Vote> subVotes = subVoteItem(votes, c.getID());
            results.put(c.getID(), new Result(c.getID(), answers(subVotes)));
            //System.out.println(c.getID()+":"+Arrays.toString(answers(subVotes).toArray()));

            for(Worker w: workers){
                if(w.containItem(c.getID())){
                    calculateWorkerRatio(w);
                }
            }

            if(subVotes.size() > maxWorkerPerItem){
                candidates.remove(c);
            }

            //System.out.println("W"+c.getID()+":"+worker.getID()+":" + rank+"/"+activeWorker+":"+workerSelect+":"+trueItem.getAns());
            /*
            if(workerSelect == trueItem.getAns()){
                System.out.println("Correct");
            } else {
                System.out.println("Incorrect");
            }
            */
            //System.out.println("------");
            if(candidates.isEmpty()) break;
        }

        double ratio = evalateRatio();
        int voteCount = votes.size();
        return new EvaluationResult(itemNum,select,workerNum, ratio, voteCount);
    }

    private double evalateRatio() {
        double sum = 0;
        for(Map.Entry<Integer, Result> r: results.entrySet()){
            int itemID = r.getKey();
            int correctAnswer = trueItems.get(itemID).getAns();
            List<Integer> answers = r.getValue().getAnswers();
            if(answers.contains(correctAnswer)){
                sum += 1 / (double) answers.size();
            }
        }
        return sum / (double) results.size();
    }

    private void calculateWorkerRatio(Worker w) {
        double majority = 0;
        List<Vote> subVoteWorker = subVoteWorker(votes, w.getID());
        for(Vote v: subVoteWorker){
            Result r = results.get(v.getItemID());
            if(r.getAnswers().contains(v.getSelect())){
                majority += 1.0 / (double) r.getAnswers().size();
                //System.out.println(r.getAnswers().size()+":"+majority+":"+Arrays.toString(r.getAnswers().toArray()));
            }
        }
        w.setRatio(majority/(double) subVoteWorker.size());
        //System.out.println(w.getID()+":"+majority+"/"+subVoteWorker.size());
    }

    private List<Vote> subVoteWorker(List<Vote> votes, int wid) {
        List<Vote> subVote = new ArrayList<>();
        for(Vote v: votes){
            if(v.getWorkerID() == wid) subVote.add(v);
        }
        return subVote;
    }

    private List<Integer> answers(List<Vote> votes) {
        int[] bins = new int[select];
        for(Vote v: votes){
            bins[v.getSelect()]++;
        }
        int max = 0;
        for(int bin: bins){
            if(bin > max) max = bin;
        }
        List<Integer> result = new ArrayList<>();
        for(int i = 0; i < bins.length; i++){
            if(bins[i] == max) result.add(i);
        }
        return result;
    }

    private List<Vote> subVoteItem(List<Vote> votes, int itemID) {
        List<Vote> subVote = new ArrayList<>();
        for(Vote v: votes){
            if(v.getItemID() == itemID) subVote.add(v);
        }
        return subVote;
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
