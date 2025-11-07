package com.milkstoremobile_fronend.models.message;

public class MessageAIRequest {
    private String content;

    public MessageAIRequest(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
