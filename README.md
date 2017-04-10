# osiris-web-backend

* Backend application for the [OSIRIS Framework](https://github.com/labtempo/osiris/wiki) web interface.
* Contains REST APIs for [SensorNet](https://github.com/labtempo/osiris/wiki/2.1-M%C3%B3dulo-SensorNet) and [VirtualSensorNet](https://github.com/labtempo/osiris/wiki/2.2-M%C3%B3dulo-VirtualSensorNet) modules management.

# Requirements

* Configure the OSIRIS Framework into your local machine. ([Instructions here](https://github.com/aghigo/osiris-binaries/blob/master/README.md#local-development-environment-setup-ubuntu-1604-64-bit))
* Get the OSIRIS API libraries ([Instructions here](https://github.com/aghigo/osiris-binaries/blob/master/README.md#using-the-osiris-api-as-maven-dependency))
* Import this maven project into your favorite Java IDE (e.g. Eclipse, IntelliJ), build and run it.

# Manual

* To see and use the endpoints, import the .json file [osiris-web-backend.postman_collection](https://github.com/aghigo/osiris-web-backend/blob/master/spec/osiris-web-backend.postman_collection.json) into your [Postman](https://www.getpostman.com/).
* Code documentation at [/spec/javadoc](https://github.com/aghigo/osiris-web-backend/tree/master/spec/javadoc) folder.

# About

This is part of a Undergraduate Thesis for the [Bachelor of Computer Information Systems](http://www.ic.uff.br/index.php/en-GB/undergraduate-programs/information-systems) course at [Universidade Federal Fluminense](www.uff.br/)(UFF).
* The main goal consists in create a user-friendly Web Interface to manage the [OSIRIS Framework](https://github.com/labtempo/osiris/wiki) modules [SensorNet](https://github.com/labtempo/osiris/wiki/2.1-M%C3%B3dulo-SensorNet) and [VirtualSensorNet](https://github.com/labtempo/osiris/wiki/2.2-M%C3%B3dulo-VirtualSensorNet).
* The OSIRIS is a framework, created at UFF's [Labtempo](https://github.com/labtempo/), that allows the implementation of sensor-based network monitoring systems. For example: suppose one have a datacenter and wants to put sensors to monitor its temperature, luminosity or any measure to manage and keep track of that environment. So OSIRIS can solve that. OSIRIS was built to monitor any kind of environment.

# Authors

* [Andre Ghigo](https://github.com/aghigo)
* [Felippe Mauricio](https://github.com/felippemauricio)

# Credits

* [Raphael Guerra](http://www2.ic.uff.br/~rguerra/), professor.
* [Felipe Ralph](https://github.com/println), creator of the [OSIRIS Framework](https://github.com/labtempo/osiris/wiki).
