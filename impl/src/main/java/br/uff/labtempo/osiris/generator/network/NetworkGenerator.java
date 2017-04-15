package br.uff.labtempo.osiris.generator.network;

import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Class resonsible to generate randomly Network mock objects for SensorNet module
 * @see NetworkCoTo
 * @see br.uff.labtempo.osiris.to.sensornet.NetworkSnTo
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@Component
public class NetworkGenerator {

    private Map<String, List<String>> networkInfos;

    public NetworkGenerator(){
        this.networkInfos = new LinkedHashMap<>();
        this.networkInfos.put("domain", Arrays.asList("br.uff.labtempo", "br.uff.sti", "br.uff.ic"));
        this.networkInfos.put("type", Arrays.asList("Wireless", "LAN", "WLAN", "VPN"));
        this.networkInfos.put("operating system", Arrays.asList("FreeBSD", "Android 4.4", "TinyOS"
                , "Windows Server 2013", "Ubuntu 16.04 LTS", "Fedora Workstation", "Windows 98", "Windows NT"));
        this.networkInfos.put("hardware", Arrays.asList("Arduino v6.4", "Raspberry Pi v1.6", "EmbbededTech x86"));
    }

    /**
     * Generates a random NetworkCoTo mock object
     * @see NetworkCoTo
     * @return networkCoTo
     */
    public NetworkCoTo getNetworkCoto() {
        NetworkCoTo networkCoTo = new NetworkCoTo(getId());
        networkCoTo.addInfo(getInfo());
        return networkCoTo;
    }

    /**
     * Get an random Map of Network Infos
     * Infos are previous populated on constructor with fixed values
     * An Info contains name and description
     * @see br.uff.labtempo.osiris.to.common.data.InfoTo
     * @return Map<String,String> where Key is the field name and value is its possible description
     */
    private Map<String, String> getInfo() {
        Map<String, String> infos = new LinkedHashMap<>();
        int totalKeys = this.networkInfos.size();

        Set<String> selectedKeys = new LinkedHashSet<>();
        for(int i = 0; i < (int) (Math.random() * totalKeys) + 1; i++) {
            int keyPosition = (int) (Math.random() *  networkInfos.keySet().size());
            String k = (String) networkInfos.keySet().toArray()[keyPosition];
            selectedKeys.add(k);
        }

        for(String key : selectedKeys) {
            int valuePosition = (int) (Math.random() * networkInfos.get(key).size());
            String value = networkInfos.get(key).get(valuePosition);
            infos.put(key, value);
        }
        return infos;
    }

    /**
     * Generates an unique random mocked id for the Network
     * pattern: "networkId-" + UUID
     * @see UUID
     * @return String with the unique network Id
     */
    private String getId() {
        return "networkId-" + UUID.randomUUID().toString();
    }
}
