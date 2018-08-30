(ns atmos-entities.implementation.core
  (:require [atmos-entities.core :refer :all]
            [environ.core :refer [env]]
            [atmos-kernel.security.ssl :refer [defssl]]
            [atmos-kernel.configuration :refer [read-edn]]
            [atmos-data-kernel.persistence.core :refer [defpersistence]]
            [korma.core :refer :all]
            [korma.db :refer [defdb]]
            [atmos-data-kernel.persistence.sql :refer [defget-all-entity
                                                       defget-entity
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
(-> configuration :ssl-data defssl)
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


(extend-type Map
  IEntityBasicProtocol
  (add-entity [entity] (add-entities* entity))
  (update-entity [entity] (update-entities* entity)))

(extend-type Number
  IEntityIdentityProtocol
  (get-entity [id] (get-entities* id)))

(extend-type ISeq
  IEntitySeqProtocol
  (get-entities [ids] (get-entities* (vec ids))))
;------------------------------
; END Entities functions
;------------------------------


;------------------------------
; BEGIN Contacts functions
;------------------------------
(load "contacts/contacts_get")
(load "contacts/contacts_add")
(load "contacts/contacts_update")


(extend-type Map
  IContactBasicProtocol
  (add-contact [contact] (add-contacts* contact))
  (update-contact [contact] (update-contacts* contact)))

(extend-type Number
  IContactSeqProtocol
  (get-contacts [entity-id] (get-contacts* entity-id))
  IContactIdentityProtocol
  (get-contact [id] (get-first-contacts* id)))
;------------------------------
; END Contacts functions
;------------------------------
