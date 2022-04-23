package cn.haohaoli.diy.session;

import cn.haohaoli.diy.mapping.BoundSql;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lwh
 */
@Getter
@Setter
public class MappedStatement {

    private String         id;
    private String         resultType;
    private String         parameterType;
    private BoundSql       boundSql;
    private SqlCommandType sqlCommandType;
    private StatementType  statementType;
    private Configuration  configuration;

}
