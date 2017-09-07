package reports;

import static java.lang.String.format;

class ReportNameFormat {

    private String reportAlias;

    ReportNameFormat(String reportAlias) {
        this.reportAlias = reportAlias;
    }

    String reportName(int index){
        return format("%s_%d", this.reportAlias, index);
    }

}
