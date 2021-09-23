package com.example.thx.mapping;

import com.example.thx.annotation.Delete;
import com.example.thx.annotation.Insert;
import com.example.thx.annotation.Select;
import com.example.thx.annotation.Update;
import com.example.thx.config.Configuration;
import com.example.thx.utils.XmlUtil;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * @Classname MapperAnnotationBuilder
 * @Description
 * @Date 2021/9/19 14:01
 * @Created by thx
 */
public class MapperAnnotationBuilder {
    private static final Set<Class<? extends Annotation>> SQL_COMMAND_TYPES = new HashSet<>();

    private final Configuration configuration;

    private final Class<?> type;

    static {
        SQL_COMMAND_TYPES.add(Select.class);
        SQL_COMMAND_TYPES.add(Delete.class);
        SQL_COMMAND_TYPES.add(Update.class);
        SQL_COMMAND_TYPES.add(Insert.class);
    }

    public MapperAnnotationBuilder(Configuration configuration, Class<?> type) {
        this.configuration = configuration;
        this.type = type;
    }

    public void parse() {
        //判断是否已经解析过了
        //interface 前缀表示接口已经解析    namespace 前缀表示xml文件已经解析
        String resource = type.toString();
        if(!configuration.isLoadedResource(resource)){
            configuration.addLoadedResource(resource);
            Method[] methods = type.getMethods();
            for (Method method : methods) {
                parseStatement(method);
            }

            //解析xml文件
            loadXmlResource();
        }
    }

    public void loadXmlResource() {
        if(!this.configuration.isLoadedResource("namespace:"+type.getName())){
            //把已经加载的资源放到configuration里面
            this.configuration.addLoadedResource("namespace:"+type.getName());
            String resource = type.getName().replaceAll("\\.","/")+".xml";
            this.configuration.addLoadedResource(resource.substring(resource.lastIndexOf("/")+1));
            URL url = MapperAnnotationBuilder.class.getClassLoader().getResource(resource);
            if(url==null){
                return;
            }
            //解析同一包路径下的xml文件
            XmlUtil.readMapperXml(new File(url.getFile()),configuration);
        }

    }


//    该方法把注解解析成一个个的MappedStatement对象

    public void parseStatement(Method method){
        try {
            Class<? extends Annotation> sqlCommandType = getSqlCommandType(method);
            if(sqlCommandType==null){
                return;
            }
            Annotation annotation = method.getAnnotation(sqlCommandType);
            String sqlString = (String) annotation.getClass().getMethod("value").invoke(annotation);
            String statementId = type.getName()+"."+method.getName();
            MappedStatement mappedStatement = new MappedStatement(configuration);
            mappedStatement.setNamespace(type.getName());
            Type returnType = method.getGenericReturnType();
            if(returnType instanceof ParameterizedType){
                Type[] actualTypeArguments = ((ParameterizedType) returnType).getActualTypeArguments();
                mappedStatement.setResultType(actualTypeArguments[0].getTypeName());
            }
            else{
                mappedStatement.setResultType(returnType.getTypeName());
            }
            mappedStatement.setSqlCommendType(sqlCommandType.getSimpleName().toLowerCase());
            mappedStatement.setSqlId(statementId);
            mappedStatement.setSql(sqlString);
            this.configuration.addMappedStatement(statementId,mappedStatement);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Class<? extends Annotation> getSqlCommandType(Method method){
        for (Class<? extends Annotation> sqlCommandType : SQL_COMMAND_TYPES) {
            Annotation annotation = method.getAnnotation(sqlCommandType);
            if(annotation!=null){
                return sqlCommandType;
            }
        }
        return null;
    }
}
