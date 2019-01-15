package net.abc.explore.rest.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: zhangwei
 * @Description:
 * @Date: Created in 上午12:45 2018/5/15
 */
@ToString
public class Result implements java.io.Serializable {

    private static final long serialVersionUID = -7550423847540881334L;

    @ApiModelProperty(value = "总页数")
    private Integer pageCount;

    @ApiModelProperty(value = "每页行数")
    private Integer pageSize;

    @ApiModelProperty(value = "当前页")
    private Integer pageIndex;

    @ApiModelProperty(value = "总数据行数")
    private Integer dataCount;

    /**
     * 成功
     */
    public static final int SUCCESS = 1;
    /**
     * 警告
     */
    public static final int WARN = 2;
    /**
     * 失败
     */
    public static final int ERROR = 0;

    /**
     * 成功消息
     */
    public static final String SUCCESS_MSG = "操作成功！";
    /**
     * 失败消息
     */
    public static final String ERROR_MSG = "操作失败:发生未知异常！";

    @ApiModelProperty(value = "结果状态 1:成功、2:警告、3:失败")
    private int code;

    @ApiModelProperty(value = "提示信息")
    private String msg;

    /**
     * 数据
     */
    @ApiModelProperty(value = "数据")
    private Map<String, Object> data;

    public Result() {
        super();
        this.code=SUCCESS;
        this.setMsg(SUCCESS_MSG);
    }

    public Result(Integer pageIndex, Integer pageSize){
        this();
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
    }

    public Result(int code, String msg, Map<String, Object> data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }


    public void success(){
        success(SUCCESS_MSG);
    }

    /**
     * 设置成功状态
     * @param msg
     */
    public void success(String msg){
        this.code = SUCCESS;
        this.msg = msg;
    }

    public void error(){
        error(ERROR_MSG);
    }

    /**
     * 设施失败状态
     * @param msg
     */
    public void error(String msg){
        this.code = ERROR;
        this.msg = msg;
    }

    /**
     * 添加结果集
     * @param key
     * @param value
     */
    public void addData(String key, Object value, boolean success){
        if(data == null)
            data = new HashMap<String, Object>();
        data.put(key, value);
        if(success)
            success();
    }


    public Integer pageIndex(){
        if(pageIndex == null)
            return null;
        if(pageIndex <= 0)
            return 0;
        return pageIndex - 1;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getPageSize() {
        if(pageSize != null && pageSize == 0)
            pageSize = 10;
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getDataCount() {
        return dataCount;
    }

    public void setDataCount(Integer dataCount) {
        this.dataCount = dataCount;
        // 总页数计算
        this.pageCount = this.dataCount / pageSize;
        if(this.dataCount % pageSize != 0) {
            this.pageCount++;
        }
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public boolean isPage() {
        return (pageIndex == null || pageSize == null) ? false : true;
    }
}
