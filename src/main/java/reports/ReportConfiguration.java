package reports;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Properties;

public class ReportConfiguration {

    private Properties properties;

    public ReportConfiguration(Properties properties) {
        this.properties = properties;
    }

    ReportNameFormat generateNameFormat() {
        String reportAlias = properties.getProperty("reportNameAlias", "report");
        return new ReportNameFormat(reportAlias);
    }

    int reportNumber() {
        return Integer.parseInt(properties.getProperty("reportsCount", "1"));
    }

    public Path storeFilePath() {
        String storePath = properties.getProperty("storePath", "");
        String innerFolder = properties.getProperty("innerFolder", "");
        Path path = Paths.get(storePath);
        if (!(innerFolder == null || innerFolder.trim().isEmpty())) {
            path = path.resolve(innerFolder);
        }
        Path directory = Paths.get("");
        try {
            if (Files.isDirectory(path)){
                Files.walkFileTree(path, deleteFilesVisitor());
            }
            directory = Files.createDirectory(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return directory;
    }

    private SimpleFileVisitor<Path> deleteFilesVisitor() throws IOException {
        return new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        };
    }

}
