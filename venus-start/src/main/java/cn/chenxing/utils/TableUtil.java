package cn.chenxing.utils;

/**
 * @Description 获取数据为系统线程中的，若有其他需求再修改
 * @Author maogen.ymg
 * @Date 2020/4/13 23:49
 */
public class TableUtil {
    private TableUtil() {}

    /**
     * 导入表格信息（分页展示）
     * @param borderPane borderPane
     */
//    public static void importTableView(BorderPane borderPane, List<TrackTable> trackTableList, boolean autoImportData) {
//        borderPane.getChildren().clear();
//
//        // 创建表格
//        TableView<TrackTable> trackTable = createTable();
//
//        // 获取数据
//        if(autoImportData) {
//            // 默认使用公共的数据
//            trackTableList = getTableData();
//        }
//        trackTable.setItems(FXCollections.observableList(trackTableList));
//
//        // 创建分页对象，每页12个
//        Page<TrackTable> page = new Page<>(trackTableList, 12);
//
//        // 添加分页到表格
//        TableWithPaginationAndSorting<TrackTable> table = new TableWithPaginationAndSorting<>(page, trackTable);
//
//        // 按列用户ID进行全局排序
//        Comparator<TrackTable> asc = Comparator.comparingLong(TrackTable::getId);
//        Comparator<TrackTable> desc = (p1, p2) -> Long.compare(p2.getId(), p1.getId());
//
//        // 列用户ID排序
//        table.addGlobalOrdering(trackTable.getColumns().get(0),
//                asc,
//                desc);
//
//        borderPane.setCenter(table.getTableViewWithPaginationPane());
//    }

//    /**
//     * 导入离散点检测信息
//     * @param borderPane borderPane
//     */
//    public static void importOutlierDetect(BorderPane borderPane, List<DataNode> dataNodes, boolean needSave) {
//        borderPane.getChildren().clear();
//
//        // 离群点检测结果保存
//        if(needSave) {
//            ThreadData.get().getTrackData().setOutlierDetectList(dataNodes);
//        }
//
//        // 创建表格
//        TableView<DataNode> dataNodeTable = creatTable2OutlierDetect();
//
//        dataNodeTable.setItems(FXCollections.observableList(dataNodes));
//        // 创建分页对象，每页15个
//        Page<DataNode> page = new Page<>(dataNodes, 15);
//        // 添加分页到表格
//        TableWithPaginationAndSorting<DataNode> table = new TableWithPaginationAndSorting<>(page, dataNodeTable);
//
//        borderPane.setCenter(table.getTableViewWithPaginationPane());
//    }

    /**
     * 创建TableView
     * @return TableView
     */
//    @SuppressWarnings("unchecked")
//    private static TableView<TrackTable> createTable() {
//
//        TableView<TrackTable> table = new TableView<>();
//        // 用户ID
//        TableColumn<TrackTable, Long> idCol = new TableColumn<>("ID");
//        idCol.setCellValueFactory(new PropertyValueFactory("id"));
//        // 经度
//        TableColumn<TrackTable, Double> longitudeCol = new TableColumn<>("Longitude");
//        longitudeCol.setCellValueFactory(new PropertyValueFactory("longitude"));
//        // 纬度
//        TableColumn<TrackTable, Double> latitudeCol = new TableColumn<>("Latitude");
//        latitudeCol.setCellValueFactory(new PropertyValueFactory("latitude"));
//        // 时间戳
//        TableColumn<TrackTable, String> timeStampCol = new TableColumn<>("Time");
//        timeStampCol.setCellValueFactory(new PropertyValueFactory("time"));
//        if(ThreadData.get().isHasSpeed()) {
//            // 速度
//            TableColumn<TrackTable, Double> speedCol = new TableColumn<>("Speed");
//            speedCol.setCellValueFactory(new PropertyValueFactory("speed"));
//            table.getColumns().addAll(idCol, longitudeCol, latitudeCol, timeStampCol, speedCol);
//        }else {
//            table.getColumns().addAll(idCol, longitudeCol, latitudeCol, timeStampCol);
//        }
//        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//
//        return table;
//    }

    /**
     * 创建TableView---用于离散点检测
     * @return TableView
     */
//    @SuppressWarnings("unchecked")
//    private static TableView<Point> creatTable2DbScan() {
//        TableView<Point> table = new TableView<>();
//        // 用户ID
//        TableColumn<Point, Long> idCol = new TableColumn<>("ID");
//        idCol.setCellValueFactory(new PropertyValueFactory("userId"));
//        // 经度
//        TableColumn<Point, Double> longitudeCol = new TableColumn<>("Longitude");
//        longitudeCol.setCellValueFactory(new PropertyValueFactory("x"));
//        // 纬度
//        TableColumn<Point, Double> latitudeCol = new TableColumn<>("Latitude");
//        latitudeCol.setCellValueFactory(new PropertyValueFactory("y"));
//
//        // cluster
//        TableColumn<Point, Integer> clusterCol = new TableColumn<>("Cluster");
//        clusterCol.setCellValueFactory(new PropertyValueFactory("cluster"));
//
//        table.getColumns().addAll(idCol, longitudeCol, latitudeCol, clusterCol);
//
//        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//        return table;
//    }
}
