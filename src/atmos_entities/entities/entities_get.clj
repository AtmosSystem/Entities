(in-ns 'atmos-entities.core)


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

(defn- get-entities*
  [data]
  (cond
    (number? data) (first (get-persist-entity* {:id data}))
    (vector? data) (get-persist-entities* data)))

(defn get-all-entities
  []
  (-> get-persist-entity-base*
      select))
