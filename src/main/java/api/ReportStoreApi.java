package api;

/**
 * This interface represents API for report storing
 */
public interface ReportStoreApi {

    /**
     * Store a report
     * @param report to store
     */
    void store(ReportingApiClient.Report report);

}
