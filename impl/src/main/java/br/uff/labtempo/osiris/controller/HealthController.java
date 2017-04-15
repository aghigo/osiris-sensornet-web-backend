package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.StatusCode;
import br.uff.labtempo.osiris.configuration.FunctionConfig;
import br.uff.labtempo.osiris.configuration.SensorNetConfig;
import br.uff.labtempo.osiris.configuration.VirtualSensorNetConfig;
import br.uff.labtempo.osiris.connection.FunctionConnection;
import br.uff.labtempo.osiris.connection.RabbitMQConnection;
import br.uff.labtempo.osiris.connection.SensorNetConnection;
import br.uff.labtempo.osiris.connection.VirtualSensorNetConnection;
import br.uff.labtempo.osiris.model.HealthDependency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/health", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class HealthController {

    @Autowired
    private SensorNetConfig sensorNetConfig;

    @Autowired
    private VirtualSensorNetConfig virtualSensorNetConfig;

    @Autowired
    private FunctionConfig functionConfig;

    private SensorNetConnection sensorNetConnection;
    private VirtualSensorNetConnection virtualSensorNetConnection;
    private FunctionConnection functionConnection;
    private RabbitMQConnection rabbitMQConnection;

    @Autowired
    public HealthController(RabbitMQConnection rabbitMQConnection, SensorNetConnection sensorNetConnection, VirtualSensorNetConnection virtualSensorNetConnection, FunctionConnection functionConnection) {
        this.rabbitMQConnection = rabbitMQConnection;
        this.functionConnection = functionConnection;
        this.sensorNetConnection = sensorNetConnection;
        this.virtualSensorNetConnection = virtualSensorNetConnection;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getHealthStatus() {
        List<HealthDependency> healthDependencyList = new ArrayList<>();
        healthDependencyList.add(testRabbitMQ());
        healthDependencyList.add(testSensorNetConnection());
        healthDependencyList.add(testVirtualSensorNet());
        healthDependencyList.addAll(testFunction());
        return ResponseEntity.ok(healthDependencyList);
    }

    private HealthDependency testRabbitMQ() {
        String moduleName = "RabbitMQ";
        String ip = this.rabbitMQConnection.getIp();
        int port = this.rabbitMQConnection.getPort();
        try {
            OmcpClient omcpClient = this.rabbitMQConnection.getConnection();
            Response response = omcpClient.doGet(this.sensorNetConfig.getModuleUri());
            return HealthDependency.builder().uri("N/A").status(response.getStatusCode().toString()).port(port).ip(ip).name(moduleName).build();
        } catch (Exception e) {
            return HealthDependency.builder().uri("N/A").status(e.getMessage()).port(port).ip(ip).name(moduleName).build();
        }
    }

    private HealthDependency testSensorNetConnection() {
        String moduleName = this.sensorNetConfig.getModuleName();
        String ip = this.sensorNetConnection.getIp();
        int port = this.sensorNetConnection.getPort();
        String moduleLocation = this.sensorNetConfig.getModuleUri();
        try {
            OmcpClient omcpClient = this.sensorNetConnection.getConnection();
            String uri = this.sensorNetConfig.getNetworksUri();
            Response response = omcpClient.doGet(uri);
            return HealthDependency.builder().name(moduleName).ip(ip).port(port).status(response.getStatusCode().toString()).uri(moduleLocation).build();
        } catch (Exception e) {
            return HealthDependency.builder().name(moduleName).ip(ip).port(port).status(e.getMessage()).uri(moduleLocation).build();
        }
    }

    private HealthDependency testVirtualSensorNet() {
        String moduleName = this.virtualSensorNetConfig.getModuleName();
        String ip = this.virtualSensorNetConfig.getIp();
        int port = this.virtualSensorNetConfig.getPort();
        String moduleLocation = this.virtualSensorNetConfig.getModuleUri();
        try {
            OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
            String uri = this.virtualSensorNetConfig.getVirtualSensorUri();
            Response response = omcpClient.doGet(uri);
            return HealthDependency.builder().name(moduleName).ip(ip).port(port).status(response.getStatusCode().toString()).uri(moduleLocation).build();
        } catch (Exception e) {
            return HealthDependency.builder().name(moduleName).ip(ip).port(port).status(e.getMessage()).uri(moduleLocation).build();
        }
    }

    private List<HealthDependency> testFunction() {
        List<HealthDependency> healthDependencyList = new ArrayList<>();
        List<String> functionNameList = this.functionConfig.getFunctionNames();
        String ip = this.functionConnection.getIp();
        int port = this.functionConnection.getPort();
        for(String functionName : functionNameList) {
            String moduleName = functionName + " " + this.functionConfig.getModuleName();
            String testUri = String.format(this.functionConfig.getFunctionInterfaceUri(), functionName);
            String moduleUri = String.format(this.functionConfig.getFunctionUri(), functionName);
            try {
                OmcpClient omcpClient = this.functionConnection.getConnection();
                Response response = omcpClient.doGet(testUri);
                HealthDependency healthDependency = HealthDependency.builder().name(moduleName).ip(ip).port(port).status(response.getStatusCode().toString()).uri(moduleUri).build();
                healthDependencyList.add(healthDependency);
            } catch(Exception e) {
                HealthDependency healthDependency = HealthDependency.builder().name(moduleName).ip(ip).port(port).status(e.getMessage()).uri(moduleUri).build();
                healthDependencyList.add(healthDependency);
            }
        }
        return healthDependencyList;
    }
}
