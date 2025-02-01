package lv.nixx.poc.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.StandardWatchEventKinds.*;

class FileWatcherSample {

    static Logger log = LoggerFactory.getLogger(FileWatcherSample.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        watchDirectory(Paths.get("./exchange"));
    }

    private static void watchDirectory(Path directoryPath) throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();

        directoryPath.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);

        log.info("Watching directory: {}", directoryPath);

        while (true) {
            WatchKey key = watchService.take();

            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();

                if (kind == StandardWatchEventKinds.OVERFLOW) {
                    continue;
                }

                @SuppressWarnings("unchecked")
                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                Path fileName = ev.context();

                log.info("Event: {} on file: {}", kind, fileName);

                Path fullPath = directoryPath.resolve(fileName);
                printFileAttributes(fullPath);
            }

            boolean valid = key.reset();
            if (!valid) {
                log.info("WatchKey is no longer valid!");
                break;
            }
        }
    }

    public static void printFileAttributes(Path filePath) throws IOException {
        if (Files.exists(filePath)) {
            var attrs = Files.readAttributes(filePath, BasicFileAttributes.class);
            log.info("File [{}] size [{}] bytes creation date [{}]", filePath, Files.size(filePath), attrs.creationTime());
        }
    }

}
