package br.uff.labtempo.osiris.controller;

import br.uff.labtempo.osiris.configuration.AvailableFunctionListConfig;
import br.uff.labtempo.osiris.configuration.FunctionModuleConfig;
import br.uff.labtempo.osiris.configuration.SensorNetModuleConfig;
import br.uff.labtempo.osiris.configuration.VirtualSensorNetModuleConfig;
import br.uff.labtempo.osiris.connection.FunctionConnection;
import br.uff.labtempo.osiris.connection.SensorNetConnection;
import br.uff.labtempo.osiris.connection.VirtualSensorNetConnection;
import br.uff.labtempo.osiris.model.health.HealthDependency;
import br.uff.labtempo.osiris.service.HealthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class that provide REST endpoints to check application dependencies status.
 * test connections with SensorNet, VirtualSensorNet, FunctionFactory modules, RabbitMQ and Application Database
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@RestController
@CrossOrigin
public class HealthController {

    private HealthService healthService;

    @Autowired
    public HealthController(HealthService healthService) {
        this.healthService = healthService;
    }

    /**
     * Get a health summary of application dependency modules status are functional
     * Checks if SensorNet, VirtualSensorNet, FunctionFactory modules and Application database is Online and Functional
     * Each application dependency is mapped on HealthDependency object
     * @see HealthDependency
     * @return List of HealthDependency
     */
    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public ResponseEntity<?> getGlobalHealthStatus() {
        try {
            List<HealthDependency> healthDependencyList = new ArrayList<>();
            healthDependencyList.add(this.healthService.testRabbitMQ());
            healthDependencyList.add(this.healthService.testSensorNetConnection());
            healthDependencyList.add(this.healthService.testVirtualSensorNet());
            healthDependencyList.addAll(this.healthService.testFunctionModules());
            healthDependencyList.addAll(this.healthService.testMessageGroups());
            healthDependencyList.add(this.healthService.testApplicationDatabase());
            return ResponseEntity.ok(healthDependencyList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Get a health status of the SensorNet module
     * @see SensorNetConnection
     * @see SensorNetModuleConfig
     * @see HealthDependency
     * @return HealthDependency with SensorNet status
     */
    @RequestMapping(value = "/sensornet/health", method = RequestMethod.GET)
    public ResponseEntity<?> getSensorNetHealthStatus() {
        try {
            HealthDependency healthDependency = this.healthService.testSensorNetConnection();
            return ResponseEntity.ok(healthDependency);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Get a health status of the VirtualSensorNet module
     * @see VirtualSensorNetConnection
     * @see VirtualSensorNetModuleConfig
     * @see HealthDependency
     * @return HealthDependency with VirtualSensorNet status
     */
    @RequestMapping(value = "/virtualsensornet/health", method = RequestMethod.GET)
    public ResponseEntity<?> getVirtualSensorNetHealthStatus() {
        try {
            HealthDependency healthDependency = this.healthService.testVirtualSensorNet();
            return ResponseEntity.ok(healthDependency);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Get a health status of the FunctionFactory modules
     * @see FunctionConnection
     * @see AvailableFunctionListConfig
     * @see FunctionModuleConfig
     * @see HealthDependency
     * @return List of HealthDependency with FunctionFactory module status
     */
    @RequestMapping(value = "/function/health", method = RequestMethod.GET)
    public ResponseEntity<?> getFunctionModulesHealthStatus() {
        try {
            List<HealthDependency> healthDependencyList = this.healthService.testFunctionModules();
            return ResponseEntity.ok(healthDependencyList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Get a health status of the MessageGroups
     * @see FunctionConnection
     * @see AvailableFunctionListConfig
     * @see FunctionModuleConfig
     * @see HealthDependency
     * @return List of HealthDependency with MessageGroups status
     */
    @RequestMapping(value = "/messagegroup/health", method = RequestMethod.GET)
    public ResponseEntity<?> getMessageGroupsHealth() {
        try {
            List<HealthDependency> healthDependencyList = this.healthService.testMessageGroups();
            return ResponseEntity.ok(healthDependencyList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * Get application database connection Health
     * @return HealthDependency
     */
    @RequestMapping(value = "/database/health", method = RequestMethod.GET)
    public ResponseEntity<?> getApplicationDatabaseHealth() {
        try {
            HealthDependency healthDependency = this.healthService.testApplicationDatabase();
            return ResponseEntity.ok(healthDependency);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }
}
