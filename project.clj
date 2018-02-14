(defproject atmos-entities "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [atmos-kernel "0.1.0-SNAPSHOT"]
                 ;persistence-deps
                 [korma "0.4.0"]
                 [mysql/mysql-connector-java "6.0.6"]]
  :plugins [[lein-ring "0.12.3"]]
  :ring {:handler atmos-entities.api/app}
  :profiles {
             :uberjar {:aot :all}
             :dev     {:dependencies [[javax.servlet/servlet-api "2.5"]
                                      [ring/ring-mock "0.3.0"]]}})
