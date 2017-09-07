import api.ReportingApiClient;
import client.SlowReportingApiClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(MockitoJUnitRunner.class)
public class SlowReportingApiClientTest {

    @InjectMocks
    private SlowReportingApiClient reportingApiClient;

    @Test
    public void getReportTest() {
        LocalDateTime startDate = LocalDateTime.now();
        List<ReportingApiClient.Report> reports = Stream
                .generate(() -> reportingApiClient.getReport("report"))
                .limit(10)
                .collect(toList());
        LocalDateTime endDate = LocalDateTime.now();
        Duration between = Duration.between(startDate, endDate);
        assertEquals(10, reports.size());
        assertFalse(between.isZero());
    }

}
