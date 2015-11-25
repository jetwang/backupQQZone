import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.junit.Test;

import java.io.IOException;
import java.util.function.Consumer;

public class BlogStoreTest {
    @Test
    public void testCreateBlogHTML() throws IOException {
        BlogStore.createBlogHTML("test", "2015-9-5 13:15", "title", "content");
    }

    @Test
    public void testFilter() throws IOException {
        String filter = BlogStore.filter("世界**日");
        System.out.println(filter);
    }

    @Test
    public void testGetBlogCollection() {
        MongoCollection<Document> collection = BlogStore.getBlogCollection("ll");
        collection.find().forEach(new Consumer<Document>() {
            @Override
            public void accept(Document document) {
                System.out.println(document.toJson());

            }
        });
    }
}