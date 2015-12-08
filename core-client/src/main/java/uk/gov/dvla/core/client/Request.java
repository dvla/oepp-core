package uk.gov.dvla.core.client;

import jersey.repackaged.com.google.common.collect.ImmutableMap;
import jersey.repackaged.com.google.common.collect.Maps;

import java.util.Map;

public class Request<P> {

    private P payload;
    private ImmutableMap<String, String> headers;

    private Request(P payload, ImmutableMap<String, String> headers) {
        this.payload = payload;
        this.headers = headers;
    }

    public ImmutableMap<String, String> getHeaders() {
        return headers;
    }

    public P getPayload() {
        return payload;
    }

    public static class Builder<P> {
        private P payload;
        private Map<String, String> headers = Maps.newHashMap();

        public Builder withPayload(P payload) {
            this.payload = payload;
            return this;
        }

        public Builder withHeader(String key, String value) {
            headers.put(key, value);
            return this;
        }

        public Builder withHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Request<P> create() {
            return new Request<>(payload, ImmutableMap.copyOf(headers));
        }
    }
}
