package com.wll.common.bean;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

@Data
public class MyPage<T> {
    /** 总记录数 */
    private long count = 0;
    /** 总页数 */
    private int totalPages = 0;
    /** 页码 */
    private int currentPage = 1;
    /** 每页记录数*/
    private int pageSize = 20 ;
    /** 数据列表 */
    private List<T> data = Lists.newArrayList();

    public MyPage(){}

    public MyPage(List<T> rows){
        PageInfo<T> tPageInfo = new PageInfo<>(rows);
        this.count = tPageInfo.getTotal();
        this.totalPages = tPageInfo.getPages();
        this.currentPage = tPageInfo.getPageNum();
        this.pageSize = tPageInfo.getPageSize();
        this.data = rows;
    }

    public static <B> MyPage<B> cp(List<B> rows,MyPage page){
        MyPage<B> myPage = new MyPage<>();
        myPage.setData(rows);
        myPage.setCount(page.getCount());
        myPage.setTotalPages(page.getTotalPages());
        myPage.setCurrentPage(page.getCurrentPage());
        myPage.setPageSize(page.getPageSize());
        return myPage;
    }
}
