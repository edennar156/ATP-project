package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class SimpleCompressorOutputStream extends OutputStream {
    private OutputStream out;
    private static final int byteMaximumValue = 255;
    private static final int TRAN = 0;
    private static final int WALL =1;


    public SimpleCompressorOutputStream(OutputStream outputStream) {
        this.out = outputStream;
    }

    public static byte[] compression(byte[] bytesArray) {
        ArrayList<Byte> byteArrayList = new ArrayList<>();
        int p = 0, q = 1;
        for (int i = 0; i < 12; i++) {
            byteArrayList.add(bytesArray[i]);
        }
        for (int i = 12; i < bytesArray.length; i++) {
            if (bytesArray[i] == q)
                p++;
            else {
                accumulation(byteArrayList, p);
                if (q == WALL)
                    q = 0;
                else
                    q = 1;
                p=1;
            }
        }
        accumulation(byteArrayList, p);
        byte[] compressedMaze = new byte[byteArrayList.size()];
        for (int i=0; i< compressedMaze.length; i++){
            compressedMaze[i] = byteArrayList.get(i);
        }

        return compressedMaze;
    }

    // Steps described in work description.
    public static void accumulation(ArrayList<Byte> byteArray, int numberToAdd) {
        while (numberToAdd >= 0) {
            if (numberToAdd > byteMaximumValue) {
                // As described in work description:
                byteArray.add(intConversionToByte(byteMaximumValue));
                byteArray.add((byte) 0);
            } else {
                byteArray.add(intConversionToByte(numberToAdd));
            }
            numberToAdd -= byteMaximumValue;
        }
    }

    public static byte intConversionToByte(int number) throws IllegalArgumentException {
        if (number < 0 || number > byteMaximumValue) {
            throw new IllegalArgumentException();
        }
        return (byte) number;
    }

    @Override
    public void write(int b) throws IOException {
        this.out.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        this.out.write(compression(b));
    }

    @Override
    public void flush() throws IOException {
        this.out.flush();
        super.flush();
    }

    @Override
    public void close() throws IOException {
        this.out.close();
        super.close();
    }
}
