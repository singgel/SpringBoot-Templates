package com.hks.hbase.service;

import com.hks.hbase.model.*;
import com.hks.hbase.pojo.CFResultCell;
import com.hks.hbase.pojo.CFResultTable;
import com.hks.hbase.pojo.CFResultTypeEnum;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IHBaseService {

    /**
     * 创建hbase表，不分区
     * @param table
     * @return
     */
    boolean createTable(HBaseTable table);

    /**
     * 创建hbase表,预分区
     * @param table
     * @param splitKeys
     * @return
     */
    boolean createTable(HBaseTable table, byte[][] splitKeys);

    /**
     * 创建预分区表
     * @param table
     * @param baseRecord 预分区算法随机产生的rowKey样本数目(建议1000000,越大运算方法执行时间越长，越小分区越不均匀)
     * @param prepareRegions 分区数量
     * @return
     */
    boolean createTable(HBaseTable table, int baseRecord, int prepareRegions);

    /**
     * 创建预分区表
     * @param tableName
     * @param columnFamily
     * @param baseRecord 预分区算法随机产生的rowKey样本数目(建议1000000,越大运算方法执行时间越长，越小分区越不均匀)
     * @param prepareRegions 分区数量
     * @return
     */
    boolean createTable(String tableName, List<String> columnFamily, int baseRecord, int prepareRegions);

    /**
     * 创建表
     * @param tableName
     * @param columnFamily
     * @param splitKeys
     * @return
     */
    boolean createTable(String tableName, List<String> columnFamily, byte[][] splitKeys);

    /**
     * 删除表
     * @param tableName
     * @return
     */
    boolean deleteTable(String tableName);

    /**
     * 清空表内容
     * @param tableName
     * @return
     */
    boolean truncateTable(String tableName);

    /**
     * 向表中插入一条记录
     * @param table
     * @param cell
     * @return
     */
    boolean insert(HBaseTable table, HBaseCell cell);

    /**
     * 向表中批量插入数据
     * @param table
     * @param cells
     * @return
     */
    boolean insert(HBaseTable table, List<? extends HBaseCell> cells);

    /**
     * 获取指定表名、列簇名、rowKey的Map结果集
     * @param table
     * @param columnName
     * @param rowKey
     * @return
     */
    Map<String,byte[]> get(HBaseTable table, String columnName, String rowKey);

    /**
     * 获取指定表名、rowKey、列簇、cellKey的结果集
     * @param table
     * @param rowKey
     * @param columnName
     * @param cellKeySet
     * @return
     */
    List<HBaseCellData> get(HBaseTable table, String rowKey, String columnName, Set<String> cellKeySet);

    /**
     * 判断表是否存在
     * @param table
     * @param rowKey
     * @return
     * @throws IOException
     */
    boolean isExistRowKey(HBaseTable table, String rowKey) throws IOException;

    /**
     * 获取rowKey实际存储的字符串
     * @param rowKey
     * @return
     */
    String getRowkeyInHbase(String rowKey);



    /**
     * 向表中某列簇插入数据
     * @param tableName
     * @param columnName
     * @param rowKey
     * @param cellMap
     * @return
     */
    boolean insert(String tableName, String columnName, String rowKey, Map<String, byte[]> cellMap);

    /**
     * 批量向表中某列簇插入数据
     * @param tableName
     * @param columnName
     * @param cellMapList
     * @return
     */
    boolean insert(String tableName, String columnName, Map<String, Map<String, byte[]>> cellMapList);

//    /**
//     * 多线程同步提交
//     * @param tableName  表名称
//     * @param cells  待提交参数
//     * @param waiting  是否等待线程执行完成  true 可以及时看到结果, false 让线程继续执行，并跳出此方法返回调用方主程序
//     */
//    void batchInsert(final String tableName, final List<? extends HBaseCell> cells, boolean waiting);
//
//
//    /**
//     * 多线程异步提交
//     * @param tableName  表名称
//     * @param cells  待提交参数
//     * @param waiting  是否等待线程执行完成  true 可以及时看到结果, false 让线程继续执行，并跳出此方法返回调用方主程序
//     */
//    void batchAsyncInsert(final String tableName, final List<? extends HBaseCell> cells, boolean waiting);

    /**
     * 批量插入推荐算法结果
     * @param resultCells
     * @param cfResultType
     * @return
     */
    boolean insertCFResult(List<CFResultCell> resultCells, CFResultTypeEnum cfResultType);

    /**
     * 批量插入推荐算法结果
     * @param resultMap
     * @param cfResultType
     * @return
     */
    boolean insertCFResult(Map<String, String> resultMap, CFResultTypeEnum cfResultType);

    /**
     * 获取推荐结果
     * @param rowKey
     * @param cfResultType
     * @return 返回的map 对象size为2, 包含:
     * "result":"用fastjson序列化的ArrayList<RecommendModel>对象"
     * "ts":"数据的时间戳"
     */
    Map<String,String> getCFResult(String rowKey, CFResultTypeEnum cfResultType);

    /**
     * 批量插入推荐算法结果
     * @param resultCells
     * @param table
     * @return
     */
    boolean insertCFResult(List<CFResultCell> resultCells, CFResultTable table);

    /**
     * 批量插入推荐算法结果
     * @param resultMap
     * @param table
     * @return
     */
    boolean insertCFResult(Map<String, String> resultMap , CFResultTable table);

    /**
     * 获取推荐结果
     * @param rowKey
     * @param table
     * @return 返回的map 对象size为2, 包含:
     * "result":"用fastjson序列化的ArrayList<RecommendModel>对象"
     * "ts":"数据的时间戳"
     */
    Map<String,String> getCFResult(String rowKey, CFResultTable table);

    /**
     * 获取userId或设备号唯一ID
     * @param rowKey userId 或 设备号
     * @return
     */
    Integer getUserDeviceMergeId(String rowKey);

    /**
     * 插入userId或设备号唯一ID
     * @param rowKey userId 或 设备号
     * @param mergeId 唯一ID
     * @return
     */
    boolean insertUserDeviceMergeId(String rowKey, Integer mergeId);


    /**
     * 获取资讯唯一ID
     * @param objId
     * @param objType
     * @return
     */
    Integer getInformationMergeId(Integer objId, Integer objType);

    /**
     * 插入资讯唯一ID
     * @param objId
     * @param objType
     * @param mergeId
     * @return
     */
    boolean insertInformationMergeId(Integer objId, Integer objType, Integer mergeId);


    /**
     * 获取资讯原始ID和Type
     * @param rowKey 即资讯cfID
     * @return KeyValue<objId,objType>
     */
    KeyValue<String,String> getInfoIdAndType(String rowKey);

    /**
     * 插入资讯唯一ID、原始id和type
     * @param rowKey 即资讯cfID
     * @param objId 资讯原始id
     * @param objType 资讯类型
     * @return
     */
    boolean insertInfoIdAndType(String rowKey, String objId, String objType);


    /**
     * 批量删除数据行
     * @param table
     * @param rowKeys
     * @return
     */
    boolean deleteRows(String table, List<String> rowKeys);

}