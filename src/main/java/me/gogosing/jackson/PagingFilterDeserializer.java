package me.gogosing.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;
import me.gogosing.model.filter.FilterDateRange;
import me.gogosing.model.filter.FilterNumericRange;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
public class PagingFilterDeserializer extends StdDeserializer<Map<String, Object>> {

    public PagingFilterDeserializer() {
        super(Map.class);
    }

    @Override
    public Map<String, Object> deserialize(JsonParser parser, DeserializationContext context) throws IOException {

        final var filters = new HashMap<String, Object>();
        final var rootNode = (JsonNode)parser.getCodec().readTree(parser);

        for (Iterator<Map.Entry<String, JsonNode>> it = rootNode.fields(); it.hasNext();) {

            final var nodeEntry = it.next();
            final var valueNode = nodeEntry.getValue();

            if (valueNode.isObject()) {
                final var filterParser = valueNode.traverse(parser.getCodec());
                if (valueNode.has("from")) {
                    filters.put(
                        nodeEntry.getKey(),
                        filterParser.readValueAs(FilterDateRange.class)
                    );
                } else if (valueNode.has("min") && valueNode.has("max")) {
                    filters.put(
                        nodeEntry.getKey(),
                        filterParser.readValueAs(FilterNumericRange.class)
                    );
                } else {
                    log.warn("[{}]는 정의되지 않은 유형입니다. 추가 구현이 필요 합니다.", nodeEntry.getKey());
                }
            } else if (valueNode.isArray()) {
                filters.put(
                    nodeEntry.getKey(),
                    StreamSupport
                        .stream(valueNode.spliterator(), false)
                        .map(JsonNode::textValue)
                        .collect(Collectors.toList())
                );
            } else if (valueNode.isTextual()) {
                filters.put(
                    nodeEntry.getKey(),
                    valueNode.textValue()
                );
            }
        }
        return filters;
    }
}
