package pathsfiles;

import java.nio.file.*;

public class UsePath {
  public static void main(String[] args) throws Throwable {
    Path abFile = Path.of("paths", "a", "b", "file-a-b.txt");
    Path cPath = Path.of("paths", "c");

    // extract parts of the path
    System.out.println(abFile.getFileName());
    // elements by index, iterator.
    System.out.println("element 2 is " + abFile.getName(2));
    abFile.iterator().forEachRemaining(System.out::println);
    // See also compareTo, startsWith, endsWith,
    // getParent, getRoot, subPath, toFile etc.
    // register for watching

    System.out.println("path is absolute? " + abFile.isAbsolute());
    System.out.println("absolute path is " + abFile.toAbsolutePath());

    Path odd = Path.of(".", "..", "Java11CertificationCourse",
        "paths", "a", "..", "a", "b", "file-a-b.txt");
    System.out.println("odd is " + odd);
    System.out.println("normalized is " + odd.normalize());

    System.out.println("resolve, joining paths "
        + cPath.resolve("d/file-c-d.txt"));
    System.out.println("to get from paths/c to file-a-b: "
        + cPath.relativize(abFile));

    try (var ws = FileSystems.getDefault().newWatchService();) {
      Path pathsDir = Path.of("paths");
      var registrationKey = pathsDir.register(ws,
          StandardWatchEventKinds.ENTRY_CREATE,
          StandardWatchEventKinds.ENTRY_DELETE,
          StandardWatchEventKinds.ENTRY_MODIFY
      );
      while (true) {
        var key = ws.take();
        System.out.println("something happened");
        for (var event : key.pollEvents()) {
          System.out.println("filename " + event.context());
        }
        registrationKey.reset();
      }
    }
  }
}
