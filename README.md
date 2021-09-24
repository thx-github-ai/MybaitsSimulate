## mybatis 框架仿写：

**一下是项目结构：**

![在这里插入图片描述](https://img-blog.csdnimg.cn/87b00e26166347fab5ee0f1bf18fb71f.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAX-WKquWKm-WKquWKm-WGjeWKquWKm18=,size_12,color_FFFFFF,t_70,g_se,x_16)
![在这里插入图片描述](https://img-blog.csdnimg.cn/de2609e908cf4a8a8463c916584d7166.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAX-WKquWKm-WKquWKm-WGjeWKquWKm18=,size_12,color_FFFFFF,t_70,g_se,x_16)

**仿写 mybatis 框架，第一步，创建 SqlSession 工厂：**
**这里 debug 调试一步步跟随**
![在这里插入图片描述](https://img-blog.csdnimg.cn/fd71618173574d738097b00768732067.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAX-WKquWKm-WKquWKm-WGjeWKquWKm18=,size_20,color_FFFFFF,t_70,g_se,x_16)
**这里使用工厂模式，创建 SqlSession**![在这里插入图片描述](https://img-blog.csdnimg.cn/97a4cce5f3554a3e9ec21f1bc89c3438.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAX-WKquWKm-WKquWKm-WGjeWKquWKm18=,size_20,color_FFFFFF,t_70,g_se,x_16)
**SqlSessionFactory 的实现类：解析 .properties 配置文件，替换路径**
![在这里插入图片描述](https://img-blog.csdnimg.cn/429033f949384a08b881ab572788ad7a.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAX-WKquWKm-WKquWKm-WGjeWKquWKm18=,size_20,color_FFFFFF,t_70,g_se,x_16)

**这里解析 UserMapper.xml 文件，拿到 namespace，sql 标签，sql 语句等，封装成一个个 Statement 对象**

![在这里插入图片描述](https://img-blog.csdnimg.cn/d9469e5f3a7a4d3799f5ea4c54908028.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAX-WKquWKm-WKquWKm-WGjeWKquWKm18=,size_20,color_FFFFFF,t_70,g_se,x_16)

![在这里插入图片描述](https://img-blog.csdnimg.cn/d384f3bf1ba84c10bfde41a0f908713e.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAX-WKquWKm-WKquWKm-WGjeWKquWKm18=,size_20,color_FFFFFF,t_70,g_se,x_16)

**然后开始创建会话：**
![在这里插入图片描述](https://img-blog.csdnimg.cn/133cdf2bc4bc4401a1ade751c9b2071d.png)


**通过调用 SimpleExecutor 来获取数据库连接：**
![在这里插入图片描述](https://img-blog.csdnimg.cn/8a152b9030ee4d78b01a17ef6158be08.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAX-WKquWKm-WKquWKm-WGjeWKquWKm18=,size_20,color_FFFFFF,t_70,g_se,x_16)
**创建会话结束，紧接着开始动态代理获得 mapper 对象，执行 sql 语句，拿到执行结果了**
**这里使用单例设计模式，新建一个 Instance 对象**

![在这里插入图片描述](https://img-blog.csdnimg.cn/82aec648e51f4bf097ef9b217cdd5ca1.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAX-WKquWKm-WKquWKm-WGjeWKquWKm18=,size_20,color_FFFFFF,t_70,g_se,x_16)
**mapper 的代理对象继承 InvocarionHandler，完成动态代理** 



**开始执行 sql 语句：**

**调用代理对象的 invoke 方法：**
![在这里插入图片描述](https://img-blog.csdnimg.cn/82b9b8890c854d6ab36f68291f07af9a.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAX-WKquWKm-WKquWKm-WGjeWKquWKm18=,size_20,color_FFFFFF,t_70,g_se,x_16)

**判断 sql 标签**
![在这里插入图片描述](https://img-blog.csdnimg.cn/20f9bf1f9ebb4297a1e5b64ea726b8b3.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAX-WKquWKm-WKquWKm-WGjeWKquWKm18=,size_20,color_FFFFFF,t_70,g_se,x_16)
**由于实现一级缓存，所以在 BaseExecutor 里面，创建缓存**

![在这里插入图片描述](https://img-blog.csdnimg.cn/59d663f867ea4d4bbbb2a1135f72cdf7.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAX-WKquWKm-WKquWKm-WGjeWKquWKm18=,size_20,color_FFFFFF,t_70,g_se,x_16)
**如果缓存等于空，则去数据库查找，如果缓存存在，直接输出缓存即可**
![在这里插入图片描述](https://img-blog.csdnimg.cn/b0893c9d9cb143409d3bfd840b548c81.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAX-WKquWKm-WKquWKm-WGjeWKquWKm18=,size_20,color_FFFFFF,t_70,g_se,x_16)
**到数据库查找，实际上就是 JDBC 的代码：**

![在这里插入图片描述](https://img-blog.csdnimg.cn/16b21add6bbc4054b0d219338d25eef1.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAX-WKquWKm-WKquWKm-WGjeWKquWKm18=,size_20,color_FFFFFF,t_70,g_se,x_16)

**这里通过返回类型，判断对象类型，并放入 resultSet 结果集中。**
```java
@Override
    public <E> List<E> handleResultSet(ResultSet resultSet) {
        if(resultSet != null) {
            try {
                String returnType = mappedStatement.getResultType();
                if (returnType.equals("Integer") || returnType.equals("Long")) {
                    Class<?> entityClass = Class.forName("java.lang.Integer");
                    int count = 0;
                    while (resultSet.next()){
                        count = resultSet.getInt("count(*)");
                    }
                    E entity = (E) new Integer(count);
                    List<E> countList = new ArrayList<>();
                    countList.add((E) entity);
                    return countList;

                }
                List<E> resultList = new ArrayList<>();
                while(resultSet.next()){
                    Class<?> entityClass = Class.forName(mappedStatement.getResultType());
                    E entity = (E) entityClass.newInstance();
                    Field[] declaredFields = entityClass.getDeclaredFields();
                    for (Field field : declaredFields) {
                        field.setAccessible(true);
                        Class<?> type = field.getType();
//                      如果是 String 或者 int 类型，设置返回结果集
                        if(String.class.equals(type)){
                            field.set(entity,resultSet.getString(field.getName()));
                        }
                        else if(int.class.equals(type)){
                            field.set(entity,resultSet.getInt(field.getName()));
                        }
                        //其他类型
                        else{
                            field.set(entity,resultSet.getObject(field.getName()));
                        }
                    }
                    resultList.add(entity);
                }
                return resultList;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
```

**执行完毕，拿到闭环结果**
![在这里插入图片描述](https://img-blog.csdnimg.cn/5e5e3f476be94ae3bb2697a54c6b76dc.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBAX-WKquWKm-WKquWKm-WGjeWKquWKm18=,size_20,color_FFFFFF,t_70,g_se,x_16)
![在这里插入图片描述](https://img-blog.csdnimg.cn/4a4ac4971ab84c06aef97acbb72ba457.png)
