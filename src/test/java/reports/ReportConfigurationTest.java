package reports;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReportConfigurationTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Mock
    private Properties properties;

    @InjectMocks
    private ReportConfiguration reportConfiguration;

    @Test
    public void generateNameFormat() {
        when(properties.getProperty("reportNameAlias", "report")).thenReturn("rep");
        ReportNameFormat reportNameFormat = reportConfiguration.generateNameFormat();
        assertNotNull(reportNameFormat);
        String reportName = reportNameFormat.reportName(1);
        assertEquals("rep_1", reportName);
        verify(properties, never()).getProperty("reportNameAlias");
        verify(properties).getProperty("reportNameAlias", "report");
    }

    @Test
    public void reportNumber() {
        int actualReports = 3;
        when(properties.getProperty("reportsCount", "1")).thenReturn(String.valueOf(actualReports));
        int reportNumber = reportConfiguration.reportNumber();
        assertEquals(actualReports, reportNumber);
        verify(properties, never()).getProperty("reportsCount");
        verify(properties).getProperty("reportsCount", "1");
    }

    @Test
    public void storeFilePath() throws IOException {
        File test = temporaryFolder.newFolder("test/testInner");
        when(properties.getProperty("storePath", "")).thenReturn("test");
        when(properties.getProperty("innerFolder", "")).thenReturn("testInner");
        Path path = reportConfiguration.storeFilePath();

        verify(properties, never()).getProperty("storePath");
        verify(properties, never()).getProperty("innerFolder");
        verify(properties).getProperty("storePath", "");
        verify(properties).getProperty("innerFolder", "");
    }

}
