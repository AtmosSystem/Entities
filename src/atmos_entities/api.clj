(ns atmos-entities.api
  (:require [compojure.core :refer :all]
            [atmos-kernel.core :refer [not-found-route
                                       not-implemented-fn
                                       ms-atmos-method
                                       make-json-app
                                       ms-atmos-response
                                       ms-atmos-main-method-response]]
            [atmos-entities.core :refer :all]
            [atmos-entities.implementation :refer [init-persistence defpersistence]]))



;-------------------------------------------------------
; BEGIN VARS
;-------------------------------------------------------

(def db {:aws       {:host     "transportation-dev-db.c4r6yc5ou9f3.us-east-1.rds.amazonaws.com"
                     :db       "atmos-entities"
                     :user     ""
                     :password ""}

         :localhost {:host     "localhost"
                     :db       "atmos-entities"
                     :user     "root"
                     :password ""}})

(-> db :localhost defpersistence init-persistence)

;-------------------------------------------------------
; END VARS
;-------------------------------------------------------

(defn- get-entities*
  [data]
  (ms-atmos-response (get-entity data)))


(defroutes app-routes
           (ms-atmos-main-method-response :Entity)

           (GET
             (ms-atmos-method :entities)
             []
             not-implemented-fn)

           (GET
             (ms-atmos-method :entities :id)
             [id]
             (get-entities* id))

           (POST
             (ms-atmos-method :entities :id)
             [id]
             not-implemented-fn)

           (PUT
             (ms-atmos-method :entities)
             []
             not-implemented-fn)

           (DELETE
             (ms-atmos-method :entities :id)
             [id]
             not-implemented-fn)

           not-found-route)

(def app (make-json-app app-routes))


