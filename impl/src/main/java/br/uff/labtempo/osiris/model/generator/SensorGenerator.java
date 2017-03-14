package br.uff.labtempo.osiris.model.generator;


import br.uff.labtempo.osiris.to.collector.SensorCoTo;
import br.uff.labtempo.osiris.to.common.definitions.LogicalOperator;
import br.uff.labtempo.osiris.to.common.definitions.State;

import java.util.*;

public class SensorGenerator {

    private final int VALUE_RANGE = 10;
    private Map<String, List<String>> values;

    public SensorGenerator() {
        this.values = new LinkedHashMap<String, List<String>>();
        this.values.put("temperature", Arrays.asList(""));
        this.values.put("", Arrays.asList(""));
        this.values.put("", Arrays.asList(""));
        this.values.put("", Arrays.asList(""));
    }

    private String[] names = {"temperature", "light", "voltage"};
    private String[] units = {"celsius", "candela", "volt"};
    private String[] symbols = {"Cº", "C", "V"};

    private final int INFO_RANGE = 7;
    private String[] infokeysNames = {"sendCount", "parent", "metric", "moteModel"};
    private String[] infoDescriptions = {"Total of sent values", "parent sensor", "millimeter", "motor"};

    private final int CONSUMABLE_RANGE = 7;
    private String[] consumableNames = {"battery", "signal"};

    private final int CONSUMABLE_RULE_RANGE = 4;
    private String[] consumableRuleName = {"Low Battery", "Full Battery", "Empty Battery", "Good Battery"};
    private LogicalOperator[] logicalOperators = LogicalOperator.values();
    private String[] messages = {"Battery level is too low!", "Battery is full.", "Battery level is normal.", "Battery is empty."};
    private State[] states = State.values();

    public SensorCoTo generate() {
        SensorCoTo sensorCoTo = new SensorCoTo(getId(), getState(), getCaptureTimestampInMillis(), getCapturePrecisionInNano(), getAcquisitionTimestampInMillis());
        for(int i = 0; i < (int) (Math.random() * VALUE_RANGE + 1); i++) {
            sensorCoTo.addValue(getName(), getValue(), getUnit(), getSymbols());
        }
        for(int i = 0; i < (int) (Math.random() * INFO_RANGE + 1); i++) {
            sensorCoTo.addInfo(getinfoKeyName(), getinfoDescriptions());
        }
        for(int i = 0; i < (int) (Math.random() * CONSUMABLE_RANGE + 1); i++) {
            sensorCoTo.addConsumable(getConsumableName(), getCurrentValue());
        }
        for(int i = 0; i < (int) (Math.random() * CONSUMABLE_RULE_RANGE + 1); i++) {
            sensorCoTo.addConsumableRule(getConsumableRuleName(), getConsumableName(), getLogicalOperator(), getLimitValue(), getMessage());
        }
        return sensorCoTo;
    }

    public String getMessage() {
        return this.messages[(int) (Math.random() * this.messages.length)];
    }

    public int getLimitValue() {
        return (int) (Math.random() * 9999);
    }

    public LogicalOperator getLogicalOperator() {
        return this.logicalOperators[(int) (Math.random() * this.logicalOperators.length)];
    }

    public String getConsumableRuleName() {
        return this.consumableRuleName[(int) (Math.random() * this.consumableRuleName.length)];
    }

    public String getConsumableName() {
        return this.consumableNames[(int) (Math.random() * this.consumableNames.length)];
    }

    public int getCurrentValue() {
        return (int) (Math.random() * 9999);
    }

    public String getinfoKeyName() {
        return this.infokeysNames[(int) (Math.random() * this.infokeysNames.length)];
    }

    public String getinfoDescriptions() {
        return this.infoDescriptions[(int) (Math.random() * this.infoDescriptions.length)];
    }

    public String getName() {
        return this.names[(int) (Math.random() * this.names.length)];
    }

    public int getValue() {
        return (int) (Math.random() * 9999);
    }

    public String getUnit() {
        return this.units[(int) (Math.random() * this.units.length)];
    }

    public String getSymbols() {
        return this.symbols[(int) (Math.random() * this.symbols.length)];
    }


    public String getId() {
        return "sensorId" + (long) (Math.random() * 99999);
    }

    public State getState() {
        return this.states[(int) (Math.random() * this.states.length)];
    }

    public long getCaptureTimestampInMillis() {
        return new Date().getTime();
    }

    public int getCapturePrecisionInNano() {
        return (int) (Math.random() * 9999);
    }

    public long getAcquisitionTimestampInMillis() {
        return (long) (Math.random() * 9999);
    }
}
