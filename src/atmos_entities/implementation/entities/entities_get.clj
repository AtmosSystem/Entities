(in-ns 'atmos-entities.implementation.core)


(def ^:private get-persist-entity-base* (-> (select* entities)))

(def ^:private get-persist-entity-by #(-> get-persist-entity-base*
                                          (where %)
                                          select))

(declare get-all-entities get-first-entities*)

(defget-all-entity entities #(-> get-persist-entity-base*
                                 select))

(defget-identity-entity entities get-persist-entity-by [id])


(defn- get-persist-entities*
  [ids]
  (-> get-persist-entity-base*
      (where {:id [in ids]})
      select))

(defn- get-entities*
  [data]
  (cond
    (number? data) (first (get-first-entities* data))
    (vector? data) (get-persist-entities* data)))


