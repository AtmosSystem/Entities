(ns atmos-entities.api
  (:require [compojure.core :refer :all]
            [atmos-kernel.core :refer [not-found-route
                                       not-implemented-fn
                                       ms-atmos-method
                                       make-json-app
                                       ms-atmos-response
                                       ms-atmos-main-method-response]]
            [atmos-entities.core :refer :all]))



;-------------------------------------------------------
; BEGIN VARS
;-------------------------------------------------------

(def db {:aws       {:host     "transportation-dev-db.c4r6yc5ou9f3.us-east-1.rds.amazonaws.com"
                     :db       "atmos-entities"
                     :user     "developer"
                     :password "12345678"}

         :localhost {:host     "localhost"
                     :db       "atmos-entities"
                     :user     "root"
                     :password ""}})

(-> db :aws defpersistence init-persistence)

;-------------------------------------------------------
; END VARS
;-------------------------------------------------------

(defn- get-entities*
  [data]
  (ms-atmos-response
    (cond
      (nil? data) (get-all-entities)
      (contains?)
      (get-entity data))))


(defroutes app-routes
           (ms-atmos-main-method-response :Entity)

           (GET
             (ms-atmos-method :entities)
             []
             (get-entities* nil))

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


