package client;

import api.ReportingApiClient;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReportFileWriteTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @InjectMocks
    private ReportFileWrite reportFileWrite;

    @Test
    public void storeNoContentTest(){
        ReportingApiClient.Report report = mock(ReportingApiClient.Report.class);
        when(report.getContent()).thenReturn(null);
        reportFileWrite.store(report);
        //noinspection ResultOfMethodCallIgnored
        verify(report).getContent();
    }

    @Test
    public void storeTest() throws IOException {
        ReportingApiClient.Report report = mock(ReportingApiClient.Report.class);
        File test = temporaryFolder.newFile("test");
        Whitebox.setInternalState(reportFileWrite, "path", Paths.get(test.toURI()));
        String content = "content";
        when(report.getContent()).thenReturn(content);
        reportFileWrite.store(report);
        //noinspection ResultOfMethodCallIgnored
        verify(report).getContent();
    }
    @Test
    public void storeOptionsTest(){
        Path path = mock(Path.class);
        ReportFileWrite reportFileWrite = new ReportFileWrite(path);
        Set<StandardOpenOption> storeOptions = (Set<StandardOpenOption>)Whitebox.getInternalState(reportFileWrite, "storeOptions");
        assertEquals(3, storeOptions.size());
        assertTrue(storeOptions.contains(StandardOpenOption.CREATE));
        assertTrue(storeOptions.contains(StandardOpenOption.WRITE));
        assertTrue(storeOptions.contains(StandardOpenOption.TRUNCATE_EXISTING));
        Charset charset = (Charset) Whitebox.getInternalState(reportFileWrite, "charset");
        assertEquals(Charset.forName("UTF-8"), charset);
    }

}
