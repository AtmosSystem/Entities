(in-ns 'atmos-entities.core)

(def ^:private remove-persist-entity-base* (-> (delete* entities)))

(defn- remove-persist-entity*
  [id]
  (-> remove-persist-entity-base*
      (where {:id id})
      delete))

(defn- remove-persist-entities*
  [ids]
  (-> remove-persist-entity-base*
      (where {:id [in ids]})
      delete))

(defn- remove-entities*
  [data]
  (let [remove-fn (fn
                    [remove-entity-fn]
                    (if-let [entity (get-entities* data)]
                      (do
                        (remove-entity-fn data)
                        true)
                      false))]
    (cond
      (number? data) (remove-fn remove-persist-entity*)
      (vector? data) (remove-fn remove-persist-entities*))))