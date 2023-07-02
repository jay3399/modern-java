import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;


  @BenchmarkMode(Mode.AverageTime)
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  @State(Scope.Benchmark)
  @Fork(value = 2 , jvmArgs = {"-Xms4G" , "-Xms4G"})
  public class Sample {

    private static final long N = 10_000_000L;

    @Benchmark
    public long sequentialSum() {
      return Stream.iterate(1L, i -> i + 1).limit(N).reduce(0L, Long::sum);
    }


    @TearDown(Level.Invocation)
    public void tearDown() {
      System.gc();
    }





  }


