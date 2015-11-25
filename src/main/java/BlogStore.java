import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.IOException;

public class BlogStore {

    static void createBlogHTML(String author, String pubTime, String paperTitle, String blogHTML) throws IOException {
        MongoCollection<Document> collection = getBlogCollection(author);
        FindIterable<Document> documents = collection.find(new Document().append("pubtime", pubTime).append("title", paperTitle));
        if (documents.iterator().hasNext()) {
            return;
        }
        collection.insertOne(new Document().append("pubtime", pubTime).append("title", paperTitle).append("content", blogHTML));
//
//            pubTime = filter(pubTime) // filter out ? \ / : | < > *
//                    .replaceAll("\\s", "_");
//            paperTitle = filter(paperTitle) // filter out ? \ / : | < > *
//                    .replaceAll("\\s", "_");
//            String fileName = "blogs/" + pubTime + "  " + paperTitle + ".html";
//            File file = new File(fileName);
//            if (!file.exists()) {
//                file.getParentFile().mkdirs();
//            }
//            StringBuilder builder = new StringBuilder();
//
//            builder.append("<html><head>");
//            builder.append("\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n");
//            builder.append(blogHTML);
//            builder.append("</head></html>");
//            Files.write(builder.toString(), file, Charset.forName("UTF-8"));
        System.out.println("Backup: " + paperTitle);
    }

    static MongoCollection<Document> getBlogCollection(String author) {
        System.setProperty("DEBUG.MONGO", "false");
        System.setProperty("DB.TRACE", "false");
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("llblog");
        return database.getCollection(author + "-blog");
    }


    static String filter(String pubTime) {
        return pubTime.replaceAll("[\\?\\\\/:|<>\\*\\[\\]@#\\$%~&\\(\\)\\+!\\^,~`]", " ");
    }
}
