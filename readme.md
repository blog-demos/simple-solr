## Simple Solr

[![lang](https://img.shields.io/badge/lang-java-brightgreen.svg)]()
[![ide](https://img.shields.io/badge/ide-IntelliJ%20IDEA-brightgreen.svg)]()
[![maven](https://img.shields.io/badge/maven-3.6.0-brightgreen.svg)]()
[![solr](https://img.shields.io/badge/solr-8.0.0-brightgreen.svg)]()
[![solrj](https://img.shields.io/badge/solrj-7.4.0-brightgreen.svg)]()

### 获取客户端连接
```java
public HttpSolrClient createSolrClient() {
    return new HttpSolrClient
            .Builder(SOLR_URL)
            .withConnectionTimeout(10000)
            .withSocketTimeout(60000)
            .build();
}
```

### 添加Document
```java
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
```

### 删除Document
```java
HttpSolrClient client = createSolrClient();
client.deleteById(String.format("%d", id));
client.commit();
client.close();
```