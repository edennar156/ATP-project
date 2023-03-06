package Server;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

public interface IServerStrategy {
    void ServerStrategy(InputStream InputFromClient, OutputStream OutputToClient) throws IOException;
}
