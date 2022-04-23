package cn.haohaoli.diy.builder;

import cn.haohaoli.diy.mapping.BoundSql;
import cn.haohaoli.diy.parsing.GenericTokenParser;
import cn.haohaoli.diy.parsing.ParameterMapping;
import cn.haohaoli.diy.parsing.ParameterMappingTokenHandler;
import cn.haohaoli.diy.session.Configuration;
import cn.haohaoli.diy.session.MappedStatement;
import cn.haohaoli.diy.session.SqlCommandType;
import cn.haohaoli.diy.session.StatementType;
import lombok.RequiredArgsConstructor;
import org.dom4j.tree.DefaultElement;

import java.util.List;
import java.util.Objects;

/**
 * @author lwh
 */
@RequiredArgsConstructor
public class XMLStatementBuilder {

    private final String namespace;

    private final DefaultElement element;

    private final Configuration configuration;

    public void parse() {
        String         sql              = Objects.requireNonNull(element.getTextTrim(), "sql不能为空");
        String         id               = Objects.requireNonNull(element.attributeValue("id"), "id不能为空");
        String         resultType       = element.attributeValue("resultType");
        String         parameterType    = element.attributeValue("parameterType");
        String         statementTypeStr = element.attributeValue("statementType", StatementType.PREPARED.toString());
        SqlCommandType sqlCommandType   = SqlCommandType.valueOf(element.getName().toUpperCase());
        StatementType  statementType    = StatementType.valueOf(statementTypeStr);

        BoundSql boundSql = getBoundSql(sql);

        id = applyCurrentNamespace(namespace, id);

        MappedStatement mappedStatement = new MappedStatement();
        mappedStatement.setId(id);
        mappedStatement.setResultType(resultType);
        mappedStatement.setParameterType(parameterType);
        mappedStatement.setBoundSql(boundSql);
        mappedStatement.setSqlCommandType(sqlCommandType);
        mappedStatement.setStatementType(statementType);
        mappedStatement.setConfiguration(this.configuration);

        this.configuration.addMappedStatement(mappedStatement);
    }

    private String applyCurrentNamespace(String namespace, String id) {
        return namespace + "." + id;
    }

    /**
     * 完成对#{}的解析.
     * 1. 将#{}替换成?
     * 2. 将#{}解析出来的值存储
     *
     * @param sql
     * @return
     */
    private BoundSql getBoundSql(String sql) {

        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser           genericTokenParser           = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);

        // 转换好的sql. #{属性} 替换成 ?
        String parse = genericTokenParser.parse(sql);

        // #{属性} 中的属性名称
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();

        return new BoundSql(parse, parameterMappings);
    }
}


