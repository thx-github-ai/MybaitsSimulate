package com.example.thx.session;

import com.example.thx.common.Constant;
import com.example.thx.config.Configuration;
import com.example.thx.utils.XmlUtil;

import java.io.File;
import java.net.URL;

/**
 * @Classname DefaultSqlSessionFactory
 * @Description
 * @Date 2021/9/19 14:03
 * @Created by thx
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory{
    private final Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration){
        this.configuration = configuration;
        /**
         * 加载mapper文件，这里规定xml文件和mapper接口必须在同一包下
         */
        loadMapperInfo(Configuration.getProperty(Constant.MAPPER_LOCATION).replaceAll("\\.","/"));
        }

    @Override
    public SqlSession openSession() {

        //默认返回一个DefaultSqlSession对象，创建过程中会给定一个默认的执行器SimpleExecutor，进去看看
        return new DefaultSqlSession(this.configuration);
    }


    private void loadMapperInfo(String mapperLocation){
        URL resource = DefaultSqlSessionFactory.class.getClassLoader().getResource(mapperLocation);
        File mapperDir = new File(resource.getFile());

        //判断给出是一个目录还是单独一个xml文件
        if(mapperDir.isDirectory()){
            File[] files = mapperDir.listFiles();
            for (File file : files) {
                //递归遍历文件夹内容
                if(file.isDirectory()){
                    loadMapperInfo(mapperLocation+"/"+file.getName());
                }
                else if (file.getName().endsWith(Constant.MAPPER_FILE_SUFFIX)){
                    if(!this.configuration.isLoadedResource(file.getName())){
                        this.configuration.addLoadedResource(file.getName());

                        //解析xml文件
                        XmlUtil.readMapperXml(file,this.configuration);
                    }
                }
                else{
                    try {
                        String temp = file.getPath().replace("\\", ".");
                        temp = temp.substring(temp.indexOf(".classes")+9,temp.lastIndexOf(".class"));
                        Class<?> type = Class.forName(temp);
                        if(type!=null){
                            this.configuration.addMapper(type);
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        else{
            XmlUtil.readMapperXml(mapperDir,this.configuration);
        }
    }
}
