package com.wuzhizhan.mybatis.generate.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

/**
 * Adds {@code @Repository} annotation to mapper interfaces.
 */
public class RepositoryAnnotationPlugin extends PluginAdapter {

    private static final FullyQualifiedJavaType REPOSITORY_TYPE =
            new FullyQualifiedJavaType("org.springframework.stereotype.Repository");

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        interfaze.addImportedType(REPOSITORY_TYPE);
        interfaze.addAnnotation("@Repository");
        return true;
    }
}

