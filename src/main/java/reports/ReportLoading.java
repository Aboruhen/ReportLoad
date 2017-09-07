package reports;

import client.SimpleReportReadWrite;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

public class ReportLoading {

    private final int reportNumber;
    private ReportSteward reportSteward;
    private CountDownLatch countDownLatch;
    private ReportNameFormat reportNameFormat;

    public ReportLoading(ExecutorService executorService, SimpleReportReadWrite simpleReportReadWrite,
                         ReportConfiguration reportConfiguration) {
        this.reportNumber = reportConfiguration.reportNumber();
        this.reportNameFormat = reportConfiguration.generateNameFormat();

        this.countDownLatch = new CountDownLatch(reportNumber);
        reportSteward = new ReportSteward(executorService, simpleReportReadWrite, this.countDownLatch);
    }

    public void reportNameGenerating() {
        Stream
                .iterate(1, i -> i + 1)
                .map(this.reportNameFormat::reportName)
                .limit(this.reportNumber)
                .forEach(this.reportSteward::loadReport);
        //noinspection StatementWithEmptyBody
        do {
            //wait for reports load finish;
        } while (countDownLatch.getCount() > 0);
    }

}
