(in-ns 'atmos-entities.core)

(declare contacts)

(defentity ^:private entities
           (pk :id)

           (entity-fields :id :type :name :lastName)

           (has-many contacts))

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


(extend-type Map
  IEntityBasicRepository
  (add-entity [entity] (add-entities* entity))
  (update-entity [entity] (update-entity* entity)))

(extend-type IPersistentCollection
  IEntitySeqRepository
  (get-entities [ids] (get-entities* ids))
  (remove-entities [ids] (remove-entities* ids)))

(extend-protocol IEntityIdentityRepository
  Number
  (get-entity [id] (get-entities* id))
  (remove-entity [id] (remove-entities* id)))

