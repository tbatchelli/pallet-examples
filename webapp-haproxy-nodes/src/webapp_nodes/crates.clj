(ns webapp-nodes.crates
  "Crates for configuring and deploying to a web application server"
  (:require
   [pallet.resource :as resource]
   [pallet.crate.automated-admin-user :as automated-admin-user]
   [pallet.crate.haproxy :as haproxy]
   [pallet.crate.java :as java]
   [pallet.crate.tomcat :as tomcat]
   [pallet.crate.mysql :as mysql]))

(defn bootstrap
  "Common Bootstrap"
  [request]
  (automated-admin-user/automated-admin-user request))

(defn tomcat
  "Tomcat server configuration"
  [request]
  (-> request
      (java/java :openjdk)
      (tomcat/tomcat)))

(defn tomcat-deploy
  "Tomcat deploy as ROOT application"
  [request path]
  (-> request
      (tomcat/deploy "ROOT" :local-file path :clear-existing true)))

(defn tomcat-deploy-from-blobstore
  [request container path]
  (-> request
      (tomcat/deploy "ROOT" :blob {:container container :path path} :clear-existing true)))

(defn haproxy
  "haproxy server with app1 on port 80."
  [request]
  (-> request
      (haproxy/install-package)
      (haproxy/configure
       :listen {:app1 {:server-address "0.0.0.0:80"
                       :balance "roundrobin"}})))

(defn reverse-proxy
  "Declare node as being reverse proxied by haproxy on tag."
  [request tag app port]
  (-> request
      (haproxy/proxied-by
       tag app :server-port port :check true :weight 1 :maxconn 50)))

(defn mysql
  "MySQL Server configuration"
  [request password]
  (-> request
      (mysql/mysql-server password)))

(defn create-db
  "Creates a database in mysql"
  [request db-name]
  (-> request
      (mysql/create-database db-name)))
