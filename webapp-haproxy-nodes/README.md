# webapp-haproxy-nodes

This example is a project that contains
[pallet](http://github.com/hugoduncan/pallet) node configurations and phases to
deploy web applications to the cloud proxied by HAProxy.  The project can be used as starting point for your own multi-node projects.

## Testing

### Setting up your environment

Copy the file [settings.xml](http://github.com/hugoduncan/pallet-examples/tree/master/webapp-haproxy-nodes/settings.xml) into your ~/.m2 directory. If you already have an existing settings.xml file in that directory, merge the contents into the existing ones. 

Add your cloud provider name and credentials in the profile, both for compute and optionally for blobstore. In the latter case, also provide the name of the bucket to use later on to deploy your applications from your blobstore.

### Building the demo webapps

We will use two different web applications for this example: nano-webapp and mini-webapp.

First we build the war file in the [mini-webapp](http://github.com/hugoduncan/pallet-examples/tree/master/mini-webapp/) project directory.

    bash$ lein deps
    bash$ lein compile
    bash$ lein uberwar

Then we build the war file in the [nano-webapp](http://github.com/hugoduncan/pallet-examples/tree/master/nano-webapp) project directory.

    bash$ mvn clean package

### Testing from the command line

To test the configuration, from the webapp-nodes directory, we start a webapp single instance of nano-webapp
node, and deploy our application.

    bash$ lein deps
    bash$ lein pallet converge webapp-nodes.nodes/proxied 1 :deploy-nano-webapp :restart-tomcat 

Using the public IP address of your new node, check that the newly deployed application is running by visiting http://<node's public IP>:8080

### Testing from the REPL

Alternatively, we can start a REPL from the webapp-nodes directory,
start a webapp node, and deploy our application.

    bash$ lein deps
    bash$ lein repl
    user> (use 'pallet.maven)
    user> (use 'org.jclouds.compute)
    user> (use 'pallet.compute)
    user> (require 'webapp-nodes.nodes)
    user> (def service (compute-service-from-settings))
    user> (pallet.core/converge {webapp-nodes.nodes/proxied 1} :compute service :phase [:deploy-nano-webapp :restart-tomcat])

### Redeploying your web application

Further deploys can be run with the `lift` function.

    bash$ lein pallet lift webapp-nodes.nodes/proxied :deploy-nano-webapp

or

    user> (pallet.core/lift webapp-nodes.nodes/proxied service :deploy-nano-webapp)

NOTE: The above methods should also work with mini-webapp by using the phase :deploy-mini-webapp.

### Deploying from blobstore

Since mini-webapp is a relatively large application, if one needs to instantiate many 'proxied' nodes, it will require uploading this .war file many times. You can optionally deploy a webapp from a blobstore. For that, you need to create a bucket in your blobstore and upload the file  

## License

Copyright (C) 2010 Hugo Duncan

Distributed under the Eclipse Public License, the same as Clojure.
