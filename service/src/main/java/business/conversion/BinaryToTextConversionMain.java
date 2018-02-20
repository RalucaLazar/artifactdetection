package business.conversion;

import entity.Configuration;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BinaryToTextConversionMain {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(8);
        File folder = new File(Configuration.BINARY_INPUT_FILES);
        Arrays.stream(folder.listFiles())
                .filter(fileEntry -> !fileEntry.isDirectory())
                .forEach(fileEntry ->
                {
                    executor.submit(new BinaryToTextConversionThread(fileEntry.getAbsolutePath(),
                            Configuration.TXT_INPUT_FILES + "\\" + BinaryToTextConversion.changeFileExtension(fileEntry.getName())));
                });

        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
