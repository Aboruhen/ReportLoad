package api;

/**
 * This interface represents API read and store report
 */
public interface ReportReadWrite {

    /**
     * Provide report load and store
     * @param reportName report to load and store
     */
    void readWrite(String reportName);

}
