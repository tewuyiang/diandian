package com.diandian.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserdetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserdetailExample() {
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

        public Criteria andRoomdetailidIsNull() {
            addCriterion("roomdetailid is null");
            return (Criteria) this;
        }

        public Criteria andRoomdetailidIsNotNull() {
            addCriterion("roomdetailid is not null");
            return (Criteria) this;
        }

        public Criteria andRoomdetailidEqualTo(Integer value) {
            addCriterion("roomdetailid =", value, "roomdetailid");
            return (Criteria) this;
        }

        public Criteria andRoomdetailidNotEqualTo(Integer value) {
            addCriterion("roomdetailid <>", value, "roomdetailid");
            return (Criteria) this;
        }

        public Criteria andRoomdetailidGreaterThan(Integer value) {
            addCriterion("roomdetailid >", value, "roomdetailid");
            return (Criteria) this;
        }

        public Criteria andRoomdetailidGreaterThanOrEqualTo(Integer value) {
            addCriterion("roomdetailid >=", value, "roomdetailid");
            return (Criteria) this;
        }

        public Criteria andRoomdetailidLessThan(Integer value) {
            addCriterion("roomdetailid <", value, "roomdetailid");
            return (Criteria) this;
        }

        public Criteria andRoomdetailidLessThanOrEqualTo(Integer value) {
            addCriterion("roomdetailid <=", value, "roomdetailid");
            return (Criteria) this;
        }

        public Criteria andRoomdetailidIn(List<Integer> values) {
            addCriterion("roomdetailid in", values, "roomdetailid");
            return (Criteria) this;
        }

        public Criteria andRoomdetailidNotIn(List<Integer> values) {
            addCriterion("roomdetailid not in", values, "roomdetailid");
            return (Criteria) this;
        }

        public Criteria andRoomdetailidBetween(Integer value1, Integer value2) {
            addCriterion("roomdetailid between", value1, value2, "roomdetailid");
            return (Criteria) this;
        }

        public Criteria andRoomdetailidNotBetween(Integer value1, Integer value2) {
            addCriterion("roomdetailid not between", value1, value2, "roomdetailid");
            return (Criteria) this;
        }

        public Criteria andUseridIsNull() {
            addCriterion("userid is null");
            return (Criteria) this;
        }

        public Criteria andUseridIsNotNull() {
            addCriterion("userid is not null");
            return (Criteria) this;
        }

        public Criteria andUseridEqualTo(Integer value) {
            addCriterion("userid =", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotEqualTo(Integer value) {
            addCriterion("userid <>", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThan(Integer value) {
            addCriterion("userid >", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThanOrEqualTo(Integer value) {
            addCriterion("userid >=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThan(Integer value) {
            addCriterion("userid <", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThanOrEqualTo(Integer value) {
            addCriterion("userid <=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridIn(List<Integer> values) {
            addCriterion("userid in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotIn(List<Integer> values) {
            addCriterion("userid not in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridBetween(Integer value1, Integer value2) {
            addCriterion("userid between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotBetween(Integer value1, Integer value2) {
            addCriterion("userid not between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andAttendtimeIsNull() {
            addCriterion("attendtime is null");
            return (Criteria) this;
        }

        public Criteria andAttendtimeIsNotNull() {
            addCriterion("attendtime is not null");
            return (Criteria) this;
        }

        public Criteria andAttendtimeEqualTo(Date value) {
            addCriterion("attendtime =", value, "attendtime");
            return (Criteria) this;
        }

        public Criteria andAttendtimeNotEqualTo(Date value) {
            addCriterion("attendtime <>", value, "attendtime");
            return (Criteria) this;
        }

        public Criteria andAttendtimeGreaterThan(Date value) {
            addCriterion("attendtime >", value, "attendtime");
            return (Criteria) this;
        }

        public Criteria andAttendtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("attendtime >=", value, "attendtime");
            return (Criteria) this;
        }

        public Criteria andAttendtimeLessThan(Date value) {
            addCriterion("attendtime <", value, "attendtime");
            return (Criteria) this;
        }

        public Criteria andAttendtimeLessThanOrEqualTo(Date value) {
            addCriterion("attendtime <=", value, "attendtime");
            return (Criteria) this;
        }

        public Criteria andAttendtimeIn(List<Date> values) {
            addCriterion("attendtime in", values, "attendtime");
            return (Criteria) this;
        }

        public Criteria andAttendtimeNotIn(List<Date> values) {
            addCriterion("attendtime not in", values, "attendtime");
            return (Criteria) this;
        }

        public Criteria andAttendtimeBetween(Date value1, Date value2) {
            addCriterion("attendtime between", value1, value2, "attendtime");
            return (Criteria) this;
        }

        public Criteria andAttendtimeNotBetween(Date value1, Date value2) {
            addCriterion("attendtime not between", value1, value2, "attendtime");
            return (Criteria) this;
        }

        public Criteria andPresenttimeIsNull() {
            addCriterion("presenttime is null");
            return (Criteria) this;
        }

        public Criteria andPresenttimeIsNotNull() {
            addCriterion("presenttime is not null");
            return (Criteria) this;
        }

        public Criteria andPresenttimeEqualTo(String value) {
            addCriterion("presenttime =", value, "presenttime");
            return (Criteria) this;
        }

        public Criteria andPresenttimeNotEqualTo(String value) {
            addCriterion("presenttime <>", value, "presenttime");
            return (Criteria) this;
        }

        public Criteria andPresenttimeGreaterThan(String value) {
            addCriterion("presenttime >", value, "presenttime");
            return (Criteria) this;
        }

        public Criteria andPresenttimeGreaterThanOrEqualTo(String value) {
            addCriterion("presenttime >=", value, "presenttime");
            return (Criteria) this;
        }

        public Criteria andPresenttimeLessThan(String value) {
            addCriterion("presenttime <", value, "presenttime");
            return (Criteria) this;
        }

        public Criteria andPresenttimeLessThanOrEqualTo(String value) {
            addCriterion("presenttime <=", value, "presenttime");
            return (Criteria) this;
        }

        public Criteria andPresenttimeLike(String value) {
            addCriterion("presenttime like", value, "presenttime");
            return (Criteria) this;
        }

        public Criteria andPresenttimeNotLike(String value) {
            addCriterion("presenttime not like", value, "presenttime");
            return (Criteria) this;
        }

        public Criteria andPresenttimeIn(List<String> values) {
            addCriterion("presenttime in", values, "presenttime");
            return (Criteria) this;
        }

        public Criteria andPresenttimeNotIn(List<String> values) {
            addCriterion("presenttime not in", values, "presenttime");
            return (Criteria) this;
        }

        public Criteria andPresenttimeBetween(String value1, String value2) {
            addCriterion("presenttime between", value1, value2, "presenttime");
            return (Criteria) this;
        }

        public Criteria andPresenttimeNotBetween(String value1, String value2) {
            addCriterion("presenttime not between", value1, value2, "presenttime");
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