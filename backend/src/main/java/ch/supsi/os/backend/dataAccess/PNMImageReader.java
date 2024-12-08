package ch.supsi.os.backend.dataAccess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PNMImageReader {

    private BufferedReader reader;

    public PNMImageReader(String filePath) throws IOException {
        File file = new File(filePath);

        if (!file.exists() || !file.canRead()) {
            throw new IOException("File cannot be read or does not exist: " + filePath);
        }

        this.reader = new BufferedReader(new FileReader(file));
    }

    public String getMagicNumber() throws IOException {
        return reader.readLine().trim();
    }

    public void close() throws IOException {
        if (reader != null) {
            reader.close();
        }
    }
}
