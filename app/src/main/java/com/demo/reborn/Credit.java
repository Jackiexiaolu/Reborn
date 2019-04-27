package com.demo.reborn;

public class Credit {

    private int priority;
    private String currency;
    private String used;
    private String amount;
    private String unused;

    public Credit(int priority, String currency, String used, String amount, String unused){
        super();
        this.priority = priority;
        this.currency = currency;
        this.used = used;
        this.unused = unused;
        this.amount = amount;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String urrency) {
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getUnused() {
        return unused;
    }

    public void setUnused(String unused) {
        this.unused = unused;
    }

}


