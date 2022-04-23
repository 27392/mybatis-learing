package cn.haohaoli.diy.builder;

import cn.haohaoli.diy.session.Configuration;
import cn.haohaoli.diy.io.Resources;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author lwh
 */
public class XmlConfigBuilder {

    private final Configuration configuration;

    private final SAXReader reader;

    public XmlConfigBuilder() {
        this.configuration = new Configuration();
        this.reader = new SAXReader();
    }

    public Configuration parseConfig(InputStream in) throws Exception {

        Document  document    = reader.read(in);
        Element   rootElement = document.getRootElement();

        this.dataSource(rootElement.element("dataSource"));
        this.mapperElement(rootElement.element("mappers"));

        return configuration;
    }

    /**
     * 数据库连接池
     *
     * @param element
     * @return
     */
    private void dataSource(Element element) {
        List<Node> nodeList = element.selectNodes("//property");

        Properties properties = new Properties();
        for (Node property : nodeList) {
            String name  = ((DefaultElement) property).attribute("name").getValue();
            String value = ((DefaultElement) property).attribute("value").getValue();
            properties.put(name, value);
        }

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(properties.getProperty("driver"));
        hikariConfig.setJdbcUrl(properties.getProperty("url"));
        hikariConfig.setUsername(properties.getProperty("username"));
        hikariConfig.setPassword(properties.getProperty("password"));

        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
        configuration.setDataSource(hikariDataSource);
    }

    /**
     * mapper
     *
     * @param element
     * @throws Exception
     */
    private void mapperElement(Element element) throws Exception {

        List<Node> mapperList = element.selectNodes("mapper");

        for (Node mapper : mapperList) {
            String resource = ((DefaultElement) mapper).attribute("resource").getValue();
            try (InputStream inputStream = Resources.getResourceAsStream(resource)) {
                XmlMapperBuilder xmlMapperBuilder = new XmlMapperBuilder(inputStream, configuration, resource);
                xmlMapperBuilder.parse();
            }
        }
    }
}
