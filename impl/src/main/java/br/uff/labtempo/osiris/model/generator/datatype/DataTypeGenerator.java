package br.uff.labtempo.osiris.model.generator.datatype;

import br.uff.labtempo.osiris.to.common.definitions.ValueType;
import br.uff.labtempo.osiris.to.virtualsensornet.DataTypeVsnTo;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataTypeGenerator {

    private Map<String, ValueType> nameTypeMap;
    private Map<String, List<String>> nameUnitMap;
    private Map<String, List<String>> nameSymbolMap;

    public DataTypeGenerator() {
        this.nameUnitMap = new LinkedHashMap<>();
        this.nameSymbolMap = new LinkedHashMap<>();
        this.nameTypeMap = new LinkedHashMap<>();

        nameTypeMap.put("temperature", ValueType.NUMBER);

        List<String> temperatureUnitList = new ArrayList<>();
        temperatureUnitList.add("celsius"); temperatureUnitList.add("fahrenheit"); temperatureUnitList.add("kelvin");
        nameUnitMap.put("temperature", temperatureUnitList);

        List<String> temperatureSymbolList = new ArrayList<>();
        temperatureSymbolList.add("Cº"); temperatureSymbolList.add("Fº"); temperatureSymbolList.add("K");
        nameSymbolMap.put("temperature", temperatureSymbolList);


        nameTypeMap.put("luminosity", ValueType.NUMBER);

        List<String> luminosityUnitList = new ArrayList<>();
        luminosityUnitList.add("candela");
        nameUnitMap.put("luminosity", luminosityUnitList);

        List<String> luminositySymbolList = new ArrayList<>();
        luminositySymbolList.add("C");
        nameSymbolMap.put("luminosity", luminositySymbolList);

        nameTypeMap.put("pressure", ValueType.NUMBER);

        List<String> pressureUnitList = new ArrayList<>();
        pressureUnitList.add("pascal");
        nameUnitMap.put("pressure", pressureUnitList);

        List<String> pressureSymbolList = new ArrayList<>();
        pressureSymbolList.add("P");
        nameSymbolMap.put("pressure", pressureSymbolList);
    }

    public DataTypeVsnTo getDataTypeVsnTo() {
        String name = getName();
        ValueType type = getType(name);
        String unit = getUnit(name);
        String symbol = getSymbol(name);
        return new DataTypeVsnTo(name, type, unit ,symbol);
    }

    private String getName() {
        int p = (int) (Math.random() * this.nameUnitMap.keySet().size());
        return (String) this.nameUnitMap.keySet().toArray()[p];
    }

    private ValueType getType(String name) {
        return this.nameTypeMap.get(name);
    }

    private String getUnit(String name) {
        List<String> unitList = this.nameUnitMap.get(name);
        return unitList.get((int) (Math.random() * unitList.size()));
    }

    private String getSymbol(String name) {
        List<String> symbolList = this.nameSymbolMap.get(name);
        return symbolList.get((int) (Math.random() * symbolList.size()));
    }
}