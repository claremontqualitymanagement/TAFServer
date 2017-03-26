package se.claremont.tafbackend.statistics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jordam on 2017-03-24.
 */
public class StatisticsCounter {
    List<TestCaseView> testCaseViews = new ArrayList<>();
    List<TestRunView> testRunViews = new ArrayList<>();

    public void addTestCaseView(){
        testCaseViews.add(new TestCaseView(new Date()));
    }

    public void addTestRunView(){
        testRunViews.add(new TestRunView(new Date()));
    }

    public int numberOfTestCaseViewsSince(Date date){
        int numberOfViewsSinceStatedDate = 0;
        for(TestCaseView testCaseView : testCaseViews){
            if(testCaseView.date.getTime() >= date.getTime()){
                numberOfViewsSinceStatedDate++;
            }
        }
        return numberOfViewsSinceStatedDate;
    }

    public int numberOfRegisteredTestRunViews(){
        return testRunViews.size();
    }

    public int numberOfRegisteredTestCaseViews(){
        return testCaseViews.size();
    }

    public int numberOfTestRunViewsSince(Date date){
        int numberOfViewsSinceStatedDate = 0;
        for(TestRunView testRunView : testRunViews){
            if(testRunView.date.getTime() >= date.getTime()){
                numberOfViewsSinceStatedDate++;
            }
        }
        return numberOfViewsSinceStatedDate;
    }

    class TestCaseView {
        Date date;

        public TestCaseView(Date date){
            this.date = date;
        }
    }

    class TestRunView{
        Date date;

        public TestRunView(Date date){
            this.date = date;
        }
    }
}
