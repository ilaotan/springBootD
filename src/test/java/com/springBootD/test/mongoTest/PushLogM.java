//package com.springBootD.test.mapper;
//
//
//import java.util.Date;
//
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.index.Indexed;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
////可以指定mongo的表名 如果不指定 与类名相同
//@Document(collection = "PushLogM")
////声明复合索引
////@CompoundIndexes({@CompoundIndex(name = "age_idx", def = "{'name': 1, 'age': -1}", unique = true)})
//public class PushLogM {
//
//    @Id
//    private String id;
//
//    private String name;
//
//    private Integer age;
//
//    //- 声明该字段需要索引，建索引可以大大的提高查询效率。
//    @Indexed
//    private Date createTime;
//
//    private Date updateTime;
//
//    //- 声明构造函数，作用是把从数据库取出的数据实例化为对象。该构造函数传入的值为从DBObject中取出的数据。
//    //@PersistenceConstructor
//
//    //与mapper插件相同 忽略本字段 不存入数据库
////    @Transient
//
//    //- 声明该字段为地理信息的索引。
//    //@GeoSpatialIndexed
//}
