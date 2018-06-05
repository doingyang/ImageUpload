package com.project.upload.bean;

import java.util.List;

/**
 * @author ydy
 */

public class GiftBean {

    private int pageno;
    private List<AdBean> ad;
    private List<ListBean> list;

    public int getPageno() {
        return pageno;
    }

    public void setPageno(int pageno) {
        this.pageno = pageno;
    }

    public List<AdBean> getAd() {
        return ad;
    }

    public void setAd(List<AdBean> ad) {
        this.ad = ad;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class AdBean {

        private int id;
        private String title;
        private int flag;
        private String iconurl;
        private String addtime;
        private String giftid;
        private String appName;
        private String appLogo;
        private int appId;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public String getIconurl() {
            return iconurl;
        }

        public void setIconurl(String iconurl) {
            this.iconurl = iconurl;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getGiftid() {
            return giftid;
        }

        public void setGiftid(String giftid) {
            this.giftid = giftid;
        }

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getAppLogo() {
            return appLogo;
        }

        public void setAppLogo(String appLogo) {
            this.appLogo = appLogo;
        }

        public int getAppId() {
            return appId;
        }

        public void setAppId(int appId) {
            this.appId = appId;
        }

        @Override
        public String toString() {
            return "AdBean{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", flag=" + flag +
                    ", iconurl='" + iconurl + '\'' +
                    ", addtime='" + addtime + '\'' +
                    ", giftid='" + giftid + '\'' +
                    ", appName='" + appName + '\'' +
                    ", appLogo='" + appLogo + '\'' +
                    ", appId=" + appId +
                    '}';
        }
    }

    public static class ListBean {

        private String id;
        private String iconurl;
        private String giftname;
        private int number;
        private int exchanges;
        private int type;
        private String gname;
        private int integral;
        private int isintegral;
        private String addtime;
        private String ptype;
        private String operators;
        private int flag;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIconurl() {
            return iconurl;
        }

        public void setIconurl(String iconurl) {
            this.iconurl = iconurl;
        }

        public String getGiftname() {
            return giftname;
        }

        public void setGiftname(String giftname) {
            this.giftname = giftname;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getExchanges() {
            return exchanges;
        }

        public void setExchanges(int exchanges) {
            this.exchanges = exchanges;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getGname() {
            return gname;
        }

        public void setGname(String gname) {
            this.gname = gname;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public int getIsintegral() {
            return isintegral;
        }

        public void setIsintegral(int isintegral) {
            this.isintegral = isintegral;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getPtype() {
            return ptype;
        }

        public void setPtype(String ptype) {
            this.ptype = ptype;
        }

        public String getOperators() {
            return operators;
        }

        public void setOperators(String operators) {
            this.operators = operators;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        @Override
        public String toString() {
            return "ListBean{" +
                    "id='" + id + '\'' +
                    ", iconurl='" + iconurl + '\'' +
                    ", giftname='" + giftname + '\'' +
                    ", number=" + number +
                    ", exchanges=" + exchanges +
                    ", type=" + type +
                    ", gname='" + gname + '\'' +
                    ", integral=" + integral +
                    ", isintegral=" + isintegral +
                    ", addtime='" + addtime + '\'' +
                    ", ptype='" + ptype + '\'' +
                    ", operators='" + operators + '\'' +
                    ", flag=" + flag +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GiftBean{" +
                "pageno=" + pageno +
                ", ad=" + ad +
                ", list=" + list +
                '}';
    }
}
