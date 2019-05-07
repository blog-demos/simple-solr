package git.simple.proxy;

import git.simple.model.Student;
import git.simple.model.StudentSolrResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.util.NamedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author: Q-WHai
 * @Date: Created in 11:13 2019/05/07
 */
public class SolrProxy {

    private final Logger logger = LoggerFactory.getLogger(SolrProxy.class);

    // solr服务器所在的地址，core0为自己创建的文档库目录
    private final static String SOLR_URL = "http://192.168.88.133:8983/solr/core0";

    /**
     * 获取客户端的连接
     */
    public HttpSolrClient createSolrClient() {
        return new HttpSolrClient
                .Builder(SOLR_URL)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
    }

    /**
     * 往索引库添加文档
     */
    public void addDocument() throws SolrServerException, IOException {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", "100");
        document.addField("sno", "2000100");
        document.addField("name", "AAA");
        document.addField("sex", "1");
        document.addField("age", "11");
        document.addField("ctime", "2018-09-04 12:45:33");

        HttpSolrClient solr = createSolrClient();
        solr.add(document);
        solr.commit();
        solr.close();

        logger.info("SolrInputDocument add successful!");
    }

    /**
     * 往索引库添加文档
     */
    public void addDocument(Student student) throws SolrServerException, IOException {
        if (null == student)return;

        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", student.getId());
        document.addField("sno", student.getSno());
        document.addField("name", student.getName());
        document.addField("sex", student.getSex());
        document.addField("age", student.getAge());
        document.addField("ctime", student.getCtime());

        HttpSolrClient solr = createSolrClient();
        solr.add(document);
        solr.commit();
        solr.close();

        logger.info("SolrInputDocument add successful!");
    }


    /**
     * 根据ID从索引库删除文档
     */
    public void deleteDocumentById(int id) throws SolrServerException, IOException {
        HttpSolrClient client = createSolrClient();
        client.deleteById(String.format("%d", id));
        client.commit();
        client.close();
    }

    /**
     * 查询字符串
     * text=*:* 表示全文搜索
     */
    public List<StudentSolrResult> queryByString(String text, int start, int rows) throws SolrServerException, IOException {
        HttpSolrClient client = createSolrClient();
        SolrQuery query = new SolrQuery();

        query.set("q", text);

        query.setStart(start);
        query.setRows(rows);

        QueryResponse response = client.query(query); // 执行查询
        // SolrDocumentList resultList = response.getResults(); // 获取返回结果
        // for(SolrDocument document: resultList){
        //     System.out.println(document);
        // }

        return response.getBeans(StudentSolrResult.class); // 获取实体对象形式
    }

    public List<StudentSolrResult> queryByFilter(String filterStr, int start, int rows) throws SolrServerException, IOException {
        HttpSolrClient client = createSolrClient();
        SolrQuery query = new SolrQuery();

        query.set("q", "*:*");
        query.addFilterQuery(filterStr);

        //设置分页参数
        query.setStart(start);
        query.setRows(rows);

        QueryResponse response = client.query(query); // 执行查询
        return response.getBeans(StudentSolrResult.class); // 获取实体对象形式
    }

    public void queryHighlighting(String text, int start, int rows) throws SolrServerException, IOException {
        // TODO
        HttpSolrClient client = createSolrClient();
        SolrQuery solrQuery = new SolrQuery();

        solrQuery.setQuery("*:*");

        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("name");
        //solrQuery.addHighlightField("desc");
        solrQuery.setHighlightSimplePre("<em>");
        solrQuery.setHighlightSimplePost("</em>");
        solrQuery.setHighlightFragsize(72);
        solrQuery.setHighlightSnippets(3);

        QueryResponse query = client.query(solrQuery);
        SolrDocumentList results = query.getResults();
        Map<String, Map<String, List<String>>> highlightresult = query.getHighlighting();
        System.out.println(highlightresult);
    }

    /**
     * 根据设定的查询条件进行文档字段的查询
     */
    public void querySolr() throws SolrServerException, IOException {
        HttpSolrClient client = createSolrClient();
        SolrQuery query = new SolrQuery();

        //下面设置solr查询参数

        //query.set("q", "*:*"); // 参数q  查询所有
        query.set("q", "钢铁侠"); // 相关查询，比如某条数据某个字段含有周、星、驰三个字  将会查询出来 ，这个作用适用于联想查询

        //参数fq, 给query增加过滤查询条件
        query.addFacetQuery("id:[0 TO 9]"); // TODO
        query.addFilterQuery("description:一个逗比的码农");

        //参数df,给query设置默认搜索域，从哪个字段上查找
        query.set("df", "name");

        //参数sort,设置返回结果的排序规则
        query.setSort("id",SolrQuery.ORDER.desc);

        //设置分页参数
        query.setStart(0);
        query.setRows(10);

        //设置高亮显示以及结果的样式
        query.setHighlight(true);
        query.addHighlightField("name");
        query.setHighlightSimplePre("<font color='red'>");
        query.setHighlightSimplePost("</font>");

        //执行查询
        QueryResponse response = client.query(query);

        //获取返回结果
        SolrDocumentList resultList = response.getResults();

        for(SolrDocument document: resultList){
            System.out.println("id:"+document.get("id")+"   document:"+document.get("name")+"    description:"+document.get("description"));
        }

        //获取实体对象形式
        List<Student> persons = response.getBeans(Student.class);

        System.out.println(persons.get(0).getName());
    }
}
