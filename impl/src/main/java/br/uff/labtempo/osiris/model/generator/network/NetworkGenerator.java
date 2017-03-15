package br.uff.labtempo.osiris.model.generator.network;


import br.uff.labtempo.osiris.to.collector.NetworkCoTo;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class NetworkGenerator {

    private Random random;
    private Map<String, List<String>> networkInfos;

    public NetworkGenerator(){
        this.random = new Random();
        this.networkInfos = new LinkedHashMap<>();
        this.networkInfos.put("domain", Arrays.asList("br.uff.labtempo", "br.uff.sti", "br.uff.ic"));
        this.networkInfos.put("type", Arrays.asList("Wireless", "LAN", "WLAN", "VPN"));
        this.networkInfos.put("operating system", Arrays.asList("FreeBSD", "Android 4.4", "TinyOS"
                , "Windows Server 2013", "Ubuntu 16.04 LTS", "Fedora Workstation", "Windows 98", "Windows NT"));
        this.networkInfos.put("hardware", Arrays.asList("Arduino v6.4", "Raspberry Pi v1.6", "EmbbededTech x86"));
    }

    public NetworkCoTo generate() {
        NetworkCoTo networkCoTo = new NetworkCoTo(getId());
        networkCoTo.addInfo(getInfo());
        return networkCoTo;
    }

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

    private String getId() {
        return "networkId" + this.random.nextInt(10000) + 1;
    }
}
