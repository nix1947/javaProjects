package pathsfiles;

import java.nio.file.Files;
import java.nio.file.Path;

public class Recursion {
  public static void main(String[] args) throws Throwable {

    System.out.println("------------------");
    /*
    walk(Path start, int maxDepth,
         FileVisitOption... options)
     */
    Files.walk(Path.of("src/main/java"), 1)
        .forEach(p -> System.out.println(
            p
//            p.normalize().toAbsolutePath()
        ));

    System.out.println("------------------");
    /*
    find(Path start, int maxDepth,
         BiPredicate<Path,BasicFileAttributes> matcher,
         FileVisitOption... options)
     */
    Files.find(Path.of("."), 2,
           /* (p, a) -> true*/ (p, a) -> a.isDirectory())
        .forEach(System.out::println);
  }
}
