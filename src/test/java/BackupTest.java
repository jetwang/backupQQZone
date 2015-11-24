import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class BackupTest {
    @Test
    public void testCreateBlogHTML() throws IOException {
        Backup.createBlogHTML("2015-9-5 13:15","title","content");
    }

    @Test
    public void testFilter() throws IOException {
        String filter = Backup.filter("世界**日");
        System.out.println(filter);
    }
}