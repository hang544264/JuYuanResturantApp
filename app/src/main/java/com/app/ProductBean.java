package com.app;

import java.util.List;

public class ProductBean {

    /**
     * list : [{"id":1,"name":"辣椒炒肉","label":"辣椒炒肉","description":"辣椒炒肉，就是肉有点贵！","icon":"http://120.76.210.221:80/SkillExam/images/1.png","price":36},{"id":2,"name":"回锅肉","label":"回锅肉","description":"回锅肉，好吃看得见！","icon":"http://120.76.210.221:80/SkillExam/images/2.png","price":28},{"id":3,"name":"醋溜土豆丝","label":"醋溜土豆丝","description":"醋溜土豆丝，可口又美味！","icon":"http://120.76.210.221:80/SkillExam/images/3.png","price":18},{"id":4,"name":"白菜","label":"白菜","description":"白菜，萝卜白菜，各有所爱！","icon":"http://120.76.210.221:80/SkillExam/images/4.png","price":14},{"id":5,"name":"拍黄瓜","label":"拍黄瓜","description":"拍黄瓜，凉菜一个！","icon":"http://120.76.210.221:80/SkillExam/images/5.png","price":10},{"id":6,"name":"丝瓜汤","label":"丝瓜汤","description":"丝瓜汤，清汤！","icon":"http://120.76.210.221:80/SkillExam/images/6.png","price":20}]
     * result : true
     * msg : 成功
     */

    private boolean result;
    private String msg;
    private List<ListBean> list;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 1
         * name : 辣椒炒肉
         * label : 辣椒炒肉
         * description : 辣椒炒肉，就是肉有点贵！
         * icon : http://120.76.210.221:80/SkillExam/images/1.png
         * price : 36
         */

        private int id;
        private String name;
        private String label;
        private String description;
        private String icon;
        private int price;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}
