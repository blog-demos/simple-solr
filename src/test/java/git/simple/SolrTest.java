package git.simple;

import git.simple.model.Student;
import git.simple.model.StudentSolrResult;
import git.simple.proxy.SolrProxy;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * @Description TODO
 * @Author: Q-WHai
 * @Date: Created in 11:16 2019/05/07
 */
public class SolrTest {

    private final Logger logger = LoggerFactory.getLogger(SolrTest.class);

    @Test
    public void test1() {
        SolrProxy solr = new SolrProxy();
        HttpSolrClient client = solr.createSolrClient();
        logger.info(client.toString());
    }

    @Test
    public void test2() {
        SolrProxy solr = new SolrProxy();
        try {
            solr.addDocument();
        } catch (SolrServerException | IOException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void test3() {
        Student stu1 = new Student();
        stu1.setId(100);
        stu1.setSno(1000);
        stu1.setName("Aaa");
        stu1.setSex(1);
        stu1.setAge(10);
        stu1.setCtime("2019-05-07 16:19:12");

        Student stu2 = new Student();
        stu2.setId(101);
        stu2.setSno(1001);
        stu2.setName("Bbb");
        stu2.setSex(0);
        stu2.setAge(20);
        stu2.setCtime("2019-05-05 16:19:12");

        SolrProxy solr = new SolrProxy();
        try {
            solr.addDocument(stu1);
            solr.addDocument(stu2);
        } catch (SolrServerException | IOException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void test4() {
        SolrProxy solr = new SolrProxy();
        try {
            solr.deleteDocumentById(100);
        } catch (SolrServerException | IOException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void test5() {
        SolrProxy solr = new SolrProxy();
        try {
            // q -> *:* 表示全部
            List<StudentSolrResult> students = solr.queryByString("name:Ryan", 0, 30);

            for (StudentSolrResult student : students) {
                logger.info(String.format("[%s, %s]", student.getSno(), student.getName()));
            }
        } catch (SolrServerException | IOException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void test6() {
        SolrProxy solr = new SolrProxy();
        try {
            // q -> *:* 表示全部
            List<StudentSolrResult> students = solr.queryByFilter("name:Ryan", 0, 30);

            for (StudentSolrResult student : students) {
                logger.info(String.format("[%s, %s]", student.getSno(), student.getName()));
            }
        } catch (SolrServerException | IOException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void test7() {
        SolrProxy solr = new SolrProxy();
        try {
            // q -> *:* 表示全部
            solr.queryHighlighting("age:18", 0, 30);
        } catch (SolrServerException | IOException ex) {
            ex.printStackTrace();
        }
    }
}
