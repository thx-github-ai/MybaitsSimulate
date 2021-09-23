package com.example.thx.utils;

import com.example.thx.common.Constant;
import com.example.thx.config.Configuration;
import com.example.thx.mapping.MappedStatement;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Classname XmlUtil
 * @Description xml 解析工具类
 * @Date 2021/9/19 14:05
 * @Created by thx
 */
public final class XmlUtil {
    public static void readMapperXml(File fileName, Configuration configuration) {

        try {

            // 创建一个读取器
            SAXReader saxReader = new SAXReader();
            saxReader.setEncoding(Constant.CHARSET_UTF8);

            // 读取文件内容
            Document document = saxReader.read(fileName);

            // 获取xml中的根元素
            Element rootElement = document.getRootElement();

            // 不是mapper根元素的，文件不对
            if (!Constant.XML_ROOT_LABEL.equals(rootElement.getName())) {
                return;
            }

            String namespace = rootElement.attributeValue(Constant.XML_SELECT_NAMESPACE);
            if (namespace != null) {
                Class<?> boundType = null;
                try {
                    boundType = Class.forName(namespace);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (boundType != null) {
                    if (!configuration.hasMapper(boundType)) {
                        configuration.addLoadedResource("namespace:" + namespace);
                        configuration.addMapper(boundType);
                    }
                }
            }

            List<MappedStatement> statements = new ArrayList<>();
            for (Iterator iterator = rootElement.elementIterator(); iterator.hasNext(); ) {
                Element element = (Element) iterator.next();
                String eleName = element.getName();

                MappedStatement statement = new MappedStatement(configuration);

                if (Constant.SELECT.equals(eleName)) {
                    String resultType = element.attributeValue(Constant.XML_SELECT_RESULTTYPE);
                    statement.setResultType(resultType);
                    statement.setSqlCommendType(Constant.SELECT);
                } else if (Constant.UPDATE.equals(eleName)) {
                    statement.setSqlCommendType(Constant.UPDATE);
                } else if (Constant.INSERT.equals(eleName)) {
                    statement.setSqlCommendType(Constant.INSERT);
                } else if (Constant.DELETE.equals(eleName)) {
                    statement.setSqlCommendType(Constant.DELETE);
                } else {
                    // 其他标签自己实现
                    System.err.println("不支持此xml标签解析:" + eleName);
                    statement.setSqlCommendType(Constant.DEFAULT);
                }

                //设置SQL的唯一ID
                String sqlId = namespace + "." + element.attributeValue(Constant.XML_ELEMENT_ID);

                statement.setSqlId(sqlId);
                statement.setNamespace(namespace);
                statement.setSql(element.getStringValue().trim());
                statements.add(statement);
                configuration.addMappedStatement(sqlId, statement);

                //这里其实是在MapperRegistry中生产一个mapper对应的代理工厂
                configuration.addMapper(Class.forName(namespace));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
