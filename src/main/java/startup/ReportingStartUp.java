package startup;

import client.ReportFileWrite;
import api.ReportStoreApi;
import api.ReportingApiClient;
import client.SlowReportingApiClient;
import reports.ReportLoading;
import reports.ReportConfiguration;
import client.SimpleReportReadWrite;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReportingStartUp {

    private static final String CONFIGURE_PROPERTIES = "configure.properties";
    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private static final ReportConfiguration reportConfiguration = loadReportConfig();
    private static final ReportingApiClient apiClient = new SlowReportingApiClient();
    private static final ReportStoreApi storeApi = new ReportFileWrite(reportConfiguration.storeFilePath());
    private static final SimpleReportReadWrite simpleReportReadWrite = new SimpleReportReadWrite(apiClient, storeApi);

    public static void main(String[] args) throws IOException {
        ReportLoading reportApp = new ReportLoading(executorService, simpleReportReadWrite, reportConfiguration);
        reportApp.reportNameGenerating();
    }

    private static ReportConfiguration loadReportConfig() {
        try {
            Properties properties = new Properties();
            properties.load(ReportingStartUp.class.getClassLoader().getResourceAsStream(CONFIGURE_PROPERTIES));
            return new ReportConfiguration(properties);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
