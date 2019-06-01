package com.lmx.blog.model;

import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;

@Document(indexName = "huskar_message",type = "message",shards = 1,replicas = 0)
public class MapEsData {
    private Long sequenceIndex;

    private String symbol;

    private BigDecimal amount;

    private Integer triggerOn;

    private BigDecimal makerFeeRate;

    private Integer fee;

    private Integer refOrderId;

    private String feeCurrency;

    private Boolean chargeQuote;

    private String source;

    private String type;

    private Long previousSeqId;

    private Long refSeqId;

    private Long userId;

    private Integer version;

    private Integer filledAmount;

    private Long createdAt;

    private Integer features;

    private BigDecimal price;

    private BigDecimal takerFeeRate;

    private Long id;

    private Long seqId;

    private String status;

    private Long updatedAt;

    public Long getSequenceIndex() {
        return sequenceIndex;
    }

    public void setSequenceIndex(Long sequenceIndex) {
        this.sequenceIndex = sequenceIndex;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getTriggerOn() {
        return triggerOn;
    }

    public void setTriggerOn(Integer triggerOn) {
        this.triggerOn = triggerOn;
    }

    public BigDecimal getMakerFeeRate() {
        return makerFeeRate;
    }

    public void setMakerFeeRate(BigDecimal makerFeeRate) {
        this.makerFeeRate = makerFeeRate;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Integer getRefOrderId() {
        return refOrderId;
    }

    public void setRefOrderId(Integer refOrderId) {
        this.refOrderId = refOrderId;
    }

    public String getFeeCurrency() {
        return feeCurrency;
    }

    public void setFeeCurrency(String feeCurrency) {
        this.feeCurrency = feeCurrency;
    }

    public Boolean getChargeQuote() {
        return chargeQuote;
    }

    public void setChargeQuote(Boolean chargeQuote) {
        this.chargeQuote = chargeQuote;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPreviousSeqId() {
        return previousSeqId;
    }

    public void setPreviousSeqId(Long previousSeqId) {
        this.previousSeqId = previousSeqId;
    }

    public Long getRefSeqId() {
        return refSeqId;
    }

    public void setRefSeqId(Long refSeqId) {
        this.refSeqId = refSeqId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getFilledAmount() {
        return filledAmount;
    }

    public void setFilledAmount(Integer filledAmount) {
        this.filledAmount = filledAmount;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getFeatures() {
        return features;
    }

    public void setFeatures(Integer features) {
        this.features = features;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTakerFeeRate() {
        return takerFeeRate;
    }

    public void setTakerFeeRate(BigDecimal takerFeeRate) {
        this.takerFeeRate = takerFeeRate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSeqId() {
        return seqId;
    }

    public void setSeqId(Long seqId) {
        this.seqId = seqId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }
}
