//package com.springBootD.test.service;
//
//import com.alibaba.fastjson.JSON;
//import com.google.common.collect.Lists;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.stereotype.Component;
//import redis.clients.jedis.JedisCluster;

//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//
///**
//
// */
//@Component
//public class UserTallyTwoCache{
//
//    private static int expire= RedisCluster.EXPIRE_LEVEL1;
//
//
//
//    /**
//     * @param relationKey 关联Id
//     * @param id    要缓存的主键Id
//     */
//    public void saveOne(String keyPrefix, String relationKey, String id){
//
//        JedisCluster jedisCluster = RedisCluster.getInstance();
//        String jsonStr = jedisCluster.get(keyPrefix+relationKey);
//        List<String> idList;
//        if(StringUtils.isNotBlank(jsonStr)){
//            idList = JSON.parseArray(jsonStr,String.class);
//            if(!idList.contains(id)){
//                idList.add(id);
//            }
//        }else{
//            idList = Lists.newArrayList();
//            idList.add(id);
//        }
//        jedisCluster.set(keyPrefix+relationKey, JSON.toJSONString(idList));
//        jedisCluster.expire(keyPrefix+relationKey, expire);
//    }
//
//    /**
//     *
//     * 二级缓存 保存多个Id
//     *
//     * @param keyPrefix
//     * @param relationKey
//     * @param ids
//     */
//    public void saveList(String keyPrefix, String relationKey, List<String> ids){
//
//        JedisCluster jedisCluster = RedisCluster.getInstance();
//        String jsonStr = jedisCluster.get(keyPrefix+relationKey);
//        List<String> idList;
//
//        if(StringUtils.isNotBlank(jsonStr)){
//            idList = JSON.parseArray(jsonStr,String.class);
//            idList.addAll(ids);
//        }else{
//            idList = ids;
//        }
//        //去重复
//        idList = new ArrayList<>(new HashSet<>(idList));
//
//        jedisCluster.set(keyPrefix+relationKey, JSON.toJSONString(idList));
//        jedisCluster.expire(keyPrefix+relationKey, expire);
//    }
//
//
//    public List<String> getByRelationId(String keyPrefix,String relationKey){
//        JedisCluster jedisCluster = RedisCluster.getInstance();
//        String jsonStr = jedisCluster.get(keyPrefix+relationKey);
//        if(StringUtils.isNotBlank(jsonStr)) {
//            List<String> idList = JSON.parseArray(jsonStr, String.class);
//            return idList;
//        }
//        return null;
//    }
//
//    public void deleteByRelationId(String keyPrefix,String relationKey,String id){
//
//        if(StringUtils.isNotBlank(relationKey) && StringUtils.isNotBlank(id)){
//            JedisCluster jedisCluster = RedisCluster.getInstance();
//            String jsonStr = jedisCluster.get(keyPrefix+relationKey);
//            if(StringUtils.isNotBlank(jsonStr)){
//                List<String> idList = JSON.parseArray(jsonStr,String.class);
//                if(idList.contains(id)){
//                    idList.remove(id);
//                }
//                if(idList.size()>0){
//                    jedisCluster.set(keyPrefix+relationKey, JSON.toJSONString(idList));
//                    jedisCluster.expire(keyPrefix+relationKey, expire);
//                }else{//删除key即可
//                    jedisCluster.del(keyPrefix+relationKey);
//                }
//            }
//        }
//    }
//
//
//}