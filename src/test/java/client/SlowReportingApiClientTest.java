package client;

import api.ReportingApiClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SlowReportingApiClientTest {

    @Mock
    private Random random;

    @InjectMocks
    @Spy
    private SlowReportingApiClient slowReportingApiClient;

    @Test
    public void getReport() {
        ReportingApiClient.Report report = slowReportingApiClient.getReport("report");
        assertEquals(report.getName(), "report");
        assertFalse(report.getContent().isEmpty());
    }

    @Test
    public void getReportException() {
        when(random.nextInt(anyInt())).thenThrow(InterruptedException.class);
        ReportingApiClient.Report report = slowReportingApiClient.getReport("report");
        assertEquals(report.getName(), "report");
        assertFalse(report.getContent().isEmpty());
    }

}
