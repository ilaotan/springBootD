//package com.springBootD.test.mapper;
//
//import java.util.Date;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.ExampleMatcher;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.BasicQuery;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.mongodb.core.query.Update;
//
//import com.alibaba.fastjson.JSON;
//import com.sonli.mpushAdmin.dao.PushLogRepository;
//import com.sonli.mpushAdmin.model.PushLogM;
//import com.sonli.mpushAdmin.sonliFramework.utils.DateUtils;
//import com.sonli.mpushAdmin.sonliFramework.utils.UuidUtils;
//import org.junit.Test;
//import test.com.sonli.Base;
//
//import static org.springframework.data.mongodb.core.query.Criteria.where;
//
///**
// *
// * 更多代码参考  https://github.com/spring-projects/spring-data-examples/blob/master/mongodb/query-by-example/src/test/java/example/springdata/mongodb/querybyexample/MongoOperationsIntegrationTests.jav
// *
// *  https://github.com/spring-projects/spring-data-mongodb/blob/master/src/main/asciidoc/reference/mongodb.adoc
// *
// */
//public class MongoDbTest extends Base {
//
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    @Autowired
//    private PushLogRepository pushLogRepository;
//
//    private PushLogM getPushLogM() {
//        PushLogM pushLogM = new PushLogM();
//        pushLogM.setId(UuidUtils.newid());
//        pushLogM.setName("xiaogou");
//        pushLogM.setAge(33);
//        pushLogM.setCreateTime(new Date());
//        pushLogM.setUpdateTime(new Date());
//        return pushLogM;
//
//    }
//
//    @Test
//    public void testInsert() {
//        PushLogM pushLogM = getPushLogM();
//        mongoTemplate.insert(pushLogM);
//    }
//
//    @Test
//    public void testInsert2() {
//        PushLogM pushLogM = getPushLogM();
//        pushLogRepository.insert(pushLogM);
//    }
//
//
//    @Test
//    public void testGetById() {
//        System.out.println(JSON.toJSONString(mongoTemplate.findById("123", PushLogM.class)));
//        System.out.println(JSON.toJSONString(pushLogRepository.findOne("123")));
//    }
//
//
//    @Test
//    public void testQuery1() {
//        int pageNumber = 1;
//        int pageSize = 10;
//
//        System.out.println(JSON.toJSONString(pushLogRepository.findAll()));
//        Sort sort = new Sort(Sort.Direction.DESC, "age");
//        System.out.println(JSON.toJSONString(pushLogRepository.findAll(new PageRequest(pageNumber - 1, pageSize,
//                sort))));
//
//    }
//
//    @Test
//    public void testQuery2() {
//        int pageNumber = 1;
//        int pageSize = 10;
//        Sort sort = new Sort(Sort.Direction.DESC, "age");
//
//
//        PushLogM pushLogM = new PushLogM();
//        pushLogM.setAge(33);
//        pushLogM.setName("xiaogou");
//
//        ExampleMatcher matcher = ExampleMatcher.matching()
//                .withIgnoreNullValues();
//
//        Example<PushLogM> example = Example.of(pushLogM, matcher);
//
//
//        System.out.println(JSON.toJSONString(pushLogRepository.findAll(example, new PageRequest(pageNumber - 1,
//                pageSize, sort))));
//
//        Date begin = DateUtils.parseDate("2017-10-10 11:20:00");
//        Date end = DateUtils.parseDate("2017-10-11");
//
//        System.out.println(JSON.toJSONString(pushLogRepository.findByCreateTimeBetween(begin, end, example, new
//                PageRequest(pageNumber - 1,
//                pageSize, sort))));
//
//    }
//
//    @Test
//    public void testQuery3() {
//        Query query = new Query(where("name").is("xiaogou"));
//        Update update = new Update().currentDate("updateTime");
//        PushLogM p = mongoTemplate.findAndModify(query, update, PushLogM.class); // return's old person object
//
//    }
//
//
//    @Test
//    public void testQuery4() {
//
//        int pageNumber = 1;
//        int pageSize = 10;
//        Sort sort = new Sort(Sort.Direction.DESC, "age");
//        Pageable pageable = new PageRequest(pageNumber - 1, pageSize, sort);
//
//
//        final Query q = Query.query(where("age").gt(40)).with(pageable);
//        System.out.println(mongoTemplate.count(q, PushLogM.class));
//        System.out.println(JSON.toJSONString(mongoTemplate.find(q, PushLogM.class)));
//
//        //如果涉及到同一字段取区间数据 需要这么写
//        Criteria c2 = Criteria.where("age").gt(40)
//                .andOperator(
//                        Criteria.where("age").lt(50)
//                );
//        //上面这个可以写一起 这里主要为了看最后拼装的结果
//        final Query q2 = Query.query(c2).with(pageable);
//        System.out.println("拼装方式2 " + c2.getCriteriaObject());
//        System.out.println(mongoTemplate.count(q2, PushLogM.class));
//        System.out.println(JSON.toJSONString(mongoTemplate.find(q2, PushLogM.class)));
//
//        //另一种写法 这种从结果上来来看 比较正规
//        Criteria c3 = new Criteria().andOperator(
//                Criteria.where("age").lt(50),
//                Criteria.where("age").gt(40)
//        );
//        final Query q3 = Query.query(c3).with(pageable);
//        System.out.println("拼装方式3 " + c3.getCriteriaObject());
//        System.out.println(mongoTemplate.count(q3, PushLogM.class));
//        System.out.println(JSON.toJSONString(mongoTemplate.find(q3, PushLogM.class)));
//
//        //自己拼装分页
//        final Query q4 = Query.query(c3).with(sort).skip(pageSize * (pageNumber - 1)).limit(pageSize);
//        System.out.println(mongoTemplate.count(q4, PushLogM.class));
//        System.out.println(JSON.toJSONString(mongoTemplate.find(q4, PushLogM.class)));
//    }
//
//    @Test
//    public void testQuery5() {
//        BasicQuery query = new BasicQuery("{ age : { $gt : 50 }}");
//        List<PushLogM> result = mongoTemplate.find(query, PushLogM.class);
//        System.out.println(JSON.toJSONString(result));
//    }
//
//}
