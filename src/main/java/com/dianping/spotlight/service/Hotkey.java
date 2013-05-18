package com.dianping.spotlight.service;

import java.io.Serializable;
import java.util.Set;

public class Hotkey implements Serializable {

    private static final long serialVersionUID = 1L;

    private Set<String> tokens;
    private String name;
    private String videoUrl;
    private double usage;

    public Hotkey(Set<String> tokens, String name, String videoUrl, double usage) {
        this.tokens = tokens;
        this.name = name;
        this.videoUrl = videoUrl;
        this.usage = usage;
    }

    public Hotkey() {
    }

    public double getUsage() {
        return usage;
    }

    public void setUsage(double usage) {
        this.usage = usage;
    }

    public Set<String> getTokens() {
        return tokens;
    }

    public void setTokens(Set<String> tokens) {
        this.tokens = tokens;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tokens == null) ? 0 : tokens.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Hotkey other = (Hotkey) obj;
        if (tokens == null) {
            if (other.tokens != null)
                return false;
        } else if (!tokens.equals(other.tokens))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("Hotkey [tokens=%s, name=%s, videoUrl=%s, usage=%s]", tokens, name, videoUrl, usage);
    }

}
