package demo;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.annotation.Documented;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.reflections.Reflections;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * This is used for listing all spring annotations & its usages.
 * <ol>
 * <li>{@link Configuration}</li>
 * </ol>
 *
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

	public static void main(String[] args) throws Exception {
		Reflections reflections = new Reflections("org.springframework");
		List<Class<?>> clazzes = new ArrayList<Class<?>>(
				reflections.getTypesAnnotatedWith(Documented.class));
		Collections.sort(clazzes, new Comparator<Class<?>>() {
			@Override
			public int compare(Class o1, Class o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
		System.out
				.println("##################################################");
		System.out.println("Total Annotations: " + clazzes.size());
		System.out
				.println("##################################################");
		String old = IOUtils.toString(new FileReader("annotations-bkp.csv"));
		FileWriter out = new FileWriter(new File("annotations.csv"));
		out.write("\"Name\",\"Class\",\"URL\"\n");
		for (Class<?> class1 : clazzes) {
			System.out.println(class1.getName());
			Document doc = Jsoup.connect(
					"http://www.bing.com/search?q="
							+ URLEncoder.encode(class1.getName(), "UTF-8"))
					.get();
			int ctr = 1;
			for (Element elem : doc.select("h2 a")) {
				ctr++;
				System.out.println(elem.attr("href"));
				out.append("\"" + class1.getSimpleName() + "\",\""
						+ class1.getName() + "\",\"" + elem.attr("href")
						+ "\"\n");
				if (ctr > 2)
					break;
			}
		}
		out.close();
		System.out
				.println("##################################################");
	}
}
