package com.example.yangweihan1214.Bean;

import java.util.List;

public class UserBean {


    private String msg;
    private int code;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * uniquekey : 1326bf3c4505783d4a964f81b6220e9d
         * title : 美国被特朗普坑惨了！又有3个铁杆盟友公开唱对台戏！
         * date : 2018-05-12 07:25
         * category : 头条
         * author_name : 大国军情
         * url : http://mini.eastday.com/mobile/180512072544020.html
         * thumbnail_pic_s : http://04.imgmini.eastday.com/mobile/20180512/20180512_fe1bf1b6ec00098f9455c84dc81e6763_cover_mwpm_03200403.jpg
         * thumbnail_pic_s02 : http://04.imgmini.eastday.com/mobile/20180512/20180512_3f636fca330f2311583896aed3185196_cover_mwpm_03200403.jpg
         * thumbnail_pic_s03 : http://04.imgmini.eastday.com/mobile/20180512/20180512_793fab03dadcd417bf2d8e5e1e48a0f5_cover_mwpm_03200403.jpg
         */

        private String title;
        private String date;
        private String category;
        private String author_name;
        private String url;
        private String thumbnail_pic_s;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getAuthor_name() {
            return author_name;
        }

        public void setAuthor_name(String author_name) {
            this.author_name = author_name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getThumbnail_pic_s() {
            return thumbnail_pic_s;
        }

        public void setThumbnail_pic_s(String thumbnail_pic_s) {
            this.thumbnail_pic_s = thumbnail_pic_s;
        }
    }
}
