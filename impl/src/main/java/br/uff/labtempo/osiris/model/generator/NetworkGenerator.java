package br.uff.labtempo.osiris.model.generator;


import br.uff.labtempo.osiris.to.collector.NetworkCoTo;

import java.util.*;

public class NetworkGenerator {
    private final int INFO_MAX_RANGE = 11;
    private Map<String, List<String>> infos;

    public NetworkGenerator(){
        this.infos = new LinkedHashMap<>();
        this.infos.put("domain", Arrays.asList("br.uff.labtempo", "br.uff.sti", "br.uff.ic"));
        this.infos.put("type", Arrays.asList("Wireless", "LAN", "WLAN", "VPN"));
        this.infos.put("operating system", Arrays.asList("FreeBSD", "Android 4.4", "TinyOS"
                , "Windows Server 2013", "Ubuntu 16.04 LTS", "Fedora Workstation", "Windows 98", "Windows NT"));
        this.infos.put("hardware", Arrays.asList("Arduino v6.4", "Raspberry Pi v1.6", "EmbbededTech x86"));
    }

    public NetworkCoTo generate() {
        NetworkCoTo networkCoTo = new NetworkCoTo(this.getId());
        for(int i = 0; i < (int) (Math.random() * INFO_MAX_RANGE + 1); i++){
            String infoKeyName = getInfoKeyName();
            networkCoTo.addInfo(infoKeyName, getInfoDescription(infoKeyName));
        }
        return networkCoTo;
    }

    private String getId() {
        return "networkId_" + (long) (Math.random() * 99999);
    }

    private String getInfoKeyName() {
        List<String> keyNames = new ArrayList<String>(this.infos.keySet());
        return keyNames.get((int) (Math.random() * keyNames.size()));
    }

    private String getInfoDescription(String infoKeyName) {
        List<String> descriptions = this.infos.get(infoKeyName);
        return descriptions.get((int) (Math.random() * descriptions.size()));
    }
}
