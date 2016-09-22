//package com.springBootD.test.service;
//
//
//import com.google.common.collect.Lists;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;

//
//import java.util.List;
//
//@Repository
//public class UserTallyRepository implements IUserTallyRepository {
//
//    @Autowired
//    private UserTallyMapper usertallyMapper;
//
//    @Autowired
//    private UserTallyCache usertallyCache;
//    @Autowired
//    private UserTallyTwoCache userTallyTwoCache;
//
//    @Override
//    public boolean insert(UserTally usertally) {
//        int num = usertallyMapper.insert(usertally);
//        if(num>0){
//            usertallyCache.save(usertally);
//            userTallyTwoCache.saveOne(CacheTwoPrefix.findUserTallyByUserId_prefix,usertally.getUserId(),usertally.getId());
//        }
//        return num>0;
//    }
//
//    @Override
//    public boolean update(UserTally usertally) {
//        //先删除再插入
//        return delete(usertally) && insert(usertally);
//    }
//
//    @Override
//    public boolean delete(UserTally userTally) {
//        int num = usertallyMapper.deleteByPrimaryKey(userTally.getId());
//        if(num>0){
//            usertallyCache.delete(userTally.getId());
//            userTallyTwoCache.deleteByRelationId(CacheTwoPrefix.findUserTallyByUserId_prefix,userTally.getUserId(),userTally.getId());
//
//        }
//        return num>0;
//    }
//
//    @Override
//    public boolean logicDelete(String id) {
//        int num =  usertallyMapper.logicDelete(id);
//        if(num>0){
//            usertallyCache.delete(id);
//        }
//        return num>0;
//    }
//
//    @Override
//    public List<UserTally> findByIdList(List<String> idList) {
//        List<UserTally> needReturnList = Lists.newArrayList();
//        UserTally userTally;
//        for(String id:idList){
//            userTally = getByUsertallyId(id);
//            if(userTally!=null){
//                needReturnList.add(userTally);
//            }
//        }
//        return needReturnList.size()>0?needReturnList:null;
//    }
//
//    @Override
//    public List<UserTally> searchUsertallyForPage(UserTally usertally){
//    return usertallyMapper.searchUsertallyForPage(usertally);
//    }
//
//    @Override
//    public UserTally getByUsertallyId(String id){
//        UserTally usertally = usertallyCache.getById(id);
//        if(usertally==null){
//            usertally = usertallyMapper.getByUsertallyId(id);
//            if(usertally!=null){
//                usertallyCache.save(usertally);
//            }
//        }
//        return usertally;
//    }
//
//    @Override
//    public UserTally getUserTallyByKeyIdAndUserId(String keyId, String userId) {
//
//        return usertallyMapper.getUserTallyByKeyIdAndUserId(keyId,userId);
//    }
//
//    @Override
//    public List<UserTally> findUserTallyByUserId(String userId) {
//
//        List<String> idList = userTallyTwoCache.getByRelationId(CacheTwoPrefix.findUserTallyByUserId_prefix,userId);
//        if(idList!=null ){
//            return findByIdList(idList);
//        }
//
//        List<UserTally> userTallyList = usertallyMapper.findUserTallyByUserId(userId);
//        if(userTallyList!=null && userTallyList.size()>0){
//            //更新二级缓存
//            List<String> ids = Lists.newArrayList();
//            for(UserTally userTally:userTallyList){
//                //无需更新一级缓存 拿的时候会更新一级缓存
//                ids.add(userTally.getId());
//            }
//            userTallyTwoCache.saveList(CacheTwoPrefix.findUserTallyByUserId_prefix,userId,ids);
//        }
//
//        return userTallyList;
//    }
//
//}