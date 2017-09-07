package client;

import api.ReportReadWrite;
import api.ReportStoreApi;
import api.ReportingApiClient;

/**
 * Simple implementation of report loading
 */
public class SimpleReportReadWrite implements ReportReadWrite {

    private ReportingApiClient apiClient;
    private ReportStoreApi storeApi;

    public SimpleReportReadWrite(ReportingApiClient apiClient, ReportStoreApi storeApi) {
        this.apiClient = apiClient;
        this.storeApi = storeApi;
    }

    @Override
    public void readWrite(String reportName) {
        ReportingApiClient.Report report = apiClient.getReport(reportName);
        storeApi.store(report);
    }

}
