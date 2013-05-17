package com.dianping.spotlight.model;

import java.util.Date;

public class Record {

    private String title;// 标题
    private String content;//内容
    private String author;//作者
    private String source;//来源，有架构wiki，搜索wiki，project，ftp
    private String url;//访问路径
    private Date createdate;//创建日期
    private String keyword;//标题＋内容＋作者
    private String extrainfo;//额外信息

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getExtrainfo() {
        return extrainfo;
    }

    public void setExtrainfo(String extrainfo) {
        this.extrainfo = extrainfo;
    }

    @Override
    public String toString() {
        return "Record [title=" + title + ", content=" + content + ", author=" + author + ", source=" + source + ", url=" + url
                + ", createdate=" + createdate + ", keyword=" + keyword + ", extrainfo=" + extrainfo + "]";
    }

}
