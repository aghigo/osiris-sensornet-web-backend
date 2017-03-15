package br.uff.labtempo.osiris.model.generator.collector;

import br.uff.labtempo.osiris.to.collector.CollectorCoTo;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class CollectorGenerator {

    private final String ID_PREFIX = "collectorId";
    private Random random;
    private Map<String, List<String>> collectorInfos;
    private final TimeUnit[] timeUnits = {TimeUnit.SECONDS, TimeUnit.MILLISECONDS, TimeUnit.MINUTES};

    public CollectorGenerator() {
        this.random = new Random();
        this.collectorInfos = new LinkedHashMap<>();
        this.collectorInfos.put("description", Arrays.asList("UFF laboratory", "STI", "network collector", "grouped collector"));
        this.collectorInfos.put("type", Arrays.asList("centralized", "distributed"));
        this.collectorInfos.put("transmission", Arrays.asList("wireless", "wired", "special fiber"));
    }

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

    public String getId() {
        return ID_PREFIX + (long) (Math.random() * 999999);
    }

    public long getCaptureInterval() {
        return new Date().getTime();
    }

    public TimeUnit getCaptureIntervalTimeUnit() {
        return this.timeUnits[(int) (Math.random() * this.timeUnits.length)];
    }

    public CollectorCoTo generate() {
        CollectorCoTo collectorCoTo = new CollectorCoTo(getId(), getCaptureInterval(), getCaptureIntervalTimeUnit());
        collectorCoTo.addInfo(getRandomCollectorInfos());
        return collectorCoTo;
    }
}
