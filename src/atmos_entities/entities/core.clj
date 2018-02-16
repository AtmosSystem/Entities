(in-ns 'atmos-entities.core)


(defentity ^:private entities
           (table :entities)

           (pk :id)
           (entity-fields :id :type :name :lastName))

;------------------------------
; BEGIN Entity functions
;------------------------------
(load "entities/entities_get")
(load "entities/entities_add")
(load "entities/entities_update")
(load "entities/entities_remove")
;------------------------------
; END Entity functions
;------------------------------


(extend-type PersistentArrayMap
  IEntityBasicRepository
  (add-entity [entity] (add-entities* entity))
  (update-entity [entity] (update-entity* entity)))

(extend-type PersistentVector
  IEntitySeqRepository
  (get-entities [ids] (get-entities* ids))
  (remove-entities [ids] (remove-entities* ids)))

(extend-protocol IEntityIdentityRepository
  Integer
  (get-entity [id] (get-entities* id))
  (remove-entity [id] (remove-entities* id))
  BigInteger
  (get-entity [id] (get-entities* id))
  (remove-entity [id] (remove-entities* id))
  Long
  (get-entity [id] (get-entities* id))
  (remove-entity [id] (remove-entities* id)))

