package org.mybatis.generator.custom.xmlmapper.elements;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.custom.config.CustomTableConfiguration;
import org.mybatis.generator.custom.config.SearchCondition;

import java.util.List;


/**
 * 添加 searchOne、searchList、searchCount 三个Sql
 *
 * @author lzj
 * @date 2018/1/6
 */
public class CustomSearchElementGenerator extends
        AbstractXmlElementGenerator {

    public CustomSearchElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        this.appendSearchOne(parentElement);
        parentElement.addElement(new TextElement(""));
        this.appendSearchList(parentElement);
        parentElement.addElement(new TextElement(""));
        this.appendSearchCount(parentElement);
        parentElement.addElement(new TextElement(""));
        this.appendWhere(parentElement);
        parentElement.addElement(new TextElement(""));

    }

    //添加 searchOne
    private void appendSearchOne(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select");
        //select属性，id和 resultMap
        answer.addAttribute(new Attribute(
                "id", "searchOne"));
        answer.addAttribute(new Attribute("resultMap",
                introspectedTable.getBaseResultMapId()));

        //select的 parameterType属性,用parameterMap
//        String parameterType = introspectedTable.getBaseRecordType();
        answer.addAttribute(new Attribute("parameterMap", introspectedTable.getBaseResultMapId()));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT "); //$NON-NLS-1$

        answer.addElement(new TextElement(sb.toString()));
        answer.addElement(getBaseColumnListElement());

        sb.setLength(0);
        sb.append("FROM "); //$NON-NLS-1$
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());
        sb.append("\n<include refid=\"where_for_search\" />");
        sb.append("\n");
        sb.append("            limit 0,1");
        sb.append("\n");

        answer.addElement(new TextElement(sb.toString()));

        parentElement.addElement(answer);
    }

    //添加 searchList
    private void appendSearchList(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select");
        //select属性，id和 resultMap
        answer.addAttribute(new Attribute(
                "id", "searchList"));
        answer.addAttribute(new Attribute("resultMap",
                introspectedTable.getBaseResultMapId()));

        //select的 parameterType属性
//        String parameterType = introspectedTable.getBaseRecordType();
        answer.addAttribute(new Attribute("parameterMap", introspectedTable.getBaseResultMapId()));
        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT "); //$NON-NLS-1$

        answer.addElement(new TextElement(sb.toString()));
        answer.addElement(getBaseColumnListElement());

        sb.setLength(0);
        sb.append("FROM "); //$NON-NLS-1$
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());
        sb.append("\n<include refid=\"where_for_search\" />");
        sb.append("\n");
        sb.append("ORDER BY id DESC");
        sb.append("\n");
        sb.append("<if test=\"offset != null and limit != null\">");
        sb.append("\n");
        sb.append("            limit #{offset},#{limit}");
        sb.append("\n");
        sb.append("        </if>");

        answer.addElement(new TextElement(sb.toString()));

        parentElement.addElement(answer);
    }


    //添加 searchCount
    private void appendSearchCount(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select");
        //select属性，id和 resultMap
        answer.addAttribute(new Attribute(
                "id", "searchCount"));
        answer.addAttribute(new Attribute("resultType",
                "java.lang.Integer"));

        //select的 parameterType属性
//        String parameterType = introspectedTable.getBaseRecordType();
        answer.addAttribute(new Attribute("parameterMap", introspectedTable.getBaseResultMapId()));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");

        answer.addElement(new TextElement(sb.toString()));
        answer.addElement(new TextElement(" count(*) "));

        sb.setLength(0);
        sb.append("FROM "); //$NON-NLS-1$
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());
        sb.append("\n<include refid=\"where_for_search\" />");
        answer.addElement(new TextElement(sb.toString()));

        parentElement.addElement(answer);
    }

    //添加where条件，作为一个 <sql id="where_for_search" />
    private void appendWhere(XmlElement answer) {
        XmlElement whereSql = new XmlElement("sql");
        whereSql.addAttribute(new Attribute("id", "where_for_search"));
        answer.addElement(whereSql);

        XmlElement dynamicElement = new XmlElement("trim");
        whereSql.addElement(dynamicElement);

        dynamicElement.addAttribute(new Attribute("prefix", "WHERE"));
        dynamicElement.addAttribute(new Attribute("prefixOverrides", "AND | OR "));
//        dynamicElement.addAttribute(new Attribute("suffixOverrides", ","));

        CustomTableConfiguration customTableConfiguration = (CustomTableConfiguration) introspectedTable.getTableConfiguration();
        List<SearchCondition> conditionList = customTableConfiguration.getConditionList();
        if (conditionList == null || conditionList.isEmpty()) {
            this.appendEquals(dynamicElement);
        } else {
            this.appendSearchCondition(dynamicElement, conditionList);
        }

    }

    //模糊查询
    private void appendSearchCondition(XmlElement parent, List<SearchCondition> conditionList) {
        StringBuilder sb = new StringBuilder();
        for (SearchCondition condition : conditionList) {
            XmlElement searchElement = new XmlElement("if");
            parent.addElement(searchElement);
            sb.setLength(0);
            sb.append(condition.getProperty());
            sb.append(" != null");
            searchElement.addAttribute(new Attribute("test", sb.toString()));

            //2. if标签内容
            sb.setLength(0);
            if (condition.getIsAnd().equalsIgnoreCase("true")) {
                sb.append("AND ");
            } else {
                sb.append("OR ");
            }
            sb.append("`").append(condition.getColumn()).append("` ");
            if (condition.getOperation().equalsIgnoreCase("between")) {
                sb.append(" ").append(condition.getOperation()).append(" ");
                sb.append("#{").append(condition.getValue()).append("}").append(" and ");
                sb.append("#{").append(condition.getSecondValue()).append("}");
            } else if (condition.getOperation().equalsIgnoreCase("like")) {
                //LIKE CONCAT('%',#{desc, jdbcType=VARCHAR},'%')
                sb.append(" LIKE CONTACT(#{").append(condition.getProperty()).append("},'%')");
            } else {
                sb.append(" ").append(condition.getOperation()).append(" ");
                sb.append(" #{").append(condition.getProperty()).append("} ");
            }
            searchElement.addElement(new TextElement(sb.toString()));
        }

    }

    //精确查询
    private void appendEquals(XmlElement dynamicElement) {
        StringBuilder sb = new StringBuilder();
        String javaPropertyName;
        for (IntrospectedColumn introspectedColumn : ListUtilities.removeGeneratedAlwaysColumns(introspectedTable
                .getNonPrimaryKeyColumns())) {

            //1. if标签属性
            sb.setLength(0);
            javaPropertyName = introspectedColumn.getJavaProperty();
            sb.append(javaPropertyName);
            sb.append(" != null");
            //list的话就再判断size >0
            if (javaPropertyName.contains("list") || javaPropertyName.contains("List")) {
                sb.append(" and ").append(javaPropertyName).append(".size() > 0");
            }
            XmlElement isNotNullElement = new XmlElement("if");
            isNotNullElement.addAttribute(new Attribute("test", sb.toString()));
            dynamicElement.addElement(isNotNullElement);

            //2. if标签内容
            sb.setLength(0);
            sb.append("AND ");
            sb.append(MyBatis3FormattingUtilities
                    .getEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities
                    .getParameterClause(introspectedColumn));

            isNotNullElement.addElement(new TextElement(sb.toString()));
        }
    }

}