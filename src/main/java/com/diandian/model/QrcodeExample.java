package com.diandian.model;

import java.util.ArrayList;
import java.util.List;

public class QrcodeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public QrcodeExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andRoomidIsNull() {
            addCriterion("roomid is null");
            return (Criteria) this;
        }

        public Criteria andRoomidIsNotNull() {
            addCriterion("roomid is not null");
            return (Criteria) this;
        }

        public Criteria andRoomidEqualTo(Integer value) {
            addCriterion("roomid =", value, "roomid");
            return (Criteria) this;
        }

        public Criteria andRoomidNotEqualTo(Integer value) {
            addCriterion("roomid <>", value, "roomid");
            return (Criteria) this;
        }

        public Criteria andRoomidGreaterThan(Integer value) {
            addCriterion("roomid >", value, "roomid");
            return (Criteria) this;
        }

        public Criteria andRoomidGreaterThanOrEqualTo(Integer value) {
            addCriterion("roomid >=", value, "roomid");
            return (Criteria) this;
        }

        public Criteria andRoomidLessThan(Integer value) {
            addCriterion("roomid <", value, "roomid");
            return (Criteria) this;
        }

        public Criteria andRoomidLessThanOrEqualTo(Integer value) {
            addCriterion("roomid <=", value, "roomid");
            return (Criteria) this;
        }

        public Criteria andRoomidIn(List<Integer> values) {
            addCriterion("roomid in", values, "roomid");
            return (Criteria) this;
        }

        public Criteria andRoomidNotIn(List<Integer> values) {
            addCriterion("roomid not in", values, "roomid");
            return (Criteria) this;
        }

        public Criteria andRoomidBetween(Integer value1, Integer value2) {
            addCriterion("roomid between", value1, value2, "roomid");
            return (Criteria) this;
        }

        public Criteria andRoomidNotBetween(Integer value1, Integer value2) {
            addCriterion("roomid not between", value1, value2, "roomid");
            return (Criteria) this;
        }

        public Criteria andUuidnameIsNull() {
            addCriterion("uuidname is null");
            return (Criteria) this;
        }

        public Criteria andUuidnameIsNotNull() {
            addCriterion("uuidname is not null");
            return (Criteria) this;
        }

        public Criteria andUuidnameEqualTo(String value) {
            addCriterion("uuidname =", value, "uuidname");
            return (Criteria) this;
        }

        public Criteria andUuidnameNotEqualTo(String value) {
            addCriterion("uuidname <>", value, "uuidname");
            return (Criteria) this;
        }

        public Criteria andUuidnameGreaterThan(String value) {
            addCriterion("uuidname >", value, "uuidname");
            return (Criteria) this;
        }

        public Criteria andUuidnameGreaterThanOrEqualTo(String value) {
            addCriterion("uuidname >=", value, "uuidname");
            return (Criteria) this;
        }

        public Criteria andUuidnameLessThan(String value) {
            addCriterion("uuidname <", value, "uuidname");
            return (Criteria) this;
        }

        public Criteria andUuidnameLessThanOrEqualTo(String value) {
            addCriterion("uuidname <=", value, "uuidname");
            return (Criteria) this;
        }

        public Criteria andUuidnameLike(String value) {
            addCriterion("uuidname like", value, "uuidname");
            return (Criteria) this;
        }

        public Criteria andUuidnameNotLike(String value) {
            addCriterion("uuidname not like", value, "uuidname");
            return (Criteria) this;
        }

        public Criteria andUuidnameIn(List<String> values) {
            addCriterion("uuidname in", values, "uuidname");
            return (Criteria) this;
        }

        public Criteria andUuidnameNotIn(List<String> values) {
            addCriterion("uuidname not in", values, "uuidname");
            return (Criteria) this;
        }

        public Criteria andUuidnameBetween(String value1, String value2) {
            addCriterion("uuidname between", value1, value2, "uuidname");
            return (Criteria) this;
        }

        public Criteria andUuidnameNotBetween(String value1, String value2) {
            addCriterion("uuidname not between", value1, value2, "uuidname");
            return (Criteria) this;
        }

        public Criteria andRealnameIsNull() {
            addCriterion("realname is null");
            return (Criteria) this;
        }

        public Criteria andRealnameIsNotNull() {
            addCriterion("realname is not null");
            return (Criteria) this;
        }

        public Criteria andRealnameEqualTo(String value) {
            addCriterion("realname =", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotEqualTo(String value) {
            addCriterion("realname <>", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameGreaterThan(String value) {
            addCriterion("realname >", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameGreaterThanOrEqualTo(String value) {
            addCriterion("realname >=", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameLessThan(String value) {
            addCriterion("realname <", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameLessThanOrEqualTo(String value) {
            addCriterion("realname <=", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameLike(String value) {
            addCriterion("realname like", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotLike(String value) {
            addCriterion("realname not like", value, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameIn(List<String> values) {
            addCriterion("realname in", values, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotIn(List<String> values) {
            addCriterion("realname not in", values, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameBetween(String value1, String value2) {
            addCriterion("realname between", value1, value2, "realname");
            return (Criteria) this;
        }

        public Criteria andRealnameNotBetween(String value1, String value2) {
            addCriterion("realname not between", value1, value2, "realname");
            return (Criteria) this;
        }

        public Criteria andSavepathIsNull() {
            addCriterion("savepath is null");
            return (Criteria) this;
        }

        public Criteria andSavepathIsNotNull() {
            addCriterion("savepath is not null");
            return (Criteria) this;
        }

        public Criteria andSavepathEqualTo(String value) {
            addCriterion("savepath =", value, "savepath");
            return (Criteria) this;
        }

        public Criteria andSavepathNotEqualTo(String value) {
            addCriterion("savepath <>", value, "savepath");
            return (Criteria) this;
        }

        public Criteria andSavepathGreaterThan(String value) {
            addCriterion("savepath >", value, "savepath");
            return (Criteria) this;
        }

        public Criteria andSavepathGreaterThanOrEqualTo(String value) {
            addCriterion("savepath >=", value, "savepath");
            return (Criteria) this;
        }

        public Criteria andSavepathLessThan(String value) {
            addCriterion("savepath <", value, "savepath");
            return (Criteria) this;
        }

        public Criteria andSavepathLessThanOrEqualTo(String value) {
            addCriterion("savepath <=", value, "savepath");
            return (Criteria) this;
        }

        public Criteria andSavepathLike(String value) {
            addCriterion("savepath like", value, "savepath");
            return (Criteria) this;
        }

        public Criteria andSavepathNotLike(String value) {
            addCriterion("savepath not like", value, "savepath");
            return (Criteria) this;
        }

        public Criteria andSavepathIn(List<String> values) {
            addCriterion("savepath in", values, "savepath");
            return (Criteria) this;
        }

        public Criteria andSavepathNotIn(List<String> values) {
            addCriterion("savepath not in", values, "savepath");
            return (Criteria) this;
        }

        public Criteria andSavepathBetween(String value1, String value2) {
            addCriterion("savepath between", value1, value2, "savepath");
            return (Criteria) this;
        }

        public Criteria andSavepathNotBetween(String value1, String value2) {
            addCriterion("savepath not between", value1, value2, "savepath");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}