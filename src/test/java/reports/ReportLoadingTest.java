package reports;

import client.SimpleReportReadWrite;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReportLoadingTest {

    @Mock
    private ReportConfiguration reportConfiguration;

    @Mock
    private ReportNameFormat reportNameFormat;

    @Test
    public void reportNameGeneratingTest() {
        when(reportNameFormat.reportName(anyInt())).thenReturn("rrr");
        when(reportConfiguration.generateNameFormat()).thenReturn(reportNameFormat);
        when(reportConfiguration.reportNumber()).thenReturn(3);
        SimpleReportReadWrite simpleReportReadWrite = mock(SimpleReportReadWrite.class);
        doNothing().when(simpleReportReadWrite).readWrite(anyString());
        ReportLoading reportLoading = new ReportLoading(Executors.newSingleThreadExecutor(),
                simpleReportReadWrite, reportConfiguration);

        reportLoading.reportNameGenerating();

        verify(reportNameFormat, times(3)).reportName(anyInt());
        verify(reportConfiguration).reportNumber();
    }

}
