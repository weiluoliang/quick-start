package com.wll.common.bean;

import lombok.Data;

@Data
public class PageReq {
    /** 当前页 */
    private Integer currentPage = 1 ;
    /** 每页记录条数 */
    private Integer pageSize = 20 ;

}
