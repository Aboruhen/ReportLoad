package reports;

import client.SimpleReportReadWrite;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

public class ReportSteward {

    private ExecutorService executorService;
    private SimpleReportReadWrite simpleReportReadWrite;
    private CountDownLatch countDownLatch;

    public ReportSteward(ExecutorService executorService, SimpleReportReadWrite simpleReportReadWrite, CountDownLatch countDownLatch) {
        this.executorService = executorService;
        this.simpleReportReadWrite = simpleReportReadWrite;
        this.countDownLatch = countDownLatch;
    }

    public void loadReport(String reportName) {
        executorService.execute(() -> {
            try {
                simpleReportReadWrite.readWrite(reportName);
            } finally {
                countDownLatch.countDown();
            }
        });
    }

}
