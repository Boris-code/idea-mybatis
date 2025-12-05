package com.wuzhizhan.mybatis.generate.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * Adds support for "for update" clause compatible with MBG 1.4+ APIs.
 */
public class ForUpdatePlugin extends PluginAdapter {

    private static final FullyQualifiedJavaType BOOLEAN_WRAPPER =
            FullyQualifiedJavaType.getBooleanPrimitiveInstance().getPrimitiveTypeWrapper();

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass,
                                              IntrospectedTable introspectedTable) {
        addField(topLevelClass, "forUpdate", BOOLEAN_WRAPPER);
        return true;
    }

    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element,
                                                                     IntrospectedTable introspectedTable) {
        addForUpdateElement(element);
        return true;
    }

    @Override
    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element,
                                                                  IntrospectedTable introspectedTable) {
        addForUpdateElement(element);
        return true;
    }

    private void addField(TopLevelClass topLevelClass, String name, FullyQualifiedJavaType type) {
        Field field = new Field(name, type);
        field.setVisibility(JavaVisibility.PRIVATE);
        topLevelClass.addField(field);

        // setter
        Method setter = new Method("set" + capitalize(name));
        setter.setVisibility(JavaVisibility.PUBLIC);
        setter.addParameter(new Parameter(type, name));
        setter.addBodyLine("this." + name + " = " + name + ";");
        topLevelClass.addMethod(setter);

        // getter
        Method getter = new Method("get" + capitalize(name));
        getter.setVisibility(JavaVisibility.PUBLIC);
        getter.setReturnType(type);
        getter.addBodyLine("return " + name + ";");
        topLevelClass.addMethod(getter);
    }

    private void addForUpdateElement(XmlElement parent) {
        XmlElement forUpdate = new XmlElement("if");
        forUpdate.addAttribute(new Attribute("test", "forUpdate != null and forUpdate == true"));
        forUpdate.addElement(new TextElement("for update"));
        parent.addElement(forUpdate);
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

