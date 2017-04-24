package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.osiris.configuration.FunctionConfig;
import br.uff.labtempo.osiris.configuration.FunctionModuleConfig;
import br.uff.labtempo.osiris.configuration.SensorNetConfig;
import br.uff.labtempo.osiris.configuration.VirtualSensorNetConfig;
import br.uff.labtempo.osiris.connection.FunctionConnection;
import br.uff.labtempo.osiris.connection.SensorNetConnection;
import br.uff.labtempo.osiris.connection.VirtualSensorNetConnection;
import br.uff.labtempo.osiris.model.HealthDependency;
import br.uff.labtempo.osiris.service.HealthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class that provide REST endpoints to check application dependencies status.
 * test connections with SensorNet, VirtualSensorNet, Function modules, RabbitMQ and Application Database
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@RestController
@CrossOrigin(value = "*")
public class HealthController {

    private HealthService healthService;

    @Autowired
    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    /**
     * Get a health summary of application dependency modules status are functional
     * Checks if SensorNet, VirtualSensorNet, Function modules and Application database is Online and Functional
     * Each application dependency is mapped on HealthDependency object
     * @see HealthDependency
      * @return List of HealthDependency
     */
    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public ResponseEntity<?> getGlobalHealthStatus() {
        List<HealthDependency> healthDependencyList = new ArrayList<>();
        healthDependencyList.add(this.healthService.testRabbitMQ());
        healthDependencyList.add(this.healthService.testSensorNetConnection());
        healthDependencyList.add(this.healthService.testVirtualSensorNet());
        healthDependencyList.addAll(this.healthService.testFunctionModules());
        return ResponseEntity.ok(healthDependencyList);
    }

    /**
     * Get a health status of the SensorNet module
     * @see SensorNetConnection
     * @see SensorNetConfig
     * @see HealthDependency
     * @return HealthDependency with SensorNet status
     */
    @RequestMapping(value = "/sensornet/health", method = RequestMethod.GET)
    public ResponseEntity<?> getSensorNetHealthStatus() {
        return ResponseEntity.ok(this.healthService.testSensorNetConnection());
    }

    /**
     * Get a health status of the VirtualSensorNet module
     * @see VirtualSensorNetConnection
     * @see VirtualSensorNetConfig
     * @see HealthDependency
     * @return HealthDependency with VirtualSensorNet status
     */
    @RequestMapping(value = "/virtualsensornet/health", method = RequestMethod.GET)
    public ResponseEntity<?> getVirtualSensorNetHealthStatus() {
        return ResponseEntity.ok(this.healthService.testVirtualSensorNet());
    }

    /**
     * Get a health status of the Function modules
     * @see FunctionConnection
     * @see FunctionConfig
     * @see FunctionModuleConfig
     * @see HealthDependency
     * @return List of HealthDependency with Function module status
     */
    @RequestMapping(value = "/function/health", method = RequestMethod.GET)
    public ResponseEntity<?> getFunctionModulesHealthStatus() {
        return ResponseEntity.ok(this.healthService.testFunctionModules());
    }

    /**
     * Get a health status of the MessageGroups
     * @see FunctionConnection
     * @see FunctionConfig
     * @see FunctionModuleConfig
     * @see HealthDependency
     * @return List of HealthDependency with MessageGroups status
     */
    @RequestMapping(value = "/messagegroup/health", method = RequestMethod.GET)
    public ResponseEntity<?> getMessageGroupsHealth() {
        return ResponseEntity.ok(this.healthService.testMessageGroups());
    }
}
