package client;

import api.ReportStoreApi;
import api.ReportingApiClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SimpleReportReadWriteTest {

    @Mock
    private ReportingApiClient apiClient;
    @Mock
    private ReportStoreApi storeApi;

    @InjectMocks
    private SimpleReportReadWrite reportReadWrite;

    @Test
    public void readWrite() {
        ReportingApiClient.Report report = mock(ReportingApiClient.Report.class);
        when(apiClient.getReport("report")).thenReturn(report);
        reportReadWrite.readWrite("report");
        verify(apiClient).getReport("report");
        verify(storeApi).store(report);
    }

}
