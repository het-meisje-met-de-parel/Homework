package reporting;

import common.business.exception.checked.ReportException;

public interface ReportService {
    void exportData() throws ReportException;
}
