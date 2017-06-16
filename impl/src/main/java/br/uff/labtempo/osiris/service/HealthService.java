package br.uff.labtempo.osiris.service;

import br.uff.labtempo.omcp.client.OmcpClient;
import br.uff.labtempo.omcp.common.Response;
import br.uff.labtempo.omcp.common.StatusCode;
import br.uff.labtempo.osiris.configuration.*;
import br.uff.labtempo.osiris.factory.connection.*;
import br.uff.labtempo.osiris.model.health.HealthDependency;
import br.uff.labtempo.osiris.security.WebSecurityConfig;
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
    private ApplicationDatabaseConfig applicationDatabaseConfig;

    @Autowired
    private ApplicationDatabaseConnectionFactory applicationDatabaseConnectionFactory;

    @Autowired
    private SensorNetModuleConfig sensorNetModuleConfig;

    @Autowired
    private VirtualSensorNetModuleConfig virtualSensorNetModuleConfig;

    @Autowired
    private FunctionModuleConfig functionModuleConfig;

    @Autowired
    private OsirisConnectionFactory osirisConnectionFactory;

    /**
     * Test connection to RabbitMQ Queue
     * @see HealthDependency
     * @return HealthDependency with RabbitMQ status
     */
    public HealthDependency testRabbitMQ() {
        String moduleName = this.osirisConnectionFactory.getModuleName();
        String uri = this.osirisConnectionFactory.getModuleUri();
        String ip = this.osirisConnectionFactory.getIp();
        int port = this.osirisConnectionFactory.getPort();
        boolean isActive = false;
        try {
            OmcpClient omcpClient = this.osirisConnectionFactory.getConnection();
            Response response = omcpClient.doGet(this.sensorNetModuleConfig.getModuleUri());
            this.osirisConnectionFactory.closeConnection(omcpClient);
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
     * @see SensorNetModuleConfig
     * @return HealthDependency with SensorNet status
     */
    public HealthDependency testSensorNetConnection() {
        String moduleName = this.sensorNetModuleConfig.getModuleName();
        String ip = this.osirisConnectionFactory.getIp();
        int port = this.osirisConnectionFactory.getPort();
        String moduleLocation = this.sensorNetModuleConfig.getModuleUri();
        boolean isActive = false;
        try {
            OmcpClient omcpClient = this.osirisConnectionFactory.getConnection();
            String uri = this.sensorNetModuleConfig.getNetworksUri();
            Response response = omcpClient.doGet(uri);
            this.osirisConnectionFactory.closeConnection(omcpClient);
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
     * @see VirtualSensorNetModuleConfig
     * @see HealthDependency
     * @return HealthDependency with VirtualSensorNet status
     */
    public HealthDependency testVirtualSensorNet() {
        String moduleName = this.virtualSensorNetModuleConfig.getModuleName();
        String ip = this.osirisConnectionFactory.getIp();
        int port = this.osirisConnectionFactory.getPort();
        String moduleLocation = this.virtualSensorNetModuleConfig.getModuleUri();
        boolean isActive = false;
        try {
            OmcpClient omcpClient = this.osirisConnectionFactory.getConnection();
            String uri = this.virtualSensorNetModuleConfig.getDataTypesUri();
            Response response = omcpClient.doGet(uri);
            this.osirisConnectionFactory.closeConnection(omcpClient);
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
     * @see WebSecurityConfig
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
