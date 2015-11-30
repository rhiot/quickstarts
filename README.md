# Rhiot quickstarts

[![Build Status](https://travis-ci.org/rhiot/quickstarts.svg?branch=master)](https://travis-ci.org/rhiot/quickstarts)

<a href="https://github.com/rhiot/rhiot"><img src="https://github.com/rhiot/rhiot/raw/master/rhiot.png" align="left" height="280" hspace="30"></a>

[Rhiot](http://rhiot.io) quickstarts are base projects that can be copied and used as the building
blocks of your IoT solution. Rhiot quickstarts reference documentation
can be found [here](https://github.com/rhiot/rhiot/blob/master/docs/readme.md#quickstarts).

## Programming model

The programming model we have chosen for Rhiot quickstarts is focused on [Camel](http://camel.apache.org) and
[Vert.x](http://vertx.io). Camel routes are our primary choice for the expression of the endpoint connectivity and
message routing. Vert.x verticles and event bus are also the first class citizen in all the Rhiot applications.

## Quickstarts list

The following quickstarts are available:

* [Kura Camel](https://github.com/rhiot/quickstarts/tree/master/kura-camel) ([documentation](https://rhiot.gitbooks.io/rhiotdocumentation/content/quickstarts/kura_camel_quickstart.html))
* [AMQP cloudlet](https://github.com/rhiot/quickstarts/tree/master/cloudlets/amqp) ([documentation](https://rhiot.gitbooks.io/rhiotdocumentation/content/quickstarts/amqp_cloudlet_quickstarts.html))
* [MQTT cloudlet](https://github.com/rhiot/quickstarts/tree/master/cloudlets/mqtt) ([documentation](https://rhiot.gitbooks.io/rhiotdocumentation/content/quickstarts/mqtt_cloudlet_quickstart.html))