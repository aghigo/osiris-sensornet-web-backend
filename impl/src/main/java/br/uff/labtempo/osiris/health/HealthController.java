package br.uff.labtempo.osiris.health;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.osiris.connection.SensorNetConnection;
import br.uff.labtempo.osiris.connection.VirtualSensorNetConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/health", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class HealthController {

    private SensorNetConnection sensorNetConnection;
    private VirtualSensorNetConnection virtualSensorNetConnection;

    @Autowired
    public HealthController(SensorNetConnection sensorNetConnection, VirtualSensorNetConnection virtualSensorNetConnection) {
        this.sensorNetConnection = sensorNetConnection;
        this.virtualSensorNetConnection = virtualSensorNetConnection;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getHealthStatus() {
        boolean sensorNet, virtualSensorNet;
        try {
            OmcpClient omcpClient = sensorNetConnection.getConnection();
            omcpClient.doGet("omcp://sensornet.osiris/");
            sensorNet = true;
        } catch (Exception e) {
            sensorNet = false;
        }
        try {
            OmcpClient omcpClient = virtualSensorNetConnection.getConnection();
            omcpClient.doGet("omcp://virtualsensornet.osiris/");
            virtualSensorNet = true;
        } catch (Exception e) {
            virtualSensorNet = false;
        }
        StringBuilder sb = new StringBuilder();
        if(sensorNet) {
            sb.append("{ \"SensorNet\" : true,").append("\n");
        } else {
            sb.append("{ \"SensorNet\" : false,").append("\n");
        }
        if(virtualSensorNet) {
            sb.append("\"VirtualSensorNet\" : true }").append("\n");
        } else {
            sb.append("\"VirtualSensorNet\" : false }").append("\n");
        }
        return ResponseEntity.ok(sb.toString());
    }
}
