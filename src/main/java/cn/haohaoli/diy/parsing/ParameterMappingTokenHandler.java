package cn.haohaoli.diy.parsing;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ParameterMappingTokenHandler implements TokenHandler {

    private final List<ParameterMapping> parameterMappings = new ArrayList<>();

    @Override
    public String handleToken(String content) {
        parameterMappings.add(buildParameterMapping(content));
        return "?";
    }

    private ParameterMapping buildParameterMapping(String content) {
        return new ParameterMapping(content);
    }
}