
import com.xxxyjade.hiphopghetto.crawl.impl.CloudMusicCrawler;
import com.xxxyjade.hiphopghetto.pojo.entity.Album;
import com.xxxyjade.hiphopghetto.pojo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@Slf4j
@SpringBootTest(classes = CloudMusicCrawler.class)
@ComponentScan(basePackages = "com.xxxyjade.hiphopghetto")
public class CrawlTest {

    @Autowired
    private CloudMusicCrawler cloudMusicCrawler;

    @Test
    public void test() {
        System.out.println(new User());
    }

}
