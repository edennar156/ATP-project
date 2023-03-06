package IO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MyDecompressorInputStream extends InputStream {
    private InputStream in;

    public MyDecompressorInputStream(InputStream i) {
        this.in = i;
    }

    // Same functionality as described in MyCompressorOutputStream class, Compression function.
    public static int decompression(byte[] buffer, byte[] targetArray) throws IllegalArgumentException {
        List<Byte> formatInformationArray = new ArrayList<>();

        for (int i = 0; i < 12; i++)
            formatInformationArray.add(buffer[i]);

        // Row Col sizes.
        int size =
                (formatInformationArray.get(0) + formatInformationArray.get(1) * 127) *
                        (formatInformationArray.get(2) + formatInformationArray.get(3) * 127) +
                        12;

        if (targetArray == null || targetArray.length < size)
            throw new IllegalArgumentException();

        for (int i = 12; i < buffer.length; i++) {
            int temp;
            if (buffer[i] > 0){
                temp = buffer[i];
            }
            else {
                temp = buffer[i] + 256;
            }
            for (int j = 0; j < 8; j++) {
                formatInformationArray.add((byte) (temp % 2));
                temp /= 2;
            }
        }
        for (int i = 0; i < size; i++) {
            targetArray[i] = formatInformationArray.get(i);
        }
        return size;
    }

    @Override
    public int read() throws IOException {
        return this.in.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        return decompression(in.readAllBytes(), b);
    }

    @Override
    public void close() throws IOException {
        this.in.close();
        super.close();
    }
}
