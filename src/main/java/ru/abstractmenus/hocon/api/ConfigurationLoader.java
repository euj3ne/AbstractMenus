package ru.abstractmenus.hocon.api;

import ru.abstractmenus.hocon.*;
import ru.abstractmenus.hocon.api.source.ConfigSource;
import ru.abstractmenus.hocon.api.serialize.NodeSerializers;

public class ConfigurationLoader {

    private final ConfigSource source;
    private final NodeSerializers serializers;

    private ConfigurationLoader(ConfigSource source, NodeSerializers serializer) {
        this.source = source;
        this.serializers = serializer;
    }

    public ConfigSource getSource() {
        return source;
    }

    public NodeSerializers getSerializers() {
        return serializers;
    }

    public ConfigNode load() throws Exception {
        Config conf = ConfigFactory.parseReader(source.getReader())
                .resolve();
        return new SimpleConfigNode(null, null, conf.root(), serializers);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private ConfigSource source;
        private NodeSerializers serializers;

        public Builder() {
            this.serializers = NodeSerializers.defaults();
        }

        public NodeSerializers serializers() {
            return serializers;
        }

        public Builder source(ConfigSource source) {
            this.source = source;
            return this;
        }

        public Builder serializers(NodeSerializers serializers) {
            this.serializers = serializers;
            return this;
        }

        public ConfigurationLoader build() {
            return new ConfigurationLoader(source, serializers);
        }

    }
}
