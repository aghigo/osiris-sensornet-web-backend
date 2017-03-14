package br.uff.labtempo.osiris.model.generator;

import br.uff.labtempo.osiris.to.collector.CollectorCoTo;

import java.util.concurrent.TimeUnit;

public class CollectorGenerator {
    private final int INFO_QUANTITY_MAX_RANGE = 15;
    private final TimeUnit[] timeUnits = {TimeUnit.SECONDS, TimeUnit.MILLISECONDS, TimeUnit.MINUTES};
    private final String[] keyInfoNames = {"description", "network", "operation", "consumable", "role", "rule", "type"};
    private final String[] keyInfoDescriptions = {"UFF laboratory", "Labtempo Network", "UFF Operations", "Sensor Battery"};

    public CollectorCoTo generate() {
        CollectorCoTo collectorCoTo = new CollectorCoTo(this.getId(), this.getCaptureInterval(), this.getTimeUnit());
        for(int i = 0; i < Math.random() * this.INFO_QUANTITY_MAX_RANGE + 1; i++) {
            collectorCoTo.addInfo(this.getKeyInfoName(), this.getKeyInfoDescription());
        }
        return collectorCoTo;
    }

    private String getId() {
        return "collectorId" + ((int) (Math.random() * 99999));
    }

    private Long getCaptureInterval()  {
        return (long) (Math.random() * 61);
    }

    private TimeUnit getTimeUnit() {
        return this.timeUnits[(int) (Math.random() * (this.timeUnits.length))];
    }

    private String getKeyInfoName() {
        return this.keyInfoNames[(int) (Math.random() * (this.keyInfoNames.length))];
    }

    private String getKeyInfoDescription() {
        return this.keyInfoDescriptions[(int) (Math.random() * (this.keyInfoDescriptions.length))];
    }
}
