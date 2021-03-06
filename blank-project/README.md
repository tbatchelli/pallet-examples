# blank-project

This example is a blank project that contains
[pallet](http://github.com/hugoduncan/pallet).  The project can be used as
starting point for your own projects.

## Creating

### lein

The first thing we need is [leiningen](http://github.com/technomancy/leiningen),
a build tool for clojure.

    bash$ curl -O http://github.com/technomancy/leiningen/raw/stable/bin/lein
    bash$ chmod +x lein

Now we can create the project:

    bash$ lein new blank-project
    Created new project in: blank-project
    bash$ cd blank-project

Leiningen creates a `project.clj` file, and we need to add pallet and jclouds to the :dependencies, and pallet-lein to the :dev-dependencies, so it looks like:

    (defproject blank-project "0.2.0-SNAPSHOT"
      :description "blank-project for pallet"
      :dependencies [[org.clojure/clojure "1.2.0"]
                     [org.clojure/clojure-contrib "1.2.0"]
                     [org.cloudhoist/pallet "0.3.0-SNAPSHOT"]
                     [org.jclouds/jclouds-all "1.0-SNAPSHOT"]
                     [org.jclouds/jclouds-jsch "1.0-SNAPSHOT"]
                     [org.jclouds/jclouds-log4j "1.0-SNAPSHOT"]
                     [org.jclouds/jclouds-enterprise "1.0-SNAPSHOT"]
                     [log4j/log4j "1.2.14"]]
      :dev-dependencies [[org.cloudhoist/pallet-lein "0.1.0"]]
      :repositories {"sonatype-snapshot" "https://oss.sonatype.org/content/repositories/snapshots/"
                     "sonatype" "https://oss.sonatype.org/content/repositories/releases"})

Note that jclouds-all is rather heavy.  You can use the list of supported clouds
and individual jclouds provider jars to slim the dependency down.

    lein pallet providers

### cake

You can equivalently use cake. You will need `[cake-pallet "0.1.0"]` in your
`:dev-dependencies` instead of the lein plugin and you should add
`:tasks [cake-pallet.tasks]`, both in project.clj.

## Credentials

The last configuration step is to specify your cloud credentials.

### ~/.m2/settings.xml

You can edit `~/.m2/settings.xml` to include your cloud credentials. The
[setttings.xml](http://github.com/hugoduncan/pallet-examples/blob/master/blank-project/settings.xml)
file in this projects provides an example of the format.  If you do not have
this file, you can create it from the example.

    <settings>
      <profiles>
        <profile>
          <id>cloud-credentials</id>
          <activation>
            <activeByDefault>true</activeByDefault>
          </activation>
          <properties>
            <pallet.compute.provider>ec2</pallet.compute.provider>
            <pallet.compute.identity>api-key</pallet.compute.identity>
            <pallet.compute.credential>api-secret</pallet.compute.credential>
            <pallet.blobstore.provider>s3</pallet.blobstore.provider>
            <pallet.blobstore.identity>api-key</pallet.blobstore.identity>
            <pallet.blobstore.credential>api-secret</pallet.blobstore.credential>
          </properties>
        </profile>
      </profiles>
    </settings>

You can select which profile is used by passing a `-P` argument, e.g.,
`lein pallet -P cloud-credentials nodes`.

### ~/.pallet/config.clj

You can edit `~/.pallet/config.clj` to include your cloud credentials.

    (defpallet
      :providers {:aws {:provider "ec2"
                        :identity "your-key"
                        :credential "your-secret"
                        :blobstore {:provider "s3"}}})

You can select which credentials are used by passing a `-P` argument, e.g.,
`lein pallet -P aws nodes`.

## Testing

To test the configuration, we can use the pallet-lein plugin, to list the nodes
in your cloud account.

    bash$ lein deps
    bash$ lein pallet nodes

Alternatively we can start a REPL, to do the same.

    bash$ lein deps
    bash$ lein repl
    user> (use 'pallet.maven)
    user> (use 'pallet.compute)
    user> (def service (compute-service-from-settings))
    user> (nodes service)

Both of these should show any instances that you have running in your cloud account.

## Options

### Logging

The project contains a `resources/log4j.properties` file that configures log4j
logging into the log subdirectory.  Edit this to control the placement and
detail level of the logs.

### Swank
If you use [SLIME](http://common-lisp.net/project/slime), you can add swank-clojure to the `project.clj`.

  :dev-dependencies [[swank-clojure/swank-clojure "1.2.1"]]

### jclouds provider specific jars
When we added jclouds, we specified `jclouds-all` as the dependency.  Jclouds
also has fine grained jars for each individual cloud provider that can be used instead to reduce the size of the jclouds dependency.




## License

Copyright (C) 2010 Hugo Duncan

Distributed under the Eclipse Public License, the same as Clojure.
