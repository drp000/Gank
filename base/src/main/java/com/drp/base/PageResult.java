package com.drp.base;


/**
 * @author durui
 * @date 2021/4/1
 * @description
 */
public class PageResult {
    private boolean isEmpty;
    private boolean isRefresh;
    private boolean hasNextPage;

    public PageResult(boolean isEmpty, boolean isRefresh, boolean hasNextPage) {
        this.isEmpty = isEmpty;
        this.isRefresh = isRefresh;
        this.hasNextPage = hasNextPage;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }
}