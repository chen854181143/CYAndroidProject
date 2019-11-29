package com.chenyang.androidproject.bean;

public class Bean {

    /**
     * res_code : 200
     * err_msg :
     * demo : {"id":"1001","name":"sss","appid":"1022","showtype":"text"}
     */

    private int res_code;
    private String err_msg;
    private DemoBean demo;

    public int getRes_code() {
        return res_code;
    }

    public void setRes_code(int res_code) {
        this.res_code = res_code;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public DemoBean getDemo() {
        return demo;
    }

    public void setDemo(DemoBean demo) {
        this.demo = demo;
    }

    public static class DemoBean {
        /**
         * id : 1001
         * name : sss
         * appid : 1022
         * showtype : text
         */

        private String id;
        private String name;
        private String appid;
        private String showtype;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getShowtype() {
            return showtype;
        }

        public void setShowtype(String showtype) {
            this.showtype = showtype;
        }

        @Override
        public String toString() {
            return "DemoBean{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", appid='" + appid + '\'' +
                    ", showtype='" + showtype + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Bean{" +
                "res_code=" + res_code +
                ", err_msg='" + err_msg + '\'' +
                ", demo=" + demo +
                '}';
    }
}
