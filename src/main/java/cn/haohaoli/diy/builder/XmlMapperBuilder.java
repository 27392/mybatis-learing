package cn.haohaoli.diy.builder;

import cn.haohaoli.diy.mapping.BoundSql;
import cn.haohaoli.diy.parsing.GenericTokenParser;
import cn.haohaoli.diy.parsing.ParameterMapping;
import cn.haohaoli.diy.parsing.ParameterMappingTokenHandler;
import cn.haohaoli.diy.session.Configuration;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;

import java.io.InputStream;
import java.util.List;

/**
 * @author lwh
 */
public class XmlMapperBuilder {

    private final InputStream   inputStream;
    private final Configuration configuration;

    private final SAXReader reader;

    public XmlMapperBuilder(InputStream inputStream, Configuration configuration, String source) {
        this.inputStream = inputStream;
        this.configuration = configuration;
        this.reader = new SAXReader();
    }

    public void parse() throws DocumentException {
        Document document    = reader.read(inputStream);
        Element  rootElement = document.getRootElement();

        String     namespace = rootElement.attributeValue("namespace");
        List<Node> nodeList  = rootElement.selectNodes("//select | //update | // insert | // delete");

        this.buildStatementFromContext(namespace, nodeList);
        this.bindMapperForNamespace(namespace);
    }

    public void buildStatementFromContext(String namespace, List<Node> nodeList) {
        for (Node node : nodeList) {
            XMLStatementBuilder xmlStatementBuilder = new XMLStatementBuilder(namespace, (DefaultElement) node, configuration);
            xmlStatementBuilder.parse();
        }
    }

    public void bindMapperForNamespace(String namespace) {
        Class<?> mapperClass = null;
        try {
            mapperClass = Class.forName(namespace);
        } catch (ClassNotFoundException e) {
            // ignore
        }
        if (mapperClass != null) {
            this.configuration.addMapper(mapperClass);
        }
    }


}
