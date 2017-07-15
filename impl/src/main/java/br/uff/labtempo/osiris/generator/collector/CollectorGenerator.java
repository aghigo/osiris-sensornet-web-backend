package br.uff.labtempo.osiris.generator.collector;

import br.uff.labtempo.osiris.to.collector.CollectorCoTo;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Class for generatic a random Collector mock object
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Component
public class CollectorGenerator {

    private Map<String, List<String>> collectorInfos;
    private final TimeUnit defaultCaptureTimeUnit = TimeUnit.SECONDS;
    private final long defaultCaptureInterval = 1;

    public CollectorGenerator() {
        this.collectorInfos = new LinkedHashMap<>();
        this.collectorInfos.put("description", Arrays.asList("UFF laboratory", "STI", "network collector", "grouped collector"));
        this.collectorInfos.put("type", Arrays.asList("centralized", "distributed"));
        this.collectorInfos.put("transmission", Arrays.asList("wireless", "wired", "special fiber"));
    }

    /**
     * Generates a random Collector mock object
     * @return CollectorCoTo
     */
    public CollectorCoTo getCollectorCoTo() {
        CollectorCoTo collectorCoTo = new CollectorCoTo(getId(), this.defaultCaptureInterval, this.defaultCaptureTimeUnit);
        collectorCoTo.addInfo(getRandomCollectorInfos());
        return collectorCoTo;
    }

    /**
     * Get a Map of Collector Infos
     * The infos are populated on the constructor with fixed names and descriptions
     * An info contains name and description
     * @see br.uff.labtempo.osiris.to.common.data.InfoTo
     * @return
     */
    private Map<String, String> getRandomCollectorInfos() {
        Map<String, String> infos = new LinkedHashMap<>();
        Set<String> selectedKeys = new LinkedHashSet<>();
        int size = this.collectorInfos.size();
        for(int i = 0; i < (int) (Math.random() * size) + 1; i++) {
            selectedKeys.add((String) this.collectorInfos.keySet().toArray()[(int) (Math.random() * size)]);
        }
        for(String key : selectedKeys) {
            List<String> values = this.collectorInfos.get(key);
            String value = values.get((int) (Math.random() * values.size()));
            infos.put(key, value);
        }
        return infos;
    }

    /**
     * Generates an unique id for Collector
     * Pattern "collectorId-" + UUID
     * @see UUID
     * @return String with the unique CollectorId
     */
    public String getId() {
        return "collectorId-" + UUID.randomUUID().toString();
    }
}
