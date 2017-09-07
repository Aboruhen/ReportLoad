package reports;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ReportNameFormatTest {

    @Test
    public void reportName() throws Exception {
        ReportNameFormat nameFormat = new ReportNameFormat("aaa");
        String actual = nameFormat.reportName(2);
        assertEquals("aaa_2", actual);

    }

}
