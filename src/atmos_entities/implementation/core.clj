(ns atmos-entities.implementation.core
  (:require [atmos-entities.core :refer :all]
            [environ.core :refer [env]]
            [atmos-kernel.configuration :refer [read-edn]]
            [atmos-data-kernel.persistence.core :refer [defpersistence]]
            [korma.core :refer :all]
            [korma.db :refer [defdb]]
            [atmos-data-kernel.persistence.sql :refer [defget-all-entity
                                                       defget-identity-entity
                                                       defadd-entity
                                                       defupdate-entity]])
  (:import (clojure.lang ISeq)
           (java.util Map)))


;-------------------------------------------------------
; BEGIN VARS
;-------------------------------------------------------
(declare atmos-entities contacts)

(def ^:private persistence-type :mysql)

(def ^:private resource-file (or (keyword (env :resource-file)) :config-prod))
(def ^:private configuration (read-edn resource-file))
;-------------------------------------------------------
; END VARS
;-------------------------------------------------------

; Persistence initialization
(->> configuration :database (defpersistence persistence-type) (defdb atmos-entities))


(defentity ^:private entities
           (pk :id)

           (entity-fields :id :type :name :lastName)

           (has-many contacts))

(defentity contacts
           (pk :id)

           (entity-fields :id :entity_id :type :description :value :emergency)

           (belongs-to entities {:fk :entity_id}))

;------------------------------
; BEGIN Entities functions
;------------------------------
(load "entities/entities_get")
(load "entities/entities_add")
(load "entities/entities_update")
(load "entities/entities_remove")


(extend-type Map
  IEntityBasicProtocol
  (add-entity [entity] (add-entities* entity))
  (update-entity [entity] (update-entity* entity)))

(extend-type Number
  IEntityIdentityProtocol
  (get-entity [id] (get-entities* id))
  (remove-entity [id] (remove-entities* id)))

(extend-type ISeq
  IEntitySeqProtocol
  (get-entities [ids] (get-entities* ids))
  (remove-entities [ids] (remove-entities* ids)))
;------------------------------
; END Entities functions
;------------------------------


;------------------------------
; BEGIN Contacts functions
;------------------------------
(load "contacts/contacts_get")
(load "contacts/contacts_add")
(load "contacts/contacts_update")
(load "contacts/contacts_remove")


(extend-type Map
  IContactBasicProtocol
  (add-contact [contact] (add-contacts* contact))
  (update-contact [contact] (update-contacts* contact)))

(extend-type Number
  IContactSeqProtocol
  (get-contacts [entity-id] (get-contacts* entity-id))
  (remove-contacts [entity-id] (remove-contacts* entity-id))
  IContactIdentityProtocol
  (get-contact [id] (get-contact* id))
  (remove-contact [id] (remove-contact* id)))
;------------------------------
; END Contacts functions
;------------------------------
