package com.example.thx.common;

/**
 * @Classname Constant
 * @Description 常量
 * @Date 2021/9/19 13:54
 * @Created by thx
 */
public interface Constant {
//    配置文件中 的配置信息
    String MAPPER_LOCATION = "mapper.location";
    String DB_DRIVER_CONF = "db.driver";

    String DB_URL_CONF = "db.url";

    String DB_USERNAME_CONF = "db.username";

    String db_PASSWORD = "db.password";


//      utf - 8 编码
    String CHARSET_UTF8 = "UTF-8";

//    文件后缀
    String MAPPER_FILE_SUFFIX = ".xml";

    String XML_ROOT_LABEL = "mapper";

    String XML_ELEMENT_ID = "id";

    String XML_SELECT_NAMESPACE = "namespace";

    String XML_SELECT_RESULTTYPE = "resultType";

    /** sqlType相关常量 */
    String SELECT = "select";

    String DELETE = "delete";

    String UPDATE = "update";

    String INSERT = "insert";

    String DEFAULT = "default";
}
