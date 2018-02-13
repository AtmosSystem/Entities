(ns atmos-entities.implementation
  (:require [korma.db :as sql]
            [korma.core :refer :all]
            [atmos-entities.core :refer :all])
  (:import (atmos_entities.core Entity)))


(declare database-instance)

(defentity ^:private entities
           ;(database database-instance)
           (table :entities)

           (pk :id)
           (entity-fields :type :name :lastName))

(defn- add-entity*
  [entity]
  (do
    (insert entities (values entity))
    true))


(defn db-definition [db-map] (sql/mysql db-map))
(defn init-persistance [db] (sql/defdb atmos-entities db))

(extend-type Entity
  IEntityRepository
  (add-entity [entity] (add-entity* entity))
  (update-entity [entity] false)
  (remove-entity [entity] false)
  (get-entity [entity] false))
