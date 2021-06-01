package cn.chenxing.domain.tableview;

import javafx.beans.property.SimpleIntegerProperty;
import lombok.Data;

import java.util.List;

/**
 * @Description 表格的分页
 * @Author maogen.ymg
 * @Date 2020/4/11 23:04
 */
@Data
public class Page<T> {
    /**
     * 数据的总记录数
     */
    private SimpleIntegerProperty totalRecord;
    /**
     * 每页的数据量
     */
    private SimpleIntegerProperty pageSize;
    /**
     * 总页数
     */
    private SimpleIntegerProperty totalPage;
    /**
     * 总数据
     */
    private List<T> rowDataList;

    public SimpleIntegerProperty totalRecordProperty() {
        return totalRecord;
    }

    public int getPageSize() {
        return pageSize.get();
    }

    public SimpleIntegerProperty pageSizeProperty() {
        return pageSize;
    }

    public int getTotalPage() {
        return totalPage.get();
    }

    public SimpleIntegerProperty totalPageProperty() {
        return totalPage;
    }

    /**
     * @param rowDataList 需要分页的数据
     * @param pageSize    每页显示的行数
     */
    public Page(List<T> rowDataList, int pageSize) {
        this.totalRecord = new SimpleIntegerProperty();
        this.totalPage = new SimpleIntegerProperty();
        this.rowDataList = rowDataList;
        this.pageSize = new SimpleIntegerProperty(pageSize);
        initialize();

    }

    /**
     * 计算相关数据的总记录数（totalRecord），总页数（totalPage）
     */
    private void initialize() {
        totalRecord.set(rowDataList.size());

        // 计算总页数
        totalPage.set(
                totalRecord.get() % pageSize.get() == 0 ?
                        totalRecord.get() / pageSize.get() :
                        totalRecord.get() / pageSize.get() + 1);

        // add listener: pageSize的值改变，那么总页数（totalPage）也需要随之改变
        pageSize.addListener((observable, oldVal, newVal) ->
                totalPage.set(
                        totalRecord.get() % pageSize.get() == 0 ?
                                totalRecord.get() / pageSize.get() :
                                totalRecord.get() / pageSize.get() + 1)
        );
    }

    /**
     * 根据传入的页码，返回当前页的数据
     *
     * @param currentPage 当前页码
     * @return 当前页的数据
     */
    public List<T> getCurrentPageDataList(int currentPage) {
        int fromIndex = pageSize.get() * currentPage;
        int tmp = pageSize.get() * currentPage + pageSize.get() - 1;
        int endIndex = tmp >= totalRecord.get() ? totalRecord.get() - 1 : tmp;

        // subList(fromIndex, toIndex) -> [fromIndex, toIndex)
        return rowDataList.subList(fromIndex, endIndex + 1);
    }
}
