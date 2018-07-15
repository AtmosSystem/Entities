(defproject atmos-entities "1.0.0"
  :description "Atmos micro service for manage entities"
  :url "https://github.com/AtmosSystem/Entities"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [atmos-kernel "0.2.0-SNAPSHOT"]
                 [atmos-rdb-kernel "0.2.0-SNAPSHOT"]
                 [environ "1.1.0"]]
  :plugins [[lein-ring "0.12.3"]
            [lein-environ "1.1.0"]]
  :ring {:handler atmos-entities.api/app}
  :profiles {
             :uberjar {:aot :all
                       :env {:resource-file "config-prod"}}
             :dev     {:dependencies [[javax.servlet/servlet-api "2.5"]
                                      [ring/ring-mock "0.3.0"]]
                       :env          {:resource-file "config-dev"}}})
