package com.wuzhizhan.mybatis.generate.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

/**
 * Adds Swagger annotations (@ApiModel and @ApiModelProperty) to model classes.
 */
public class SwaggerAnnotationPlugin extends PluginAdapter {

    private static final FullyQualifiedJavaType API_MODEL_TYPE =
            new FullyQualifiedJavaType("io.swagger.annotations.ApiModel");
    private static final FullyQualifiedJavaType API_MODEL_PROPERTY_TYPE =
            new FullyQualifiedJavaType("io.swagger.annotations.ApiModelProperty");

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        addApiModelAnnotation(topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public boolean modelFieldGenerated(Field field,
                                       TopLevelClass topLevelClass,
                                       IntrospectedColumn introspectedColumn,
                                       IntrospectedTable introspectedTable,
                                       ModelClassType modelClassType) {
        addApiModelPropertyAnnotation(field, introspectedColumn);
        return true;
    }

    /**
     * 添加 @ApiModel 注解到类上
     * 格式：@ApiModel(value="包名.类名表注释")
     */
    private void addApiModelAnnotation(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addImportedType(API_MODEL_TYPE);
        topLevelClass.addImportedType(API_MODEL_PROPERTY_TYPE);

        String packageName = topLevelClass.getType().getPackageName();
        String className = topLevelClass.getType().getShortName();
        String tableRemark = introspectedTable.getRemarks();

        // 构建 value 值：包名.类名 + 表注释
        StringBuilder valueBuilder = new StringBuilder();
        valueBuilder.append(packageName).append(".").append(className);
        if (tableRemark != null && !tableRemark.trim().isEmpty()) {
            valueBuilder.append(tableRemark);
        }

        String annotation = String.format("@ApiModel(value=\"%s\")", valueBuilder.toString());
        topLevelClass.addAnnotation(annotation);
    }

    /**
     * 添加 @ApiModelProperty 注解到字段上
     * 格式：@ApiModelProperty(value="字段注释")
     */
    private void addApiModelPropertyAnnotation(Field field, IntrospectedColumn introspectedColumn) {
        String comment = getFieldComment(introspectedColumn);
        // 转义引号，避免在注释中包含引号时出错
        comment = comment.replace("\"", "\\\"");
        field.addAnnotation("@ApiModelProperty(value=\"" + comment + "\")");
    }

    /**
     * 获取字段注释，如果没有注释则使用字段名
     */
    private String getFieldComment(IntrospectedColumn introspectedColumn) {
        String remarks = introspectedColumn.getRemarks();
        if (remarks != null && !remarks.trim().isEmpty()) {
            return remarks;
        }
        // 如果没有注释，使用字段名
        return introspectedColumn.getJavaProperty();
    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        addApiModelAnnotation(topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass,
                                                       IntrospectedTable introspectedTable) {
        addApiModelAnnotation(topLevelClass, introspectedTable);
        return true;
    }
}

