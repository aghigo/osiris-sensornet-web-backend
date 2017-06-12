package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.StatusCode;
import br.uff.labtempo.osiris.configuration.*;
import br.uff.labtempo.osiris.factory.connection.*;
import br.uff.labtempo.osiris.model.health.HealthDependency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class with business rules to Check application dependency health status
 * (SensorNet, VirtualSensorNet, RabbitMQ, Functions (average, min, max, sum), Application database)
 * @see HealthDependency
 * @author andre.ghigo
 * @version 1.0
 * @since 1.8
 */
@Service
public class HealthService {

    @Autowired
    private SensorNetModuleConfig sensorNetModuleConfig;

    @Autowired
    private VirtualSensorNetModuleConfig virtualSensorNetModuleConfig;

    @Autowired
    private FunctionModuleConfig functionModuleConfig;

    @Autowired
    private ApplicationDatabaseConfig applicationDatabaseConfig;

    private SensorNetConnectionFactory sensorNetConnectionFactory;
    private VirtualSensorNetConnectionFactory virtualSensorNetConnectionFactory;
    private FunctionConnectionFactory functionConnectionFactory;
    private CommunicationLayerConnectionFactory communicationLayerConnectionFactory;
    private ApplicationDatabaseConnectionFactory applicationDatabaseConnectionFactory;

    @Autowired
    public HealthService(ApplicationDatabaseConnectionFactory applicationDatabaseConnectionFactory, CommunicationLayerConnectionFactory communicationLayerConnectionFactory, SensorNetConnectionFactory sensorNetConnectionFactory, VirtualSensorNetConnectionFactory virtualSensorNetConnectionFactory, FunctionConnectionFactory functionConnectionFactory) {
        this.applicationDatabaseConnectionFactory = applicationDatabaseConnectionFactory;
        this.communicationLayerConnectionFactory = communicationLayerConnectionFactory;
        this.functionConnectionFactory = functionConnectionFactory;
        this.sensorNetConnectionFactory = sensorNetConnectionFactory;
        this.virtualSensorNetConnectionFactory = virtualSensorNetConnectionFactory;
    }

    /**
     * Test connection to RabbitMQ Queue
     * @see HealthDependency
     * @return HealthDependency with RabbitMQ status
     */
    public HealthDependency testRabbitMQ() {
        String moduleName = this.communicationLayerConnectionFactory.getModuleName();
        String uri = this.communicationLayerConnectionFactory.getModuleUri();
        String ip = this.communicationLayerConnectionFactory.getIp();
        int port = this.communicationLayerConnectionFactory.getPort();
        boolean isActive = false;
        try {
            OmcpClient omcpClient = this.communicationLayerConnectionFactory.getConnection();
            Response response = omcpClient.doGet(this.sensorNetModuleConfig.getModuleUri());
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
     * @see SensorNetConnectionFactory
     * @see SensorNetModuleConfig
     * @return HealthDependency with SensorNet status
     */
    public HealthDependency testSensorNetConnection() {
        String moduleName = this.sensorNetModuleConfig.getModuleName();
        String ip = this.sensorNetConnectionFactory.getIp();
        int port = this.sensorNetConnectionFactory.getPort();
        String moduleLocation = this.sensorNetModuleConfig.getModuleUri();
        boolean isActive = false;
        try {
            OmcpClient omcpClient = this.sensorNetConnectionFactory.getConnection();
            String uri = this.sensorNetModuleConfig.getNetworksUri();
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
     * @see VirtualSensorNetConnectionFactory
     * @see VirtualSensorNetModuleConfig
     * @see HealthDependency
     * @return HealthDependency with VirtualSensorNet status
     */
    public HealthDependency testVirtualSensorNet() {
        String moduleName = this.virtualSensorNetModuleConfig.getModuleName();
        String ip = this.virtualSensorNetConnectionFactory.getIp();
        int port = this.virtualSensorNetConnectionFactory.getPort();
        String moduleLocation = this.virtualSensorNetModuleConfig.getModuleUri();
        boolean isActive = false;
        try {
            OmcpClient omcpClient = this.virtualSensorNetConnectionFactory.getConnection();
            String uri = this.virtualSensorNetModuleConfig.getDataTypesUri();
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
     * Test connection to the Application main relational database
     * @see ApplicationDatabaseConnectionFactory
     * @see ApplicationDatabaseConfig
     * @see br.uff.labtempo.osiris.security.WebSecurityConfig
     * @return HealthDependency with the dependency connection status
     */
    public HealthDependency testApplicationDatabase() {
        HealthDependency healthDependency = HealthDependency.builder()
                .name(this.applicationDatabaseConfig.getDriverClassName())
                .uri(this.applicationDatabaseConfig.getDataSourceUrl()).build();
        try {
            this.applicationDatabaseConnectionFactory.getJdbcTemplate().getDataSource().getConnection();
            healthDependency.setStatus("OK");
            healthDependency.setActive(true);
        } catch (Exception e) {
            healthDependency.setStatus("ERROR");
            healthDependency.setActive(false);
        }
        return healthDependency;
    }
}
