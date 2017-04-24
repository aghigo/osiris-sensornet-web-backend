package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.StatusCode;
import br.uff.labtempo.osiris.configuration.*;
import br.uff.labtempo.osiris.connection.*;
import br.uff.labtempo.osiris.model.HealthDependency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class with business rules to Check application dependency health status
 * (SensorNet, VirtualSensorNet, RabbitMQ, Functions (average, min, max, sum), Application database)
 * @see br.uff.labtempo.osiris.model.HealthDependency
 * @author andre.ghigo
 * @version 1.0
 * @since 1.8
 */
@Service
public class HealthService {

    @Autowired
    private SensorNetConfig sensorNetConfig;

    @Autowired
    private VirtualSensorNetConfig virtualSensorNetConfig;

    @Autowired
    private FunctionConfig functionConfig;

    @Autowired
    private MessageGroupConfig messageGroupConfig;

    private SensorNetConnection sensorNetConnection;
    private VirtualSensorNetConnection virtualSensorNetConnection;
    private FunctionConnection functionConnection;
    private RabbitMQConnection rabbitMQConnection;
    private MessageGroupConnection messageGroupConnection;

    @Autowired
    public HealthService(MessageGroupConnection messageGroupConnection, RabbitMQConnection rabbitMQConnection, SensorNetConnection sensorNetConnection, VirtualSensorNetConnection virtualSensorNetConnection, FunctionConnection functionConnection) {
        this.messageGroupConnection = messageGroupConnection;
        this.rabbitMQConnection = rabbitMQConnection;
        this.functionConnection = functionConnection;
        this.sensorNetConnection = sensorNetConnection;
        this.virtualSensorNetConnection = virtualSensorNetConnection;
    }

    /**
     * Test connection to RabbitMQ Queue
     * @see RabbitMQConnection
     * @see HealthDependency
     * @return HealthDependency with RabbitMQ status
     */
    public HealthDependency testRabbitMQ() {
        String moduleName = this.rabbitMQConnection.getModuleName();
        String uri = this.rabbitMQConnection.getModuleUri();
        String ip = this.rabbitMQConnection.getIp();
        int port = this.rabbitMQConnection.getPort();
        boolean isActive = false;
        try {
            OmcpClient omcpClient = this.rabbitMQConnection.getConnection();
            Response response = omcpClient.doGet(this.sensorNetConfig.getModuleUri());
            if(response.getStatusCode().equals(StatusCode.OK)) {
                isActive = true;
            }
            return HealthDependency.builder().uri(uri).status(response.getStatusCode().toString()).port(port).ip(ip).name(moduleName).active(isActive).build();
        } catch (Exception e) {
            return HealthDependency.builder().uri(uri).status(e.getMessage()).port(port).ip(ip).name(moduleName).active(isActive).build();
        }
    }

    /**
     * Test connection to SensorNet module
     * @see HealthDependency
     * @see SensorNetConnection
     * @see SensorNetConfig
     * @return HealthDependency with SensorNet status
     */
    public HealthDependency testSensorNetConnection() {
        String moduleName = this.sensorNetConfig.getModuleName();
        String ip = this.sensorNetConnection.getIp();
        int port = this.sensorNetConnection.getPort();
        String moduleLocation = this.sensorNetConfig.getModuleUri();
        boolean isActive = false;
        try {
            OmcpClient omcpClient = this.sensorNetConnection.getConnection();
            String uri = this.sensorNetConfig.getNetworksUri();
            Response response = omcpClient.doGet(uri);
            if(response.getStatusCode().equals(StatusCode.OK)) {
                isActive = true;
            }
            return HealthDependency.builder().name(moduleName).ip(ip).port(port).status(response.getStatusCode().toString()).uri(moduleLocation).active(isActive).build();
        } catch (Exception e) {
            return HealthDependency.builder().name(moduleName).ip(ip).port(port).status(e.getMessage()).uri(moduleLocation).active(isActive).build();
        }
    }

    /**
     * Test connection to VirtualSensorNet module
     * @see VirtualSensorNetConnection
     * @see VirtualSensorNetConfig
     * @see HealthDependency
     * @return HealthDependency with VirtualSensorNet status
     */
    public HealthDependency testVirtualSensorNet() {
        String moduleName = this.virtualSensorNetConfig.getModuleName();
        String ip = this.virtualSensorNetConnection.getIp();
        int port = this.virtualSensorNetConnection.getPort();
        String moduleLocation = this.virtualSensorNetConfig.getModuleUri();
        boolean isActive = false;
        try {
            OmcpClient omcpClient = this.virtualSensorNetConnection.getConnection();
            String uri = this.virtualSensorNetConfig.getDataTypesUri();
            Response response = omcpClient.doGet(uri);
            if(response.getStatusCode().equals(StatusCode.OK)) {
                isActive = true;
            }
            return HealthDependency.builder().name(moduleName).ip(ip).port(port).status(response.getStatusCode().toString()).uri(moduleLocation).active(isActive).build();
        } catch (Exception e) {
            return HealthDependency.builder().name(moduleName).ip(ip).port(port).status(e.getMessage()).uri(moduleLocation).active(isActive).build();
        }
    }

    /**
     * Test connection to all available Function modules
     * @see FunctionModuleConfig
     * @see FunctionConfig
     * @see FunctionConnection
     * @return List of HealthDependency with all Function modules status
     */
    public List<HealthDependency> testFunctionModules() {
        List<HealthDependency> healthDependencyList = new ArrayList<>();
        List<FunctionModuleConfig> functionModuleConfigList = this.functionConfig.getFunctionModuleConfigList();
        for(FunctionModuleConfig functionModuleConfig : functionModuleConfigList) { 
            boolean isActive = false;
            try {
                OmcpClient omcpClient = this.functionConnection.getConnection(functionModuleConfig);
                Response response = omcpClient.doGet(functionModuleConfig.getInterfaceUri());
                if(response.getStatusCode().equals(StatusCode.OK)) {
                    isActive = true;
                }
                HealthDependency healthDependency = HealthDependency.builder()
                        .ip(functionModuleConfig.getIp())
                        .name(functionModuleConfig.getModuleName())
                        .port(functionModuleConfig.getPort())
                        .status(response.getStatusCode().toString())
                        .uri(functionModuleConfig.getModuleUri())
                        .active(isActive)
                        .build();
                healthDependencyList.add(healthDependency);
            } catch (Exception e) {
                HealthDependency healthDependency = HealthDependency.builder()
                        .ip(functionModuleConfig.getIp())
                        .name(functionModuleConfig.getModuleName())
                        .port(functionModuleConfig.getPort())
                        .status(e.getMessage())
                        .uri(functionModuleConfig.getModuleUri())
                        .active(isActive)
                        .build();
                healthDependencyList.add(healthDependency);
            }
        }
        return healthDependencyList;
    }

    /**
     * Test connection to all available messageGroups
     * @see MessageGroupConfig
     * @see OsirisMessageGroupConfig
     * @return List of HealthDependency with all messageGroups status
     */
    public List<HealthDependency> testMessageGroups() {
        List<HealthDependency> healthDependencyList = new ArrayList<>();
        for(OsirisMessageGroupConfig osirisMessageGroupConfig : this.messageGroupConfig.getOsirisMessageGroupConfigList()) {
            String messageGroupName = osirisMessageGroupConfig.getName();
            String uri = osirisMessageGroupConfig.getUri();
            String ip = osirisMessageGroupConfig.getIp();
            int port = osirisMessageGroupConfig.getPort();
            String username = osirisMessageGroupConfig.getUsername();
            String password = osirisMessageGroupConfig.getPassword();
            HealthDependency healthDependency = HealthDependency.builder()
                    .ip(ip).name(messageGroupName).port(port).uri(uri).build();
            try {
                OmcpClient omcpClient = this.messageGroupConnection.getConnection(osirisMessageGroupConfig);
                Response response = omcpClient.doGet(uri);
                healthDependency.setActive(true);
                healthDependency.setStatus(response.getStatusCode().toString());
            } catch (Exception e) {
                healthDependency.setActive(false);
                healthDependency.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
            }
            healthDependencyList.add(healthDependency);
        }
        return healthDependencyList;
    }
}
