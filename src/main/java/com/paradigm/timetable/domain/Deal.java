package com.paradigm.timetable.domain;

/**
 * Because no Room instances change during solving, a Deal is called a problem fact.
 * Such Underwriters do not require any OptaPlanner specific annotations.
 */
public class Deal {

    private String dealId;

    public Deal() {
    }

    public Deal(String dealId) {
        this.dealId = dealId;
    }

    public String getDealId() {
        return dealId;
    }

    @Override
    public String toString() {
        return "Deal{" +
            "dealId='" + dealId + '\'' +
            '}';
    }
}
