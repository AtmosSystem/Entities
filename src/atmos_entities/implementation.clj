(ns atmos-entities.implementation
  (:require [korma.db :as sql]
            [korma.core :refer :all]
            [atmos-entities.core :refer :all])
  (:import (clojure.lang PersistentArrayMap)))

;------------------------------
; BEGIN General functions
;------------------------------

(defn defpersistence [db-map] (sql/mysql db-map))

(defn init-persistence [db-definition] (sql/defdb atmos-entities db-definition))

;------------------------------
; END - General functions
;------------------------------

;------------------------------
; BEGIN Entity functions
;------------------------------

(defentity ^:private entities
           (table :entities)

           (pk :id)
           (entity-fields :id :type :name :lastName))

(defn- add-entity*
  [entity]
  (do
    (insert entities (values entity))
    true))

(defn- get-entities*
  [where-filter]
  (select entities
          (where where-filter)))

(defn- remove-entities*
  [where-filter]
  (delete entities
          (where where-filter)))

(extend-type PersistentArrayMap
  IEntityBasicRepository
  (add-entity [entity] (add-entity* entity))
  (update-entity [entity] false)
  (remove-entity [entity] false))

(extend-type Long
  IEntityIdentityRepository
  (get-entity [id] (first (get-entities* {:id id})))
  (remove-entity [id] (remove-entities* {:id id})))

;------------------------------
; END - Entity functions
;------------------------------
