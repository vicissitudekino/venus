package cn.chenxing.constant;

/**
 * @Description 常量
 * @Author maogen.ymg
 * @Date 2020/2/24 21:49
 */
public class VenusConstant {
    private VenusConstant() {}

    public static final String MAIN_FXML_PATH = "\\fxml\\Main.fxml";

    public static final String ICON_URL = "/images/logo/logo.png";

    /***************************** Title ********************************/

    public static final String MAIN_TITLE = "算法";

    public static final String FILE_STRUCT_TITLE = "文件结构分析";

    /***************************** View ********************************/
    public static final String FILE_VIEW_CLASS = "cn.chenxing.module.file.view.FileView";
    public static final String FILE_LOCAL_VIEW_CLASS = "cn.chenxing.module.file.view.LocalDataView";
    public static final String FILE_ONLINE_VIEW_CLASS = "cn.chenxing.module.file.view.OnlineDataView";

    public static final String QUERY_VIEW_CLASS = "cn.chenxing.module.query.view.QueryView";
    public static final String QUERY_TIME_VIEW_CLASS = "cn.chenxing.module.query.view.QueryTimeView";
    public static final String QUERY_DIST_VIEW_CLASS = "cn.chenxing.module.query.view.QueryDistView";
    public static final String QUERY_USER_VIEW_CLASS = "cn.chenxing.module.query.view.QueryUserView";
    public static final String QUERY_LOCATION_VIEW_CLASS = "cn.chenxing.module.query.view.QueryLocationView";

    public static final String ANALYSIS_VIEW_CLASS = "cn.chenxing.module.analysis.view.AnalysisView";
    public static final String ANALYSIS_DBSCAN_VIEW_CLASS = "cn.chenxing.module.analysis.view.DbScanView";
    public static final String ANALYSIS_OUTLIER_DETECT_VIEW_CLASS = "cn.chenxing.module.analysis.view.OutlierDetectView";

    public static final String VISUAL_VIEW_CLASS = "cn.chenxing.module.visualization.view.VisualizeView";
    public static final String VISUAL_TRACK_VIEW_CLASS = "cn.chenxing.module.visualization.view.VisualTrackView";
    public static final String VISUAL_OUTLIER_VIEW_CLASS = "cn.chenxing.module.visualization.view.VisualOutlierView";
    public static final String VISUAL_DBSCAN_VIEW_CLASS = "cn.chenxing.module.visualization.view.VisualDbScanView";

    public static final String ABOUT_VIEW_CLASS = "cn.chenxing.module.login.view.ForgetView";


    public static final double EARTH_RADIUS_IN_METERS = 6378137.0;
}
