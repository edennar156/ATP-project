package IO;

import algorithms.mazeGenerators.Maze;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MyCompressorOutputStream extends OutputStream {
    private OutputStream out;

    public MyCompressorOutputStream(OutputStream o) {
        this.out = o;
    }

    public static byte[] compression(byte[] bytes) {
        List<Byte> formatInformationArray = new ArrayList<>();
        // By formatting the given array for our needs,
        // The most first 12 places used to grab information about the array data.
        // Separate as following:
        // Indexes 1,2 -> row len.
        // Indexes 3,4 -> col len.
        // Indexes (5,6),(7,8) -> Start position (row,col).
        // Indexes (9,10),(11,12) -> Goal position (row,col).
        for (int i = 0; i < 12; i++) {
            formatInformationArray.add(bytes[i]);
        }
        // Because the limit of Byte type is 255, we will use 8 bits to represent it (2^8=256 zero count).
        // Each array byte gets only 1/0 value, so the treatment will be as a bit.
        // The solution we are suggesting is to take Chunks of 8 bytes (represents as bits),
        // Create a new "binary number" from it, and use it.
        // For example, array with the following values: {0,0,0,0,0,1,1,0} equals to 6 (Binary conversion).
        for (int i = 12; i < bytes.length; i += 8) {
            byte[] eightBitRepresentationToBinaryValue = new byte[8];
            for (int j = 0; j < 8; j++) {
                if (i + j < bytes.length) {
                    eightBitRepresentationToBinaryValue[j] = bytes[i + j];
                } else {
                    eightBitRepresentationToBinaryValue[j] = 0;
                }
            }
            int tempValue = 0;
            // Convert to a decimal number from 8 bit array representation (actually 8 byte array):
            for (int k=0; k< eightBitRepresentationToBinaryValue.length; k++){
                tempValue += eightBitRepresentationToBinaryValue[k]* Math.pow(2,k);
            }
            formatInformationArray.add((byte)tempValue);
        }
        byte[] compressedContent = new byte[formatInformationArray.size()];
        for (int i=0; i<compressedContent.length; i++){
            compressedContent[i] = formatInformationArray.get(i);
        }
        return compressedContent;
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
    public void close() throws IOException {
        this.out.close();
        super.close();
    }

    @Override
    public void flush() throws IOException {
        this.out.flush();
        super.flush();
    }

    public OutputStream getOut() {
        return out;
    }
}
