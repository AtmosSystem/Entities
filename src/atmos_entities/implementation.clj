(in-ns 'atmos-entities.core)

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

(defn- add-persist-entity*
  [entity]
  (insert entities
          (values entity)))

(defn- update-persist-entity*
  [entity]
  (update entities
          (set-fields entity)
          (where {:id (:id entity)})))

(def ^:private get-persist-entity-base* (-> (select* entities)))

(defn- get-persist-entity*
  [where-filter]
  (-> get-persist-entity-base*
      (where where-filter)
      select))

(defn- get-persist-entities*
  [ids]
  (-> get-persist-entity-base*
      (where {:id [in ids]})
      select))

(defn- remove-persist-entities*
  [where-filter]
  (delete entities
          (where where-filter)))


(defn- add-entity*
  [entity]
  (if-let [key-inserted (add-persist-entity* entity)]
    (:generated_key key-inserted)
    false))

(defn- update-entity*
  [entity]
  (if-let [exists (get-entity (:id entity))]
    (do
      (update-persist-entity* entity)
      true)
    false))

(defn- get-entities*
  [data]
  (cond
    (number? data) (first (get-persist-entity* {:id data}))
    (vector? data) (get-persist-entities* data)))

(defn- remove-entities*
  [id]
  (let [where-filter {:id id}]
    (if-let [entity (get-entity id)]
      (do
        (remove-persist-entities* where-filter)
        true)
      false)))

;------------------------------
; BEGIN Entity public functions
;------------------------------

(defn get-all-entities
  []
  (-> get-persist-entity-base*
      select))

;------------------------------
; END Entity public functions
;------------------------------

(extend-type PersistentArrayMap
  IEntityBasicRepository
  (add-entity [entity] (add-entity* entity))
  (update-entity [entity] (update-entity* entity)))

(extend-protocol IEntityIdentityRepository
  BigInteger
  (get-entity [id] (get-entities* id))
  (remove-entity [id] (remove-entities* id))
  Long
  (get-entity [id] (get-entities* id))
  (remove-entity [id] (remove-entities* id)))

(extend-protocol IEntitySeqRepository
  PersistentArrayMap
  (get-entities [range] (get-entities* range))
  PersistentVector
  (get-entities [ids] (get-entities* ids)))

;------------------------------
; END - Entity functions
;------------------------------
