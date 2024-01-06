package entity;

public class Page {
    private int pageNow;
    private int pageCount;
    private int totalPage;
    private int totalRows;

    public Page(int pageNow, int pageCount, int totalRows, int totalPage) {
        this.pageNow = pageNow;
        this.pageCount = pageCount;
        this.totalRows = totalRows;
        this.totalPage = totalPage;
    }

    public int getPageNow() {
        return pageNow;
    }

    public void setPageNow(int pageNow) {
        this.pageNow = pageNow;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }
}
