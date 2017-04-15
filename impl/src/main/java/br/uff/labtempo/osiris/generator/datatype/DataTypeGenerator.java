package br.uff.labtempo.osiris.generator.datatype;

import br.uff.labtempo.osiris.to.common.definitions.ValueType;
import br.uff.labtempo.osiris.to.virtualsensornet.DataTypeVsnTo;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Class responsible for Generate an DataType mock object for the VirtualSensorNet module
 * An DataType is an data object measure information that is used on VirtualSensors (vsensor, link, blending, composite).
 * @see DataTypeVsnTo
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
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

    /**
     * Generates a new randomly DataTypeVsnTo mock object
     * @see DataTypeVsnTo
     * @return DataTypeVsnTo
     */
    public DataTypeVsnTo getDataTypeVsnTo() {
        String name = getName();
        ValueType type = getType(name);
        String unit = getUnit(name);
        String symbol = getSymbol(name);
        return new DataTypeVsnTo(name, type, unit ,symbol);
    }

    /**
     * Get a random dataType name
     * @return String with the name of the DataType
     */
    private String getName() {
        int p = (int) (Math.random() * this.nameUnitMap.keySet().size());
        return (String) this.nameUnitMap.keySet().toArray()[p];
    }

    /**
     * Get a random ValueType
     * ValueType is an enum that can be = NUMBER | LOGIC | TEXT
     * @see ValueType
     * @param name
     * @return
     */
    private ValueType getType(String name) {
        return this.nameTypeMap.get(name);
    }

    /**
     * Get an dataType Unit (e.g. Celsius, Fahrenheit, Kelvin)
     * @param name
     * @return String with the unit name
     */
    private String getUnit(String name) {
        List<String> unitList = this.nameUnitMap.get(name);
        return unitList.get((int) (Math.random() * unitList.size()));
    }

    /**
     * Get a DataType unit Symbol (e.g. "ºC" for Celsius Temperature)
     * @param name
     * @return String with the unit symbol
     */
    private String getSymbol(String name) {
        List<String> symbolList = this.nameSymbolMap.get(name);
        return symbolList.get((int) (Math.random() * symbolList.size()));
    }
}