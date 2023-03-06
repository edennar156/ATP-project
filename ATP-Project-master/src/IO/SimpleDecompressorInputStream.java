package IO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SimpleDecompressorInputStream extends InputStream {
    private InputStream in;
    private static final int BASE = 127;

    public SimpleDecompressorInputStream(InputStream inputStream) {
        this.in = inputStream;
    }

    public static int decompression(byte[] s, byte[] t) throws IllegalArgumentException {
        ArrayList<Byte> byteArrayList = new ArrayList<>();
        byte q = 1;
        for (int i = 0; i < 12; i++)
            byteArrayList.add(s[i]);

        int expectedSize = (byteArrayList.get(0) + byteArrayList.get(1) * BASE) *
                (byteArrayList.get(2) + byteArrayList.get(3) * BASE) + 12;
        if (t.length < expectedSize)
            throw new IllegalArgumentException();

        for (int i = 12; i < s.length; i++) {
            int value = byteConversionToInt(s[i]);
            for (int j = 0; j < value; j++)
                byteArrayList.add(q);
            if (q == 1)
                q = (byte) 0;
            else
                q = (byte) 1;
        }

        for (int i = 0; i < expectedSize; i++)
            t[i] = byteArrayList.get(i);

        return expectedSize;
    }

    public static int byteConversionToInt(byte number) throws IllegalArgumentException {
        if (number >= 0)
            return number;
        else
            return number + 256;
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
