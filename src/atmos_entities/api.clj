(ns atmos-entities.api
  (:require [compojure.core :refer [GET POST PUT DELETE defroutes]]
            [atmos-kernel.core :refer [not-found-route
                                       not-implemented-fn
                                       ms-atmos-method
                                       make-json-app
                                       ms-atmos-response
                                       ms-atmos-cond-response
                                       ms-atmos-let-cond-response
                                       ms-atmos-main-method-response
                                       keyword-map
                                       request-body
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

;------------------------------
; BEGIN Entities functions
;------------------------------

(defn- get-entities*
  ([data]
   (ms-atmos-cond-response
     (nil? data) (get-all-entities)
     (map? data) (get-entities (:ids data))
     (string? data) (get-entity (Long. data))))
  ([]
   (get-entities* nil)))

(defn- add-entities*
  [data]
  (ms-atmos-let-cond-response
    [entities (keyword-map (:entities data))]

    (map? entities) (str (add-entity entities))))


(defn- update-entities*
  [data]
  (ms-atmos-let-cond-response
    [entities (keyword-map (:entities data))]

    (map? entities) (str (update-entity entities))))


(defn- remove-entities*
  [data]
  (ms-atmos-cond-response
    (map? data) (str (when-let [entity-ids (:ids data)]
                       (doseq [entity-id entity-ids]
                         (remove-contacts entity-id))
                       (remove-entities entity-ids)))

    (string? data) (str (when-let [entity-id (Long. data)]
                          (remove-contacts entity-id)
                          (remove-entity entity-id)))))

;------------------------------
; END Entities functions
;------------------------------

;------------------------------
; BEGIN Contacts functions
;------------------------------

(defn- get-contacts*
  [data]
  (ms-atmos-cond-response
    (string? data) (get-contacts (Long. data))))


(defn- add-contacts*
  [data]
  (ms-atmos-let-cond-response
    [contacts (:contacts data)]

    (vector? contacts) (str (do
                              (doseq [contact contacts]
                                (add-contact (keyword-map contact)))
                              true))))



(defn- update-contacts*
  [data]
  (ms-atmos-let-cond-response
    [contacts (:contacts data)]

    (vector? contacts) (str (do
                              (doseq [contact contacts]
                                (update-contact (keyword-map contact)))
                              true))))

;------------------------------
; END Contacts functions
;------------------------------
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


           (GET
             (ms-atmos-method :entities contacts :entity-id)
             [entity-id]
             (get-contacts* entity-id))

           (POST
             (ms-atmos-method :entities contacts)
             request
             (update-contacts* (request-body request)))


           (PUT
             (ms-atmos-method :entities contacts)
             request
             (add-contacts* (request-body request)))

           not-found-route)

(def app (make-json-app app-routes))


