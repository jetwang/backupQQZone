import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.mongodb.client.FindIterable;
import org.bson.Document;

import java.io.*;
import java.util.HashMap;

public class BlogReader {
    public static void main(String[] args) throws IOException {
        FindIterable<Document> blogs = BlogStore.getBlogCollection("ll").find().sort(new Document("_id", 1));
        HashMap<String, Object> scopes = new HashMap<String, Object>();
        scopes.put("blogs", blogs);

        Writer writer = new FileWriter(new File("blogs/blogs.html"));
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(new FileReader("blogs.template"), "template");
        mustache.execute(writer, scopes);
        writer.flush();
    }
}
