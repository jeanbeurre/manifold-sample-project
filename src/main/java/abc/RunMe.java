package abc;

import abc.res.*;
import abc.res.Person;
import abc.stuff.*;
import manifold.collections.api.range.IntegerRange;
import manifold.ext.props.rt.api.override;
import manifold.ext.props.rt.api.val;
import manifold.ext.props.rt.api.var;
import manifold.ext.rt.api.ComparableUsing;
import manifold.ext.rt.api.Jailbreak;
import manifold.ext.rt.api.Structural;
import manifold.ext.rt.api.auto;
import manifold.science.measures.*;
import manifold.science.util.Rational;

import static manifold.collections.api.range.RangeFun.*;
import static manifold.science.measures.MetricScaleUnit.M;
import static manifold.science.util.UnitConstants.*;
import static manifold.science.util.CoercionConstants.*;
import static manifold.rt.api.util.Pair.and;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.*;
import java.util.function.Function;

import static java.time.temporal.ChronoUnit.MONTHS;
import static abc.res.movies.Genre.Action;
import static java.lang.System.out;

// use #define/#undef to demonstrate use of the Preprocessor
#define EXPERIMENTAL
#undef EXPERIMENTAL // comment out this #undef to test experimental features

/**
 * Utilize a sampling of core Manifold features to demonstrate the
 * structure of a single module project using Manifold. Use the pom.xml
 * file as a template for your own project.
 * <p/>
 * Play with this in IntelliJ IDEA using the <b>Manifold</b> plugin:
 * <pre>
 * Settings | Plugins | Marketplace | search: 'Manifold'
 * </pre>
 * Use IntelliJ plugin features such as: <ul>
 * <li/> Navigation
 * <li/> Code completion
 * <li/> Find Usages
 * <li/> Rename/Move Refactor
 * <li/> Type-safe GraphQL, JSON, XML, CSV, etc.
 * <li/> Properties
 * <li/> Tuples
 * <li/> Fragment support
 * <li/> Unit Expressions
 * <li/> Template Authoring
 * <li/> Incremental compilation
 * <li/> Hotswap debugging
 * <li/> etc.
 * </ul>
 */
public class RunMe {
  public static void main(String[] args) {
    useImage();
    usePropertiesFiles();
    useJsonSample();
    useJsonSchema();
    useYamlUsingJsonSchema();
    useGraphQL();
    useCsv();
    useCustomExtension();
    useProvidedExtension();
    useStructuralInterface();
    useMapStructuralInterface();
    useSelfType();
    useJailbreak();
    useCheckedExceptionSuppression();
    useStringLiteralTemplates();
    useTemplateManifold();
    useJavascript();
    useOperatorOverloading();
    useUnitExpressions();
    useRanges();
    useExplicitProperties();
    useInferredProperties();
    useMultipleReturnValues();
    useAutoTypeInference();
    useTupleExpressions();
    useDelegation();
    useOptionalParameters();

    #if EXPERIMENTAL
    useJsonFragment();
    useGraphQLFragment();
    useJavascriptFragment();
    #endif

    #if MyBuildProp == 20 //this is set as -Akey[=value] compiler arg in pom.xml
      System.out.println("MyBuildProp is set");
    #endif
  }

  public interface Book {
    @val int id;
    @var String title;
  }
  static class BookImpl implements Book {
    @override @val int id = (int) (Math.random() * 1_000_000);
    @override @var String title;
  }
  // via manifold-props dependency
  private static void useExplicitProperties() {
    out.println("\n\n### Use manifold-props explicit properties ###\n");
    Book book = new BookImpl();
    book.title = "Chain";
    out.println(book.id + ": " + book.title);
    book.title += " of Command";
    out.println(book.title);
  }
  // via manifold-props dependency
  private static void useInferredProperties() {
    out.println("\n\n### Use manifold-props inferred properties ###\n");
    LocalTime t = LocalTime.now();

    // access inferred properties: hour, minute, second, nano
    t = LocalTime.of(t.hour, t.minute, t.second, t.nano);
    out.println( t );
  }

  // via manifold-image dependency
  private static void useImage() {
    out.println("\n\n### Use Image Manifold ###\n");
    var logoImage = logo_png.get();
    out.println(logoImage.getIconWidth());
  }

  // via manifold-properties dependency
  private static void usePropertiesFiles() {
    out.println("\n\n### Use Properties Files ###\n");
    out.println(MyProperties.Chocolate);
    out.println(MyProperties.Chocolate.dark);
    out.println(MyProperties.Chocolate.milk);
  }

  // via manifold-json dependency
  private static void useJsonSample() {
    out.println("\n\n### Use JSON Manifold With JSON Sample ###\n");
    // Create instances of the *type* inferred from the sample data in Person.json
    Person person = Person.create();
    person.setName("Scott");
    person.setAddress(Person.Address.create());
    Person.Address address = person.getAddress();
    address.setCity("Cupertino");
    address.setState("CA");
    out.println(person.write().toJson());

    // Use the JSON sample *data* from Person.json itself type-safely
    Person p = Person.fromSource();
    out.println(p.getName());
    out.println(p.getAge());
  }

  // via manifold-json dependency
  private static void useJsonSchema() {
    out.println("\n\n### Use JSON Manifold With JSON Schema ###\n");
    Contact contact = Contact.builder()
      .withName("Scott McKinney")
      .withDateOfBirth(LocalDate.of(1986, 8, 9))
      .withPrimaryAddress(Contact.Address.create("111 Main St.", "Cupertino", "CA"))
      .withContactAddresses(Collections.singletonList(Contact.Address.create("a", "b", "c")))
      .build();
    out.println(contact.write().toJson());
  }

  #if EXPERIMENTAL
  // via manifold-json dependency
  private static void useJsonFragment() {
    out.println("\n\n### Use Json Fragment ###\n");
    /*[Dude.json/] {
      "Name": "Scott",
      "Age": 100,
      "Address": {
        "Street": "345 Syracuse Way",
        "City": "Atlantis"
      }
    }
    */

    Dude dude = Dude.fromSource();
    out.println(dude.getName());
    out.println(dude.getAge());
    out.println(dude.getAddress().getCity());
  }
  #endif

  // via manifold-yaml dependency
  private static void useYamlUsingJsonSchema() {
    out.println("\n\n### Use YAML Manifold With JSON Schema ###\n");
    Contact2 contact = Contact2.builder()
      .withName("Scott McKinney")
      .withDateOfBirth(LocalDate.of(1986, 8, 9))
      .withPrimaryAddress(Contact2.Address.create("111 Main St.", "Cupertino", "CA"))
      .withContactAddresses(Collections.singletonList(Contact2.Address.create("a", "b", "c")))
      .build();
    out.println(contact.write().toJson());
  }

  // via manifold-graphql dependency
  private static void useGraphQL() {
    out.println("\n\n### Use GraphQL Schemas ###\n");
    // Create new data
    movies.Person steveMcQueen = movies.Person.builder("anId", "Steve McQueen", LocalDate.of(1930, 3, 24))
      .withHeight(5 ft + 10 in) // extension method
      .withNationality("American")
      .build();
    out.println(steveMcQueen.write().toJson());

    // Build and execute queries
    queries.MovieQuery query = queries.MovieQuery.builder()
      .withGenre(Action)
      .build();
    out.println(query.write().toJson());

    // Executing the query requires a GraphQL server or test infrastructure,
    // see GraphQL sample app at https://github.com/manifold-systems/manifold-sample-graphql-app)
    //
    // query execution is simple and looks like this:
    //
    // var result = query.request("http://example.com/graphql").post();
    // var actionMovies = result.getMovies();
  }

#if EXPERIMENTAL
  // via manifold-graphql dependency
  private static void useGraphQLFragment() {
    out.println("\n\n### Use GraphQL Fragment ###\n");

    // Note get the JS GraphQL IntelliJ plugin for rich editing of embedded GraphQL fragments

    /*[MyQuery.graphql/]
    query Movies($title: String, $genre: Genre, $releaseDate: Date) {
        movies(title: $title, genre: $genre, releaseDate: $releaseDate) {
            id
            title
            genre
            releaseDate
        }
    }
    */

    var query = MyQuery.Movies.builder().withGenre(Action).build();
    out.println(query.getGenre());

  #if JAVA_15_OR_LATER
    var moviesQuery = """
    [.graphql/]
      query Movies($title: String, $genre: Genre, $releaseDate: Date) {
          movies(title: $title, genre: $genre, releaseDate: $releaseDate) {
              id
              title
              genre
              releaseDate
          }
      }
    """;
    var actionMoviesQuery = moviesQuery.builder()
            .withGenre(Action).build();
// Need a server to field this request, see the GraphQL Sample Application
//    var result = actionMoviesQuery.request("https://com.example/movies/graphql").post();
//    for (var movie : result.getMovies()) {
//      out.println("""
//        Title: ${movie.getTitle()}
//        Genre: ${movie.getGenre()}
//        Year: ${movie.getReleaseDate().getYear()}
//        """);
//    }

  #endif
  }
#endif

  // via manifold-csv
  public static void useCsv() {
    out.println("\n\n### Use CSV resource ###\n");
    for (TestData.TestDataItem item : TestData.fromSource()) {
      System.out.println("${item.first} ${item.second} ${item.third}");
    }
  }

  private static void useAutoTypeInference() {
    out.println("\n\n### Use `auto` return type inference ###\n");
    var printer = getPrinter("hello");
    printer.run();
    out.println(printer.chars); // anonymous type's chars field
  }

  static auto getPrinter(String message) {
    return new Runnable() {
      int chars;
      public void run() {
        out.println(message);
        chars = message.length();
      }
    };
  }

  private static void useTupleExpressions() {
    out.println("\n\n### Use tuple expressions ###\n");

    // type-safe & lightweight name/value structures
    var t = (name: "Bob", age: "35");
    System.out.println("Name: ${t.name} Age: ${t.age}");

    // names are inferred
    var t2 = (t.name, t.age);
    System.out.println("Name: ${t2.name} Age: ${t2.age}");

    // labels are optional
    var t3 = (10, 20);
    out.println(t3.item1);
    out.println(t3.item2);
  }

  // via manifold-delegation
  private static void useDelegation() {
    out.println("\n\n### Use Delegation ###\n");

    DelegationExample.main(null);
  }

  // via manifold-params
  private static void useOptionalParameters() {
    out.println("\n\n### Use Optional Parameters ###\n");

    char[] data = new char[] {'m', 'a', 'n', 'i', 'f', 'o', 'l', 'd'};
    out.println(valueOf(data)); // use defaults for offset and count
    out.println(valueOf(data, 4)); // use default for count
    out.println(valueOf(data, count:4)); // use default for offset by naming count
  }
  private static String valueOf(char[] data,
                                int offset = 0,
                                int count = data.length - offset) {
    return String.valueOf(data, offset, count);
  }

  // via manifold-ext dependency
  private static void useCustomExtension() {
    // echo() extension on String
    out.println("\n\n### Use Custom Extension Method ###\n");
    String hello = "hello";
    hello.echo();

    // containsKeys() extension on Map
    HashMap<String, Integer> map = new HashMap<>() {{
      put("one", 1);
      put("two", 2);
      put("three", 3);
    }};
    List<String> keys = Arrays.asList("one", "two");
    out.println(map.containsKeys(keys));

    for( Map.Entry<String, Integer> entry: map.entrySet()) {
      // hiFromEntry extension on inner type Map.Entry
      out.println(entry.hiFromEntry());
    }
  }

  // via manifold-collections dependency
  private static void useProvidedExtension() {
    out.println("\n\n### Use Collections Extension Library ###\n");
    Iterable<Integer> list = Arrays.asList(1, 2, 3);
    out.println(list.first());
    out.println(list.joinToString(", "));

    // Simplified Map construction (mapOf extension and the Pair.and binding constant)
    Map<String, Integer> map = Map.mapOf("Moe" and 88, "Larry" and 99, "Curly" and 100);
    out.println(map.get("Moe"));
  }

  // via manifold-ext dependency
  private static void useStructuralInterface() {
    out.println("\n\n### Use Structural Interfaces ###\n");
    // Point nominally implements Coordinate via interface extension (see MyPointExt).
    Coordinate coord = new Point(4, 5);
    out.println("x: " + coord.getX() + ", y: " + coord.getY());

    // Rectangle structurally implements Coordinate
    Coordinate loc = new Rectangle(3, 4, 5, 6);
    out.println("x: " + loc.getX() + ", y: " + loc.getY());

    // Use a registered IProxyFactory interface to make old Date structurally compatible with new ChronoLocalDateTime
    // See Date_To_ChronoLocalDateTime and MyChronoLocalDateTimeExt
    Date date = Date.from(Instant.now());
    //noinspection rawtypes
    out.println(((ChronoLocalDateTime) date).plus(1, MONTHS));
  }

  // via manifold-ext dependency
  private static void useMapStructuralInterface() {
    out.println("\n\n### Use Maps with Structural Interfaces ###\n");
    // Use a Map to directly back any interface
    Dog dog = (Dog)new HashMap<>();
    dog.setBreed("Foxhound");
    ((Map)dog).put("likes", (Function<String,Boolean>)(thing -> !thing.equals("relaxation")));
    String breed = dog.getBreed();
    out.println(breed);
    out.println(dog.likes("ball"));
    out.println(dog.likes("relaxation"));
  }
  @Structural
  interface Dog {
    String getBreed();
    void setBreed(String breed);
    default boolean likes(String thing) {
      return true;
    }
  }

  // via manifold-ext dependency
  private static void useSelfType() {
    out.println("\n\n### Use Self Type ###\n");
    CarBuilder carBuilder = new CarBuilder();
    Car car = carBuilder
      .withName("Mach Five") // returns CarBuilder
      .withColor(255, 255, 255)
      .build();
    out.println(car.getName());
  }

  // via manifold-ext dependency
  private static void useJailbreak() {
    out.println("\n\n### Use Jailbreak (Type-safe Reflection) ###\n");
    @Jailbreak SampleClass sample = new SampleClass();
    out.println(sample.privateMethod());
    sample._privateField = "assign to private field";
    out.println(sample._privateField);
    sample = new abc.stuff.@Jailbreak SampleClass("use hidden constructor");
    out.println(sample._privateField);

    String data = new SampleClass().jailbreak().privateMethod();
    out.println(data);

    // Note, usage of compiler option --release to target older JDKS may not be
    // compatible with @Jailbreak when used on JDK types. Since the JDK does not
    // ship with older JDKs and instead ships with filtered type info where the private
    // fields and methods are excluded, the private type information just isn't
    // available in the compiler.
//    @Jailbreak LocalDateTime ldt = LocalDateTime.now();
//    ldt.time = LocalTime.of( 5, 0 );  // private field assignment
//    out.println(ldt);
  }

  // via manifold-strings dependency
  private static void useStringLiteralTemplates() {
    out.println("\n\n### Use String Literal Templates ###\n");
    int hour = 5;
    int minute = 30;
    String nice = "The time is $hour:$minute";
    out.println(nice);

    LocalTime time = LocalTime.now();
    String cool = "The time is ${time.getHour()}:${String.format(\"%02d\", time.getMinute())}";
    out.println(cool);

    // use with text blocks
    out.println( """
            The time is:
            $hour:$minute
            """ );
  }

  // via manifold-templates dependency
  private static void useTemplateManifold() {
    out.println("\n\n### Use ManTL (Type-safe Java Templates) ###\n");
    String html = SampleTemplate.render("ZOMG");
    out.println(html);
  }

  // via manifold-exceptions dependency
  private static void useCheckedExceptionSuppression() {
    out.println("\n\n### Use Checked Exception Suppression ###\n");
    java.util.List<String> list = Arrays.asList("http://manifold.systems", "https://github.com/manifold-systems/manifold");
    List<URL> urls = list.map(URL::new).toList(); // w/o suppression, this would have compile errors
    urls.forEach(out::println);
    boom(); // w/o suppression, this would have a compile error
  }

  private static void boom() throws IOException {
    //noinspection ConstantConditions
    if (false)
      throw new IOException();
  }

  @SuppressWarnings("unused")
  private static void freedom() {
    //noinspection ConstantConditions
    if (false)
      // w/o the manifold-exceptions dependency this would have a compile error
      throw new IOException();
  }

  // via manifold-js dependency
  private static void useJavascript() {
    out.println("\n\n### Use Javascript Manifold ###\n");

    // Use a js *program*
    out.println(BasicJavascriptProgram.bar());
    out.println(BasicJavascriptProgram.incrementAndGet());
    out.println(BasicJavascriptProgram.incrementAndGet());
    out.println(BasicJavascriptProgram.identity("Foo"));
    out.println(BasicJavascriptProgram.identity(1));
    out.println(BasicJavascriptProgram.callback((Runnable)()-> out.println("callback from js")));
  }

#if EXPERIMENTAL
  // via manifold-js dependency
  private static void useJavascriptFragment() {
    out.println("\n\n### Use Javascript Fragment ###\n");

    // Embedded fragment (requires IJ Ultimate Edition for assisted JS code injection editing)

    /*[Sample.js/]
    function total(list) {
      return reduce(list, (sum, entry) => {
        return sum + entry.intValue();
      })
    }

    function max(list) {
      return reduce(list, (max, entry) => {
        return max < entry.intValue() ? entry.intValue() : max;
      })
    }

    function reduce(list, f) {
      var acc = null;
      for (var i = 0; i < list.size(); i++) {
        acc = f(acc, list.get(i));
      }
      return acc;
    }
    */
    out.println("Sum: " + Sample.total(Arrays.asList(1, 2, 3)));
    out.println("Max: " + Sample.max(Arrays.asList(1, 2, 3)));

    // Fragment *evaluation* from String literal (experimental)
    var value = "[.js/] 3 + 4 + 5";
    out.println(value);
  }
#endif

  // manifold-ext dependency
  @SuppressWarnings("unused")
  private static void useOperatorOverloading() {
    out.println("\n\n### Use Operator Overloading ###\n");

    MyClass a = new MyClass(2);
    MyClass b = new MyClass(3);

    // use '+' operator directly on Foo
    MyClass sum = a + b; // Foo(5)
    out.println(sum.x);

    // use relational operators
    if (a < b) {
      out.println("a < b");
    }
    // map '==' to compareTo()
    if (a == sum - b) {
      out.println(":)");
    }
    // Use operators on BigDecimal
    BigDecimal result = 2.1bd * 3.2bd;
    out.println(2.1bd < 3.2bd);
    out.println(2.1bd == 2.1bd);

    out.println(1bd/3bd);

    // Use convenient index operator with Map, List, etc.
    Map<String, String> map = new HashMap<>();
    map["color"] = "Red";
    map["shape"] = "Round";
    out.println(map["shape"]);
  }

  // manifold-ext dependency
  @SuppressWarnings("unused")
  private static void useUnitExpressions() {
    out.println("\n\n### Use Unit Expressions ###\n");

    // Conveniently use Rational, BigDecimal, etc. (CoercionConstants)
    Rational rational = 2.1r + 2.2r;
    BigDecimal result = 2.1bd + 2.2bd;

    // Type-safely work with physical measurements (UnitConstants)
    Length distance = 75mph * 2.3hr;
    Force force = 5kg * 9.8m/s/s;
    Energy e1 = force * 12m;
    Energy e2 = 200kg m/s/s m;

    // SI formatted measurements (UnitConstants)
    HeatCapacity kBoltzmann = 1.380649e-23 J/dK;
    out.println(kBoltzmann);

    // Convenient metric units (MetricScaleUnits)
    Length longDistance = 5.2M mi; // 5.2 million miles

    // Use your own unit names
    LengthUnit bigYard = LengthUnit.Meter;
    Length twoMeters = 2 bigYard;

    // Easily mix units
    Length measurement = 2 m + 5 in;
    out.println(measurement);
    // Display with any unit
    out.println(measurement.to(ft));
    // Display as mixed fraction
    out.println(measurement.to(ft).toMixedString());
  }

  // manifold-collections dependency
  @SuppressWarnings("unused")
  private static void useRanges() {
    out.println("\n\n### Use Ranges with `RangeFun` ###\n");

    // Work with ranges using binder constants from manifold.collections.api.range.RangeFun

    // Easily make ranges on sequential endpoints using 'to'
    for (int i : 1 to 10) {
      out.println(i);
    }

    // Use with lambdas
    (1 to 10).forEach(out::println);

    // Strong typing
    IntegerRange range = 1 to 10;

    // Reverse the endpoints
    for (int i : 10 to 1) {
      out.println(i);
    }

    // Use 'step' to define an increment
    for (Rational i : 1r to 10r step 2r) {
      out.println(i);
    }

    // Use variations of 'to' ('_to', '_to_', 'to_') to exclude range endpoints
    for (Rational i : 1r _to 10r) {
      out.println(i);
    }

    // Iterate a range of measures with a 'unit'
    for (Mass mass : 5kg to 6kg step 2.7r unit oz) {
      out.println(mass); // increments of 2.7 oz starting with 5kg
    }

    // Use 'inside' to check for containment on Comparable endpoints
    if ("le matos" inside "a" to "m~") {
      out.println("le matos");
    }
  }

  private static void useMultipleReturnValues()
  {
    out.println("\n\n### Use multiple return values ###\n");

    var result = findMinMax( new int[]{1, 2, 3} );
    System.out.println( "Minimum: " + result.min + " Maximum: " + result.max );
  }

  static auto findMinMax(int[] data) {
    if(data == null || data.length == 0) return null;
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
    for(int i: data) {
      if(i < min) min = i;
      if(i > max) max = i;
    }
    return min, max; // return multiple values
  }

  /**
   * Implements operator methods
   */
  public static class MyClass implements ComparableUsing<MyClass> {
    public final int x;

    public MyClass(int x) {
      this.x = x;
    }

    /**
     * implement '+' operator
     */
    @SuppressWarnings("unused")
    public MyClass plus(MyClass that) {
      return new MyClass(x + that.x);
    }

    /**
     * implement '-' operator
     */
    @SuppressWarnings("unused")
    public MyClass minus(MyClass that) {
      return new MyClass(x - that.x);
    }

    /**
     * ComparableUsing delegates to compareTo() for >, >=, <, <=
     */
    @Override
    public int compareTo(MyClass that) {
      return x - that.x;
    }

    /**
     * Use compareTo() for == and !=
     */
    @Override
    public EqualityMode equalityMode() {
      return EqualityMode.CompareTo;
    }
  }
}
