(ns atmos-entities.api
  (:require [compojure.core :refer [GET POST PUT DELETE defroutes]]
            [atmos-kernel.core :refer [not-found-route
                                       not-implemented-fn
                                       ms-atmos-method
                                       make-json-app
                                       ms-atmos-response
                                       ms-atmos-main-method-response
                                       request-body
                                       keyword-map
                                       read-resource-edn]]
            [atmos-rdb-kernel.core :refer [defpersistence init-persistence]]
            [atmos-entities.core :refer :all]
            [clojure.string :refer [includes? split]]))



;-------------------------------------------------------
; BEGIN VARS
;-------------------------------------------------------
(def configuration (read-resource-edn :config-prod))

(-> configuration :database defpersistence init-persistence)

;-------------------------------------------------------
; END VARS
;-------------------------------------------------------

(defn- get-entities*
  ([data]
   (ms-atmos-response
     (cond
       (nil? data) (get-all-entities)
       (map? data) (get-entities (:ids data))
       (string? data) (get-entity (Long. data)))))
  ([]
   (get-entities* nil)))

(defn- add-entities*
  [data]
  (let [entities (keyword-map (:entities data))]
    (ms-atmos-response
      (cond
        (map? entities) (str (add-entity entities))))))

(defn- remove-entities*
  [data]
  (ms-atmos-response
    (cond
      (map? data) (remove-entities (:ids data))
      (string? data) (str (remove-entity (Long. data))))))

(defn- update-entities*
  [data]
  (let [entities (keyword-map (:entities data))]
    (ms-atmos-response
      (cond
        (map? entities) (str (update-entity entities))))))

(defroutes app-routes
           (ms-atmos-main-method-response :Entity)

           (GET
             (ms-atmos-method :entities)
             request
             (get-entities*))

           (GET
             (ms-atmos-method :entities :id)
             [id]
             (get-entities* id))

           (POST
             (ms-atmos-method :entities)
             request
             (let [body (request-body request)
                   method (-> body :method keyword)]
               (cond
                 (= method :get) (get-entities* body)
                 (= method :delete) (remove-entities* body)
                 (= method :update) (update-entities* body))))

           (PUT
             (ms-atmos-method :entities)
             request
             (add-entities* (request-body request)))

           (DELETE
             (ms-atmos-method :entities :id)
             [id]
             (remove-entities* id))

           not-found-route)

(def app (make-json-app app-routes))


