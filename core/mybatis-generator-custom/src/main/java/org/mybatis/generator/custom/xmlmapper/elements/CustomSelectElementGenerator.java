package org.mybatis.generator.custom.xmlmapper.elements;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.elements.AbstractXmlElementGenerator;


/**
 * 添加 selectOne、selectList、selectCount、selectAll 四个Sql
 *
 * @author lzj
 * @date 2018/1/6
 */
public class CustomSelectElementGenerator extends
        AbstractXmlElementGenerator {

    public CustomSelectElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        this.appendSelectAll(parentElement);
        parentElement.addElement(new TextElement(""));
        this.appendSelectOne(parentElement);
        parentElement.addElement(new TextElement(""));
        this.appendSelectList(parentElement);
        parentElement.addElement(new TextElement(""));
        this.appendSelectCount(parentElement);
        parentElement.addElement(new TextElement(""));
        this.appendWhere(parentElement);
        parentElement.addElement(new TextElement(""));

    }

    //selectAll
    private void appendSelectAll(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select");
        //select属性，id和 resultMap
        answer.addAttribute(new Attribute(
                "id", "selectAll"));
        answer.addAttribute(new Attribute("resultMap",
                introspectedTable.getBaseResultMapId()));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");

        answer.addElement(new TextElement(sb.toString()));
        answer.addElement(this.getBaseColumnListElement());

        sb.setLength(0);
        sb.append("FROM ");
        sb.append(introspectedTable
                .getAliasedFullyQualifiedTableNameAtRuntime());
        answer.addElement(new TextElement(sb.toString()));

        this.addOrderByElement(answer, sb);

        parentElement.addElement(answer);

    }

    //添加 selectOne
    private void appendSelectOne(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select");
        //select属性，id和 resultMap
        answer.addAttribute(new Attribute(
                "id", "selectOne"));
        answer.addAttribute(new Attribute("resultMap",
                introspectedTable.getBaseResultMapId()));

        //select的 parameterType属性,用parameterMap
        String parameterType = introspectedTable.getBaseRecordType();
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
        sb.append("\n<include refid=\"where_for_select\" />");
        sb.append("\n");
        sb.append("            limit 0,1");
        sb.append("\n");

        answer.addElement(new TextElement(sb.toString()));

        parentElement.addElement(answer);
    }

    //添加 selectList
    private void appendSelectList(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select");
        //select属性，id和 resultMap
        answer.addAttribute(new Attribute(
                "id", "selectList"));
        answer.addAttribute(new Attribute("resultMap",
                introspectedTable.getBaseResultMapId()));

        //select的 parameterType属性
        String parameterType = introspectedTable.getBaseRecordType();
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
        sb.append("\n<include refid=\"where_for_select\" />");
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


    //添加 selectCount
    private void appendSelectCount(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select");
        //select属性，id和 resultMap
        answer.addAttribute(new Attribute(
                "id", "selectCount"));
        answer.addAttribute(new Attribute("resultType",
                "java.lang.Integer"));

        //select的 parameterType属性
        String parameterType = introspectedTable.getBaseRecordType();
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
        sb.append("\n<include refid=\"where_for_select\" />");
        answer.addElement(new TextElement(sb.toString()));

        parentElement.addElement(answer);
    }

    //添加where条件，作为一个 <sql id="where_for_select" />
    private void appendWhere(XmlElement answer) {
        XmlElement whereSql = new XmlElement("sql");
        whereSql.addAttribute(new Attribute("id", "where_for_select"));
        answer.addElement(whereSql);

        XmlElement dynamicElement = new XmlElement("trim");
        whereSql.addElement(dynamicElement);

        dynamicElement.addAttribute(new Attribute("prefix", "WHERE"));
        dynamicElement.addAttribute(new Attribute("prefixOverrides", "AND | OR "));
//        dynamicElement.addAttribute(new Attribute("suffixOverrides", ","));

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
            sb.append("`");
            sb.append(MyBatis3FormattingUtilities
                    .getEscapedColumnName(introspectedColumn));
            sb.append("`");
            sb.append(" = "); //$NON-NLS-1$
            sb.append(MyBatis3FormattingUtilities
                    .getParameterClause(introspectedColumn));

            isNotNullElement.addElement(new TextElement(sb.toString()));
        }

    }

    //添加 order by 和 limit
    private void addOrderByElement(XmlElement answer, StringBuilder sb) {
        sb.setLength(0);
        sb.append("\n");
        sb.append("ORDER BY id DESC");
        sb.append("\n");
        sb.append("<if test=\"offset != null and limit != null\">");
        sb.append("\n");
        sb.append("            limit #{offset},#{limit}");
        sb.append("\n");
        sb.append("        </if>");

        answer.addElement(new TextElement(sb.toString()));
    }


}