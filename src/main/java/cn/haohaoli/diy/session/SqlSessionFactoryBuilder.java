package cn.haohaoli.diy.session;

import cn.haohaoli.diy.session.defaults.DefaultSqlSessionFactory;
import cn.haohaoli.diy.builder.XmlConfigBuilder;

import java.io.InputStream;

/**
 * @author lwh
 */
public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream in) throws Exception {
        XmlConfigBuilder xmlConfigBuilder = new XmlConfigBuilder();
        Configuration    configuration    = xmlConfigBuilder.parseConfig(in);
        return new DefaultSqlSessionFactory(configuration);
    }

}
