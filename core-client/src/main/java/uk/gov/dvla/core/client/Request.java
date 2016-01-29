package uk.gov.dvla.core.client;

import jersey.repackaged.com.google.common.collect.ImmutableMap;
import jersey.repackaged.com.google.common.collect.Maps;

import com.google.common.base.MoreObjects;

import java.util.Map;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request<?> request = (Request<?>) o;
        return Objects.equals(payload, request.payload) &&
                Objects.equals(headers, request.headers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(payload, headers);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("payload", payload)
                .add("headers", headers)
                .toString();
    }
}
