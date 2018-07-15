(ns atmos-entities.service
  (:require [atmos-entities.core :refer :all]
            [atmos-entities.implementation.core :refer :all]))


;------------------------------
; BEGIN Entities functions
;------------------------------

(defn get-entities*
  ([data]
   (cond
     (nil? data) (get-all-entities)
     (map? data) (get-entities (seq (:ids data)))
     (number? data) (get-entity data)))
  ([]
   (get-entities* nil)))

(defn add-entities*
  [data]
  (let
    [entities (:entities data)]
    (cond
      (map? entities) (add-entity entities))))


(defn update-entities*
  [data]
  (let
    [entities (:entities data)]
    (cond
      (map? entities) (update-entity entities))))


(defn remove-entities*
  [data]
  (cond
    (map? data) (str (when-let [entity-ids (:ids data)]
                       (doseq [entity-id entity-ids]
                         (remove-contacts entity-id))
                       (remove-entities entity-ids)))

    (string? data) (str (when-let [entity-id (Long. (str data))]
                          (remove-contacts entity-id)
                          (remove-entity entity-id)))))

;------------------------------
; END Entities functions
;------------------------------

;------------------------------
; BEGIN Contacts functions
;------------------------------

(defn get-contacts*
  [data]
  (cond
    (string? data) (get-contacts (Long. (str data)))))


(defn add-contacts*
  [data]
  (let
    [contacts (:contacts data)]
    (cond
      (vector? contacts) (str (do
                                (doseq [contact contacts]
                                  (add-contact contact))
                                true)))))



(defn update-contacts*
  [data]
  (let
    [contacts (:contacts data)]
    (cond
      (vector? contacts) (str (do
                                (doseq [contact contacts]
                                  (update-contact contact))
                                true)))))

;------------------------------
; END Contacts functions
;------------------------------
