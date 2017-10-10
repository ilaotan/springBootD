//package com.springBootD.test.mongoTest;
//
//import java.util.Date;
//
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.mongodb.repository.MongoRepository;
//import org.springframework.stereotype.Repository;
//
//import com.sonli.mpushAdmin.model.PushLogM;
//
//@Repository
//public interface PushLogRepository extends MongoRepository<PushLogM, String> {
//
//
//    PushLogM findByName(String name);
//
//
//    Page<PushLogM> findByCreateTimeBetween(Date begin, Date end, Example<PushLogM> example, Pageable pageable);
//}
