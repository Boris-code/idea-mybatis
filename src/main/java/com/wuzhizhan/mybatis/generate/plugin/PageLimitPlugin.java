package com.wuzhizhan.mybatis.generate.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * Pagination plugin compatible with MBG 1.4+ API.
 * Adds limit/offset fields to Example and appends limit clauses to select statements.
 */
public class PageLimitPlugin extends PluginAdapter {

    private static final FullyQualifiedJavaType INTEGER_TYPE =
            new FullyQualifiedJavaType(Integer.class.getName());
    private static final FullyQualifiedJavaType LONG_TYPE =
            new FullyQualifiedJavaType(Long.class.getName());

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
                                              IntrospectedTable introspectedTable) {
        addField(topLevelClass, "limit", INTEGER_TYPE);
        addField(topLevelClass, "offset", LONG_TYPE);
        return true;
    }

    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element,
                                                                     IntrospectedTable introspectedTable) {
        addLimitElement(element);
        return true;
    }

    @Override
    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element,
                                                                  IntrospectedTable introspectedTable) {
        addLimitElement(element);
        return true;
    }

    private void addLimitElement(XmlElement parent) {
        XmlElement limitCheck = new XmlElement("if");
        limitCheck.addAttribute(new Attribute("test", "limit != null"));

        XmlElement offsetNotNull = new XmlElement("if");
        offsetNotNull.addAttribute(new Attribute("test", "offset != null"));
        offsetNotNull.addElement(new TextElement("limit ${offset}, ${limit}"));
        limitCheck.addElement(offsetNotNull);

        XmlElement offsetNull = new XmlElement("if");
        offsetNull.addAttribute(new Attribute("test", "offset == null"));
        offsetNull.addElement(new TextElement("limit ${limit}"));
        limitCheck.addElement(offsetNull);

        parent.addElement(limitCheck);
    }

    private void addField(TopLevelClass topLevelClass, String name, FullyQualifiedJavaType type) {
        Field field = new Field(name, type);
        field.setVisibility(JavaVisibility.PRIVATE);
        topLevelClass.addField(field);

        Method setter = new Method("set" + capitalize(name));
        setter.setVisibility(JavaVisibility.PUBLIC);
        setter.addParameter(new Parameter(type, name));
        setter.addBodyLine("this." + name + " = " + name + ";");
        topLevelClass.addMethod(setter);

        Method getter = new Method("get" + capitalize(name));
        getter.setVisibility(JavaVisibility.PUBLIC);
        getter.setReturnType(type);
        getter.addBodyLine("return " + name + ";");
        topLevelClass.addMethod(getter);
    }

    private String capitalize(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        if (name.length() == 1) {
            return name.toUpperCase();
        }
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
}

