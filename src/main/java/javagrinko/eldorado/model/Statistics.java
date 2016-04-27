package javagrinko.eldorado.model;

public class Statistics {
    private double totalSummary;
    private int bestCustomerId;
    private double maxOrderValue;
    private double minOrderValue;
    private long ordersCount;
    private double expectedMeanValue;

    public double getTotalSummary() {
        return totalSummary;
    }

    public void setTotalSummary(double totalSummary) {
        this.totalSummary = totalSummary;
    }

    public int getBestCustomerId() {
        return bestCustomerId;
    }

    public void setBestCustomerId(int bestCustomerId) {
        this.bestCustomerId = bestCustomerId;
    }

    public double getMaxOrderValue() {
        return maxOrderValue;
    }

    public void setMaxOrderValue(double maxOrderValue) {
        this.maxOrderValue = maxOrderValue;
    }

    public double getMinOrderValue() {
        return minOrderValue;
    }

    public void setMinOrderValue(double minOrderValue) {
        this.minOrderValue = minOrderValue;
    }

    public long getOrdersCount() {
        return ordersCount;
    }

    public void setOrdersCount(long ordersCount) {
        this.ordersCount = ordersCount;
    }

    public double getExpectedMeanValue() {
        return expectedMeanValue;
    }

    public void setExpectedMeanValue(double expectedMeanValue) {
        this.expectedMeanValue = expectedMeanValue;
    }
}
