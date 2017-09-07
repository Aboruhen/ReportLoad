package client;

import api.ReportStoreApi;
import api.ReportingApiClient;
import com.jcabi.aspects.RetryOnFailure;

import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.HashSet;
import java.util.Set;

/**
 * This is a simple implementation that store report into file
 */
public final class ReportFileWrite implements ReportStoreApi {

    private final Path path;
    private final Set<StandardOpenOption> storeOptions;
    private final Charset charset;

    public ReportFileWrite(Path path) {
        this.path = path;
        this.storeOptions = storeOptions();
        this.charset = Charset.forName("UTF-8");
    }

    @RetryOnFailure()
    @Override
    public void store(ReportingApiClient.Report report) {
        String content = report.getContent();
        if (content == null) {
            return;
        }
        Path reportPath = this.path.resolve(report.getName());
        try (FileChannel fileChannel = FileChannel.open(reportPath, this.storeOptions)) {
            ByteBuffer byteBuffer = ByteBuffer.wrap(content.getBytes(this.charset));
            fileChannel.write(byteBuffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<StandardOpenOption> storeOptions() {
        Set<StandardOpenOption> options = new HashSet<>();
        options.add(StandardOpenOption.CREATE);
        options.add(StandardOpenOption.WRITE);
        options.add(StandardOpenOption.TRUNCATE_EXISTING);
        return options;
    }

}
