package com.hks.hbase.service;

import com.hks.hbase.config.HBaseZookeeperConfig;
import com.hks.hbase.model.HBaseCell;
import com.hks.hbase.model.HBaseCellData;
import com.hks.hbase.model.HBaseTable;
import com.hks.hbase.model.KeyValue;
import com.hks.hbase.pojo.*;
import com.hks.hbase.regionhelper.HashChoreWoker;
import com.hks.hbase.util.HBaseUtil;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.MD5Hash;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

import static com.hks.hbase.util.CheckUtil.checkIsEmpty;

@Service
public class HBaseServiceImpl implements IHBaseService {

    private static Logger logger = Logger.getLogger(HBaseServiceImpl.class);
    private Map<String,CFResultTable> cfResultTableMap = new HashMap<>(CFResultTypeEnum.values().length);
    // private ThreadPoolUtil threadPool= ThreadPoolUtil.init();       // 初始化线程池
    @Autowired
    private HBaseZookeeperConfig config;


    /**
     * 非注入方式需要调用此方法进行实例化
     * @param config
     */
    public HBaseServiceImpl(HBaseZookeeperConfig config){
        this.config = config;
        init();
    }

    @PostConstruct
    private void init(){
        HBaseUtil.init(config);
        for(CFResultTypeEnum typeEnum : CFResultTypeEnum.values()){
            cfResultTableMap.put(typeEnum.toString(),new CFResultTable(typeEnum));
        }
    }

    @Override
    public boolean createTable(HBaseTable table){
        return this.createTable(table,null);
    }
    @Override
    public boolean createTable(HBaseTable table, byte[][] splitKeys){
        try {
            HBaseUtil.createTable(table.getTableName(),table.getColumnFamily(),splitKeys);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            return false;
        }
    }

    /**
     * 创建预分区表
     * @param tableName
     * @param columnFamily
     * @param baseRecord 预分区算法随机产生的rowKey样本数目(建议1000000,越大运算方法执行时间越长，越小分区越不均匀)
     * @param prepareRegions 分区数量
     * @return
     */
    @Override
    public boolean createTable(String tableName, List<String> columnFamily, int baseRecord, int prepareRegions){
        byte[][] splitKeys = new HashChoreWoker(baseRecord, prepareRegions).calcSplitKeys();
        return createTable(tableName,columnFamily,splitKeys);
    }

    /**
     * 创建预分区表
     * @param table
     * @param baseRecord 预分区算法随机产生的rowKey样本数目(建议1000000,越大运算方法执行时间越长，越小分区越不均匀)
     * @param prepareRegions 分区数量
     * @return
     */
    @Override
    public boolean createTable(HBaseTable table, int baseRecord, int prepareRegions){
        return createTable(table.getTableName(),table.getColumnFamily(),baseRecord,prepareRegions);
    }

    @Override
    public boolean createTable(String tableName, List<String> columnFamily, byte[][] splitKeys){
        try {
            HBaseUtil.createTable(tableName,columnFamily,splitKeys);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteTable(String tableName) {
        try {
            HBaseUtil.deleteTable(tableName);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean truncateTable(String tableName)  {
        try {
            HBaseUtil.truncateTable(tableName,true);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean insert(HBaseTable table, HBaseCell cell) {
        return insert(table, Collections.singletonList(cell));
    }

    @Override
    public boolean insert(HBaseTable table, List<? extends HBaseCell> cells){
        try {
            HBaseUtil.put(table.getTableName(),convertToPut(cells));
            return true;
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean insert(String tableName, String columnName, String rowKey, Map<String,byte[]> cellMap){
        return insert(tableName,columnName, Collections.singletonMap(rowKey,cellMap));
    }

    @Override
    public boolean insert(String tableName, String columnName, Map<String,Map<String, byte[]>> cellMapList){
        try {
            List<Put> putList = new ArrayList<Put>();

            byte[] columnNameBytes = Bytes.toBytes(columnName);
            cellMapList.forEach((rowKey,cellMap)->{
                byte[] rowkey = getHbaseRowkey(rowKey);
                Put put = new Put(rowkey, System.currentTimeMillis());
                cellMap.forEach((k, v)->{
                    put.addColumn(columnNameBytes,Bytes.toBytes(k),v);
                });
                putList.add(put);
            });
            HBaseUtil.put(tableName,putList);
            return true;
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取指定表名、列簇名、rowKey的Map结果集
     * @param table
     * @param columnName
     * @param rowKey
     * @return
     */
    @Override
    public Map<String,byte[]> get(HBaseTable table, String columnName, String rowKey) {

        byte[] row = getHbaseRowkey(rowKey);
        Result result = HBaseUtil.getRow(table.getTableName(),columnName,row);
        return getCellKeyValue(result);
    }


    /**
     * 获取Result
     * @param tableName
     * @param columnFamily
     * @param rowKey
     * @return
     */
    private Result getResult(String tableName,String columnFamily,String rowKey){
        byte[] row = getHbaseRowkey(rowKey);
        return HBaseUtil.getRow(tableName,columnFamily,row);
    }


    /**
     * 获取指定表名、rowKey、列簇、多cell的结果集
     *
     * @param table
     * @param rowKey
     * @param columnName
     * @param cellKeySet
     * @return
     */
    @Override
    public List<HBaseCellData> get(HBaseTable table, String rowKey, String columnName, Set<String> cellKeySet) {
        byte[] row = getHbaseRowkey(rowKey);
        Result result = HBaseUtil.getRow(table.getTableName(),columnName,cellKeySet,row);
        if(result == null || result.isEmpty()){
            return new ArrayList<>(0);
        }
        List<HBaseCellData> dataList = new ArrayList<>(result.listCells().size());
        for(Cell c:result.listCells()) {
            dataList.add(new HBaseCellData(c));
        }
        return dataList;
    }

    @Override
    public boolean isExistRowKey(HBaseTable table, String rowKey) throws IOException {
        byte[] row = getHbaseRowkey(rowKey);
        Result result = HBaseUtil.getRow(table.getTableName(),row);
        if(result != null && !result.isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     *
     * @param rowKey
     * @return 获取hbase存储的rowKey字节数组
     */
    private byte[] getHbaseRowkey(String rowKey){
        checkIsEmpty(rowKey,"rowKey 不能为空");
        rowKey = rowKey.toLowerCase();
        byte [] rowkey = Bytes.add(MD5Hash.getMD5AsHex(Bytes.toBytes(rowKey)).substring(0, 8).getBytes(),
                Bytes.toBytes(rowKey));
        return rowkey;
    }

    /**
     *
     * @param rowKey
     * @return 获取业务rowKey在hbase中实际存储的rowKey
     */
    @Override
    public String getRowkeyInHbase(String rowKey){
        byte[] bytes = getHbaseRowkey(rowKey);
        return Bytes.toString(bytes);
    }

    /**
     * 将Result转成map对象，该方法只适合一个列簇的情况。多列簇的话，如果不同列簇下的key有重复，结果将有丢失
     * @param result
     * @return
     */
    private Map<String,byte[]> getCellKeyValue(Result result){
        Map<String,byte[]> resultMap = new HashMap<>();
        if(result == null || result.isEmpty()){
            return resultMap;
        }
        else {
            for(Cell c:result.listCells()) {
                String cellKey = Bytes.toString(CellUtil.cloneQualifier(c));
                byte[] cellValue = CellUtil.cloneValue(c);
                resultMap.put(cellKey,cellValue);
            }
            return resultMap;
        }
    }


    private Put convertToPut(HBaseCell cell){
        Put put = new Put(getHbaseRowkey(cell.getRowKey()), System.currentTimeMillis());
        byte[] columnNameBytes = cell.getColumnNameBytes();
        cell.getCellMap().forEach((k, v) -> {
            put.addColumn(columnNameBytes, Bytes.toBytes(k), v);
        });
        return put;
    }

    private List<Put> convertToPut(List<? extends HBaseCell> cells){
        List<Put> putList = new ArrayList<>(cells.size());
        cells.forEach(cell->putList.add(convertToPut(cell)));
        return putList;
    }

//    /**
//     * 多线程同步提交
//     * @param tableName  表名称
//     * @param cells  待提交参数
//     * @param waiting  是否等待线程执行完成  true 可以及时看到结果, false 让线程继续执行，并跳出此方法返回调用方主程序
//     */
//    public void batchInsert(final String tableName, final List<? extends HBaseCell> cells, boolean waiting) {
//        threadPool.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    HBaseUtil.put(tableName, convertToPut(cells));
//                } catch (Exception e) {
//                    logger.error("batchPut failed . ", e);
//                }
//            }
//        });
//
//        if(waiting){
//            try {
//                threadPool.awaitTermination();
//            } catch (InterruptedException e) {
//                logger.error("HBase put job thread pool await termination time out.", e);
//            }
//        }
//    }

//    /**
//     * 多线程异步提交
//     * @param tableName  表名称
//     * @param cells  待提交参数
//     * @param waiting  是否等待线程执行完成  true 可以及时看到结果, false 让线程继续执行，并跳出此方法返回调用方主程序
//     */
//    public void batchAsyncInsert(final String tableName, final List<? extends HBaseCell> cells, boolean waiting) {
//        Future f = threadPool.submit(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    HBaseUtil.putByHTable(tableName, convertToPut(cells));
//                } catch (Exception e) {
//                    logger.error("batchPut failed . ", e);
//                }
//            }
//        });
//
//        if(waiting){
//            try {
//                f.get();
//            } catch (InterruptedException | ExecutionException e) {
//                logger.error("多线程异步提交返回数据执行失败.", e);
//            }
//        }
//    }

    /**
     * 批量插入推荐算法结果
     * @param resultCells
     * @param cfResultType
     * @return
     */
    @Override
    public boolean insertCFResult(List<CFResultCell> resultCells, CFResultTypeEnum cfResultType){
        CFResultTable resultTable = cfResultTableMap.get(cfResultType.toString());
        return this.insert(resultTable,resultCells);
    }

    /**
     * 批量插入推荐算法结果
     * @param resultMap
     * @param cfResultType
     * @return
     */
    @Override
    public boolean insertCFResult(Map<String,String> resultMap, CFResultTypeEnum cfResultType){
        List<CFResultCell> resultCells = new ArrayList<>();
        resultMap.forEach((k,v)->{
            resultCells.add(new CFResultCell(k,v));
        });
        this.insertCFResult(resultCells,cfResultType);
        return true;
    }

    /**
     * 获取推荐结果
     *
     * @param rowKey
     * @return
     */
    @Override
    public Map<String,String> getCFResult(String rowKey, CFResultTypeEnum cfResultType) {
        CFResultTable cfResultTable = cfResultTableMap.get(cfResultType.toString());
        CFResultCell cfResultCell = new CFResultCell(rowKey,null);
        Result result = getResult(cfResultTable.getTableName(),cfResultTable.getDefaultColumnName(),rowKey);
        Map<String,String> resultMap = new HashMap<>();
        if(result != null && !result.isEmpty()){
            for(Cell cell: result.rawCells()){
                String cellKey = Bytes.toString(CellUtil.cloneQualifier(cell));
                if(cellKey.equals(cfResultCell.getDefaultCellName())){
                    String cellValue = Bytes.toString(CellUtil.cloneValue(cell));
                    resultMap.put("result",cellValue);
                    resultMap.put("ts",String.valueOf(cell.getTimestamp()));
                }
            }
            return resultMap;
        }
        else{
            return resultMap;
        }
    }

    /**
     * 批量插入推荐算法结果
     *
     * @param resultCells
     * @param table
     * @return
     */
    @Override
    public boolean insertCFResult(List<CFResultCell> resultCells, CFResultTable table) {
        return this.insert(table,resultCells);
    }

    /**
     * 批量插入推荐算法结果
     *
     * @param resultMap
     * @param table
     * @return
     */
    @Override
    public boolean insertCFResult(Map<String, String> resultMap, CFResultTable table) {
        List<CFResultCell> resultCells = new ArrayList<>();
        resultMap.forEach((k,v)->{
            resultCells.add(new CFResultCell(k,v));
        });
        this.insertCFResult(resultCells,table);
        return true;
    }

    /**
     * 获取推荐结果
     *
     * @param rowKey
     * @param table
     * @return 返回的map 对象size为2, 包含:
     * "result":"用fastjson序列化的ArrayList<RecommendModel>对象"
     * "ts":"数据的时间戳"
     */
    @Override
    public Map<String, String> getCFResult(String rowKey, CFResultTable table) {
        CFResultCell cfResultCell = new CFResultCell(rowKey,null);
        Result result = getResult(table.getTableName(),table.getDefaultColumnName(),rowKey);
        Map<String,String> resultMap = new HashMap<>();
        if(result != null && !result.isEmpty()){
            for(Cell cell: result.rawCells()){
                String cellKey = Bytes.toString(CellUtil.cloneQualifier(cell));
                if(cellKey.equals(cfResultCell.getDefaultCellName())){
                    String cellValue = Bytes.toString(CellUtil.cloneValue(cell));
                    resultMap.put("result",cellValue);
                    resultMap.put("ts",String.valueOf(cell.getTimestamp()));
                }
            }
            return resultMap;
        }
        else{
            return resultMap;
        }
    }

    @Override
    public Integer getUserDeviceMergeId(String rowKey) {
        UserDeviceTable userDeviceTable = new UserDeviceTable();
        UserDeviceCell userDeviceCell = new UserDeviceCell(rowKey,0);
        Map<String,byte[]> result = get(userDeviceTable,userDeviceTable.getDefaultColumnName(),rowKey);
        if(result != null && !result.isEmpty()){
            return Integer.parseInt(Bytes.toString(result.get(userDeviceCell.getIdCellName())));
        }
        else{
            return 0;
        }
    }

    @Override
    public boolean insertUserDeviceMergeId(String rowKey, Integer mergeId) {
        UserDeviceTable userDeviceTable = new UserDeviceTable();
        UserDeviceCell userDeviceCell = new UserDeviceCell(rowKey,mergeId);
        return insert(userDeviceTable,userDeviceCell);
    }

    @Override
    public Integer getInformationMergeId(Integer objId,Integer objType) {
        InformationTable informationTable = new InformationTable();
        InformationCell informationCell = new InformationCell(objId,objType,0);
        Map<String,byte[]> result = get(informationTable,informationTable.getDefaultColumnName(),informationCell.getRowKey());
        if(result != null && !result.isEmpty()){
            return Integer.parseInt(Bytes.toString(result.get(informationCell.getIdCellName())));
        }
        else{
            return 0;
        }
    }

    @Override
    public boolean insertInformationMergeId(Integer objId,Integer objType, Integer mergeId) {
        InformationTable informationTable = new InformationTable();
        InformationCell informationCell = new InformationCell(objId,objType,mergeId);
        return insert(informationTable,informationCell);
    }

    /**
     * 获取资讯原始ID和Type
     *
     * @param rowKey 即资讯cfID
     * @return  KeyValue<objId,objType>
     */
    @Override
    public KeyValue<String, String> getInfoIdAndType(String rowKey) {
        CFIdInfoTable cfIdInfoTable = new CFIdInfoTable();
        CFIdCell cfIdCell = new CFIdCell(rowKey,"","");
        Map<String,byte[]> result = get(cfIdInfoTable,cfIdInfoTable.getDefaultColumnName(),rowKey);
        if(result != null && !result.isEmpty()){
            return new KeyValue<>(
                    Bytes.toString(result.get(cfIdCell.getObjIdCell()))
                    , Bytes.toString(result.get(cfIdCell.getObjTypeCell()))
            );
        }
        else{
            return new KeyValue<>("","");
        }
    }

    /**
     * 插入资讯唯一ID、原始id和type
     *
     * @param rowKey  即资讯cfID
     * @param objId   资讯原始id
     * @param objType 资讯类型
     * @return
     */
    @Override
    public boolean insertInfoIdAndType(String rowKey, String objId, String objType) {
        CFIdInfoTable cfIdInfoTable = new CFIdInfoTable();
        CFIdCell cfIdCell = new CFIdCell(rowKey,objId,objType);
        return insert(cfIdInfoTable,cfIdCell);
    }

    /**
     * 批量删除数据行
     *
     * @param table
     * @param rowKeys
     * @return
     */
    @Override
    public boolean deleteRows(String table, List<String> rowKeys) {
        if(rowKeys == null || rowKeys.isEmpty()){
            return false;
        }
        List<Delete> deletes = new ArrayList<>();
        rowKeys.forEach(key->{
            Delete row = new Delete(getHbaseRowkey(key));
            deletes.add(row);
        });
        try {
            HBaseUtil.delete(table,deletes);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("HBase删除异常",e);
            return false;
        }
    }
}
