package cn.haohaoli.diy.mapping;

import cn.haohaoli.diy.parsing.ParameterMapping;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author lwh
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoundSql  {

    private String sqlText;

    private List<ParameterMapping> parameterMappingList;
}
