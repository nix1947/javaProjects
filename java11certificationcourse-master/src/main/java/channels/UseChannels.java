package channels;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class UseChannels {
  public static void main(String[] args) throws Throwable {
    ByteBuffer buff = ByteBuffer.allocate(1024);
    try (FileInputStream fis = new FileInputStream(
                                Path.of("paths","data.txt").toFile());
         FileChannel fcIn = fis.getChannel();
         FileChannel fcOut = FileChannel.open(Path.of("paths","copy.txt"),
             StandardOpenOption.CREATE, StandardOpenOption.WRITE);) {
      int count;
      while ((count = fcIn.read(buff)) >= 0) {
        buff.flip();
        fcOut.write(buff);
        buff.clear();
      }
    }
  }
}
