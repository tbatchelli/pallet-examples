(defproject blank-project "0.2.0-SNAPSHOT"
  :description "blank-project for pallet"
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [org.cloudhoist/pallet "0.3.0-SNAPSHOT"]
                 [org.jclouds/jclouds-all "1.0-SNAPSHOT"]
                 [org.jclouds/jclouds-jsch "1.0-SNAPSHOT"]
                 [org.jclouds/jclouds-log4j "1.0-SNAPSHOT"]
                 [log4j/log4j "1.2.14"]]

  :dev-dependencies [;;[swank-clojure/swank-clojure "1.2.1"]
                     [org.cloudhoist/pallet-lein "0.1.0"]
                     [cake-pallet "0.1.0"]]
  :tasks [cake-pallet.tasks]
  :repositories {"sonatype-snapshot" "https://oss.sonatype.org/content/repositories/snapshots/"
                 "sonatype-release" "https://oss.sonatype.org/content/repositories/releases/"})
