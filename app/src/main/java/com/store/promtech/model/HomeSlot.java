package com.store.promtech.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeSlot {

    @SerializedName("SlotData")
    @Expose
    private List<SlotDatum> slotData = null;
    @SerializedName("Ack")
    @Expose
    private Integer ack;
    @SerializedName("msg")
    @Expose
    private String msg;

    public List<SlotDatum> getSlotData() {
        return slotData;
    }

    public void setSlotData(List<SlotDatum> slotData) {
        this.slotData = slotData;
    }

    public Integer getAck() {
        return ack;
    }

    public void setAck(Integer ack) {
        this.ack = ack;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public class SlotDatum {

        @SerializedName("slot_id")
        @Expose
        private String slotId;
        @SerializedName("slot_name")
        @Expose
        private String slotName;

        public String getSlotId() {
            return slotId;
        }

        public void setSlotId(String slotId) {
            this.slotId = slotId;
        }

        public String getSlotName() {
            return slotName;
        }

        public void setSlotName(String slotName) {
            this.slotName = slotName;
        }
    }
}
