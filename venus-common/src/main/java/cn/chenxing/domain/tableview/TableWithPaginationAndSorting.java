package cn.chenxing.domain.tableview;

import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import lombok.Getter;

import java.util.Comparator;
import java.util.HashMap;

/**
 * @Description 表格分页和排序
 * @Author maogen.ymg
 * @Date 2020/4/11 23:16
 */
@Getter
public class TableWithPaginationAndSorting<T> {
    /**
     * 分页
     */
    private Page<T> page;
    /**
     * 表格
     */
    private TableView<T> tableView;
    /**
     * 表格分页
     */
    private Pagination tableViewWithPaginationPane;


    public TableWithPaginationAndSorting(Page<T> page, TableView<T> tableView) {
        this.page = page;
        this.tableView = tableView;
        tableViewWithPaginationPane = new Pagination();
        tableViewWithPaginationPane.pageCountProperty().bindBidirectional(page.totalPageProperty());
        updatePagination();
    }

    /**
     * 更新
     * Pagination的页面工厂，回调方法会在一个页面被选中时触发
     * 会加载并返回被选中页面的内容。若页面索引不存在，返回null值
     * 接收传过来的当前页码（pageIndex，从0开始），调取方法获取当页的数据，转换格式并添加到TableView中
     * 最后返回TableView。
     *
     */
    private void updatePagination() {
        tableViewWithPaginationPane.setPageFactory(pageIndex -> {
            tableView.setItems(FXCollections.observableList(page.getCurrentPageDataList(pageIndex)));
            return tableView;
        });
    }

    private HashMap<Label, String>  orderMap = new HashMap<>();

    public void addGlobalOrdering(TableColumn<T, ?> column,
                                  Comparator<? super T> ascComparator,
                                  Comparator<? super T> descComparator) {

        // ------------------标签设置 --------------------
        // 将列名给标签
        Label label = new Label(column.getText());
        label.setMinWidth(column.getMinWidth());
        label.setMaxWidth(column.getMaxWidth());
        label.setPrefWidth(column.getPrefWidth());

        // 默认不排序
        orderMap.put(label, "NO");

        // ------------------列设置 --------------------
        // 列名为空
        column.setText(null);

        // 关闭排序
        column.setSortable(false);

        // 用标签代替原来的表头
        column.setGraphic(label);

        ImageView ascImg = new ImageView("/images/main/table/asc.png");
        ImageView descImg = new ImageView("/images/main/table/desc.png");
        ImageView noImg = new ImageView("/images/main/table/no.png");

        // 设置事件
        label.setOnMouseClicked(mouseEvent -> {

            // 每次只对一列排序，使其他列不排序
            orderMap.keySet().forEach(lab -> lab.setGraphic(noImg));
            switch (orderMap.get(label)) {
                case "NO":
                    orderMap.replace(label, "ASC");
                    label.setGraphic(ascImg);
                    order(ascComparator);
                    updatePagination();
                    break;
                case "ASC":
                    orderMap.put(label, "DESC");
                    label.setGraphic(descImg);
                    order(descComparator);
                    updatePagination();
                    break;
                case "DESC":
                    orderMap.put(label, "ASC");
                    label.setGraphic(ascImg);
                    order(ascComparator);
                    updatePagination();
                    break;
                default:break;
            }
        });
    }

    /**
     * 按给定的比较器对列表排序
     * @param comparator 比较器
     */
    private void order(Comparator<? super T> comparator) {
        page.getRowDataList().sort(comparator);
    }
}
