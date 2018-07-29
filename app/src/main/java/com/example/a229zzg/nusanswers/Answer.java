package com.example.a229zzg.nusanswers;

public class Answer {
    String content;
    String uri;

    public Answer(String content, String uri) {
        this.content = content;
        this.uri = uri;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
